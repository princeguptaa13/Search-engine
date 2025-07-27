package com.Search_Engine.Search_engine.Common.Util;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

    public class HtmlUtils {
        // Clean HTML by parsing and normalizing
        public static String cleanText(String html) {
            if (html == null || html.isEmpty()) return "";
            Document doc = Jsoup.parse(html);
            return normaliseText(doc.body().text());
        }

        // Clean directly from a Jsoup Document
        public static String cleanText(Document doc) {
            return normaliseText(doc.body().text());
        }

        // Normalize text: remove non-alphanumerics, lower case, and trim
        public static String normaliseText(String text) {
            return text
                    .trim()
                    .replaceAll("[^a-z0-9 ]", " ")  // Keep only letters, numbers, spaces
                    .replaceAll("\\s+", " ")        // Collapse multiple spaces
                    .toLowerCase();                 // Convert to lowercase
        }
    }


