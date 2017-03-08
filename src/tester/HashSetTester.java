package tester;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

import hashTable.HashSet;

public class HashSetTester {
	

	public static void main(String[] args) {
		HashSet<String> hashSet = new HashSet<String>();
		readIn(hashSet, "words");

		for (String s : hashSet) 
			System.out.println(s);
		
		hashSet.printStats();
		
	}
	
	private static void readIn(HashSet<String> hashSet, String FILENAME) {
		BufferedReader br = null;
		FileReader fr = null;
		try {

			fr = new FileReader(FILENAME);
			br = new BufferedReader(fr);

			String sCurrentLine;

			br = new BufferedReader(new FileReader(FILENAME));

			while ((sCurrentLine = br.readLine()) != null) {
				hashSet.add(sCurrentLine);
			}

		} catch (IOException e) {

			e.printStackTrace();

		} finally {
			try {
				if (br != null)
					br.close();

				if (fr != null)
					fr.close();

			} catch (IOException ex) {

				ex.printStackTrace();

			}

		}
	}
}
