package visMan;

import visMan.database.CheckOut;
import visMan.utils.Utils;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class MainController implements Initializable {
	public void deleteTempData(){
        File tempFile = new File("temp.jpg");
        tempFile.delete();
        tempFile = new File("temp.png");
        tempFile.delete();
        File directory = new File(String.valueOf(Main.IMGDB));
        if (! directory.exists()){
            directory.mkdir();
        }
	}
	@FXML ToggleGroup gateNo;
	@FXML VBox mainRoot;
	@FXML Button goButton;

    @FXML
    void manualReportScene() {
    	try{
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("ManualReport.fxml"));
	        Parent manualReportRoot = (Parent) loader.load();
	        Scene manualReport = new Scene(manualReportRoot);
			manualReport.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	//        NewUserController controller = (NewUserController) loader.getController();
	//                control.initData(selectedSong);
	        Stage stager = new Stage();
	        stager.setScene(manualReport);
	//        stager.initStyle(StageStyle.UNDECORATED);
	        stager.setResizable(false);
	        stager.initModality(Modality.WINDOW_MODAL);
	        stager.initOwner(goButton.getScene().getWindow());
	//		stager.setOnCloseRequest((new EventHandler<WindowEvent>() {
	//			public void handle(WindowEvent we)
	//			{
	//		        mainRoot.setDisable(false);
	//		        if(NewUserController.openStage!=null){
	//		        	NewUserController.openStage.close();
	//		        	NewUserController.openStage=null;
	//		        }
	//			}
	//		}));
	        stager.setTitle("Manual Report Generation");
	        stager.showAndWait();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
    }
	@FXML
	void submitGo(){
        if(Utils.getToggleText(check).equals("Checkin")){
        	if(Utils.getToggleText(user).equals("New Visitor")){
        		try
        		{
	    			// load the FXML resource
	                FXMLLoader loader = new FXMLLoader(getClass().getResource("NewUser.fxml"));
	                Parent newUserRoot = (Parent) loader.load();
	                Scene newUser = new Scene(newUserRoot);
	    			newUser.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
//	                NewUserController controller = (NewUserController) loader.getController();
	//                control.initData(selectedSong);
	                Stage stager = new Stage();
	                stager.setScene(newUser);
//	                stager.initStyle(StageStyle.UNDECORATED);
	                stager.setResizable(false);
	                stager.initModality(Modality.WINDOW_MODAL);
	                stager.initOwner(goButton.getScene().getWindow());
	    			stager.setOnHiding((new EventHandler<WindowEvent>() {
	    				public void handle(WindowEvent we)
	    				{
	    			        mainRoot.setDisable(false);
//	    			        if(NewUserController.openStage!=null){
//	    			        	NewUserController.openStage.close();
//	    			        	NewUserController.openStage=null;
//	    			        }
	    				}
	    			}));
//	    			stager.setOnCloseRequest((new EventHandler<WindowEvent>() {
//	    				public void handle(WindowEvent we)
//	    				{
//	    			        mainRoot.setDisable(false);
//	    			        if(NewUserController.openStage!=null){
//	    			        	NewUserController.openStage.close();
//	    			        	NewUserController.openStage=null;
//	    			        }
//	    				}
//	    			}));
	                stager.setTitle("Checkin New Visitor");
	                stager.showAndWait();
	    		}
	    		catch (Exception e)
	    		{
	    			e.printStackTrace();
	    		}
        	}
        	else if(Utils.getToggleText(user).equals("Old Visitor")){
        		try
        		{
	    			// load the FXML resource
	                FXMLLoader loader = new FXMLLoader(getClass().getResource("OldUser.fxml"));
	                Parent oldUserRoot = (Parent) loader.load();
	                Scene oldUser = new Scene(oldUserRoot);
	    			oldUser.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
//	                OldUserController controller = (OldUserController) loader.getController();
	//                control.initData(selectedSong);
	                Stage stager = new Stage();
	                stager.setScene(oldUser);
//	                stager.initStyle(StageStyle.UNDECORATED);
	                stager.setResizable(false);
	                stager.initModality(Modality.WINDOW_MODAL);
	                stager.initOwner(goButton.getScene().getWindow());
	    			stager.setOnHiding((new EventHandler<WindowEvent>() {
	    				public void handle(WindowEvent we)
	    				{
	    			        mainRoot.setDisable(false);
//	    			        if(NewUserController.openStage!=null){
//	    			        	NewUserController.openStage.close();
//	    			        	NewUserController.openStage=null;
//	    			        }
	    				}
	    			}));
//	    			stager.setOnCloseRequest((new EventHandler<WindowEvent>() {
//	    				public void handle(WindowEvent we)
//	    				{
//	    			        mainRoot.setDisable(false);
//	    			        if(NewUserController.openStage!=null){
//	    			        	NewUserController.openStage.close();
//	    			        	NewUserController.openStage=null;
//	    			        }
//	    				}
//	    			}));
	                stager.setTitle("Checkin Old Visitor");
	                stager.showAndWait();
	    		}
	    		catch (Exception e)
	    		{
	    			e.printStackTrace();
	    		}
        	}
        }
        else{
        	try
    		{
        		CheckOut ch = new CheckOut();
        		if(ch.getCheckOutList()==null || ch.getCheckOutList().size()==0){
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Checkout Error");
					alert.setHeaderText("No Visitor Checked In.");
					alert.setContentText("There is no Visitor currently Checked In.");
					alert.showAndWait();
        		}
        		else{
	    			// load the FXML resource
	                FXMLLoader loader = new FXMLLoader(getClass().getResource("CheckOut.fxml"));
	                Parent checkOutRoot = (Parent) loader.load();
	                Scene checkOut = new Scene(checkOutRoot);
	    			checkOut.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	//                NewUserController controller = (NewUserController) loader.getController();
	//                control.initData(selectedSong);
	                Stage stager = new Stage();
	                stager.setScene(checkOut);
	//                stager.initStyle(StageStyle.UNDECORATED);
	                stager.setResizable(false);
	                stager.initModality(Modality.WINDOW_MODAL);
	                stager.initOwner(goButton.getScene().getWindow());
	    			stager.setOnHiding((new EventHandler<WindowEvent>() {
	    				public void handle(WindowEvent we)
	    				{
	    			        mainRoot.setDisable(false);
	//    			        if(NewUserController.openStage!=null){
	//    			        	NewUserController.openStage.close();
	//    			        	NewUserController.openStage=null;
	//    			        }
	    				}
	    			}));
	//    			stager.setOnCloseRequest((new EventHandler<WindowEvent>() {
	//    				public void handle(WindowEvent we)
	//    				{
	//    			        mainRoot.setDisable(false);
	//    			        if(NewUserController.openStage!=null){
	//    			        	NewUserController.openStage.close();
	//    			        	NewUserController.openStage=null;
	//    			        }
	//    				}
	//    			}));
	                stager.setTitle("Check Out Visitor");
	                stager.showAndWait();
        		}
    		}
    		catch (Exception e)
    		{
    			e.printStackTrace();
    		}
        }
        deleteTempData();
		
	}
	@FXML ToggleGroup user;
	@FXML ToggleGroup check;

	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		deleteTempData();
		/*---------------------Initalizations--------------------------------------------*/
		for(Toggle rb : user.getToggles()){
			RadioButton r = (RadioButton) rb;
			r.setVisible(false);
		}
		check.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			@Override
			public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
				// TODO Auto-generated method stub
//				System.out.println(getToggleText(newValue));
				boolean visibleValue=true;
				if(Utils.getToggleText(newValue).equals("Checkout")){
					visibleValue=false;
				}
				for(Toggle rb : user.getToggles()){
					RadioButton r = (RadioButton) rb;
					r.setVisible(visibleValue);
				}
				if(Utils.getToggleText(user).equals("null") && visibleValue){
					goButton.setDisable(true);
				}
				if(!visibleValue)
					goButton.setDisable(visibleValue);
			}
		});
		user.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {@Override
			public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
				// TODO Auto-generated method stub
			if(!Utils.getToggleText(newValue).equals("null")){
				goButton.setDisable(false);
			}
			}
		});
		gateNo.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			@Override
			public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
				// TODO Auto-generated method stub
				String gate=((RadioButton)newValue.getToggleGroup().getSelectedToggle()).getText();
				if(gate.contains("1")){
					Main.gateNumber=1;
				}
				else
					Main.gateNumber=2;
			}
		});
//		user.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
//			@Override
//			public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
//				// TODO Auto-generated method stub
//			       RadioButton chk = (RadioButton)newValue.getToggleGroup().getSelectedToggle(); // Cast object to radio button
//			        System.out.println("Selected Radio Button - "+chk.getText());	
//			}
//		});
		
	}
	
}
