package cinema.controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import cinema.BO.Cinema;
import cinema.BO.Franchise;
import cinema.DAO.CinemaDAO;
import cinema.DAO.FranchiseDAO;
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
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ModifierCinemaController extends MenuController implements Initializable {

    @FXML
    private TextField tfDenomination, tfAdresse, tfVille;

    @FXML
    private ListView<Franchise> lvFranchise;

    @FXML
    private Button bRetour, bEnregistrer;

    private int idCinema;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lvFranchise.setItems(getFranchiseList());
    }

    private ObservableList<Franchise> getFranchiseList() {
        FranchiseDAO franDao = new FranchiseDAO();
        List<Franchise> franchises = franDao.findAll();
        return FXCollections.observableArrayList(franchises);
    }

    public void setAttributes(Cinema cinema) {
        tfDenomination.setText(cinema.getDenomination());
        tfAdresse.setText(cinema.getAdresse());
        tfVille.setText(cinema.getVille());
        this.idCinema = cinema.getIdCinema();

        for (Franchise fran : lvFranchise.getItems()) {
            if (fran.getIdFranchise() == cinema.getIdFranchise()) {
                lvFranchise.getSelectionModel().select(fran);
                break;
            }
        }
    }

    @FXML
    private void bRetourClick(ActionEvent event) {
        Stage stageP = (Stage) bRetour.getScene().getWindow();
        stageP.close();
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(
                    getClass().getResource("/cinema/views/page_liste_cinema.fxml"));
            Parent root = fxmlLoader.load();

            ListeCinemaController listeCinemaController = fxmlLoader.getController();
            listeCinemaController.setName(nameUti);

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
    private void bEnregistrerClick(ActionEvent event) {
        Franchise selected = lvFranchise.getSelectionModel().getSelectedItem();

        Cinema cinema = new Cinema(
                idCinema,
                tfDenomination.getText(),
                tfAdresse.getText(),
                tfVille.getText(),
                selected.getIdFranchise()
        );

        CinemaDAO dao = new CinemaDAO();
        dao.update(cinema);

        Stage stageP = (Stage) bEnregistrer.getScene().getWindow();
        stageP.close();
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(
                    getClass().getResource("/cinema/views/page_liste_cinema.fxml"));
            Parent root = fxmlLoader.load();

            ListeCinemaController listeCinemaController = fxmlLoader.getController();
            listeCinemaController.setName(nameUti);

            Stage stage = new Stage();
            stage.setTitle("Liste cinemas");
            stage.setScene(new Scene(root));

            stage.initModality(Modality.APPLICATION_MODAL);

            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
