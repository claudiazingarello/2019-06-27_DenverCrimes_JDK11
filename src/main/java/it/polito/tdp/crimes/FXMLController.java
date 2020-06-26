package it.polito.tdp.crimes;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.crimes.model.Arco;
import it.polito.tdp.crimes.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

//controller turno A --> switchare al branch master_turnoB o master_turnoC per turno B o C

public class FXMLController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> boxCategoria;

    @FXML
    private ComboBox<Integer> boxAnno;

    @FXML
    private Button btnAnalisi;

    @FXML
    private ComboBox<Arco> boxArco;

    @FXML
    private Button btnPercorso;

    @FXML
    private TextArea txtResult;

    @FXML
    void doCalcolaPercorso(ActionEvent event) {
    	txtResult.clear();
    	
    	Arco arco = boxArco.getValue();
    	
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	
    	String categoria = boxCategoria.getValue();
    	if(categoria == null) {
    		txtResult.appendText("ERRORE: scegliere una categoria!");
    		return;
    	}
    	Integer anno = boxAnno.getValue();
    	if(anno == null) {
    		txtResult.appendText("ERRORE: scegliere un anno!");
    		return;
    	}
    	
    	model.creaGrafo(categoria, anno);
    	
    	txtResult.appendText("Grafo creato!\n");
    	btnPercorso.setDisable(false);
    	boxArco.setDisable(false);
    	
    	List<Arco> archi = model.getArchi();
    	for(Arco a : archi){
    		txtResult.appendText(a.toString()+"\n");
    	}
    	boxArco.getItems().addAll(archi);
    }

    @FXML
    void initialize() {
        assert boxCategoria != null : "fx:id=\"boxCategoria\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert boxAnno != null : "fx:id=\"boxAnno\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert btnAnalisi != null : "fx:id=\"btnAnalisi\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert boxArco != null : "fx:id=\"boxArco\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert btnPercorso != null : "fx:id=\"btnPercorso\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Crimes.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
		
		boxCategoria.getItems().addAll(model.getAllCategory());
		boxAnno.getItems().addAll(model.getAllYears());
		
		btnPercorso.setDisable(true);
		boxArco.setDisable(true);
	}
}
