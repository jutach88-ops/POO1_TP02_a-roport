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
        this.itineraire = TestFactory.itineraireCourtQcMtl;
        this.planVol =
                new PlanVol(this.itineraire, PlanVolTest.VITESSE_CROISIERE,
                        PlanVolTest.RESERVE_0_POURCENT);
    }

    @Test
    public void etantDonneUnPlanVolLorsDeLObtenirDistanceAlorsRetourneLaDistanceDeLItineraire() {
        assertEquals(PlanVolTest.DISTANCE_QUEBEC_MONTREAL, this.planVol.getDistanceEnKm(), 0.01);
    }

    @Test
    public void etantDonneUnItineraireValideAlorsLePlanVolEstValide() {
        assertTrue(this.planVol.isValide());
    }

    @Test
    public void etantDonneUnPlanVolAlorsLaDureePrevueEstCalculeeSelonDistanceEtVitesse() {
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
    public void etantDonneCarburantLorsqueInsuffisantAlorsInvalide() {
        Carburant carburant = new Carburant(CarburantTest.FAIBLE_VOLUME_EN_LITRES);

        boolean estCarburantSuffisant = this.planVol.isVolumeCarburantSecuritaire(carburant);

        assertFalse(estCarburantSuffisant);
    }

    @Test
    public void etantDonneCarburantLorsqueSuffisantAlorsValide() {
        Carburant carburant = new Carburant(CarburantTest.VOLUME_EN_LITRES);

        boolean estCarburantSuffisant = this.planVol.isVolumeCarburantSecuritaire(carburant);

        assertTrue(estCarburantSuffisant);
    }
}