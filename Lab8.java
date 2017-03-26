import java.util.Random;

public class Lab8 {

	static Random rand = new Random();

	static void randomlyPermute(int[] array) {
		for (int i = 0; i < array.length; i++)
		{
			int randInt = rand.nextInt(array.length-i) + i;
			int tmp = array[i];
			array[i] = array[randInt];
			array[randInt] = tmp;
		}
	}

	// Function to generate an int array from 0 to n-1 inclusive
	static int[] generateArray(int n) {
		int array[] = new int[n];
		for(int i = 0; i < n; i++) {
			array[i] = i;
		}
		randomlyPermute(array);
		return array;
	}

	static boolean testMerge() {
		final int SIZE = rand.nextInt(99) + 1; // Avoid 0
		boolean passed;

		System.out.println("Testing with array of size " + SIZE);
		int[] testArray = generateArray(SIZE);

		// Testing plain mergesort
		MergeSortLab8.mergesort(testArray, 0, SIZE - 1);
		passed = testOrder(testArray);
		System.out.println(passed ? "Plain mergesort is working correctly" : "Plain mergesort is NOT working correctly");
		if(!passed)
			return false;

		// Testing improved mergesort
		int[] scratch = new int[SIZE];
		randomlyPermute(testArray);
		MergeSortLab8.improvedMergesort(scratch, testArray, 0, SIZE - 1);
		passed = testOrder(testArray);
		System.out.println(passed ? "Improved mergesort is working correctly" : "Improved mergesort is NOT working correctly");
		if(!passed)
			return false;

		// Testing iterative mergesort
		scratch = new int[SIZE];
		randomlyPermute(testArray);
		MergeSortLab8.iterativeMergesort(scratch, testArray);
		passed = testOrder(testArray);
		System.out.println(passed ? "Iterative mergesort is working correctly" : "Iterative mergesort is NOT working correctly");
		if(!passed)
			return false;
		return true;
	}

	static boolean testOrder(int[] a) {
		for(int i = 0; i < a.length; i++)
			if(a[i] !=  i)
				return false;
		return true;
	}
  static void mergesortExperiment() {
  	int size;
		final int MAX = 134217728;
		final int RUN = 5;
		long startTime, endTime, time;
		int[] test, scratch;

		System.out.printf("%15s %8s %8s %8s%n", "Size", "Mergesort", "Improved", "Iterative");
		for(size = 1; size <= MAX; size *= 2) {
			System.out.printf("%15s ", size);
			test = generateArray(size);
			time = 0;

			// Timing plain mergesort
			for(int i = 0; i < RUN; i ++) {
				randomlyPermute(test);
				startTime = System.currentTimeMillis();
				MergeSortLab8.mergesort(test, 0, size - 1);
				endTime = System.currentTimeMillis();
				time += endTime - startTime;
			}
			System.out.printf("%8.2f ", (float) time / 1000.0);

			time = 0;

			// Timing improved mergesort
			for(int i = 0; i < RUN; i++) {
				randomlyPermute(test);
				startTime = System.currentTimeMillis();
				scratch = new int[size];
				MergeSortLab8.improvedMergesort(scratch, test, 0, size - 1);
				endTime = System.currentTimeMillis();
				time += endTime - startTime;
			}
			System.out.printf("%8.2f ", (float) time / 1000.0);

			time = 0;

			// Timing iterative mergesort
			for(int i = 0; i < RUN; i++) {
				randomlyPermute(test);
				startTime = System.currentTimeMillis();
				scratch = new int[size];
				MergeSortLab8.iterativeMergesort(scratch, test);
				endTime = System.currentTimeMillis();
				time += endTime - startTime;
			}
			System.out.printf("%8.2f ", (float) time / 1000.0);

			System.out.println();
		}
	}

  public static void main(String args[]) {
  		if(args.length > 0) {
  			if(args[0].equals("--test")) {
  				for(int i = 0; i < 10; i++)
  					System.out.println(testMerge() ? "All tests passed!\n" : "Mergesort is not working properly\n");
  			}
  		}
  		else {
  			mergesortExperiment();
  		}
  	}
}
