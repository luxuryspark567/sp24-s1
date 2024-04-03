package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class WordNet {
    private final Map<String, Set<Integer>> wordID;
    private final Graph graph;
    private final Map<Integer, Set<String>> synsetID;

    public WordNet(String synsetFile, String hyponymFile) {
        this.graph = new Graph();
        this.synsetID = new HashMap<>();
        this.wordID = new HashMap<>();
        loadSynset(synsetFile);
        loadHyponym(hyponymFile);
    }

    private void loadSynset(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] token = line.split(",");
                Integer sID = Integer.parseInt(token[0]);
                graph.addNode(sID);

                String[] words = token[1].split(" ");
                Set<String> wordSet = new HashSet<>();
                for (String word : words) {
                    wordSet.add(word);
                    wordID.computeIfAbsent(word, k -> new HashSet<>()).add(sID);
                }
                synsetID.put(sID, wordSet);
            }
        } catch (IOException X) {
            X.printStackTrace(); //used https://www.educative.io/answers/what-is-the-printstacktrace-method-in-java//
        }
    }

    private void loadHyponym(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] token = line.split(",");
                Integer hypernymID = Integer.parseInt(token[0]);
                for (int i = 1; i < token.length; i++) {
                    Integer hyponymID = Integer.parseInt(token[i]);
                    graph.addEdge(hypernymID, hyponymID);
                }
            }
        } catch (IOException X) {
            X.printStackTrace();
        }
    }


    public Set<String> commonHyponym(List<String> words) {
        Set<String> commonHyponym = new TreeSet<>();
        boolean firstWord = true;
        for (String word : words) {
            Set<String> currentHyponyms = new HashSet<>();
            if (wordID.containsKey(word)) {
                for (Integer id : wordID.get(word)) {
                    Set<Integer> reachIDs = graph.getNodes(id);
                    for (Integer reachID : reachIDs) {
                        currentHyponyms.addAll(synsetID.get(reachID));
                    }
                }
            }
            if (firstWord) {
                commonHyponym.addAll(currentHyponyms);
                firstWord = false;
            } else {
                commonHyponym.retainAll(currentHyponyms);
            }
        }
        return commonHyponym;
    }

    public Set<String> commonAncestor(List<String> words) {
        Set<Integer> commonAncestor = new TreeSet<>();
        boolean firstWord = true;
        for (String word : words) {
            Set<Integer> currentAncestor = new HashSet<>();
            if (wordID.containsKey(word)) {
                for (Integer id : wordID.get(word)) {
                    currentAncestor.addAll(graph.getNodesReverse(id));
                }
            }
            if (firstWord) {
                commonAncestor.addAll(currentAncestor);
                firstWord = false;
            } else {
                commonAncestor.retainAll(currentAncestor);
            }
        }
        return idToWord(commonAncestor);
    }

    private Set<String> idToWord(Set<Integer> ids) {
        Set<String> words = new TreeSet<>();
        for (Integer id : ids) {
            if (synsetID.containsKey(id)) {
                words.addAll(synsetID.get(id));
            }
        }
        return words;
    }
}
