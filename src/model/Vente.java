package model;

import java.time.LocalDate;

public class Vente {
    private int id;
    private LocalDate date;
    private int quantite;
    private double total;
    private Medicament medicament;

    public Vente() {}

    public Vente(int id, Medicament medicament, int quantite, LocalDate date) {
        this.id = id;
        this.medicament = medicament;
        this.quantite = quantite;
        this.date = date;
        if (medicament != null) {
            this.total = medicament.getPrix() * quantite;
        }
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public int getQuantite() { return quantite; }
    public void setQuantite(int quantite) { this.quantite = quantite; }
    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }
    public Medicament getMedicament() { return medicament; }
    public void setMedicament(Medicament medicament) { this.medicament = medicament; }

    @Override
    public String toString() {
        return date + " - " + medicament.getNom() + " x" + quantite + " = " + total + " MAD";
    }
}