import java.util.ArrayList;
import java.util.Comparator;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

public class Solver {

	private ArrayList<Board> _solution;

	private class SearchNode {
		Board board;
		public int moves;
		SearchNode parent;
		boolean isSolvable;

		public SearchNode(Board _board, int _moves, SearchNode searchNode, boolean solvable) {
			board = _board;
			moves = _moves;
			parent = searchNode;
			isSolvable = solvable;
		}

		public int priority() {
			return board.manhattan() + moves + board.hamming();
		}
	}

	private class NodeComparator implements Comparator<SearchNode> {
		@Override
		public int compare(SearchNode o1, SearchNode o2) {

			int p1 = o1.priority();
			int p2 = o2.priority();

			if (p1 == p2) {
				return 0;
			}
			return p1 < p2 ? -1 : 1;
		}
	}

	// find a solution to the initial board (using the A* algorithm)
	public Solver(Board initial) {

		if (initial == null) {
			throw new IllegalArgumentException();
		}

		SearchNode sn = new SearchNode(initial, 0, null, true);
		MinPQ<SearchNode> pq = new MinPQ<>(new NodeComparator());
		pq.insert(new SearchNode(initial.twin(), 0, null, false));

		while (!sn.board.isGoal()) {

			for (Board nb : sn.board.neighbors()) {
				Board parent = (sn.parent != null) ? sn.parent.board : null;

				if (!nb.equals(parent)) {
					SearchNode node = new SearchNode(nb, sn.moves + 1, sn, sn.isSolvable);
					pq.insert(node);
				}
			}

			sn = pq.delMin();
		}

		_solution = new ArrayList<>();
		
		if (sn.isSolvable) {
			// collect solutions
			SearchNode nodes = sn;

			while (nodes != null) {
				_solution.add(nodes.board);
				nodes = nodes.parent;
			}

			for (int i = 0; i < _solution.size() / 2; i++) {
				Board tmp = _solution.get(i);
				int idx = _solution.size() - i - 1;
				_solution.set(i, _solution.get(idx));
				_solution.set(idx, tmp);
			}
		}
	}

	// is the initial board solvable?
	public boolean isSolvable() {
		return !_solution.isEmpty();
	}

	// min number of moves to solve initial board; -1 if unsolvable
	public int moves() {
		return !_solution.isEmpty() ? (_solution.size() - 1) : -1;
	}

	// sequence of boards in a shortest solution; null if unsolvable
	public Iterable<Board> solution() {
		return !_solution.isEmpty() ? _solution : null;
	}

	// solve a slider puzzle (given below)
	public static void main(String[] args) {

		int n;
		int[][] blocks;

		String filename = "testing/puzzle2x2-02.txt";

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

		// solve the puzzle
		Solver solver = new Solver(initial);

		// print solution to standard output
		if (!solver.isSolvable()) {
			StdOut.println("No solution possible");
		} else {
			StdOut.println("Minimum number of moves = " + solver.moves());
			int i = 0;
			for (Board board : solver.solution()) {
				System.out.println("move " + i);
				i++;
				StdOut.println(board);
			}
		}
	}
}