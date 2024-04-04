package main;

import browser.NgordnetQueryHandler;
import ngrams.NGramMap;


public class AutograderBuddy {
    /**
     * Returns a HyponymHandler
     */
    public static NgordnetQueryHandler getHyponymsHandler(
            String wordFile, String countFile,
            String synsetFile, String hyponymFile) {

        WordNet wordnet = new WordNet(synsetFile, hyponymFile);
        NGramMap nGram = new NGramMap(wordFile, countFile);
        return new HyponymsHandler(wordnet, nGram);
    }
}
