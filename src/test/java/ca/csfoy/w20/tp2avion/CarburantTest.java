package ca.csfoy.w20.tp2avion;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CarburantTest {

    public static final double VOLUME_EN_LITRES = 1000.0;
    public static final double FAIBLE_VOLUME_EN_LITRES = 15.0;
    public static final double MASSE_VOLUMIQUE_1000_LITRES =
            CarburantTest.VOLUME_EN_LITRES * Carburant.MASSE_VOLUMIQUE_KEROSENE_15_C;
    public static final double VOLUME_INVALIDE = Carburant.SEUIL_VOLUME_EN_LITRES - 0.01;

    @Test
    public void etantDonneeDuCarburantLorsqueCreeAvecUneQuantiteValideAlorsEstValide() {
        Carburant carburant = new Carburant(CarburantTest.VOLUME_EN_LITRES);

        assertEquals(CarburantTest.VOLUME_EN_LITRES + " L (" + CarburantTest.MASSE_VOLUMIQUE_1000_LITRES + " kg)",
                carburant.toString());
    }

    @Test
    public void etantDonneeDuCarburantLorsqueCreeAvecUneQuantiteDonneeAlorsPeutCalculerLaMasseVolumique() {
        Carburant carburant = new Carburant(CarburantTest.VOLUME_EN_LITRES);

        double resultat = carburant.calculerPoidsEnKg();

        assertEquals(CarburantTest.MASSE_VOLUMIQUE_1000_LITRES, resultat, 0.01);
    }

    @Test
    public void etantDonneeDuCarburantLorsqueCreeAvecUneInferieureAuSeuilAlorsEstInvalide() {
        assertThrows(IllegalArgumentException.class, () -> new Carburant(CarburantTest.VOLUME_INVALIDE));
    }

    @Test
    public void etantDonneeCarburantLorsqueInsuffisantPlanDeVolAlorsEstInvalide() {
        Carburant carburant = new Carburant(CarburantTest.FAIBLE_VOLUME_EN_LITRES);

        boolean estCarburantSuffisant = carburant.estAutonomieCouvreDuree(PlanVolTest.DUREE_QUEBEC_MONTREAL, PlanVolTest.RESERVE_0_POURCENT);

        assertFalse(estCarburantSuffisant);
    }

    @Test
    public void etantDonneeCarburantLorsqueSuffisantPlanDeVolAlorsEstValide() {
        Carburant carburant = new Carburant(CarburantTest.VOLUME_EN_LITRES);

        boolean estCarburantSuffisant = carburant.estAutonomieCouvreDuree(PlanVolTest.DUREE_QUEBEC_MONTREAL, PlanVolTest.RESERVE_0_POURCENT);

        assertTrue(estCarburantSuffisant);
    }

}