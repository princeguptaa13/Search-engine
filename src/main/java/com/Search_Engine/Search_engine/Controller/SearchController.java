package com.Search_Engine.Search_engine.Controller;

import com.Search_Engine.Search_engine.Search.Model.SearchResult;
import com.Search_Engine.Search_engine.Search.Service.SearchService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {

    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping
    public List<SearchResult> search(@RequestParam String query) {
        return searchService.search(query);   // ✅ Now both use the same SearchResult
    }
}
