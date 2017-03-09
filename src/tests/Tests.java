package tests;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import hashTable.HashSet;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

public class Tests {

	private static final String testFile = "sample-dictionary";
	private static final Set<String> correctImplementation = new TreeSet<String>();	
	private static final HashSet<String> hashSet = new HashSet<String>();
	
	
	
	@Test
	public void testInsertedAllAndContains() {
		for (String s: correctImplementation) {
			boolean found = false;
			for (String s1 : hashSet)
				if (s1.equals(s))
					found = true;
			
			assertTrue("String is present", found);
			
			assertTrue("String contains returns true", hashSet.contains(s));
		}
	}
	
	@Test
	public void testSize() {
		assertTrue("Correct number of elements", correctImplementation.size() == hashSet.size());
	}
	
	@Test
	public void testRemove() {
		// Get an element
		String element = correctImplementation.iterator().next();
		
		assertTrue("Contains element to begin with", hashSet.contains(element));
		assertTrue("Remove successful", hashSet.remove(element));
		assertFalse("Element now gone", hashSet.contains(element));
		
	}
	
	@Test
	public void testClone() {
		HashSet<String> clone = hashSet.clone();
		
		for (String s: correctImplementation) {
			assertTrue("String exists in hashset", clone.contains(s));
		}
	}
	
	@Test
	public void testIsEmpty() {
		assertFalse("Existing hashset isn't empty", hashSet.isEmpty());
		
		assertTrue("New Hashset is empty", new HashSet<Integer>().isEmpty());
	}
	
	@Test
	public void testContainsAll() {
		assertTrue("Contains all elements of the other collectoin", hashSet.containsAll(correctImplementation));
		
	}
	
	@Test
	public void testAddAll() {
		// Shouldn't allow adding elements it already has
		assertFalse("No new elements", hashSet.addAll(correctImplementation));
		
		Collection<String> newCollection = new ArrayList<String>();
		// Nonsense characters to reduce chance of already being in a list
		newCollection.add("srriugsiughsdfiughsdfiuh");
		newCollection.add("3454gfrgrGgddgfg");
		newCollection.add("sjkillr");
		assertTrue("New elements added", hashSet.addAll(newCollection ));
	}
	
	@BeforeClass
	public static void readInFile() {
		BufferedReader br = null;
		FileReader fr = null;
		try {

			fr = new FileReader(testFile);
			br = new BufferedReader(fr);

			String currentLine;

			br = new BufferedReader(new FileReader(testFile));

			while ((currentLine = br.readLine()) != null) {
				hashSet.add(currentLine);
				correctImplementation.add(currentLine);
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
