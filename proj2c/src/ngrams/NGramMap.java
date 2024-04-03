package ngrams;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * An object that provides utility methods for making queries on the
 * Google NGrams dataset (or a subset thereof).
 * <p>
 * An NGramMap stores pertinent data from a "words file" and a "counts
 * file". It is not a map in the strict sense, but it does provide additional
 * functionality.
 *
 * @author Josh Hug
 */
public class NGramMap {
    private final HashMap<String, TimeSeries> historyT = new HashMap<>();
    private final TimeSeries tCounts = new TimeSeries();

    /**
     * Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME.
     */
    public NGramMap(String wordsFilename, String countsFilename) {
        loadCountsFile(countsFilename);
        loadWordsFile(wordsFilename);
    }

    private void loadWordsFile(String filename) {
        try {
            Scanner scan = new Scanner(new File(filename));
            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                String[] parts = line.split("\t");
                String word = parts[0];
                int year = Integer.parseInt(parts[1]);
                double count = Double.parseDouble(parts[2]);
                TimeSeries ts = historyT.getOrDefault(word, new TimeSeries());
                ts.put(year, count);
                historyT.put(word, ts);
            }
            scan.close();
        } catch (FileNotFoundException e) {
            System.err.println(("File not found: " + filename));
        }
    }

    private void loadCountsFile(String filename) {
        try {
            Scanner scan = new Scanner(new File(filename));
            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                String[] parts = line.split(",");
                int year = Integer.parseInt(parts[0]);
                double count = Double.parseDouble(parts[1]);
                tCounts.put(year, count);
            }
            scan.close();
        } catch (FileNotFoundException e) {
            System.err.println(("File not found: " + filename));
        }
    }

    /**
     * Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     * returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other
     * words, changes made to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy". If the word is not in the data files,
     * returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word, int start, int end) {
        TimeSeries og = historyT.getOrDefault(word, new TimeSeries());
        return new TimeSeries(og, start, end);
    }

    /**
     * Provides the history of WORD. The returned TimeSeries should be a copy, not a link to this
     * NGramMap's TimeSeries. In other words, changes made to the object returned by this function
     * should not also affect the NGramMap. This is also known as a "defensive copy". If the word
     * is not in the data files, returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word) {
        return countHistory(word, TimeSeries.MIN_YEAR, TimeSeries.MAX_YEAR);
    }

    /**
     * Returns a defensive copy of the total number of words recorded per year in all volumes.
     */
    public TimeSeries totalCountHistory() {
        TimeSeries newT = new TimeSeries();
        newT.putAll(tCounts);
        return newT;
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     * and ENDYEAR, inclusive of both ends. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        TimeSeries history = countHistory(word, startYear, endYear);
        TimeSeries end = new TimeSeries();
        for (Integer year : history.keySet()) {
            if (tCounts.containsKey(year)) {
                double rFrequency = history.get(year) / tCounts.get(year);
                end.put(year, rFrequency);
            }
        }
        return end;
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD compared to all
     * words recorded in that year. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word) {
        return weightHistory(word, TimeSeries.MIN_YEAR, TimeSeries.MAX_YEAR);
    }

    /**
     * Provides the summed relative frequency per year of all words in WORDS between STARTYEAR and
     * ENDYEAR, inclusive of both ends. If a word does not exist in this time frame, ignore it
     * rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words,
                                          int startYear, int endYear) {
        TimeSeries sHistory = new TimeSeries();
        for (String word : words) {
            TimeSeries wHistory = weightHistory(word, startYear, endYear);
            for (Map.Entry<Integer, Double> entry : wHistory.entrySet()) {
                Integer year = entry.getKey();
                Double frequency = entry.getValue();
                sHistory.put(year, sHistory.getOrDefault(year, 0.0) + frequency);
            }
        }
        return sHistory;
    }

    /**
     * Returns the summed relative frequency per year of all words in WORDS. If a word does not
     * exist in this time frame, ignore it rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        return summedWeightHistory(words, TimeSeries.MIN_YEAR, TimeSeries.MAX_YEAR);
    }
}
