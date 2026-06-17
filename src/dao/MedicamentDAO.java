package dao;

import model.Medicament;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MedicamentDAO {

    //Recuperation de la connection
    private Connection conn = Database.getConnection();

    //La selection de tous les medicament de la base de donnée
    public List<Medicament> getAll() {
        List<Medicament> liste = new ArrayList<>();
        String query = "SELECT * FROM medicament";

        try (PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                liste.add(new Medicament(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("laboratoire"),
                        rs.getDouble("dosage"),
                        rs.getDouble("prix"),
                        rs.getInt("stock"),
                        rs.getBoolean("ordonnance"),
                        rs.getString("categorie")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Erreur getAll() : " + e.getMessage());
            e.printStackTrace();
        }

        return liste;
    }

    //Recuperation d' un médicament à partir de son ID
    public Medicament getByTD(int id){
        Medicament medicament = null ;
        String query = "SELECT * FROM medicament WHERE id = ?";

        try(PreparedStatement ps = conn.prepareStatement(query)
        ){
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                medicament = new Medicament(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("laboratoire"),
                        rs.getDouble("dosage"),
                        rs.getDouble("prix"),
                        rs.getInt("stock"),
                        rs.getBoolean("ordonnance"),
                        rs.getString("categorie")
                );
            }
            rs.close();
        }catch (SQLException e){
            System.err.println("Erreur getById() : " + e.getMessage());
            e.printStackTrace();
        }
        return medicament ;
    }

    //focntion pour ajouter un nouveau médicament
    public boolean create(Medicament medicament){
        String query = "INSERT INTO medicament (nom, laboratoire, dosage, prix, stock, ordonnance, categorie) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try(PreparedStatement ps = conn.prepareStatement(query,java.sql.Statement.RETURN_GENERATED_KEYS)){
            ps.setString(1, medicament.getNom());
            ps.setString(2,medicament.getLaboratoire());
            ps.setDouble(3,medicament.getDosage());
            ps.setDouble(4,medicament.getPrix());
            ps.setInt(5, medicament.getStock());
            ps.setBoolean(6, medicament.getOrdonnance());
            ps.setString(7, medicament.getCategorie());

            int lignes = ps.executeUpdate();
            if (lignes > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        // On met à jour l'objet Java avec le vrai ID de la base
                        medicament.setId(generatedKeys.getInt(1));
                    }
                }
                System.out.println("Médicament inséré avec succès ! ID généré : " + medicament.getId());
                return true;
            }
            return false ;
        }catch (SQLException e){
            System.err.println("Erreur create() : " + e.getMessage());
            e.printStackTrace();
            return false ;
        }
    }

    //Pour modifier une colonne
    public boolean update(Medicament medicament){
        String query = "UPDATE medicament SET nom=?, laboratoire=?, dosage=?, prix=?, stock=?, ordonnance=?, categorie=? WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, medicament.getNom());
            ps.setString(2, medicament.getLaboratoire());
            ps.setDouble(3, medicament.getDosage());
            ps.setDouble(4, medicament.getPrix());
            ps.setInt(5, medicament.getStock());
            ps.setBoolean(6, medicament.getOrdonnance());
            ps.setString(7, medicament.getCategorie());
            ps.setInt(8, medicament.getId());

            int lignes = ps.executeUpdate();
            return lignes > 0; // true = modification réussie

        } catch (SQLException e) {
            System.err.println("Erreur update() : " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    //Supression d'un médicament à partire de son ID
    public boolean delete(int id){
        String query = "DELETE FROM medicament WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)){
            ps.setInt(1,id);

            int lignes = ps.executeUpdate();
            return lignes>0 ;

        }catch (SQLException e){
            System.err.println("Erreur delete() : " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    //Chercher un medicament a partire de son nom
    public List<Medicament> search(String nom){
        List<Medicament> liste = new ArrayList<>();
        String query = "SELECT * FROM medicament WHERE nom = ?";
        try(PreparedStatement ps = conn.prepareStatement(query)){
            ps.setString(1, nom);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                liste.add(new Medicament(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("laboratoire"),
                        rs.getDouble("dosage"),
                        rs.getDouble("prix"),
                        rs.getInt("stock"),
                        rs.getBoolean("ordonnance"),
                        rs.getString("categorie")
                ));

            }


        }catch (SQLException e){
            System.err.println("Erreur search(String nom) : " + e.getMessage());
            e.printStackTrace();
        }
        return liste ;
    }



}