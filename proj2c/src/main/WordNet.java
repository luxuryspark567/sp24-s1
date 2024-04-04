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
        if (words.isEmpty()) {
            return Collections.emptySet();
        }

        List<Set<String>> allAncestorsWords = new ArrayList<>();
        for (String word : words) {
            Set<String> wordAncestors = getAncestorWords(word);
            if (!wordAncestors.isEmpty()) {
                allAncestorsWords.add(wordAncestors);
            }
        }
        if (allAncestorsWords.isEmpty()) {
            return Collections.emptySet();
        }
        Set<String> commonAncestors = intersect(allAncestorsWords);

        return commonAncestors;
    }

    private Set<String> getAncestorWords(String word) {
        Set<Integer> ancestorIDs = new HashSet<>();
        Set<String> ancestorWords = new HashSet<>();
        Set<Integer> wordSynsetIDs = wordID.getOrDefault(word, Collections.emptySet());
        for (Integer synsetId : wordSynsetIDs) {
            ancestorIDs.addAll(graph.getNodesReverse(synsetId));
        }

        for (Integer ancestorId : ancestorIDs) {
            ancestorWords.addAll(synsetID.getOrDefault(ancestorId, Collections.emptySet()));
        }

        return ancestorWords;
    }

    private Set<String> intersect(List<Set<String>> ancestorWordsList) {
        Set<String> commonAncestors = new HashSet<>(ancestorWordsList.get(0));
        for (Set<String> ancestors : ancestorWordsList) {
            commonAncestors.retainAll(ancestors);
        }
        return commonAncestors;
    }
}
