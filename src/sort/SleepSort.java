package sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
public class SleepSort {
	
	/**
	 * Best sort algorithm
	 * 
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		int arrayLength = 100;
		int maxIndexValue = 1000;
		int indexTimeDelay = 20;
		
		Random rnd = new Random();
		int[] arrInput = new int[arrayLength]; //make the array
		List<Integer> arrOutput = Collections.synchronizedList(new ArrayList<Integer>()); 
		ArrayList<Thread> threads = new ArrayList<Thread>();
		
		System.out.println("Starting array is: ");
		for(int i=0;i<arrInput.length;i++) { //fill the array with values. These are just garbage random values, in a real use case obviously skip this step.
			int num = rnd.nextInt(maxIndexValue)+1;
			arrInput[i] = num;
			System.out.print(num + " ");
		}
		
		System.out.println("\n\n"+"Preparing sort");
		for(final int number : arrInput) { //create threads and add them to a list
	
			Thread thread = new Thread(() -> {	
				try {Thread.sleep((number*indexTimeDelay));} catch (Exception e) {e.printStackTrace();}
				arrOutput.add(number);
			});
			threads.add(thread);
		}
		
		System.out.print("Sorting");
		for(Thread thread : threads) { //start threads, running the sort
			thread.start();
		}
		
		int arrOutputLength = 0;
		while(arrOutput.size()!=arrayLength && !Thread.interrupted()) { //wait for array to be sorted
			Thread.sleep(100);
			if(arrOutput.size()!=arrOutputLength) {
				System.out.print(".");
				arrOutputLength = arrOutput.size();
			}
		}
		
		System.out.println("\n\nComplete! Sorted array is");
		for(int i=0;i<arrOutput.size();i++) { //print the sorted array
			System.out.print(arrOutput.get(i) + " ");
		}

		System.out.println("\n\nVerifying sort is correct");
		if(arrOutput.stream().sorted().collect(Collectors.toList()).equals(arrOutput)) {
			System.out.println("Success!");
		} else {
			System.err.println("Failed. :(");
		}
		
	}
	
}
