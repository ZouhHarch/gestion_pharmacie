package controller;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import model.Medicament;
import model.Vente;
import dao.MedicamentDAO;
import dao.VenteDAO;

public class StatistiquesController {

    @FXML private Label totalMedicaments;
    @FXML private Label stockFaible;
    @FXML private Label chiffreAffaires;
    @FXML private Label nombreVentes;
    @FXML private BarChart<String, Number> ventesChart;

    private MedicamentDAO medicamentDAO = new MedicamentDAO();
    private VenteDAO venteDAO = new VenteDAO();

    public void rafraichir() {
        chargerStatistiques();
        chargerGraphique();
    }

    @FXML
    public void initialize() {
        chargerStatistiques();
        chargerGraphique();
    }

    private void chargerStatistiques() {
        totalMedicaments.setText(String.valueOf(medicamentDAO.getAll().size()));
        stockFaible.setText(String.valueOf(medicamentDAO.getStockFaible(5).size()));
        chiffreAffaires.setText(String.format("%.2f MAD", venteDAO.getChiffreAffaires()));
        nombreVentes.setText(String.valueOf(venteDAO.getAll().size()));
    }

    private void chargerGraphique() {
        ventesChart.getData().clear();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Quantités vendues");

        for (Medicament m : medicamentDAO.getAll()) {
            int total = 0;
            for (Vente v : venteDAO.getAll()) {
                if (v.getMedicament().getId() == m.getId()) {
                    total += v.getQuantite();
                }
            }
            if (total > 0) {
                series.getData().add(new XYChart.Data<>(m.getNom(), total));
            }
        }

        ventesChart.getData().add(series);
    }
}