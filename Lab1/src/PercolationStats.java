
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class PercolationStats {

	private int _trials;
	private double _results[];
	final private static double CONFIDENCE_95 = 1.96;

	// perform trials independent experiments on an n-by-n grid
	public PercolationStats(int n, int trials) {
		if (n < 1 || trials < 1) {
			throw new java.lang.IllegalArgumentException();
		}

		_results = new double[trials];
		_trials = trials;

		for (int i = 0; i < trials; i++) {
			_results[i] = _run(n);
		}
	}

	private double _run(int size) {
		Percolation p = new Percolation(size);

		int i = 0;
		while (!p.percolates()) {
			int row = StdRandom.uniform(1, size + 1);
			int col = StdRandom.uniform(1, size + 1);
			if (!p.isOpen(row, col)) {
				p.open(row, col);
				i++;
			}
		}

		return i / Math.pow(size, 2);

	}

	// sample mean of percolation threshold
	public double mean() {
		return StdStats.mean(_results);
	}

	// sample standard deviation of percolation threshold
	public double stddev() {
		return StdStats.stddev(_results);
	}

	// low endpoint of 95% confidence interval
	public double confidenceLo() {
		return mean() - (CONFIDENCE_95 * stddev() / Math.sqrt(_trials));
	}

	// high endpoint of 95% confidence interval
	public double confidenceHi() {
		return mean() + (CONFIDENCE_95 * stddev() / Math.sqrt(_trials));
	}

	private void confidence() {
		System.out.println("[" + confidenceLo() + ";" + confidenceHi() + "]");
	}

	public static void main(String[] args) {

		if ( args.length == 1)
		{
			int N = StdIn.readInt();
			System.out.println("size: " + N);

			Percolation percolation = new Percolation(N);
			while (!StdIn.isEmpty()) {
				int p = StdIn.readInt();
				int q = StdIn.readInt();
				System.out.println("read: " + p +"," + q);

				percolation.open(p, q);
				

				if (!percolation.isFull(p, q)) {
					System.out.println("[error]");
					break;
				}

			}
			return;
		}

		
		Percolation percolation = new Percolation(6);
		percolation.open(1, 6);
//		percolation.printGrid();
		System.out.println("[isFull][isOpen] :" + String.valueOf( percolation.isFull(1, 6) ) + "," + String.valueOf( percolation.isOpen(1, 6) ));
		

		// int n = 0;
		// int trials = 0;
		// if (args.length == 2) {
		// n = Integer.parseInt(args[0]);
		// trials = Integer.parseInt(args[1]);
		// }
		//
		// n = 20;
		// trials = 5;
		//
		// PercolationStats stats = new PercolationStats(n, trials);

	}
}
