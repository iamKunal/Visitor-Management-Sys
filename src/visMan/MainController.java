package visMan;

import visMan.utils.Utils;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class MainController implements Initializable {
	public void deleteTempData(){
        File tempFile = new File("temp.jpg");
        tempFile.delete();
        tempFile = new File("temp.png");
        tempFile.delete();
	}
	@FXML VBox mainRoot;
	@FXML Button goButton;
	@FXML
	void submitGo(){
		System.out.println(Utils.getToggleText(check) + " " + Utils.getToggleText(user));
        mainRoot.setDisable(true);
        if(Utils.getToggleText(check).equals("Checkin")){
        	if(Utils.getToggleText(user).equals("New User")){
        		try
        		{
	    			// load the FXML resource
	                FXMLLoader loader = new FXMLLoader(getClass().getResource("NewUser.fxml"));
	                Parent newUserRoot = (Parent) loader.load();
	                Scene newUser = new Scene(newUserRoot);
	                NewUserController controller = (NewUserController) loader.getController();
	//                control.initData(selectedSong);
	                Stage stager = new Stage();
	                stager.setScene(newUser);
//	                stager.initStyle(StageStyle.UNDECORATED);
	                stager.setResizable(false);
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
	                stager.setTitle("Checkin New User");
	                stager.showAndWait();
	    		}
	    		catch (Exception e)
	    		{
	    			e.printStackTrace();
	    		}
        	}
        }

        mainRoot.setDisable(false);
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
