package breakout;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.JButton;

import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;
import de.cau.infprogoo.lighthouse.LighthouseDisplay;

public class LightHouseView extends GraphicsProgram {
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

	private static final double BRICK_WIDTH = 2;
	private static final double BRICK_HEIGHT = 1;
	private static final double SEPERATION = 1;
	private static int ACTUAL_SCORE = 0;
	private RandomGenerator rgen = new RandomGenerator();
	/**
	 * Zeichnet ein Level.
	 * @param levelOne Level zu zeichnen.
	 */
	private void drawBricks(double[][] levelOne) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * Zeichnet den Ball.
	 */
	private void drawBall() {
		
	}
	private void drawPaddle() {
		double x = getWidth() / 2 - PADDLE_WIDTH / 2;
		double y = getHeight() - PADDLE_Y_OFFSET - PADDLE_HEIGHT;
		paddle = new GRect(x, y, PADDLE_WIDTH, PADDLE_HEIGHT);
		paddle.setFilled(true);
		add(paddle);
		addMouseListeners();
	}
	
	
	/*------------------- CONTROLLER------------------ */

	public void run() {
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
			if (ball.getY() > APPLICATION_HEIGHT || ACTUAL_SCORE == model.getScore()) {
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
		model = new Model(APPLICATION_WIDTH, APPLICATION_HEIGHT, BALL_RADIUS);
		add(new JButton("Restart"), APPLICATION_HEIGHT / 2, APPLICATION_WIDTH / 2);
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