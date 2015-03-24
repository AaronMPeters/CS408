import java.awt.BorderLayout;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

import javax.swing.*;



public class profile {
	
	  JFrame profile;
	  JPanel p,p1,p2,p3,p4,p5;
	  JButton back;
	  JLabel Name, Age, Mail, Score, Rank;
	  String curName, curMail;
	  int curAge, curScore, curRank;
	  Point point;
	  
	  public void screenSize(){
		    GraphicsEnvironment ge=GraphicsEnvironment.getLocalGraphicsEnvironment();
		    point=ge.getCenterPoint();
		  }
	  
	  public void inforLoad(String name, String mail, int age){
		  //score rank needed here----------------------------------------------------------------
		  curName = name;
		  curMail = mail;
		  curAge = age;
		  curScore = 0; 
		  curRank = 0;
	  }
	  
	  public void showProfile(){
		    profile = new JFrame("Player Profile");
		    createPanel();
		    profile.add(p);
		    profile.setSize(450,350);
		    screenSize();
		    profile.setLocation(point.x-450/2,point.y-350/2);
		    profile.setVisible(true);
	  }
	
	
	  public void createPanel(){
		    createLabel(); 
		    createButton();
		    p  = new JPanel(new BorderLayout());
		    p1 = new JPanel(new GridLayout(3,1));
		    p2 = new JPanel(new GridLayout(2,1));
		    p3 = new JPanel(new GridLayout(1,2));
		    p4 = new JPanel();
		    p1.add(Name);
		    p1.add(Age);
		    p1.add(Mail);
		    p2.add(Score);
		    p2.add(Rank);
		    p3.add(p1);
		    p3.add(p2);
		    p4.add(back);
		    p.add(p3,BorderLayout.CENTER);
		    p.add(p4,BorderLayout.SOUTH);
	  }
	
	  public void createLabel(){
		  	Name= new JLabel("    Name: "+curName);
		    Mail = new JLabel("    E-Mail: "+curMail);
		    Age = new JLabel("    Age: "+curAge);
		    Score = new JLabel("Highest Score: "+curScore);
		    Rank = new JLabel("Rank: "+curRank);
	  }
	  
	  
	  public void createButton(){
		    back = new JButton("Back To Menu");
		    back.addActionListener(
		    		new ActionListener(){
		    			public void actionPerformed(ActionEvent e) {
		    				Object o = e.getSource();
						    if( o == back ){
						    	//back to the main menu----------------------------------------
						    	profile.setVisible(false);
						    	System.exit(0);
						    }
						}
		    		}
		    );
	  }
	

	public static void main(String[] args) {
		//testing-----------------------
		 //profile newUser = new  profile();
		   //newUser.showProfile();
	}
}
