import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class LargerRangeBomb implements PowerUps{

	private Rectangle RangeBomb;
	private Hero myHero;

	private int RectX = -10;
	private int RectY = -10;
	private int grid = 50;
	private int side = 25;
	private WorldComponent myComponent;

	public LargerRangeBomb(Hero myHero, WorldComponent myComponent) {
		this.RangeBomb = RangeBomb;
		this.myHero = myHero;
		this.myComponent = myComponent;
	}

	/*
	 * Function that draws the powerup pickup for larger ranged bombs.
	 * 
	 * @param g2 - graphics object on which to draw
	 */
	public void drawOn(Graphics2D g2) {
		Graphics2D g = (Graphics2D) g2;
		g.setColor(Color.WHITE);
		RangeBomb = new Rectangle(RectX, RectY, side, side);
		g.fill(RangeBomb);
	}

	public Rectangle getRect() {
		return new Rectangle(RectX, RectY, side, side);
	}

	/*
	 * Function that sets the location of the powerup.
	 * 
	 * @param x - coordinate placement
	 * 		y - coordinate placement
	 */
	public void set(int x, int y) {
		this.RectX = (x * grid) + grid / 2 + 10;
		this.RectY = (y * grid) + grid / 2 + 10;
	}

	/*
	 * Function that checks to see if the hero has collided with the powerup.
	 */
	public boolean getPowerup() {
		if (myHero.getBounds2D().intersects(this.getRect())) {
			myComponent.increaseRange();
			side = 0;
			return true;
		}
		return false;
	}

	public void setHero(Hero hero) {
		this.myHero = hero;
	}
}
