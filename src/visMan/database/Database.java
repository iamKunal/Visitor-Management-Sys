package visMan.database;
import java.sql.*;
public class Database extends CreateConnection{
   public Database(){
   Statement stmt = null;

   try{
      stmt = conn.createStatement();

      String sql = "CREATE DATABASE if not exists VisitorManagement";
      stmt.execute(sql);
      stmt.execute("use VisitorManagement");
       /*sql = "CREATE TABLE if not exists securityOfficials " +
              "(sid int not NULL primary key, " +
              " name varchar(255) not null, " + 
              " address varchar(255)not null, " + 
              " contact_no varchar(255))";
       stmt.execute(sql);
       
       sql = "CREATE TABLE if not exists securityOfficialsCredentials " +
               "(sid int, " +
               " username varchar(255) not null, " + 
               " password varchar(64)not null," +
               "FOREIGN KEY(sid) REFERENCES securityOfficials(sid))";
        stmt.execute(sql);
      
        sql = "CREATE TABLE if not exists admin " +
                "(adid int not NULL primary key, " +
                " name varchar(255) not null, " + 
                " address varchar(255)not null, " + 
                " contact_no varchar(255))";
         stmt.execute(sql);

      
         sql = "CREATE TABLE if not exists adminCredentials " +
                 "(adid int, " +
                 " username varchar(255) not null, " + 
                 " password varchar(64)not null," +
                 "foreign key(adid) references admin(adid))";
          stmt.execute(sql);
         */ 
          sql = "CREATE TABLE if not exists userinfo " +
                  "(uid int not NULL primary key AUTO_INCREMENT, " +
                  "name varchar(255) not null, " + 
                  "gender varchar(1) not null," +
                  "contact varchar(10) not null," +
                  "dateOfBirth date not null,"+
                  " address varchar(255)not null," +                  
          		  "firstVisit timestamp DEFAULT CURRENT_TIMESTAMP," +
                  "lastVisit timestamp DEFAULT CURRENT_TIMESTAMP," +
                  "noOfVisits int not null default 0)";
                  
           stmt.execute(sql);
           sql = "CREATE TABLE if not exists report " +
                   "(SNo int not NULL primary key AUTO_INCREMENT, " +
                   "uid int not NULL," +
                   " name varchar(255) not null," +
                   "category varchar(1) not null," +
                   " purposeOfVisit varchar(255)not null," +
                   " location varchar(255) not null," +
                   " gateNo int not null," +
                   " validUpto date not null," +
                   "inTimestamp timestamp DEFAULT CURRENT_TIMESTAMP," +
                   "outTimestamp timestamp default 0," +
                   "FOREIGN KEY(uid) REFERENCES userinfo(uid))";
                   
            stmt.execute(sql);
            
            sql = "CREATE TABLE if not exists validity " +
                    "(uid int primary key," +
                    "category varchar(1) not null," +
                    " purposeOfVisit varchar(255)not null," +
                    " location varchar(255) not null," +
                    " gateNo int not null," +
                    " validUpto date not null check (validupto>CURDATE()+1)," +
                    "FOREIGN KEY(uid) REFERENCES userinfo(uid))";
             stmt.execute(sql); 
             sql = "SET GLOBAL event_scheduler = ON ";
             stmt.execute(sql);
             sql = "CREATE  EVENT IF NOT EXISTS `RemoveInvalid` ON SCHEDULE EVERY 1 DAY STARTS CONCAT(DATE(NOW()), ' 00:00:01') ENDS CONCAT(DATE(NOW()+INTERVAL 19 YEAR ), ' 00:00:01') ON COMPLETION PRESERVE ENABLE " +
            		 "DO DELETE FROM validity where validUpto<CURDATE()";
             stmt.execute(sql);
   }
   catch(Exception e){ 
	   System.out.println("Hey");;
   }
}
}
