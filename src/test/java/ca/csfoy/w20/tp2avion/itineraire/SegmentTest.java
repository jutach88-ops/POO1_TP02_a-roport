package ca.csfoy.w20.tp2avion.itineraire;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SegmentTest {

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
        assertThrows(IllegalArgumentException.class,
                () -> new Segment(AeroportTest.aeroportYQB, AeroportTest.aeroportYQB));
    }
}