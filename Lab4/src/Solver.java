import java.util.ArrayList;
import java.util.Comparator;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;


public class Solver {
	
	private MinPQ<SearchNode> _pq;
	private int _moves;
	private ArrayList< Board > _solution;
	private boolean _solved;

	private class SearchNode
	{
		Board _board;
		Board _pred;
		int _moves;
				
		public SearchNode( Board board, int moves, Board pred ) {
			_board = board;
			_moves = moves;
			_pred = pred;
		}
		
		public int priority()
		{
			return _board.manhattan() + _moves;
		}
		
		public Board getBoard()
		{
			return _board;
		}
						
	}
	
	private class NodeComparator implements Comparator<SearchNode>
	{
		@Override
		public int compare(SearchNode o1, SearchNode o2) {
			
			int p1 = o1.priority();
			int p2 = o2.priority();
			
			if  (p1 == p2 )
			{
				return 0;
			}
			return  p1 < p2 ? -1 : 1;
		}
	}

	// find a solution to the initial board (using the A* algorithm)
	public Solver(Board initial) {
		
		_moves = 0;
		_pq = new MinPQ<>( new NodeComparator( ) );
		_solution = new ArrayList<>();
		_solved = true;
		
		_pq.insert( new SearchNode(initial, _moves, null));
		
		SearchNode sn = _pq.delMin();
		_solution.add( sn.getBoard());
		
		while ( !sn.getBoard( ).isGoal( ) )
		{
			_moves++;
			
			for ( Board nb : sn.getBoard().neighbors() )
			{
				if ( nb != sn._pred )
				{
					SearchNode node = new SearchNode(nb, _moves, sn.getBoard());
					_pq.insert(node);
				}
			}
			
			sn = _pq.delMin( );
			
			_solution.add( sn.getBoard());
			
			if ( _moves == 100)
			{
				_solved = false;
				break;
			}

		}
	}

	// is the initial board solvable?
	public boolean isSolvable() {
		return _solved;
	}

	// min number of moves to solve initial board; -1 if unsolvable
	public int moves() {
		return _moves++;
	}

	// sequence of boards in a shortest solution; null if unsolvable
	public Iterable<Board> solution() {
		return _solution;
	}

	// solve a slider puzzle (given below)
	public static void main(String[] args) {

		int n;
		int[][] blocks;
		
		if ( args.length == 0 )
		{
			n = 3;
			blocks = new int[n][n];
			blocks[0][0] = 0;
			blocks[0][1] = 1;
			blocks[0][2] = 3;
			blocks[1][0] = 4;
			blocks[1][1] = 2;
			blocks[1][2] = 5;
			blocks[2][0] = 7;
			blocks[2][1] = 8;
			blocks[2][2] = 6;
		}
		else
		{
			// create initial board from file
			In in = new In(args[0]);
			n = in.readInt();
			blocks = new int[n][n];
			for (int i = 0; i < n; i++)
			{
				for (int j = 0; j < n; j++)
					blocks[i][j] = in.readInt();
			}

		}
		
		Board initial = new Board(blocks);

		// solve the puzzle
		Solver solver = new Solver(initial);

		// print solution to standard output
		if (!solver.isSolvable())
			StdOut.println("No solution possible");
		else {
			StdOut.println("Minimum number of moves = " + solver.moves());
			for (Board board : solver.solution())
				StdOut.println(board);
		}
	}
}