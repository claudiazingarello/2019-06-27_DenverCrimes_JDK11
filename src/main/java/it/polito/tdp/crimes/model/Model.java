package it.polito.tdp.crimes.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.crimes.db.EventsDAO;

public class Model {
	private EventsDAO dao;
	private List<String> tipiReato;
	private Graph<String, DefaultWeightedEdge> grafo;
	
	//variabili ricorsione
	private List<String> percorsoBest;
	private Double pesoMin;
	
	public Model() {
		dao = new EventsDAO();
	}

	public List<String> getAllCategory() {
		return dao.listAllCategory();
	}
	
	public List<Integer> getAllYears() {
		return dao.listAllYears();
	}

	public void creaGrafo(String categoria, Integer anno) {
		grafo = new SimpleWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		//Aggiungi i vertici
		tipiReato = dao.getTipiReato(categoria, anno);
		Graphs.addAllVertices(grafo, tipiReato);
		
		//Aggiungi gli archi
		for(Adiacenza a : dao.getAdiacenze(categoria, anno)) {
			if(grafo.getEdge(a.getT1(), a.getT2()) == null) {
				Graphs.addEdge(grafo, a.getT1(), a.getT2(), a.getPeso());
			}
		}
		
		
		System.out.println("Grafo creato!\n#vertici:" +grafo.vertexSet().size()+"\n#archi: "+ grafo.edgeSet().size());
	}
	
	public List<Arco> getArchi(){
		double pesoMax = 0.0;
		List<Arco> archi = new ArrayList<Arco>();
		
		for(DefaultWeightedEdge e : grafo.edgeSet()) {
			if(grafo.getEdgeWeight(e) > pesoMax) {
				pesoMax = grafo.getEdgeWeight(e);
			}
		}
		
		for(DefaultWeightedEdge e : grafo.edgeSet()) {
			if(grafo.getEdgeWeight(e) == pesoMax) {
				Arco a = new Arco(grafo.getEdgeSource(e), grafo.getEdgeTarget(e), pesoMax);
				archi.add(a);
			}
		}
		
		Collections.sort(archi);
		return archi;
	}

	public List<String> calcolaPercorso(Arco arco) {

		// // procedura chiamate che deve predisporre le variabili della ricorsione di modo che possano 
		// lavorare in modo corretto
		percorsoBest = new ArrayList<String>();
		pesoMin = 0.0;
		
		// creo anche parziale
		List<String> parziale = new ArrayList<String>();
		
		cerca(parziale, arco.getT1(), 0);
		
		return percorsoBest;
	}
	
	public void cerca(List<String> parziale, String source, int L ) {
		
		//siamo nei CASI TERMINALI
	
		// N.B. ricordarsi che quando voglio sostituire la soluzione migliore con parziale devo CLONARE
		// l'oggetto
		// per ogni caso terminale devo vedere se andare avanti o se mettere return
		// considero casi terminali prima della fine della ricorsione e anche caso terminale di quando ho finito
		// i livelli
		
		// se no GENERIAMO SOTTO-PROBLEMI
		// ci chiediamo: L di parziale al livello L e' da aggiungere oppure no?
		// => provo a non aggiungerlo a provo ad aggiungerlo:
		
		//2. provo ad aggiungerlo: prendo l'attributo che considero ad ogni livello e lo aggiungo a parziale
		for(String s : grafo.vertexSet()) {
			if(!parziale.contains(s)) {
				parziale.add(s);
				// a questo punto posso fare la ricerca dove pero' parziale e' diverso da come mi e' arrivato
				cerca(parziale, source, parziale.size()-1);
			}
		}
		
		
		
		
		//backtracking: rimettiamo le cose a posto
		/*parziale.remove(---)*/ // QUANDO HO UNA LISTA NON POSSO FARE REMOVE DELL'ELEMENTO MA DEVO PRENDERE L'INDICE
		
		
	}
}
