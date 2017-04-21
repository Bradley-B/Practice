package tables;

import java.awt.AWTException;
import java.awt.Frame;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

/**
 * unfinished, broken
 * @author Bradley
 *
 */
public class KeyMash {

	static boolean on = true;
	static Robot robot;
	
	public static void enable() {
		on = true;
	}
	
	public static void disable() {
		on = false;
	}
	
	public static boolean getEnabledState() {
		return on;
	}
	
	public static void main(String[] args) {
		
		try {
			robot = new Robot();
		} catch (AWTException e1) {}
		
		Random rnd = new Random();
		Thread keyListen = new Thread(() -> {
			
			Frame f = new Frame();
			f.setVisible(true);
			f.setSize(200, 100);
			f.setFocusable(true);
			
			f.addKeyListener(new KeyListener() {

				@Override
				public void keyPressed(KeyEvent e) {
					enable();
				}

				@Override
				public void keyReleased(KeyEvent e) {
					disable();
				}

				@Override
				public void keyTyped(KeyEvent e) {}
				
			});
			f.requestFocus();
			
			
			while(getEnabledState()) {
				
				int keycode = 10;
				System.out.println("key event "+keycode);
				robot.keyPress(keycode);
				robot.keyRelease(keycode);
				
				try {
					Thread.sleep(1);
				} catch (Exception e) {}
			}
		});
		
		keyListen.start();
		
	}

}
