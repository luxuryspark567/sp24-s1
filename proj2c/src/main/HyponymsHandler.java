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
        int endYear = query.endYear() == 0 ? end : query.endYear();
        int k = query.k();
        Set<String> result;

        if (query.ngordnetQueryType() == NgordnetQueryType.HYPONYMS) {
            result = wordNet.commonHyponym(words);
        } else if (query.ngordnetQueryType() == NgordnetQueryType.ANCESTORS) {
            result = wordNet.commonAncestor(words);
        } else {
            result = new HashSet<>();
        }
        return finalResult(result, startYear, endYear, k);
    }

    private String finalResult(Set<String> result, int startYear, int endYear, int k) {
        if (k > 0) {
            return topKWords(result, startYear, endYear, k).toString();
        } else {
            List<String> resultList = new ArrayList<>(result);
            Collections.sort(resultList);
            return resultList.toString();
        }
    }

    private List<String> topKWords(Set<String> words, int startYear, int endYear, int k) {
        Map<String, Double> wCount = new TreeMap<>();
        for (String word : words) {
            double total = totalWords(word, startYear, endYear);
            if (total > 0) {
                wCount.put(word, total);
            }
        }

        List<Map.Entry<String, Double>> sorted = new ArrayList<>(wCount.entrySet());
        Collections.sort(sorted, new Comparator<Map.Entry<String, Double>>() {

            public int compare(Map.Entry<String, Double> a, Map.Entry<String, Double> b) {
                int countCompare = b.getValue().compareTo(a.getValue());
                if (countCompare != 0) {
                    return countCompare;
                } else {
                    return a.getKey().compareTo(b.getKey());
                }
            }
        });

        List<String> topWords = new ArrayList<>();
        int i = 0;
        for (Map.Entry<String, Double> entry : sorted) {
            if (i < k) {
                topWords.add(entry.getKey());
                i++;
            } else {
                break;
            }
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
