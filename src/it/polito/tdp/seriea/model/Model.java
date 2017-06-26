package it.polito.tdp.seriea.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.WeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.seriea.db.SerieADAO;

public class Model {
	
	private List<Season> stagioni;
	private Map<String, Team> mappaTeam;
	private SerieADAO dao;
	private WeightedGraph <Team, DefaultWeightedEdge> grafo;
	private List<Team> classifica;
	
	public Model(){
		dao = new SerieADAO();
		mappaTeam = new HashMap<String,Team>();
	}
	

	public List<Season> getSeasons(){
		if (stagioni==null){
			stagioni = dao.listSeasons();
		}
		
		return stagioni;
	}
	
	public void creaGrafo(Season season){
		
		grafo = new SimpleDirectedWeightedGraph <Team, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		List <Team> teamStagione = dao.getTeamForSeason(season);
		
		//carico i vertici
		Graphs.addAllVertices(grafo, teamStagione);
		
		for (Team team : teamStagione){
			mappaTeam.put(team.getTeam(), team);
		}
		
		//System.out.println(grafo);
		
		dao.getMatch(season, mappaTeam);
		
		//aggiungo gli archi
		for (TeamPair tm : dao.getTeamPair(season)){
			Team t1 = mappaTeam.get(tm.getTeamH());
			Team t2 = mappaTeam.get(tm.getTeamA());
			
			DefaultWeightedEdge e = grafo.addEdge(t1, t2);
			
			if (tm.getFtr().compareTo("H")==0){
				grafo.setEdgeWeight(e, 1);
				t1.setPunteggio(t1.getPunteggio()+3);
			}
			
			if (tm.getFtr().compareTo("A")==0){
				grafo.setEdgeWeight(e, -1);
				t2.setPunteggio(t2.getPunteggio()+3);
			}
			
			if (tm.getFtr().compareTo("D")==0){
				grafo.setEdgeWeight(e, 0);
				t1.setPunteggio(t1.getPunteggio()+1);
				t2.setPunteggio(t2.getPunteggio()+1);
			}
			
		}
		
		System.out.println(grafo);
		
	}
	
	public List<Team> getClassifica(){
		
		classifica = new ArrayList<>(grafo.vertexSet());
		
		Collections.sort(classifica);
		
		
		
		return classifica;
	}

}
