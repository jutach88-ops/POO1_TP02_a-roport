package ca.csfoy.w20.tp2avion;

import ca.csfoy.w20.tp2avion.itineraire.Itineraire;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PlanVolTest {
    public static final int VITESSE_CROISIERE = 900;
    public static final double RESERVE_10_POURCENT = 0.10;
    public static final double RESERVE_0_POURCENT = 0.0;

    public static final double DISTANCE_QUEBEC_MONTREAL = 232.93;
    public static final long DUREE_QUEBEC_MONTREAL =
            Math.round(
                    PlanVolTest.DISTANCE_QUEBEC_MONTREAL / PlanVolTest.VITESSE_CROISIERE * PlanVol.SECONDES_PAR_HEURE);

    public PlanVol planVol;
    public Itineraire itineraire;

    @BeforeEach
    public void setUp() {
        this.itineraire = TestFactory.ITINERAIRE_COURT_QC_MTL;
        this.planVol =
                new PlanVol(this.itineraire, PlanVolTest.VITESSE_CROISIERE,
                        PlanVolTest.RESERVE_0_POURCENT);
    }

    @Test
    public void etantDonnePlanVolLorsqueObtenirDistanceAlorsRetourneDistanceItineraire() {
        assertEquals(PlanVolTest.DISTANCE_QUEBEC_MONTREAL, this.planVol.getDistanceEnKm(), 0.01);
    }

    @Test
    public void etantDonnePlanDeVolLorsqueItineraireValideAlorsPlanVolValide() {
        assertTrue(this.planVol.isValide());
    }

    @Test
    public void etantDonnePlanVolAlorsLaDureePrevueEstCalculeeSelonDistanceEtVitesse() {
        assertEquals(PlanVolTest.DUREE_QUEBEC_MONTREAL, this.planVol.getDureePrevue());
    }

    @Test
    public void etantDonneUnPlanVolAlorsToStringContientLaDureeEtLaVitesse() {
        String resultat = this.planVol.toString();

        assertTrue(resultat.contains(String.valueOf(PlanVolTest.DUREE_QUEBEC_MONTREAL)));
        assertTrue(resultat.contains(String.valueOf(PlanVolTest.VITESSE_CROISIERE)));
    }

    @Test
    public void etantDonneCargoVivantLorsquePresentAlorsEstEnSecurite() {
        this.planVol.cargoVivantPresent();

        boolean estCargoVivantEnSecurite = this.planVol.isCargoVivantEnSecurite();

        assertTrue(estCargoVivantEnSecurite);
    }

    @Test
    public void etantDonneCargoVivantLorsqueNonPresentAlorsEstEnSecurite() {
        boolean estCargoVivantEnSecurite = this.planVol.isCargoVivantEnSecurite();

        assertTrue(estCargoVivantEnSecurite);
    }

    @Test
    public void etantDonneUnPlanVolAvecReserveAlorsToStringContientLePourcentageDeReserve() {
        PlanVol planVolAvecReserve = new PlanVol(this.itineraire, PlanVolTest.VITESSE_CROISIERE,
                PlanVolTest.RESERVE_10_POURCENT);

        String resultat = planVolAvecReserve.toString();

        assertTrue(resultat.contains("10.0"));
    }

    @Test
    public void etantDonneCarburantLorsqueSansReserveAlorsInvalide() {
        Carburant carburant = new Carburant(TestFactory.LITRES_CARBURANT_NECESSAIRE_QC_MTL_NON_AJUSTE);

        boolean estPlanVolSecuritaire = TestFactory.PLAN_VOL_QC_MTL_AVEC_RESERVE.isVolumeCarburantSecuritaire(carburant);

        assertFalse(estPlanVolSecuritaire);
    }

    @Test
    public void etantDonneCarburantLorsqueAvecReserveAlorsValide() {
        Carburant carburant = new Carburant(TestFactory.LITRE_CARBURANT_NECESSAIRE_QC_MTL_AJUSTE);

        boolean estPlanVolSecuritaire = TestFactory.PLAN_VOL_QC_MTL_AVEC_RESERVE.isVolumeCarburantSecuritaire(carburant);

        assertTrue(estPlanVolSecuritaire);
    }

    @Test
    public void etantDonneCarburantLorsqueAvecReserveMoinUneSecondeAlorsInvalide() {
        Carburant carburant = new Carburant(TestFactory.LITRE_CARBURANT_QC_MTL_MOINS_UNE_SECONDE);

        boolean estPlanVolSecuritaire = TestFactory.PLAN_VOL_QC_MTL_AVEC_RESERVE.isVolumeCarburantSecuritaire(carburant);

        assertFalse(estPlanVolSecuritaire);
    }
}