//package com.Search_Engine.Search_engine.Controller;
//
//
//import com.Search_Engine.Search_engine.Crawler.Model.SearchResult;
//import com.Search_Engine.Search_engine.Crawler.Service.SearchService;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/search")
//public class SearchController {
//
//    private final SearchService searchService = new SearchService();
//
//    @GetMapping
//    public List<SearchResult> search(@RequestParam String query) {
//        return searchService.search(query);
//    }
//}
package com.Search_Engine.Search_engine.Controller;

import com.Search_Engine.Search_engine.Crawler.Model.SearchResult;
import com.Search_Engine.Search_engine.Crawler.Service.SearchService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {

    private final SearchService searchService;

    // ✅ Constructor injection (Spring will inject SearchService automatically)
    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping
    public List<SearchResult> search(@RequestParam String query) {
        return searchService.search(query);
    }
}

