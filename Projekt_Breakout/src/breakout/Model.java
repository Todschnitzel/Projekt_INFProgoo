package breakout;

import acm.util.RandomGenerator;

public class Model {
	// Speichert die Größen der View.
	private static int WIDTH;
	private static int HEIGTH;
	private static int BALL_RADIUS;

	// Random Generator
	private RandomGenerator rgen;

	// Ball Beschleunigung
	private double by, bx;

	// Ball Koordinaten
	private double X, Y;

	public Model(int width, int height, int ballRadius) {
		WIDTH = width;
		HEIGTH = height;
		BALL_RADIUS = ballRadius;
		rgen = new RandomGenerator();
		X = WIDTH / 2 - ballRadius;
		Y = HEIGTH / 2 - ballRadius;
		getBallVelocity();
	}

	/**
	 * Bewegt die Ball Koordinaten um die Ball Beschleunigung. Außerdem wird die
	 * Kollision mit den Wänden behandelt.
	 */
	public void moveBall() {
		
		X += bx;
		Y += by;

		if ((X - bx <= 0 && bx < 0) || (X + bx >= (WIDTH - BALL_RADIUS * 2) && bx > 0)) {
			bx = -bx;
		}
		if ((Y - by <= 0 && by < 0 )) {
			by = -by;
		}

	}

	private void getBallVelocity() {
		by = -4.0;
		bx = rgen.nextDouble(1.0, 3.0);
		if (rgen.nextBoolean(0.5)) {
			bx = -bx;
		}

	}

	public double getX() {
		return X;
	}

	public double getY() {
		return Y;
	}

	public void paddleKollision() {

		by = -by;
		moveBall();
		
	}
}
