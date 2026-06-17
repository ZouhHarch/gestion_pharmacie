package model;

import java.time.LocalDate;

public class Vente{
    private int id ;
    private LocalDate date ;
    private int quantite ;
    private double total ;
    private Medicament medicament ;

    public Vente(){}

    //Constructeur parametré
    public Vente(int id,LocalDate date ,int quantite,Medicament medicament){
        this.id = id ;
        this.date = date ;
        this.quantite = quantite ;
        this.medicament=medicament;
        this.total = calculerTotal();
    }

    //Constructeur de copie
    public Vente(Vente other){
        this.id = other.id ;
        this.date = other.date ;
        this.quantite = other.quantite ;
        this.total = other.total ;
        this.medicament = other.medicament ;
    }

    //geters
    public int getId(){return this.id ;}
    public LocalDate getDate(){return this.date ;}
    public int getQuantite(){return this.quantite ;}
    public double getTotal(){return this.total ;}
    public Medicament getMedicament(){return this.medicament ;}

    //seters
    public void setId(int id){this.id = id ;}
    public void setDate(LocalDate date){this.date = date ;}
    public void setQuantite(int quantite){this.quantite = quantite ;}
    public void setTotal(double total){this.total = total ;}
    public void setMedicament(Medicament medicament){this.medicament = medicament ;}

    //Calculer total
    public double calculerTotal(){ return this.quantite * this.medicament.getPrix();}
}
