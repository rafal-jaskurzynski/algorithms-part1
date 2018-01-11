import java.util.ArrayList;
import java.util.Iterator;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Board {

	private final int EMPTY_ELEMENT = 0;
	private final int _n;
	private final int[] _block;
	private int _manhatan = -1;

	// construct a board from an n-by-n array of blocks
	// (where blocks[i][j] = block in row i, column j)
	public Board(int[][] blocks) {
		if (blocks == null) {
			throw new java.lang.IllegalArgumentException();
		}

		_n = blocks.length;
		_block = two_to_one(blocks);
	}

	private int[] two_to_one(int[][] to_copy) {
		int[] copy = new int[_n * _n];

		for (int row = 0; row < _n; row++) {
			for (int col = 0; col < _n; col++) {
				copy[getIndex(row, col)] = to_copy[row][col];
			}
		}
		return copy;
	}

	private int[][] one_to_two(int[] to_copy) {
		int[][] copy = new int[_n][_n];

		for (int i = 0; i < _n * _n; i++) {

			copy[toRow(i)][toCol(i)] = to_copy[i];
		}
		return copy;
	}

	// board dimension n
	public int dimension() {
		return _n;
	}

	// number of blocks out of place
	public int hamming() {
		int hamming = 0;
		for (int i = 0; i < _block.length; i++) {
			if (_block[i] != (i + 1) && _block[i] != EMPTY_ELEMENT) {
				hamming += 1;
			}
		}
		return hamming;
	}

	private int getIndex(int row, int col) {
		return (row * _n + col);
	}

	private int toRow(int idx) {
		return idx / _n;
	}

	private int toCol(int idx) {
		return idx % _n;
	}

	// sum of Manhattan distances between blocks and goal
	public int manhattan() {

		if (_manhatan != -1) {
			return _manhatan;
		}

		int manhatan = 0;

		for (int i = 0; i < _block.length - 1; i++) {
			int idx = indexOf(_block, i + 1);
			int diff = Math.abs(toRow(i) - toRow(idx)) + Math.abs(toCol(i) - toCol(idx));
			manhatan += diff;
		}

		_manhatan = manhatan;

		return _manhatan;
	}

	// is this board the goal board?
	public boolean isGoal() {
		for (int i = 0; i < _block.length - 1; i++) {
			if (_block[i] != i + 1)
				return false;
		}
		return true;
	}

	// a board that is obtained by exchanging any pair of blocks
	public Board twin() {
		int[] cpy = copy(_block);

		int idx_2 = indexOf(_block, 2);
		int idx_1 = indexOf(_block, 1);

		cpy[idx_1] = 2;
		cpy[idx_2] = 1;

		return new Board(one_to_two(cpy));
	}

	// does this board equal y?
	public boolean equals(Object y) {
		if (y == null || y instanceof String) {
			return false;
		}
		Board that = (Board) y;

		if (_n != that._n) {
			return false;
		}

		for (int i = 0; i < _block.length - 1; i++) {
			if (_block[i] != that._block[i])
				return false;
		}

		return true;
	}

	private int indexOf(int[] board, int el) {
		for (int i = 0; i < board.length; i++) {
			if (el == board[i]) {
				return i;
			}
		}
		return -1;
	}

	private class BoardCollection implements Iterable<Board> {

		ArrayList<Board> _neighbors;

		public BoardCollection(ArrayList<Board> neighbors) {
			_neighbors = neighbors;
		}

		public Iterator<Board> iterator() {
			return _neighbors.iterator();
		}
	}

	// all neighboring boards
	public Iterable<Board> neighbors() {
		ArrayList<Board> neighbors = new ArrayList<Board>();
		int idx = indexOf(_block, EMPTY_ELEMENT);

		if ((idx - _n) >= 0) {
			int[] cpy = copy(_block);
			cpy[idx] = cpy[idx - _n];
			cpy[idx - _n] = EMPTY_ELEMENT;
			neighbors.add(new Board(one_to_two(cpy)));
		}

		if ((idx + _n) < (_n * _n)) {
			int[] cpy = copy(_block);
			cpy[idx] = cpy[idx + _n];
			cpy[idx + _n] = EMPTY_ELEMENT;
			neighbors.add(new Board(one_to_two(cpy)));
		}

		if ((idx % _n) - 1 >= 0) {
			int[] cpy = copy(_block);
			cpy[idx] = cpy[idx - 1];
			cpy[idx - 1] = EMPTY_ELEMENT;
			neighbors.add(new Board(one_to_two(cpy)));
		}

		if ((idx % _n) + 1 < _n) {
			int[] cpy = copy(_block);
			cpy[idx] = cpy[idx + 1];
			cpy[idx + 1] = EMPTY_ELEMENT;
			neighbors.add(new Board(one_to_two(cpy)));
		}

		return new BoardCollection(neighbors);
	}

	private int[] copy(int[] to_copy) {
		int[] copy = new int[to_copy.length];
		for (int i = 0; i < to_copy.length; i++) {
			copy[i] = to_copy[i];
		}
		return copy;
	}

	// string representation of this board (in the output format specified below)
	public String toString() {
		return _n + print(_block) + String.format("\n");
	}

	private String print(int[] _arg) {
		String s = "";
		for (int i = 0; i < _arg.length; i++) {

			if ((i % _n) == 0) {
				s += String.format("\n");
			}

			s += String.format("%6d", _block[i]);
		}
		return s;
	}

	// unit tests (not graded)
	public static void main(String[] args) {

		int n;
		int[][] blocks;

		String filename = "testing/puzzle02.txt";

		if (args.length == 1) {
			filename = args[0];
		}

		// create initial board from file
		In in = new In(filename);
		n = in.readInt();
		blocks = new int[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++)
				blocks[i][j] = in.readInt();
		}

		Board initial = new Board(blocks);
		StdOut.println(initial);

		for (Board b : initial.neighbors()) {
			System.out.println(b);
		}

		System.out.println("hamming: " + initial.hamming());
		System.out.println("manhattan: " + initial.manhattan());
	}
}