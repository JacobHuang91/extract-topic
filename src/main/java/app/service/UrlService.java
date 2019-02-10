package app.service;

import java.util.HashMap;

public interface UrlService {
    public boolean isValid(String s);
    public HashMap<String, Integer> parseUrl(String s);
}
