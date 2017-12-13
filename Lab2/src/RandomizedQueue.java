import java.util.Iterator;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

	private Item[] _items;
	private int N = 0;
	final static private int INIT_SIZE = 10;

	// construct an empty randomized queue
	public RandomizedQueue() {
		_resize(INIT_SIZE);
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
		if (item == null) {
			throw new java.lang.IllegalArgumentException();
		}

		if (N == _items.length) {
			_resize(_items.length * 2);
		}

		_items[N++] = item;
	}

	private void _resize(int size) {
		Item[] items_resized = (Item[]) new Object[size];

		if (_items != null) {
			for (int i = 0; i < N; i++) {
				items_resized[i] = _items[i];
			}
		}
		_items = items_resized;
	}

	// remove and return a random item
	public Item dequeue() {
		if (N == 0) {
			throw new java.util.NoSuchElementException();
		}

		int rand = StdRandom.uniform(0, N);
		Item item = _items[rand];

		for (int i = rand; i < N - 1; i++) {
			_items[i] = _items[i + 1];
		}

		N--;
		return item;
	}

	// return a random item (but do not remove it)
	public Item sample() {
		if (N == 0) {
			throw new java.util.NoSuchElementException();
		}

		return _items[StdRandom.uniform(0, N)];
	}

	// return an independent iterator over items in random order
	public Iterator<Item> iterator() {
		return new RandomizedQueueIterator();
	}

	private class RandomizedQueueIterator implements Iterator<Item> {

		private Item[] _shuffled_items;
		private int _N = 0;

		public RandomizedQueueIterator() {
			_shuffled_items = (Item[]) new Object[N];

			for (int i = 0; i < N; i++) {
				_shuffled_items[i] = _items[i];
			}

			for (int i = 0; i < N; i++) {
				int new_index = StdRandom.uniform(0, N);
				Item tmp = _shuffled_items[new_index];
				_shuffled_items[new_index] = _shuffled_items[i];
				_shuffled_items[i] = tmp;
			}
		}

		@Override
		public boolean hasNext() {
			return _N < _shuffled_items.length;
		}

		@Override
		public Item next() {

			if (!hasNext()) {
				throw new java.util.NoSuchElementException();
			}

			return _shuffled_items[_N++];
		}
	}
}
