import edu.princeton.cs.algs4.StdIn;

public class Permutation {
	public static void main(String[] args) {

		System.out.println("permutation");

		if (args.length == 1) {
			System.out.println("reading from stdin: ");
			int N = Integer.parseInt(args[0]);

			System.out.println("return elements: " + N);

			RandomizedQueue< String > randq = new RandomizedQueue<>( );
			while (!StdIn.isEmpty()) {
				String s = StdIn.readString();
				System.out.println("enqueqe: " + s);

				randq.enqueue(s);
			}
			
			System.out.println("dequeue: " + N);

			for( int i=0; i < N; i++)
			{
				randq.dequeue();
			}
			
			return;
		}
		
	}
}
