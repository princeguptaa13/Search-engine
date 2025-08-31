package com.Search_Engine.Search_engine.Crawler.Engine;

import com.Search_Engine.Search_engine.Common.Util.HtmlUtils;
import com.Search_Engine.Search_engine.Search.Model.PageData;
import com.Search_Engine.Search_engine.Storage.InMemoryPageStore;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class DFSCrawler {

    private final Set<String> visited = ConcurrentHashMap.newKeySet();
    private final int maxDepth;
    private final String baseDomain;

    // Constructor with runtime values
    public DFSCrawler(int maxDepth, String startUrl) {
        this.maxDepth = maxDepth;
        this.baseDomain = extractDomain(startUrl);
    }

    public void crawl(String url, int depth) {
        if (depth > maxDepth || visited.contains(url)) return;

        try {
            Document doc = Jsoup.connect(url)
                    .userAgent("SearchBot/1.0")
                    .timeout(6000)
                    .get();

            String cleanedText = HtmlUtils.cleanText(doc);
            String title = doc.title();

            // Save page
            InMemoryPageStore.save(new PageData(url, title, cleanedText, depth));
            visited.add(url);

            // Crawl links
            Elements links = doc.select("a[href]");
            for (var link : links) {
                String absHref = link.absUrl("href");
                if (isSameDomain(absHref)) {
                    crawl(absHref, depth + 1);
                }
            }

            // Politeness delay
            Thread.sleep(200);

        } catch (IOException e) {
            System.err.println("❌ Failed to fetch: " + url + " | " + e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private String extractDomain(String url) {
        try {
            URI uri = new URI(url);
            return uri.getHost() != null ? uri.getHost() : "";
        } catch (URISyntaxException e) {
            return "";
        }
    }

    private boolean isSameDomain(String url) {
        return baseDomain.isEmpty() || url.contains(baseDomain);
    }
}
