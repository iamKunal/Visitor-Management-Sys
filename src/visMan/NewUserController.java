package visMan;

import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.transform.Scale;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import visMan.database.CheckIn;
import visMan.utils.Utils;

public class NewUserController implements Initializable {
	@FXML
	AnchorPane newUserRoot;
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
	private TextField locationField;
	@FXML
	private TextField purposeField;
	@FXML
	private TextField validityField;
	@FXML
	private ImageView userImage;
	@FXML
	private Button captureButton;
	@FXML
	private Button reviewButton;
	@FXML
	private Button cancelButton;
	public static Stage openStage=null;
	private void checkValues(){
		boolean correct=true;
		correct&=(!nameField.getText().trim().isEmpty());
		correct&=(!Utils.getToggleText(gender).equals("null"));
		correct&=(!contactField.getText().trim().isEmpty());
		correct&=!(dateOfBirth.getValue()==null);
		correct&=(!addressField.getText().trim().isEmpty());
		correct&=(!Utils.getToggleText(category).equals("null"));
		correct&=(!purposeField.getText().trim().isEmpty());
		correct&=(!locationField.getText().trim().isEmpty());
		correct&=(!validityField.getText().trim().isEmpty());
		try{
		correct&=!(userImage.getImage().getHeight()==0);
		}
		catch (Exception e) {
			correct=false;
			// TODO: handle exception
		}
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
        openStage = new Stage();
        openStage.setScene(newUser);
        openStage.initModality(Modality.WINDOW_MODAL);
        openStage.initOwner(reviewButton.getScene().getWindow());
        openStage.setResizable(false);
        openStage.setTitle("Checkin New User");
//        openStage.setOnHiding(new EventHandler<WindowEvent>() {
//
//            @Override
//            public void handle(WindowEvent event) {
//                Platform.runLater(new Runnable() {
//
//                    @Override
//                    public void run() {
//                    	newUserRoot.setDisable(false);
//                    }
//                });
//            }
//        });
        openStage.showAndWait();
		}
		openStage=null;

		try{
	        File file = new File("temp.png");
	        Image image = new Image(file.toURI().toString());
			userImage.setImage(image);
		}
		catch (Exception e) {
			System.out.println("Image not present");
			;
		}
		reviewButton.requestFocus();
		
	}
	// Event Listener on Button[#reviewButton].onAction
	@FXML
	public void reviewAndPrint(ActionEvent event) {
        trimAll();
		Visitor newVisitor = new Visitor(nameField.getText(), gender, contactField.getText(), dateOfBirth.getValue().toString(), addressField.getText(), category, purposeField.getText());
//		System.out.println(newVisitor);
		newVisitor.setLocation(locationField.getText());
		newVisitor.setGateNo(Main.gateNumber);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		newVisitor.setValidity((LocalDate.now().plusDays(Integer.parseInt(validityField.getText()))).format(dtf));
		CheckIn ch = new CheckIn();
		Visitor tempVisitor = ch.alreadyInserted(newVisitor);
		if(tempVisitor==null)
			try
			{
				// load the FXML resource
	            FXMLLoader loader = new FXMLLoader(getClass().getResource("PrintUserCard.fxml"));
	            Parent userCardRoot = (Parent) loader.load();
	            Scene userCard = new Scene(userCardRoot);
				userCard.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	            PrintUserCardController controller = (PrintUserCardController) loader.getController();
	//                control.initData(selectedSong);
	            Stage stager = new Stage();
	            controller.initData(newVisitor);
	            stager.setScene(userCard);
                stager.initModality(Modality.WINDOW_MODAL);
                stager.initOwner(reviewButton.getScene().getWindow());
	            stager.initStyle(StageStyle.UTILITY);
	//            stager.setResizable(false);
	//			stager.setOnHiding((new EventHandler<WindowEvent>() {
	//				public void handle(WindowEvent we)
	//				{
	//			        mainRoot.setDisable(false);
	//				}
	//			}));
	            stager.setTitle("Print Card");
	            stager.showAndWait();
	            reviewButton.requestFocus();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		else{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Existing User");
			alert.setHeaderText("This is an old user!");
			alert.setContentText("Please use Checkin Old User to print card for existing user!");
			alert.showAndWait();
		}
	}
	// Event Listener on Button[#cancelButton].onAction
	@FXML
	public void cancelExecute(ActionEvent event) {
        Stage currentStage = (Stage) cancelButton.getScene().getWindow();
//        if(openStage!=null){
//        	openStage.close();
//        	openStage=null;
//        }
        currentStage.close();
	}
	public void trimAll(){
		nameField.setText(nameField.getText().trim());
		contactField.setText(contactField.getText().trim());
		addressField.setText(addressField.getText().trim());
		locationField.setText(locationField.getText().trim());
		purposeField.setText(purposeField.getText().trim());
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		PrintUserCardController.insertedInDB=false;
		nameField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if(!newValue.isEmpty()){
					String newString = newValue.substring(newValue.indexOf(newValue.trim()));
					newString = newString.replaceAll("  ", " ");
					
					nameField.setText(Utils.toTitleCase(newString));
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
					int n=ov.length;
					if(n>3)
						n=3;
					for (int i = 0; i < n; i++) {
						ov[i] = ov[i].substring(ov[i].indexOf(ov[i].trim()));
						while(ov[i].contains("  "))
							ov[i] = ov[i].replaceAll("  ", " ");
						if(ov[i].contains("\t")){
							ov[i] = ov[i].replaceAll("\\t*", "");
							RadioButton b =  (RadioButton)category.getToggles().get(0);
							b.requestFocus();
						}
					}
					ov=Arrays.copyOfRange(ov, 0, n);
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
		locationField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if(!newValue.isEmpty()){
					String newString = newValue.substring(newValue.indexOf(newValue.trim()));
					newString = newString.replaceAll("  ", " ");
					locationField.setText(newString);
				}
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
		validityField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if(!newValue.matches("^\\d*$") || newValue.matches("^0*$") && newValue.length()!=0){
					validityField.setText(oldValue);
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
