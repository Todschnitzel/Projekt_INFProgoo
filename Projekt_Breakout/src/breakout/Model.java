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
		if ((Y - by <= 0 && by < 0)) {
			by = -by;
		}

	}

	private void getBallVelocity() {
		by = 4.0;
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

	/**
	 * Das erste Level sind 4 Reihen, so vielen Bricks wie möglich. Erstellt ein
	 * Array, in dem die erste Spalte die X und die zweite die Y Position der Bricks
	 * ist.
	 * 
	 * @param brickWidth
	 *            Die Breite der Bricks.
	 * @param brickHeight
	 *            Die Höhe der Bricks.
	 * @param sepration
	 *            Der Abstand zwischen den Bricks.
	 * @return Gibt das Array zurück.
	 */
	public double[][] levelOne(double brickWidth, double brickHeight, double seperation) {
		// Berechnet die Anzahl an Bricks in der Reihe.

		double columns = WIDTH / (brickWidth + seperation);
		double[][] pos = new double[(int) columns * 4 + 4][2];
		
		int counter = 0;
		// X WERTE
		for (int i = 0; i < pos.length; i++) {
			if (counter >= columns) {
				counter = 0;
			}
			if (counter < columns) {
				if (counter > 0) {
					pos[i][0] = pos[counter - 1][0] + (brickWidth + seperation);
				}

			}
			counter++;
		}
		counter = 0;
		// Y WERTE
		for (int j = 0; j < pos.length; j++) {
			if(counter >= 4) {
				counter = 0;
			}
			if(counter < 4) {
				if(counter > 0) {
				pos[j][1] = pos[counter - 1][1] + (brickHeight + seperation);
				}
			}
			counter++;
		}
		
		return pos;
	}
}
