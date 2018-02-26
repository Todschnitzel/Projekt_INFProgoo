
public class Model {
	private static int WIDTH;
	private static int HEIGHT;
	
	public static double ballPositionX() {
		 double x = WIDTH / 2 - 10;
		 return x;
		
	}
	public static double ballPositionY() {
		double y = HEIGHT / 2 - 10;
		return y;
	}
	public static void setupGame(int width, int height) {
		WIDTH = width;
		HEIGHT = height;
	}
}
