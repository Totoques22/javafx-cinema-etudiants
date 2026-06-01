package cinema.controllers;


import cinema.BO.Cinema;
import cinema.BO.Salle;
import cinema.BO.Utilisateur;
import cinema.DAO.CinemaDAO;
import cinema.DAO.SalleDAO;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AjouterSalleController extends MenuController implements Initializable {

    @FXML
    private TextField tfDesc;
    @FXML
    private Button bRetour;
    @FXML
    private ListView<Cinema> lvCinemaSalle;
    @FXML
    private Spinner<Integer> spinNbPlaces = new Spinner<>(20, 1000, 20, 1);


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ObservableList<Cinema> lesCinemas = getCinemaList();

        lvCinemaSalle.setItems(lesCinemas);
    }

    private ObservableList<Cinema> getCinemaList() {

        CinemaDAO cinemaDAO = new CinemaDAO();
        List<Cinema> lesCinemas = cinemaDAO.findAll();

        return FXCollections.observableArrayList(lesCinemas);
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

            Stage stage = new Stage();
            stage.setTitle("Accueil Gestion de franchises");
            stage.setScene(new Scene(root));

            stage.initModality(Modality.APPLICATION_MODAL);

            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void bEnregistrerClick(ActionEvent event) {

        int nbPlaces = spinNbPlaces.getValue();
        String desc = tfDesc.getText();

        Cinema selectedCinema = lvCinemaSalle.getSelectionModel().getSelectedItem();

        if (selectedCinema != null) {
            int idCine = selectedCinema.getIdCinema();

            Salle newSalle = new Salle(idCine, desc, nbPlaces);

            SalleDAO salleDAO = new SalleDAO();
            boolean res = salleDAO.create(newSalle);
            if (res) {
                spinNbPlaces.getValueFactory().setValue(20);
                lvCinemaSalle.getSelectionModel().clearSelection();
            }
        } else {
            System.out.println("Aucun gérant sélectionné !");
        }
    }

    @FXML
    public void bEffacerClick(ActionEvent event) {
        if (tfDesc != null) tfDesc.clear();
        if (spinNbPlaces != null) {
            spinNbPlaces.getValueFactory().setValue(20);
        }
        lvCinemaSalle.getSelectionModel().clearSelection();
    }
}
