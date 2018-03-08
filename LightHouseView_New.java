package breakout;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.lang.reflect.Array;

import javax.swing.JButton;

import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;
import de.cau.infprogoo.lighthouse.LighthouseDisplay;

public class LightHouseView_New extends GraphicsProgram {
	private LighthouseDisplay display = new LighthouseDisplay("Todschnitzel", "API-TOK_iFNS-m9w4-g6zN-0jNd-g3tJ");
	/* Fenstergröße */
	private static final int APPLICATION_WIDTH = 28;
	private static final int APPLICATION_HEIGHT = 14;
	/* Objekte des Spiels */
	private GObject ball;
	private GRect paddle;

	/* Modell */
	private Model model;
	/* Objekt Variablen */
	private static final int PADDLE_WIDTH = 3;
	private static final int PADDLE_HEIGHT = 1;
	private static final int PADDLE_Y_OFFSET = 0;

	private static final int BALL_RADIUS = 1;

	private static final double BRICK_WIDTH = 1;
	private static final double BRICK_HEIGHT = 1;
	private static final double SEPERATION = 1;
	private static int ACTUAL_SCORE = 0;
	private RandomGenerator rgen = new RandomGenerator();

	/**
	 * Zeichnet einen Ball.
	 * 
	 * @return Returns a Ball Objekt.
	 */
	private void drawBall() {
		double x = model.getX();
		double y = model.getY();
		GRect ballFirst = new GRect(x, y, BALL_RADIUS, BALL_RADIUS);
		ballFirst.setFilled(true);
		ball = ballFirst;
		add(ball);
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

	public void drawBricks(double[][] level) {

		for (int i = 0; i < level.length; i++) {
			GRect brick = new GRect(level[i][0], level[i][1] + 30, BRICK_WIDTH, BRICK_HEIGHT);
			brick.setFilled(true);
			Color rdm = rgen.nextColor();
			brick.setFillColor(rdm);
			brick.setColor(rdm);
			add(brick);
		}
	}

	/*------------------- CONTROLLER------------------ */
	private void sendToDisplay() {
		byte[] data = new byte[14 * 28 * 3];
		// Bricks
		double bricks[][] = model.levelOne(BRICK_WIDTH, BRICK_HEIGHT, SEPERATION);

		for (int i = 0; i < bricks.length; i++) {
			// Koordinaten in der DesktopView
			int x = (int) bricks[i][0];
			int y = (int) bricks[i][1];
			// Koordinate im Lighthouse
			int z = (28 * y) - (28 - x);
			if (getElementAt(x, y) != null) {
				data[z] = (byte) 0;
				data[z + 1] = (byte) 255;
				data[z + 2] = (byte) 0;
			}
		}
		// Ball
		int a = (28 * (int) model.getY()) - (28 - (int) model.getX());
		data[a] = (byte) 0;
		data[a + 1] = (byte) 0;
		data[a + 2] = (byte) 255;

		// Paddle
		int b = (28 * (int) paddle.getY()) - (28 - (int) paddle.getX());
		for (int i = 0; i < 2; i += 3) {
			data[b + i] = (byte) 255;
			data[b + i + 1] = (byte) 255;
			data[b + i + 2] = (byte) 0;
		}

		// Send data to the display
		try {
		    // This array contains for every window (14 rows, 28 columns) three
		    // bytes that define the red, green, and blue component of the color
		    // to be shown in that window. See documentation of LighthouseDisplay's
		    // send(...) method.
		 
		    display.send(data);
		} catch (IOException e) {
		    System.out.println("Connection failed: " + e.getMessage());
		    e.printStackTrace();
		}
	}

	public void run() {
		resize(APPLICATION_WIDTH, APPLICATION_HEIGHT);
		// Try connecting to the display
		try {
		    display.connect();
		} catch (Exception e) {
		    System.out.println("Connection failed: " + e.getMessage());
		    e.printStackTrace();
		}
		model = new Model(APPLICATION_WIDTH, APPLICATION_HEIGHT, BALL_RADIUS);
		setupGame();
	}

	/**
	 * Startet das Spiel und lässt und spielen.
	 */
	private void playGame() {
		waitForClick();
		while (true) {

			GObject kollision = getCollidingObject();
			if (kollision == ball) {
				kollision = null;
			}
			println(kollision);
			println(ball);
			if (kollision == paddle) {
				model.paddleKollision();

			} else if (kollision != null) {
				remove(kollision);
				model.paddleKollision();
				ACTUAL_SCORE += 10;

			}
			model.moveBall();
			ball.setLocation(model.getX(), model.getY());
			sendToDisplay();
			if (ball.getY() > APPLICATION_HEIGHT || ACTUAL_SCORE == model.getScore()) {
				add(new GLabel("Your score is " + ACTUAL_SCORE, 10, 10));
				break;
			}
			pause(1000);
		}
		waitForClick();
		restartScreen();
		setupGame();
	}

	/**
	 * Erschafft das Level.
	 */
	private void setupGame() {
		drawBricks(model.levelOne(BRICK_WIDTH, BRICK_HEIGHT, SEPERATION));
		drawBall();
		drawPaddle();
		playGame();

	}

	/**
	 * Gibt uns Buttons um das Spiel neuzustarten (WIP).
	 */
	private void restartScreen() {
		model = new Model(APPLICATION_WIDTH, APPLICATION_HEIGHT, BALL_RADIUS);
		ACTUAL_SCORE = 0;
		this.removeAll();
	}

	/**
	 * Überprüft ob eine Kollision vorliegt.
	 * 
	 * @return Gibt das kollidierende Objekt zurück, null wenn keine Kollision
	 *         vorliegt.
	 */
	private GObject getCollidingObject() {
		// Oben links
		if ((getElementAt(model.getX(), model.getY() - BALL_RADIUS / 2)) != null) {
			return getElementAt(model.getX(), model.getY() - BALL_RADIUS / 2);
			// Oben rechts * 2
		} else if (getElementAt((model.getX() + BALL_RADIUS), model.getY() - BALL_RADIUS / 2) != null) {
			return getElementAt(model.getX() + BALL_RADIUS, model.getY() - BALL_RADIUS / 2);
			// Unten links * 2
		} else if (getElementAt(model.getX(), (model.getY() + BALL_RADIUS * 2)) != null) {
			return getElementAt(model.getX(), model.getY() + BALL_RADIUS * 2);
			// Unten rechts * 2
		} else if (getElementAt((model.getX() + BALL_RADIUS), (model.getY() + BALL_RADIUS * 2)) != null) {
			return getElementAt(model.getX() + BALL_RADIUS, model.getY() + BALL_RADIUS * 2);
		} else {
			return null;
		}
	}

	/*-----Steuerung-----*/

	public void mouseMoved(MouseEvent e) {

		if ((e.getX() < getWidth() - PADDLE_WIDTH / 2) && (e.getX() > PADDLE_WIDTH / 2)) {
			paddle.setLocation(e.getX() - PADDLE_WIDTH / 2, getHeight() - PADDLE_Y_OFFSET - PADDLE_HEIGHT);
		}
	}

	public void actionPerformed(ActionEvent a) {
		if (a.getActionCommand().equals("Restart")) {
			run();
		}
		if (a.getActionCommand().equals("Exit")) {
			exit();
		}
	}

}
