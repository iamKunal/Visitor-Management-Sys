package visMan.database;

import java.sql.*;
import javafx.collections.ObservableList;
import visMan.Visitor;
import visMan.utils.Utils;

public class CheckOut extends CreateConnection{
	public ObservableList<Visitor> getCheckOutList(){
		PreparedStatement statement = null;
		try{
	        statement = conn.prepareStatement("select * from userinfo join report on userinfo.uid=report.uid where report.outtimestamp=0;");
	        ResultSet res = statement.executeQuery();
	        return Utils.toVisitorList(res);
		}
		catch(Exception e){
			;
		}
		return null;
	}
	public void checkOut(ObservableList<Visitor> list){
		PreparedStatement statement = null;
		try{
			for(Visitor v : list){
	            statement = conn.prepareStatement("update report SET outTimeStamp=CURRENT_TIMESTAMP where uid=? AND outTimeStamp=0");
	            statement.setInt(1, v.getuID());
	            statement.execute();
	            statement.close();
			}
		}
		catch (Exception e) {
			// TODO: handle exception
		}
	}
}