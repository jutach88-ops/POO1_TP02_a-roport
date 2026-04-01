package ca.csfoy.w20.tp2avion;

import ca.csfoy.w20.tp2avion.bagage.Bagage;
import ca.csfoy.w20.tp2avion.itineraire.Aeroport;
import ca.csfoy.w20.tp2avion.itineraire.Itineraire;
import ca.csfoy.w20.tp2avion.itineraire.Segment;

import java.util.ArrayList;
import java.util.Random;

public class Main {

    private final Avion avion;

    public Main() {


        PlanVol planVol = new PlanVol(Factory.itineraireLongQcMtlTorontoParisVancouver, 800, 0.18);

        this.avion = new Avion(
                planVol,
                37_000,
                27_000
        );

        this.avion.ajouterPassagers(100);
        this.avion.remplir(new Carburant(9 * 2400 / Carburant.MASSE_VOLUMIQUE_KEROSENE_15_C * 1.2));
        this.ajouterAnimaux(0, 4);
        this.ajouterBagageCabine(20, 30);
        this.ajouterBagageSoute(30, 120);
    }

    static void main() {
        Main main = new Main();
        main.afficherManifeste();
        main.debug();
    }

    private void ajouterAnimaux(int min, int max) {
        ArrayList<Bagage> bagages = this.genererBagages(min, max, 7, 10);
        for (Bagage bagage : bagages) {
            this.avion.ajouterAnimalEnSoute(bagage);
        }
    }

    private void ajouterBagageCabine(int min, int max) {
        ArrayList<Bagage> bagages = this.genererBagages(min, max, 15, Avion.VOLUME_MAXIMAL_EN_LITRES_EN_CABINE);
        for (Bagage bagage : bagages) {
            this.avion.ajouterBagageEnCabine(bagage);
        }
    }

    private void ajouterBagageSoute(int min, int max) {
        ArrayList<Bagage> bagages = this.genererBagages(min, max, 22, Avion.VOLUME_MAXIMAL_EN_LITRES_ENREGISTRE);
        for (Bagage bagage : bagages) {
            this.avion.ajouterBagageEnSoute(bagage);
        }
    }

    public void afficherManifeste() {
        System.out.println(AvionFormateur.detail(this.avion));
    }

    public void debug() {
        System.out.println("== DEBUG ==");
        System.out.println(this.avion);
    }

    private ArrayList<Bagage> genererBagages(int min, int max, int poidsMaximum, double volumeMaximum) {
        ArrayList<Bagage> bagages = new ArrayList<>();

        Random random = new Random();
        int count = random.nextInt(min, max);
        for (int i = 0; i < count; i++) {
            bagages.add(new Bagage(
                    Math.round(random.nextDouble(5, poidsMaximum)),
                    Math.round(random.nextDouble(4, volumeMaximum))));
        }

        return bagages;
    }
}
