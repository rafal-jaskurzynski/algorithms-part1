import edu.princeton.cs.algs4.*;

public class BruteCollinearPoints {
	
	private LineSegment[] _segments;

	// finds all line segments containing 4 points
	public BruteCollinearPoints(Point[] points) {
		if ( points == null )
		{
			throw new java.lang.IllegalArgumentException();
		}
		
		_segments = new LineSegment[10];
		
		for ( int i = 0; i < points.length - 1; i ++ )
		{
			
			Point p = points[i];
			
			for ( int j = i + 1; j < points.length; j ++ )
			{
				Point p2 = points[j];
				System.out.println(i + " "+ j + " | (" + p.x + ","+p.y +"," + p2.x + ","+p2.y + ") | " + p.slopeTo(p2));

			}
			
//			_segments[i] = new LineSegment( points[i], points[i+1]);
			
			
		}
		
//		for( Point p : points )
//		{
//			if ( p == null )
//			{
//				throw new java.lang.IllegalArgumentException();
//			}
//
//		}
		
	}

	// the number of line segments
	public int numberOfSegments() {
		return _segments.length;
	}

	// the line segments
	public LineSegment[] segments() {
		return _segments;
	}
	
	public static void main(String[] args) {

	    // read the n points from a file
		
		String input = args.length == 1 ? args[0] : "test/input6.txt";
		
	    In in = new In(input);
	    int n = in.readInt();
	    
	    Point[] points = new Point[n];
	    for (int i = 0; i < n; i++) {
	        int x = in.readInt();
	        int y = in.readInt();
	        points[i] = new Point(x, y);
	    }

	    // draw the points
//	    StdDraw.enableDoubleBuffering();
//	    StdDraw.setXscale(0, 32768);
//	    StdDraw.setPenRadius(0.02);
//	    StdDraw.setYscale(0, 32768);
//	    for (Point p : points) {
//	        p.draw();
//	        System.out.println("draw: " + p.x + ","+p.y);
//	    }
//	    StdDraw.show();
	    
//	    StdDraw.setPenColor(StdDraw.RED);
//	    StdDraw.setPenRadius(0.003);


	    // print and draw the line segments
	    BruteCollinearPoints collinear = new BruteCollinearPoints(points);
	    for (LineSegment segment : collinear.segments()) {
	    	
	        StdOut.println(segment);
	        if ( segment == null )
	        {
	        	break;
	        }
	        
//	        segment.draw();
	    }
//	    StdDraw.show();
	}
}
