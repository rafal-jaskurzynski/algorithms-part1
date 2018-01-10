
/******************************************************************************
 *  Compilation:  javac Point.java
 *  Execution:    java Point
 *  Dependencies: none
 *  
 *  An immutable data type for points in the plane.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/

import java.util.Comparator;
import edu.princeton.cs.algs4.StdDraw;

public class Point implements Comparable<Point> {

	private final int x; // x-coordinate of this point
	private final int y; // y-coordinate of this point

	/**
	 * Initializes a new point.
	 *
	 * @param x
	 *            the <em>x</em>-coordinate of the point
	 * @param y
	 *            the <em>y</em>-coordinate of the point
	 */
	public Point(int x, int y) {
		/* DO NOT MODIFY */
		this.x = x;
		this.y = y;
	}

	/**
	 * Draws this point to standard draw.
	 */
	public void draw() {
		/* DO NOT MODIFY */
		StdDraw.point(x, y);
	}

	/**
	 * Draws the line segment between this point and the specified point to standard
	 * draw.
	 *
	 * @param that
	 *            the other point
	 */
	public void drawTo(Point that) {
		/* DO NOT MODIFY */
		StdDraw.line(this.x, this.y, that.x, that.y);
	}

	/**
	 * Returns the slope between this point and the specified point. Formally, if
	 * the two points are (x0, y0) and (x1, y1), then the slope is (y1 - y0) / (x1 -
	 * x0). For completeness, the slope is defined to be +0.0 if the line segment
	 * connecting the two points is horizontal; Double.POSITIVE_INFINITY if the line
	 * segment is vertical; and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1)
	 * are equal.
	 *
	 * @param that
	 *            the other point
	 * @return the slope between this point and the specified point
	 */
	public double slopeTo(Point that) {

		if (that == null) {
			throw new java.lang.NullPointerException();
		}

		double diff_y = (that.y - y);
		double diff_x = (that.x - x);

		if (diff_x == 0 && diff_y == 0) {
			return Double.NEGATIVE_INFINITY;
		}
		
		if ( diff_y == 0 )
		{
			return 0;
		}

		if (diff_x == 0) {
			return Double.POSITIVE_INFINITY;
		}

		return diff_y / diff_x;
	}

	/**
	 * Compares two points by y-coordinate, breaking ties by x-coordinate. Formally,
	 * the invoking point (x0, y0) is less than the argument point (x1, y1) if and
	 * only if either y0 < y1 or if y0 = y1 and x0 < x1.
	 *
	 * @param that
	 *            the other point
	 * @return the value <tt>0</tt> if this point is equal to the argument point (x0
	 *         = x1 and y0 = y1); a negative integer if this point is less than the
	 *         argument point; and a positive integer if this point is greater than
	 *         the argument point
	 */
	public int compareTo(Point that) {
		if (x == that.x && y == that.y) {
			return 0;
		}

		return (y < that.y) || (y == that.y && x < that.x) ? -1 : 1;
	}

	/**
	 * Compares two points by the slope they make with this point. The slope is
	 * defined as in the slopeTo() method.
	 *
	 * @return the Comparator that defines this ordering on points
	 */
	public Comparator<Point> slopeOrder() {
		return new BySlope(this);
	}

	private class BySlope implements Comparator<Point> {

		private Point _p;

		BySlope(Point p) {
			_p = p;
		}

		public int compare(Point a, Point b) {

			if (a == null || b == null) {
				throw new java.lang.NullPointerException();
			}
			
			if (  _p.slopeTo(a) ==  _p.slopeTo(b) )
			{
				return 0;
			}

			return _p.slopeTo(a) < _p.slopeTo(b) ? -1 : 1;
		}
	}

	/**
	 * Returns a string representation of this point. This method is provide for
	 * debugging; your program should not rely on the format of the string
	 * representation.
	 *
	 * @return a string representation of this point
	 */
	public String toString() {
		/* DO NOT MODIFY */
		return "(" + x + ", " + y + ")";
	}

	public static void main(String[] args) {

		// Test 1
		{
			Point p = new Point(278, 46);
			Point q = new Point(278, 180);

			System.out.println("test1 " + p + ": " + q + " slope: " + p.slopeTo(q));

			if (Double.POSITIVE_INFINITY == p.slopeTo(q)) {
				System.out.println("test1 ok");
			}
		}

		// Test 2
		{
			Point same = new Point(222, 89);

			if (Double.NEGATIVE_INFINITY == same.slopeTo(same)) {
				System.out.println("test2 ok");
			}
		}

		// Test 3
		{
			Point p = new Point(38, 443);
			Point q = new Point(221, 443);

			System.out.println("test3 " + p + ": " + q + " slope: " + p.slopeTo(q));

			if (0 == p.slopeTo(q)) {
				System.out.println("test3 ok");
			}
		}

		// Test 4
		{
			Point p = new Point(376, 247);
			Point q = new Point(376, 370);

			System.out.println("test4 " + p + ": " + q + " slope: " + p.slopeTo(q));

			if (Double.POSITIVE_INFINITY == p.slopeTo(q)) {
				System.out.println("test4 ok");
			}
		}

		// Test 5
		{
			Point p = new Point(4, 7);
			Point q = new Point(4, 0);

			System.out.println("test5 " + p + ": " + q + " slope: " + p.slopeTo(q));

			if (Double.POSITIVE_INFINITY == p.slopeTo(q)) {
				System.out.println("test5 ok");
			}
		}
		
		// Test 6
		{
			Point p = new Point(259, 182);
			Point q = new Point(116, 182);

			System.out.println("test6 " + p + ": " + q + " slope: " + p.slopeTo(q));

			if (0 == p.slopeTo(q)) {
				System.out.println("test6 ok");
			}
		}

		{
			Point p = new Point(13956, 144);
			Point q = new Point(29826, 25868);
			Point r = new Point(11186, 21011);

			System.out.println("compare 1 = " + p.slopeOrder().compare(q, r));

		}

	}

}
