package cinema.service;

import cinema.DAO.DBManager;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Connection;

public class LogService {

    public static void log(
            int idUtilisateur,
            String action,
            String entite,
            int idEntite,
            String ancienneValeur,
            String nouvelleValeur) {

        Connection connect = DBManager.getInstance();
        try {

            String sql = """
                INSERT INTO log
                (
                    id_utilisateur,
                    type_action,
                    entite,
                    id_entite,
                    ancienne_valeur,
                    nouvelle_valeur
                )
                VALUES (?, ?, ?, ?, ?, ?)
            """;

            PreparedStatement ps = connect.prepareStatement(sql);

            ps.setInt(1, idUtilisateur);
            ps.setString(2, action);
            ps.setString(3, entite);
            ps.setInt(4, idEntite);
            ps.setString(5, ancienneValeur);
            ps.setString(6, nouvelleValeur);

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}