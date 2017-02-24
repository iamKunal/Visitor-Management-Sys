package visMan;
import java.sql.*;
public class Database {
   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
   static final String DB_URL = "jdbc:mysql://localhost/";
   static final String USER = "root";
   static final String PASS = "toor";
   
   public static void main(String[] args) {
   Connection conn = null;
   Statement stmt = null;
   try{
      Class.forName(JDBC_DRIVER);
      conn = DriverManager.getConnection(DB_URL, USER, PASS);
      stmt = conn.createStatement();
      String sql = "CREATE DATABASE if not exists VisitorManagement";
      stmt.execute(sql);
      stmt.execute("use VisitorManagement");
       sql = "CREATE TABLE if not exists securityOfficials " +
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
          
          sql = "CREATE TABLE if not exists userinfo " +
                  "(uid int not NULL primary key, " +
                  " name varchar(255) not null, " + 
                  " address varchar(255)not null," +
                  "gender varchar(255) not null," +
                  "firstVisit timestamp DEFAULT CURRENT_TIMESTAMP," +
                  "lastVisit timestamp DEFAULT CURRENT_TIMESTAMP," +
                  "contact_no varchar(10) not null," +
                  "purposeOfVisit varchar(255)not null," +
                  "noOfVisits int not null default 1," +
                  "category varchar(255) not null)";
              
           stmt.execute(sql);
   
           sql = "CREATE TABLE if not exists report " +
                   "(uid int not NULL," +
                   " name varchar(255) not null, " + 
                   " purposeOfVisit varchar(255)not null," +
                   "inTimestamp timestamp," +
                   "outTimestamp timestamp default 0," +
                   "FOREIGN KEY(uid) REFERENCES userinfo(uid))";
                   
            stmt.execute(sql);
            
            sql="create trigger after_insert_userinfo after insert " +
            	"on userinfo "+
            	"for each row "+
            	"begin "+
            	"insert into report values (NEW.uid,NEW.name,NEW.purposeOfVisit,NEW.firstVisit,0);"+
            	"end";
            	
            stmt.execute(sql);
   }
   catch(Exception e){ 
	   ;
   }
}
}
