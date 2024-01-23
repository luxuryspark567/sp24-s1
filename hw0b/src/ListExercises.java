import java.util.ArrayList;
import java.util.List;

import static edu.princeton.cs.algs4.StdOut.print;

public class ListExercises {

    /** Returns the total sum in a list of integers */
	public static int sum(List<Integer> L) {
        int total = 0;
        for(int i = 0; i < L.size(); i++) {
            total += L.get(i);
        }
        return total;
    }


    /** Returns a list containing the even numbers of the given list */
    public static List<Integer> evens(List<Integer> L) {
        List<Integer> evennum = new ArrayList<>();
        for(int i = 0; i < L.size(); i++) {
            if (L.get(i) % 2 == 0) {
                evennum.add(L.get(i));
            }
        }
        return evennum;
    }

    /** Returns a list containing the common item of the two given lists */
    public static List<Integer> common(List<Integer> L1, List<Integer> L2) {
        List<Integer> common = new ArrayList();
        for(int number : L1){
            if(L2.contains(number)){
                common.add(number);
            }
        }
        return common;
    }


    /** Returns the number of occurrences of the given character in a list of strings. */
    public static int countOccurrencesOfC(List<String> words, char c) {
        int occur = 0; //occurances//
        for(String elem: words) { //for every elem in words
            for (int i = 0; i< elem.length(); i++) { //nested loop for i in length of elem
                if (elem.charAt(i) == c) { //if elem is the same as c then occur goes up
                    occur ++;
                }
            }
        }
        return occur;
    }

}
