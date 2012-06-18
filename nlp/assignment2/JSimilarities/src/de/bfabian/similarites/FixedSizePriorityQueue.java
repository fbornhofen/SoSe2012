package de.bfabian.similarites;

import java.util.Comparator;
import java.util.TreeSet;

/*
 * We need something like this in java.util
 * http://stackoverflow.com/questions/7878026/is-there-a-priorityqueue-implementation-with-fixed-capacity-and-custom-comparato
 */

public class FixedSizePriorityQueue<E> extends TreeSet<E> {

	private int elementsLeft;

	private static class NaturalComparator<T extends Comparable<? super T>> implements Comparator<T> {
	    @Override
	    public int compare(T o1, T o2) {
	        return o1.compareTo(o2);
	    }
	}
	
	public FixedSizePriorityQueue(int maxSize) {
		super(new NaturalComparator());
		this.elementsLeft = maxSize;
	}

	public FixedSizePriorityQueue(int maxSize, Comparator<E> comparator) {
		super(comparator);
		this.elementsLeft = maxSize;
	}

	/**
	 * @return true if element was added, false otherwise
	 * */
	@Override
	public boolean add(E e) {
		if (elementsLeft == 0 && size() == 0) {
			// max size was initiated to zero => just return false
			return false;
		} else if (elementsLeft > 0) {
			// queue isn't full => add element and descrement elementsLeft
			super.add(e);
			elementsLeft--;
			return true;
		} else {
			// there is already 1 or more elements => compare to the least
			int compared = super.comparator().compare(e, this.first());
			if (compared == 1) {
				// new element is larger than the least in queue => pull the
				// least and add new one to queue
				pollFirst();
				super.add(e);
				return true;
			} else {
				// new element is less than the least in queue => return false
				return false;
			}
		}
	}
}
