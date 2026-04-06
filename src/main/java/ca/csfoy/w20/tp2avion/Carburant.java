package ca.csfoy.w20.tp2avion;

import static java.lang.Math.round;

public class Carburant {

    public static final double SEUIL_VOLUME_EN_LITRES = 0.0;
    public static final double MASSE_VOLUMIQUE_KEROSENE_15_C = 0.804;
    public static final double CONSOMMATION_PAR_HEURE_EN_KG = 2400;
    public static final int SECONDES_PAR_HEURE = 3600;

    private final double volumeEnLitre;

    public Carburant(double volumeEnLitre) {
        this.validerVolume(volumeEnLitre);

        this.volumeEnLitre = volumeEnLitre;
    }

    private void validerVolume(double volumeEnLitre) {
        if (volumeEnLitre < Carburant.SEUIL_VOLUME_EN_LITRES) {
            throw new IllegalArgumentException(
                    "Le volume de litres (" + volumeEnLitre + ") doit être supérieur ou égal à " + Carburant.SEUIL_VOLUME_EN_LITRES);
        }
    }

    public double calculerPoidsEnKg() {
        return Carburant.MASSE_VOLUMIQUE_KEROSENE_15_C * this.volumeEnLitre;
    }

    @Override
    public String toString() {
        return this.volumeEnLitre + " L (" + (round(this.calculerPoidsEnKg() * 100) / 100.0) + " kg)";
    }

    public boolean hasAutonomieSecondes(long dureePrevuPlanVol) {
        double autonomieEnSecondes = this.calculerPoidsEnKg() / Carburant.CONSOMMATION_PAR_HEURE_EN_KG * Carburant.SECONDES_PAR_HEURE;
        return dureePrevuPlanVol <= autonomieEnSecondes;
    }
}
