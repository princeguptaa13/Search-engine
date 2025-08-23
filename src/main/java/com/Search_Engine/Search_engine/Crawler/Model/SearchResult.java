package com.Search_Engine.Search_engine.Crawler.Model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchResult {
    private String url;
    private String title;
    private String snippet;
    private double score; // Simple relevance score
}

