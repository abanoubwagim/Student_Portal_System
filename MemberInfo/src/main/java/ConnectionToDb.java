import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionToDb {
	
    private final static String DRIVER_NAME="com.mysql.cj.jdbc.Driver";
    private final static String URL="jdbc:mysql://localhost/servletdb";
    private final static String PASSWORD = "";
    private final static String USERNAME = "root";
    
    private static Connection connection = null;	
	
    public static Connection connect() {
        try {
            // Ensure the MySQL JDBC driver is loaded
            Class.forName(DRIVER_NAME);

            // Attempt to establish a connection
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
           
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC Driver not found: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
        }

        // Check if connection is null
        if (connection == null) {
            System.err.println("Connection is null, cannot proceed.");
        }
        return connection;
    }


	
	
        
	
	
	
}




