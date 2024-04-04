package main;

import browser.NgordnetServer;
import ngrams.NGramMap;
import org.slf4j.LoggerFactory;

public class Main {
    static {
        LoggerFactory.getLogger(Main.class).info("\033[1;38mChanging text color to white");
    }

    public static void main(String[] args) {
        NgordnetServer hns = new NgordnetServer();
        String wordFile = "./data/ngrams/top_14377_words.csv";
        String countFile = "./data/ngrams/total_counts.csv";
        String synsetFile = "./data/wordnet/synsets16.txt";
        String hyponymFile = "./data/wordnet/hyponyms16.txt";
        NGramMap ngm = new NGramMap(wordFile, countFile);
        WordNet wordNet = new WordNet(synsetFile, hyponymFile);

        hns.startUp();
        hns.register("history", new DummyHistoryHandler());
        hns.register("historytext", new DummyHistoryTextHandler());

        hns.register("hyponyms", new HyponymsHandler(wordNet, ngm));


        System.out.println("Finished server startup! Visit http://localhost:4567/ngordnet.html");
    }
}
