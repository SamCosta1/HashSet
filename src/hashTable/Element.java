package hashTable;

public class Element<T extends Comparable<T>> implements Comparable<T>{
	
	private T element;
	private State state = State.EMPTY;
	
	public Element(T o) {
		setElement(o);
	}

	@Override
	public int compareTo(T other) {		
		int cmpTo = element.compareTo(other);
		return state == State.IN_USE ? cmpTo : -1;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object other) {
		if (state != State.IN_USE)
			return false;
		
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
		state = State.IN_USE;
		this.element = element;
	}

	public void remove() {
		state = State.DELETED;		
	}
}
