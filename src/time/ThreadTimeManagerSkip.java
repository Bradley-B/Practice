package time;

/**
 * Demonstrates keeping track of time to execute a thread once every x ms.
 * Will skip "ticks" if execution time is greater than alloted time between each individual execution.
 * 
 * To run: place in a thread, call start(), then run() every execution of the thread's main loop. call stop() to stop.  
 * 
 * @author Bradley Boxer
 *
 */
public class ThreadTimeManagerSkip {
	private long startTime = 0;
	private int iteration = 0;
	private int timeBetweenTicks = 0;
	private boolean stopped = true;
	private int ticksSkipped = 0;
	private boolean silence = false;
	private boolean debug = false;
	
	public ThreadTimeManagerSkip(int timeBetweenTicks) {
		this.timeBetweenTicks = timeBetweenTicks;
	}

	public int getIteration() {
		return iteration;
	}
	
	public int getTotalTicksSkipped() {
		return ticksSkipped;
	}
	
	public void start() {
		startTime = System.currentTimeMillis();
		stopped = false;
	}
	
	public void run() {
		if(!stopped) {
			iteration+=getIterationCount(startTime, iteration, timeBetweenTicks);
		}
	}

	public void stop() {
		stopped = true;
	}
	
	public void disableDebug() {
		debug = false;
	}
	
	/**
	 * 'debug' prints the amount of time waited after each "tick"
	 */
	public void enableDebug() {
		debug = true;
	}
	
	/**
	 * 'silence' silences all print statements
	 */
	public void silence() {
		silence = true;
	}
	
	
	private int getIterationCount(long startTime, int iteration, int timeBetweenTicks) {
		long theoreticalCurrentTime = startTime+(iteration*timeBetweenTicks);
		long excessTime = theoreticalCurrentTime-System.currentTimeMillis();

		if(debug && !silence) {
			System.out.println("waiting "+excessTime+"ms");
		}
		
		if(excessTime>0) {
			try {Thread.sleep(excessTime);} catch (InterruptedException e) {e.printStackTrace();}
			return 1;
		} else {
			int ticksToSkip = (int) Math.floor(-excessTime/timeBetweenTicks) + 1;
			long adjTheoreticalCurrentTime = startTime+((iteration+ticksToSkip)*timeBetweenTicks);

			if(!silence) {
				System.out.println("Can't keep up! Did the system time change, or is the server overloaded? Running "+(-excessTime)+"ms behind, skipping "+(ticksToSkip)+" tick(s)");	
			}
			
			try {Thread.sleep(adjTheoreticalCurrentTime-System.currentTimeMillis());} catch (InterruptedException e) {e.printStackTrace();}

			return ticksToSkip+1;
		}
	}
}
