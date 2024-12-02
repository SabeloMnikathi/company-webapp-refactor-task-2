### README  

# **E-Commerce Web Application - Search and Report Service Refactoring**  

## **Overview**  
This project focuses on improving the maintainability and scalability of the e-commerce web application's search and reporting functionalities. The refactor involves creating a reusable `SearchService` to centralize search logic and updating the `SearchController` and `ReportController` to utilize the service.  

The result is a cleaner, more modular architecture, ensuring the application is easier to maintain and extend in the future.  

---

## **Features Implemented**  

### **1. Centralized Search Logic with `SearchService`**  
- Moved the search functionality from the `SearchController` to a newly created `SearchService` class.  
- `SearchService` encapsulates:  
  - Exact and partial matches for product names and descriptions.  
  - Case-insensitive and regex-based search capabilities.  
  - Integration with the `ProductItemRepository` for efficient data querying.  

### **2. Refactored Controllers**  
#### **SearchController**  
- Refactored to delegate all search logic to the `SearchService`.  
- Acts as an API entry point to handle HTTP requests and pass queries to the service.  

#### **ReportController**  
- Rewritten to use the `SearchService` for term matching and product search reporting.  
- Retained unique reporting features, such as regex matching for specific terms and generating summary reports.  

---

## **Usage**  

### **Search API**  
The `SearchController` provides an endpoint for searching products based on a query.  

#### **Endpoint:**  
`GET /api/products/search`  

#### **Query Parameter:**  
- `query` (String): The search term to match product names or descriptions.  

#### **Response:**  
Returns a collection of matching products.  

### **Daily Report API**  
The `ReportController` generates a daily report summarizing product counts and matches for specific terms.  

#### **Endpoint:**  
`GET /api/products/report`  

#### **Response:**  
A detailed summary of product counts and matches for key terms like "Cool," "Kids," and "Perfect."  

---

## **Technical Highlights**  

### **1. Modular Design**  
- Logic is encapsulated in `SearchService`, promoting code reusability.  
- Controllers are streamlined to focus on request handling.  

### **2. Dependency Injection**  
- `@Autowired` is used to inject dependencies, adhering to Spring's IoC (Inversion of Control) principles.  

### **3. Enhanced Querying**  
- Combined JPA queries and in-memory filtering for performance and flexibility.  
- Used regex patterns for advanced term matching.  

### **4. Scalability**  
- Centralizing search logic in `SearchService` ensures that future enhancements can be made in a single place without impacting the controllers.  

---

## **Setup**  

### **Requirements**  
- Java 11+  
- Gradle 6+  
- Spring Boot 2.5+  

### **Steps to Run**  
1. Clone the repository:  
   ```bash
   git clone <https://github.com/SabeloMnikathi/company-webapp-refactor-task-2>
   cd mockcompany-webapp
   ```  
2. Build the project:  
   ```bash
   ./gradlew build
   ```  
3. Run the application:  
   ```bash
   ./gradlew bootRun
   ```  
4. Access the APIs via your preferred HTTP client or browser:  
   - Search API: `http://localhost:8080/api/products/search?query=<search-term>`  
   - Report API: `http://localhost:8080/api/products/report`  

---

## **Lessons Learned**  

1. Refactoring for maintainability ensures long-term benefits, making the codebase easier to extend and debug.  
2. The importance of separating concerns by centralizing business logic into services, leaving controllers to handle only web-specific tasks.  
3. Advanced querying techniques, including JPA and regex-based searches, allow for flexible and precise data filtering.  
4. Using Spring's Dependency Injection promotes cleaner, testable, and scalable code.  

---

## **Next Steps**  

1. **Add Unit Tests:**  
   - Increase test coverage for the `SearchService`.  
   - Ensure edge cases like invalid inputs are thoroughly tested.  

2. **Enhance Reporting Features:**  
   - Include more detailed metrics and data visualizations in the report response.  

3. **Performance Optimization:**  
   - Refactor regex matching to improve efficiency for large datasets.  

4. **Documentation:**  
   - Expand API documentation for better consumer understanding.  

--- 

## **Author**  
This refactor was implemented by Sabelo Mnikathi, focusing on improving the application's architecture while adhering to industry best practices.  
