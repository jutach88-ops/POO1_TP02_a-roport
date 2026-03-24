package ca.csfoy.w20.tp2avion;

import ca.csfoy.w20.tp2avion.bagage.Bagage;

import java.util.ArrayList;

public class Avion {

    public static final int POIDS_MOYEN_PASSAGER_EN_KG = 84;
    public static final int NB_MINIMUM_AJOUT_PASSAGERS = 1;

    public static final double VOLUME_MAXIMAL_EN_LITRES_ENREGISTRE = 100;
    public static final double VOLUME_MAXIMAL_EN_LITRES_EN_CABINE = 30;

    public static final int SECONDES_PAR_HEURE = 3600;
    public static final int DUREE_VOL_MAXIMUM_AVEC_ANIMAUX_EN_SECONDES = 10 * Avion.SECONDES_PAR_HEURE;

    private final PlanVol planVol;
    private final double capaciteChargementEnKg;
    private final double capaciteChargementSouteEnLitres;

    private final ArrayList<Bagage> bagagesEnSoute = new ArrayList<>();
    private final ArrayList<Bagage> animauxEnSoute = new ArrayList<>();
    private final ArrayList<Bagage> bagagesCabine = new ArrayList<>();

    private Carburant carburant;
    private int nbPassagers = 0;

    public Avion(PlanVol planVol, double capaciteChargementEnKg, double capaciteChargementSouteEnLitres) {
        this.planVol = planVol;
        this.carburant = new Carburant(0);
        this.capaciteChargementEnKg = capaciteChargementEnKg;
        this.capaciteChargementSouteEnLitres = capaciteChargementSouteEnLitres;
    }

    public void ajouterPassagers(int nombre) {
        if (nombre < Avion.NB_MINIMUM_AJOUT_PASSAGERS) {
            throw new IllegalArgumentException(
                    "Le nombre de passagers à ajouter doit être supérieur à " + Avion.NB_MINIMUM_AJOUT_PASSAGERS);
        }
        this.nbPassagers += nombre;
    }

    public void ajouterBagageEnSoute(Bagage bagage) {
        this.bagagesEnSoute.add(bagage);
    }

    public void ajouterBagageEnCabine(Bagage bagage) {
        this.bagagesCabine.add(bagage);
    }

    public void ajouterBagagesEnSoute(ArrayList<Bagage> bagages) {
        for (Bagage bagage : bagages) {
            this.ajouterBagageEnSoute(bagage);
        }
    }

    public void ajouterAnimalEnSoute(Bagage bagage) {
        this.animauxEnSoute.add(bagage);
    }

    public double remplir(Carburant carburant) {
        this.carburant = carburant;
        return this.capaciteChargementEnKg - this.carburant.calculerPoidsEnKg();
    }

    public double getPoidsPassagers() {
        return this.nbPassagers * Avion.POIDS_MOYEN_PASSAGER_EN_KG;
    }

    public double getPoidsBagagesEnSoute() {
        return this.calculerPoids(this.bagagesEnSoute)
                + this.calculerPoids(this.animauxEnSoute);
    }

    public double getPoidsBagagesEnCabine() {
        return this.calculerPoids(this.bagagesCabine);
    }

    private double getVolumeEnSoute() {
        return this.calculerVolume(this.bagagesEnSoute)
                + this.calculerVolume(this.animauxEnSoute);
    }

    private double calculerPoids(ArrayList<Bagage> bagages) {
        double total = 0;

        for (Bagage bagage : bagages) {
            total += bagage.getPoidsEnKg();
        }

        return total;
    }

    private double calculerVolume(ArrayList<Bagage> bagages) {
        double total = 0;

        for (Bagage bagage : bagages) {
            total += bagage.getVolumeEnLitres();
        }

        return total;
    }

    public boolean autoriseADecoller() {
        return this.isCargoVivantEnSecurite(!this.animauxEnSoute.isEmpty())
                && this.isCarburantSuffisant()
                && this.isVolumeBagagesSouteConforme()
                && this.isVolumeBagagesCabineConforme()
                && this.isLimitePoidsRespectee()
                && this.isLimiteVolumeRespectee();
    }

    public boolean isCargoVivantEnSecurite(boolean hasAnimaux) {
        return !hasAnimaux || this.planVol.getDureePrevue() <= Avion.DUREE_VOL_MAXIMUM_AVEC_ANIMAUX_EN_SECONDES;
    }

    private boolean isLimiteVolumeRespectee() {
        return this.getVolumeEnSoute() <= this.capaciteChargementSouteEnLitres;
    }

    private boolean isCarburantSuffisant() {
        double poidsCarburant = this.carburant.calculerPoidsEnKg();
        double autonomieEnSecondes = poidsCarburant / Carburant.CONSOMMATION_PAR_HEURE_EN_KG * Avion.SECONDES_PAR_HEURE;
        long dureePrevue = Math.round(
                this.planVol.getDureePrevue() * (1 + this.planVol.getReserveCarburantMinimale())
        );
        return dureePrevue <= autonomieEnSecondes;
    }

    private boolean isVolumeBagagesSouteConforme() {
        for (Bagage bagage : this.bagagesEnSoute) {
            if (bagage.getVolumeEnLitres() > Avion.VOLUME_MAXIMAL_EN_LITRES_ENREGISTRE) {
                return false;
            }
        }
        return true;
    }

    private boolean isVolumeBagagesCabineConforme() {
        for (Bagage bagage : this.bagagesCabine) {
            if (bagage.getVolumeEnLitres() > Avion.VOLUME_MAXIMAL_EN_LITRES_EN_CABINE) {
                return false;
            }
        }
        return true;
    }

    private boolean isLimitePoidsRespectee() {
        return this.calculerPoidsChargementTotal() <= this.capaciteChargementEnKg;
    }

    private double calculerPoidsChargementTotal() {
        return this.getPoidsPassagers()
                + this.getPoidsBagagesEnCabine()
                + this.getPoidsBagagesEnSoute()
                + this.carburant.calculerPoidsEnKg();
    }

    public double getCapaciteChargementEnKg() {
        return this.capaciteChargementEnKg;
    }

    public double getCapaciteChargementSouteEnLitres() {
        return this.capaciteChargementSouteEnLitres;
    }

    public double getPoidsCarburantEnKg() {
        return this.carburant.calculerPoidsEnKg();
    }

    public double getReserveCarburantRequise() {
        return this.planVol.getReserveCarburantMinimale();
    }

    public double getDureeVolPrevueEnHeures() {
        return this.planVol.getDureePrevue() / PlanVol.SECONDES_PAR_HEURE;
    }

    public double getVitesseVolPrevue() {
        return this.planVol.getVitesseCroisiereKmH();
    }

    public double getVolumeOccupeSoute() {
        return this.getVolumeEnSoute();
    }

    public double getPoidsChargementTotal() {
        return this.calculerPoidsChargementTotal();
    }

    @Override
    public String toString() {
        return "Avion{"
                + "autoriseADecoller=" + this.autoriseADecoller()
                + ", capaciteChargementEnKg=" + this.capaciteChargementEnKg
                + ", capaciteChargementSouteEnLitres=" + this.capaciteChargementSouteEnLitres
                + ", nbPassagers=" + this.nbPassagers
                + ", poidsChargementTotal=" + this.getPoidsChargementTotal()
                + ", volumeOccupeSoute=" + this.getVolumeOccupeSoute()
                + ", carburant=" + this.carburant
                + ", planValide=" + this.planVol.isValide()
                + ", carburantSuffisant=" + this.isCarburantSuffisant()
                + ", poidsRespete=" + this.isLimitePoidsRespectee()
                + ", volumeRespete=" + this.isLimiteVolumeRespectee()
                + ", bagagesSouteConformes=" + this.isVolumeBagagesSouteConforme()
                + ", bagagesCabineConformes=" + this.isVolumeBagagesCabineConforme()
                + ", cargoVivantEnSecurite=" + this.isCargoVivantEnSecurite(!this.animauxEnSoute.isEmpty())
                + "}";

    }
}
