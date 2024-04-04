package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class WordNet {
    private final Map<String, Set<Integer>> wordID = new HashMap<>();
    private final Graph graph = new Graph();
    private final Map<Integer, Set<String>> idWord = new HashMap<>();

    public WordNet(String synsetFile, String hyponymFile) {
        loadSynset(synsetFile);
        loadHyponym(hyponymFile);
    }

    private void loadSynset(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] token = line.split(",");
                int sID = Integer.parseInt(token[0]);
                String[] words = token[1].split(" ");
                Set<String> set = new HashSet<>(Arrays.asList(words));
                idWord.put(sID, set);
                for (String word : words) {
                   wordID.computeIfAbsent(word, k -> new HashSet<>()).add(sID);
                }
                graph.addNode(sID);
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
                int hypernymID = Integer.parseInt(token[0]);
                for (int i = 1; i < token.length; i++) {
                    int hyponymID = Integer.parseInt(token[i]);
                    graph.addEdge(hypernymID, hyponymID);
                }
            }
        } catch (IOException X) {
            X.printStackTrace();
        }
    }


    public Set<String> commonHyponym(List<String> words) {
        Set<String> hyponyms = new HashSet<>();
        for (String word : words) {
            Set<Integer> ids = wordID.getOrDefault(word, Collections.emptySet());
            for (int id : ids) {
                Set<Integer> reachableIds = graph.getNodes(id);
                for (int reachableId : reachableIds) {
                    hyponyms.addAll(idWord.getOrDefault(reachableId, Collections.emptySet()));
                }
            }
        }
        return hyponyms;
    }

    public Set<String> commonAncestor(List<String> words) {
        Set<Integer> ancestorIds = new HashSet<>();
        boolean firstWord = true;

        for (String word : words) {
            Set<Integer> ids = wordID.getOrDefault(word, Collections.emptySet());
            Set<Integer> currentAncestors = new HashSet<>();
            for (Integer id : ids) {
                currentAncestors.addAll(graph.getNodesReverse(id));
            }
            if (firstWord) {
                ancestorIds.addAll(currentAncestors);
                firstWord = false;
            } else {
                ancestorIds.retainAll(currentAncestors);
            }
        }
        return idToWord(ancestorIds);
    }
    private Set<String> idToWord(Set<Integer> ids) {
        Set<String> words = new TreeSet<>();
        for (Integer id : ids) {
            Set<String> set = idWord.get(id);
            if (set != null) {
                words.addAll(set);
            }
        }
        return words;
    }
}

