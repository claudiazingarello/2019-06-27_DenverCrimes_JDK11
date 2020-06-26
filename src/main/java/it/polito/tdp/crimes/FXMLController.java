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

//controller turno B --> switchare al branch master_turnoA o master_turnoC per turno A o C

public class FXMLController {
	
	private Model model;

	@FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> boxCategoria;

    @FXML
    private ComboBox<Integer> boxMese;

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
    	if(arco == null) {
    		txtResult.appendText("ERRORE: devi selezionare un arco!");
    		return;
    	}
    	
    	List<String> percorso = model.calcolaPercorso(arco.getT1(), arco.getT2());
    	for(String s: percorso) {
    		txtResult.appendText(s.toString() +"\n");
    	}
    	
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	
    	String categoria = boxCategoria.getValue();
    	
    	if(categoria == null ) {
    		txtResult.appendText("ERRORE: scegliere categoria");
    		return;
    	}
    	
    	Integer mese = boxMese.getValue();
    	if(mese == null ) {
    		txtResult.appendText("ERRORE: scegliere mese");
    		return;
    	}
    	
    	model.creaGrafo(categoria, mese);
    	
    	txtResult.appendText("ELENCO DI ARCHI:\n");
    	List<Arco> archi = model.getArchi();
    	for(Arco a : archi) {
    		txtResult.appendText(a.getT1()+" & "+a.getT2()+" ("+a.getPeso()+")\n");
    	}
    	btnPercorso.setDisable(false);
    	boxArco.setDisable(false);
    	boxArco.getItems().addAll(model.getArchi());
    	
    }

    @FXML
    void initialize() {
        assert boxCategoria != null : "fx:id=\"boxCategoria\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert boxMese != null : "fx:id=\"boxMese\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert btnAnalisi != null : "fx:id=\"btnAnalisi\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert boxArco != null : "fx:id=\"boxArco\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert btnPercorso != null : "fx:id=\"btnPercorso\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Crimes.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
		
		boxCategoria.getItems().addAll(model.getCategorie());
		boxMese.getItems().addAll(model.getMesi());
		btnPercorso.setDisable(true);
		boxArco.setDisable(true);
	}
}
