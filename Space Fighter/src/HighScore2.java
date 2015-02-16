import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;


public class HighScore2 extends JFrame implements ActionListener{
	private JPanel mainPanel;
	private JButton okButton;
	
	
	public HighScore2() {
		// TODO Auto-generated constructor stub
		 String dbName = "CS408";
		  String userName = "TEST";
		  String password = "TESTTEST";
		  String hostname = "test.cuiczhxpopzs.us-west-2.rds.amazonaws.com";
		  String port = "3306";
		  String jdbcUrl = "jdbc:mysql://test.cuiczhxpopzs.us-west-2.rds.amazonaws.com:3306/?user=TEST&password=" + password;
		  
		   Connection conn = null;
		   Statement stmt = null;
		  
		System.out.println("-------- MySQL JDBC Connection Testing ------------");
		 
		try {
			String driver = "com.mysql.jdbc.Driver";
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			System.out.println("Where is your MySQL JDBC Driver?");
			e.printStackTrace();
			return;
		}
	 
		System.out.println("MySQL JDBC Driver Registered!");
		try {		
			//System.out.println("jdbcUrl: " + jdbcUrl);
			conn = DriverManager
			.getConnection(jdbcUrl);
			System.out.println("check");
			
		} catch (SQLException e) {
			System.out.println("Connection: " + conn);
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		}
		mainPanel = new JPanel(new GridLayout(10, 1,10,10));
		try{
			if (conn != null) {
				System.out.println("You made it, take control your database now!");
				//STEP 4: Execute a query
				System.out.println("Creating statement...");
				stmt = conn.createStatement();
				String sql;
				sql = "SELECT * FROM CS408.CS408 ORDER BY score DESC LIMIT 10";
				ResultSet rs = stmt.executeQuery(sql);
				
				
				
				//new UpdateScore("test1",201);
				//STEP 5: Extract data from result set
			      while(rs.next()){
			         //Retrieve by column name
			         int id  = rs.getInt("id");
			         int score = rs.getInt("score");
			         String user = rs.getString("user");
			         
			         mainPanel.add(new JLabel(user));
			         mainPanel.add(new JLabel(String.valueOf(score)));
			         
			         //Display values
			         System.out.print("\nID: " + id);
			         System.out.print(", user: " + user);
			         System.out.print(", score: " + score);
			        // System.out.print("test");
			         
			         
			      }
			      //STEP 6: Clean-up environment
			      rs.close();
			      stmt.close();
			      conn.close();
			} else {
				System.out.println("Failed to make connection!");
			}
		}catch(SQLException se){
		    
		      se.printStackTrace();
		}
		
	     
		
		okButton = new JButton("OK");
		okButton.addActionListener(this);
		mainPanel.add(okButton);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setSize(400,300);
		this.add(mainPanel);
		this.setTitle("Top 10");
		this.setVisible(true);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		
	}

	
}
