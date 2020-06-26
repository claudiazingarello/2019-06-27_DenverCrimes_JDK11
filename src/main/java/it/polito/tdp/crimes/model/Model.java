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
	private List<String> categorie;
	private List<Integer> mesi;
	private List<String> tipiReato;

	Graph<String, DefaultWeightedEdge> grafo;

	//Variabili per la ricorsione
	List<String> best;
	public Model() {
		dao = new EventsDAO();
	}

	public List<String> getCategorie() {
		categorie = dao.listAllCategories();
		return categorie;
	}

	public List<Integer> getMesi() {
		mesi = dao.listAllMonth();
		return mesi;
	}

	public void creaGrafo(String categoria, Integer mese) {
		grafo = new SimpleWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);

		//aggiungi i vertici
		tipiReato = dao.getTipiReato(categoria, mese);
		Graphs.addAllVertices(grafo, tipiReato);

		//Aggiungi gli archi
		for(Adiacenza a : dao.getAdiacenze(categoria, mese)) {
			if(grafo.getEdge(a.getT1(), a.getT2()) == null){
				Graphs.addEdge(grafo, a.getT1(), a.getT2(), a.getPeso());
			}
		}

		//		List<Adiacenza> adiacenze = this.dao.getAdiacenze(categoria, mese);
		//		//Scorriamo la lista di Adiacenze
		//		for(Adiacenza a : adiacenze) {
		//			if(!this.grafo.containsVertex(a.getV1())) {
		//				this.grafo.addVertex(a.getV1());
		//			}
		//			if(!this.grafo.containsVertex(a.getV2())) {
		//				this.grafo.addVertex(a.getV2());
		//			}
		//			
		//			if(this.grafo.getEdge(a.getV1(), a.getV2() ) == null) //l'arco non esiste ancora
		//					Graphs.addEdge(this.grafo, a.getV1(), a.getV2(), a.getPeso());
		//		}
		//		
		System.out.println("Grafo creato!\n# vertici: "+grafo.vertexSet().size()+"\n# archi: "+grafo.edgeSet().size());

	}
	
	public List<Arco> getArchi(){
		//calcolo il peso medio
		double pesoMedio = 0.0;
		
		for (DefaultWeightedEdge e : grafo.edgeSet()) {
			pesoMedio += this.grafo.getEdgeWeight(e);
		}
		pesoMedio = pesoMedio/this.grafo.edgeSet().size();
		
		//lista do archi il cui peso è meggiore al pesoMedio
		List<Arco> archi = new ArrayList<Arco>();
		for(DefaultWeightedEdge e : grafo.edgeSet()) {
			if(grafo.getEdgeWeight(e) > pesoMedio) {
				archi.add(new Arco(grafo.getEdgeSource(e), grafo.getEdgeTarget(e), grafo.getEdgeWeight(e)));
			}
		}
		Collections.sort(archi);
		return archi;
	}

	public List<String> calcolaPercorso(String sorgente, String destinazione) {
		List<String> parziale = new ArrayList<String>();
		
		this.best = new ArrayList<String>();
		
		parziale.add(sorgente);
		trovaRicorsivo(destinazione, parziale, 0);
		
		return this.best;
	}

	private void trovaRicorsivo(String destinazione, List<String> parziale, int L) {
		//CASO TERMINALE : quando l'ultimo vertice inserito in pariale è uguale alla destinazione
		if(parziale.get(parziale.size() -1).equals(destinazione)) {
			if(parziale.size() > this.best.size()) {
				this.best = new ArrayList<String>(parziale);
			}
			return;
		}
		
		//scorro i vicini dell'ultimo vertice inserito in parziale
		for(String vicino : Graphs.neighborListOf(this.grafo, parziale.get(parziale.size() -1))) {
			// cammino aciclico : controllo che il vertice non sia già in parziale
			if(!parziale.contains(vicino)) {
				//provo ad aggiungere
				parziale.add(vicino);
				//continuo la ricorsione
				this.trovaRicorsivo(destinazione, parziale, L+1);
				//faccio backtracking
				parziale.remove(parziale.size()-1);
			}
		}
	}
}
