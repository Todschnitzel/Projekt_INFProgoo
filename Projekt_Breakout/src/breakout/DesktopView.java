package breakout;

import java.awt.event.MouseEvent;

import acm.graphics.GObject;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;

public class DesktopView extends GraphicsProgram {
	/* Objekte des Spiels */
	private GObject ball;
	public GRect paddle;

	/* Modell */
	private Model model;
	/* Objekt Variablen */
	private static final int PADDLE_WIDTH = 60;
	private static final int PADDLE_HEIGHT = 10;
	private static final int PADDLE_Y_OFFSET = 20;

	/**
	 * Zeichnet einen Ball.
	 * 
	 * @return Returns a Ball Objekt.
	 */
	private GObject drawBall() {
		double x = model.getX();
		double y = model.getY();
		GRect ball = new GRect(x, y, 10, 10);
		ball.setFilled(true);
		return ball;
	}
	/**
	 * Zeichnet ein Paddle.
	 */
	public void drawPaddle() {
		double x = getWidth() / 2 - PADDLE_WIDTH / 2;
		double y = getHeight() - PADDLE_Y_OFFSET - PADDLE_HEIGHT;
		paddle = new GRect(x, y, PADDLE_WIDTH, PADDLE_HEIGHT);
		paddle.setFilled(true);
		add(paddle);
		addMouseListeners();
	}

	/*------------------- CONTROLLER------------------ */

	public void run() {
		resize(400, 600);
		model = new Model(400, 600, 10);
		ball = drawBall();
		add(ball);
		drawPaddle();
		playGame();
	}

	private void playGame() {
		waitForClick();
		while (true) {
			model.moveBall();
			ball.setLocation(model.getX(), model.getY());
			pause(10);
		}
	}

	public void mouseMoved(MouseEvent e) {

		if ((e.getX() < getWidth() - PADDLE_WIDTH / 2) && (e.getX() > PADDLE_WIDTH / 2)) {
			paddle.setLocation(e.getX() - PADDLE_WIDTH / 2, getHeight() - PADDLE_Y_OFFSET - PADDLE_HEIGHT);
		}
	}
}
