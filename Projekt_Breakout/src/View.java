
import acm.graphics.GRect;
import acm.program.GraphicsProgram;

public class View extends GraphicsProgram {
	
	public void drawBall() {
		double x = Model.ballPositionX();
		double y = Model.ballPositionY();
		GRect ball = new GRect(x,y,10,10);
		ball.setFilled(true);
		add(ball);
	}

}
