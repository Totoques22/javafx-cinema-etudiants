package cinema.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import cinema.BO.Utilisateur;
import cinema.Session;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class AccueilController extends MenuController implements Initializable {

    @FXML
    private Label bienvenue;

    private Utilisateur utilisateur;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Utilisateur user = Session.getUtilisateur();
        System.out.println(user.getLogin());
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public void setBienvenue() {
        bienvenue.setText("BONJOUR " + utilisateur.getNom() + " " + utilisateur.getPrenom());
    }

    @FXML
    private MenuController menuController;

    public MenuController getMenuController() {
        return menuController;
    }

}
