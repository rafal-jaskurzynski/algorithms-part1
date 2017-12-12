import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {

	private Item[] s;
	private int N = 0;

	// construct an empty randomized queue
	public RandomizedQueue() {
		s = (Item[]) new Object[10];
	}
	
	// is the randomized queue empty?
	public boolean isEmpty() {
		return N == 0;
	}

	// return the number of items on the randomized queue
	public int size() {
		return N;
	}

	// add the item
	public void enqueue(Item item) {
		s[N++] = item;
	}

	// remove and return a random item
	public Item dequeue() {
		return s[--N];
	}

	// return a random item (but do not remove it)
	public Item sample() {
		return null;
	}

	// return an independent iterator over items in random order
	public Iterator<Item> iterator() {
		return new RandomizedQueueIterator();
	}

	private class RandomizedQueueIterator implements Iterator<Item> {

		@Override
		public boolean hasNext() {
			return false;
		}

		@Override
		public Item next() {
			return null;
		}
	}

	public static void main(String[] args) {

	}
}
