import java.util.ArrayList;
import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;

public class FastCollinearPoints {

	private ArrayList<LineSegment> _seg;

	public FastCollinearPoints(Point[] points) {
		if (points == null) {
			throw new java.lang.IllegalArgumentException();
		}

		for (Point p : points) {
			if (p == null) {
				throw new java.lang.IllegalArgumentException();
			}
		}

		_seg = new ArrayList<>();

		for (int i = 0; i < points.length - 1; i++) {
			Point p = points[i];

			ArrayList<Point> pts = new ArrayList<>();

			for (int j = i + 1; j < points.length; j++) {
				Point q = points[j];
				pts.add(q);
			}
			pts.sort(p.slopeOrder());

			for (int j = 0; j < pts.size() - 2; j++) {

				if (cmp(p, pts.get(j), pts.get(j + 1)) && cmp(p, pts.get(j + 1), pts.get(j + 2))) {
					Point[] list = { p, pts.get(j), pts.get(j + 1), pts.get(j + 2) };
					Arrays.sort(list);
					
					_seg.add( new LineSegment(list[0], list[3]) );
				}
			}
		}
	}

	private boolean cmp(Point a, Point b, Point c) {
		return Double.compare(a.slopeTo(b), a.slopeTo(c)) == 0;
	}

	public int numberOfSegments() {
		return _seg.size();
	}

	public LineSegment[] segments() {
		return (LineSegment[]) _seg.toArray(new LineSegment[0]);
	}

	/**
	 * Unit tests the Point data type.
	 */
	public static void main(String[] args) {

		String input = "test/input9.txt";

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
		FastCollinearPoints collinear = new FastCollinearPoints(points);
		for (LineSegment segment : collinear.segments()) {
			segment.draw();
			StdDraw.show();
		}

		System.out.println("results: " + collinear.segments().length);
		StdDraw.show();
	}
}
