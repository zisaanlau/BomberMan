import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.ArrayList;

public class Monster {

	private int RectX;
	private int RectY;
	private Hero myhero;
	private World myWorld;
	private int grid = 50;
	private int side = 30;
	private int counter = 0;
	private int moveXby = 5;
	private int moveYby = 5;

	private int type;

	private ArrayList<Walls> WI;
	private ArrayList<Walls> WB;
	private ArrayList<Bombs> Bombs;

	public Monster(Hero myhero, World myWorld, ArrayList<Walls> WI, ArrayList<Walls> WB, int type) {
		this.myhero = myhero;
		this.myWorld = myWorld;
		int x = -10;
		int y = -10;
		this.type = type;
		this.WI = WI;
		this.WB = WB;

		this.RectX = (x * grid) + grid / 2;
		this.RectY = (y * grid) + grid / 2;
	}

	/*
	 * Function that draws the monster on our screen
	 * 
	 * @param g2 - graphics object on which to draw
	 */
	public void drawOn(Graphics2D g2) {
		Graphics2D g = (Graphics2D) g2;
		g.setColor(Color.RED);
		Rectangle myMonster = new Rectangle((int) RectX, (int) RectY, 20, 30);
		g.fill(myMonster);
	}

	/*
	 * Function that sets the location of the monster initially.
	 * 
	 * @param x - coordinate placement
	 * 		y - coordinate placement
	 */
	public void set(int x, int y) {
		this.RectX = (x * grid) + grid / 2 + 10;
		this.RectY = (y * grid) + grid / 2 + 10;
	}

	/*
	 * Function that returns the 'x' position of the monster.
	 */
	public int getX() {
		return RectX;
	}

	/*
	 * Function that returns the 'y' position of the monster.
	 */
	public int getY() {
		return RectY;
	}

	/*
	 * Function that resets the monster based on pixels rather than prior coordinate system.
	 * 
	 * @param x - pixel coordinate placement
	 * 		y - pixel coordinate placement
	 */
	public void stuck(int x, int y) {
		this.RectX = x;
		this.RectY = y;
	}

	/*
	 * Function that defines the movement of the monster.
	 * 
	 * @param x - change of speed in x direction
	 * 		y - change of speed in y direction
	 * 		direction - the direction (-1 or 1)
	 */
	public void move(int x, int y, int direction) {
		moveYby = moveYby * direction;
		moveXby = moveXby * direction;
		RectY = RectY + moveYby * y;
		RectX = RectX + moveXby * x;
	}

	public Rectangle2D getBounds2D() {
		return new Rectangle(RectX, RectY, side, side);
	}

	/*
	 * Function that checks for contact with walls of both kinds.
	 */
	public boolean checkContact() {
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

	public Rectangle getRect() {
		return new Rectangle(RectX, RectY, 20, 30);
	}
	
	/*
	 * Function that deals with the death of a monster.
	 */
	public void die() {
		this.RectX = -50;
		this.RectY = -50;
	}

	public void setHero(Hero hero) {
		this.myhero = hero;
	}

	/*
	 * Checks to see if the monster kills the hero by colliding with the hero.
	 */
	public boolean checkHero() {
		try {
			if (myhero.getBounds2D().intersects(this.getRect())) {
				myWorld.retry();
				return true;
			}
		} catch (IOException exception) {
			throw new RuntimeException("failed checking hero vs monster");
		}
		return false;
	}

	public void disappear() {
		this.side = 0;
	}

	public int getType() {
		return this.type;
	}
	
	public void setBomb(ArrayList<Bombs> Bombs) {
		this.Bombs = Bombs;	
	}
}
