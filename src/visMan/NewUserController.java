package visMan;

import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import visMan.utils.Utils;

public class NewUserController implements Initializable {
	@FXML
	private TextField nameField;
	@FXML
	private ToggleGroup gender;
	@FXML
	private TextField contactField;
	@FXML
	private DatePicker dateOfBirth;
	@FXML
	private TextArea addressField;
	@FXML
	private ToggleGroup category;
	@FXML
	private TextField purposeField;
	@FXML
	private ImageView userImage;
	@FXML
	private Button captureButton;
	@FXML
	private Button reviewButton;
	@FXML
	private Button cancelButton;
	private void checkValues(){
		boolean correct=true;
		correct&=(!nameField.getText().trim().isEmpty());
		correct&=(!Utils.getToggleText(gender).equals("null"));
		correct&=(!contactField.getText().trim().isEmpty());
		correct&=!(dateOfBirth.getValue()==null);
		correct&=(!addressField.getText().trim().isEmpty());
		correct&=(!Utils.getToggleText(category).equals("null"));
		correct&=(!purposeField.getText().trim().isEmpty());
		correct&=!(userImage.getImage()==null);
		reviewButton.setDisable(!correct);
	}
	// Event Listener on Button[#captureButton].onAction
	@FXML
	public void openCaptureWindow(ActionEvent event) throws Exception {
		{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("WebCamCapture.fxml"));
        Parent webCamRoot = (Parent) loader.load();
        Scene newUser = new Scene(webCamRoot,400,600);
        WebCamCaptureController controller = (WebCamCaptureController) loader.getController();
//        control.initData(selectedSong);
        Stage stager = new Stage();
        stager.setScene(newUser);
        stager.setResizable(false);
        stager.setTitle("Checkin New User");
//        stager.setOnHiding(new EventHandler<WindowEvent>() {
//
//            @Override
//            public void handle(WindowEvent event) {
//                Platform.runLater(new Runnable() {
//
//                    @Override
//                    public void run() {
//                        System.out.println("Hi");
//                    }
//                });
//            }
//        });
        stager.showAndWait();
		}
		try{
	        File file = new File("temp.png");
	        Image image = new Image(file.toURI().toString());
			userImage.setImage(image);
		}
		catch (Exception e) {
//			System.out.println("Image not present");
			;
		}
		
	}
	// Event Listener on Button[#reviewButton].onAction
	@FXML
	public void reviewAndPrint(ActionEvent event) {
		Visitor newVisitor = new Visitor(nameField.getText(), gender, contactField.getText(), dateOfBirth.getValue().toString(), addressField.getText(), category, purposeField.getText());
		System.out.println(newVisitor);
		//DB.insert(newVisitor);
	}
	// Event Listener on Button[#cancelButton].onAction
	@FXML
	public void cancelExecute(ActionEvent event) {
        Stage currentStage = (Stage) cancelButton.getScene().getWindow();
        currentStage.close();
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		nameField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if(!newValue.isEmpty()){
					String newString = newValue.substring(newValue.indexOf(newValue.trim()));
					newString = newString.replaceAll("  ", " ");
					nameField.setText(newString);
				}
			checkValues();
			}
		});
		gender.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			@Override
			public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
				checkValues();
			}
		});
		contactField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if(!newValue.matches("^\\d*$")){
					contactField.setText(oldValue);
				}
				checkValues();
			}
		});
		addressField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if(!newValue.isEmpty()){
					String nv="";
					String ov[] = newValue.split("\n", -1);
					for (int i = 0; i < ov.length; i++) {
						ov[i] = ov[i].substring(ov[i].indexOf(ov[i].trim()));
						while(ov[i].contains("  "))
							ov[i] = ov[i].replaceAll("  ", " ");
					}
					nv=String.join("\n", ov);
					while(nv.contains("\n\n"))
						nv=nv.replaceAll("\n\n", "\n");
					addressField.setText(nv);
				}
				checkValues();
			}
		});
		category.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			@Override
			public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
				checkValues();
			}
		});
		purposeField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if(!newValue.isEmpty()){
					String newString = newValue.substring(newValue.indexOf(newValue.trim()));
					newString = newString.replaceAll("  ", " ");
					purposeField.setText(newString);
				}
				checkValues();
			}
		});
		userImage.imageProperty().addListener(new ChangeListener<Image>() {
			@Override
			public void changed(ObservableValue<? extends Image> observable, Image oldValue, Image newValue) {
				checkValues();
			}
		});
		dateOfBirth.valueProperty().addListener((o,oldValue,newValue) -> {
			if(newValue!=null){
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
				LocalDate localDate = LocalDate.now();
				if(ChronoUnit.DAYS.between(newValue,localDate)<0 || ChronoUnit.YEARS.between(newValue,localDate)>150)
					dateOfBirth.setValue(localDate);
			}
			checkValues();
		});
	}
	
}
