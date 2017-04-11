package visMan;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import visMan.database.CheckOut;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class CheckOutController implements Initializable {
	private ObservableList<Visitor> toCheckOut;
	private ObservableList<Visitor> checkOutList;
	@FXML
	private ListView<Visitor> visitorListView;
	@FXML
	private TextField searchBar;
    @FXML
    private Button cancelButton;
    @FXML
    private Button checkoutButton;
    @FXML
    private Label errorLabel;
    @FXML
    void cancelExecute() {
        Stage currentStage = (Stage) cancelButton.getScene().getWindow();
//      if(openStage!=null){
//      	openStage.close();
//      	openStage=null;
//      }
      currentStage.close();
    }

    @FXML
    void checkout() {
    	Alert alert = new Alert(AlertType.CONFIRMATION, "", ButtonType.YES, ButtonType.CANCEL);
		alert.setHeaderText("Do you wish to checkout the following visitors?");
		String s="";
		for(Visitor v: toCheckOut){
			s+=v.getName()+"\n";
		}
		alert.setContentText(s);
    	alert.showAndWait();

    	if (alert.getResult() == ButtonType.YES) {
    	    try{
    	    	CheckOut ch = new CheckOut();
    	    	ch.checkOut(toCheckOut);
    	    	visitorListView.setItems(ch.getCheckOutList());
    	    	toCheckOut.clear();
    	    	if(visitorListView.getItems().size()==0){
    	    		cancelExecute();
    	    	}
    	    	checkoutButton.setDisable(true);
    	    }
    	    catch (Exception e) {
				// TODO: handle exception
			}
    	}
    }
    @FXML
    void search() {
    	visitorListView.setItems(findInList(searchBar.getText()));
    	if(visitorListView.getItems().size()==0)
    		errorLabel.setVisible(true);
    	else
    		errorLabel.setVisible(false);
    		
    }
    ObservableList<Visitor> findInList(String query){
    	ObservableList<Visitor> list = FXCollections.observableArrayList();
    	for(Visitor v : checkOutList){
    		if(v.toString().toLowerCase().contains(query.toLowerCase())){
    			list.add(v);
    		}
    	}
    	return list;
    }
    @Override
	public void initialize(URL location, ResourceBundle resources) {
    	toCheckOut = FXCollections.observableArrayList();
//    	scrollBar.valueProperty().addListener(new ChangeListener<Number>() {
//    		@Override
//    		public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
//    			// TODO Auto-generated method stub
//    			visitorIDListView.scrollTo(newValue.intValue());
//    		}
//		});
    	checkOutList=FXCollections.observableArrayList();
	    visitorListView.setCellFactory(CheckBoxListCell.forListView(new Callback<Visitor, ObservableValue<Boolean>>() {
	        @Override
	        public ObservableValue<Boolean> call(Visitor item) {
	            BooleanProperty observable = new SimpleBooleanProperty();
	            observable.addListener((obs, wasSelected, isNowSelected) -> {
	            	if(isNowSelected){
	            		toCheckOut.add(item);
	            		if(toCheckOut.size()>0){
	            			checkoutButton.setDisable(false);
	            		}
	            	}
	            	else{
	            		toCheckOut.remove(item);
	            		if(toCheckOut.size()==0){
	            			checkoutButton.setDisable(true);
	            		}
	            	}
	            });
	            return observable ;
	        }
	    }));
	    searchBar.textProperty().addListener(new ChangeListener<String>() {
	    	@Override
	    	public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
	    		// TODO Auto-generated method stub
	    		if(!newValue.isEmpty()){
					String newString = newValue.substring(newValue.indexOf(newValue.trim()));
					newString = newString.replaceAll("  ", " ");
					searchBar.setText(newString);
					if(newString.contains("|")){
						searchBar.setText(oldValue);
					}

				}
	    	}
		});
        visitorListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent click) {
                if (click.getClickCount()>0) {
                    //Use ListView's getSelected Item
                    visitorListView.getSelectionModel().select(-1);
                }
            }
        });
        try{
        	CheckOut ch = new CheckOut();
    	    checkOutList = ch.getCheckOutList();
    	    visitorListView.setItems(checkOutList);
        }
        
        catch (Exception e) {
			// TODO: handle exception
		}

	    
	}

}
