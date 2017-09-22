package sort;

import java.util.ArrayList;
import java.util.Random;

public class BubbleSort {

	public static void main(String[] args) {
		int arrayLength = 10;
		int maxIndexValue = 10;

		Random rnd = new Random();
		int[] arr = new int[arrayLength]; //make the array

		System.out.println("Starting array is... ");
		for(int i=0;i<arr.length;i++) { //fill the array
			int num = rnd.nextInt(maxIndexValue);
			arr[i] = num;
			System.out.print(num + " ");
		}

		System.out.println("Sorting..."); //run sort, TODO
		while(!isArraySortedCorrectly(arr)) {
			for(int i=0;i<arr.length;i++) {
				if(arr[i]>arr[i+1]) {
					
				}
			}

		}

		System.out.println("\nSorted array is... ");
		for(int i=0;i<arr.length;i++) { //print the sorted array
			System.out.print(arr[i] + " ");
		}

	}

	public static boolean isArraySortedCorrectly(int[] arr) {
		for(int i=0;i<arr.length;i++) {
			if(arr[i]<arr[i+1]) {
				return false;
			}
		}
		return true;
	}

}
