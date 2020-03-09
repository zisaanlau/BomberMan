import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JComponent;

public class WorldComponent extends JComponent implements Runnable {

	private ArrayList<Walls> WI;
	private Hero myhero;
	private ArrayList<Bombs> Bombs;
	ArrayList<Monster> Monsters;
	private ArrayList<Walls> WB;
	private int count = 0;
	private World myworld;
	private LargerRangeBomb rangeBomb;
	private double prob = Math.random();
	private int range;
	private BombIncrease moreBomb;
	private RemoteBomb remoteBomb;
	private realRemote realRemote;
	private ArrayList<PowerUps> powerup;
	private information info;

	public void sethero(Hero myhero) {
		this.myhero = myhero;
	}

	public void setWI(ArrayList<Walls> WI) {
		this.WI = WI;
	}

	public void setBombs(ArrayList<Bombs> Bombs) {
		this.Bombs = Bombs;
	}

	public void setWB(ArrayList<Walls> WB) {
		this.WB = WB;
	}

	public void setMonster(ArrayList<Monster> myMonster) {
		this.Monsters = myMonster;
	}

	public void setLargerRangeBomb(LargerRangeBomb rangeBomb) {
		this.rangeBomb = rangeBomb;
	}

	public void setBombIncrease(BombIncrease moreBomb) {
		this.moreBomb = moreBomb;
	}

	public void setPowerUps(ArrayList<PowerUps> power) {
		this.powerup = power;
	}

	public void setRemoteBomb(RemoteBomb remoteBomb) {
		this.remoteBomb = remoteBomb;
	}

	public void setRealRemote(realRemote myReal) {
		this.realRemote = myReal;
	}

	public void setInfo(information info) {
		this.info = info;
	}

	public int getCount() {
		return count;
	}

	public void newBomb() {
		Bombs myBomb = new Bombs(myhero, myworld, range);
		Bombs.add(myBomb);
	}

	public void increaseRange() {
		range = 200;
		for (int i = 0; i < Bombs.size(); i++) {
			this.Bombs.get(i).largerRange();
		}
	}

	public void setworld(World myworld) {
		this.myworld = myworld;
	}

	public WorldComponent() {
	}

	/*
	 * Function that draws everything in the world.
	 * 
	 * @param g - graphics object on which to draw
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.green);
		g2.fillRect(0, 0, 1100, 900);
		for (int i = 0; i < Monsters.size(); i++) {
			this.myworld.Monsters.get(i).checkHero();
		}
		myworld.CheckWin();
		for (int i = 0; i < Bombs.size(); i++) {
			Bombs.get(i).checkHero();
			Bombs.get(i).drawOn(g2);
		}
		myhero.drawOn(g2);
		rangeBomb.drawOn(g2);
		remoteBomb.drawOn(g2);
		remoteBomb.getPowerup();
		realRemote.drawOn(g2);
		info.drawOn(g2);
		for (int i = 0; i < Monsters.size(); i++) {
			Monsters.get(i).drawOn(g2);
		}
		for (int i = 0; i < this.powerup.size(); i++) {
			this.powerup.get(i).drawOn(g2);
			this.powerup.get(i).getPowerup();
		}
		for (int i = 0; i < WI.size(); i++) {
			WI.get(i).drawOn(g2, ' ');
		}
		for (int i = 0; i < WB.size(); i++) {
			WB.get(i).drawOn(g2, 'b');
		}
		if (count%50 == 0){
			info.time -= 1;	
		}
		if(info.time == 0) {
			try {
				myworld.retry();
			} catch (IOException exception) {
				throw new RuntimeException("Failed time kill");
			}
		}
	}

	/*
	 * Function for a constantly running thread.
	 */
	@Override
	public void run() {
		try {
			while (true) {
				for (int i = 0; i < Monsters.size(); i++) {
					if (!myworld.isPaused) {
						if (Monsters.get(i).getType() == 0) {
							int startX = Monsters.get(i).getX();
							int startY = Monsters.get(i).getY();
							if (!Monsters.get(i).checkContact()) {
								Monsters.get(i).move(1, 0, 1);
								if (Monsters.get(i).checkContact()) {
									Monsters.get(i).stuck(startX, startY);
									Monsters.get(i).move(1, 0, -1);
								}
							}
						}
						if (Monsters.get(i).getType() == 1) {
							int startX = Monsters.get(i).getX();
							int startY = Monsters.get(i).getY();
							if (!Monsters.get(i).checkContact()) {
								Monsters.get(i).move(0, 1, 1);
								if (Monsters.get(i).checkContact()) {
									Monsters.get(i).stuck(startX, startY);
									Monsters.get(i).move(0, 1, -1);
								}
							}
						}
						if (Monsters.get(i).getType() == 2) {
							int startX = Monsters.get(i).getX();
							int startY = Monsters.get(i).getY();
							int k = (int) (Math.round((Math.random())));
							if (!Monsters.get(i).checkContact()) {
								Monsters.get(i).move(k, 1 - k, 1);
								if (Monsters.get(i).checkContact()) {
									Monsters.get(i).stuck(startX, startY);
									Monsters.get(i).move(k, 1 - k, -1);
								}
							}
							if (!Monsters.get(i).checkContact()) {
								Monsters.get(i).move(k, 1 - k, 1);
								if (Monsters.get(i).checkContact()) {
									Monsters.get(i).stuck(startX, startY);
									Monsters.get(i).move(k, 1 - k, -1);
								}
							}
							if (!Monsters.get(i).checkContact()) {
								Monsters.get(i).move(k, 1 - k, 1);
								if (Monsters.get(i).checkContact()) {
									Monsters.get(i).stuck(startX, startY);
									Monsters.get(i).move(k, 1 - k, -1);
								}
							}
						}
					}
				}
				explode: for (int i = 0; i < Bombs.size(); i++) {
					if (Bombs.get(i).explode(count)) {
						Bombs.get(i).grow();
						if (!Bombs.get(i).checkHero()) {
							Bombs.get(i).check();
						} else {
							break explode;
						}
					}
					if (Bombs.get(i).explode(count - 5)) {
						Bombs.get(i).move();
					}
					if (realRemote.canExplode()) {
						if (realRemote.explodeLimit(this.count)) {
							realRemote.grow();
							if (!realRemote.checkHero()) {
								realRemote.check();
							}
						}
						if (realRemote.explodeWait()) {
							realRemote.move();
						}
					}
				}
				if (myworld.Lives == 0) {
					System.exit(0);
				}
				count++;
				repaint();
				Thread.sleep(20);
			}
		} catch (InterruptedException exception) {
			throw new RuntimeException("Thread component failed");
		}
	}
}
