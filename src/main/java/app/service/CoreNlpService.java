package app.service;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

import java.util.*;

public class CoreNlpService {


    public HashMap<String, Integer> parseSentence(String text) {
        HashMap<String, Integer> map = new HashMap<>();
        HashSet<String> keyPos = new HashSet<>();

        /**
         * exact important noun
         * NN	Noun, singular or mass
         * NNS	Noun, plural
         * NNP	Proper noun, singular
         * NNPS	Proper noun, plural
         */
        keyPos.add("NN");
        keyPos.add("NNS");
        keyPos.add("NNP");
        keyPos.add("NNPS");

        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, pos");
        props.setProperty("ner.useSUTime", "false");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

        Annotation document = new Annotation(text);
        pipeline.annotate(document);
        List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);

        // parse sentences and extract keywords
        for (CoreMap sentence : sentences) {
            for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                String word = token.get(CoreAnnotations.TextAnnotation.class);
                String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);
                if (keyPos.contains(pos)) {
                    word = word.toLowerCase();
//                    System.out.println(String.format("Print: word: [%s] pos: [%s]", word, pos));
                    map.put(word, map.getOrDefault(word, 0) + 1);
                }
            }
        }

        return map;
    }
}

