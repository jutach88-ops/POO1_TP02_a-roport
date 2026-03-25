package ca.csfoy.w20.tp2avion.bagage;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BagageTest {

    public static final double POIDS_EN_KG = 5;
    public static final double VOLUME_EN_LITRE = 10;
    public static final double VOLUME_INVALIDE = Bagage.SEUIL_VOLUME_INVALIDE - 0.01;
    public static final double POIDS_INVALIDE = Bagage.SEUIL_POIDS_INVALIDE - 0.01;
    public static final double VOLUME_MAXIMAL_EN_LITRES = 100;

    @Test
    public void etantDonneeUnBagageLorsqueCreeAvecToutesLesValeursRequisesAlorsEstValide() {
        Bagage bagage = new Bagage(BagageTest.POIDS_EN_KG, BagageTest.VOLUME_EN_LITRE);

        assertEquals(BagageTest.POIDS_EN_KG, bagage.getPoidsEnKg());
        assertEquals(BagageTest.VOLUME_EN_LITRE, bagage.getVolumeEnLitres());
    }

    @Test
    public void etantDonneUnBagageLorsqueCreeAvecUnPoidsInvalideAlorsEstInvalide() {
        assertThrows(IllegalArgumentException.class,
                () -> new Bagage(BagageTest.POIDS_INVALIDE, BagageTest.VOLUME_EN_LITRE));
    }

    @Test
    public void etantDonneUnBagageLorsqueCreeAvecUnVolumeInvalideAlorsEstInvalide() {
        assertThrows(IllegalArgumentException.class,
                () -> new Bagage(BagageTest.POIDS_EN_KG, BagageTest.VOLUME_INVALIDE));
    }

    @Test
    public void etantDonneUnBagageLorsqueNonConformeAlorsInvalide() {
        Bagage bagage = new Bagage(30,80);

        boolean estBagageConforme = bagage.estBagageConforme(BagageTest.VOLUME_MAXIMAL_EN_LITRES);

        assertTrue(estBagageConforme);
    }

    @Test
    public void etantDonneUnBagageLorsqueLimiteAlorsValide() {
        Bagage bagage = new Bagage(30,100);

        boolean estBagageConforme = bagage.estBagageConforme(BagageTest.VOLUME_MAXIMAL_EN_LITRES);

        assertTrue(estBagageConforme);
    }

    @Test
    public void etantDonneUnBagageLorsqueConformeAlorsValide() {
        Bagage bagage = new Bagage(30,BagageTest.VOLUME_MAXIMAL_EN_LITRES + 1);

        boolean estBagageConforme = bagage.estBagageConforme(BagageTest.VOLUME_MAXIMAL_EN_LITRES);

        assertFalse(estBagageConforme);
    }

}