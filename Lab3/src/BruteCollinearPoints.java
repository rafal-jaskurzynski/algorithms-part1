import java.util.ArrayList;
import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;

public class BruteCollinearPoints {

	private ArrayList<LineSegment> _seg;

	// finds all line segments containing 4 points
	public BruteCollinearPoints(Point[] points) {
		if (points == null) {
			throw new java.lang.IllegalArgumentException();
		}

		for (Point p : points) {
			if (p == null) {
				throw new java.lang.IllegalArgumentException();
			}
		}

		_seg = new ArrayList<>();

		for (int i = 0; i < points.length; i++) {
			Point pi = points[i];

			for (int j = i + 1; j < points.length; j++) {
				Point pj = points[j];

				if (pj.compareTo(pi) == 0) {
					throw new java.lang.IllegalArgumentException();
				}

				double slope1 = pi.slopeTo(pj);

				for (int k = j + 1; k < points.length; k++) {
					Point pk = points[k];

					if (pk.compareTo(pj) == 0 || pk.compareTo(pi) == 0) {
						throw new java.lang.IllegalArgumentException();
					}

					double slope2 = pj.slopeTo(pk);

					for (int l = k + 1; l < points.length; l++) {
						Point pl = points[l];

						if (pl.compareTo(pk) == 0 || pl.compareTo(pi) == 0 || pl.compareTo(pj) == 0) {
							throw new java.lang.IllegalArgumentException();
						}

						double slope3 = pk.slopeTo(pl);

						if (slope1 == slope2 && slope2 == slope3) {
							Point[] list = { pi, pj, pk, pl };
							Arrays.sort(list);
							_seg.add(new LineSegment(list[0], list[3]));

						}
					}
				}
			}
		}
	}

	// the number of line segments
	public int numberOfSegments() {
		return _seg.size();
	}

	// the line segments
	public LineSegment[] segments() {
		return (LineSegment[]) _seg.toArray(new LineSegment[0]);
	}

	public static void main(String[] args) {

		String input = "test/test5.txt";

		In in = new In(input);
		int n = in.readInt();

		Point[] points = new Point[n];
		for (int i = 0; i < n; i++) {
			int x = in.readInt();
			int y = in.readInt();
			points[i] = new Point(x, y);
		}

		// draw the points
		StdDraw.enableDoubleBuffering();
		StdDraw.setXscale(0, 32768);
		StdDraw.setPenRadius(0.02);
		StdDraw.setYscale(0, 32768);
		for (Point p : points) {
			p.draw();
		}
		StdDraw.show();
		StdDraw.setPenColor(StdDraw.RED);
		StdDraw.setPenRadius(0.003);

		// print and draw the line segments
		BruteCollinearPoints collinear = new BruteCollinearPoints(points);
		for (LineSegment segment : collinear.segments()) {
			segment.draw();
		}
		StdDraw.show();
	}
}
