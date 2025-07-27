package com.Search_Engine.Search_engine.Crawler.Engine;

import com.Search_Engine.Search_engine.Common.Util.HtmlUtils;
import com.Search_Engine.Search_engine.Crawler.Model.PageData;
import com.Search_Engine.Search_engine.Storage.InMemoryPageStore;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class DFSCrawler {

    private final Set<String> visited = new HashSet<>();
    private final int maxDepth;

    public DFSCrawler(int maxDepth) {
        this.maxDepth = maxDepth;
    }

    public void crawl(String url, int depth) {
        if (depth > maxDepth || visited.contains(url)) return;

        try {
            Document doc = Jsoup.connect(url).get();

            String cleanedText = HtmlUtils.cleanText(doc);
            String title = doc.title();

            PageData page = new PageData(url, title, cleanedText , depth);
            InMemoryPageStore.save(page);
            visited.add(url);

            Elements links = doc.select("a[href]");
            for (var link : links) {
                String absHref = link.absUrl("href");
                if (absHref.startsWith(url)) { // Optional: restrict to same domain
                    crawl(absHref, depth + 1);
                }
            }

        } catch (IOException e) {
            System.err.println("Failed to fetch: " + url + " | Reason: " + e.getMessage());
        }
    }
}
