package com.mockcompany.webapp.controller;

import com.mockcompany.webapp.api.SearchReportResponse;
import com.mockcompany.webapp.model.ProductItem;
import com.mockcompany.webapp.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Management decided it is super important that we have lots of products that match the following terms.
 * So much so, that they would like a daily report of the number of products for each term along with the total
 * product count.
 */
@RestController
public class ReportController {

    private final EntityManager entityManager;
    private final SearchService searchService; // Injecting SearchService

    @Autowired
    public ReportController(EntityManager entityManager, SearchService searchService) {
        this.entityManager = entityManager;
        this.searchService = searchService;
    }

    @GetMapping("/api/products/report")
    public SearchReportResponse runReport() {
        // Initialize response object and result map
        Map<String, Integer> hits = new HashMap<>();
        SearchReportResponse response = new SearchReportResponse();
        response.setSearchTermHits(hits);

        // Total product count
        int count = this.entityManager.createQuery("SELECT item FROM ProductItem item").getResultList().size();
        response.setProductCount(count);

        // Search for "Cool"
        int coolCount = searchService.search("Cool").size();
        hits.put("Cool", coolCount);

        // Search for "Kids" using regex
        List<ProductItem> allItems = entityManager.createQuery("SELECT item FROM ProductItem item").getResultList();
        int kidCount = 0;
        Pattern kidPattern = Pattern.compile("(.*)[kK][iI][dD][sS](.*)");
        for (ProductItem item : allItems) {
            if (kidPattern.matcher(item.getName()).matches() || kidPattern.matcher(item.getDescription()).matches()) {
                kidCount++;
            }
        }
        hits.put("Kids", kidCount);

        // Search for "Amazing"
        int amazingCount = searchService.search("Amazing").size();
        hits.put("Amazing", amazingCount);

        // Search for "Perfect"
        int perfectCount = searchService.search("Perfect").size();
        hits.put("Perfect", perfectCount);

        return response;
    }
}
