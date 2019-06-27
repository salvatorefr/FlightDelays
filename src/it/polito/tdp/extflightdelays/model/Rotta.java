package it.polito.tdp.extflightdelays.model;

public class Rotta {
	private int idPartenza;
	private int idArrivo;
	private double distanza;
	public Rotta(int idPartenza, int idArrivo, double distanza) {
		
		this.idPartenza = idPartenza;
		this.idArrivo = idArrivo;
		this.distanza = distanza;
	}
	public int getIdPartenza() {
		return idPartenza;
	}
	public int getIdArrivo() {
		return idArrivo;
	}
	public double getDistanza() {
		return distanza;
	}

}
