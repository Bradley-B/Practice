package sort;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
public class SleepSort {
	
	/**
	 * NOTE: THIS SORT <u>WILL</u> SEND YOU TO HELL FOR BEING A TERRIBLE PERSON.
	 * Currently does not work, sometimes a number doesn't get added to arrOutput.
	 * 
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		int arrayLength = 10;
		int maxIndexValue = 10;
		int indexTimeDelay = 1;
		
		Random rnd = new Random();
		int[] arrInput = new int[arrayLength]; //make the array
		ArrayList<Integer> arrOutput = new ArrayList<Integer>(); 
		ArrayList<Thread> threads = new ArrayList<Thread>();
		
		System.out.println("Starting array is... ");
		for(int i=0;i<arrInput.length;i++) { //fill the array
			int num = 0;
			while(num==0) {
				num = rnd.nextInt(maxIndexValue);
			}
			arrInput[i] = num;
			System.out.print(num + " ");
		}
		
		System.out.println("\n\n"+"Preparing sort...");
		for(final int number : arrInput) { //setup array sorting
	
			Thread thread = new Thread(() -> {	
				System.out.println("starting wait for thread with value "+number);	
				try {Thread.sleep((number*indexTimeDelay));} catch (Exception e) {e.printStackTrace();}
				
				
				//System.out.println("added value to array "+number);
				arrOutput.add(number);
			});
			threads.add(thread);
		}
		
		System.out.println("Sorting...");
		for(Thread thread : threads) { //start threads, run sort
			thread.start();
		}
		
		//Thread.sleep((arrayLength*indexTimeDelay)+100); //allow time for sorting array
		
		while(arrOutput.size()!=arrayLength) { //wait for array to be sorted
			Thread.sleep(maxIndexValue*indexTimeDelay);
			
			System.out.print("array value: ");  //print current array
			for(int i : arrOutput) {
				System.out.print(arrOutput.get(i)+" ");
			}
			System.out.println();
				
		}
		
		System.out.println("\nSorted array is... ");
		for(int i=0;i<arrOutput.size();i++) { //print the sorted array
			System.out.print(arrOutput.get(i) + " ");
		}
	}

}
