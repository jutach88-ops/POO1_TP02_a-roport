package ca.csfoy.w20.tp2avion;

import ca.csfoy.w20.tp2avion.bagage.Bagage;
import ca.csfoy.w20.tp2avion.itineraire.Itineraire;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static java.lang.Math.round;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AvionTest {

    // Caractéristiques d'avion
    private static final double CAPACITE_POIDS_KG = 50_000.0;
    private static final double CAPACITE_SOUTE_LITRES = 10_000.0;

    // Caractéristique de plan de vol
    private static final int VITESSE_CROISIERE = 900;
    private static final double RESERVE_0_POURCENT = 0.0;

    // Caractéristique d'itinéraire
    private static final long DUREE_QUEBEC_MONTREAL = TestFactory.DUREE_SECONDE_PREVU_QC_MTL;

    // Caractéristique de Carburant
    public static final double VOLUME_CARBURANT_EN_LITRE = 500.0;
    private static final double VOLUME_CARBURANT_SUFFISANT = AvionTest.volumeCarburant(AvionTest.DUREE_QUEBEC_MONTREAL);

    private Avion avion;
    private PlanVol planVolCourt;
    private PlanVol planVolLong;

    private static double volumeCarburant(long dureeVolLong) {
        return Math.ceil(
                dureeVolLong
                        * Carburant.CONSOMMATION_PAR_HEURE_EN_KG
                        / Carburant.MASSE_VOLUMIQUE_KEROSENE_15_C
                        / Carburant.SECONDES_PAR_HEURE);
    }

    @BeforeEach
    public void setUp() {
        this.planVolCourt = new PlanVol(
                TestFactory.ITINERAIRE_COURT_QC_MTL, AvionTest.VITESSE_CROISIERE,
                AvionTest.RESERVE_0_POURCENT);
        this.planVolLong = new PlanVol(
                TestFactory.ITINERAIRE_LONG_QC_MTL_TORONTO_PARIS_VANCOUVER, AvionTest.VITESSE_CROISIERE,
                AvionTest.RESERVE_0_POURCENT);
        this.avion = new Avion(planVolCourt, AvionTest.CAPACITE_POIDS_KG, AvionTest.CAPACITE_SOUTE_LITRES);
        this.avion.remplir(new Carburant(AvionTest.VOLUME_CARBURANT_SUFFISANT));
    }

    @Test
    public void etantDonneUnAvionLorsAjoutDePassagersValidesAlorsLesPassagersSontAjoutes() {
        this.avion.ajouterPassagers(10);
        this.avion.ajouterPassagers(5);

        long resultat = round(this.avion.getPoidsPassagers() / Avion.POIDS_MOYEN_PASSAGER_EN_KG);

        assertEquals(15, resultat);
    }

    @Test
    public void etantDonneUnAvionLorsAjoutDeZeroPassagerAlorsLanceUneException() {
        assertThrows(IllegalArgumentException.class, () -> this.avion.ajouterPassagers(0));
    }

    @Test
    public void etantDonneUnAvionLorsAjoutDePassagersNegatifAlorsLanceUneException() {
        assertThrows(IllegalArgumentException.class, () -> this.avion.ajouterPassagers(-5));
    }

    @Test
    public void etantDonneUnBagageConformeAlorsAjouterBagageEnSouteReussit() {
        double poidsAvant = this.avion.getPoidsChargementTotal();

        this.avion.ajouterBagageEnSoute(new Bagage(10.0, Avion.VOLUME_MAXIMAL_EN_LITRES_ENREGISTRE));
        double resultat = this.avion.getPoidsChargementTotal();

        assertEquals(poidsAvant + 10, resultat);
    }

    @Test
    public void etantDonneUnBagageConformeAlorsAjouterBagageEnCabineReussit() {
        double poidsAvant = this.avion.getPoidsChargementTotal();

        this.avion.ajouterBagageEnCabine(new Bagage(5.0, Avion.VOLUME_MAXIMAL_EN_LITRES_EN_CABINE));
        double resultat = this.avion.getPoidsChargementTotal();

        assertEquals(poidsAvant + 5, resultat);
    }

    @Test
    public void etantDonneUnAvionLorsAjoutDUneListeDeBagagesAlorsIlsSontTousAjoutes() {
        double poidsAvant = this.avion.getPoidsChargementTotal();
        ArrayList<Bagage> bagages = new ArrayList<>();
        bagages.add(new Bagage(10.0, 50.0));
        bagages.add(new Bagage(15.0, 60.0));

        this.avion.ajouterBagagesEnSoute(bagages);
        double resultat = this.avion.getPoidsChargementTotal();

        assertEquals(poidsAvant + 10 + 15, resultat);
    }

    @Test
    public void etantDonneUnAvionAlorsRemplirMetsAJourLeCarburant() {
        this.avion.remplir(new Carburant(AvionTest.VOLUME_CARBURANT_EN_LITRE));
        Carburant carburant = new Carburant(AvionTest.VOLUME_CARBURANT_EN_LITRE);

        assertEquals(carburant.calculerPoidsEnKg(), this.avion.getPoidsCarburantEnKg());
    }

    @Test
    public void etantDonneUnAvionAlorsRemplirRetourneLePoidsUtileRestant() {
        this.avion.remplir(new Carburant(AvionTest.VOLUME_CARBURANT_EN_LITRE));

        double poidsUtileRestant = this.avion.getCapaciteChargementEnKgRestant();

        assertEquals(
                AvionTest.CAPACITE_POIDS_KG - new Carburant(AvionTest.VOLUME_CARBURANT_EN_LITRE).calculerPoidsEnKg(),
                poidsUtileRestant);
    }

    @Test
    public void etantDonneAvionLorsqueDepassantLaLimiteDePoidsTotaleAlorsNonAutoriseADecoller() {
        int nbPassagersTropLourd =
                (int) Math.ceil(AvionTest.CAPACITE_POIDS_KG / Avion.POIDS_MOYEN_PASSAGER_EN_KG) + 1;

        this.avion.ajouterPassagers(nbPassagersTropLourd);

        assertFalse(this.avion.autoriseADecoller());
    }

    @Test
    public void etantDonneUnAvionAvecBagagesDepassantLaLimiteDePoidsAlorsNonAutoriseADecoller() {
        this.avion.ajouterBagageEnSoute(new Bagage(AvionTest.CAPACITE_POIDS_KG + 1, 1.0));

        assertFalse(this.avion.autoriseADecoller());
    }

    @Test
    public void etantDonneUnAvionDepassantLaCapaciteTotaleEnSouteAlorsNonAutoriseADecoller() {
        this.avion.ajouterBagageEnSoute(new Bagage(1.0, AvionTest.CAPACITE_SOUTE_LITRES + 1));

        assertFalse(this.avion.autoriseADecoller());
    }

    @Test
    public void etantDonneUnAvionAvecAnimauxEtVolCourtAlorsAutoriseADecoller() {
        this.avion.ajouterAnimalEnSoute(new Bagage(5.0, 50.0));

        assertTrue(this.avion.autoriseADecoller());
    }

    @Test
    public void etantDonneUnAvionAvecAnimauxEtVolDepassantDixHeuresAlorsNonAutoriseADecoller() {
        Avion avionVolLong = new Avion(this.planVolLong, AvionTest.CAPACITE_POIDS_KG, AvionTest.CAPACITE_SOUTE_LITRES);
        long dureeVolLong = this.planVolLong.getDureePrevue();
        avionVolLong.remplir(new Carburant(AvionTest.volumeCarburant(dureeVolLong)));
        avionVolLong.ajouterAnimalEnSoute(new Bagage(5.0, 50.0));

        boolean resultat = avionVolLong.autoriseADecoller();

        assertFalse(resultat);
    }

    @Test
    public void etantDonneUnAvionSansAnimauxEtVolLongAlorsLaViabiliteAnimauxNestPasUnObstacle() {
        Avion avionVolLong = new Avion(this.planVolLong, AvionTest.CAPACITE_POIDS_KG, AvionTest.CAPACITE_SOUTE_LITRES);
        long dureeVolLong = this.planVolLong.getDureePrevue();
        avionVolLong.remplir(new Carburant(AvionTest.volumeCarburant(dureeVolLong)));

        assertTrue(avionVolLong.autoriseADecoller());
    }

    @Test
    public void etantDonneUnAvionAlorsToStringContientLesCapacites() {
        String resultat = this.avion.toString();

        assertTrue(resultat.contains(String.valueOf(AvionTest.CAPACITE_POIDS_KG)));
        assertTrue(resultat.contains(String.valueOf(AvionTest.CAPACITE_SOUTE_LITRES)));
    }

    @Test
    public void etantDonneUnAvionAlorsToStringContientLeDetailDuChargement() {
        double poidsAvant = this.avion.getPoidsChargementTotal();
        this.avion.ajouterPassagers(3);
        this.avion.ajouterBagageEnCabine(new Bagage(5.0, 10.0));
        this.avion.ajouterBagageEnSoute(new Bagage(8.0, 20.0));

        double resultat = this.avion.getPoidsChargementTotal();

        double poidsApres = poidsAvant + (3 * Avion.POIDS_MOYEN_PASSAGER_EN_KG) + 13;
        assertEquals(poidsApres, resultat);
    }

    @Test
    public void etantDonneAvionLorsqueBagageCabineDepasseLimiteDeVolumeIndividuelleAlorsNonAutoriseADecoller() {
        this.avion.ajouterBagageEnCabine(new Bagage(1.0, Avion.VOLUME_MAXIMAL_EN_LITRES_EN_CABINE + 1));

        assertFalse(this.avion.autoriseADecoller());
    }

    @Test
    public void etantDonneAvionLorsqueBagageCabineExactementLimiteVolumeIndividuelleAlorsAutoriseADecoller() {
        this.avion.ajouterBagageEnCabine(new Bagage(1.0, Avion.VOLUME_MAXIMAL_EN_LITRES_EN_CABINE));

        assertTrue(this.avion.autoriseADecoller());
    }

    @Test
    public void etantDonneAvionLorsqueBagageSouteDepasseLimiteVolumeIndividuelleAlorsNonAutoriseADecoller() {
        this.avion.ajouterBagageEnSoute(new Bagage(1.0, Avion.VOLUME_MAXIMAL_EN_LITRES_ENREGISTRE + 1));

        assertFalse(this.avion.autoriseADecoller());
    }

    @Test
    public void etantDonneAvionLorsqueBagageSouteExactementLimiteVolumeIndividuelleAlorsAutoriseADecoller() {
        this.avion.ajouterBagageEnSoute(new Bagage(1.0, Avion.VOLUME_MAXIMAL_EN_LITRES_ENREGISTRE));

        assertTrue(this.avion.autoriseADecoller());
    }

    @Test
    public void etantDonneAvionLorsqueItineraireInvalideAlorsNonAutoriseeADecoller() {
        Itineraire itineraireInvalide =
                TestFactory.constructeurItineraire(TestFactory.QUEBEC_MONTREAL, TestFactory.QUEBEC_TORONTO);
        PlanVol planDeVolInvalide = new PlanVol(itineraireInvalide, TestFactory.VITESSE_DE_CROISIERE,
                TestFactory.RESERVE_DE_CARBURANT_MINIMAL);
        Avion avion = new Avion(planDeVolInvalide, AvionTest.CAPACITE_POIDS_KG, AvionTest.CAPACITE_SOUTE_LITRES);
        Carburant carburant = new Carburant(AvionTest.VOLUME_CARBURANT_EN_LITRE);

        avion.remplir(carburant);

        assertFalse(avion.autoriseADecoller());
    }

    @Test
    public void etantDonneAvionLorsqueItineraireValideAlorsAutoriseeADecoller() {
        Itineraire itineraireInvalide =
                TestFactory.constructeurItineraire(TestFactory.QUEBEC_MONTREAL, TestFactory.MONTREAL_TORONTO, TestFactory.TORONTO_PARIS, TestFactory.PARIS_VANCOUVER);
        PlanVol planDeVolInvalide = new PlanVol(itineraireInvalide, TestFactory.VITESSE_DE_CROISIERE,
                TestFactory.RESERVE_DE_CARBURANT_MINIMAL);
        Avion avion = new Avion(planDeVolInvalide, AvionTest.CAPACITE_POIDS_KG, AvionTest.CAPACITE_SOUTE_LITRES);
        Carburant carburant = new Carburant(TestFactory.LITRE_CARBURANT_QC_MTL_TOR_PA_VAN);

        avion.remplir(carburant);

        assertFalse(avion.autoriseADecoller());
    }

    @Test
    public void etantDonneAvionLorsqueCarburantSuffisantSansReserveAlorsAvionNonAutoriseeADecoller() {
        Avion avion = new Avion(TestFactory.PLAN_VOL_QC_MTL_AVEC_RESERVE, AvionTest.CAPACITE_POIDS_KG,
                AvionTest.CAPACITE_SOUTE_LITRES);
        Carburant carburantSuffisant = new Carburant(TestFactory.LITRES_CARBURANT_NECESSAIRE_QC_MTL_NON_AJUSTE);

        avion.remplir(carburantSuffisant);

        assertFalse(avion.autoriseADecoller());
    }

    @Test
    public void etantDonneAvionLorsqueCarburantInsuffisantSansReserveAlorsLAvionNestPasAutoriseeADecoller() {
        Avion avion = new Avion(TestFactory.PLAN_VOL_QC_MTL_AVEC_RESERVE, AvionTest.CAPACITE_POIDS_KG,
                AvionTest.CAPACITE_SOUTE_LITRES);
        Carburant carburantInsuffisant = new Carburant(TestFactory.LITRE_CARBURANT_QC_MTL_MOINS_UNE_SECONDE);

        avion.remplir(carburantInsuffisant);

        assertFalse(avion.autoriseADecoller());
    }

    @Test
    public void etantDonneAvionLorsqueCarburantSuffisantAvecReserveAlorsAvionAutoriseeADecoller() {
        Avion avion = new Avion(TestFactory.PLAN_VOL_QC_MTL_AVEC_RESERVE, AvionTest.CAPACITE_POIDS_KG,
                AvionTest.CAPACITE_SOUTE_LITRES);
        Carburant carburantSuffisant = new Carburant(TestFactory.LITRE_CARBURANT_NECESSAIRE_QC_MTL_AJUSTE);

        avion.remplir(carburantSuffisant);

        assertTrue(avion.autoriseADecoller());
    }
}