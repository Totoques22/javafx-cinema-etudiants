package cinema.DAO;

import cinema.BO.Salarie;
import cinema.Session;
import cinema.service.LogService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SalarieDAO extends DAO<Salarie> {

    @Override
    public boolean create(Salarie obj) {
        boolean res = false;
        Salarie ancienSal = this.find(obj.getIdSalarie());
        try {
            String a = "INSERT INTO salarie(nom, prenom, id_cinema) values (?,?,?);";
            PreparedStatement stmt = this.connect.prepareStatement(a);
            stmt.setString(1, obj.getNomSal());
            stmt.setString(2, obj.getPrenomSal());
            stmt.setInt(3, obj.getIdCinema());

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0){
                res = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        LogService.log(
                Session.getUtilisateur().getIdUtilisateur(),
                "CREATE",
                "Salarie",
                obj.getIdSalarie(),
                ancienSal.toLogString(),
                obj.toLogString()
        );
        return res;
    }

    @Override
    public boolean delete(Salarie obj) {
        boolean res = false;
        Salarie ancienSal = this.find(obj.getIdSalarie());
        String sql = "DELETE FROM salarie WHERE id_salarie = ?";
        try(PreparedStatement statement = this.connect.prepareStatement(sql)) {
            statement.setInt(1, obj.getIdSalarie());
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                res = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        LogService.log(
                Session.getUtilisateur().getIdUtilisateur(),
                "DELETE",
                "Salarie",
                obj.getIdSalarie(),
                ancienSal.toLogString(),
                obj.toLogString()
        );
        return res;
    }

    @Override
    public boolean update(Salarie obj) {
        boolean res = false;
        Salarie ancienSalarie = this.find(obj.getIdSalarie());
        String sql = "UPDATE salarie SET nom_salarie=?, Desc=? WHERE id_salle = ?";
        try(PreparedStatement ps = this.connect.prepareStatement(sql)) {
            ps.setString(1, obj.getNomSal());
            ps.setString(2, obj.getPrenomSal());
            ps.setInt(3, obj.getIdSalarie());
            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                res = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        LogService.log(
                Session.getUtilisateur().getIdUtilisateur(),
                "UPDATE",
                "Salarie",
                obj.getIdSalarie(),
                ancienSalarie.toLogString(),
                obj.toLogString()
        );
        return res;
    }

    @Override
    public Salarie find(int idSal) {
        Salarie leSalarie = null;
        String sql = "SELECT * FROM salarie WHERE id_salarie = ?";
        try(PreparedStatement ps = this.connect.prepareStatement(sql)) {
            ps.setInt(1, idSal);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                leSalarie = hydrate(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return leSalarie;
    }

    public Salarie findInCinema(int idCinema, int idSal) {
        Salarie leSalarie = null;
        String sql = "SELECT * FROM salarie WHERE id_salarie = ? and id_cinema = ?";
        try(PreparedStatement ps = this.connect.prepareStatement(sql)) {
            ps.setInt(1, idSal);
            ps.setInt(2, idCinema);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                leSalarie= hydrate(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return leSalarie;
    }

    @Override
    public List<Salarie> findAll() {
        List<Salarie> lesSalaries = new ArrayList<>();
        Salarie leSalarie;
        String sql = "SELECT * FROM salarie ORDER BY id_salarie";
        try (PreparedStatement ps = this.connect.prepareStatement(sql)){
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                leSalarie = hydrate(rs);
                lesSalaries.add(leSalarie);
            }
        } catch (SQLException e) {
            return null;
        }
        return lesSalaries;
    }

    public List<Salarie> findAllParCinema(int idCinema) {
        List<Salarie> mesSalaries = new ArrayList<>();
        Salarie monSalarie;
        String sql = "SELECT * FROM salarie WHERE id_cinema = ?";
        try(PreparedStatement stmt = this.connect.prepareStatement(sql)) {
            stmt.setInt(1, idCinema);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                monSalarie = hydrate(rs);
                mesSalaries.add(monSalarie);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mesSalaries;
    }

    public int findSalarieCount(int idCinema) {
        String sql = "SELECT COUNT(id_salarie) as nb FROM salarie WHERE id_cinema = ?";
        int res = 0;
        try(PreparedStatement stmt = this.connect.prepareStatement(sql)) {
            stmt.setInt(1, idCinema);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                res = rs.getInt(1);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return res;
    }
    private Salarie hydrate(ResultSet rs) throws SQLException {
        return new Salarie(
                rs.getInt("id_salarie"),
                rs.getString("nom"),
                rs.getString("prenom"),
                rs.getInt("id_cinema")
        );
    }
}
