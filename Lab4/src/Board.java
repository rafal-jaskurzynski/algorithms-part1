import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Board {

	private final static Integer EMPTY_ELEMENT = 0;
	private final int _n;
	private final Integer[] _block;
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

	private Integer[] two_to_one(int[][] to_copy) {
		Integer[] copy = new Integer[_n * _n];

		for (int row = 0; row < _n; row++) {
			for (int col = 0; col < _n; col++) {
				copy[getIndex(row, col)] = to_copy[row][col];
			}
		}
		return copy;
	}

	private int[][] one_to_two(Integer[] to_copy) {
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
			int idx = Arrays.asList(_block).indexOf(i + 1);
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
		Integer[] cpy = copy( _block );
		
		int idx_2 = Arrays.asList(_block).indexOf( 2 );
		int idx_1 = Arrays.asList(_block).indexOf( 1 );

		cpy[ idx_1 ] = 2;
		cpy[ idx_2 ] = 1;
		
		return new Board( one_to_two(cpy));
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

	private class BoardCollection<Board> implements Iterable<Board> {

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
		int idx = Arrays.asList(_block).indexOf(EMPTY_ELEMENT);

		if ((idx - _n) >= 0) {
			Integer[] cpy = copy(_block);
			cpy[idx] = cpy[idx - _n];
			cpy[idx - _n] = EMPTY_ELEMENT;
			neighbors.add(new Board(one_to_two(cpy)));
		}

		if ((idx + _n) < (_n * _n)) {
			Integer[] cpy = copy(_block);
			cpy[idx] = cpy[idx + _n];
			cpy[idx + _n] = EMPTY_ELEMENT;
			neighbors.add(new Board(one_to_two(cpy)));
		}

		if ((idx % _n) - 1 >= 0) {
			Integer[] cpy = copy(_block);
			cpy[idx] = cpy[idx - 1];
			cpy[idx - 1] = EMPTY_ELEMENT;
			neighbors.add(new Board(one_to_two(cpy)));
		}

		if ((idx % _n) + 1 < _n) {
			Integer[] cpy = copy(_block);
			cpy[idx] = cpy[idx + 1];
			cpy[idx + 1] = EMPTY_ELEMENT;
			neighbors.add(new Board(one_to_two(cpy)));
		}

		return new BoardCollection<Board>(neighbors);
	}

	private Integer[] copy(Integer[] to_copy) {
		Integer[] copy = new Integer[to_copy.length];
		for (int i = 0; i < to_copy.length; i++) {
			copy[i] = to_copy[i];
		}
		return copy;
	}

	// string representation of this board (in the output format specified below)
	public String toString() {
		return String.valueOf(_n) + print(_block) + String.format("\n");
	}

	private String print(Integer[] _arg) {
		String s = "";
		for (int i = 0; i < _arg.length; i++) {

			if ((i % _n) == 0) {
				s += String.format("\n");
			}

			s += String.format("%4d", _block[i]);
		}
		return s;
	}

//	public String toString() {
//	    StringBuilder s = new StringBuilder();
//	    s.append(n + "\n");
//	    for (int i = 0; i < n; i++) {
//	        for (int j = 0; j < n; j++) {
//	            s.append(String.format("%2d ", tiles[i][j]));
//	        }
//	        s.append("\n");
//	    }
//	    return s.toString();
//	}
	
	// unit tests (not graded)
	public static void main(String[] args) {
		// create initial board from file
		int n = 3;
		int[][] blocks = new int[n][n];
		blocks[0][0] = 8;
		blocks[0][1] = 1;
		blocks[0][2] = 3;
		blocks[1][0] = 4;
		blocks[1][1] = 0;
		blocks[1][2] = 2;
		blocks[2][0] = 7;
		blocks[2][1] = 6;
		blocks[2][2] = 5;

		// int n = 2;
		// int[][] blocks = new int[n][n];
		// blocks[0][0] = 3;
		// blocks[0][1] = 2;
		// blocks[1][0] = 1;
		// blocks[1][1] = 0;

		Board initial = new Board(blocks);
		StdOut.println(initial);
		
		StdOut.println(initial.twin());

		

//		for (Board b : initial.neighbors()) {
//			System.out.println(b);
//		}

		System.out.println("hamming: " + initial.hamming());
		System.out.println("manhattan: " + initial.manhattan());

	}
}