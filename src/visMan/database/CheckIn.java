package visMan.database;

import java.sql.*;

import visMan.Visitor;
import visMan.utils.Utils;

public class CheckIn extends CreateConnection{
	public Visitor insertNewUser(Visitor visitor){
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
            statement.close();
            statement = conn.prepareStatement("select * from userinfo where name=? and gender=? and contact=? and dob=? and address=? and category=?");
            statement.setString(1, visitor.getName());
            statement.setString(2, visitor.getGender());
            statement.setString(3, visitor.getContact());
            statement.setString(4, visitor.getDateOfBirth());
            statement.setString(5, visitor.getAddress());
            statement.setString(6, visitor.getCategory());
            Visitor vis=Utils.toVisitor(statement.executeQuery());
            insertReport(vis);
            return vis;  
        } catch (SQLException e) {
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {

                }
            }
        }
		return visitor;
	}	
	public void insertReport(Visitor visitor){
		PreparedStatement statement = null;
		try {
            statement = conn.prepareStatement("insert into report(uid,name,purposeOfVisit) values(?,?,?)");
            statement.setInt(1, visitor.getuID());
            statement.setString(2, visitor.getName());
            statement.setString(3, visitor.getPurpose());
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
