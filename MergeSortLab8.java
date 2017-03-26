public class MergeSortLab8 {


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
			// Since we are not using an extra space to store Integer.MAX_VALUE, we have to do some checks here to see if we emptied either of the subarray
			if(leftIndex > middle) {
				i++;
				while(rightIndex <= right)
					a[i++] = scratch[rightIndex++];
				break;
			}
			if(rightIndex > right) {
				i++;
				while(leftIndex <= middle)
					a[i++] = scratch[leftIndex++];
				break;
			}
		}
	}
	// End of improved mergesort

	// Iterative mergesort
	static void iterativeMergesort(int[] scratch, int[] a) {
		// Declaring variables:
		// subLength: Length of the subproblem being considered
		// rightEnd: The end index of the subproblem being considered. The begin index can be found using rightEnd and subLength
		// halfLength: For convenience in finding the middle index to pass into improvedMerge
		// end and begin are used to take care of cases where the length of the array is not a power of 2, hence some elements at the end might have been left unsorted
		int halfLength, end, begin, rightEnd, subLength;
		for(subLength = 2; subLength <= a.length; subLength *= 2) {
			halfLength = subLength / 2;
			for(rightEnd = subLength; rightEnd <= a.length; rightEnd += subLength) {
				improvedMerge(scratch, a, rightEnd - subLength, rightEnd - halfLength - 1, rightEnd - 1);
			}
			// Taking care in the case of when the length of the array is not a power of 2
			// Merging the left out array with the previously sorted array
			if(rightEnd > a.length) {
				if (rightEnd - subLength >= a.length)
					continue;
				end = a.length - 1;
				begin = rightEnd - 2 * subLength;
				improvedMerge(scratch, a, begin, begin + subLength - 1, end);
			}
		}
		if(subLength > a.length) {
			if(subLength / 2 >= a.length)
				return;
			subLength =  subLength / 2;
			end = a.length - 1;
			improvedMerge(scratch, a, 0, subLength - 1, end);
		}
	}
	// End of iterative mergesort
}
