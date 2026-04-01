package ca.csfoy.w20.tp2avion.itineraire;

import ca.csfoy.w20.tp2avion.PlanVol;
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

        assertEquals(0, itineraire.calculerDistance());
    }

    @Test
    void etantDonneItineraireLosrqueUnSeulSegmentAlorsValeurAttendue() {
        Itineraire itineraire = TestFactory.itineraireCourtQcMtl;

        assertEquals(TestFactory.DISTANCE_QUEBEC_MONTREAL, itineraire.calculerDistance(), 0.01);
    }

    @Test
    void etantDonneItineraireLosrqueSegmentPlusieursSegmentsAlorsValeurAttendue() {
        Itineraire itineraire = TestFactory.itineraireLongQcMtlTorontoParisVancouver;

        assertEquals(TestFactory.DISTANCE_QUEBEC_MONTREAL_TORONTO_PARIS_VANCOUVER, itineraire.calculerDistance(), 0.01);
    }

}