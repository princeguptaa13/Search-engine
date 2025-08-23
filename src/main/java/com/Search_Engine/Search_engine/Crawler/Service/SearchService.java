package com.Search_Engine.Search_engine.Crawler.Service;


import com.Search_Engine.Search_engine.Crawler.Model.PageData;
import com.Search_Engine.Search_engine.Crawler.Model.SearchResult;

import com.Search_Engine.Search_engine.Storage.InMemoryPageStore;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchService{

    public List<SearchResult> search(String keyword) {
        keyword = keyword.toLowerCase();
        List<SearchResult> results = new ArrayList<>();

        for (PageData page : InMemoryPageStore.getAll().values()) {
            if (page.getTitle().toLowerCase().contains(keyword) ||
                    page.getTextSnippet().toLowerCase().contains(keyword)) {

                double score = computeScore(page, keyword);
                results.add(new SearchResult(
                        page.getUrl(),
                        page.getTitle(),
                        createSnippet(page.getTextSnippet(), keyword),
                        score
                ));
            }
        }

        return results;
    }

    private double computeScore(PageData page, String keyword) {
        int titleMatches = page.getTitle().toLowerCase().split(keyword, -1).length - 1;
        int bodyMatches = page.getTextSnippet().toLowerCase().split(keyword, -1).length - 1;
        return titleMatches * 2 + bodyMatches; // Simple scoring rule
    }

    private String createSnippet(String text, String keyword) {
        int idx = text.toLowerCase().indexOf(keyword);
        if (idx == -1) return text.substring(0, Math.min(150, text.length())) + "...";
        int start = Math.max(0, idx - 50);
        int end = Math.min(text.length(), idx + 50);
        return text.substring(start, end) + "...";
    }
}

