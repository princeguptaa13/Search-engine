package com.Search_Engine.Search_engine.Controller;

import com.Search_Engine.Search_engine.Crawler.Engine.DFSCrawler;
import com.Search_Engine.Search_engine.Storage.InMemoryPageStore;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class CrawlController {

    // Trigger crawl via POST /api/crawl?url=https://spring.io&depth=1
    @PostMapping("/crawl")
    public Map<String, Object> startCrawl(@RequestParam String url,
                                          @RequestParam(defaultValue = "1") int depth) {

        // Create crawler instance manually
        DFSCrawler crawler = new DFSCrawler(depth, url);

        // Start crawling
        crawler.crawl(url, 0);

        return Map.of(
                "message", "✅ Crawl finished",
                "seed", url,
                "depth", depth,
                "pagesIndexed", InMemoryPageStore.getAll().size()
        );
    }

    // Optional: Check number of pages indexed
    @GetMapping("/pages/count")
    public Map<String, Object> pageCount() {
        return Map.of("pages", InMemoryPageStore.getAll().size());
    }
}
