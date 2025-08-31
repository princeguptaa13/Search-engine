package com.Search_Engine.Search_engine.Crawler.Repository;

import com.Search_Engine.Search_engine.Search.Model.SearchResult;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Repository
public class InMemoryIndex {
    private final List<SearchResult> index = new CopyOnWriteArrayList<>();

    public void add(SearchResult result) {
        index.add(result);
    }

    public List<SearchResult> search(String query) {
        List<SearchResult> results = new ArrayList<>();
        for (SearchResult result : index) {
            if (result.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                    result.getUrl().toLowerCase().contains(query.toLowerCase())) {
                results.add(result);
            }
        }
        return results;
    }

    public List<SearchResult> getAll() {
        return index;
    }

    public void clear() {
        index.clear();
    }
}
