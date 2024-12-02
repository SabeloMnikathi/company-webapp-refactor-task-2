package com.mockcompany.webapp.controller;

import com.mockcompany.webapp.data.ProductItemRepository;
import com.mockcompany.webapp.model.ProductItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Service class encapsulating the search logic.
 */
@Service
class SearchService {

    private final ProductItemRepository productItemRepository;

    @Autowired
    public SearchService(ProductItemRepository productItemRepository) {
        this.productItemRepository = productItemRepository;
    }

    public Collection<ProductItem> search(String query) {
        Iterable<ProductItem> allItems = productItemRepository.findAll();
        List<ProductItem> itemList = new ArrayList<>();

        boolean exactMatch = query.startsWith("\"") && query.endsWith("\"");
        if (exactMatch) {
            query = query.substring(1, query.length() - 1); // Remove quotes for exact match
        } else {
            query = query.toLowerCase(); // Normalize for case-insensitive match
        }

        for (ProductItem item : allItems) {
            boolean nameMatches;
            boolean descMatches;

            if (exactMatch) {
                nameMatches = query.equals(item.getName());
                descMatches = query.equals(item.getDescription());
            } else {
                nameMatches = item.getName().toLowerCase().contains(query);
                descMatches = item.getDescription().toLowerCase().contains(query);
            }

            if (nameMatches || descMatches) {
                itemList.add(item);
            }
        }
        return itemList;
    }
}

/**
 * Controller class exposing the search API.
 */
@RestController
public class SearchController {

    private final SearchService searchService;

    @Autowired
    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/api/products/search")
    public Collection<ProductItem> search(@RequestParam("query") String query) {
        return searchService.search(query);
    }
}
