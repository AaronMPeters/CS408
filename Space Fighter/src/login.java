import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.*;


public class login {
	
	JFrame f, f2;
	JPanel p, p2, p3, p4, p5, p6, p7;
	JLabel userName, passWord, regName, regPass, regAge, regMail;
	JButton login, cancel, reg, newAcc, backToLogin;
	JTextField txName,txPassword, txname, txpass, txage, txmail;
	File userInfo;
	FileWriter out;
	profile newUser = new profile();
	GUI g;
	
	public void createloginFrame(GUI g){
		f = new JFrame("Player Login");
		f.setSize(300, 150);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		createloginPanel();
		f.add(p);
		f.setLocationRelativeTo(null);
		f.setVisible(true);
		this.g = g;
	}
	
	
	public void createloginPanel(){
		createloginButton();
		createloginLabel();
		p  = new JPanel(new BorderLayout());
		p2 = new JPanel(new GridLayout(1,3));
		p3 = new JPanel(new GridLayout(2,2));
		

		p2.add(login);
		p2.add(cancel);
		p2.add(reg);
		
		p3.add(userName);
		p3.add(txName);
		p3.add(passWord);
		p3.add(txPassword);

		p.add(p2,BorderLayout.SOUTH);
		p.add(p3,BorderLayout.CENTER);
	}
	
	public void createloginLabel(){
		userName = new JLabel("User");
		userName.setBounds(10, 10, 80, 25);
		
		passWord = new JLabel("Password");
		passWord.setBounds(10, 40, 80, 25);
		
		txName = new JTextField("",20);
		txName.setBounds(100, 10, 160, 25);
		txPassword = new JPasswordField("",20);
		txPassword.setBounds(100, 40, 160, 25);
	}
	
	public void createloginButton(){
		login = new JButton("Login");
		login.setBounds(10, 80, 80, 25);
		login.addActionListener(
	    		new ActionListener(){
	    			public void actionPerformed(ActionEvent e) {
	    				Object o = e.getSource();
					    if( o == login ){
					    	userLogin(txName.getText(), txPassword.getText());
					    }
					}
	    		}
	    );
		
		cancel = new JButton("Exit");
		cancel.setBounds(10, 80, 80, 25);
		cancel.addActionListener(
	    		new ActionListener(){
	    			public void actionPerformed(ActionEvent e) {
	    				Object o = e.getSource();
					    if( o == cancel ){
					    	//may need to change-------------------------------------------------
					    	System.exit(0);
					    }
					}
	    		}
	    );
		
		reg = new JButton("Register");
		reg.setBounds(190, 80, 80, 25);
		reg.addActionListener(
	    		new ActionListener(){
	    			public void actionPerformed(ActionEvent e) {
	    				Object o = e.getSource();
					    if( o == reg ){
					    	userReg();
					    }
					}
	    		}
	    );
	}
	
	
	public void userReg(){
		f2 = new JFrame("Create New Player");
		f2.setSize(400, 200);
		f2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		p5 = new JPanel(new BorderLayout());
		p6 = new JPanel(new GridLayout(4,2));
		p7 = new JPanel(new GridLayout(1,2));
		
		regName = new JLabel("Username: ");
		regName.setBounds(10, 10, 80, 25);
		regPass = new JLabel("Password: ");
		regPass.setBounds(10, 10, 80, 25);
		regAge = new JLabel("Age: ");
		regAge.setBounds(10, 10, 80, 25);
		regMail = new JLabel("E-mail Address: ");
		regMail.setBounds(10, 10, 80, 25);
		
		txname = new JTextField(20);
		txname.setBounds(100, 10, 160, 25);
		txpass = new JTextField(20);
		txpass.setBounds(100, 10, 160, 25);
		txage = new JTextField(20);
		txage.setBounds(100, 10, 160, 25);
		txmail = new JTextField(20);
		txmail.setBounds(100, 10, 160, 25);
		
		newAcc = new JButton("Enter");
		newAcc.setBounds(10, 80, 80, 25);
		newAcc.addActionListener(
	    		new ActionListener(){
	    			public void actionPerformed(ActionEvent e) {
	    				Object o = e.getSource();
					    if( o == newAcc ){
					    	checkValid(txname.getText(), txpass.getText(), txage.getText(), txmail.getText());
					    }
					}
	    		}
	    ); 
		
		backToLogin =  new JButton("Back");
		backToLogin.setBounds(10, 80, 80, 25);
		backToLogin.addActionListener(
	    		new ActionListener(){
	    			public void actionPerformed(ActionEvent e) {
	    				Object o = e.getSource();
					    if( o == backToLogin ){
					    	f2.setVisible(false);
					    	f.setVisible(true);
					    }
					}
	    		}
	    ); 
		
		p6.add(regName);
		p6.add(txname);
		p6.add(regPass);
		p6.add(txpass);
		p6.add(regAge);
		p6.add(txage);
		p6.add(regMail);
		p6.add(txmail);
		
		p7.add(newAcc);
		p7.add(backToLogin);
		
		p5.add(p7,BorderLayout.SOUTH);
		p5.add(p6,BorderLayout.CENTER);
		
		f2.add(p5);
		f2.setLocationRelativeTo(null);
		f2.setVisible(true);
		f.setVisible(false);
	}
	
	
	public void userLogin(String name, String password){
		
	    try {
	        File userInfo = new File("UserInfor.txt");
	        Scanner read = null;
	        int found = 0;
	        read = new Scanner(userInfo);
	        while(read.hasNextLine()){
	          String line=read.nextLine();
	          if(("User "+name+" "+password).equals(line)){
	        	read.next();
	        	String curAge = read.next();
	        	String curMail = read.next();
	            found = 1;
	            newUser.inforLoad(name, curMail, Integer.parseInt(curAge) );
	            JOptionPane.showMessageDialog(null, "You have successfully logged in!", "Welcome", JOptionPane.PLAIN_MESSAGE);
	            f.setVisible(false);
	            g.settingPanel.setVisible(true);
	            // pop up the main menu here-----------------------------------------------------
	          }
	        }
	        if(found == 0){
	          JOptionPane.showMessageDialog(null, "Wrong User Name or Wrong Password", "Error", JOptionPane.ERROR_MESSAGE);
	        }
	      } catch (FileNotFoundException e) {}
	}


	public void checkValid(String name, String password, String age, String mail){
		try {      
		      //may need some extra rules here----------------------------------------------------
		      if(!name.equals("null") && !name.equals("") &&
		    	 !password.equals("null") && !password.equals("") &&
		         !age.equals("null") && !age.equals("") &&
		    	 !mail.equals("null") && !mail.equals("")){	  
		    	  
		    	  if(name.length()<6 || name.length()>20){
					  JOptionPane.showMessageDialog(null, "The length of password needs to be longer than 6 and shorter than 20.", "Error", JOptionPane.PLAIN_MESSAGE);
					  return;
				  }
				  else if(password.length()<6 || password.length()>20){
					  JOptionPane.showMessageDialog(null, "The length of username needs to be longer than 6 and shorter than 20.", "Error", JOptionPane.PLAIN_MESSAGE);
					  return;
				  }
		    	  
		    	  try { 
		    	        Integer.parseInt(age); 
		    	  } catch(NumberFormatException e) { 
		    	    	JOptionPane.showMessageDialog(null, "Please enter a valid number for age!", "Error", JOptionPane.PLAIN_MESSAGE);
						return;
		    	  }
		    	  if(Integer.parseInt(age) >= 65 && Integer.parseInt(age) <= 13){
		    		  JOptionPane.showMessageDialog(null, "Sorry, you must be older than 12 and younger than 65 to play this game due to government regulations.", "Error", JOptionPane.PLAIN_MESSAGE);
					  return;
		    	  }
		    	  if(mailCheck(mail) == false){
		    		  JOptionPane.showMessageDialog(null, "Please enter a valid e-mail address!", "Error", JOptionPane.PLAIN_MESSAGE);
					  return;
		    	  }
		    	  if(name.equals("foobar")){
					  return;
		    	  }
		    	  if(password.equals("barfoo")){
					  return;
		    	  }
		    	  	    	  
		    	  storeUserInfo();
			      /*Scanner read = new Scanner(userInfo);
			      while(read.hasNext()){
			        String check = read.next();
			        if(check.equals("User")){
			          check = read.next();
			          if(check.equals(name) || !userInfo.isFile()){
			            JOptionPane.showMessageDialog(null, "The user name is already existed!", "Error", JOptionPane.ERROR_MESSAGE);
			            return;
			          }
			        }
			      }*/
		    	  out.write("User "+name+" "+password+"\n" + "infor"+" "+age+" "+mail+"\n");// check if we need age or mail saved
		    	  out.close();
		    	  JOptionPane.showMessageDialog(null, "You have successfully created a new account!", "Welcome", JOptionPane.PLAIN_MESSAGE);
		    	  f2.setVisible(false);
		    	  f.setVisible(true);
		    	  return;
		      }
		      else{
		    	  JOptionPane.showMessageDialog(null, "Please fill all the necessary information.", "Error", JOptionPane.PLAIN_MESSAGE);
		    	  return;
		      }
		    } catch (IOException e) {}
	}
	
	
	public boolean mailCheck(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
	}
	
	
	public void storeUserInfo(){
	    userInfo = new File("UserInfor.txt");
	    try{
	      out = new FileWriter(userInfo,true);
	    }catch(Exception e){}
	}
}
