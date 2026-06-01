package cinema.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.mindrot.jbcrypt.BCrypt;
import cinema.BO.Utilisateur;
import cinema.Session;


public class UtilisateurDAO extends DAO<Utilisateur> {

    private static final String salt = BCrypt.gensalt(6);
    private static final String dummyHash = "$2a$12$dummyvalue69.poof420/bad6gateway7.abcd3fghIjKLMN0PQR5";

    @Override
    public boolean create(Utilisateur obj) {
        boolean result = false;
        String hashPassword = BCrypt.hashpw(obj.getMdp(),salt);
        String sql = "INSERT INTO utilisateur(login, mdp) VALUES(?,?)";
        try(PreparedStatement ps = this.connect.prepareStatement(sql)) {
            ps.setString(1, obj.getLogin());
            ps.setString(2, hashPassword);
            int rowsInserted = ps.executeUpdate();
            if (rowsInserted > 0) {
                result = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean delete(Utilisateur obj) {
        boolean result = false;
        String sql = "DELETE FROM utilisateur WHERE id_utilisateur = ?";
        try(PreparedStatement ps = this.connect.prepareStatement(sql)){
            ps.setInt(1, obj.getIdUtilisateur());

            int rowsDeleted = ps.executeUpdate();
            if (rowsDeleted > 0) {
                result = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean update(Utilisateur obj) {
        boolean result = false;
        String sql = "UPDATE utilisateur SET login=?, mdp=? WHERE id_utilisateur = ?";
        try(PreparedStatement ps = this.connect.prepareStatement(sql)) {
            ps.setString(1, obj.getLogin());
            ps.setString(2, obj.getMdp());
            ps.setInt(3, obj.getIdUtilisateur());
            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                result = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    private Utilisateur hydrate(ResultSet resultSet) throws SQLException {
        return new Utilisateur(resultSet.getInt("id_utilisateur"),
                resultSet.getString("nom"),
                resultSet.getString("prenom"),
                resultSet.getString("login"),
                resultSet.getString("mdp"));
    }

    @Override
    public List<Utilisateur> findAll() {
        List<Utilisateur> mesUtilisateurs = new ArrayList<>();
        Utilisateur utilisateur;
        String sql = "SELECT * FROM utilisateur";
        try(Statement statement = this.connect.createStatement()) {
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                utilisateur = hydrate(rs);
                mesUtilisateurs.add(utilisateur);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mesUtilisateurs;
    }

    @Override
    public Utilisateur find(int idUtilisateur) {
        Utilisateur user;
        String sql = "SELECT * FROM utilisateur WHERE id_utilisateur = ?";
        try (PreparedStatement ps = this.connect.prepareStatement(sql)) {
            ps.setInt(1, idUtilisateur);
            ResultSet result = ps.executeQuery();
            if (result.next()) {
                user = hydrate(result);
            } else {
                user = null;
            }

        } catch (SQLException e) {
            return null;
        }
        return user;
    }

    public Utilisateur authenticate(String login, String password) {
        Utilisateur user = null;
        String sql = "SELECT * FROM utilisateur WHERE login =?";
        String passCheck = dummyHash;
        try (PreparedStatement ps = this.connect.prepareStatement(sql)){
            ps.setString(1, login);
            ResultSet result = ps.executeQuery();
            if (result.next()) {
                passCheck = result.getString("mdp");
            }
            if(BCrypt.checkpw(password, passCheck)){
                user = hydrate(result);
            }
        } catch (SQLException e) {
            return null;
        }
        return user;
    }

}
