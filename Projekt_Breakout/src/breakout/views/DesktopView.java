package breakout.views;

import acm.graphics.GRect;
import acm.program.GraphicsProgram;
import breakout.models.Model;

public class DesktopView extends GraphicsProgram {
	
	public void drawBall() {
		double x = Model.ballPositionX();
		double y = Model.ballPositionY();
		GRect ball = new GRect(x,y,10,10);
		ball.setFilled(true);
		add(ball);
	}

}
