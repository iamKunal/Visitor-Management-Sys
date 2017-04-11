package visMan;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;

import java.io.File;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.ResourceBundle;


import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ManualReportController extends CreateConnection implements Initializable {
	private final static String FOLDERNAME="reportlogs";
	private static DateTimeFormatter dtf=DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-YYYY");;
	private final static String header[]={"S.No.","VisitorID","Name","Category","Location","Purpose of Visit","In Time","Out Time","Pass Validity","Gate Number","Gender","Contact","Date of Birth","Address",};
	@FXML
	private DatePicker datePicker;
	@FXML
	private Button gerenateButton;
	@FXML
	private Button cancelButton;
	private PreparedStatement prepare=null;
    @FXML
    private Label numberOfReports;
	// Event Listener on Button[#gerenateButton].onAction
	@FXML
	public void generateReport() {
	PreparedStatement statement = null;
	String sql;
	try {

		sql = "SELECT report.uid,report.name,category,location,purposeOFVisit,inTimeStamp,outTimeStamp,validUpto,gateNo,gender,contact,dateOfBirth,address "+
				"FROM userinfo RIGHT JOIN report ON userinfo.uid=report.uid WHERE DATE(inTimeStamp)=? OR DATE(outTimeStamp)=? ORDER BY inTimeStamp ASC;";
		
		statement = conn.prepareStatement(sql);
		statement.setDate(1, Date.valueOf(datePicker.getValue()));
		statement.setDate(2, Date.valueOf(datePicker.getValue()));
		ResultSet res=statement.executeQuery();
        LocalDate today = datePicker.getValue();
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
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Report Generated");
        alert.setHeaderText(null);
        alert.setContentText("Report for " + datePicker.getValue()+ " has been successfully generated!");

        alert.showAndWait();
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

	}
	// Event Listener on Button[#cancelButton].onAction
	@FXML
	public void cancelExecute() {
        Stage currentStage = (Stage) cancelButton.getScene().getWindow();
        currentStage.close();
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
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		datePicker.valueProperty().addListener((o,oldValue,newValue) -> {
			if(newValue!=null){
//				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
				LocalDate localDate = LocalDate.now().minusDays(1);
				if(ChronoUnit.DAYS.between(newValue,localDate)<0 || ChronoUnit.YEARS.between(newValue,localDate)>150)
					datePicker.setValue(localDate);
				try{
					prepare = conn.prepareStatement("SELECT count(*) FROM report WHERE DATE(inTimeStamp)=? OR DATE(outTimeStamp)=?");
					prepare.setDate(1, Date.valueOf(datePicker.getValue()));
					prepare.setDate(2, Date.valueOf(datePicker.getValue()));
					ResultSet res = prepare.executeQuery();
					while(res.next()){
						numberOfReports.setText(res.getInt(1) + " entries for that day.");
					}
				}
				catch (Exception e) {
					// TODO: handle exception
				}
			}
		});
		datePicker.setValue(LocalDate.now().minusDays(1));
	}
}
