package cinema.BO;

import javafx.beans.property.SimpleStringProperty;

public class Salle {

    private int idSalle;
    private int numero;
    private SimpleStringProperty description = new SimpleStringProperty();
    private int nbPlaces;
    private Cinema LeCine;

    public Salle (int idSalle, int num, String desc, int nbPlaces) {
        this.idSalle = idSalle;
        this.numero = num;
        this.description.set(desc);
        this.nbPlaces = nbPlaces;
    }

    public Salle (int num, String desc, int nbPlaces) {
        this.numero = num;
        this.description.set(desc);
        this.nbPlaces = nbPlaces;
    }

    public void setIdSal(int idSalle) {
        this.idSalle = idSalle;
    }

    public void setNbPlaces(int nbPlaces) {
        this.nbPlaces = nbPlaces;
    }

    public void setNumSal(int numero) {
        this.numero = numero;
    }

    public int getIdSal() {
        return idSalle;
    }

    public int getPlaces() {
        return nbPlaces;
    }

    public int getNumSal() {
        return numero;
    }

    public void setDesc(String desc) {
        this.description.set(desc);
    }

    public String getDesc(){
        return description.getValue();
    }

    public Cinema getLeCine(){
        return LeCine;
    }

    public String toString() {
        return "Salle " + getNumSal() + " du Cinéma " + getLeCine().getDenomination() + " : \n" +
                "Description = " + getDesc() + "\n" +
                "Nombre de Places = " + getPlaces();
    }
}
