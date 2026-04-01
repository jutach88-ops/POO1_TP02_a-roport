package ca.csfoy.w20.tp2avion.itineraire;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SegmentTest {

    public static Segment quebecMontreal = new Segment(AeroportTest.aeroportYQB,AeroportTest.aeroportYUL);
    public static Segment montrealToronto = new Segment(AeroportTest.aeroportYUL,AeroportTest.aeroportYYZ);
    public static Segment torontoVancouver = new Segment(AeroportTest.aeroportYYZ,AeroportTest.aeroportYVR);
    public static Segment vancouverOrlando = new Segment(AeroportTest.aeroportYVR,AeroportTest.aeroportMCO);
    public static Segment orlandoCancun = new Segment(AeroportTest.aeroportMCO,AeroportTest.aeroportCUN);
    public static Segment cancunParis = new Segment(AeroportTest.aeroportCUN,AeroportTest.aeroportCDG);

    @Test
    void etantDonneSegmentLorsqueInstantiationParametreValideAlorsValide() {
        Segment segment = new Segment(AeroportTest.aeroportYQB, AeroportTest.aeroportYUL);

        assertEquals(AeroportTest.aeroportYQB, segment.getDepart());
        assertEquals(AeroportTest.aeroportYUL, segment.getArrive());
    }

    @Test
    void etantDonneSegmentLorsqueInstantiationAeroportDepartNullAlorsNonValide() {
        assertThrows(IllegalArgumentException.class, () -> new Segment(null, AeroportTest.aeroportYUL));
    }

    @Test
    void etantDonneSegmentLorsqueInstantiationAeroportArriveeNullAlorsNonValide() {
        assertThrows(IllegalArgumentException.class, () -> new Segment(AeroportTest.aeroportYQB, null));
    }

    @Test
    void etantDonneSegmentLorsqueInstantiationMemeAeroportArriveeDepartAlorsNonValide() {
        assertThrows(IllegalArgumentException.class, () -> new Segment(AeroportTest.aeroportYQB, AeroportTest.aeroportYQB));
    }
}