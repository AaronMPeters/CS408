import javax.swing.*;
import java.applet.*;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class GUI implements Runnable, KeyListener, ActionListener {
	public JFrame mainf;
	public JPanel mainp;
	private Graphics2D g2d;
	private Image dbImage;
	private Ship ship1, ship2, rogueShip;
	private JPanel settingPanel;
	private JRadioButton graExtTrue, graExtFalse, graVisTrue, graVisFalse,
			unlimLifeTrue, unlimLifeFalse;
	private JComboBox modeList;
	private ButtonGroup graExt, graVis, unlimLife;
	private JButton resetScore, loadGame, okButton, cancelButton, saveGame,
			quit;
	private JTextField numAstField, startLevel;
	private Boolean run;
	private int score1, score2;
	private int life1, life2;
	private List<Asteroid> astList;
	private Boolean start;
	private int numAst;
	private AffineTransform identity;
	private List<Bullet> bullets;
	private List<AsteroidSmall> smallAsts;
	private int level;
	int alientime;
	private AlienShip alien;
	private List<Bullet> alienbullets;
	long starttime, starttime2;
	long nowtime;
	private Boolean gravVis = false;
	private SEPlayer se;
	private LinkedList<Long> bq1, bq2;
	static Boolean quitB = false;

	private int mode;
	private int SINGLE_PLAYER = 0;
	private int TWO_PLAYER = 1;
	private int AI = 2;

	GUI() {
		se = new SEPlayer();
		start = true;
		run = false;
		score1 = 0;
		score2 = 0;
		life1 = 0;
		life2 = 0;

		alientime = 0;

		bq1 = new LinkedList<Long>();
		bq2 = new LinkedList<Long>();
		for (int i = 0; i < 4; i++) {
			bq1.addLast(System.currentTimeMillis());
			bq2.addLast(System.currentTimeMillis());
		}

		Setting.gravitationalIsExist = false;
		Setting.gravitationalIsVisible = false;
		mainp = new JPanel();
		mainp.setBackground(Color.BLACK);
		mainp.addKeyListener(this);
		graExtTrue = new JRadioButton("Gravitational Exist");
		graExtFalse = new JRadioButton("Gravitational Not Exist");
		graExt = new ButtonGroup();
		graExt.add(graExtTrue);
		graExt.add(graExtFalse);
		graExtFalse.setSelected(true);

		graVisTrue = new JRadioButton("Gravitational Visible");
		graVisFalse = new JRadioButton("Gravitational Invisible");
		graVis = new ButtonGroup();
		graVis.add(graVisTrue);
		graVis.add(graVisFalse);
		graVisFalse.setSelected(true);

		numAstField = new JTextField(10);

		startLevel = new JTextField(10);

		unlimLifeTrue = new JRadioButton("Unlimited Life");
		unlimLifeFalse = new JRadioButton("Limited Life");
		unlimLife = new ButtonGroup();
		unlimLife.add(unlimLifeTrue);
		unlimLife.add(unlimLifeFalse);
		unlimLifeFalse.setSelected(true);

		String[] modes = { "Single Player", "Two Player", "Boss Fight" };
		modeList = new JComboBox(modes);
		modeList.setSelectedIndex(0);
		modeList.addActionListener(this);

		resetScore = new JButton("Reset Score Now");
		resetScore.addActionListener(this);

		okButton = new JButton("Play with this setting");
		okButton.addActionListener(this);
		okButton.addKeyListener(this);

		quit = new JButton("Quit");
		quit.addActionListener(this);
		quit.addKeyListener(this);

		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(this);
		cancelButton.addKeyListener(this);
		cancelButton.setEnabled(false);

		loadGame = new JButton("Load Game");
		loadGame.addActionListener(this);

		saveGame = new JButton("Save Game");
		saveGame.addActionListener(this);
		saveGame.setEnabled(false);

		settingPanel = new JPanel(new GridLayout(9, 2));
		settingPanel.setSize(200, 200);
		settingPanel.setBackground(Color.WHITE);
		settingPanel.add(graExtTrue);
		settingPanel.add(graExtFalse);
		settingPanel.add(graVisTrue);
		settingPanel.add(graVisFalse);
		settingPanel.add(unlimLifeTrue);
		settingPanel.add(unlimLifeFalse);
		settingPanel.add(new JLabel("Number of Asteroids:"));
		settingPanel.add(numAstField);
		settingPanel.add(new JLabel("Starting level:"));
		settingPanel.add(startLevel);
		settingPanel.add(new JLabel("Game mode:"));
		settingPanel.add(modeList);
		settingPanel.add(resetScore);
		settingPanel.add(quit);
		settingPanel.add(saveGame);
		settingPanel.add(loadGame);
		settingPanel.add(okButton);
		settingPanel.add(cancelButton);

		settingPanel.setVisible(true);

		identity = new AffineTransform();

		mainf = new JFrame("Asteriod Game");
		mainf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		mainf.setSize(600, 400);
		mainf.setResizable(false);
		// mainf.setBackground(Color.BLUE);

		mainf.setContentPane(mainp);
		mainf.add(settingPanel);
		mainf.addKeyListener(this);
		mainf.setVisible(true);
	}

	private void reset_score() {
		score1 = 0;
		score2 = 0;
	}

	private void obInit() {
		astList = new ArrayList<Asteroid>();
		for (int i = 0; i < Setting.astNum; i++) {
			Asteroid tmpAsteroid;
			if (i % 4 == 0) {
				tmpAsteroid = new Asteroid((int) (Math.random() * 600), 0,
						3 + level, 0);
			} else if (i % 4 == 1) {
				tmpAsteroid = new Asteroid((int) (Math.random() * 600), 400,
						3 + level, 0);
			} else if (i % 4 == 2) {
				tmpAsteroid = new Asteroid(600, (int) (Math.random() * 400),
						3 + level, 0);
			} else {
				tmpAsteroid = new Asteroid(0, (int) (Math.random() * 400),
						3 + level, 0);
			}

			tmpAsteroid.isAlive = true;
			astList.add(tmpAsteroid);
		}
		if (life1 > 0) {
			ship1 = new Ship(200, 200, 0, 0);
		}
		if (life2 > 0) {
			ship2 = new Ship(400, 200, 0, 0);
		}

		if (mode == SINGLE_PLAYER) {
			ship2.isAlive = false;
		}
		bullets = new ArrayList<Bullet>();
		smallAsts = new ArrayList<AsteroidSmall>();
		alien = new AlienShip(400, 400, 2 + level, 180, 200);
		alienbullets = new ArrayList<Bullet>();
		starttime = System.currentTimeMillis();
		starttime2 = System.currentTimeMillis();
		rogueShip = new Ship(300, 200, 0, 0);
		rogueShip.speed = level + 3;
		if (mode == AI) {
			alientime = 10;
			alien.hp = 50;
			rogueShip.isAlive = false;
		} else {
			alientime = 0;
		}
	}

	private void paintScreen() { // mainp.paintComponents(g2d);
		try {
			Graphics2D g = (Graphics2D) mainp.getGraphics();
			if ((g != null) && (this.dbImage != null)) {
				g.drawImage(this.dbImage, 0, 0, null);
			}
			g.dispose();
		} catch (Exception e) {
			System.out.println("Graphics context error: " + e);
		}
	}

	public void gameRender() {
		// if (this.dbImage == null)
		// {
		this.dbImage = mainp.createImage(600, 400);
		if (this.dbImage == null) {
			System.out.println("dbImage is null");
			return;
		}
		this.g2d = (Graphics2D) this.dbImage.getGraphics();
		// }
		// System.out.println(dbImage + "" + g2d);
		this.g2d.setColor(Color.BLACK);
		this.g2d.fillRect(0, 0, 600, 400);
		this.g2d.setColor(Color.WHITE);
		g2d.drawString("Ship1 Score: " + score1, 0, 20);
		if (Setting.unlimitLife) {
			g2d.drawString("Ship1 Life: unlimited", 0, 40);
		} else {
			g2d.drawString("Ship1 Life: " + life1, 0, 40);
		}

		if (mode != SINGLE_PLAYER) {
			g2d.drawString("Ship2 Score: " + score2, 0, 60);
			if (Setting.unlimitLife) {
				g2d.drawString("Ship2 Life: unlimited", 0, 80);
			} else {
				g2d.drawString("Ship2 Life: " + life2, 0, 80);
			}
		}

		if (mode == AI) {
			g2d.drawString("Boss HP: " + alien.hp, 0, 100);
		}
		g2d.drawString("Level: " + level, 270, 20);

		if (gravVis) {
			g2d.setTransform(identity);
			g2d.setColor(Color.BLUE);
			// g2d.translate(300, 200);
			// g2d.rotate(Math.toRadians(rogueShip.heading));
			g2d.fillRect(290, 190, 20, 20);
			// g2d.drawString("G", 295, 195);
		}

		// System.out.print("here");
		if (ship1.isAlive) {
			g2d.setTransform(identity);
			this.g2d.setColor(Color.ORANGE);
			g2d.translate(ship1.x, ship1.y);
			g2d.rotate(Math.toRadians(ship1.heading));
			g2d.drawPolygon(ship1.shipX, ship1.shipY, ship1.shipX.length);
		}

		if (mode != SINGLE_PLAYER) {
			if (ship2.isAlive) {
				g2d.setTransform(identity);
				this.g2d.setColor(Color.GREEN);
				g2d.translate(ship2.x, ship2.y);
				g2d.rotate(Math.toRadians(ship2.heading));
				g2d.drawPolygon(ship2.shipX, ship2.shipY, ship2.shipX.length);
			}
		}
		this.g2d.setColor(Color.WHITE);
		if ((!ship1.isAlive) && (!ship2.isAlive)) {
			g2d.drawString("Game over", 260, 190);
		}
		for (Asteroid asteroid : astList) {
			// System.out.println(asteroid.x + " " +asteroid.y +
			// " "+asteroid.heading + " " + asteroid.speed );
			if (asteroid.isAlive) {
				g2d.setTransform(identity);
				g2d.translate(asteroid.x, asteroid.y);
				g2d.rotate(Math.toRadians(asteroid.heading));
				g2d.drawPolygon(asteroid.xs, asteroid.ys, asteroid.xs.length);
			}
		}

		for (Bullet bullet : bullets) {
			g2d.setTransform(identity);
			// g2d.translate(bullet.x, bullet.y);
			// g2d.rotate(Math.toRadians(bullet.heading));
			g2d.drawOval(bullet.x, bullet.y, 2, 2);
			// g2d.drawPolygon(asteroid.xs, asteroid.ys, asteroid.xs.length);
		}
		for (Bullet abullet : alienbullets) {
			g2d.setTransform(identity);
			g2d.setColor(Color.RED);
			g2d.drawOval(abullet.x, abullet.y, 2, 2);
		}
		g2d.setColor(Color.WHITE);
		for (AsteroidSmall asteroid : smallAsts) {
			// System.out.println(asteroid.x + " " +asteroid.y +
			// " "+asteroid.heading + " " + asteroid.speed );
			if (asteroid.isAlive) {
				g2d.setTransform(identity);
				g2d.translate(asteroid.x, asteroid.y);
				g2d.rotate(Math.toRadians(asteroid.heading));
				g2d.drawPolygon(asteroid.xs, asteroid.ys, asteroid.xs.length);
			}
		}

		if (alientime >= 2 && alien.isAlive) {
			g2d.setColor(Color.RED);
			g2d.setTransform(identity);
			g2d.translate(alien.x, alien.y);
			g2d.rotate(Math.toRadians(180));
			g2d.drawPolygon(alien.xs, alien.ys, alien.xs.length);

		}

		if (rogueShip.isAlive) {
			g2d.setColor(Color.RED);
			g2d.setTransform(identity);
			g2d.translate(rogueShip.x, rogueShip.y);
			g2d.rotate(Math.toRadians(rogueShip.heading));
			g2d.drawPolygon(rogueShip.shipX, rogueShip.shipY,
					rogueShip.shipX.length);
		}

	}

	public static void main(String args[]) {
		GUI gui = new GUI();
		gui.gamePlay();

	}

	public void gamePlay() {

		while (true) {
			System.out.print("");
			if (run) {

				gameRender();
				paintScreen();
				if (ship1.isAlive || ship2.isAlive) {
					updateShip();
					updateAst();
					updateBullet();
					if (alientime >= 2 && alien.isAlive) {
						updateAlien();
					}
					updateAlienbullet();
					if (rogueShip.isAlive) {
						updateRogue();
					}
					calCol();

					checkLevel();
				} else {
					run = false;
					new HighScore(score1, score2, 1);

				}
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {

					e.printStackTrace();
				}
			}
		}

	}

	private void checkLevel() {
		if (astList.isEmpty() && (!alien.isAlive) && (!rogueShip.isAlive)
				&& smallAsts.isEmpty() && mode != AI) {
			level++;
			Setting.astNum += 2;
			obInit();
		}

	}

	private void updateRogue() {
		rogueShip.heading = (int) (rogueShip.heading + Math.random() * 3);
		rogueShip.x = rogueShip.x
				- (int) (Math.sin(Math.toRadians(rogueShip.heading)) * rogueShip.speed);
		if (rogueShip.x > 600) {
			rogueShip.x -= 600;
		} else if (rogueShip.x < 0) {
			rogueShip.x += 600;
		}
		rogueShip.y = (int) (Math.cos(Math.toRadians(rogueShip.heading)) * rogueShip.speed)
				+ rogueShip.y;
		if (rogueShip.y > 400) {
			rogueShip.y -= 400;
		} else if (rogueShip.y < 0) {
			rogueShip.y += 400;
		}
		nowtime = System.currentTimeMillis();
		if (nowtime - starttime2 >= 2000) {
			// for (int i=0;i<8;i++){
			Bullet newBullet = new Bullet(rogueShip.x, rogueShip.y,
					(int) (Math.random() * 360), 3);
			alienbullets.add(newBullet);
			// }
			starttime2 = nowtime;
		}
	}

	private void updateAlien() {
		alien.heading = (int) (alien.heading + Math.random() * 3);
		alien.x = alien.x
				- (int) (Math.sin(Math.toRadians(alien.heading)) * alien.speed);
		if (alien.x > 600) {
			alien.x -= 600;
		} else if (alien.x < 0) {
			alien.x += 600;
		}
		alien.y = (int) (Math.cos(Math.toRadians(alien.heading)) * alien.speed)
				+ alien.y;
		if (alien.y > 400) {
			alien.y -= 400;
		} else if (alien.y < 0) {
			alien.y += 400;
		}
		nowtime = System.currentTimeMillis();
		if (nowtime - starttime >= 2000) {
			for (int i = 0; i < 8; i++) {
				Bullet newalienBullet = new Bullet(alien.x, alien.y, i * 45, 3);
				alienbullets.add(newalienBullet);
			}
			starttime = nowtime;
		}
	}

	private void updateShip() {
		ship1.x = ship1.x
				- (int) (Math.sin(Math.toRadians(ship1.heading)) * ship1.speed);
		if (ship1.x > 600) {
			ship1.x -= 600;
		} else if (ship1.x < 0) {
			ship1.x += 600;
		}
		ship1.y = (int) (Math.cos(Math.toRadians(ship1.heading)) * ship1.speed)
				+ ship1.y;
		if (ship1.y > 400) {
			ship1.y -= 400;
		} else if (ship1.y < 0) {
			ship1.y += 400;
		}

		ship2.x = ship2.x
				- (int) (Math.sin(Math.toRadians(ship2.heading)) * ship2.speed);
		if (ship2.x > 600) {
			ship2.x -= 600;
		} else if (ship2.x < 0) {
			ship2.x += 600;
		}
		ship2.y = (int) (Math.cos(Math.toRadians(ship2.heading)) * ship2.speed)
				+ ship2.y;
		if (ship2.y > 400) {
			ship2.y -= 400;
		} else if (ship2.y < 0) {
			ship2.y += 400;
		}

		if (Setting.gravitationalIsExist) {
			if (ship1.isAlive) {
				if (ship1.x > 300) {
					ship1.x--;
				} else if (ship1.x < 300) {
					ship1.x++;
				}

				if (ship1.y > 200) {
					ship1.y--;
				} else if (ship1.y < 200) {
					ship1.y++;
				}
			}

			if (ship2.isAlive) {
				if (ship2.x > 300) {
					ship2.x--;
				} else if (ship2.x < 300) {
					ship2.x++;
				}

				if (ship2.y > 200) {
					ship2.y--;
				} else if (ship2.y < 200) {
					ship2.y++;
				}
			}
		}
	}

	private void updateAst() {
		for (Asteroid ast : astList) {
			ast.x = ast.x
					- (int) (Math.sin(Math.toRadians(ast.heading)) * ast.speed);
			if (ast.x > 600) {
				ast.x -= 600;
			} else if (ast.x < 0) {
				ast.x += 600;
			}
			ast.y = (int) (Math.cos(Math.toRadians(ast.heading)) * ast.speed)
					+ ast.y;
			if (ast.y > 400) {
				ast.y -= 400;
			} else if (ast.y < 0) {
				ast.y += 400;
			}
		}

		for (AsteroidSmall ast : smallAsts) {
			ast.x = ast.x
					- (int) (Math.sin(Math.toRadians(ast.heading)) * ast.speed);
			if (ast.x > 600) {
				ast.x -= 600;
			} else if (ast.x < 0) {
				ast.x += 600;
			}
			ast.y = (int) (Math.cos(Math.toRadians(ast.heading)) * ast.speed)
					+ ast.y;
			if (ast.y > 400) {
				ast.y -= 400;
			} else if (ast.y < 0) {
				ast.y += 400;
			}
		}

	}

	private void updateBullet() {
		List<Bullet> tempList = new ArrayList<Bullet>();
		for (Bullet bullet : bullets) {
			bullet.x = bullet.x
					- (int) (Math.sin(Math.toRadians(bullet.heading)) * bullet.speed);
			if (bullet.x > 600) {
				bullet.x -= 600;
			} else if (bullet.x < 0) {
				bullet.x += 600;
			}
			bullet.y = (int) (Math.cos(Math.toRadians(bullet.heading)) * bullet.speed)
					+ bullet.y;
			if (bullet.y > 400) {
				bullet.y -= 400;
			} else if (bullet.y < 0) {
				bullet.y += 400;
			}

			bullet.dis += 10;
			if (bullet.dis > 400) {
				tempList.add(bullet);
				// bullets.remove(bullet);
			}
		}

		for (Bullet bullet : tempList) {
			bullets.remove(bullet);
		}
	}

	private void updateAlienbullet() {
		List<Bullet> tempList = new ArrayList<Bullet>();
		for (Bullet bullet : alienbullets) {
			bullet.x = bullet.x
					- (int) (Math.sin(Math.toRadians(bullet.heading)) * bullet.speed);
			if (bullet.x > 600) {
				bullet.x -= 600;
			} else if (bullet.x < 0) {
				bullet.x += 600;
			}
			bullet.y = (int) (Math.cos(Math.toRadians(bullet.heading)) * bullet.speed)
					+ bullet.y;
			if (bullet.y > 400) {
				bullet.y -= 400;
			} else if (bullet.y < 0) {
				bullet.y += 400;
			}

			bullet.dis += 10;
			if (bullet.dis > 400) {
				tempList.add(bullet);
				// bullets.remove(bullet);
			}

		}
		for (Bullet bullet : tempList) {
			alienbullets.remove(bullet);
		}
	}

	private void calCol() {
		List<Bullet> tempB = new ArrayList<Bullet>();
		List<Asteroid> tempA = new ArrayList<Asteroid>();
		List<AsteroidSmall> tempS = new ArrayList<AsteroidSmall>();
		List<Bullet> tempAien = new ArrayList<Bullet>();
		List<Bullet> Atemp = new ArrayList<Bullet>();
		try {
			if (Setting.gravitationalIsExist) {
				if (((290 < ship1.x) && (310 > ship1.x)) && (190 < ship1.y)
						&& (210 > ship1.y)) {
					ship1 = new Ship(200, 200, 0, 0);
					if (life1 == 0) {
						ship1.isAlive = false;
					}
					if (life1 > 0) {
						life1--;
					}
					if (life1 == 0) {
						ship1.isAlive = false;
					}
					se.SePlayer("explosion.wav", "1");
				}

				if (((290 < ship2.x) && (310 > ship2.x)) && (190 < ship2.y)
						&& (210 > ship2.y)) {
					ship2 = new Ship(400, 200, 0, 0);
					if (life2 == 0) {
						ship2.isAlive = false;
					}
					if (life2 > 0) {
						life2--;
					}
					if (life2 == 0) {
						ship2.isAlive = false;
					}
					se.SePlayer("explosion.wav", "1");
				}
			}

			for (Bullet abullet : alienbullets) {
				if (((abullet.x < (ship1.x + 10)) && (abullet.x > (ship1.x - 10)))
						&& ((abullet.y < (ship1.y + 10)) && (abullet.y > (ship1.y - 10)))) {
					ship1 = new Ship(200, 200, 0, 0);
					if (life1 == 0) {
						ship1.isAlive = false;
					}
					if (life1 > 0) {
						life1--;
					}
					if (life1 == 0) {
						ship1.isAlive = false;
					}
					se.SePlayer("explosion.wav", "1");
					Atemp.add(abullet);
				} else if (((abullet.x < (ship2.x + 10)) && (abullet.x > (ship2.x - 10)))
						&& ((abullet.y < (ship2.y + 10)) && (abullet.y > (ship2.y - 10)))) {
					ship2 = new Ship(400, 200, 0, 0);
					if (life2 == 0) {
						ship2.isAlive = false;
					}
					if (life2 > 0) {
						life2--;
					}
					if (life2 == 0) {
						ship2.isAlive = false;
					}
					se.SePlayer("explosion.wav", "1");
					// ship1 = new Ship(200,200,0,0);
					Atemp.add(abullet);

				}
			}

			if (((ship1.x < (alien.x + 20)) && (ship1.x > (alien.x - 20)))
					&& ((ship1.y < (alien.y + 10)) && (ship1.y > (alien.y - 10)))) {
				alien.hp--;
				ship1 = new Ship(200, 200, 0, 0);
				if (alien.hp == 0) {
					alien.isAlive = false;
				}
				if (life1 == 0) {
					ship1.isAlive = false;
				}
				if (life1 > 0) {
					life1--;
				}
				if (life1 == 0) {
					ship1.isAlive = false;
				}
				se.SePlayer("explosion.wav", "1");
			}

			if (((ship2.x < (alien.x + 20)) && (ship2.x > (alien.x - 20)))
					&& ((ship2.y < (alien.y + 10)) && (ship2.y > (alien.y - 10)))) {
				alien.hp--;
				ship2 = new Ship(400, 200, 0, 0);
				if (alien.hp == 0) {
					alien.isAlive = false;
				}
				if (life2 == 0) {
					ship2.isAlive = false;
				}
				if (life2 > 0) {
					life2--;
				}
				if (life2 == 0) {
					ship2.isAlive = false;
				}
				se.SePlayer("explosion.wav", "1");
			}

			if (((ship1.x < (rogueShip.x + 20)) && (ship1.x > (rogueShip.x - 20)))
					&& ((ship1.y < (rogueShip.y + 10)) && (ship1.y > (rogueShip.y - 10)))) {

				rogueShip.isAlive = false;
				ship1 = new Ship(200, 200, 0, 0);
				if (life1 == 0) {
					ship1.isAlive = false;
				}
				if (life1 > 0) {
					life1--;
				}
				if (life1 == 0) {
					ship1.isAlive = false;
				}
				se.SePlayer("explosion.wav", "1");
			}

			if (((ship2.x < (rogueShip.x + 20)) && (ship2.x > (rogueShip.x - 20)))
					&& ((ship2.y < (rogueShip.y + 10)) && (ship2.y > (rogueShip.y - 10)))) {

				rogueShip.isAlive = false;
				ship2 = new Ship(400, 200, 0, 0);
				if (life2 == 0) {
					ship2.isAlive = false;
				}
				if (life2 > 0) {
					life2--;
				}
				if (life2 == 0) {
					ship2.isAlive = false;
				}
				se.SePlayer("explosion.wav", "1");
			}

			for (Bullet bullet : bullets) {
				if (bullet.owner == 3) {
					continue;
				}
				if (((bullet.x < (alien.x + 20)) && (bullet.x > (alien.x - 20)))
						&& ((bullet.y < (alien.y + 10)) && (bullet.y > (alien.y - 10)))) {
					if (alien.isAlive) {
						alien.hp--;
						if (alien.hp == 0) {
							alien.isAlive = false;
							if (bullet.owner == Bullet.SHIP1) {
								score1 += 100;

							} else {
								score2 += 100;

							}
						}
						tempB.add(bullet);
					}

				}

				if (mode == TWO_PLAYER) {
					if (bullet.owner == 2
							&& ((bullet.x < (ship1.x + 10)) && (bullet.x > (ship1.x - 10)))
							&& ((bullet.y < (ship1.y + 10)) && (bullet.y > (ship1.y - 10)))) {
						ship1 = new Ship(200, 200, 0, 0);
						if (life1 == 0) {
							ship1.isAlive = false;
						}
						if (life1 > 0) {
							life1--;
						}
						if (life1 == 0) {
							ship1.isAlive = false;
						}
						score2 += 100;
						se.SePlayer("explosion.wav", "1");
						tempB.add(bullet);
					}

					if (bullet.owner == 1
							&& ((bullet.x < (ship2.x + 10)) && (bullet.x > (ship2.x - 10)))
							&& ((bullet.y < (ship2.y + 10)) && (bullet.y > (ship2.y - 10)))) {
						ship2 = new Ship(400, 200, 0, 0);
						if (life2 == 0) {
							ship2.isAlive = false;
						}
						if (life2 > 0) {
							life2--;
						}
						if (life2 == 0) {
							ship2.isAlive = false;
						}
						score1 += 100;
						se.SePlayer("explosion.wav", "1");
						tempB.add(bullet);
					}

				}

				if (((bullet.x < (rogueShip.x + 20)) && (bullet.x > (rogueShip.x - 20)))
						&& ((bullet.y < (rogueShip.y + 10)) && (bullet.y > (rogueShip.y - 10)))) {
					if (rogueShip.isAlive) {
						rogueShip.isAlive = false;
						tempB.add(bullet);
						if (bullet.owner == Bullet.SHIP1) {
							score1 += 100;
							alientime++;
						} else {
							score2 += 100;
							alientime++;
						}
					}

				}

				for (AsteroidSmall ast : smallAsts) {
					if (((bullet.x < (ast.x + 10)) && (bullet.x > (ast.x - 10)))
							&& ((bullet.y < (ast.y + 10)) && (bullet.y > (ast.y - 10)))) {
						tempB.add(bullet);
						tempS.add(ast);
						if (bullet.owner == Bullet.SHIP1) {
							score1 += 5;
							alientime++;
						} else {
							score2 += 5;
							alientime++;
						}
					}
				}
				for (Asteroid ast : astList) {
					if (((bullet.x < (ast.x + 15)) && (bullet.x > (ast.x - 15)))
							&& ((bullet.y < (ast.y + 15)) && (bullet.y > (ast.y - 15)))) {
						tempB.add(bullet);
						tempA.add(ast);
						smallAsts.add(new AsteroidSmall(ast.x, ast.y,
								ast.speed, 0));
						smallAsts.add(new AsteroidSmall(ast.x, ast.y,
								ast.speed, 0));
						smallAsts.add(new AsteroidSmall(ast.x, ast.y,
								ast.speed, 0));
						if (bullet.owner == Bullet.SHIP1) {
							score1 += 10;
						} else {
							score2 += 10;
						}
					}
				}

			}

			for (AsteroidSmall ast : smallAsts) {
				if (((ship1.x < (ast.x + 15)) && (ship1.x > (ast.x - 15)))
						&& ((ship1.y < (ast.y + 15)) && (ship1.y > (ast.y - 15)))) {
					ship1 = new Ship(200, 200, 0, 0);
					tempS.add(ast);
					if (life1 == 0) {
						ship1.isAlive = false;
					}
					if (life1 > 0) {
						life1--;
					}
					if (life1 == 0) {
						ship1.isAlive = false;
					}
					se.SePlayer("explosion.wav", "1");

				}
			}
			for (Asteroid ast : astList) {
				if (((ship1.x < (ast.x + 25)) && (ship1.x > (ast.x - 25)))
						&& ((ship1.y < (ast.y + 25)) && (ship1.y > (ast.y - 25)))) {
					ship1 = new Ship(200, 200, 0, 0);
					tempA.add(ast);
					smallAsts
							.add(new AsteroidSmall(ast.x, ast.y, ast.speed, 0));
					smallAsts
							.add(new AsteroidSmall(ast.x, ast.y, ast.speed, 0));
					smallAsts
							.add(new AsteroidSmall(ast.x, ast.y, ast.speed, 0));
					if (life1 == 0) {
						ship1.isAlive = false;
					}
					if (life1 > 0) {
						life1--;
					}
					if (life1 == 0) {
						ship1.isAlive = false;
					}
					se.SePlayer("explosion.wav", "1");
					// ship1.isAlive=true;
				}
			}

			for (AsteroidSmall ast : smallAsts) {
				if (((ship2.x < (ast.x + 15)) && (ship2.x > (ast.x - 15)))
						&& ((ship2.y < (ast.y + 15)) && (ship2.y > (ast.y - 15)))) {
					ship2 = new Ship(400, 200, 0, 0);
					tempS.add(ast);
					if (life2 == 0) {
						ship2.isAlive = false;
					}
					if (life2 > 0) {
						life2--;
					}
					if (life2 == 0) {
						ship2.isAlive = false;
					}
					se.SePlayer("explosion.wav", "1");
					// ship2.isAlive=true;

				}
			}
			for (Asteroid ast : astList) {
				if (((ship2.x < (ast.x + 25)) && (ship2.x > (ast.x - 25)))
						&& ((ship2.y < (ast.y + 25)) && (ship2.y > (ast.y - 25)))) {
					ship2 = new Ship(400, 200, 0, 0);
					tempA.add(ast);
					smallAsts
							.add(new AsteroidSmall(ast.x, ast.y, ast.speed, 0));
					smallAsts
							.add(new AsteroidSmall(ast.x, ast.y, ast.speed, 0));
					smallAsts
							.add(new AsteroidSmall(ast.x, ast.y, ast.speed, 0));
					if (life2 == 0) {
						ship2.isAlive = false;
					}
					if (life2 > 0) {
						life2--;
					}
					if (life2 == 0) {
						ship2.isAlive = false;
					}
					se.SePlayer("explosion.wav", "1");
					// ship2.isAlive=true;
				}
			}

		} catch (Exception e) {
			System.out.println();
			e.printStackTrace();
		}
		for (Bullet bullet : tempB) {
			bullets.remove(bullet);
		}
		for (Bullet bullet : Atemp) {
			alienbullets.remove(bullet);
		}
		for (Asteroid ast : tempA) {
			astList.remove(ast);
		}
		for (AsteroidSmall ast : tempS) {
			smallAsts.remove(ast);
		}

	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// System.out.println(arg0.getKeyCode());
		if (arg0.getKeyCode() == KeyEvent.VK_RIGHT) {
			ship1.heading += 45;
			if (ship1.heading == 360) {
				ship1.heading = 0;
			}
		} else if (arg0.getKeyCode() == KeyEvent.VK_LEFT) {
			ship1.heading -= 45;
			if (ship1.heading == -45) {
				ship1.heading = 315;
			}
		} else if (arg0.getKeyCode() == KeyEvent.VK_UP) {
			// System.out.println("speed______");
			if (ship1.speed == 0) {
				ship1.speed = 1;
			}
			ship1.speed++;
		} else if (arg0.getKeyCode() == KeyEvent.VK_D) {
			ship2.heading += 45;
			if (ship2.heading == 360) {
				ship2.heading = 0;
			}
		} else if (arg0.getKeyCode() == KeyEvent.VK_A) {
			ship2.heading -= 45;
			if (ship2.heading == -45) {
				ship2.heading = 315;
			}
		} else if (arg0.getKeyCode() == KeyEvent.VK_W) {
			// System.out.println("speed______");
			if (ship2.speed == 0) {
				ship2.speed = 1;
			}
			ship2.speed++;
		} else if (arg0.getKeyCode() == KeyEvent.VK_ESCAPE) {
			// System.out.println("speed______");
			run = false;
			startLevel.setText("" + level);
			numAstField.setText("" + Setting.astNum);
			settingPanel.setVisible(true);
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		if (arg0.getKeyCode() == KeyEvent.VK_UP) {
			ship1.speed = 0;
		} else if (arg0.getKeyCode() == KeyEvent.VK_SPACE) {
			// System.out.println("speed______");
			if (ship1.isAlive) {
				if (System.currentTimeMillis() - bq1.getFirst() > 200) {

					Bullet newBullet = new Bullet(ship1.x, ship1.y,
							ship1.heading, Bullet.SHIP1);
					bullets.add(newBullet);
					se.SePlayer("shoot.wav", "1");
					bq1.removeFirst();
					bq1.addLast(System.currentTimeMillis());
				}

			}
		} else if (arg0.getKeyCode() == KeyEvent.VK_W) {
			ship2.speed = 0;
		} else if (arg0.getKeyCode() == KeyEvent.VK_R) {
			// System.out.println("speed______");
			if (ship2.isAlive) {
				if (System.currentTimeMillis() - bq1.getFirst() > 200) {
					Bullet newBullet = new Bullet(ship2.x, ship2.y,
							ship2.heading, Bullet.SHIP2);
					bullets.add(newBullet);
					se.SePlayer("shoot.wav", "1");
					bq1.removeFirst();
					bq1.addLast(System.currentTimeMillis());
				}
			}
		}

	}

	@Override
	public void keyTyped(KeyEvent arg0) {

	}

	@Override
	public void run() {
		while (true) {
			if (run) {
				gameRender();
				paintScreen();
				ship1.x++;
				ship1.y++;
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {

					e.printStackTrace();
				}
			}
		}

	}

	@Override
	public void actionPerformed(ActionEvent event) {

		if (event.getSource() == resetScore) {
			// score1 = 0;
			// score2 = 0;
			PrintWriter writer;
			try {
				writer = new PrintWriter("top", "UTF-8");
				writer.write("");
				writer.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else if (event.getSource() == loadGame) {
			JFileChooser fc = new JFileChooser();
			int returnVal = fc.showOpenDialog(mainp);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				bullets = new ArrayList<Bullet>();
				alienbullets = new ArrayList<Bullet>();
				astList = new ArrayList<Asteroid>();
				smallAsts = new ArrayList<AsteroidSmall>();

				String current;
				BufferedReader br;
				try {
					br = new BufferedReader(new FileReader(file));

					while ((current = br.readLine()) != null) {
						// System.out.println(sCurrentLine);
						String line[] = current.split(" ");
						if (Integer.parseInt(line[0]) == AstBase.SHIP) {
							if (line[line.length - 1].equals("1")) {
								ship1 = new Ship(Integer.parseInt(line[2]),
										Integer.parseInt(line[3]), 0, 0);
								if (line[1].equals("true")) {
									ship1.isAlive = true;
								} else {
									ship1.isAlive = false;
								}
								ship1.heading = Integer.parseInt(line[5]);
								ship1.speed = Integer.parseInt(line[4]);
								score1 = Integer.parseInt(line[6]);
								life1 = Integer.parseInt(line[7]);
							} else if (line[line.length - 1].equals("2")) {
								ship2 = new Ship(Integer.parseInt(line[2]),
										Integer.parseInt(line[3]), 0, 0);
								if (line[1].equals("true")) {
									ship2.isAlive = true;
								} else {
									ship2.isAlive = false;
								}
								ship2.heading = Integer.parseInt(line[5]);
								ship2.speed = Integer.parseInt(line[4]);
								score2 = Integer.parseInt(line[6]);
								life2 = Integer.parseInt(line[7]);
							} else if (line[line.length - 1].equals("3")) {
								rogueShip = new Ship(Integer.parseInt(line[2]),
										Integer.parseInt(line[3]), 0, 0);
								if (line[1].equals("true")) {
									rogueShip.isAlive = true;
								} else {
									rogueShip.isAlive = false;
								}
								rogueShip.heading = Integer.parseInt(line[5]);
								rogueShip.speed = Integer.parseInt(line[4]);
								// score2 = Integer.parseInt(line[6]);
								// life2 = Integer.parseInt(line[7]);
							}

						} else if (Integer.parseInt(line[0]) == AstBase.ASTERIOD) {
							Asteroid ast = new Asteroid(
									Integer.parseInt(line[2]),
									Integer.parseInt(line[3]),
									Integer.parseInt(line[4]), 0);
							ast.heading = Integer.parseInt(line[5]);
							astList.add(ast);
						} else if (Integer.parseInt(line[0]) == AstBase.ASTERIODSMALL) {
							AsteroidSmall ast = new AsteroidSmall(
									Integer.parseInt(line[2]),
									Integer.parseInt(line[3]),
									Integer.parseInt(line[4]), 0);
							ast.heading = Integer.parseInt(line[5]);
							smallAsts.add(ast);
						} else if (Integer.parseInt(line[0]) == AstBase.BULLET) {
							Bullet bullet = new Bullet(
									Integer.parseInt(line[2]),
									Integer.parseInt(line[3]),
									Integer.parseInt(line[5]),
									Integer.parseInt(line[6]));
							if (line[6].equals("3")) {
								alienbullets.add(bullet);
							} else {
								bullets.add(bullet);
							}
						} else if (Integer.parseInt(line[0]) == AstBase.ENEMY) {
							alien = new AlienShip(Integer.parseInt(line[2]),
									Integer.parseInt(line[3]),
									Integer.parseInt(line[4]),
									Integer.parseInt(line[5]),
									Integer.parseInt(line[6]));
							if (line[1].equals("true")) {
								alien.isAlive = true;
							} else {
								alien.isAlive = false;
							}
							alien.hp = Integer.parseInt(line[7]);
							alientime = Integer.parseInt(line[8]);
						} else if (line[1].equals("gravExt")) {
							if (line[2].equals("true")) {
								Setting.gravitationalIsExist = true;
							} else {
								Setting.gravitationalIsExist = false;
							}
						} else if (line[1].equals("gravVis")) {
							if (line[2].equals("true")) {
								gravVis = true;
							} else {
								gravVis = false;
							}

						} else if (line[1].equals("level")) {
							level = Integer.parseInt(line[2]);
						} else if (line[1].equals("unlimit")) {
							if (line[2].equals("true")) {
								Setting.unlimitLife = true;
							} else {
								Setting.unlimitLife = false;
							}
						}

						starttime = System.currentTimeMillis();
						starttime2 = System.currentTimeMillis();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			settingPanel.setVisible(false);
			mainp.requestFocus();
			run = true;
			// TODO : add load game
		} else if (event.getSource() == saveGame) {
			JFileChooser fc = new JFileChooser();
			int returnVal = fc.showSaveDialog(mainp);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				try {
					PrintWriter writer = new PrintWriter(file, "UTF-8");
					writer.write(ship1.toString() + " " + score1 + " " + life1
							+ " " + "1" + "\n");
					writer.write(ship2.toString() + " " + score2 + " " + life2
							+ " " + "2" + "\n");
					writer.write(rogueShip.toString() + " " + "3" + "\n");
					writer.write(alien.toString() + " " + alien.firerate + " "
							+ alien.hp + " " + alientime + "\n");
					for (Asteroid asteroid : astList) {
						writer.write(asteroid.toString() + "\n");
					}
					for (AsteroidSmall asteroid : smallAsts) {
						writer.write(asteroid.toString() + "\n");
					}
					for (Bullet alienbullet : alienbullets) {
						writer.write(alienbullet.toString() + " "
								+ alienbullet.owner + "\n");
					}
					for (Bullet bullet : bullets) {
						writer.write(bullet.toString() + " " + bullet.owner
								+ "\n");
					}
					writer.write("0" + " " + "gravExt" + " "
							+ Setting.gravitationalIsExist + "\n");
					writer.write("0" + " " + "gravVis" + " " + gravVis + "\n");
					writer.write("0" + " " + "level" + " " + level + "\n");
					writer.write("0" + " " + "unlimit" + " "
							+ Setting.unlimitLife + "\n");
					writer.close();

				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			settingPanel.setVisible(false);
			mainp.requestFocus();
			run = true;

			// TODO : add load game
		} else if (event.getSource() == okButton) {
			if (graExtTrue.isSelected()) {
				Setting.gravitationalIsExist = true;

			} else {
				Setting.gravitationalIsExist = false;

			}

			if (graVisTrue.isSelected()) {
				Setting.gravitationalIsVisible = true;
				gravVis = true;
			} else {
				Setting.gravitationalIsVisible = false;
				gravVis = false;
			}
			System.out.println(Setting.gravitationalIsVisible);

			if (unlimLifeTrue.isSelected()) {
				Setting.unlimitLife = true;
				life1 = Integer.MAX_VALUE;
				life2 = Integer.MAX_VALUE;
			} else {
				Setting.unlimitLife = false;
				life1 = 3;
				life2 = 3;
			}

			if (mode != AI) {
				Setting.astNum = Integer.parseInt(numAstField.getText());
			} else {
				Setting.astNum = 0;
			}

			settingPanel.setVisible(false);
			mainp.requestFocus();

			// if(mode != AI) {
			level = Integer.parseInt(startLevel.getText());
			// } else {
			// level = 10;
			// }

			// if(start){
			obInit();
			reset_score();
			// start = false;
			// }
			saveGame.setEnabled(true);
			cancelButton.setEnabled(true);
			run = true;
		} else if (event.getSource() == cancelButton) {
			settingPanel.setVisible(false);
			mainp.requestFocus();
			run = true;
		} else if (event.getSource() == quit) {
			int reply = JOptionPane.showConfirmDialog(null,
					"You sure you want to quit?", "quit",
					JOptionPane.YES_NO_OPTION);
			if (reply == JOptionPane.YES_OPTION) {
				quitB = false;
				new HighScore(score1, score2, 0);

			}
		} else if (event.getSource() == modeList) {
			JComboBox cb = (JComboBox) event.getSource();
			mode = cb.getSelectedIndex();
			System.out.println("mode change to " + mode);
			if (mode == AI) {
				numAstField.setEnabled(false);
				// startLevel.setEnabled(false);
			} else {
				numAstField.setEnabled(true);
				// startLevel.setEnabled(true);
			}

		}

	}
}
