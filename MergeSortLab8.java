import java.util.Random;


public class MergeSortLab8 {
	
	static Random rand = new Random();
	
	void randomlyPermute(int[] array) {
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
		return array;
	}
}