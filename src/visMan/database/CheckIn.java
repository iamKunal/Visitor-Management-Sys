package visMan.database;

import java.sql.*;

import visMan.Main;
import visMan.Visitor;
import visMan.utils.Utils;

public class CheckIn extends CreateConnection{
	public Visitor insertNewUser(Visitor visitor){
		PreparedStatement statement = null;
		try {
			
            statement = conn.prepareStatement("insert into userinfo(name,gender,contact,dateOfBirth,address,category) values(?,?,?,?,?,?)");
            statement.setString(1, visitor.getName());
            statement.setString(2, visitor.getGender());
            statement.setString(3, visitor.getContact());
            statement.setString(4, visitor.getDateOfBirth());
            statement.setString(5, visitor.getAddress());
            statement.setString(6, visitor.getCategory());
            statement.execute();
            statement.close();
            statement = conn.prepareStatement("select * from userinfo where name=? and gender=? and contact=? and dateofbirth=? and address=? and category=?");
            statement.setString(1, visitor.getName());
            statement.setString(2, visitor.getGender());
            statement.setString(3, visitor.getContact());
            statement.setString(4, visitor.getDateOfBirth());
            statement.setString(5, visitor.getAddress());
            statement.setString(6, visitor.getCategory());
            ResultSet res = statement.executeQuery();
            Visitor vis=Utils.toVisitor(res);
            visitor.setuID(vis.getuID());
            insertReport(visitor);
            statement.close();
            return vis;  
        } catch (Exception e) {
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
	public Visitor alreadyInserted(Visitor visitor){
		PreparedStatement statement = null;
		try {
            statement = conn.prepareStatement("select * from userinfo where name=? and contact=?");// and dateofbirth=?");
            statement.setString(1, visitor.getName());
            statement.setString(2, visitor.getContact());
//            statement.setString(3, visitor.getDateOfBirth());
            ResultSet res = statement.executeQuery();
            Visitor vis=Utils.toVisitor(res);
            statement.close();
            return vis;  
        } catch (Exception e) {
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
            statement = conn.prepareStatement("insert into report(uid,name,purposeOfVisit,location,gateNo) values(?,?,?,?,?)");
            statement.setInt(1, visitor.getuID());
            statement.setString(2, visitor.getName());
            statement.setString(3, visitor.getPurpose());
            statement.setString(4, visitor.getLocation());
            statement.setInt(5, Main.gateNumber);
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
	public boolean isCheckedIn(Visitor oldVisitor){
		PreparedStatement statement = null;
		try {
            statement = conn.prepareStatement("select * from report where uid=? and outTimestamp=0");
            statement.setInt(1, oldVisitor.getuID());
            ResultSet res = statement.executeQuery();
            while(res.next()){
            	return true;
            }
        } catch (SQLException e) {
        	System.out.println("Hello");
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {

                }
            }
        }
		return false;
	}
	public void updateUser(Visitor visitor){
		PreparedStatement statement = null;
		try {
            statement = conn.prepareStatement("update userinfo SET name=?, contact=?, dateofbirth=?, address=? where uid=?");
            statement.setString(1, visitor.getName().trim());
            statement.setString(2, visitor.getContact().trim());
            statement.setString(3, visitor.getDateOfBirth().trim());
            statement.setString(4, visitor.getAddress().trim());
            statement.setInt(5, visitor.getuID());
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