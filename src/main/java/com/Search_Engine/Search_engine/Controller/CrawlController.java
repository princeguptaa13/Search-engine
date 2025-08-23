//package com.Search_Engine.Search_engine.Controller;
//
//
//import com.Search_Engine.Search_engine.Crawler.Engine.DFSCrawler;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api")
//public class CrawlController {
//
//    @Autowired
//    private final DFSCrawler dfsCrawler;
//
//    public CrawlController(DFSCrawler dfsCrawler1) {
//        this.dfsCrawler = dfsCrawler1;
//    }
//
//    @PostMapping("/crawl")
//    public String startCrawl(@RequestParam String url) {
//        dfsCrawler.crawl(url , 2);  // Trigger the crawl
//        return "Crawling started for: " + url;
//    }
//}
//

package com.Search_Engine.Search_engine.Controller;

import com.Search_Engine.Search_engine.Crawler.Engine.DFSCrawler;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CrawlController {

    @PostMapping("/crawl")
    public String startCrawl(@RequestParam String url,
                             @RequestParam(defaultValue = "2") int depth) {

        // Create crawler manually
        DFSCrawler dfsCrawler = new DFSCrawler(depth, url);
        dfsCrawler.crawl(url, 0);

        return "✅ Crawling started for: " + url;
    }
}

