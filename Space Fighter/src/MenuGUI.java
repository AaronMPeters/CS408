import javax.swing.*;

import java.applet.*;
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

public class MenuGUI { //implements ActionListener, KeyListener {
	/*
	private JRadioButton graExtTrue, graExtFalse, graVisTrue, graVisFalse, unlimLifeTrue, unlimLifeFalse;
	private ButtonGroup graExt, graVis, unlimLife;
	private JButton resetScore, loadGame, okButton, cancelButton, saveGame, quit;
	private JTextField numAstField, startLevel;
	private Boolean start;
	private int numAst;
	
	MenuGUI(){
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

		settingPanel = new JPanel(new GridLayout(8, 2));
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
		settingPanel.add(resetScore);
		settingPanel.add(quit);
		settingPanel.add(saveGame);
		settingPanel.add(loadGame);
		settingPanel.add(okButton);
		settingPanel.add(cancelButton);

		settingPanel.setVisible(true);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent event) {

		if (event.getSource() == resetScore) {
			//score1 = 0;
			//score2 = 0;
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
						if(Integer.parseInt(line[0]) == AstBase.SHIP){
							if(line[line.length - 1].equals("1")){
								ship1 = new Ship(Integer.parseInt(line[2]), Integer.parseInt(line[3]), 0, 0);
								if(line[1].equals("true")){
									ship1.isAlive = true;
								} else {
									ship1.isAlive = false;
								}
								ship1.heading = Integer.parseInt(line[5]);
								ship1.speed = Integer.parseInt(line[4]);
								score1 = Integer.parseInt(line[6]);
								life1 = Integer.parseInt(line[7]);
							} else if(line[line.length - 1].equals("2")){
								ship2 = new Ship(Integer.parseInt(line[2]), Integer.parseInt(line[3]), 0, 0);
								if(line[1].equals("true")){
									ship2.isAlive = true;
								} else {
									ship2.isAlive = false;
								}
								ship2.heading = Integer.parseInt(line[5]);
								ship2.speed = Integer.parseInt(line[4]);
								score2 = Integer.parseInt(line[6]);
								life2 = Integer.parseInt(line[7]);
							} else if(line[line.length - 1].equals("3")){
								rogueShip = new Ship(Integer.parseInt(line[2]), Integer.parseInt(line[3]), 0, 0);
								if(line[1].equals("true")){
									rogueShip.isAlive = true;
								} else {
									rogueShip.isAlive = false;
								}
								rogueShip.heading = Integer.parseInt(line[5]);
								rogueShip.speed = Integer.parseInt(line[4]);
								//score2 = Integer.parseInt(line[6]);
								//life2 = Integer.parseInt(line[7]);
							}
							
						} else if (Integer.parseInt(line[0]) == AstBase.ASTERIOD){
							Asteroid ast = new Asteroid(Integer.parseInt(line[2]), Integer.parseInt(line[3]), Integer.parseInt(line[4]), 0);
							ast.heading = Integer.parseInt(line[5]);
							astList.add(ast);
						} else if (Integer.parseInt(line[0]) == AstBase.ASTERIODSMALL){
							AsteroidSmall ast = new AsteroidSmall(Integer.parseInt(line[2]), Integer.parseInt(line[3]), Integer.parseInt(line[4]), 0);
							ast.heading = Integer.parseInt(line[5]);
							smallAsts.add(ast);
						} else if (Integer.parseInt(line[0]) == AstBase.BULLET){
							Bullet bullet = new Bullet(Integer.parseInt(line[2]), Integer.parseInt(line[3]), Integer.parseInt(line[5]), Integer.parseInt(line[6]));
							if(line[6].equals("3")){
								alienbullets.add(bullet);
							} else {
								bullets.add(bullet);
							}
						} else if (Integer.parseInt(line[0]) == AstBase.ENEMY){
							alien = new AlienShip(Integer.parseInt(line[2]), Integer.parseInt(line[3]), Integer.parseInt(line[4]), Integer.parseInt(line[5]),Integer.parseInt(line[6]));
							if(line[1].equals("true")){
								alien.isAlive = true;
							} else {
								alien.isAlive = false;
							}
							alien.hp = Integer.parseInt(line[7]);
							alientime = Integer.parseInt(line[8]);
						} else if(line[1].equals("gravExt")) {
							if(line[2].equals("true")){
							Setting.gravitationalIsExist = true;
							} else{
								Setting.gravitationalIsExist = false;
							}
						} else if(line[1].equals("gravVis")) {
							if(line[2].equals("true")){
									gravVis = true;
								} else{
									gravVis = false;
								}
							
						}  else if(line[1].equals("level")) {
							level = Integer.parseInt(line[2]);
						} else if (line[1].equals("unlimit")){
							if(line[2].equals("true")){
								Setting.unlimitLife = true;
							} else{
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
					writer.write(ship1.toString() + " " + score1 + " " + life1 + " " + "1"+"\n");
					writer.write(ship2.toString() + " " + score2 + " " + life2 + " " + "2"+"\n");
					writer.write(rogueShip.toString() +  " " + "3"+"\n");
					writer.write(alien.toString() + " " + alien.firerate +" " + alien.hp+ " "+ alientime+"\n");
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
						writer.write(bullet.toString() + " " + bullet.owner + "\n");
					}
					writer.write("0" + " " + "gravExt" + " " + Setting.gravitationalIsExist + "\n");
					writer.write("0" + " " +"gravVis" + " " + gravVis + "\n");
					writer.write("0" + " " +"level" + " " + level + "\n");
					writer.write("0" + " " +"unlimit" + " " + Setting.unlimitLife + "\n");
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

			Setting.astNum = Integer.parseInt(numAstField.getText());

			settingPanel.setVisible(false);
			mainp.requestFocus();

			level = Integer.parseInt(startLevel.getText());

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
		} else if (event.getSource() == quit){
		    int reply = JOptionPane.showConfirmDialog(null,"You sure you want to quit?", "quit", JOptionPane.YES_NO_OPTION);
	        if (reply == JOptionPane.YES_OPTION) {
	          quitB = false;
	          new HighScore(score1, score2, 0);

	        }
		}

	}
	
	@Override
	public void keyPressed(KeyEvent arg0) {
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		

	}


	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}*/

}
