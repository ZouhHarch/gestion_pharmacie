package dao;

import model.Medicament;
import model.Vente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VenteDAO {

    private Connection conn = Database.getConnection();

    public List<Vente> getAll() {
        List<Vente> listeVentes = new ArrayList<>();

        String query = "SELECT v.id, v.date, v.quantite, v.total, " +
                "m.id_medicament, m.nom, m.laboratoire, m.dosage_mg, m.prix_unitaire, m.stock_quantite, m.ordonnance_requise, m.categorie " +
                "FROM vente v " +
                "INNER JOIN medicament m ON v.medicament_id = m.id_medicament";

        if (conn != null) {
            try (PreparedStatement ps = conn.prepareStatement(query);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    Medicament med = new Medicament();
                    med.setId(rs.getInt("m.id_medicament"));
                    med.setNom(rs.getString("m.nom"));
                    med.setLaboratoire(rs.getString("m.laboratoire"));
                    med.setDosage(rs.getDouble("m.dosage_mg"));
                    med.setPrix(rs.getDouble("m.prix_unitaire"));
                    med.setStock(rs.getInt("m.stock_quantite"));
                    med.setOrdonnance(rs.getBoolean("m.ordonnance_requise"));
                    med.setCategorie(rs.getString("m.categorie"));

                    Vente vente = new Vente();
                    vente.setId(rs.getInt("v.id"));
                    if (rs.getDate("v.date") != null) {
                        vente.setDate(rs.getDate("v.date").toLocalDate());
                    }
                    vente.setQuantite(rs.getInt("v.quantite"));
                    vente.setTotal(rs.getDouble("v.total"));
                    vente.setMedicament(med);

                    listeVentes.add(vente);
                }

            } catch (SQLException e) {
                System.err.println("Erreur getAll() : " + e.getMessage());
                e.printStackTrace();
            }
        }
        return listeVentes;
    }

    public boolean create(Vente v) {
        String query = "INSERT INTO vente (date, quantite, total, medicament_id) VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            if (v.getDate() != null) {
                ps.setDate(1, java.sql.Date.valueOf(v.getDate()));
            } else {
                ps.setNull(1, java.sql.Types.DATE);
            }
            ps.setInt(2, v.getQuantite());
            ps.setDouble(3, v.getTotal());

            if (v.getMedicament() != null) {
                ps.setInt(4, v.getMedicament().getId());
            } else {
                System.err.println("Erreur : Vente sans médicament associé.");
                return false;
            }

            int lignesModifiees = ps.executeUpdate();
            if (lignesModifiees > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        v.setId(generatedKeys.getInt(1));
                    }
                }
                System.out.println("Vente enregistrée ! ID : " + v.getId());
                return true;
            }

        } catch (SQLException e) {
            System.err.println("Erreur create() : " + e.getMessage());
            e.printStackTrace();
            return false;
        }
        return false;
    }

    public List<Vente> getVentesByMedicament(int idMed) {
        List<Vente> listeVentes = new ArrayList<>();
        String query = "SELECT v.id, v.date, v.quantite, v.total, " +
                "m.id_medicament, m.nom, m.laboratoire, m.dosage_mg, m.prix_unitaire, m.stock_quantite, m.ordonnance_requise, m.categorie " +
                "FROM vente v " +
                "INNER JOIN medicament m ON v.medicament_id = m.id_medicament " +
                "WHERE v.medicament_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, idMed);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Medicament med = new Medicament();
                    med.setId(rs.getInt("m.id_medicament"));
                    med.setNom(rs.getString("m.nom"));
                    med.setLaboratoire(rs.getString("m.laboratoire"));
                    med.setDosage(rs.getDouble("m.dosage_mg"));
                    med.setPrix(rs.getDouble("m.prix_unitaire"));
                    med.setStock(rs.getInt("m.stock_quantite"));
                    med.setOrdonnance(rs.getBoolean("m.ordonnance_requise"));
                    med.setCategorie(rs.getString("m.categorie"));

                    Vente vente = new Vente();
                    vente.setId(rs.getInt("v.id"));
                    if (rs.getDate("v.date") != null) {
                        vente.setDate(rs.getDate("v.date").toLocalDate());
                    }
                    vente.setQuantite(rs.getInt("v.quantite"));
                    vente.setTotal(rs.getDouble("v.total"));
                    vente.setMedicament(med);

                    listeVentes.add(vente);
                }
            }

        } catch (SQLException e) {
            System.err.println("Erreur getVentesByMedicament() : " + e.getMessage());
            e.printStackTrace();
        }

        return listeVentes;
    }

    public double getChiffreAffaires() {
        double chiffreAffaires = 0.0;
        String query = "SELECT SUM(total) AS chiffre_affaire FROM vente";

        try (PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                chiffreAffaires = rs.getDouble("chiffre_affaire");
            }

        } catch (SQLException e) {
            System.err.println("Erreur getChiffreAffaires() : " + e.getMessage());
            e.printStackTrace();
        }

        return chiffreAffaires;
    }

    public int getTotalVentes() {
        int total = 0;
        String query = "SELECT COUNT(*) AS total FROM vente";

        try (PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                total = rs.getInt("total");
            }

        } catch (SQLException e) {
            System.err.println("Erreur getTotalVentes() : " + e.getMessage());
            e.printStackTrace();
        }

        return total;
    }

    public void ajouter(Vente v) { create(v); }
    public List<Vente> getVentesParMedicament(int idMed) { return getVentesByMedicament(idMed); }
}