package cinema.DAO;
import cinema.BO.Salle;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SalleDAO extends DAO<Salle> {

    @Override
    public boolean create(Salle obj) {
        boolean res = false;
        try {
            String a = "INSERT INTO salle(numero, description, nb_places, id_cinema) values (?,?,?,?);";
            PreparedStatement stmt = this.connect.prepareStatement(a);
            stmt.setInt(1, obj.getNumSal());
            stmt.setString(2, obj.getDesc());
            stmt.setInt(3, obj.getPlaces());
            stmt.setInt(4, obj.getIdCine());

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0){
                res = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public boolean delete(Salle obj) {
        boolean res = false;
        String sql = "DELETE FROM salle WHERE id_salle = ?";
        try(PreparedStatement statement = this.connect.prepareStatement(sql)) {
            statement.setInt(1, obj.getIdSal());
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                res = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public boolean update(Salle obj) {
        boolean res = false;
        String sql = "UPDATE salle SET NumSal=?, Desc=? WHERE id_salle = ?";
        try(PreparedStatement ps = this.connect.prepareStatement(sql)) {
            ps.setInt(1, obj.getNumSal());
            ps.setString(2, obj.getDesc());
            ps.setInt(3, obj.getIdSal());
            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                res = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public Salle find(int idSal) {
        Salle laSalle = null;
        String sql = "SELECT * FROM salle WHERE id_salle = ?";
        try(PreparedStatement ps = this.connect.prepareStatement(sql)) {
            ps.setInt(1, idSal);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                laSalle = hydrate(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return laSalle;
    }

    public Salle findInCinema(int idCinema, int idSal) {
        Salle laSalle = null;
        String sql = "SELECT * FROM salle WHERE id_salle = ? and id_cinema = ?";
        try(PreparedStatement ps = this.connect.prepareStatement(sql)) {
            ps.setInt(1, idSal);
            ps.setInt(2, idCinema);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                laSalle = hydrate(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return laSalle;
    }

    @Override
    public List<Salle> findAll() {
        List<Salle> lesSalles = new ArrayList<>();
        Salle laSalle;
        String sql = "SELECT * FROM salle ORDER BY id_salle";
        try (PreparedStatement ps = this.connect.prepareStatement(sql)){
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                laSalle = hydrate(rs);
                lesSalles.add(laSalle);
            }
        } catch (SQLException e) {
            return null;
        }
        return lesSalles;
    }

    public List<Salle> findAllParCinema(int idCinema) {
        List<Salle> mesSalles = new ArrayList<>();
        Salle maSalle;
        String sql = "SELECT * FROM salle WHERE id_cinema = ?";
        try(PreparedStatement stmt = this.connect.prepareStatement(sql)) {
            stmt.setInt(1, idCinema);
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                maSalle = hydrate(rs);
                mesSalles.add(maSalle);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mesSalles;
    }

    private Salle hydrate(ResultSet rs) throws SQLException {
        return new Salle(
                rs.getInt("id_salle"),
                rs.getInt("numero"),
                rs.getString("description"),
                rs.getInt("nb_places"),
                rs.getInt("id_cinema")
        );
    }

}
