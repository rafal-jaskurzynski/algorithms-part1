
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

	public int _ids[];
	private int _n;
	private int _grid[][];

	// create n-by-n grid, with all sites blocked
	public Percolation(int n) {

		if (n < 1) {
			throw new java.lang.IllegalArgumentException();
		}

		_n = n;

		_grid = new int[n][];

		for (int i = 0; i < n; i++) {
			_grid[i] = new int[n];

			for (int j = 0; j < n; j++) {
				// _grid[i][j] = j;
			}

		}

		int N = (int) Math.pow(_n, 2);

		System.out.println("ids: " + N);

		_ids = new int[N];
		for (int i = 0; i < N; i++) {
			_ids[i] = i;
		}

	}

	// open site (row, col) if it is not open already
	public void open(int row, int col) {
		checkRange(row);
		checkRange(col);

		
		
		_grid[idx(row)][idx(col)] = 1;
	}

	// is site (row, col) open?
	public boolean isOpen(int row, int col) {
		checkRange(row);
		checkRange(col);

		return _grid[idx(row)][idx(col)] == 1;

	}

	// is site (row, col) full?
	public boolean isFull(int row, int col) {
		checkRange(row);
		checkRange(col);

		return true;
	}

	public int numberOfOpenSites() {

		int count = 0;

		for (int rows[] : _grid) {
			for (int col : rows) {
				count += col;
			}
		}

		return count;
	}

	// does the system percolate?
	public boolean percolates() {

		return true;
	}

	private void checkRange(int n) {
		if (n < 1 || n > _n) {
			throw new java.lang.IllegalArgumentException();
		}
	}

	public void printGrid() {
		for (int[] rows : _grid) {
			for (int i : rows) {
				String str = String.valueOf(i) + " | ";

				System.out.print(str);
			}

			System.out.println("\n");
		}

		System.out.println("ids: " + _ids.length);

		for (int id : _ids) {
			String str = String.valueOf(id) + " | ";
			System.out.print(str);
		}
		System.out.println("\n");

	}

	int idx(int i) {
		return i - 1;
	}

	void union() {

	}

	boolean find() {
		return true;
	}

	boolean connected() {
		return true;
	}

	public static void main(String[] args) {

		System.out.println("Percolation");
		Percolation p = new Percolation(5);
		p.open(1, 1);
		p.open(2, 1);

		System.out.println("open: " + p.numberOfOpenSites());

		p.printGrid();

	}

}
