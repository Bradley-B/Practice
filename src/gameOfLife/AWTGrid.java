package gameOfLife;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.SwingUtilities;

/**
 * work in progress
 * @author Bradley
 *
 */
public class AWTGrid extends Frame {

	public float boxHeight;
	public float boxWidth;
	public int screenWidth;
	public int screenHeight;
	public boolean setup = true;

	private static final long serialVersionUID = 1L;

	ArrayList<ArrayList<Rectangle2D>> gridPieces = new ArrayList<ArrayList<Rectangle2D>>();
	ArrayList<Rectangle2D> filledShapes = new ArrayList<Rectangle2D>();

	public AWTGrid() {
		super("Graphics Testing");
		setup();
	}

	public void setup() {

		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		screenWidth = gd.getDisplayMode().getWidth();
		screenHeight = gd.getDisplayMode().getHeight();
		this.setSize(screenWidth, screenHeight);

		this.setExtendedState(this.getExtendedState() | Frame.MAXIMIZED_BOTH);
		this.setResizable(false);
		setVisible(true);

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				dispose();
			}
		});

		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				
				if(SwingUtilities.isLeftMouseButton(me)) {
					processClickToRectangle(gridPieces, me.getPoint());
				} else if(SwingUtilities.isRightMouseButton(me)) {
					
				}
			}
		});
	}

	
	public void processClickToRectangle(ArrayList<ArrayList<Rectangle2D>> grid, Point point) {
		int rectXCord = (int)Math.floor(point.getX()/boxWidth);
		int rectYCord = (int)Math.floor(point.getY()/boxHeight);
		Rectangle2D s = (grid.get(rectXCord).get(rectYCord));
			
		if(filledShapes.contains(s)) {
			filledShapes.remove(s);
		} else {
			filledShapes.add(s);
		}

		repaint();
	}

	@Override
	public void paint(Graphics g) {

		Graphics2D g2 = (Graphics2D) g;
		g2.setPaint(Color.BLACK);

		paintGrid(g2);

		g2.setPaint(new Color(13, 126, 124));
		
		for(Shape s : filledShapes) {
			g2.fill(s);
		}

	}

	public void paintGrid(Graphics2D g2) {

		boxWidth = (float) (screenWidth/(16*4)); //38.4
		boxHeight = (float) (screenHeight/(9*4)); //21.6

		ArrayList<ArrayList<Rectangle2D>> gridPieces = new ArrayList<ArrayList<Rectangle2D>>();

		for(float x=0;x<this.getWidth()/boxWidth;x++) {
			ArrayList<Rectangle2D> yArr = new ArrayList<Rectangle2D>();
			for(float y=0;y<this.getHeight()/boxHeight;y++) {
				Rectangle2D s = new Rectangle.Float(x*boxWidth, y*boxHeight, boxWidth, boxHeight);
				yArr.add(s);
				g2.draw(s);
			}
			gridPieces.add(yArr);
		}

		this.gridPieces = gridPieces;
	}

	public static void main(String[] args) {
		AWTGrid grid = new AWTGrid();
		
	}

}
