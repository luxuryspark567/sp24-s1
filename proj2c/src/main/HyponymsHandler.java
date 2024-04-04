package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import browser.NgordnetQueryType;
import ngrams.NGramMap;
import ngrams.TimeSeries;

import java.util.*;

public class HyponymsHandler extends NgordnetQueryHandler {
    private final WordNet wordNet;
    private final NGramMap mapN;
    private final int startYEAR = 1900;
    private final int endYEAR = 2020;

    public HyponymsHandler(WordNet wordNet, NGramMap mapN) {
        this.wordNet = wordNet;
        this.mapN = mapN;
    }

    @Override
    public String handle(NgordnetQuery query) {
        List<String> words = query.words();
        int startYear = query.startYear() == 0 ? startYEAR : query.startYear();
        int endYear = query.startYear() == 0 ? endYEAR : query.endYear();
        int k = query.k();
        Set<String> result;

        if (query.ngordnetQueryType() == NgordnetQueryType.HYPONYMS) {
            result = wordNet.commonHyponym(words);
        } else {
            result = wordNet.commonAncestor(words);
        }
        if (k > 0) {
            return getAlphabetical(result, startYear, endYear, k).toString();
        } else {
            List<String> resultList = new ArrayList<>(result);
            Collections.sort(resultList);
            return resultList.toString();
        }
    }

    private List<String> getAlphabetical(Set<String> words, int startYear, int endYear, int k) {
        List<Map.Entry<String, Double>> sorted = new ArrayList<>();
        for (String word : words) {
            double count = totalWords(word, startYear, endYear);
            if (count > 0) {
                sorted.add(new AbstractMap.SimpleEntry<>(word, count));
            }
        }
        Collections.sort(sorted, new Comparator<>() {
            @Override
            public int compare(Map.Entry<String, Double> a, Map.Entry<String, Double> b) {
                int finalR = b.getValue().compareTo(a.getValue());
                if (finalR == 0) {
                    return a.getKey().compareTo(b.getKey());
                }
                return finalR;
            }
        });
        int resultSize = Math.min(k, sorted.size());
        List<String> topWords = new ArrayList<>();
        for (int i = 0; i < resultSize; i++) {
            topWords.add(sorted.get(i).getKey());
        }

        Collections.sort(topWords);
        return topWords;
    }

    private double totalWords(String word, int startYear, int endYear) {
        TimeSeries history = mapN.countHistory(word, startYear, endYear);
        double total = 0;
        for (Map.Entry<Integer, Double> entry : history.entrySet()) {
            total += entry.getValue();
        }
        return total;
    }
}
