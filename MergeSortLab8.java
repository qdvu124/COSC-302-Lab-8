import java.util.Arrays;
import java.util.Random;


public class MergeSortLab8 {
	
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
	
	// Plain mergesort
	static void mergesort(int[] a, int left, int right){
		if(left == right) 
			return;
		int middle = (left + right) / 2;
		mergesort(a, left, middle);
		mergesort(a, middle + 1, right);
		merge(a, left, middle, right);
	}
	
	static void merge(int[] a, int left, int middle, int right) {
		int leftSize =  middle - left + 1;
		int rightSize = right - middle;
		int[] leftArray = new int[leftSize + 1];
		int[] rightArray = new int[rightSize + 1];
		for (int i = 0; i < leftSize; i++)
			leftArray[i] = a[left + i];
		for (int i = 0; i < rightSize; i++)
			rightArray[i] =  a[middle + i + 1];
		leftArray[leftSize] = Integer.MAX_VALUE;
		rightArray[rightSize] = Integer.MAX_VALUE; 
		int leftIndex = 0;
		int rightIndex = 0;
		for(int i = left; i <= right; i++) {
			if(leftArray[leftIndex] < rightArray[rightIndex]) {
				a[i] = leftArray[leftIndex];
				leftIndex++;
			}
			else {
				a[i] = rightArray[rightIndex];
				rightIndex++;
			}
		}
	}
	//End of plain mergesort
	
	// Improved mergesort with 1 memalloc
	static void improvedMergesort(int[] scratch, int[] a, int left, int right){
		if(left == right) 
			return;
		int middle = (left + right) / 2;
		improvedMergesort(scratch, a, left, middle);
		improvedMergesort(scratch, a, middle + 1, right);
		improvedMerge(scratch, a, left, middle, right);
	}
	
	static void improvedMerge(int[] scratch, int[] a, int left, int middle, int right) {
		// Transferring all elements to scratch area
		for(int i = left; i <= right; i++) {
			scratch[i] = a[i];
		}
		int leftIndex = left;
		int rightIndex = middle + 1;
		for(int i = left; i <= right; i++) {
			if(scratch[leftIndex] < scratch[rightIndex]) {
				a[i] = scratch[leftIndex];
				leftIndex++;
			}
			else {
				a[i] = scratch[rightIndex];
				rightIndex++;
			}
			if(leftIndex > middle) {
				i++;
				while(rightIndex <= right) {
					a[i] = scratch[rightIndex];
					i++;
					rightIndex++;
				}
				break;
			}
			if(rightIndex > right) {
				i++;
				while(leftIndex <= middle) {
					a[i] = scratch[leftIndex];
					i++;
					leftIndex++;
				}
				break;
			}
		}
	}
	// End of improved mergesort
	
	static void testMerge() {
		final int SIZE = 20;
		int[] testArray = generateArray(SIZE);
		System.out.println(Arrays.toString(testArray));
		mergesort(testArray, 0, SIZE - 1);
		System.out.println(Arrays.toString(testArray));
		
		// Testing improved mergesort
		int[] scratch = new int[SIZE];
		randomlyPermute(testArray);
		System.out.println(Arrays.toString(testArray));
		improvedMergesort(scratch, testArray, 0, SIZE - 1);
		System.out.println(Arrays.toString(testArray));
	}
	
	public static void main(String args[]) {
		testMerge();
	}
}