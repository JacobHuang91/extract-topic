package app.service;

import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URL;
import java.util.HashMap;

public class UserInputUrlService implements UrlService {

    private Sentence2WordsService sentence2WordsService;

    @Autowired
    public UserInputUrlService(Sentence2WordsService sentence2WordsService) {
        this.sentence2WordsService = sentence2WordsService;
    }

    /**
     * check if the input url is valid
     *
     * @param s the URL String
     * @return if the URL is valid
     */
    public boolean isValid(String s) {
        System.out.println("==========> Start to check the validation of URL");
        UrlValidator urlValidator = new UrlValidator();
        if (urlValidator.isValid(s)) {
            System.out.println("URL is valid");
            return true;
        } else {
            System.out.println("URL is not valid");
            return false;
        }
    }

    /**
     * @param s the URL String
     * @return HashMap<String                               ,                                                               Integer> the URL Keywords
     */
    public HashMap<String, Integer> parseUrl(String s) {
        System.out.println("==========> Start to collect keywords in URL");
        System.out.println("The input URL is: " + s);
        try {
            URL url = new URL(s.trim());
            // path and query always have some Keywords
            System.out.println("path = " + url.getPath());
            System.out.println("query = " + url.getQuery());
            String path = url.getPath();
            String query = url.getQuery();


            // parse path
            HashMap<String, Integer> keywordMapPath = sentence2WordsService.parseSentenceUrl(path);
            // parse query
            HashMap<String, Integer> keywordMapQuery = sentence2WordsService.parseSentenceQuery(query);

            HashMap<String, Integer> keywordMap = new HashMap<>();
            keywordMap.putAll(keywordMapPath);

            if (keywordMapQuery == null || keywordMapQuery.size() == 0) {
                return keywordMap;
            }

            for (String key : keywordMapQuery.keySet()) {
                if (keywordMap.containsKey(key)) {
                    keywordMap.put(key, keywordMap.get(key) + keywordMapQuery.get(key));
                } else {
                    keywordMap.put(key, keywordMapQuery.get(key));
                }
            }

            return keywordMap;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
