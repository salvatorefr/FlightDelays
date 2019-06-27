

/**
 * Sample Skeleton for 'ExtFlightDelays.fxml' Controller Class
 */

package it.polito.tdp.flightdelays;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.extflightdelays.model.Airport;
import it.polito.tdp.extflightdelays.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
/**
 * Sample Skeleton for 'FlightDelays.fxml' Controller Class
 */



public class FlightDelaysController {
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML // fx:id="distanzaMinima"
    private TextField distanzaMinima; // Value injected by FXMLLoader

    @FXML // fx:id="btnAnalizza"
    private Button btnAnalizza; // Value injected by FXMLLoader

    @FXML // fx:id="cmbBoxAeroportoPartenza"
    private ComboBox<Airport> cmbBoxAeroportoPartenza; // Value injected by FXMLLoader

    @FXML // fx:id="cmbBoxAeroportoArrivo"
    private ComboBox<Airport> cmbBoxAeroportoArrivo; // Value injected by FXMLLoader

    @FXML // fx:id="btnAeroportiConnessi"
    private Button btnAeroportiConnessi; // Value injected by FXMLLoader

    @FXML
    void doAnalizzaAeroporti(ActionEvent event) {
    	this.txtResult.clear();
    
   try {
	   double distanza = Double.parseDouble(this.distanzaMinima.getText());
   
	   
   
    model.creaGrafo(distanza);
    this.txtResult.appendText("creato un grafo con "+model.getVertex()+" vertici e "+model.getArchi()+" archi");
    }catch (NumberFormatException nf) {this.txtResult.clear();
    this.txtResult.appendText("inserisci un numero valido");
    }}

    @FXML
    void doTestConnessione(ActionEvent event) {
    	this.txtResult.clear();
    	model.creaGrafo(0);
    	int idPartenza=this.cmbBoxAeroportoPartenza.getValue().getId();
    	int idArrivo=this.cmbBoxAeroportoArrivo.getValue().getId();
    	if (model.testConnessione(idPartenza, idArrivo)) {
    	this.txtResult.appendText("connessi");
    	this.txtResult.appendText(model.percorso(idPartenza, idArrivo).toString());
    	}
    	else 
    		this.txtResult.appendText("non connessi");

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'FlightDelays.fxml'.";
        assert distanzaMinima != null : "fx:id=\"distanzaMinima\" was not injected: check your FXML file 'FlightDelays.fxml'.";
        assert btnAnalizza != null : "fx:id=\"btnAnalizza\" was not injected: check your FXML file 'FlightDelays.fxml'.";
        assert cmbBoxAeroportoPartenza != null : "fx:id=\"cmbBoxAeroportoPartenza\" was not injected: check your FXML file 'FlightDelays.fxml'.";
        assert cmbBoxAeroportoArrivo != null : "fx:id=\"cmbBoxAeroportoArrivo\" was not injected: check your FXML file 'FlightDelays.fxml'.";
        assert btnAeroportiConnessi != null : "fx:id=\"btnAeroportiConnessi\" was not injected: check your FXML file 'FlightDelays.fxml'.";

      
     
      
        	
        
        }
        
    
    
    public void setModel(Model model) {
		this.model = model;
		
	}

	public void popolaCampi() {
		List<Airport> tutti= model.getListaAeroporti();
		this.cmbBoxAeroportoPartenza.getItems().addAll(tutti);
		this.cmbBoxAeroportoArrivo.getItems().addAll(tutti);
		
	}
}




