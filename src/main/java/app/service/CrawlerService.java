package app.service;

import java.util.HashMap;

public interface CrawlerService {
    public HashMap<String, Integer> getMetaKeywords(String s);
    public HashMap<String, Integer> getContentKeywords(String s);
}
