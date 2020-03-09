import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class Hero {

	private Rectangle rect;
	private int RectX;
	private int RectY;
	private int grid = 50;

	private Color color;
	private int side = 30;
	private ArrayList<Walls> WI;
	private ArrayList<Walls> WB;
	private ArrayList<Bombs> Bombs;
	private char bombPlacement;
	private World game;

	public Hero(ArrayList<Walls> WI, ArrayList<Walls> WB, ArrayList<Bombs> Bombs, World mygame) {
		rect = new Rectangle();
		rect.height = this.side;
		rect.width = this.side;
		int x = -10;
		int y = -10;
		this.RectX = (x * grid) + grid / 2;
		this.RectY = (y * grid) + grid / 2;
		this.color = Color.black;
		this.WI = WI;
		this.WB = WB;
		this.Bombs = Bombs;
		this.bombPlacement = 'u';
		this.game = mygame;
		rect = new Rectangle(this.RectX, this.RectY, side, side);
	}
	/*
	 * This function draws our hero/bomberman onto our screen.
	 * 
	 * @param g2 - graphics object on which to draw
	 * 
	 */
	public void drawOn(Graphics2D g2) {
		Graphics2D g = (Graphics2D) g2;
		g.setColor(Color.blue);
		rect = new Rectangle(RectX, RectY, side, side);
		g.fill(rect);
	}
	
	/*
	 * Function that sets the location of the hero
	 * 
	 * @param x - coordinate placement
	 * 		y - coordinate placement
	 */
	public void set(int x, int y) {
		this.RectX = (x * grid) + grid / 2 + 10;
		this.RectY = (y * grid) + grid / 2 + 10;
	}

	/*
	 * Function resets the hero based on pixels and not the grid system used prior to getting stuck.
	 * 
	 * @param x - pixel coordinate placement
	 * 		y - pixel coordinate placement
	 */
	public void stuck(int x, int y) {
		this.RectX = x;
		this.RectY = y;
	}

	public char whereToPlaceBomb() {
		return bombPlacement;
	}

	/*
	 * The following functions are for the movement of our hero/bomberman.
	 */
	public void moveRight() {
		if (!game.isPaused) {
			RectX = RectX + 5;
			bombPlacement = 'r';
		}
	}

	public void moveLeft() {
		if (!game.isPaused) {
			RectX = RectX - 5;
			bombPlacement = 'l';
		}
	}

	public void moveUp() {
		if (!game.isPaused) {
			RectY = RectY - 5;
			bombPlacement = 'u';
		}
	}

	public void moveDown() {
		if (!game.isPaused) {
			RectY = RectY + 5;
			bombPlacement = 'd';
		}
	}

	/*
	 * Function used to get 'x' position of hero
	 */
	public int getX() {
		return RectX;
	}
	
	/*
	 * Function used to get 'y' position of hero
	 */
	public int getY() {
		return RectY;
	}

	public void setBomb(ArrayList<Bombs> Bombs) {
		this.Bombs = Bombs;
	}

	public Rectangle2D getBounds2D() {
		return new Rectangle(RectX, RectY, side, side);
	}

	/*
	 * Function used to detect collision with bombs, concrete walls, and breakable walls.
	 */
	public boolean checkContact() {
		for (Bombs Bomb : Bombs) {
			if (Bomb.getRectangle().intersects(this.getBounds2D())) {
				return true;
			}
		}
		for (Walls walli : WI) {
			if (walli.getRect().intersects(this.getBounds2D())) {
				return true;
			}
		}
		for (Walls wallb : WB) {
			if (wallb.getRect().intersects(this.getBounds2D())) {
				return true;
			}
		}
		return false;
	}
}
