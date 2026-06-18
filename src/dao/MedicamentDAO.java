package dao;

import model.Medicament;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicamentDAO {

    private Connection conn = Database.getConnection();

    public List<Medicament> getAll() {
        List<Medicament> liste = new ArrayList<>();
        String query = "SELECT * FROM medicament";

        try (PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                liste.add(new Medicament(
                        rs.getInt("id_medicament"),
                        rs.getString("nom"),
                        rs.getString("laboratoire"),
                        rs.getDouble("dosage_mg"),
                        rs.getDouble("prix_unitaire"),
                        rs.getInt("stock_quantite"),
                        rs.getBoolean("ordonnance_requise"),
                        rs.getString("categorie")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Erreur getAll() : " + e.getMessage());
            e.printStackTrace();
        }
        return liste;
    }

    public Medicament getById(int id) {
        Medicament medicament = null;
        String query = "SELECT * FROM medicament WHERE id_medicament = ?";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                medicament = new Medicament(
                            rs.getInt("id_medicament"),
                        rs.getString("nom"),
                        rs.getString("laboratoire"),
                        rs.getDouble("dosage_mg"),
                        rs.getDouble("prix_unitaire"),
                        rs.getInt("stock_quantite"),
                        rs.getBoolean("ordonnance_requise"),
                        rs.getString("categorie")
                );
            }
            rs.close();
        } catch (SQLException e) {
            System.err.println("Erreur getById() : " + e.getMessage());
            e.printStackTrace();
        }
        return medicament;
    }

    public boolean ajouter(Medicament m) {
        String query = "INSERT INTO medicament (id_medicament, nom, laboratoire, dosage_mg, prix_unitaire, stock_quantite, ordonnance_requise, categorie) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, m.getId());
            ps.setString(2, m.getNom());
            ps.setString(3, m.getLaboratoire());
            ps.setDouble(4, m.getDosage());
            ps.setDouble(5, m.getPrix());
            ps.setInt(6, m.getStock());
            ps.setBoolean(7, m.isOrdonnance());
            ps.setString(8, m.getCategorie());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Erreur ajouter() : " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean modifier(Medicament m) {
        String query = "UPDATE medicament SET nom=?, laboratoire=?, dosage_mg=?, prix_unitaire=?, stock_quantite=?, ordonnance_requise=?, categorie=? WHERE id_medicament=?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, m.getNom());
            ps.setString(2, m.getLaboratoire());
            ps.setDouble(3, m.getDosage());
            ps.setDouble(4, m.getPrix());
            ps.setInt(5, m.getStock());
            ps.setBoolean(6, m.isOrdonnance());
            ps.setString(7, m.getCategorie());
            ps.setInt(8, m.getId());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Erreur modifier() : " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean supprimer(int id) {
        String query = "DELETE FROM medicament WHERE id_medicament = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Erreur supprimer() : " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public List<Medicament> rechercher(String nom) {
        List<Medicament> resultats = new ArrayList<>();
        String query = "SELECT * FROM medicament WHERE nom LIKE ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, "%" + nom + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                resultats.add(new Medicament(
                        rs.getInt("id_medicament"),
                        rs.getString("nom"),
                        rs.getString("laboratoire"),
                        rs.getDouble("dosage_mg"),
                        rs.getDouble("prix_unitaire"),
                        rs.getInt("stock_quantite"),
                        rs.getBoolean("ordonnance_requise"),
                        rs.getString("categorie")
                ));
            }
            rs.close();
        } catch (SQLException e) {
            System.err.println("Erreur rechercher() : " + e.getMessage());
            e.printStackTrace();
        }
        return resultats;
    }

    public List<Medicament> getStockFaible(int seuil) {
        List<Medicament> resultats = new ArrayList<>();
        String query = "SELECT * FROM medicament WHERE stock_quantite <= ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, seuil);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                resultats.add(new Medicament(
                        rs.getInt("id_medicament"),
                        rs.getString("nom"),
                        rs.getString("laboratoire"),
                        rs.getDouble("dosage_mg"),
                        rs.getDouble("prix_unitaire"),
                        rs.getInt("stock_quantite"),
                        rs.getBoolean("ordonnance_requise"),
                        rs.getString("categorie")
                ));
            }
            rs.close();
        } catch (SQLException e) {
            System.err.println("Erreur getStockFaible() : " + e.getMessage());
            e.printStackTrace();
        }
        return resultats;
    }
}