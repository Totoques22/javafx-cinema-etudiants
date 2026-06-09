package cinema.controllers;

import javafx.fxml.Initializable;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import cinema.BO.Salle;
import cinema.BO.Utilisateur;
import cinema.DAO.SalleDAO;
import cinema.DAO.UtilisateurDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ListeSalleController  extends MenuController implements Initializable{
    @FXML
    private TableView<Salle> tvSalles;

    @FXML
    private TableColumn<Salle, String> tcNumero;

    @FXML
    private TableColumn<Salle, String> tcDesc;

    @FXML
    private TableColumn<Salle, Integer> tcIdCinema;

    @FXML
    private TableColumn<Salle, Void> tcModifier;

    @FXML
    private TableColumn<Salle, Void> tcSupprimer;

    @FXML
    private Button bRetour;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        UtilisateurDAO gerantDAO = new UtilisateurDAO();

        // Programmation fonctionnelle
        // Collecteur de flux :
        // https://www.ionos.fr/digitalguide/sites-internet/developpement-web/les-collectors-de-streams-en-java/
        // toMap :
        // https://www.geeksforgeeks.org/java/collectors-tomap-method-in-java-with-examples/
        //
        Map<Integer, Utilisateur> gerants = gerantDAO.findAll()
                .stream()
                .collect(Collectors.toMap(Utilisateur::getIdUtilisateur, u -> u));


        tcNumero.setCellValueFactory(new PropertyValueFactory<>("Numéro"));
        tcDesc.setCellValueFactory(new PropertyValueFactory<>("Description"));
        ObservableList<Salle> data = getSalleList();
        tvSalles.setItems(data);

        addButtonModifierToTable();
        addButtonSupprimerToTable();
    }

    private ObservableList<Salle> getSalleList() {

        SalleDAO var1 = new SalleDAO();
        List<Salle> var2 = var1.findAll();

        ObservableList<Salle> list = FXCollections.observableArrayList();
        if (var2 != null) {
            list.addAll(var2);
        }
        return list;
    }

    @FXML
    private void bRetourClick() {
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
            stage.setTitle("Accueil Gestion de salles");
            stage.setScene(new Scene(root));

            // Configurer la fenêtre en tant que modal
            stage.initModality(Modality.APPLICATION_MODAL);

            // Afficher la fenêtre et attendre qu'elle se ferme
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void addButtonModifierToTable() {
        tcModifier.setCellFactory(column -> new TableCell<>() {
            private final Button btn = new Button("Modifier");
            {
                btn.setOnAction(event -> {
                    Salle salle = getTableView().getItems().get(getIndex());
                    Stage stageP = (Stage) bRetour.getScene().getWindow();
                    stageP.close();

                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(
                                getClass().getResource("/cinema/views/page_modif_Salle.fxml"));
                        Parent root = fxmlLoader.load();

                        ModifierSalleController modifierSalleCtrl = fxmlLoader.getController();
                        modifierSalleCtrl.setAttributes(salle);
                        modifierSalleCtrl.setName(nameUti);

                        Stage stage = new Stage();
                        stage.setTitle("Modification Salle");
                        stage.setScene(new Scene(root));

                        stage.initModality(Modality.APPLICATION_MODAL);

                        stage.show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn);
            }
        });
    }

    private void addButtonSupprimerToTable() {
        tcSupprimer.setCellFactory(column -> new TableCell<>() {
            private final Button btn = new Button("Supprimer");

            {
                btn.setOnAction(event -> {
                    Salle salle = getTableView().getItems().get(getIndex());
                    tvSalles.getItems().remove(salle);
                    SalleDAO SalleDAO = new SalleDAO();
                    SalleDAO.delete(salle);
                });
                // btn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn);
            }
        });
    }

}
