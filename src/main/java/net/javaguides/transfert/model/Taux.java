package net.javaguides.transfert.model;

public class Taux {
	private int id;
	private Double montant1;
	private Double montant2;
	
	
	
	public Taux(Double montant1, Double montant2) {
		super();
		this.montant1 = montant1;
		this.montant2 = montant2;
	}


	public Taux(int id, Double montant1, Double montant2) {
		super();
		this.id = id;
		this.montant1 = montant1;
		this.montant2 = montant2;
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Double getMontant1() {
		return montant1;
	}
	public void setMontant1(Double montant1) {
		this.montant1 = montant1;
	}
	public Double getMontant2() {
		return montant2;
	}
	public void setMontant2(Double montant2) {
		this.montant2 = montant2;
	}
	
	
}
