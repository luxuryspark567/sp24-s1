import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import browser.NgordnetQueryType;
import main.AutograderBuddy;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.google.common.truth.Truth.assertThat;

public class TestOneWordKNot0Hyponyms {
    public static final String WORDS_FILE = "data/ngrams/frequency-EECS.csv";
    public static final String TOTAL_COUNTS_FILE = "data/ngrams/total_counts.csv";
    public static final String SMALL_SYNSET_FILE = "data/wordnet/synsets-EECS.txt";
    public static final String SMALL_HYPONYM_FILE = "data/wordnet/hyponyms-EECS.txt";

    @Test
    public void testActKNot0() {
        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymsHandler(
                WORDS_FILE, TOTAL_COUNTS_FILE, SMALL_SYNSET_FILE, SMALL_HYPONYM_FILE);
        List<String> words = List.of("CS61A");

        NgordnetQuery nq = new NgordnetQuery(words, 2010, 2020, 4, NgordnetQueryType.HYPONYMS);
        String actual = studentHandler.handle(nq);
        String expected = "[CS170, CS61A, CS61B, CS61C]";
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testEmpty() {
        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymsHandler(
                WORDS_FILE, TOTAL_COUNTS_FILE, SMALL_SYNSET_FILE, SMALL_HYPONYM_FILE);
        List<String> words = List.of();

        NgordnetQuery nq = new NgordnetQuery(words, 2010, 2020, 4, NgordnetQueryType.HYPONYMS);
        String actual = studentHandler.handle(nq);
        String expected = "[]";
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testWrong() {
        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymsHandler(
                WORDS_FILE, TOTAL_COUNTS_FILE, SMALL_SYNSET_FILE, SMALL_HYPONYM_FILE);
        List<String> words = List.of("peanutbutterjellytime");

        NgordnetQuery nq = new NgordnetQuery(words, 2010, 2020, 4, NgordnetQueryType.HYPONYMS);
        String actual = studentHandler.handle(nq);
        String expected = "[]";
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testDouble() {
        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymsHandler(
                WORDS_FILE, TOTAL_COUNTS_FILE, SMALL_SYNSET_FILE, SMALL_HYPONYM_FILE);
        List<String> words = List.of("CS61A", "CS170");

        NgordnetQuery nq = new NgordnetQuery(words, 2010, 2020, 2, NgordnetQueryType.HYPONYMS);
        String actual = studentHandler.handle(nq);
        String expected = "[CS61A, CS61B]";
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testNegativeK() {
        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymsHandler(
                WORDS_FILE, TOTAL_COUNTS_FILE, SMALL_SYNSET_FILE, SMALL_HYPONYM_FILE);
        List<String> words = List.of("CS170");

        NgordnetQuery nq = new NgordnetQuery(words, 2000, 2020, -2, NgordnetQueryType.HYPONYMS);
        String actual = studentHandler.handle(nq);
        String expected = "[CS170, CS172, CS174, CS176, CS191]";
        assertThat(actual).isEqualTo(expected);
    }

}
