package ca.csfoy.w20.tp2avion.itineraire;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CoordonneeTest {

    public static final String ZERO_ZERO = "(0.0, 0.0)";
    public static final int LATITUDE_VALIDE = 0;
    public static final int LONGITUDE_VALIDE = 0;
    public static final double DELTA_INVALIDE = 0.1;
    public static final double LATITUDE_TROP_PETITE = Coordonnee.LATITUDE_MIN - CoordonneeTest.DELTA_INVALIDE;
    public static final double LATITUDE_TROP_GRANDE = Coordonnee.LATITUDE_MAX + CoordonneeTest.DELTA_INVALIDE;
    public static final double LONGITUDE_TROP_PETITE = Coordonnee.LONGITUDE_MIN - CoordonneeTest.DELTA_INVALIDE;
    public static final double LONGITUDE_TROP_GRANDE = Coordonnee.LONGITUDE_MAX + CoordonneeTest.DELTA_INVALIDE;
    public static final int DISTANCE_NULLE = 0;
    public static final double DISTANCE_QUEBEC_PARIS = 5270.36;

    private final Coordonnee quebec = new Coordonnee(46.81228, -71.21454);
    private final Coordonnee paris = new Coordonnee(48.8588, 2.2943);

    @Test
    public void etantDonneeUneCoordonneLorsqueToutesLesValeursSontValidesAlorsEstValide() {
        Coordonnee coordonnee = new Coordonnee(CoordonneeTest.LATITUDE_VALIDE, CoordonneeTest.LONGITUDE_VALIDE);

        assertEquals(CoordonneeTest.ZERO_ZERO, coordonnee.toString());
    }

    @Test
    public void etantDonneeUneCoordonneLorsqueLatitudeTropPetiteAlorsAlorsEstInvalide() {
        assertThrows(IllegalArgumentException.class,
                () -> new Coordonnee(CoordonneeTest.LATITUDE_TROP_PETITE, CoordonneeTest.LONGITUDE_VALIDE));
    }

    @Test
    public void etantDonneeUneCoordonneLorsqueLatitudeTropGrandeAlorsAlorsEstInvalide() {
        assertThrows(IllegalArgumentException.class,
                () -> new Coordonnee(CoordonneeTest.LATITUDE_TROP_GRANDE, CoordonneeTest.LONGITUDE_VALIDE));
    }

    @Test
    public void etantDonneeUneCoordonneLorsqueLongitudeTropPetiteAlorsAlorsEstInvalide() {
        assertThrows(IllegalArgumentException.class,
                () -> new Coordonnee(CoordonneeTest.LATITUDE_VALIDE, CoordonneeTest.LONGITUDE_TROP_PETITE));
    }

    @Test
    public void etantDonneeUneCoordonneLorsqueLongitudeTropGrandeAlorsAlorsEstInvalide() {
        assertThrows(IllegalArgumentException.class,
                () -> new Coordonnee(CoordonneeTest.LATITUDE_VALIDE, CoordonneeTest.LONGITUDE_TROP_GRANDE));
    }

    @Test
    public void etantDonneeDeuxCoordonneesLorsqueCalculeLaDistanceAlorsLaDistanceEstBonne() {
        double resultat = this.quebec.distanceEnKm(this.paris);

        assertEquals(CoordonneeTest.DISTANCE_QUEBEC_PARIS, resultat, 0.01);
    }

    @Test
    public void etantDonneeUneCoordonneesLorsqueCalculeLaDistanceAvecElleMemeAlorsEstNulle() {
        double resultat = this.quebec.distanceEnKm(this.quebec);

        assertEquals(CoordonneeTest.DISTANCE_NULLE, resultat);
    }

}