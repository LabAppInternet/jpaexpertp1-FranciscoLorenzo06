package cat.tecnocampus.fgcstations.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name ="Station")
@Table(name = "station")
public class Station {

    @Id
    private String nom;

    @OneToMany(
            mappedBy = "journey",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Journey> journeyOriginStation = new ArrayList<>();


    @OneToMany(
            mappedBy = "journey",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Journey> journeyDestinationStation = new ArrayList<>();

    private String longitud;
    private String latitud;

    public Station() {
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public boolean sameStation(Station station) {
        return this.getNom().equalsIgnoreCase(station.getNom());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Station station = (Station) o;

        return nom.equals(station.nom);
    }

    @Override
    public int hashCode() {
        return nom.hashCode();
    }

    @Override
    public String toString() {
        return "Station{" +
                ", nom='" + nom + '\'' +
                ", longitud='" + longitud + '\'' +
                ", latitud='" + latitud + '\'' +
                '}';
    }
}
