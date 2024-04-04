import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import browser.NgordnetQueryType;
import main.AutograderBuddy;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.google.common.truth.Truth.assertThat;

public class TestCommonAncestors {
    public static final String WORDS_FILE = "data/ngrams/very_short.csv";
    public static final String LARGE_WORDS_FILE = "data/ngrams/top_14377_words.csv";
    public static final String TOTAL_COUNTS_FILE = "data/ngrams/total_counts.csv";
    public static final String SMALL_SYNSET_FILE = "data/wordnet/synsets16.txt";
    public static final String SMALL_HYPONYM_FILE = "data/wordnet/hyponyms16.txt";
    public static final String LARGE_SYNSET_FILE = "data/wordnet/synsets.txt";
    public static final String LARGE_HYPONYM_FILE = "data/wordnet/hyponyms.txt";

    /** This is an example from the spec for a common-ancestors query on the word "adjustment".
     * You should add more tests for the other spec examples! */
    @Test
    public void testSpecAdjustment() {
        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymsHandler(
                WORDS_FILE, TOTAL_COUNTS_FILE, SMALL_SYNSET_FILE, SMALL_HYPONYM_FILE);
        List<String> words = List.of("adjustment");

        NgordnetQuery nq = new NgordnetQuery(words, 2000, 2020, 0, NgordnetQueryType.ANCESTORS);
        String actual = studentHandler.handle(nq);
        String expected = "[adjustment, alteration, event, happening, modification, natural_event, occurrence, occurrent]";
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testEmpty() {
        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymsHandler(
                WORDS_FILE, TOTAL_COUNTS_FILE, SMALL_SYNSET_FILE, SMALL_HYPONYM_FILE);
        List<String> words = List.of();

        NgordnetQuery nq = new NgordnetQuery(words, 2000, 2020, 0, NgordnetQueryType.ANCESTORS);
        String actual = studentHandler.handle(nq);
        String expected = "[]";
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testWrong() {
        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymsHandler(
                WORDS_FILE, TOTAL_COUNTS_FILE, SMALL_SYNSET_FILE, SMALL_HYPONYM_FILE);
        List<String> words = List.of("ILOVELA");

        NgordnetQuery nq = new NgordnetQuery(words, 2000, 2020, 0, NgordnetQueryType.ANCESTORS);
        String actual = studentHandler.handle(nq);
        String expected = "[]";
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testEntity() {
        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymsHandler(
                LARGE_WORDS_FILE, TOTAL_COUNTS_FILE, LARGE_SYNSET_FILE, LARGE_HYPONYM_FILE);
        List<String> words = List.of("entity");

        NgordnetQuery nq = new NgordnetQuery(words, 1470, 2019, 8, NgordnetQueryType.HYPONYMS);
        String actual = studentHandler.handle(nq);
        String expected = "[are, at, can, have, he, in, one, will]";
        assertThat(actual).isEqualTo(expected);
    }
    

}
