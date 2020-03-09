import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

public class LevelListener implements KeyListener {

	private World game;
	private String Level;

	public LevelListener(World game, String Level) {
		this.game = game;
		this.Level = Level;
	}

	/*
	 * Function that waits for the user to press 'U', 'D', or 'P' for Up level, Down level, and
	 * pause game.
	 * 
	 * @param e - the keyEvent object that is passed through the function
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_U) {
			if (this.Level == "Level1") {
				try {
					this.game.ChangeLevel("Level2");
				} catch (IOException exception) {
					throw new RuntimeException("Failed Level Load");
				}
			} else if (this.Level == "Level2") {
				try {
					this.game.ChangeLevel("Level3");
				} catch (IOException exception) {
					throw new RuntimeException("Failed Level Load");
				}
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_D) {
			if (this.Level == "Level3") {
				try {
					this.game.ChangeLevel("Level2");
				} catch (IOException exception) {
					throw new RuntimeException("Failed Level Load");
				}
			} else if (this.Level == "Level2") {
				try {
					this.game.ChangeLevel("Level1");
				} catch (IOException exception) {
					throw new RuntimeException("Failed Level Load");
				}
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_P) {
			game.pause();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
}
