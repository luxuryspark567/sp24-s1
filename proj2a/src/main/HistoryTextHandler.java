package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;

import java.util.List;

public class HistoryTextHandler extends NgordnetQueryHandler {
    private final NGramMap nGramMap;

    public HistoryTextHandler(NGramMap map) {
        this.nGramMap = map;
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();
        String endResult = "";
        for (String word : words) {
            endResult = endResult + word + ": " + nGramMap.weightHistory(word, startYear, endYear).toString() + "\n";
        }
        return endResult;
    }
}
