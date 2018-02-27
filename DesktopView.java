package breakout.views;

import java.awt.Color;
import java.awt.event.MouseEvent;

import acm.graphics.GObject;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;
import breakout.models.Model;

public class DesktopView extends GraphicsProgram {

	private static final int PADDLE_WIDTH = 60;
	private static final int PADDLE_HEIGHT = 10;
	private static final int PADDLE_Y_OFFSET = 20;
	private static final int NBRICKS_PER_ROW = 10;
	private static final int BRICK_SEP = 4;
	private static final int BRICK_HEIGHT = 8;
	private static final int BRICK_WIDTH = (400 - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW + 30;
	

	public void run() {
		this.resize(400,600);
		ball = drawBall();
		drawPaddel();
		drawBrick(0,50);
		add(ball);
		
		
	}
	public GObject ball;
	
	public GObject drawBall() {
		
		double x = Model.ballPositionX();
		double y = Model.ballPositionY();
		GRect ball = new GRect(x, y, 10, 10);
		ball.setFilled(true);
		return ball;
	}

	public GRect paddle;

	public void drawPaddel() {
		double x = getWidth() / 2 - PADDLE_WIDTH / 2;
		double y = getHeight() - PADDLE_Y_OFFSET - PADDLE_HEIGHT;
		paddle = new GRect(x, y, PADDLE_WIDTH, PADDLE_HEIGHT);
		paddle.setFilled(true);
		add(paddle);
		addMouseListeners();
	}

	public GRect brick; 
	
	private void drawBrick(double bx, double by) {
		
		for( int row = 0; row < NBRICKS_PER_ROW; row++) {
			
			for( int columns = 0; columns < NBRICKS_PER_ROW; columns++) {
			
				double x = bx - (NBRICKS_PER_ROW*BRICK_WIDTH)/2 -((NBRICKS_PER_ROW-1)*BRICK_SEP)/2 + columns*BRICK_WIDTH + columns * BRICK_SEP;
				double y = by + row * BRICK_HEIGHT + row * BRICK_SEP;
				
				brick = new GRect(x, y, BRICK_WIDTH, BRICK_HEIGHT);
				brick.setFilled(true);
				add(brick);
			
			if ( row < 2) {
				brick.setColor(Color.GREEN);
			}
			if ( row == 2 || row == 3) {
				brick.setColor(Color.BLUE);
			}
			if ( row == 4 || row == 5) {
				brick.setColor(Color.MAGENTA);
			}
			if ( row == 6 || row == 7) {
				brick.setColor(Color.RED);
			}
			if ( row == 8 || row == 9) {
				brick.setColor(Color.ORANGE);
			}
		}	
	
		}
	}
		public void mouseMoved(MouseEvent e) {
			
			if ((e.getX() < getWidth() - PADDLE_WIDTH/2) && (e.getX() > PADDLE_WIDTH/2)) {
				paddle.setLocation(e.getX() - PADDLE_WIDTH/2, getHeight() - PADDLE_Y_OFFSET - PADDLE_HEIGHT);
			}
	}
}

