package time;

import java.util.Random;

@Deprecated
public class TimeManagerTester {

	static long savedMs = 0;
	
	public static void main(String[] args) {
		ThreadTimeManagerRush tmgr = new ThreadTimeManagerRush(500);

		Random rnd = new Random();
		tmgr.start();
		savedMs = System.currentTimeMillis()-500;
		
		while(!Thread.interrupted()) {

			long currentMs = System.currentTimeMillis();
			System.out.println(currentMs-savedMs);
			savedMs = currentMs;
			
			try {Thread.sleep(rnd.nextInt(500)+200);} catch (InterruptedException e) {} //simulation of "load"

			tmgr.run();
			
			System.out.println(tmgr.getMissedMs());
		}

		tmgr.stop();

	}

}
