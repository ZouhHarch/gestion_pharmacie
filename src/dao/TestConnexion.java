package dao;

import model.Medicament;
import model.Vente;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

public class TestConnexion {
    public static void main(String[] args) {
        System.out.println("Tentative de connexion...");
        Connection c = Database.getConnection();
        VenteDAO venteDAO = new VenteDAO() ;
        double chiffre = venteDAO.getChiffreAffaires();
        if (c != null) {
            System.out.println("Succès ! Le projet est parfaitement connecté à WampServer.");
        } else {
            System.out.println("Échec de la connexion.");
        }

        System.out.println("Chiffre d'affaires est : "+chiffre);

        Database.closeConnection();

    }
}