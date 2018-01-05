import java.util.ArrayList;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
//import edu.princeton.cs.algs4.StdOut;

public class KdTree {

	private class Node {

		public Node(Point2D value, boolean horizontal) {
			_value = value;
			_horizontal = horizontal;
		}

		Point2D _value;
		Node _left;
		Node _right;
		boolean _horizontal;
	}

	private Node _root;

	// construct an empty set of points
	public KdTree() {
	}

	// is the set empty?
	public boolean isEmpty() {
		return _root == null;
	}

	// number of points in the set
	public int size() {
		return _count(_root);
	}

	private int _count(Node n) {
		if (n == null) {
			return 0;
		}
		return _count(n._left) + _count(n._right) + 1;
	}

	// add the point to the set (if it is not already in the set)
	public void insert(Point2D p) {
		if (p == null) {
			throw new java.lang.IllegalArgumentException();
		}

		if (contains(p)) {
			return;
		}

		// System.out.println("kd.insert( new Point2D" + p + ");");

		if (_root == null) {
			_root = new Node(p, false);
		} else {
			_insert(_root, p, true);
		}
	}

	private void _insert(Node n, Point2D p, boolean horizontal) {
		boolean insert_left = horizontal ? (p.x() < n._value.x()) : (p.y() < n._value.y());
		Node nd = insert_left ? n._left : n._right;

		if (nd == null) {
			nd = insert_left ? (n._left = new Node(p, horizontal)) : (n._right = new Node(p, horizontal));
		} else {
			_insert(nd, p, !horizontal);
		}
	}

	// does the set contain point p?
	public boolean contains(Point2D p) {
		if (p == null) {
			throw new java.lang.IllegalArgumentException();
		}

		return _contains(_root, p, true);
	}

	private boolean _contains(Node node, Point2D p, boolean horizontal) {
		if (node == null) {
			return false;
		}

		if (p.equals(node._value)) {
			return true;
		}

		boolean comp_key = horizontal ? (p.x() < node._value.x()) : (p.y() < node._value.y());
		return _contains(comp_key ? node._left : node._right, p, !horizontal);

	}

	// draw all points to standard draw
	public void draw() {
		_draw(_root, 0, 1);
	}

	private void _draw(Node n, double x, double y) {
		if (n == null) {
			return;
		}
		Point2D p = n._value;

		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.setPenRadius(0.005);

		if (n._horizontal) {
			StdDraw.setPenColor(StdDraw.BLUE);
			if (x < p.x()) {
				StdDraw.line(x, p.y(), 1, p.y());
			} else {
				StdDraw.line(0, p.y(), x, p.y());
			}
		} else {
			StdDraw.setPenColor(StdDraw.RED);
			if (y < p.y()) {
				StdDraw.line(p.x(), y, p.x(), 1);
			} else {
				StdDraw.line(p.x(), 0, p.x(), y);
			}
		}

		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.setPenRadius(0.02);
		p.draw();

		_draw(n._left, p.x(), p.y());
		_draw(n._right, p.x(), p.y());

	}

	// all points that are inside the rectangle (or on the boundary)
	public Iterable<Point2D> range(RectHV rect) {
		if (rect == null) {
			throw new java.lang.IllegalArgumentException();
		}

		ArrayList<Point2D> list = new ArrayList<>();

		_range(_root, rect, list, true);

		return list;
	}

	private void _range(Node node, RectHV rect, ArrayList<Point2D> list, boolean horizontal) {

		if (node == null) {
			return;
		}

		if (rect.contains(node._value)) {
			list.add(node._value);
		}

		// boolean comp_key = horizontal ? ( rect.xmax() < node._value.x() ) : (
		// rect.ymax() < node._value.y() );
		// _range( comp_key ? node._left : node._right , rect, list, !horizontal);

		_range(node._left, rect, list, false);
		_range(node._right, rect, list, false);

	}

	// a nearest neighbor in the set to point p; null if the set is empty
	public Point2D nearest(Point2D p) {
		if (p == null) {
			throw new java.lang.IllegalArgumentException();
		}

		return best(_root, p);
	}

	private Point2D best(Node n, Point2D p) {
		if (n == null) {
			return null;
		}

		ArrayList<Point2D> list = new ArrayList<>();
		list.add(n._value);
		if (n._left != null) {
			list.add(n._left._value);
		}

		if (n._right != null) {
			list.add(n._right._value);
		}

		Point2D best_left = best(n._left, p);
		if (best_left != null) {
			list.add(best_left);
		}

		Point2D best_right = best(n._right, p);
		if (best_right != null) {
			list.add(best_right);
		}

		Point2D best = null;
		double dist = 0;

		for (Point2D point : list) {
			if (point == null) {
				continue;
			}
			if (best == null) {
				best = point;
				dist = Math.abs(p.distanceTo(point));
			} else {
				double new_dist = Math.abs(p.distanceTo(point));
				if (new_dist < dist) {
					dist = new_dist;
					best = point;
				}
			}
		}

		return best;
	}

//	public void print() {
//		_print(_root, 0, "c");
//	}
//
//	private void _print(Node n, int level, String s) {
//		if (n == null) {
//			return;
//		}
//		_print(n._left, level + 1, "l");
//		StdOut.printf("%d | %s | %1.2f %1.2f\n", level, s, n._value.x(), n._value.y());
//		_print(n._right, level + 1, "r");
//	}

	// unit testing of the methods (optional)
	public static void main(String[] args) {

		KdTree kd = new KdTree();

		Point2D p = new Point2D(0.544921875, 0.833984375);
		System.out.println("contains: " + kd.contains(p));
		kd.insert(p);
		System.out.println("contains: " + kd.contains(p));

		kd.insert(new Point2D(0.206107, 0.095492));
		kd.insert(new Point2D(0.975528, 0.654508));
		kd.insert(new Point2D(0.024472, 0.345492));
		kd.insert(new Point2D(0.793893, 0.095492));
		kd.insert(new Point2D(0.793893, 0.904508));
		kd.insert(new Point2D(0.975528, 0.345492));
		kd.insert(new Point2D(0.206107, 0.904508));
		kd.insert(new Point2D(0.5, 0.0));
		kd.insert(new Point2D(0.024472, 0.654508));
		kd.insert(new Point2D(0.5, 1.0));

		// kd.print();

		RectHV rect = new RectHV(0.5, 0.5, 1, 1);
		for (Point2D po : kd.range(rect)) {
			System.out.println("p: " + po);
		}

	}
}
