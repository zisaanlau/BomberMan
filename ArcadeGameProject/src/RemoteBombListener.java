import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class RemoteBombListener implements KeyListener {

	private RemoteBomb myRemote;
	private realRemote myReal;

	public RemoteBombListener(RemoteBomb remotebomb, realRemote realRemote) {
		this.myReal = realRemote;
		this.myRemote = remotebomb;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (myRemote.getPowerup() == true) {
			if (e.getKeyCode() == KeyEvent.VK_A) {
				if (myReal.CanReplace()) {
					myReal.drop();
				}
			}
		}
		if (myRemote.getPowerup() == true) {
			if (e.getKeyCode() == KeyEvent.VK_B) {
				this.myReal.explode();
				myReal.setEnd(this.myReal.getCount() + 10);
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}
}
