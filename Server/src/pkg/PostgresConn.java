package pkg;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

public class PostgresConn {
	private static Connection conn;
	private String user = "client";
	private String password = "client";
	private static PostgresConn driver;
	
	private PostgresConn(String user, String password)
	{
		this.user = user;
		this.password = password;
	}
	
	public static PostgresConn getInstance(String user, String password)
	{
		if(conn == null)
			driver = new PostgresConn(user, password);
		return driver;
		
	}
	
	public Connection getConnection()
	{
		if(conn==null)
			try {
				conn = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/dbSD", user, password);
			} catch (SQLException e) {
				System.out.println("Could not connect to the database");
				e.printStackTrace();
			}
		
		return conn;
	}

}
