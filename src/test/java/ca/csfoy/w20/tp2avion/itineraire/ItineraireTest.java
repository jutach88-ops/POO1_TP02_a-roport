package ca.csfoy.w20.tp2avion.itineraire;

import ca.csfoy.w20.tp2avion.TestFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItineraireTest {

    @Test
    void etantDonneItinéraireLorsqueSansSegmentAlorsCompteurDeSegmentAttendu() {
        Itineraire itineraire = new Itineraire();

        assertEquals(0, itineraire.getNombreDeSegment());
    }

    @Test
    void etantDonneItinéraireLorsqueAjoutDeSegmentAlorsCompteurDeSegmentAttendu() {
        Itineraire itineraire = new Itineraire();
        Segment segment1 = new Segment(AeroportTest.aeroportYUL, AeroportTest.aeroportYQB);

        itineraire.ajoutSegment(segment1);

        assertEquals(1, itineraire.getNombreDeSegment());
    }

    @Test
    void etantDonneItinéraireLorsqueAjoutPlusieursSegmentAlorsCompteurDeSegmentAttendu() {
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
    void etantDonneItineraireLorsqueAucunSegmentAlorsValeurDistanceNonAttendue() {
        Itineraire itineraire = new Itineraire();

        assertEquals(0, itineraire.getDistance());
    }

    @Test
    void etantDonneItineraireLorsqueUnSeulSegmentAlorsValeurDistanceAttendue() {
        Itineraire itineraire = TestFactory.ITINERAIRE_COURT_QC_MTL;

        assertEquals(TestFactory.DISTANCE_QUEBEC_MONTREAL, itineraire.getDistance(), 0.01);
    }

    @Test
    void etantDonneItineraireLorsquePlusieursSegmentsAlorsValeurAttendue() {
        Itineraire itineraire = TestFactory.ITINERAIRE_LONG_QC_MTL_TORONTO_PARIS_VANCOUVER;

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
        itineraire.ajoutSegment(TestFactory.QUEBEC_MONTREAL);

        boolean estItineraireValide = itineraire.estItineraireValide();

        assertTrue(estItineraireValide);
    }

    @Test
    void etantDonneItineraireLorsqueDeuxSegmentAvecMemeAeroportDepartAlorsInvalide() {
        Itineraire itineraire = new Itineraire();
        itineraire.ajoutSegment(TestFactory.QUEBEC_MONTREAL);
        itineraire.ajoutSegment(TestFactory.QUEBEC_TORONTO);

        boolean estItineraireValide = itineraire.estItineraireValide();

        assertFalse(estItineraireValide);
    }

    @Test
    void etantDonneItineraireLorsqueDeuxSegmentSansLiaisonAlorsInvalide() {
        Itineraire itineraire = new Itineraire();
        itineraire.ajoutSegment(TestFactory.QUEBEC_MONTREAL);
        itineraire.ajoutSegment(TestFactory.TORONTO_PARIS);

        boolean estItineraireValide = itineraire.estItineraireValide();

        assertFalse(estItineraireValide);
    }

    @Test
    void etantDonneItineraireLorsquePlusieursSegmentAvecLiaisonAlorsValide() {
        Itineraire itineraire = new Itineraire();
        itineraire.ajoutSegment(TestFactory.QUEBEC_TORONTO);
        itineraire.ajoutSegment(TestFactory.TORONTO_PARIS);
        itineraire.ajoutSegment(TestFactory.PARIS_VANCOUVER);

        boolean estItineraireValide = itineraire.estItineraireValide();

        assertTrue(estItineraireValide);
    }

    @Test
    void etantDonneItineraireLorsqueDeuxChainesSansLiaisonAlorsInvalide() {
        Itineraire itineraire = new Itineraire();
        itineraire.ajoutSegment(TestFactory.QUEBEC_MONTREAL);
        itineraire.ajoutSegment(TestFactory.MONTREAL_TORONTO);
        itineraire.ajoutSegment(TestFactory.PARIS_VANCOUVER);
        itineraire.ajoutSegment(TestFactory.VANCOUVER_AMSTERDAM);

        boolean estItineraireValide = itineraire.estItineraireValide();

        assertFalse(estItineraireValide);
    }

}