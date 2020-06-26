package it.polito.tdp.crimes.model;

public class Arco implements Comparable<Arco>{
	private String t1;
	private String t2;
	private Double peso;
	
	public Arco(String t1, String t2, Double peso) {
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
	public Double getPeso() {
		return peso;
	}
	public void setPeso(Double peso) {
		this.peso = peso;
	}
	@Override
	public int compareTo(Arco o) {
		return this.t1.compareTo(o.getT2());
	}
	@Override
	public String toString() {
		return "Arco [t1=" + t1 + ", t2=" + t2 + ", peso=" + peso + "]";
	}
	
	
	

}
