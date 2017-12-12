import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

public class Deque<Item> implements Iterable<Item> {

	private class Node {
		Item _item;
		Node _next;
	}

	private Node _root = null;

	// construct an empty deque
	public Deque() {
		// nothing to do
	}

	// is the deque empty?
	public boolean isEmpty() {
		return _root == null;
	}

	// return the number of items on the deque
	public int size() {
		int count = 0;
		Node node = _root;

		while (node != null) {
			node = node._next;
			count++;
		}
		return count;
	}

	// add the item to the front
	public void addFirst(Item item) {
		if (item == null) {
			throw new java.lang.IllegalArgumentException();
		}

		Node new_node = new Node();
		new_node._item = item;
		new_node._next = _root;
		_root = new_node;
	}

	// add the item to the end
	public void addLast(Item item) {
		if (item == null) {
			throw new java.lang.IllegalArgumentException();
		}

		if (_root == null) {
			_root = new Node();
			_root._item = item;
			return;
		}

		Node node = _root;

		while (node._next != null) {
			node = node._next;
		}

		Node new_node = new Node();
		new_node._item = item;
		node._next = new_node;
	}

	// remove and return the item from the front
	public Item removeFirst() {
		if (_root == null) {
			throw new java.util.NoSuchElementException();
		}

		Item item = _root._item;
		_root = _root._next;
		return item;
	}

	// remove and return the item from the end
	public Item removeLast() {
		if (_root == null) {
			throw new java.util.NoSuchElementException();
		}

		if (_root._next == null) {
			Item temp = _root._item;
			_root = null;
			return temp;
		}

		Node node = _root;

		while (node._next._next != null) {
			node = node._next;
		}

		Item temp = node._next._item;
		node._next = null;
		return temp;
	}

	private class ListIterator implements Iterator<Item> {
		private Node current = _root;

		@Override
		public boolean hasNext() {
			return current._next != null;
		}

		@Override
		public Item next() {

			if (current._item == null) {
				throw new java.util.NoSuchElementException();
			}

			Item item = current._item;
			current = current._next;
			return item;
		}
	}

	// return an iterator over items in order from front to end
	public Iterator<Item> iterator() {
		return new ListIterator();
	}

	public void print() {
		Node node = _root;

		while (node != null) {
			System.out.print(" | " + String.valueOf(node._item));
			node = node._next;
		}

		System.out.println(" ");
	}

	public static void main(String[] args) {
		System.out.println("dequeue");

		Deque<Integer> dq = new Deque<>();
		System.out.println("isEmpty: " + String.valueOf(dq.isEmpty()));
		dq.addFirst(10);
		System.out.println("isEmpty: " + String.valueOf(dq.isEmpty()));
		dq.addFirst(20);
		dq.addLast(30);
		dq.addLast(40);
		dq.addLast(50);

		System.out.println("count: " + String.valueOf(dq.size()));

		for (int it : dq) {
			System.out.println("item: " + String.valueOf(it));
		}

		dq.print();

		dq.removeLast();
		dq.removeLast();
		dq.print();
		dq.removeLast();
		dq.removeLast();
		dq.removeLast();

		// System.out.println("removeFirst: " + String.valueOf(dq.removeFirst( )));
		// System.out.println("removeFirst: " + String.valueOf(dq.removeFirst( )));
		System.out.println("isEmpty: " + String.valueOf(dq.isEmpty()));
	}
}
