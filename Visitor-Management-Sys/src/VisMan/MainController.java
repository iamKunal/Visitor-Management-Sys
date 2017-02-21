package VisMan;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

public class MainController implements Initializable {
	@FXML
	Button goButton;
	@FXML
	void submitGo(){
		System.out.println(getToggleText(check) + " " + getToggleText(user));
	}
	@FXML ToggleGroup user;
	@FXML ToggleGroup check;
	
	String getToggleText(ToggleGroup t){
		try{
		return ((RadioButton)t.getSelectedToggle()).getText();
		}
		catch(Exception e){
			return "null";
		}
		
	}
	String getToggleText(Toggle t){
		return getToggleText(t.getToggleGroup());
	}
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
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
				if(getToggleText(newValue).equals("Checkout")){
					visibleValue=false;
				}
				for(Toggle rb : user.getToggles()){
					RadioButton r = (RadioButton) rb;
					r.setVisible(visibleValue);
				}
				if(getToggleText(user).equals("null") && visibleValue){
					goButton.setDisable(true);
				}
			}
		});
		user.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {@Override
			public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
				// TODO Auto-generated method stub
			if(!getToggleText(newValue).equals("null")){
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
