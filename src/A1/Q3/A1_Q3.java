package A1.Q3;

import javax.swing.*;
import java.io.FilenameFilter;
import java.util.*;

public class A1_Q3 {

	private static Hashtable<String, Hashtable<String, Integer>> map;
	// hashtable: names -> (hashtable: words -> frequency (int))


	public static ArrayList<String> Discussion_Board(String[] posts) {

		map = new Hashtable<String, Hashtable<String, Integer>>();

		for (String line : posts) { //go through each line, split strings by words (with spaces)
			StringTokenizer stringTokenizer = new StringTokenizer(line, " ");
			String name = stringTokenizer.nextToken();

			while (stringTokenizer.hasMoreTokens()) { //add each word to hashmap
				String word = stringTokenizer.nextToken();
				if (!map.containsKey(name)) {
					map.put(name, new Hashtable<String, Integer>());
					map.get(name).put(word, 1);
				} else {
					if (!map.get(name).containsKey(word)) {
						map.get(name).put(word, 1);
					} else {
						map.get(name).replace(word, map.get(name).get(word)+1);
					}
				}

			}
		}

		Enumeration<String> names = map.keys();
		int numNames = map.keySet().size();
		Hashtable<String, Integer> allWords = new Hashtable<String, Integer>();

		while (names.hasMoreElements()) { //make hashmap of all words and total num of times they show up
			String nm = names.nextElement();
			Hashtable<String, Integer> wordFreqs = map.get(nm);
			for (String w : wordFreqs.keySet()) {
				if(allWords.containsKey(w)) {
					allWords.put(w, allWords.get(w)+wordFreqs.get(w));
				} else {
					allWords.put(w, wordFreqs.get(w));
				}
			}

		}

		Enumeration<String> words = allWords.keys();
		ArrayList<String> commonWords = new ArrayList<String>();
		ArrayList<Integer> commonWordsFreq = new ArrayList<Integer>();

		while (words.hasMoreElements()) { //convert all words hashmap to two array list
			String w = words.nextElement();
			commonWords.add(w);
			commonWordsFreq.add(allWords.get(w));
		}

		ArrayList<String> filteredCommonWords = new ArrayList<String>();
		ArrayList<Integer> filteredCommonWordsFreq = new ArrayList<Integer>();

		for (int i = 0; i < commonWords.size(); i++) { //only keep words that have been said by all names
			String w = commonWords.get(i);
			boolean saidByAllNames = true;
			for (String nm : map.keySet()) {
				if (!map.get(nm).containsKey(w)) {
					saidByAllNames = false;
					break;
				}
			}
			if (saidByAllNames) {
				filteredCommonWords.add(w);
				filteredCommonWordsFreq.add(commonWordsFreq.get(i));
			}
		}
		//sort array of remaining words according to identical array holding frequency
		return (mergeSort (filteredCommonWords, filteredCommonWordsFreq)).get(0);
	}

	/**
	 * Merge Sort algorithm sorts by frequency, then alphabetical order if needed
	 * When rearranging arrays, need to rearrange both word array and its sister frequency array
	 * Source: COMP 250 Notes (Winter 2021)
	 * @param wordList List to sort
	 * @param wordFreqList Frequency of each word in previous list, to determine soring order
	 * @return Sorted word list based on frequency
	 */
	private static ArrayList<ArrayList> mergeSort(ArrayList<String> wordList, ArrayList<Integer> wordFreqList) {
		if (wordList.size() <= 1) {
			ArrayList<ArrayList> result = new ArrayList<ArrayList>();
			result.add(wordList);
			result.add(wordFreqList);
			return result;
		} else {
			int midIndex = (wordList.size()-1)/2;
			ArrayList<String> wordList1 = new ArrayList<String>(wordList.subList(0, midIndex+1));
			ArrayList<Integer> wordFreq1 = new ArrayList<Integer>(wordFreqList.subList(0, midIndex+1));

			ArrayList<String> wordList2 = new ArrayList<String>(wordList.subList(midIndex + 1, wordList.size()));
			ArrayList<Integer> wordFreq2 = new ArrayList<Integer>(wordFreqList.subList(midIndex + 1, wordFreqList.size()));

			ArrayList<ArrayList> res1 = mergeSort(wordList1, wordFreq1);
			ArrayList<ArrayList> res2 = mergeSort(wordList2, wordFreq2);
			wordList1 = res1.get(0);
			wordFreq1 = res1.get(1);
			wordList2 = res2.get(0);
			wordFreq2 = res2.get(1);

			return merge(wordList1, wordFreq1, wordList2, wordFreq2);
		}
	}

	/**
	 * Merges two lists by re-placing the elements in order according to their frequency, then alphabetical order
	 * When rearranging arrays, need to do both word array and sister frequency array
	 * Source: COMP 250 Notes (Winter 250)
	 * @param wordList1 First list to merge
	 * @param wordFreq1 Frequency of each word in first list, to determine sorting order
	 * @param wordList2 Second list to merge
	 * @param wordFreq2 Frequency of each word in second list, to determine sorting order
	 * @return Sorted Array list based on frequency
	 */
	private static ArrayList<ArrayList> merge(ArrayList<String> wordList1, ArrayList<Integer> wordFreq1, ArrayList<String> wordList2, ArrayList<Integer> wordFreq2) {
		ArrayList<String> newList = new ArrayList<String>();
		ArrayList<Integer> newFreq = new ArrayList<Integer>();
		while (!wordList1.isEmpty() && !wordList2.isEmpty()) {
			if (wordFreq1.get(0) > wordFreq2.get(0)) {
				newList.add(wordList1.remove(0));
				newFreq.add(wordFreq1.remove(0));
			} else if (wordFreq1.get(0) < wordFreq2.get(0)) {
				newList.add(wordList2.remove(0));
				newFreq.add(wordFreq2.remove(0));
			} else {
				if (wordList1.get(0).compareToIgnoreCase(wordList2.get(0)) < 0) {
					newList.add(wordList1.remove(0));
					newFreq.add(wordFreq1.remove(0));
				} else {
					newList.add(wordList2.remove(0));
					newFreq.add(wordFreq2.remove(0));
				}

			}
		}

		while (!wordList1.isEmpty()) {
			newList.add(wordList1.remove(0));
			newFreq.add(wordFreq1.remove(0));
		}

		while (!wordList2.isEmpty()) {
			newList.add(wordList2.remove(0));
			newFreq.add(wordFreq2.remove(0));
		}
		ArrayList<ArrayList> bothLists = new ArrayList<ArrayList>();
		bothLists.add(newList);
		bothLists.add(newFreq);
		return bothLists;
	}


}
