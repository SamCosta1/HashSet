package hashTable;

public class Element<T extends Comparable<T>> implements Comparable<T>{
	
	private T element;
	private State state = State.EMPTY;
	
	public Element(T o) {
		setElement((T)o);
		state = State.IN_USE;
	}

	@Override
	public int compareTo(T other) {
		return element.compareTo(other);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object other) {
		if (other instanceof Comparable<?>)
			return this.compareTo((T)other) == 0;
		
		return false;
	}
	
	public boolean isEmpty() {
		return state == State.EMPTY;
	}
	
	public boolean isInUse() {
		return state == State.IN_USE;
	}

	public T getElement() {
		return element;
	}

	public void setElement(T element) {
		this.element = element;
	}

	public void remove() {
		state = State.DELETED;		
	}
}
