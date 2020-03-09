
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class BombListener implements KeyListener {

	private ArrayList<Bombs> Bombs;
	private Hero myHero;

	public BombListener(ArrayList<Bombs> Bombs) {
		this.Bombs = Bombs;
	}

	/*
	 * Function that waits for the user to press 'Z' to place a bomb.
	 * 
	 * @param e - the KeyEvent object that the function passes in
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_Z) {
			for (int i = 0; i < Bombs.size(); i++) {
				if (Bombs.get(i).CanReplace()) {
					Bombs.get(i).drop();
					break;
				}
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
}
