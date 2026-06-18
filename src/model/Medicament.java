package model;

public class Medicament {
    private int id;
    private String nom;
    private String laboratoire;
    private double dosage;
    private double prix;
    private int stock;
    private boolean ordonnance;
    private String categorie;

    public Medicament() {}

    public Medicament(int id, String nom, String laboratoire, double dosage, double prix, int stock, boolean ordonnance, String categorie) {
        this.id = id;
        this.nom = nom;
        this.laboratoire = laboratoire;
        this.dosage = dosage;
        this.prix = prix;
        this.stock = stock;
        this.ordonnance = ordonnance;
        this.categorie = categorie;
    }

    // Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getLaboratoire() { return laboratoire; }
    public void setLaboratoire(String laboratoire) { this.laboratoire = laboratoire; }
    public double getDosage() { return dosage; }
    public void setDosage(double dosage) { this.dosage = dosage; }
    public double getPrix() { return prix; }
    public void setPrix(double prix) { this.prix = prix; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
    public boolean isOrdonnance() { return ordonnance; }
    public void setOrdonnance(boolean ordonnance) { this.ordonnance = ordonnance; }
    public String getCategorie() { return categorie; }
    public void setCategorie(String categorie) { this.categorie = categorie; }

    @Override
    public String toString() {
        return nom + " - " + prix + " MAD";
    }
}