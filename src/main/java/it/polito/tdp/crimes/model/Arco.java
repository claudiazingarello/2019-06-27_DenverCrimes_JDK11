package it.polito.tdp.crimes.model;

public class Arco implements Comparable<Arco> {
	private String t1;
	private String t2;
	private Double peso;
	
	public Arco(String t1, String t2, double peso) {
		super();
		this.t1 = t1;
		this.t2 = t2;
		this.peso = peso;
	}
	public String getT1() {
		return t1;
	}
	public void setT1(String t1) {
		this.t1 = t1;
	}
	public String getT2() {
		return t2;
	}
	public void setT2(String t2) {
		this.t2 = t2;
	}
	public double getPeso() {
		return peso;
	}
	public void setPeso(double peso) {
		this.peso = peso;
	}
	@Override
	public int compareTo(Arco o) {
		return this.peso.compareTo(o.getPeso());
	}
	@Override
	public String toString() {
		return " ( " + t1 + ", " + t2 + " )";
	}
	
	
}
