package sort;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Map.Entry;

public class Voting {

	public static void main(String[] args) throws IOException {
		SortedMap<String, Integer> votes = new TreeMap<String, Integer>();
		
		FileReader fileReader = new FileReader(new File("EventVoting.csv"));
		BufferedReader bufferedReader = new BufferedReader(fileReader);

		int maxItemsPerVote = 10;

		while(true) {
			String line = bufferedReader.readLine();
			if(line==null) break;
			String[] names = line.split(",");
			for(int i=0;i<names.length;i++) {
				String key = names[i];
				int voteWeight = maxItemsPerVote-i;
				if(votes.get(key)!=null) {
					int currentVote = votes.get(key);
					votes.remove(key);
					votes.put(key, currentVote+(voteWeight));
				} else {
					votes.put(key, voteWeight);
				}
			}
		}	

		bufferedReader.close();

		List<Entry<String, Integer>> sortedVotes = sortByValues(votes);

		for (Entry<String, Integer> entry : sortedVotes) {
			System.out.println(entry.getKey()+": "+entry.getValue());
		}

	}

	public static List<Entry<String, Integer>> sortByValues(final Map<String, Integer> map) {
		Comparator<Entry<String, Integer>> comparator =
				(Entry<String, Integer> entry1, Entry<String, Integer> entry2) -> {return entry2.getValue()-entry1.getValue();}; 
		
		List<Entry<String, Integer>> list = new ArrayList<Entry<String, Integer>>();
		list.addAll(map.entrySet());
		Collections.sort(list, comparator);
		return list;
	}

}