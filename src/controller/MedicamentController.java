package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import model.Medicament;
import dao.MedicamentDAO;

import java.io.File;
import java.io.PrintWriter;

public class MedicamentController {

    @FXML private TableView<Medicament> tableMedicaments;
    @FXML private TableColumn<Medicament, Integer> colId;
    @FXML private TableColumn<Medicament, String> colNom;
    @FXML private TableColumn<Medicament, Double> colPrix;
    @FXML private TableColumn<Medicament, Integer> colStock;

    @FXML private TextField searchField;
    @FXML private TextField idField;
    @FXML private TextField nomField;
    @FXML private TextField prixField;
    @FXML private TextField stockField;
    @FXML private Label idLabel;

    private MedicamentDAO medicamentDAO = new MedicamentDAO();
    private ObservableList<Medicament> medicaments = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colPrix.setCellValueFactory(new PropertyValueFactory<>("prix"));
        colStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        chargerMedicaments();

        tableMedicaments.getSelectionModel().selectedItemProperty().addListener(
                (obs, old, selected) -> {
                    if (selected != null) remplirFormulaire(selected);
                }
        );
    }

    private void chargerMedicaments() {
        medicaments.setAll(medicamentDAO.getAll());
        tableMedicaments.setItems(medicaments);
    }

    private void remplirFormulaire(Medicament m) {
        idField.setText(String.valueOf(m.getId()));
        nomField.setText(m.getNom());
        prixField.setText(String.valueOf(m.getPrix()));
        stockField.setText(String.valueOf(m.getStock()));
        idLabel.setText("Modification en cours...");
    }

    @FXML
    public void ajouterMedicament() {
        try {
            int id = Integer.parseInt(idField.getText());
            String nom = nomField.getText();
            double prix = Double.parseDouble(prixField.getText());
            int stock = Integer.parseInt(stockField.getText());

            Medicament m = new Medicament(id, nom, "", 0, prix, stock, false, "");
            medicamentDAO.ajouter(m);
            chargerMedicaments();
            annulerFormulaire();
            showAlert("Succès", "Médicament ajouté !", Alert.AlertType.INFORMATION);
        } catch (Exception e) {
            showAlert("Erreur", "Veuillez remplir tous les champs correctement.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void modifierMedicament() {
        try {
            int id = Integer.parseInt(idField.getText());
            String nom = nomField.getText();
            double prix = Double.parseDouble(prixField.getText());
            int stock = Integer.parseInt(stockField.getText());

            Medicament m = new Medicament(id, nom, "", 0, prix, stock, false, "");
            medicamentDAO.modifier(m);
            chargerMedicaments();
            annulerFormulaire();
            showAlert("Succès", "Médicament modifié !", Alert.AlertType.INFORMATION);
        } catch (Exception e) {
            showAlert("Erreur", "Sélectionnez un médicament.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void supprimerMedicament() {
        Medicament selected = tableMedicaments.getSelectionModel().getSelectedItem();
        if (selected != null) {
            medicamentDAO.supprimer(selected.getId());
            chargerMedicaments();
            annulerFormulaire();
            showAlert("Succès", "Médicament supprimé !", Alert.AlertType.INFORMATION);
        } else {
            showAlert("Erreur", "Sélectionnez un médicament.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void annulerFormulaire() {
        idField.clear();
        nomField.clear();
        prixField.clear();
        stockField.clear();
        idLabel.setText("");
        tableMedicaments.getSelectionModel().clearSelection();
    }

    @FXML
    public void rechercher() {
        String texte = searchField.getText();
        if (texte == null || texte.isEmpty()) {
            chargerMedicaments();
        } else {
            medicaments.setAll(medicamentDAO.rechercher(texte));
            tableMedicaments.setItems(medicaments);
        }
    }

    @FXML
    public void reinitialiserRecherche() {
        searchField.clear();
        chargerMedicaments();
    }

    @FXML
    public void exporterMedicamentsCSV() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Exporter les médicaments en CSV");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers CSV", "*.csv"));
        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            try (PrintWriter writer = new PrintWriter(file)) {
                writer.println("ID,Nom,Laboratoire,Dosage,Prix,Stock,Ordonnance,Categorie");

                for (Medicament m : medicamentDAO.getAll()) {
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

                showAlert("✅ Export CSV", "Export des médicaments réussi !", Alert.AlertType.INFORMATION);

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