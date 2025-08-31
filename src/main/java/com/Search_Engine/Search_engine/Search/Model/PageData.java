package com.Search_Engine.Search_engine.Search.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageData {
    private String url ;
    private String title ;
    private String textSnippet ;
    private int depth ;
}
