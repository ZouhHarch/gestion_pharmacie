package controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import model.Medicament;
import model.Vente;
import dao.MedicamentDAO;
import dao.VenteDAO;
import java.io.File;
import java.io.PrintWriter;
import java.time.LocalDate;

public class VenteController {

    @FXML private ComboBox<Medicament> medicamentCombo;
    @FXML private Spinner<Integer> quantiteSpinner;
    @FXML private DatePicker datePicker;
    @FXML private Label totalLabel;
    @FXML private ListView<String> listeVentes;

    private MedicamentDAO medicamentDAO = new MedicamentDAO();
    private VenteDAO venteDAO = new VenteDAO();

    @FXML
    public void initialize() {
        quantiteSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1));
        datePicker.setValue(LocalDate.now());
        medicamentCombo.setItems(FXCollections.observableArrayList(medicamentDAO.getAll()));

        medicamentCombo.getSelectionModel().selectedItemProperty().addListener((obs, old, selected) -> calculerTotal());
        quantiteSpinner.valueProperty().addListener((obs, old, val) -> calculerTotal());

        chargerHistorique();
    }

    private void calculerTotal() {
        Medicament m = medicamentCombo.getSelectionModel().getSelectedItem();
        int qte = quantiteSpinner.getValue();
        if (m != null && qte > 0) {
            totalLabel.setText(String.format("%.2f MAD", m.getPrix() * qte));
        } else {
            totalLabel.setText("0.00 MAD");
        }
    }

    public void chargerHistorique() {
        listeVentes.getItems().clear();
        for (Vente v : venteDAO.getAll()) {
            listeVentes.getItems().add(
                    v.getDate() + " - " + v.getMedicament().getNom() +
                            " x" + v.getQuantite() + " = " + v.getTotal() + " MAD"
            );
        }
    }

    @FXML
    public void enregistrerVente() {
        Medicament m = medicamentCombo.getSelectionModel().getSelectedItem();
        if (m == null) {
            showAlert("Erreur", "Sélectionnez un médicament.", Alert.AlertType.ERROR);
            return;
        }
        int qte = quantiteSpinner.getValue();
        LocalDate date = datePicker.getValue();

        Vente v = new Vente(0, m, qte, date);
        venteDAO.ajouter(v);

        m.setStock(m.getStock() - qte);
        medicamentDAO.modifier(m);

        chargerHistorique();
        showAlert("Succès", "Vente enregistrée !", Alert.AlertType.INFORMATION);

        quantiteSpinner.getValueFactory().setValue(1);
        medicamentCombo.getSelectionModel().clearSelection();
        totalLabel.setText("0.00 MAD");
    }

    @FXML
    public void annulerVente() {
        quantiteSpinner.getValueFactory().setValue(1);
        medicamentCombo.getSelectionModel().clearSelection();
        totalLabel.setText("0.00 MAD");
    }

    @FXML
    public void exporterVentesCSV() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Exporter les ventes en CSV");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers CSV", "*.csv"));
        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            try (PrintWriter writer = new PrintWriter(file)) {
                writer.println("ID_Vente,Date,Quantite,Total,Médicament_ID,Médicament_Nom");

                for (Vente v : venteDAO.getAll()) {
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

    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}