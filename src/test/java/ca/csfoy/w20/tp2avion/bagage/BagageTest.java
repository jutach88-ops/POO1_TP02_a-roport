package ca.csfoy.w20.tp2avion.bagage;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BagageTest {

    public static final double POIDS_EN_KG = 5;
    public static final double VOLUME_EN_LITRE = 10;
    public static final double VOLUME_INVALIDE = Bagage.SEUIL_VOLUME_INVALIDE - 0.01;
    public static final double POIDS_INVALIDE = Bagage.SEUIL_POIDS_INVALIDE - 0.01;

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

}