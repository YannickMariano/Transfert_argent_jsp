package net.javaguides.transfert.model;

public class Frais {
	private int id_frais;
	private Double montant_1;
	private Double montant_2;
	private Double frais ;
	public Frais(int id_frais, Double montant_1, Double montant_2, Double frais) {
		super();
		this.id_frais = id_frais;
		this.montant_1 = montant_1;
		this.montant_2 = montant_2;
		this.frais = frais;
	}
	public Frais(Double montant_1, Double montant_2, Double frais) {
		super();
		this.montant_1 = montant_1;
		this.montant_2 = montant_2;
		this.frais = frais;
	}
	public int getId_frais() {
		return id_frais;
	}
	public void setId_frais(int id_frais) {
		this.id_frais = id_frais;
	}
	public Double getMontant_1() {
		return montant_1;
	}
	public void setMontant_1(Double montant_1) {
		this.montant_1 = montant_1;
	}
	public Double getMontant_2() {
		return montant_2;
	}
	public void setMontant_2(Double montant_2) {
		this.montant_2 = montant_2;
	}
	public Double getFrais() {
		return frais;
	}
	public void setFrais(Double frais) {
		this.frais = frais;
	}
	
	
	
	
}