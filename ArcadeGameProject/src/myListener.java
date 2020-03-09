import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class myListener implements KeyListener {

	private Hero myHero;
	private World game;
	int startX;
	int startY;

	public myListener(Hero myHero, World game) {
		this.myHero = myHero;
		this.game = game;
		startX = myHero.getX();
		startY = myHero.getY();
	}

	/*
	 * Function that waits for the user to press set of keys for movement of the hero.
	 * 
	 * @param e - KeyEvent object that is passed in to function
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			myHero.moveRight();
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			myHero.moveLeft();
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			myHero.moveDown();
		}
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			myHero.moveUp();
		}
		for (int i = 0; i < game.Monsters.size(); i++) {
			game.Monsters.get(i).checkHero();
		}
		if (!game.hero.checkContact()) {
			startX = myHero.getX();
			startY = myHero.getY();
		} else {
			myHero.stuck(startX, startY);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
}