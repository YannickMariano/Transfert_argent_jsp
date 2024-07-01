package net.javaguides.transfert.model;

import java.sql.Date;
import java.time.LocalDate;

public class Envoyer {
	private int id_env;
	private int num_envoyeur;
	private int num_recepteur;
	private Double montant;
	private String date;
	private String raison;
	private String recepteurNom;
	
	public Envoyer(int id_env, int num_envoyeur, int num_recepteur, Double montant, String date, String raison) {
		super();
		this.id_env = id_env;
		this.num_envoyeur = num_envoyeur;
		this.num_recepteur = num_recepteur;
		this.montant = montant;
		this.date = date;
		this.raison = raison;
	}
	public Envoyer(int num_envoyeur, int num_recepteur, Double montant, String date, String raison) {
		super();
		this.num_envoyeur = num_envoyeur;
		this.num_recepteur = num_recepteur;
		this.montant = montant;
		this.date = date;
		this.raison = raison;
	}
	public Envoyer(String num_envoyeur2, String num_recepteur2, Double montant2, Object object, String raison2) {
		// TODO Auto-generated constructor stub
	}
	public Envoyer(int int1, int int2, int int3, double double1, Date date2, String string) {
		// TODO Auto-generated constructor stub
	}
	public Envoyer(int id_env, int num_envoyeur, int num_recepteur, double montant, String date, String raison, String recepteurNom) {
		super();
		this.id_env = id_env;
		this.num_envoyeur = num_envoyeur;
		this.num_recepteur = num_recepteur;
		this.montant = montant;
		this.date = date;
		this.raison = raison;
		this.recepteurNom = recepteurNom;
	}
	
	public String getRecepteurNom() {
        return recepteurNom;
    }

    public void setRecepteurNom(String recepteurNom) {
        this.recepteurNom = recepteurNom;
    }
    
	public int getId_env() {
		return id_env;
	}
	public void setId_env(int id_env) {
		this.id_env = id_env;
	}
	public int getNum_envoyeur() {
		return num_envoyeur;
	}
	public void setNum_envoyeur(int num_envoyeur) {
		this.num_envoyeur = num_envoyeur;
	}
	public int getNum_recepteur() {
		return num_recepteur;
	}
	public void setNum_recepteur(int num_recepteur) {
		this.num_recepteur = num_recepteur;
	}
	public Double getMontant() {
		return montant;
	}
	public void setMontant(Double montant) {
		this.montant = montant;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String localDate) {
		this.date = localDate;
	}
	public String getRaison() {
		return raison;
	}
	public void setRaison(String raison) {
		this.raison = raison;
	}
	
	
}