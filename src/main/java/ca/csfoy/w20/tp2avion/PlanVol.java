package ca.csfoy.w20.tp2avion;

import ca.csfoy.w20.tp2avion.itineraire.Itineraire;

public class PlanVol {

    public static final int SECONDES_PAR_HEURE = 3600;
    public static final int DUREE_VOL_MAXIMUM_AVEC_ANIMAUX_EN_SECONDES = 10 * PlanVol.SECONDES_PAR_HEURE;

    private final int vitesseCroisiereKmH;
    private final double reserveCarburantMinimale;
    private final Itineraire itineraire;
    private boolean estCargoVivantPresent = false;

    public PlanVol(Itineraire itineraire, int vitesseCroisiereKmH, double reserveCarburantMinimale) {
        this.itineraire = itineraire;
        this.vitesseCroisiereKmH = vitesseCroisiereKmH;
        this.reserveCarburantMinimale = reserveCarburantMinimale;
    }

    public int getVitesseCroisiereKmH() {
        return this.vitesseCroisiereKmH;
    }

    public double getReserveCarburantMinimale() {
        return this.reserveCarburantMinimale;
    }

    public boolean isValide() {
        return true;
    }

    public double getDistanceEnKm() {
        return this.itineraire.calculerDistance();
    }


    public long getDureePrevue() {
        return Math.round(this.itineraire.calculerDistance() / this.vitesseCroisiereKmH * PlanVol.SECONDES_PAR_HEURE);
    }

    public boolean isCargoVivantEnSecurite() {
        return !this.estCargoVivantPresent || this.getDureePrevue() <= PlanVol.DUREE_VOL_MAXIMUM_AVEC_ANIMAUX_EN_SECONDES;
    }

    public void cargoVivantPresent() {
        this.estCargoVivantPresent = true;
    }

    // Tell dont ask
    @Override
    public String toString() {
        return "Durée prévue " + this.getDureePrevue() + " à une vitesse de croisière de " + this.vitesseCroisiereKmH + " Km / h\n"
                + "Une réserve de carburant supplémentaire de " + this.reserveCarburantMinimale * 100 + " % est requise pour le décollage\n"
                + this.itineraire;
    }
}
