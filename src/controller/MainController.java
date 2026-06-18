package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TabPane;
import javafx.stage.FileChooser;
import model.Medicament;
import model.Vente;
import dao.MedicamentDAO;
import dao.VenteDAO;

import java.io.File;
import java.io.PrintWriter;

public class MainController {

    @FXML private TabPane tabPane;

    @FXML
    public void goToMedicaments() {
        tabPane.getSelectionModel().select(0);
    }

    @FXML
    public void goToVentes() {
        tabPane.getSelectionModel().select(1);
    }

    @FXML
    public void goToStats() {
        tabPane.getSelectionModel().select(2);
    }

    // ===== EXPORT MÉDICAMENTS =====
    @FXML
    public void exporterMedicamentsCSV() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Exporter les médicaments en CSV");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers CSV", "*.csv"));
        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            try (PrintWriter writer = new PrintWriter(file)) {
                writer.println("ID,Nom,Laboratoire,Dosage,Prix,Stock,Ordonnance,Categorie");

                for (Medicament m : new MedicamentDAO().getAll()) {
                    writer.println(
                            m.getId() + "," +
                                    m.getNom() + "," +
                                    m.getLaboratoire() + "," +
                                    m.getDosage() + "," +
                                    m.getPrix() + "," +
                                    m.getStock() + "," +
                                    m.isOrdonnance() + "," +
                                    m.getCategorie()
                    );
                }

                showAlert("✅ Export CSV", "Export des médicaments réussi !\nFichier : " + file.getName(), Alert.AlertType.INFORMATION);

            } catch (Exception e) {
                showAlert("❌ Erreur", "Erreur lors de l'export : " + e.getMessage(), Alert.AlertType.ERROR);
                e.printStackTrace();
            }
        }
    }

    // ===== EXPORT VENTES =====
    @FXML
    public void exporterVentesCSV() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Exporter les ventes en CSV");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers CSV", "*.csv"));
        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            try (PrintWriter writer = new PrintWriter(file)) {
                writer.println("ID_Vente,Date,Quantite,Total,Médicament_ID,Médicament_Nom");

                for (Vente v : new VenteDAO().getAll()) {
                    writer.println(
                            v.getId() + "," +
                                    v.getDate() + "," +
                                    v.getQuantite() + "," +
                                    v.getTotal() + "," +
                                    v.getMedicament().getId() + "," +
                                    v.getMedicament().getNom()
                    );
                }

                showAlert("✅ Export CSV", "Export des ventes réussi !\nFichier : " + file.getName(), Alert.AlertType.INFORMATION);

            } catch (Exception e) {
                showAlert("❌ Erreur", "Erreur lors de l'export : " + e.getMessage(), Alert.AlertType.ERROR);
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void quitter() {
        System.exit(0);
    }

    @FXML
    public void aPropos() {
        showAlert("À propos", "Application Gestion Pharmacie\nVersion 1.0\nDéveloppé par Mohamed-Taha Hrimache / Zouhir Herchaoui", Alert.AlertType.INFORMATION);
    }

    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}