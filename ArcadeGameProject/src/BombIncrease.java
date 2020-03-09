import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class BombIncrease implements PowerUps {
	private Rectangle moreBomb;
	private Hero myHero;
	private int RectX = -10;
	private int RectY = -10;
	private int grid = 50;
	private int side = 25;
	private WorldComponent myComponent;

	public BombIncrease(Hero myHero, WorldComponent myComponent) {
		this.moreBomb = moreBomb;
		this.myHero = myHero;
		this.myComponent = myComponent;
	}

	/*
	 * Function that draws the powerup pickup for increased bomb inventory.
	 * 
	 * @param g2 - graphics object on which to draw
	 */
	public void drawOn(Graphics2D g2) {
		Graphics2D g = (Graphics2D) g2;
		g.setColor(Color.pink);
		moreBomb = new Rectangle(RectX, RectY, side, side);
		g.fill(moreBomb);
	}

	public Rectangle getRect() {
		return new Rectangle(RectX, RectY, side, side);
	}

	public void setHero(Hero myHero) {
		this.myHero = myHero;
	}

	/*
	 * Function that sets the location of the powerup
	 * 
	 * @param x - coordinate placement
	 * 		y - coordinate placement
	 */
	public void set(int x, int y) {
		this.RectX = (x * grid) + grid / 2 + 10;
		this.RectY = (y * grid) + grid / 2 + 10;
	}

	/*
	 * Function that checks for the hero to pickup or collide with the powerup.
	 */
	public boolean getPowerup() {
		if (myHero.getBounds2D().intersects(this.getRect())) {
			myComponent.newBomb();
			side = 0;
			return true;
		}
		return false;
	}
}
