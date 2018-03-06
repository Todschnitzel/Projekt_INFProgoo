package breakout;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.lang.reflect.Array;

import javax.swing.JButton;

import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;

public class DesktopView extends GraphicsProgram {
	/* Fenstergröße */
	private static final int APPLICATION_WIDTH = 400;
	private static final int APPLICATION_HEIGTH = 600;
	/* Objekte des Spiels */
	private GObject ball;
	private GRect paddle;

	/* Modell */
	private Model model;
	/* Objekt Variablen */
	private static final int PADDLE_WIDTH = 60;
	private static final int PADDLE_HEIGHT = 10;
	private static final int PADDLE_Y_OFFSET = 30;

	private static final int BALL_RADIUS = 10;

	private static final double BRICK_WIDTH = 40;
	private static final double BRICK_HEIGHT = 10;
	private static final double SEPERATION = 5;
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
		paddle.setColor(Color.GREEN);
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

	public void run() {
		resize(400, 600);
		model = new Model(APPLICATION_WIDTH, APPLICATION_HEIGTH, BALL_RADIUS);
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
			if (ball.getY() > APPLICATION_HEIGTH || ACTUAL_SCORE == model.getScore()) {
				add(new GLabel("Your score is " + ACTUAL_SCORE, 10, 10));
				break;
			}
			pause(10);
		}
		// restartScreen();
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
		model = new Model(APPLICATION_WIDTH, APPLICATION_HEIGTH, BALL_RADIUS);
		add(new JButton("Restart"), APPLICATION_HEIGTH / 2, APPLICATION_WIDTH / 2);
		// add(new JButton("EXIT"), CENTER);
		addActionListeners();
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
