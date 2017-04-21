package time;

import java.io.IOException;
import java.util.Random;

/**
 * Demonstrates keeping track of time to execute a thread once every x ms.
 * Will skip "ticks" if execution time is greater than alloted time between each individual execution.
 * 
 * @author Bradley
 *
 */
public class MinecraftServerLagSimulator {
	public static void main(String[] args) throws IOException, InterruptedException{
		long startTime = System.currentTimeMillis();
		int iteration = 0;
		int timeBetweenTicks = 500; //counts in milliseconds
		long savedMs = System.currentTimeMillis();
		System.err.println("initializing with ticks every "+timeBetweenTicks+"ms");
		
		while(!Thread.interrupted()) { 
			
			long currentMs = System.currentTimeMillis();
			System.out.println("time between executions: "+(currentMs-savedMs));
			savedMs = currentMs;
			
			Random rnd = new Random();
			Thread.sleep(rnd.nextInt(500)+100);
			
			iteration++;
			iteration+=adequateSleep(startTime, iteration, timeBetweenTicks);
		}
		
		
	}
	
	public static int adequateSleep(long startTime, int iteration, int timeBetweenTicks) {
		long theoreticalCurrentTime = startTime+(iteration*timeBetweenTicks);
		long excessTime = theoreticalCurrentTime-System.currentTimeMillis();
		
		if(excessTime>0) {
			try {Thread.sleep(excessTime);} catch (InterruptedException e) {}
			return 0;
		} else {
			int ticksToSkip = (int) Math.floor(-excessTime/timeBetweenTicks) + 1;
			long adjTheoreticalCurrentTime = startTime+((iteration+ticksToSkip)*timeBetweenTicks);
			
			try {Thread.sleep(adjTheoreticalCurrentTime-System.currentTimeMillis());} catch (InterruptedException e) {}
			
			System.out.println("Can't keep up! Did the system time change, or is the server overloaded? Running "+(-excessTime)+"ms behind, skipping "+(ticksToSkip)+" tick(s)");
			return ticksToSkip;
		}
	}
}
