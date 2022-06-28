package it.polito.tdp.itunes.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.itunes.db.Adiacenza;
import it.polito.tdp.itunes.db.ItunesDAO;

public class Model {
	
	ItunesDAO dao;
	private Map<Integer,Track> idMap;
	private Graph<Track, DefaultWeightedEdge> grafo;

	public Model() {
		dao = new ItunesDAO();
		this.idMap = new HashMap<>();
		this.dao.riempiMappa(idMap);
	}
	
	public void creaGrafo(Genre g) {
		
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		Graphs.addAllVertices(grafo, dao.getVertici(g, idMap));
		
		for(Adiacenza a : dao.getAdiacenza(g, idMap)) {
			if(grafo.containsVertex(a.getT1()) && grafo.containsVertex(a.getT2())) {
				Graphs.addEdgeWithVertices(grafo, a.getT1(), a.getT2(), a.getDifferenza());
			}
		}	
		
		System.out.println("Grafo creato!");
		System.out.println(String.format("# Vertici: %d", 
				this.grafo.vertexSet().size()));
		System.out.println(String.format("# Archi: %d", 
				this.grafo.edgeSet().size()));
	}
	public List<Genre> getGeneri(){
		List<Genre> listageneri = new ArrayList<>(dao.getAllGenres());
		Collections.sort(listageneri,new Comparator<Genre>() {

			@Override
			public int compare(Genre o1, Genre o2) {
				// TODO Auto-generated method stub
				return o1.getName().compareTo(o2.getName());
			}
		});
		return listageneri;
	}
	
	public int nVertici() {
		return this.grafo.vertexSet().size();
	}
	
	public int nArchi() {
		return this.grafo.edgeSet().size();
	}
	
	public Adiacenza getDurataMassima() {
		double max = Integer.MIN_VALUE;
		Track canzone1 = null;
		Track canzone2 = null;
		for(Track t : this.grafo.vertexSet()) {
		for(DefaultWeightedEdge e : this.grafo.edgesOf(t)) {
			if(this.grafo.getEdgeWeight(e)>max) {
				max = this.grafo.getEdgeWeight(e);
			}
		}
		
		}
		for(Track t : this.grafo.vertexSet()) {
			for(DefaultWeightedEdge e : this.grafo.edgesOf(t)) {
				if(this.grafo.getEdgeWeight(e)==max) {
					canzone1 = t;
					canzone2 = Graphs.getOppositeVertex(grafo, e, t);
				}
			}
			
		}
		
		return new Adiacenza(canzone1, canzone2, max);
	}
}
