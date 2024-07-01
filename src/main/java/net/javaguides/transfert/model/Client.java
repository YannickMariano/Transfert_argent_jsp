package net.javaguides.transfert.model;

public class Client {
	private int id;
	private String numtel;
	private String nom;
	private String sexe;
	private String pays;
	private Double solde;
	private String mail;		
	
	public Client(int id, String numtel, String nom, String sexe, String pays, Double solde, String mail) {
		super();
		this.id = id;
		this.numtel = numtel;
		this.nom = nom;
		this.sexe = sexe;
		this.pays = pays;
		this.solde = solde;
		this.mail = mail;
	}

	public Client(String numtel, String nom, String sexe, String pays, Double solde, String mail) {
		super();
		this.numtel = numtel;
		this.nom = nom;
		this.sexe = sexe;
		this.pays = pays;
		this.solde = solde;
		this.mail = mail;
	}
	
	public Client(int id_client, String num_tel) {
		super();
		this.id = id_client;
		this.numtel = num_tel;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNumtel() {
		return numtel;
	}
	public void setNumtel(String numtel) {
		this.numtel = numtel;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getSexe() {
		return sexe;
	}
	public void setSexe(String sexe) {
		this.sexe = sexe;
	}
	public String getPays() {
		return pays;
	}
	public void setPays(String pays) {
		this.pays = pays;
	}
	public Double getSolde() {
		return solde;
	}
	public void setSolde(Double solde) {
		this.solde = solde;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	
	
	
}
