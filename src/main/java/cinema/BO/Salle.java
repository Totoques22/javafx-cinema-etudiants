package cinema.BO;

import javafx.beans.property.SimpleStringProperty;

public class Salle {

    private int idSalle;
    private int numeroSalle;
    private SimpleStringProperty description = new SimpleStringProperty();
    private int nbPlaces;
    private int idCinema;

    public Salle (int idSalle, int numSal, String desc, int nbPlaces, int idCine) {
        this.idSalle = idSalle;
        this.idCinema = idCine;
        this.numeroSalle = numSal;
        this.description.set(desc);
        this.nbPlaces = nbPlaces;
    }

    public Salle (int idCine, int numSal, String desc, int nbPlaces) {
        this.idCinema = idCine;
        this.numeroSalle = numSal;
        this.description.set(desc);
        this.nbPlaces = nbPlaces;
    }

    public Salle (int idCine, String desc,  int nbPlaces) {
        this.idCinema = idCine;
        this.description.set(desc);
        this.nbPlaces = nbPlaces;
    }

    public void setIdSal(int idSalle) {
        this.idSalle = idSalle;
    }

    public void setNbPlaces(int nbPlaces) {
        this.nbPlaces = nbPlaces;
    }

    public void setNumSal(int numSal) {
        this.numeroSalle = numSal;
    }

    public int getIdSal() {
        return idSalle;
    }

    public int getPlaces() {
        return nbPlaces;
    }

    public int getNumSal() {
        return numeroSalle;
    }

    public void setDesc(String desc) {
        this.description.set(desc);
    }

    public String getDesc(){
        return description.getValue();
    }

    public int getIdCine(){
        return idCinema;
    }

    public void setIdCinema(int idCine) {
        this.idCinema = idCine;
    }

    public String toString() {
        return "Salle " + getNumSal() + " du Cinéma ID [" + getIdCine() + "] : \n" +
                "Description = " + getDesc() + "\n" +
                "Nombre de Places = " + getPlaces();
    }


}
