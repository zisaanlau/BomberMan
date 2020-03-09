import java.awt.Graphics2D;
import java.awt.Rectangle;

/*
 * Interface used to give base functions for all powerups in our game.
 */
public interface PowerUps {
	public boolean getPowerup();

	public void set(int x, int y);

	public Rectangle getRect();

	public void drawOn(Graphics2D g2);

	public void setHero(Hero hero);

}
