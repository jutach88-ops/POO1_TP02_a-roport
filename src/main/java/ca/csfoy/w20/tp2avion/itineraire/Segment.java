package ca.csfoy.w20.tp2avion.itineraire;

import java.util.Objects;

public class Segment {

    private final Aeroport aeroportDepart;
    private final Aeroport aeroportArrivee;
    //    private final float distanceEnKm;

    public Segment(Aeroport aeroportDepart, Aeroport aeroportArrivee) {
        this.validationCodeAeroport(aeroportDepart);
        this.validationCodeAeroport(aeroportArrivee);
        this.validationDepartArrivee(aeroportDepart, aeroportArrivee);

        this.aeroportDepart = aeroportDepart;
        this.aeroportArrivee = aeroportArrivee;
    }

    private void validationCodeAeroport(Aeroport codeAeroport) {
        if (codeAeroport == null) {
            throw new IllegalArgumentException("Le code d'aéroport entré est invalide.");
        }
    }

    private void validationDepartArrivee(Aeroport aeroportDepart, Aeroport aeroportArrivee) {
        if (aeroportDepart == aeroportArrivee) {
            throw new IllegalArgumentException("Un segment ne peut pas avoir le même départ que la destination");
        }
    }

    public Aeroport getDepart() {
        return this.aeroportDepart;
    }

    public Aeroport getArrive() {
        return this.aeroportArrivee;
    }

    public double calculerDistanceAeroport() {
        return  this.aeroportDepart.getDistance(this.aeroportArrivee);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Segment segment = (Segment) obj;
        return this.aeroportDepart.equals(segment.aeroportDepart) && this.aeroportArrivee.equals(
                segment.aeroportArrivee);
    }

    @Override
    public int hashCode() {
        return Objects.hash(aeroportDepart, aeroportArrivee);
    }

    //    @Override
    //    public String toString() {
    //        return this.nom + "[" + this.code + "]" + this.coordonnee;
    //    }
}
