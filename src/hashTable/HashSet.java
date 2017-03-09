package hashTable;

import java.io.Serializable;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class HashSet<T extends Comparable<T>> extends AbstractSet<T> 
											  implements Collection<T>, Iterable<T>, Set<T>, Serializable {
	
	
	private static final long serialVersionUID = 1L;
	private static final int defaultSize = 97;
	private int totalCollisions = 0;
	
	private Element<T> elements[];
	private int noElements = 0;

	@SuppressWarnings("unchecked")
	public HashSet() {
		elements = (Element<T>[])new Element[defaultSize];
	}
	
	@SuppressWarnings("unchecked")
	public HashSet(int initialSize) {
		elements = (Element<T>[])new Element[initialSize];
	}
	
	@SuppressWarnings("unchecked")
	public HashSet(Collection<? extends T> other) {
		elements = (Element<T>[])new Element[other.size() * 2 + 1];			
		
		for (T element : other) {
			add(element);
		}
	}

	@Override
	public Iterator<T> iterator() {
		return new Iterator<T>() {		
			private int nextIndex = -1;
			@Override						
			public boolean hasNext() {				
				if (nextIndex == -1)
					updateNext();
				
				return nextIndex != elements.length;
			}

			private void updateNext() {
				do {
					nextIndex++;
					
				} while	(nextIndex < elements.length &&					
									(  elements[nextIndex] == null
									   || !elements[nextIndex].isInUse() 
									));					
						
				
			}

			@Override
			public T next() {	
				if (nextIndex == -1)
					updateNext();
				
				T toReturn = elements[nextIndex].getElement();				
				updateNext();
				
				return toReturn;			
			
			}
			
		};
	}
	
	private boolean insert(int index, T element) {
		if (element == null)
			return false;

		if (elements[index] == null) {
			elements[index] = new Element<T>(element);
			return true;
		}
		
		if (elements[index].isInUse()) {
			if (elements[index].equals(element))
				return false;
			
			totalCollisions++;
			
			return doubleHash(index, element);
		}		
		
		elements[index].setElement(element);
		return true;
			
	}

	private boolean doubleHash(int index, T element) {		
		
		int loopCount = 1;
		boolean spaceFound = false;
		
		while (!spaceFound) {
			int newHash = getDoubleHashVal(index, loopCount);
			if (elements[newHash] == null) {
				elements[newHash] = new Element<T>(element);
				return true;
			}
			
			if (elements[newHash].equals(element))
				return false;
			
			if (!elements[index].isInUse()) {
				elements[index].setElement(element);
				return true;
			}
			
			loopCount++;			
				
		}
		
		// Should never happen
		return false;
	}

	private int getDoubleHashVal(int hash, int loopCount) {
		loopCount = loopCount % elements.length;		
		return Math.abs((hash + loopCount * secondaryHash(hash) % elements.length) % elements.length);
		
	}

	private int secondaryHash(int hash) {
		return (7919 - (hash % 7919)) % elements.length;
	}

	@Override
	public boolean addAll(Collection<? extends T> c) {
		boolean changed = false;
		for (T obj : c) {
			if (this.add(obj))
				changed = true;
		}
		
		return changed;
	}
	@Override
	public boolean add(T e) {	
		
		if (noElements >= elements.length - 1)
			extendElementsArray();
		boolean inserted = insert(hashCode(e), e);
		
		if (inserted)
			noElements++;
		
		return inserted;
		
	}
	
	private int hashCode(Object o) {
		return Math.abs(o.hashCode()) % elements.length;
	}

	@SuppressWarnings("unchecked")
	private void extendElementsArray() {
		Element<T> oldArray[] = elements;
		elements = new Element[elements.length * 2 + 1];
		
		
		totalCollisions = 0;
		for (int i = 0; i < oldArray.length; i++) {
			if (oldArray[i] != null && oldArray[i].isInUse())
				insert(hashCode(oldArray[i].getElement()), oldArray[i].getElement());
		}
		
		
	}  

	@Override
	public int size() {
		return noElements;
	}
	
	@Override
	public boolean remove(Object o) {
		int hash = hashCode(o);
		
		if (elements[hash] == null)
			return false;
		
			
		if (elements[hash].equals(o)) {
			elements[hash].remove();
			noElements--;
			return true;			
		}
		
		boolean stop = false;	
		int loopCount = 1;
		while (!stop) {
			int newHash = getDoubleHashVal(hash, loopCount);
			
			if (elements[newHash] == null || elements[newHash].isEmpty())
				return false;
			
			if (elements[newHash].equals(o)) {
				elements[newHash].remove();
				noElements--;
				return true;			
			}
			loopCount++;
		}
		
		return false;
			
	}
	
	@Override
	public boolean isEmpty() {
		return noElements == 0;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void clear() {
		elements = (Element<T>[])new Element[defaultSize];
	}
	
	@Override
	public boolean containsAll(Collection<?> collection) {
		for (Object obj : collection) 
			if (!this.contains(obj))
				return false;
		
		
		return true;
	}
	
	@Override
	public boolean contains(Object o) {
		int hash = hashCode(o);
		if (elements[hash] == null)
			return false;
		
			
		if (elements[hash].equals(o)) {
			return true;			
		}
		
		boolean stop = false;	
		int loopCount = 1;
		while (!stop) {
			int newHash = getDoubleHashVal(hash, loopCount);
			
			if (elements[newHash] == null || elements[newHash].isEmpty())
				return false;
			
			if (elements[newHash].equals(o)) {				
				return true;			
			}
			loopCount++;
		}
		
		return false;
		
	}
	
	public HashSet<T> clone() {
		return new HashSet<T>(this);
	}
	
	public void printStats() {
		if (noElements == 0)
			return;
		
		System.out.println("Avrg Collisions: " + (double)totalCollisions / noElements);
		System.out.println("Total Collisions: " + totalCollisions);
		System.out.println("ArraySize: " + elements.length);
		System.out.println("Number of Items: " + this.size());
	}
}
