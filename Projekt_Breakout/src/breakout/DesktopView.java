package breakout;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

import acm.graphics.GObject;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;

public class DesktopView extends GraphicsProgram {
	/* Fenstergröße */
	private static final int APPLICATION_WIDTH = 400;
	private static final int APPLICATION_HEIGTH = 600;
	/* Objekte des Spiels */
	private GObject ball;
	private GRect paddle;
	private GObject kollision;

	/* Modell */
	private Model model;
	/* Objekt Variablen */
	private static final int PADDLE_WIDTH = 60;
	private static final int PADDLE_HEIGHT = 10;
	private static final int PADDLE_Y_OFFSET = 30;
	private static final int BALL_RADIUS = 10;

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
			model.moveBall();
			kollision = getCollidingObject();
			if (kollision == paddle) {
				// if(model.getY() >= APPLICATION_HEIGTH - PADDLE_Y_OFFSET - PADDLE_HEIGHT -
				// BALL_RADIUS*2 && model.getY() < APPLICATION_HEIGTH - PADDLE_Y_OFFSET -
				// PADDLE_HEIGHT - BALL_RADIUS*2 + 4)
				model.paddleKollision();
			}

			ball.setLocation(model.getX(), model.getY());
			if (ball.getY() > APPLICATION_HEIGTH) {
				break;
			}
			pause(10);
		}
		//restartScreen();
	}
	/**
	 * Erschafft das Level.
	 */
	private void setupGame() {
		
		drawBall();
		drawPaddle();
		playGame();
	}
	
	/**
	 * Gibt uns Buttons um das Spiel neuzustarten (WIP).
	 */
	private void restartScreen() {
		model = new Model(APPLICATION_WIDTH, APPLICATION_HEIGTH, BALL_RADIUS);
		add(new JButton("Restart"), APPLICATION_HEIGTH / 2, APPLICATION_WIDTH /2 );
		//add(new JButton("EXIT"), CENTER);
		addActionListeners();
	}
	
	/**
	 * Überprüft ob eine Kollision vorliegt.
	 * @return Gibt das kollidierende Objekt zurück, null wenn keine Kollision vorliegt.
	 */
	private GObject getCollidingObject() {

		if ((getElementAt(model.getX(), model.getY())) != null) {
			return getElementAt(model.getX(), model.getY());
		} else if (getElementAt((model.getX() + BALL_RADIUS * 2), model.getY()) != null) {
			return getElementAt(model.getX() + BALL_RADIUS * 2, model.getY());
		} else if (getElementAt(model.getX(), (model.getY() + BALL_RADIUS * 2)) != null) {
			return getElementAt(model.getX(), model.getY() + BALL_RADIUS * 2);
		} else if (getElementAt((model.getX() + BALL_RADIUS * 2), (model.getY() + BALL_RADIUS * 2)) != null) {
			return getElementAt(model.getX() + BALL_RADIUS * 2, model.getY() + BALL_RADIUS * 2);
		}
		// need to return null if there are no objects present
		else {
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
			setupGame();
			}
		if (a.getActionCommand().equals("Exit")) {
			exit();
		}
	}


}
