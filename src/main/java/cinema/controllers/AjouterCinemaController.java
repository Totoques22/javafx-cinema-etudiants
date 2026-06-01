package cinema.controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import cinema.BO.Cinema;
import cinema.BO.Franchise;
import cinema.BO.Utilisateur;
import cinema.DAO.CinemaDAO;
import cinema.DAO.FranchiseDAO;
import cinema.DAO.UtilisateurDAO;
import cinema.Session;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AjouterCinemaController extends MenuController implements Initializable {

    @FXML
    private TextField tfNomCinema, tfAdresseCinema, tfVilleCinema;
    @FXML
    private Button bRetour;
    @FXML
    private ListView<Franchise> lvFranchise;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ObservableList<Franchise> franchises = getFranchiseList();

        lvFranchise.setItems(franchises);
    }

    private ObservableList<Franchise> getFranchiseList() {

        FranchiseDAO franchiseDAO = new FranchiseDAO();
        List<Franchise> franchises = franchiseDAO.findAll();

        ObservableList<Franchise> list = FXCollections.observableArrayList(franchises);

        return list;
    }

    @FXML
    public void bRetourClick(ActionEvent event) {
        Stage stageP = (Stage) bRetour.getScene().getWindow();
        stageP.close();

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(
                    getClass().getResource("/cinema/views/page_accueil.fxml"));
            Parent root = fxmlLoader.load();

            AccueilController accueilController = fxmlLoader.getController();
            accueilController.setUtilisateur(user);
            accueilController.setBienvenue();

            // Créer une nouvelle fenêtre (Stage)
            Stage stage = new Stage();
            stage.setTitle("Accueil Gestion de franchises");
            stage.setScene(new Scene(root));

            // Configurer la fenêtre en tant que modal
            stage.initModality(Modality.APPLICATION_MODAL);

            // Afficher la fenêtre et attendre qu'elle se ferme
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void bEnregistrerClick(ActionEvent event) {

        String x = tfNomCinema.getText();
        String y = tfAdresseCinema.getText();
        String w = tfVilleCinema.getText();


        Franchise selectedFran = lvFranchise.getSelectionModel().getSelectedItem();

        if (selectedFran != null) {
            int z = selectedFran.getIdFranchise();

            Cinema nouvCinema = new Cinema(0, x, y, w, z);

            CinemaDAO cinemaDAO = new CinemaDAO();
            boolean controle = cinemaDAO.create(nouvCinema);
            if (controle) {
                tfNomCinema.clear();
                tfAdresseCinema.clear();
                tfVilleCinema.clear();

                lvFranchise.getSelectionModel().clearSelection();
            }
        } else {
            System.out.println("Aucun gérant sélectionné !");
        }

    }

    @FXML
    public void bEffacerClick(ActionEvent event) {
        if (tfNomCinema != null)
            tfNomCinema.clear();
        if (tfAdresseCinema != null)
            tfAdresseCinema.clear();
        if (tfVilleCinema != null)
            tfVilleCinema.clear();
        lvFranchise.getSelectionModel().clearSelection();
    }

}