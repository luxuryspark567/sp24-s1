package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;

import java.util.List;
import java.util.Set;

public class HyponymsHandler extends NgordnetQueryHandler {
    private final WordNet wordNet;

    public HyponymsHandler(WordNet wordNet) {
        this.wordNet = wordNet;
    }

    @Override
    public String handle(NgordnetQuery query) {
        List<String> words = query.words();
        Set<String> hyponymsOfWord = this.wordNet.commonHyponym(words);
        return hyponymsOfWord.toString();
    }
}
