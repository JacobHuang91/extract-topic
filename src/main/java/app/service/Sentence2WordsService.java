package app.service;

import edu.stanford.nlp.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;

public class Sentence2WordsService {

    @Autowired
    private CoreNlpService coreNlpService;

    public Sentence2WordsService(CoreNlpService coreNlpService) {
        this.coreNlpService = coreNlpService;
    }


    /**
     * @param s the URL String
     * @return keywords map
     */
    public HashMap<String, Integer> parseSentenceUrl(String s) {
        HashMap<String, Integer> map = new HashMap<>();
        if (s == null || s.length() == 0) return map;

        String[] words = s.trim().split("\\W+");

        // reconstruct a sentence
        StringBuilder sb = new StringBuilder();
        for (String word : words) {
            sb.append(word);
            sb.append(" ");
        }

        String sentence = sb.toString().trim();

        return coreNlpService.parseSentence(sentence);
    }

    /**
     * @param s the URL String
     * @return keywords map
     */
    public HashMap<String, Integer> parseSentenceQuery(String s) {
        HashMap<String, Integer> map = new HashMap<>();
        if (s == null || s.length() == 0) return map;

        String[] pairs = s.trim().split("&");



        for (String pair : pairs) {
            // only get the value
            String word = pair.split("=")[1];
            word = word.trim().toLowerCase();
            if (word == null || word.length() == 0) continue;
            // filter numbers
            if (StringUtils.isNumeric(word)) continue;
            // filter too short words
            if (word.length() <= 2) continue;


            map.put(word, map.getOrDefault(word, 0) + 1);
        }

        return map;
    }

    /**
     * extract the keywords in the sentence
     * call the coreNLP function
     *
     * @param s the sentence
     * @return keywords map
     */
    public HashMap<String, Integer> parseSentenceCoreNlp(String s) {
        HashMap<String, Integer> keywords = coreNlpService.parseSentence(s);
        return keywords;
    }
}
