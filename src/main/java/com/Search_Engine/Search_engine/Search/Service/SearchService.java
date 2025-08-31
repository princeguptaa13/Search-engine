package com.Search_Engine.Search_engine.Search.Service;

import com.Search_Engine.Search_engine.Search.Model.PageData;
import com.Search_Engine.Search_engine.Search.Model.SearchResult;
import com.Search_Engine.Search_engine.Storage.InMemoryPageStore;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchService {

    /**
     * Search pages in InMemoryPageStore by keyword.
     */
    public List<SearchResult> search(String keyword) {
        String k = keyword.toLowerCase();
        List<SearchResult> results = new ArrayList<>();

        for (PageData page : InMemoryPageStore.getAll().values()) {
            if (page.getTitle().toLowerCase().contains(k) ||
                    page.getTextSnippet().toLowerCase().contains(k)) {

                double score = computeScore(page, k);
                results.add(new SearchResult(
                        page.getUrl(),
                        page.getTitle(),
                        createSnippet(page.getTextSnippet(), k),
                        score
                ));
            }
        }

        // Sort results by score descending
        results.sort((a, b) -> Double.compare(b.getScore(), a.getScore()));
        return results;
    }

    /**
     * Simple scoring: matches in title = 2 points, in body = 1 point.
     */
    private double computeScore(PageData page, String keyword) {
        int titleMatches = countOccurrences(page.getTitle().toLowerCase(), keyword);
        int bodyMatches = countOccurrences(page.getTextSnippet().toLowerCase(), keyword);
        return titleMatches * 2 + bodyMatches;
    }

    /**
     * Count occurrences of a token in a text.
     */
    private int countOccurrences(String text, String token) {
        if (token.isEmpty()) return 0;
        return text.split(java.util.regex.Pattern.quote(token), -1).length - 1;
    }

    /**
     * Create a snippet showing the keyword in context.
     */
    private String createSnippet(String text, String keyword) {
        int idx = text.toLowerCase().indexOf(keyword);
        if (idx == -1) return text.substring(0, Math.min(150, text.length())) + "...";
        int start = Math.max(0, idx - 50);
        int end = Math.min(text.length(), idx + 50);
        return text.substring(start, end) + "...";
    }
}
