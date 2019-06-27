package it.polito.tdp.extflightdelays.model;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.event.ConnectedComponentTraversalEvent;
import org.jgrapht.event.EdgeTraversalEvent;
import org.jgrapht.event.TraversalListener;
import org.jgrapht.event.VertexTraversalEvent;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.traverse.BreadthFirstIterator;

import it.polito.tdp.extflightdelays.db.ExtFlightDelaysDAO;

public class Model {
	private Map<Integer,Airport> idMap;
	private List<Airport> listaAeroporti;
	private Graph<Airport,DefaultWeightedEdge> grafo;
	private Map<Airport,Airport> visita;
	

	public Model() {
		idMap=new HashMap<Integer,Airport>();
		listaAeroporti=getAeroporti(idMap);
		visita= new HashMap<Airport,Airport>();
	}
	
	
public List< Airport> getAeroporti(Map<Integer,Airport> airport) {
	ExtFlightDelaysDAO dao = new ExtFlightDelaysDAO();
	List<Airport> tuttiAeroporti= dao.loadAllAirports(); 
	for (Airport a: tuttiAeroporti) {
		if (airport.get(a.getId())==null) {  //se non è presente, lo aggiunge alla mappa
			airport.put(a.getId(),a);
		}
	}
	
	return tuttiAeroporti;
	
}


public List<Airport> getListaAeroporti() {
	return listaAeroporti;
}

public void creaGrafo(double distanza) {
	grafo= new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
	Graphs.addAllVertices(grafo, listaAeroporti);
	ExtFlightDelaysDAO dao = new ExtFlightDelaysDAO();
	List<Rotta> rotte = dao.getRotte(distanza);
	for(Rotta r: rotte) {
		if(grafo.getEdge(this.idMap.get(r.getIdArrivo()),this.idMap.get(r.getIdPartenza()))==null){
		Graphs.addEdge(grafo,idMap.get(r.getIdPartenza()),this.idMap.get(r.getIdArrivo()), r.getDistanza());
	}
		else {
			DefaultWeightedEdge arco= grafo.getEdge(this.idMap.get(r.getIdArrivo()),this.idMap.get(r.getIdPartenza()));
	double pesoPrima=		grafo.getEdgeWeight(arco);
	double nuovoPeso= (pesoPrima+r.getDistanza())/2;
	grafo.setEdgeWeight(this.idMap.get(r.getIdArrivo()),this.idMap.get(r.getIdPartenza()),nuovoPeso);
	
		}
	}	
		
	
	
}


public Graph<Airport, DefaultWeightedEdge> getGrafo() {
	return grafo;
}
public boolean testConnessione(Integer id1,Integer id2) {
	Set<Airport> visitati= new HashSet<Airport>();
	Airport partenza= idMap.get(id1);
	Airport arrivo= idMap.get(id2);
	BreadthFirstIterator<Airport,DefaultWeightedEdge> it= new BreadthFirstIterator<>(grafo,partenza);
	
	while (it.hasNext()) {
		visitati.add(it.next());
	}
	
			return (visitati.contains(arrivo));
	
	
}

public List<Airport> percorso(Integer id1,Integer id2) {
	List<Airport> percorso= new ArrayList<Airport>();
	
	Airport partenza= idMap.get(id1);
	Airport arrivo= idMap.get(id2);
	BreadthFirstIterator<Airport,DefaultWeightedEdge> it= new BreadthFirstIterator<>(grafo,partenza);
	visita.put(partenza,null);
	it.addTraversalListener(new TraversalListener<Airport,DefaultWeightedEdge>(){

		@Override
		public void connectedComponentFinished(ConnectedComponentTraversalEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void connectedComponentStarted(ConnectedComponentTraversalEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void edgeTraversed(EdgeTraversalEvent<DefaultWeightedEdge> arg0) {
			Airport sorgente= grafo.getEdgeSource(arg0.getEdge());
			Airport destinazione= grafo.getEdgeTarget(arg0.getEdge());
			if (!visita.containsKey(destinazione)&&visita.containsKey(sorgente)) {
				visita.put(destinazione,sorgente);
			}
			else if (!visita.containsKey(sorgente)&&visita.containsKey(destinazione)) {
				visita.put(sorgente,destinazione);
			}
			
		}

		@Override
		public void vertexFinished(VertexTraversalEvent<Airport> arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void vertexTraversed(VertexTraversalEvent<Airport> arg0) {
			// TODO Auto-generated method stub
			
		}
		
	});
	while (it.hasNext()) {
		it.next();
	}
	if(!visita.containsKey(partenza) || !visita.containsKey(arrivo))
	return null;
	
		Airport step= arrivo;
		while(!step.equals(partenza))
		{percorso.add(step);
		step=visita.get(step);
		}
		percorso.add(step);
		return percorso;
	
	
}
	
			

public String getArchi() {
	String result=""+this.grafo.edgeSet().size();
	return result;
}


public String getVertex() {
	String result=""+this.grafo.vertexSet().size();
	return result;
}
}
