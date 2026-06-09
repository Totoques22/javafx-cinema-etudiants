package cinema.BO;

public class Salarie {

    private int idSalarie;
    private String nomSalarie;
    private String prenomSalarie;
    private int idCinema;

    public Salarie() {
    }

    public Salarie(String nomSal, String prenomSal, int idCinema) {
        this.nomSalarie = nomSal;
        this.prenomSalarie = prenomSal;
        this.idCinema = idCinema;
    }

    public int getIdSalarie() {
        return idSalarie;
    }

    public void setIdSalarie(int idSalarie) {
        this.idSalarie = idSalarie;
    }

    public String getNomSal() {
        return nomSalarie;
    }

    public void setNomSal(String nomSal) {
        this.nomSalarie = nomSal;
    }

    public String getPrenomSal() {
        return prenomSalarie;
    }

    public void setPrenomSal(String prenomSal) {
        this.prenomSalarie = prenomSal;
    }

    public int getIdCinema() {
        return idCinema;
    }

    public void setIdCinema(int idCinema) {
        this.idCinema = idCinema;
    }

    @Override
    public String toString() {
        return nomSalarie + " " + prenomSalarie;
    }

    public String toLogString() {
        return "Salarie{" +
                "idSalarie=" + idSalarie +
                ", nomSalarie='" + nomSalarie +
                "', prenomSalarie='" + prenomSalarie +
                "', idCinema="+ idCinema +
                "}";
    }
}
