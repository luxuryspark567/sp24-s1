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
    private final int start = 1900;
    private final int end = 2020;

    public HyponymsHandler(WordNet wordNet, NGramMap mapN) {
        this.wordNet = wordNet;
        this.mapN = mapN;
    }

    @Override
    public String handle(NgordnetQuery query) {
        List<String> words = query.words();
        int startYear = query.startYear() == 0 ? start : query.startYear();
        int endYear = query.startYear() == 0 ? end : query.endYear();
        int k = query.k();
        Set<String> result = new HashSet<>();

        if (query.ngordnetQueryType() == NgordnetQueryType.HYPONYMS) {
            result = wordNet.commonHyponym(words);
        } else if (query.ngordnetQueryType() == NgordnetQueryType.ANCESTORS) {
            result = wordNet.commonAncestor(words);
        }
        if (k > 0) {
            List<String> newTopKWords = topKWords(result, startYear, endYear, k);
            return newTopKWords.toString();
        }
        return new ArrayList<>(result).toString();
    }

    private List<String> topKWords(Set<String> words, int startYear, int endYear, int k) {
        Map<String, Integer> wCount = new HashMap<>();
        for (String word : words) {
            int count = (int) totalWords(word, startYear, endYear);
            if (count > 0) {
                wCount.put(word, count);
            }
        }

        List<Map.Entry<String, Integer>> sorted = new ArrayList<>(wCount.entrySet());
        Collections.sort(sorted, new Comparator<>() {
            public int compare(Map.Entry<String, Integer> a, Map.Entry<String, Integer> b) {
                int countCompare = b.getValue().compareTo(a.getValue());
                if (countCompare != 0) {
                    return countCompare;
                } else {
                    return a.getKey().compareTo(b.getKey());
                }
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
        for (Double count : history.values()) {
            total += count;
        }
        return total;
    }
}
