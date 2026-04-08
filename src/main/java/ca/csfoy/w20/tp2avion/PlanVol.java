package ca.csfoy.w20.tp2avion;

import ca.csfoy.w20.tp2avion.itineraire.Itineraire;

import static java.lang.Math.round;

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
        return this.itineraire.estItineraireValide();
    }

    public double getDistanceEnKm() {
        return this.itineraire.getDistance();
    }

    public long getDureePrevue() {
        return Math.round(this.itineraire.getDistance() / this.vitesseCroisiereKmH * PlanVol.SECONDES_PAR_HEURE);
    }

    public boolean isCargoVivantEnSecurite() {
        return !this.estCargoVivantPresent || this.getDureePrevue() <= PlanVol.DUREE_VOL_MAXIMUM_AVEC_ANIMAUX_EN_SECONDES;
    }

    public void cargoVivantPresent() {
        this.estCargoVivantPresent = true;
    }

    /* R3 - Le présent bris concerte un TDA car l'Avion demande au carburant si le carburant est suffisant. L'expert est carburant,
     *       c'est lui qui sait en fonction de ces attributs et caractéristiques de classe s'il est suffisant pour le vol. Cependant,
     *       faire attention au bris potentiel de Déméter, car carburant et PlanVol ne sont pas amis. S'assurer de passer les données
     *       brutes de PlanVol en paramètres. */
    /* R4 - Régler le SRP présent : Ici au sujet de Carburant, sa responsabilité unique est de donner des informations sur lui-même (il peut dire par rapport
             à une durée donnée s'il couvre la durée, mais non par rapport à un vol directement). C'est la responsabilité de planDeVol et
     *       non de dire s'il est suffisant pour un vol. C'est plan de vol qui a la responsabilité de dire si en fonction des informations
     *       de vol le vol est prêt et sécuritaire. */
    public boolean isVolumeCarburantSecuritaire(Carburant carburant) {
        long dureePrevueAvecReserve = round(
                this.getDureePrevue() * (1 + this.reserveCarburantMinimale)
        );
        return carburant.hasAutonomieSecondes(dureePrevueAvecReserve);
    }

    // Tell dont ask
    @Override
    public String toString() {
        return "Durée prévue " + this.getDureePrevue() + " à une vitesse de croisière de " + this.vitesseCroisiereKmH + " Km / h\n"
                + "Une réserve de carburant supplémentaire de " + this.reserveCarburantMinimale * 100 + " % est requise pour le décollage\n"
                + this.itineraire;
    }
}
