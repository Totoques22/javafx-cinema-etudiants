package cinema.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import cinema.BO.Utilisateur;
import cinema.DAO.UtilisateurDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ConnexionController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private TextField tfLogin;
    @FXML
    private PasswordField tfMDP;
    @FXML
    private Button bConnexion;

    @FXML
    public void bConnexionClick(ActionEvent event) {
        String login = tfLogin.getText();
        String mdp = tfMDP.getText(); // il faut le hash pour le bug 18

        /*note thomas : String salt = "SalageBasique"; // peut etre ameliorer avec un hash unique a chaque utilisatuer
        String mdpHashe = hash(mdp, salt);
        authentificate dois etre changé pour utiliser le mot de passe hasher
         voir les extensions qui hache tout seul comme BCrypt
         */

        UtilisateurDAO userDAO = new UtilisateurDAO();
        try {
            Utilisateur user = userDAO.authenticate(login, mdp);
            showAccueil(user);
        } catch (Exception e) {
            e.printStackTrace();
            showError();
        }
    }

    // modifier pour renvoyer un utilisateur au controller de l'accuiel plutot qu'un string
    private void showAccueil(Utilisateur user) {
        Stage stageP = (Stage) bConnexion.getScene().getWindow();
        // on ferme l'écran
        stageP.close();
        try {

            // Charger le fichier FXML pour la pop-up
            FXMLLoader fxmlLoader = new FXMLLoader(
                    getClass().getResource("/cinema/views/page_accueil.fxml"));
            Parent root = fxmlLoader.load();

            // Obtenir le contrôleur de la nouvelle fenetre
            AccueilController accueilController = fxmlLoader.getController();
            accueilController.setUtilisateur(user);
            accueilController.setBienvenue();

            // Créer une nouvelle fenêtre (Stage)
            Stage stage = new Stage();
            stage.setTitle("Accueil Gestion de franchises");
            stage.setScene(new Scene(root));
            stage.getIcons().add(new Image("/cinema/images/cinema_32x32.png"));
            // Configurer la fenêtre en tant que modal
            stage.initModality(Modality.APPLICATION_MODAL);

            // Afficher la fenêtre et attendre qu'elle se ferme
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void showError() {

        try {
            // Charger le fichier FXML pour la pop-up
            FXMLLoader fxmlLoader = new FXMLLoader(
                    getClass().getResource("/cinema/views/ErreurConnexion.fxml"));
            Parent root = fxmlLoader.load();

            // Obtenir le contrôleur de la pop-up
            ErrorController errorController = fxmlLoader.getController();

            // Passer la variable au contrôleur de la pop-up
            // errorController.setMajLabel(Integer.toString(compteur));

            // Créer une nouvelle fenêtre (Stage)
            Stage stage = new Stage();
            stage.setTitle("Error Window");
            stage.setScene(new Scene(root));

            // Configurer la fenêtre en tant que modal
            stage.initModality(Modality.APPLICATION_MODAL);

            // Afficher la fenêtre et attendre qu'elle se ferme
            stage.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
