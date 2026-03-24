package ca.csfoy.w20.tp2avion.itineraire;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AeroportTest {

    public static final Coordonnee COORDONNEE_QUEBEC = new Coordonnee(46.81, -71.20);
    public static final String VILLE_QUEBEC = "Québec";
    public static final String VILLE_MONTREAL = "Montréal";
    public static final Coordonnee COORDONNEE_MONTREAL = new Coordonnee(45.50, -73.56);
    public static final double DISTANCE_QUEBEC_MONTREAL = 232.92;
    public static final String AUTRE_CHOSE = "Pas un aeroport";

    private final Aeroport quebec =
            new Aeroport(CodeAeroport.YQB, AeroportTest.VILLE_QUEBEC, AeroportTest.COORDONNEE_QUEBEC);
    private final Aeroport montreal = new Aeroport(CodeAeroport.YUL,
            AeroportTest.VILLE_MONTREAL, AeroportTest.COORDONNEE_MONTREAL);

    @Test
    public void etantDonneUnAeroportLorsqueCreeAvecToutesSesValeursAlorsEstValide() {
        assertEquals(
                AeroportTest.VILLE_QUEBEC + "[" + CodeAeroport.YQB + "]" + AeroportTest.COORDONNEE_QUEBEC,
                this.quebec.toString());
    }

    @Test
    public void etantDonneUnAeroportLorsqueCreeSansCodeAlorsEstInvalide() {
        assertThrows(IllegalArgumentException.class,
                () -> new Aeroport(null, AeroportTest.VILLE_QUEBEC, AeroportTest.COORDONNEE_QUEBEC));
    }

    @Test
    public void etantDonneUnAeroportLorsqueCreeSansNomAlorsEstInvalide() {
        assertThrows(IllegalArgumentException.class,
                () -> new Aeroport(CodeAeroport.YQB, null, AeroportTest.COORDONNEE_QUEBEC));
    }

    @Test
    public void etantDonneUnAeroportLorsqueCreeAvecUnNomVideAlorsEstInvalide() {
        assertThrows(IllegalArgumentException.class,
                () -> new Aeroport(CodeAeroport.YQB, "   ", AeroportTest.COORDONNEE_QUEBEC));
    }

    @Test
    public void etantDonneUnAeroportLorsqueCreeSansCoordonneesAlorsEstInvalide() {
        assertThrows(IllegalArgumentException.class,
                () -> new Aeroport(CodeAeroport.YQB, AeroportTest.VILLE_QUEBEC, null));
    }

    @Test
    public void etantDonneDeuxAeroportsLorsDuCalculDeLaDistanceAlorsCalculeLaBonneDistance() {
        double resultat = this.quebec.getDistance(this.montreal);

        assertEquals(AeroportTest.DISTANCE_QUEBEC_MONTREAL, resultat, 0.01);
    }

    @Test
    public void etantDonneUnAeroportsLorsqueCompareALuiMemeAlorsEstEgal() {
        assertEquals(this.quebec, this.quebec);
    }

    @Test
    public void etantDonneUnAeroportsLorsqueCompareAUnAutreAeroportAlorsNEstPasEgal() {
        assertNotEquals(this.quebec, this.montreal);
    }

    @Test
    public void etantDonneUnAeroportsLorsqueCompareAUChoseAlorsNEstPasEgal() {
        assertNotEquals(AeroportTest.AUTRE_CHOSE, this.quebec);
    }

    @Test
    public void etantDonneeUnAeroportLorsqueProduitHashCodeAlorsEstValeurAttendue() {
        int resultat  = this.quebec.hashCode();

        assertEquals(CodeAeroport.YQB.hashCode(), resultat);
    }
}