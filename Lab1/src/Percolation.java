
public class Percolation {

	private class UnionFind {
		int[] _ids;

		public UnionFind(int N) {
			_ids = new int[N];
			for (int i = 0; i < N; i++) {
				_ids[i] = i;
			}
		}

		public void print() {
			System.out.println("ids: ");

			for (int i = 0; i < _ids.length; i++) {
				System.out.printf("%-2d |", i);
			}
			System.out.println("");

			for (int id : _ids) {
				System.out.printf("%-2d |", id);
			}
			System.out.println("");
		}

		public boolean connected(int p, int q) {
			return _root(p) == _root(q);
		}

		public void union(int p, int q) {
			_ids[_root(p)] = _root(q);
		}

		private int _root(int i) {
			while (_ids[i] != i) {
				i = _ids[i];
			}
			return i;
		}
	}

	private int _size;
	private boolean _opened[];
	private UnionFind _union;
	private int _top_idx;
	private int _bottom_idx;

	// create n-by-n grid, with all sites blocked
	public Percolation(int n) {

		if (n < 1) {
			throw new java.lang.IllegalArgumentException();
		}

		_size = n;

		int union_size = (int) Math.pow(_size, 2);

		_opened = new boolean[union_size];

		// adding virtual top site
		_top_idx = union_size;

		// virtual bottom site
		_bottom_idx = union_size + 1;

		_union = new UnionFind(union_size + 2);

	}

	// open site (row, col) if it is not open already
	public void open(int row, int col) {
		if (isOpen(row, col)) {
			return;
		}

		int open_idx = getIndex(row, col);

		_opened[open_idx] = true;

		// top
		if (isValidIndex(row - 1, col)) {
			int idx = getIndex(row - 1, col);
			if (_opened[idx]) {
				_union.union(open_idx, idx);
			}
		} else {
			// if top layer is not available connect to virtual top node
			_union.union(open_idx, _top_idx);
		}

		// bottom
		if (isValidIndex(row + 1, col)) {
			int idx = getIndex(row + 1, col);
			if (_opened[idx]) {
				_union.union(open_idx, idx);
			}
		} else {
			// if bottom layer is not available connect to virtual bottom node
			_union.union(open_idx, _bottom_idx);
		}

		// left
		if (isValidIndex(row, col - 1)) {
			int idx = getIndex(row, col - 1);
			if (_opened[idx]) {
				_union.union(open_idx, idx);
			}
		}

		// right
		if (isValidIndex(row, col + 1)) {
			int idx = getIndex(row, col + 1);
			if (_opened[idx]) {
				_union.union(open_idx, idx);
			}
		}
	}

	// is site (row, col) open?
	public boolean isOpen(int row, int col) {
		if (!isValidIndex(row, col)) {
			throw new java.lang.IllegalArgumentException();
		}

		return _opened[getIndex(row, col)];
	}

	// is site (row, col) full?
	public boolean isFull(int row, int col) {

		if (!isValidIndex(row, col)) {
			throw new java.lang.IllegalArgumentException();
		}

		return _union.connected(_top_idx, getIndex(row, col));
	}

	public int numberOfOpenSites() {
		int count = 0;
		for (boolean opened : _opened) {
			count += opened ? 1 : 0;
		}
		return count;
	}

	// does the system percolate?
	public boolean percolates() {
		return _union.connected(_top_idx, _bottom_idx);
	}

	private boolean isValidIndex(int p, int q) {
		int x = p - 1;
		int y = q - 1;
		return !(x < 0 || y < 0 || x >= _size || y >= _size);
	}

	private int getIndex(int p, int q) {
		int x = p - 1;
		int y = q - 1;
		return x * _size + y;
	}

	private void printGrid() {
		int i = 0;
		for (boolean opened : _opened) {

			String str = String.valueOf(opened ? 1 : 0) + " | ";
			System.out.print(str);

			i++;

			if (i % _size == 0) {
				System.out.println("\n");
			}
		}

		_union.print();
	}
}
