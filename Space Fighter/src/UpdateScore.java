
import java.sql.*;



public class UpdateScore {
	
	public UpdateScore(String user,int score) {
		
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
				
				try{
					if (conn != null) {
						System.out.println("You made it, take control your database now!");
						//STEP 4: Execute a query
						System.out.println("Creating statement...");
						stmt = conn.createStatement();
						String sql;
						//sql = "INSERT INTO CS408.CS408(user, score) VALUES('test', 20)";
						sql = String.format("INSERT INTO CS408.CS408(user, score) VALUES('%s', %d)",user,score);
						//ResultSet rs = stmt.executeQuery(sql);
						stmt.executeUpdate(sql);
						System.out.println("Finish....");
					     // rs.close();
					      stmt.close();
					      conn.close();
					} else {
						System.out.println("Failed to make connection!");
					}
				}catch(SQLException se){
				    
				      se.printStackTrace();
				}
				
			     
		
	}

}
