import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class information {
	private Rectangle information;

	private int RectX = 700;
	private int RectY = 780;
	private int grid = 50;
	private int width = 100;
	private int height = 50;

	private int lives;
	public double time;

	private World game;

	public information(int lives, double time, World game) {
		this.lives = lives;
		this.game = game;
		this.time = time;
	}

	public Rectangle getRect() {
		return new Rectangle(RectX, RectY, width, height);
	}

	/*
	 * This function is used to render the lives and time of the player and game.
	 * 
	 * @param g2 - The graphics object on which we draw
	 */
	public void drawOn(Graphics2D g2) {
		Graphics2D g = (Graphics2D) g2;
		g.setColor(Color.magenta);
		g.drawString("Life: " + lives, 700, 830);
		g.drawString("Time: " + time, 500, 830);
	}
}
