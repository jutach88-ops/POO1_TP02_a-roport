package ca.csfoy.w20.tp2avion.itineraire;

import ca.csfoy.w20.tp2avion.TestFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItineraireTest {

    @Test
    void etantDonneItinéraireLorsqueSansSegmentAlorsValide() {
        Itineraire itineraire = new Itineraire();

        assertEquals(0, itineraire.getNombreDeSegment());
    }

    @Test
    void etantDonneItinéraireLorsqueAjoutDeSegmentAlorsValide() {
        Itineraire itineraire = new Itineraire();
        Segment segment1 = new Segment(AeroportTest.aeroportYUL, AeroportTest.aeroportYQB);

        itineraire.ajoutSegment(segment1);

        assertEquals(1, itineraire.getNombreDeSegment());
    }

    @Test
    void etantDonneItinéraireLorsqueAjoutPlusieursSegmentAlorsValide() {
        Itineraire itineraire = new Itineraire();
        Segment segment1 = new Segment(AeroportTest.aeroportYUL, AeroportTest.aeroportYQB);
        Segment segment2 = new Segment(AeroportTest.aeroportYQB, AeroportTest.aeroportCUN);

        itineraire.ajoutSegment(segment1);
        itineraire.ajoutSegment(segment2);

        assertEquals(2, itineraire.getNombreDeSegment());
    }

    @Test
    void etantDonneItinéraireLorsqueAjoutDeSegmentNullAlorsInvalide() {
        Itineraire itineraire = new Itineraire();
        Segment segment1 = null;

        assertThrows(IllegalArgumentException.class, () -> itineraire.ajoutSegment(segment1));
    }

    @Test
    void etantDonneItinéraireLorsqueAjoutDeSegmentEnDoubleAlorsInvalide() {
        Itineraire itineraire = new Itineraire();
        Segment segment1 = new Segment(AeroportTest.aeroportYUL, AeroportTest.aeroportYQB);
        Segment segment2 = new Segment(AeroportTest.aeroportYUL, AeroportTest.aeroportYQB);

        itineraire.ajoutSegment(segment1);

        assertThrows(IllegalArgumentException.class, () -> itineraire.ajoutSegment(segment2));
    }

    @Test
    void etantDonneItineraireLosrqueAucunSegmentAlorsValeurAttendue() {
        Itineraire itineraire = new Itineraire();

        assertEquals(0, itineraire.getDistance());
    }

    @Test
    void etantDonneItineraireLosrqueUnSeulSegmentAlorsValeurAttendue() {
        Itineraire itineraire = TestFactory.itineraireCourtQcMtl;

        assertEquals(TestFactory.DISTANCE_QUEBEC_MONTREAL, itineraire.getDistance(), 0.01);
    }

    @Test
    void etantDonneItineraireLosrqueSegmentPlusieursSegmentsAlorsValeurAttendue() {
        Itineraire itineraire = TestFactory.itineraireLongQcMtlTorontoParisVancouver;

        assertEquals(TestFactory.DISTANCE_QUEBEC_MONTREAL_TORONTO_PARIS_VANCOUVER, itineraire.getDistance(), 0.01);
    }

    @Test
    void etantDonneItineraireLorsqueSansSegmentAlorsInvalide() {
        Itineraire itineraire = new Itineraire();

        boolean estItineraireValide = itineraire.estItineraireValide();

        assertFalse(estItineraireValide);
    }

    @Test
    void etantDonneItineraireLorsqueUnSeulSegmentAlorsValide() {
        Itineraire itineraire = new Itineraire();
        itineraire.ajoutSegment(TestFactory.quebecMontreal);

        boolean estItineraireValide = itineraire.estItineraireValide();

        assertTrue(estItineraireValide);
    }

    @Test
    void etantDonneItineraireLorsqueDeuxSegmentAvecMemeAeroportDepartAlorsInvalide() {
        Itineraire itineraire = new Itineraire();
        itineraire.ajoutSegment(TestFactory.quebecMontreal);
        itineraire.ajoutSegment(TestFactory.quebecToronto);

        boolean estItineraireValide = itineraire.estItineraireValide();

        assertFalse(estItineraireValide);
    }

    @Test
    void etantDonneItineraireLorsqueDeuxSegmentSansLiaisonAlorsInvalide() {
        Itineraire itineraire = new Itineraire();
        itineraire.ajoutSegment(TestFactory.quebecMontreal);
        itineraire.ajoutSegment(TestFactory.torontoParis);

        boolean estItineraireValide = itineraire.estItineraireValide();

        assertFalse(estItineraireValide);
    }

    @Test
    void etantDonneItineraireLorsquePlusieursSegmentAvecLiaisonAlorsValide() {
        Itineraire itineraire = new Itineraire();
        itineraire.ajoutSegment(TestFactory.quebecToronto);
        itineraire.ajoutSegment(TestFactory.torontoParis);
        itineraire.ajoutSegment(TestFactory.parisVancouver);

        boolean estItineraireValide = itineraire.estItineraireValide();

        assertTrue(estItineraireValide);
    }
}