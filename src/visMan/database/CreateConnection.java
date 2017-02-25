package visMan.database;
import java.sql.*;
public class CreateConnection {

    public static Connection conn;
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
    static final String DB_URL = "jdbc:mysql://localhost/";
    static final String USER = "root";
    static final String PASS = "toor";
    CreateConnection() {
        try {
        	 Class.forName(JDBC_DRIVER);
             conn = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (Exception e) {

        }
    }
}