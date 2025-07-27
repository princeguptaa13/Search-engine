package com.Search_Engine.Search_engine.Storage;

import com.Search_Engine.Search_engine.Crawler.Model.PageData;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;


public class InMemoryPageStore {

    // ConcurrentHashMap ensures thread-safe operations
        private static final ConcurrentHashMap<String, PageData> store = new ConcurrentHashMap<>();
        /**
         * Saves a page if not already present (to avoid duplicates).
         */
        public static void save(PageData pageData) {
            store.putIfAbsent(pageData.getUrl(), pageData);
        }
        /**
         * Retrieves a PageData by its URL.
         */
        public static PageData get(String url) {
            return store.get(url);
        }
        /**
         * Searches the store for pages containing the keyword in title or snippet.
         */
        public static ConcurrentHashMap<String, PageData> search(String keyword) {
            ConcurrentHashMap<String, PageData> results = new ConcurrentHashMap<>();
            String lowerKeyword = keyword.toLowerCase();

            for (var entry : store.entrySet()) {
                PageData data = entry.getValue();
                if (data.getTitle().toLowerCase().contains(lowerKeyword) ||
                        data.getTextSnippet().toLowerCase().contains(lowerKeyword)) {
                    results.put(entry.getKey(), data);
                }
            }
            return results;
        }
        /**
         * Prints all stored pages to console (for testing).
         */
        public static void printAll() {
            store.values().forEach(System.out::println);
        }
        /**
         * Returns the entire map (for external use).
         */
        public static ConcurrentHashMap<String, PageData> getAll() {
            return store;
        }
}

