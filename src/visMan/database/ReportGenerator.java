package visMan.database;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import visMan.utils.Utils;

import java.io.File;
import java.io.PrintWriter;
import java.sql.*;

public class ReportGenerator extends CreateConnection {
	private final static String FOLDERNAME="reportlogs";
	private static DateTimeFormatter dtf=DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-YYYY");;
	private final static String header[]={"S.No.","VisitorID","Name","Category","Location","Purpose of Visit","In Time","Out Time","Pass Validity","Gate Number","Gender","Contact","Date of Birth","Address",};
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Database db = new Database();
//        LocalDate today = LocalDate.now();
//        String subFolder = String.format("%02d",today.getMonthValue()) + "-"+today.getYear();
        dailyReport();
        LocalDate today = LocalDate.now().minusMonths(1);
        File f = new File(FOLDERNAME + "/" + today.getYear() + "-" + String.format("%02d",today.getMonthValue()) + "/" +Utils.toTitleCase(today.getMonth().name().toLowerCase())+"-"+today.getYear()+".csv");
        if(!f.isFile())
        	monthlyReport();
	}
	private static String rowString(String [] toWrite){
		ArrayList<String> mod= new ArrayList<>();
		for(String s : toWrite){
			s="\""+s+"\"";
			mod.add(s);
		}
		String writeIt=String.join(",", mod);
		return writeIt;
	}
	private static boolean dailyReport(){
		Statement statement = null;
		String sql;
		try {
	
			statement = conn.createStatement();
			sql = "SELECT report.uid,report.name,category,location,purposeOFVisit,inTimeStamp,outTimeStamp,validUpto,gateNo,gender,contact,dateOfBirth,address "+
					"FROM userinfo RIGHT JOIN report ON userinfo.uid=report.uid WHERE DATE(inTimeStamp)=CURDATE()-INTERVAL 1 DAY OR DATE(outTimeStamp)=CURDATE()-INTERVAL 1 DAY ORDER BY inTimeStamp ASC;";
	        ResultSet res=statement.executeQuery(sql);
	        LocalDate today = LocalDate.now();
	        today=today.minusDays(1);
	        String subFolder = String.format("%02d",today.getMonthValue()) + "-"+today.getYear();
	//        f.(rowString(header));
	        File f=new File(FOLDERNAME);
	        f.mkdir();
	        f=new File(FOLDERNAME + "/" +   today.getYear() + "-" + String.format("%02d",today.getMonthValue()));
	        f.mkdir();
	        f = new File(FOLDERNAME + "/" + today.getYear() + "-" + String.format("%02d",today.getMonthValue()) + "/" + String.format("%02d",today.getDayOfMonth())+"-"+subFolder+".csv");
	        f.createNewFile();
	        PrintWriter p = new PrintWriter(f);
	        p.println(rowString(header));
	        ArrayList<String> row = new ArrayList<>();
	        int sNo=1;
	        while(res.next()){
	        	row.add(Integer.toString(sNo));
	        	row.add(res.getString("uid"));
	        	row.add(res.getString("name"));
	        	row.add(res.getString("category"));
	        	row.add(res.getString("location"));
	        	row.add(res.getString("purposeOfVisit"));
	        	row.add(res.getTimestamp("inTimeStamp").toLocalDateTime().format(dtf));
	        	try{
	        	row.add(res.getTimestamp("outTimeStamp").toLocalDateTime().format(dtf));
	        	}
	        	catch (NullPointerException e) {
					row.add("Not Checked Out");
				}
	        	row.add(res.getTimestamp("validUpto").toLocalDateTime().format(dtf));
	        	row.add(res.getString("gateNo"));
	        	row.add(res.getString("gender"));
	        	row.add(res.getString("contact"));
	        	row.add(res.getDate("dateOfBirth").toLocalDate().format(DateTimeFormatter.ofPattern("dd-MM-YYYY")));
	        	row.add(res.getString("address"));
	        	p.println(rowString(row.toArray(new String[row.size()])));
	        	row.clear();
	        	sNo++;
	        }
	        p.close();
	        return true;
	    } catch (Exception e) {
	    	e.printStackTrace();
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
	private static boolean monthlyReport(){
		Statement statement = null;
		String sql;
		try {
	
			statement = conn.createStatement();
			sql = "SELECT report.uid,report.name,category,location,purposeOFVisit,inTimeStamp,outTimeStamp,validUpto,gateNo,gender,contact,dateOfBirth,address "+
					"FROM userinfo RIGHT JOIN report ON userinfo.uid=report.uid WHERE MONTH(inTimeStamp)=MONTH(CURDATE())-1 OR MONTH(outTimeStamp)=MONTH(CURDATE())-1 ORDER BY inTimeStamp ASC;";
	        ResultSet res=statement.executeQuery(sql);
	        LocalDate today = LocalDate.now().minusMonths(1);
//	        String subFolder = String.format("%02d",today.getMonthValue()) + "-"+today.getYear();
	//        f.(rowString(header));
	        File f=new File(FOLDERNAME);
	        f.mkdir();
	        f=new File(FOLDERNAME + "/" +  today.getYear() + "-" + String.format("%02d",today.getMonthValue()) );
	        f.mkdir();
	        f = new File(FOLDERNAME + "/" + today.getYear() + "-" + String.format("%02d",today.getMonthValue()) + "/" +Utils.toTitleCase(today.getMonth().name().toLowerCase())+"-"+today.getYear()+".csv");
	        f.createNewFile();
	        PrintWriter p = new PrintWriter(f);
	        p.println(rowString(header));
	        ArrayList<String> row = new ArrayList<>();
	        int sNo=1;
	        while(res.next()){
	        	row.add(Integer.toString(sNo));
	        	row.add(res.getString("uid"));
	        	row.add(res.getString("name"));
	        	row.add(res.getString("category"));
	        	row.add(res.getString("location"));
	        	row.add(res.getString("purposeOfVisit"));
	        	row.add(res.getTimestamp("inTimeStamp").toLocalDateTime().format(dtf));
	        	try{
	        	row.add(res.getTimestamp("outTimeStamp").toLocalDateTime().format(dtf));
	        	}
	        	catch (NullPointerException e) {
					row.add("Not Checked Out");
				}
	        	row.add(res.getTimestamp("validUpto").toLocalDateTime().format(dtf));
	        	row.add(res.getString("gateNo"));
	        	row.add(res.getString("gender"));
	        	row.add(res.getString("contact"));
	        	row.add(res.getDate("dateOfBirth").toLocalDate().format(DateTimeFormatter.ofPattern("dd-MM-YYYY")));
	        	row.add(res.getString("address"));
	        	p.println(rowString(row.toArray(new String[row.size()])));
	        	row.clear();
	        	sNo++;
	        }
	        p.close();
	        if(f.exists() &&f.length()>0){
	        	sql="DELETE FROM report WHERE MONTH(inTimeStamp)=MONTH(CURDATE())-1 AND outTimeStamp!=0";
	        	statement.execute(sql);
	        }
	        return true;
	    } catch (Exception e) {
	    	e.printStackTrace();
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
}
