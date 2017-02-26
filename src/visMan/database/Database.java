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
                  "category varchar(1) not null," +
          		  "firstVisit timestamp DEFAULT CURRENT_TIMESTAMP," +
                  "lastVisit timestamp DEFAULT CURRENT_TIMESTAMP," +
                  "noOfVisits int not null default 1)";
                  
           stmt.execute(sql);
           sql = "CREATE TABLE if not exists report " +
                   "(uid int not NULL," +
                   " name varchar(255) not null, " + 
                   " purposeOfVisit varchar(255)not null," +
                   "inTimestamp timestamp DEFAULT CURRENT_TIMESTAMP," +
                   "outTimestamp timestamp default 0," +
                   "FOREIGN KEY(uid) REFERENCES userinfo(uid))";
                   
            stmt.execute(sql);
   }
   catch(Exception e){ 
	   System.out.println("Hey");;
   }
}
}
