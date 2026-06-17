package dao;

import model.Medicament;
import model.Vente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VenteDAO {

    //Recuperation de la connection
    private Connection conn = Database.getConnection();

    public List<Vente> getAll() {
        List<Vente> listeVentes = new ArrayList<>();

        String query = "SELECT v.id AS vente_id, v.date, v.quantite, v.total, " +
                "m.id AS med_id, m.nom, m.laboratoire, m.dosage, m.prix, m.stock, m.ordonnance, m.categorie " +
                "FROM vente v " +
                "INNER JOIN medicament m ON v.medicament_id = m.id";


        if (conn != null) {
            try (PreparedStatement ps = conn.prepareStatement(query);
                 ResultSet rs = ps.executeQuery()) {

                // On parcourt chaque ligne retournée par la base de données
                while (rs.next()) {
                    //On crée et on remplit d'abord l'objet Medicament
                    Medicament med = new Medicament();
                    med.setId(rs.getInt("med_id"));
                    med.setNom(rs.getString("nom"));
                    med.setLaboratoire(rs.getString("laboratoire"));
                    med.setDosage(rs.getDouble("dosage"));
                    med.setPrix(rs.getDouble("prix"));
                    med.setStock(rs.getInt("stock"));
                    med.setOrdonnance(rs.getBoolean("ordonnance"));
                    med.setCategorie(rs.getString("categorie"));

                    //  On crée et on remplit l'objet Vente
                    Vente vente = new Vente();
                    vente.setId(rs.getInt("vente_id"));

                    // Conversion de la date SQL (java.sql.Date) vers le type LocalDate de ton diagramme
                    if (rs.getDate("date") != null) {
                        vente.setDate(rs.getDate("date").toLocalDate());
                    }

                    vente.setQuantite(rs.getInt("quantite"));
                    vente.setTotal(rs.getDouble("total"));

                    //  On associe le médicament à la vente
                    vente.setMedicament(med);

                    //  On ajoute la vente complète à notre liste
                    listeVentes.add(vente);
                }

            } catch (SQLException e) {
                System.err.println("Erreur lors de la récupération de la liste des ventes.");
                e.printStackTrace();
            }
        }
        return listeVentes;
    }

    public boolean create(Vente v){
        String query = "INSERT INTO vente (date, quantite, total, medicament_id) VALUES (?, ?, ?, ?)";

        try(PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)){

            // Conversion de LocalDate (Java) en java.sql.Date (SQL)
            if (v.getDate() != null) {
                ps.setDate(1, java.sql.Date.valueOf(v.getDate()));
            } else {
                ps.setNull(1, java.sql.Types.DATE);
            }
            ps.setInt(2, v.getQuantite());
            ps.setDouble(3, v.getTotal());
            // On récupère l'ID du médicament associé à cette vente
            if (v.getMedicament() != null) {
                ps.setInt(4, v.getMedicament().getId());
            } else {
                System.err.println("Erreur : Impossible de créer une vente sans médicament associé.");
                return false;
            }

            // Exécution de la requête (executeUpdate renvoie le nombre de lignes modifiées)
            int lignesModifiees = ps.executeUpdate();
            if (lignesModifiees > 0) {
                // 3. Récupération de l'ID auto-généré
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        // On attribue le nouvel ID généré par WampServer à notre objet Java
                        v.setId(generatedKeys.getInt(1));
                    }
                }
                System.out.println("Vente enregistrée avec succès dans la base de données ! ID généré : " + v.getId());
                return true;
            }



        }catch(SQLException e){
            System.err.println("Erreur lors de l'insertion de la vente.");
            e.printStackTrace();
            return false ;
        }
        return  false ;
    }

    public List<Vente> getVentesByMedicament(int idMed) {
        List<Vente> listeVentes = new ArrayList<>();
        String query = "SELECT v.id AS vente_id, v.date, v.quantite, v.total, " +
                "m.id AS med_id, m.nom, m.laboratoire, m.dosage, m.prix, m.stock, m.ordonnance, m.categorie " +
                "FROM vente v " +
                "INNER JOIN medicament m ON v.medicament_id = m.id " +
                "WHERE v.medicament_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, idMed);

            try (ResultSet rs = ps.executeQuery()) {
                // On parcourt toutes les ventes trouvées pour ce médicament
                while (rs.next()) {

                    //On reconstruit l'objet Médicament
                    Medicament med = new Medicament();
                    med.setId(rs.getInt("med_id"));
                    med.setNom(rs.getString("nom"));
                    med.setLaboratoire(rs.getString("laboratoire"));
                    med.setDosage(rs.getDouble("dosage"));
                    med.setPrix(rs.getDouble("prix"));
                    med.setStock(rs.getInt("stock"));
                    med.setOrdonnance(rs.getBoolean("ordonnance"));
                    med.setCategorie(rs.getString("categorie"));

                    //On crée l'objet Vente
                    Vente vente = new Vente();
                    vente.setId(rs.getInt("vente_id"));

                    if (rs.getDate("date") != null) {
                        vente.setDate(rs.getDate("date").toLocalDate());
                    }

                    vente.setQuantite(rs.getInt("quantite"));
                    vente.setTotal(rs.getDouble("total"));

                    //On attache le médicament à la vente
                    vente.setMedicament(med);

                    //On ajoute la vente à la liste
                    listeVentes.add(vente);
                }
            }

        } catch (SQLException e) {
            System.err.println("Erreur dans getVentesByMedicament() : " + e.getMessage());
            e.printStackTrace();
        }

        return listeVentes;
    }

    public double getChiffreAffaires() {
        double chiffreAffaires = 0.0;
        String query = "SELECT SUM(total) AS chiffre_affaire FROM vente";

        try (PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            // La requête SUM() retourne toujours une seule ligne
            if (rs.next()) {
                chiffreAffaires = rs.getDouble("chiffre_affaire");
            }

        } catch (SQLException e) {
            System.err.println("Erreur dans getChiffreAffaires() : " + e.getMessage());
            e.printStackTrace();
        }

        return chiffreAffaires;
    }





}
