package visMan.database;

import java.sql.*;

import visMan.Visitor;

public class CheckIn extends CreateConnection{
	public void insertNewUser(Visitor visitor){
		PreparedStatement statement = null;
		try {
            statement = conn.prepareStatement("insert into userinfo(name,gender,contact,dob,address,category) values(?,?,?,?,?,?)");
            statement.setString(1, visitor.getName());
            statement.setString(2, visitor.getGender());
            statement.setString(3, visitor.getContact());
            statement.setString(4, visitor.getDateOfBirth());
            statement.setString(5, visitor.getAddress());
            statement.setString(6, visitor.getCategory());
            statement.execute();
            
          
        } catch (SQLException e) {
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {

                }
            }
        }
	}	
	public void insertReport(Visitor visitor){
		PreparedStatement statement = null;
		try {
            statement = conn.prepareStatement("insert into report(name,gender,contact,dob,address,category) values(?,?,?,?,?,?)");
            statement.setString(1, visitor.getName());
            statement.setString(2, visitor.getGender());
            statement.setString(3, visitor.getContact());
            statement.setString(4, visitor.getDateOfBirth());
            statement.setString(5, visitor.getAddress());
            statement.setString(6, visitor.getCategory());
            statement.execute();
        } catch (SQLException e) {
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {

                }
            }
        }
	}	

}
