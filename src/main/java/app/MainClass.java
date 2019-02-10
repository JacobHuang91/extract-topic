package app;

import app.Config.AppConfig;
import app.service.CrawlerService;
import app.service.UrlService;
import app.service.UserInputService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.*;

public class MainClass {
    public static void main(String[] args) {
        // Keywords maps
        HashMap<String, Integer> resultMap;
        HashMap<String, Integer> urlKeywords;
        HashMap<String, Integer> metaKeywords;
        HashMap<String, Integer> contentKeywords;

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        // 1. parse user input
        // 1.1 get user input
        UserInputService userInputService = context.getBean("userInputService", UserInputService.class);
        String userInputUrl = userInputService.getUserUrl();

        // 1.2 validate the url
        UrlService urlService = context.getBean("userInputUrlService", UrlService.class);
        if (!urlService.isValid(userInputUrl)) {
            context.close();
            System.exit(1);
        }

        // 1.3 parse url and get some Keywords
        urlKeywords = urlService.parseUrl(userInputUrl);

        // 2. crawler the content of the url
        CrawlerService crawlerService = context.getBean("userInputCrawlerService", CrawlerService.class);
        metaKeywords = crawlerService.getMetaKeywords(userInputUrl);
        contentKeywords = crawlerService.getContentKeywords(userInputUrl);

        // 3. merge all map
        resultMap = new HashMap<>();
        resultMap.putAll(urlKeywords);
        for (String key : metaKeywords.keySet()) {
            resultMap.put(key, resultMap.getOrDefault(key, 0) + 1);
        }

        for (String key : contentKeywords.keySet()) {
            resultMap.put(key, resultMap.getOrDefault(key, 0) + 1);
        }

        // 4. get top 5 keywords
        PriorityQueue<Map.Entry<String, Integer>> queue = new PriorityQueue<>((a, b) -> (b.getValue() - a.getValue()));

        for (Map.Entry<String, Integer> entry : resultMap.entrySet()) {
            queue.offer(entry);
        }

        List<String> resultList = new ArrayList<>();
        int count = 5;
        System.out.println("The most important words in this web site are: ");
        while (count > 0 && !queue.isEmpty()) {
            Map.Entry<String, Integer> entry = queue.poll();
            resultList.add(entry.getKey());
            System.out.println("Word: " + entry.getKey() + ", " + "Importance: " + entry.getValue());
            count--;
        }

        context.close();
    }
}
