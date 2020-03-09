import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class World {
	private final JFileChooser chooser;
	private String level;
	private int time;
	ArrayList<Walls> WI = new ArrayList<Walls>();
	ArrayList<Walls> WB = new ArrayList<Walls>();
	ArrayList<Bombs> Bombs = new ArrayList<Bombs>();
	ArrayList<Monster> Monsters = new ArrayList<Monster>();
	ArrayList<PowerUps> Powerups = new ArrayList<PowerUps>();

	JFrame myWindow = new JFrame();
	WorldComponent myworld = new WorldComponent();
	KeyListener change;
	Hero hero;
	Thread t1;
	Walls wall;
	int Lives;
	LargerRangeBomb rangeBomb;
	Bombs myBomb;
	Boolean isPaused;
	int range = 80;
	private BombIncrease moreBomb;
	private RemoteBomb remotebomb;
	private realRemote realRemote;
	private RemoteBombListener myRemote;
	private myListener mykey;
	private BombListener myBombs;
	private information info;
	private Clip gamemusic;

	public World(String level) {
		this.Lives = 3;
		this.level = level;
		this.chooser = new JFileChooser();
		this.time = 200;
		this.isPaused = false;
		change = new LevelListener(this, this.level);
		myWindow.addKeyListener(this.change);
		t1 = new Thread(myworld);

	}

	/*
	 * Function that loads all objects involved in the game and given to main.
	 */
	public void load() throws IOException {

		play();

		myWindow.removeKeyListener(this.change);
		myWindow.removeKeyListener(mykey);
		myWindow.removeKeyListener(myBombs);
		myWindow.removeKeyListener(myRemote);

		myWindow.setSize(1100, 900);
		myWindow.setTitle("BomberMan");

		File inputFile = new File(level);
		Scanner inScanner = new Scanner(inputFile);
		for (int y = 0; y < 15; y++) {
			if (inScanner.hasNextLine()) {
				String line = inScanner.nextLine();
				for (int x = 0; x < 19; x++) {
					if (line.charAt(x) == ' ') {
						continue;
					} else if (line.charAt(x) == 'w') {
						wall = new Walls(x, y, this);
						this.WI.add(wall);
					} else if (line.charAt(x) == 'h') {
						hero = new Hero(WI, WB, Bombs, this);
						hero.set(x, y);
						myBomb = new Bombs(hero, this, range);

						Bombs.add(myBomb);
						hero.setBomb(Bombs);

						mykey = new myListener(hero, this);
						myBombs = new BombListener(Bombs);
						myWindow.addKeyListener(mykey);
						myWindow.addKeyListener(myBombs);
						myworld.setBombs(Bombs);
						myworld.sethero(hero);
						myworld.setworld(this);

						info = new information(Lives, time, this);
						myworld.setInfo(info);

					} else if (line.charAt(x) == 'e') {

						Monster myMonster = new Monster(hero, this, WI, WB, Monsters.size() % 3);
						myMonster.set(x, y);
						Monsters.add(myMonster);

					} else if (line.charAt(x) == 'b') {
						wall = new Walls(x, y, this);
						this.WB.add(wall);

					} else if (line.charAt(x) == '1') {
						realRemote = new realRemote(hero, this, range);
						remotebomb = new RemoteBomb(hero, this, realRemote);
						myRemote = new RemoteBombListener(remotebomb, realRemote);
						myworld.setRealRemote(realRemote);
						myWindow.addKeyListener(myRemote);
						myworld.setBombIncrease(moreBomb);
						myworld.setRemoteBomb(remotebomb);
						remotebomb.set(x, y);
						Powerups.add(remotebomb);
						wall = new Walls(x, y, this);
						this.WB.add(wall);
					} else if (line.charAt(x) == '2') {
						moreBomb = new BombIncrease(hero, myworld);
						moreBomb.set(x, y);
						Powerups.add(moreBomb);
						wall = new Walls(x, y, this);
						this.WB.add(wall);
					} else if (line.charAt(x) == '3') {
						rangeBomb = new LargerRangeBomb(hero, myworld);
						rangeBomb.set(x, y);
						Powerups.add(rangeBomb);
						wall = new Walls(x, y, this);
						this.WB.add(wall);

					} else {
						throw new RuntimeException("Invalid Character in World Text File");

					}
				}
			}
		}
		if (myworld.getCount() < 1) {
			t1.start();
		}
		for (int m = 0; m < Monsters.size(); m++) {
			Monsters.get(m).setHero(hero);
			Monsters.get(m).setBomb(Bombs);
		}
		for (int m = 0; m < Powerups.size(); m++) {
			Powerups.get(m).setHero(hero);
		}
		myworld.setPowerUps(Powerups);
		myworld.setMonster(Monsters);
		myworld.setLargerRangeBomb(rangeBomb);
		myworld.setWI(WI);
		myworld.setWB(WB);
		change = new LevelListener(this, this.level);
		myWindow.addKeyListener(change);
		myWindow.add(myworld);
		myWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myWindow.setVisible(true);
		inScanner.close();
	}

	/*
	 * Function for changing levels and clears the screen of all objects so that
	 * the new level can be loaded.
	 * 
	 * @param level - this is the level that is supposed to be loaded next
	 */
	public void ChangeLevel(String level) throws IOException {
		Monsters.clear();
		gamemusic.stop();
		Bombs.clear();
		Powerups.clear();
		WI.clear();
		WB.clear();
		this.level = level;
		this.time = 200;
		this.load();
		System.out.println(level + " has loaded!");
	}

	/*
	 * Function that handles the death of the hero and its affects on the world.
	 */
	public void retry() throws IOException {
		Lives -= 1;
		gamemusic.stop();
		Monsters.clear();
		Bombs.clear();
		Powerups.clear();
		WI.clear();
		WB.clear();
		this.time = 200;
		this.load();
		System.out.println("You died! You have " + Lives + " left!");
	}

	/*
	 * Function that handles the death of all Monsters and its affects on the
	 * world.
	 */
	public void win() throws IOException {
		Monsters.clear();
		gamemusic.stop();
		Bombs.clear();
		Powerups.clear();
		WI.clear();
		WB.clear();
		this.time = 200;
		this.level = next();
		this.load();
		System.out.println("Congradulations! You have " + Lives + "left!");

	}

	// sees if there is a next possible level and returns it
	public String next() {
		if (this.level == "Level1") {
			return "Level2";
		} else {
			return "Level3";

		}

	}

	// when called togels the pause of the game
	public void pause() {
		if (this.isPaused == false) {
			this.isPaused = true;
		} else {
			this.isPaused = false;
		}
	}

	/*
	 * Function that checks to see if all monsters are dead.
	 */
	public void CheckWin() {
		if (this.Monsters.size() == 0) {
			try {
				this.win();
			} catch (IOException exception) {

			}
		}

	}

	// plays music when called
	public void play() {
		File inputFile = new File("8bit_Dungeon_Boss.wav");
		AudioInputStream gameMusic;
		try {
			gameMusic = AudioSystem.getAudioInputStream(inputFile);
			gamemusic = AudioSystem.getClip();
			gamemusic.open(gameMusic);
			gamemusic.start();
		} catch (UnsupportedAudioFileException | LineUnavailableException | IOException exception) {
			throw new RuntimeException("Music failed");
		}
	}
}
