import edu.princeton.cs.algs4.StdIn;

public class Permutation {
	public static void main(String[] args) {

		RandomizedQueue<String> rq = new RandomizedQueue<>();

		if (args.length == 1) {
			int N = Integer.parseInt(args[0]);

			while (!StdIn.isEmpty()) {
				String s = StdIn.readString();
				rq.enqueue(s);
			}

			for (int i = 0; i < N; i++) {
				System.out.println(String.valueOf(rq.dequeue()));
			}
			return;
		}
	}
}
