package model;

public class Medicament {
    private int id ;
    private String nom ;
    private String laboratoire ;
    private double dosage;
    private double prix ;
    private int stock ;
    private boolean ordonnance ;
    private String categorie;

    public Medicament(){}

    //Constructeur parametré
    public Medicament(int id ,String nom,String laboratoire ,
                      double dosage ,double prix ,int stock ,boolean ordonnance,String categorie){
        this.id = id;
        this.nom = nom ;
        this.laboratoire = laboratoire ;
        this.dosage = dosage;
        this.prix = prix ;
        this.stock = stock ;
        this.ordonnance = ordonnance;
        this.categorie = categorie ;
    }
    //Constructeur de copie
    public Medicament(Medicament other){
        this.id = other.id ;
        this.nom = other.nom ;
        this.laboratoire = other.laboratoire ;
        this.dosage = other.dosage ;
        this.prix = other.prix ;
        this.stock = other.stock ;
        this.ordonnance = other.ordonnance ;
        this.categorie = other.categorie ;
    }

    //Les getters
    public int getId(){return this.id ;}
    public String getNom(){return this.nom ;}
    public String getLaboratoire(){return this.laboratoire ;}
    public double getDosage(){return this.dosage ;}
    public double getPrix(){return this.prix ;}
    public int getStock(){return this.stock ;}
    public boolean getOrdonnance(){return this.ordonnance ;}
    public String getCategorie(){return this.categorie ;}

    //Les setters
    public void setId(int id){this.id = id ;}
    public void setNom(String nom){this.nom = nom ;}
    public void setLaboratoire(String laboratoire){this.laboratoire = laboratoire ;}
    public void setDosage(double dosage){this.dosage = dosage;}
    public void setPrix(double prix){this.prix = prix ;}
    public void setStock(int stock){this.stock = stock ;}
    public void setOrdonnance(boolean ordonnance){this.ordonnance = ordonnance ;}
    public void setCategorie(String categorie){this.categorie = categorie ;}


    //Redefinition de la fonction toString()
    @Override
    public String toString(){
        return "Id         : " + this.id + "\n" +
                "Nom        : " + this.nom + "\n" +
                "Laboratoire: " + this.laboratoire + "\n" +
                "Dosage     : " + this.dosage + "\n" +
                "Prix       : " + this.prix + " DH\n" +
                "Stock      : " + this.stock + "\n" +
                "Ordonnance : " + (this.ordonnance ? "Oui" : "Non") + "\n" +
                "Categorie  : " + this.categorie;
    }

}
