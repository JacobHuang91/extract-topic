package app.service;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;

import javax.print.Doc;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

public class UserInputCrawlerService implements CrawlerService {
    private Sentence2WordsService sentence2WordsService;

    @Autowired
    public UserInputCrawlerService(Sentence2WordsService sentence2WordsService) {
        this.sentence2WordsService = sentence2WordsService;
    }

    /**
     * get meta data from website
     *
     * @param url the URL String
     * @return keywords map
     */
    @Override
    public HashMap<String, Integer> getMetaKeywords(String url) {
        System.out.println("==========> Start to collect meta data keywords");
        try {
            Document doc = Jsoup.connect(url)
                    .followRedirects(true)
                    .userAgent("Mozilla")
                    .cookie("auth", "token")
                    .get();

            HashMap<String, Integer> map = new HashMap<>();

            // get title
            String title = doc.title();
            System.out.println(">> Title: " + title);
            HashMap<String, Integer> titleMap = sentence2WordsService.parseSentenceCoreNlp(title);

            map.putAll(titleMap);

            // get meta data
            HashSet<String> keywordsNames = new HashSet<>();
            // three import keywords for main topic
            keywordsNames.add("title");
            keywordsNames.add("description");
            keywordsNames.add("keywords");

            Elements metaTags = doc.getElementsByTag("meta");
            for (Element metaTag : metaTags) {
                String content = metaTag.attr("content");
                String name = metaTag.attr("name");

                if (keywordsNames.contains(name)) {
                    System.out.println(">> name: " + name);
                    System.out.println(">> content: " + content);
                    HashMap<String, Integer> temp = sentence2WordsService.parseSentenceCoreNlp(content);
                    for (String key : temp.keySet()) {
                        if (map.containsKey(key)) {
                            map.put(key, map.get(key) + temp.get(key));
                        } else {
                            map.put(key, temp.get(key));
                        }
                    }
                }
            }

            return map;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public HashMap<String, Integer> getContentKeywords(String url) {
        System.out.println("==========> Start to collect content data keywords");
        try {
            Connection connection = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36")
                    .cookie("auth", "token");

            connection.header("Accept-Language", "en");

            Document doc = connection.get();
            HashMap<String, Integer> map = new HashMap<>();

            // parse important tags
            String[] collectTags = {"h1", "h2", "h3"};
            for (String collectTag : collectTags) {
                System.out.println(">> Collect " + collectTag + " content");
                Elements tags = doc.select(collectTag);
                if (tags == null) continue;
                for (Element tag : tags) {
                    String text = tag.text();
                    System.out.println(">> parse content: " + text);
                    HashMap<String, Integer> temp = sentence2WordsService.parseSentenceCoreNlp(text);

                    for (String key : temp.keySet()) {
                        if (map.containsKey(key)) {
                            map.put(key, map.get(key) + temp.get(key));
                        } else {
                            map.put(key, temp.get(key));
                        }
                    }
                }
            }

            return map;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
