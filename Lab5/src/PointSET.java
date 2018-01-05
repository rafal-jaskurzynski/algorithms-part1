import java.util.ArrayList;
import java.util.TreeSet;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

public class PointSET {

	private TreeSet<Point2D> _tree;

	// construct an empty set of points
	public PointSET() {
		_tree = new TreeSet<>();
	}

	// is the set empty?
	public boolean isEmpty() {
		return _tree.size() == 0;
	}

	// number of points in the set
	public int size() {
		return _tree.size();
	}

	// add the point to the set (if it is not already in the set)
	public void insert(Point2D p) {
		if (p == null) {
			throw new java.lang.IllegalArgumentException();
		}
		_tree.add(p);
	}

	// does the set contain point p?
	public boolean contains(Point2D p) {
		if (p == null) {
			throw new java.lang.IllegalArgumentException();
		}

		return _tree.contains(p);
	}

	// draw all points to standard draw
	public void draw() {
		for (Point2D p : _tree) {
			p.draw();
		}
	}

	// all points that are inside the rectangle (or on the boundary)
	public Iterable<Point2D> range(RectHV rect) {
		if (rect == null) {
			throw new java.lang.IllegalArgumentException();
		}

		ArrayList<Point2D> range = new ArrayList<Point2D>();

		for (Point2D p : _tree) {
			if (rect.contains(p)) {
				range.add(p);
			}
		}

		return range;
	}

	// a nearest neighbor in the set to point p; null if the set is empty
	public Point2D nearest(Point2D p) {
		if (p == null) {
			throw new java.lang.IllegalArgumentException();
		}

		Point2D best = null;

		for (Point2D point : _tree) {
			best = (best == null) ? point : ((point.distanceTo(p) < best.distanceTo(p) ? point : best));
		}

		return best;
	}

	// unit testing of the methods (optional)
	public static void main(String[] args) {
	}

}
