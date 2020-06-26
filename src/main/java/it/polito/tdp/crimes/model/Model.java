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
}
