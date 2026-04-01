package ca.csfoy.w20.tp2avion;

import ca.csfoy.w20.tp2avion.itineraire.Aeroport;
import ca.csfoy.w20.tp2avion.itineraire.CodeAeroport;
import ca.csfoy.w20.tp2avion.itineraire.Itineraire;
import ca.csfoy.w20.tp2avion.itineraire.RegistresAeroport;
import ca.csfoy.w20.tp2avion.itineraire.Segment;

public class TestFactory {

    public static final double DISTANCE_QUEBEC_MONTREAL = 232.93;
    public static final double DISTANCE_QUEBEC_MONTREAL_TORONTO_PARIS_VANCOUVER = 14660.11;


    //Creation Aeroport

    public static final Aeroport aeroportYQB = RegistresAeroport.initialiserAeroport(CodeAeroport.YQB);
    public static final Aeroport aeroportYUL = RegistresAeroport.initialiserAeroport(CodeAeroport.YUL);
    public static final Aeroport aeroportYYZ = RegistresAeroport.initialiserAeroport(CodeAeroport.YYZ);
    public static final Aeroport aeroportYVR = RegistresAeroport.initialiserAeroport(CodeAeroport.YVR);
    public static final Aeroport aeroportMCO = RegistresAeroport.initialiserAeroport(CodeAeroport.MCO);
    public static final Aeroport aeroportCUN = RegistresAeroport.initialiserAeroport(CodeAeroport.CUN);
    public static final Aeroport aeroportCDG = RegistresAeroport.initialiserAeroport(CodeAeroport.CDG);
    public static final Aeroport aeroportAMS = RegistresAeroport.initialiserAeroport(CodeAeroport.AMS);

    // Creation de Segments

    public static Segment quebecMontreal = new Segment(aeroportYQB, aeroportYUL);
    public static Segment quebecToronto = new Segment(aeroportYQB, aeroportYYZ);
    public static Segment montrealToronto = new Segment(aeroportYUL, aeroportYYZ);
    public static Segment torontoParis = new Segment(aeroportYYZ, aeroportCDG);
    public static Segment parisVancouver = new Segment(aeroportCDG, aeroportYVR);
    public static Segment torontoVancouver = new Segment(aeroportYYZ, aeroportYVR);
    public static Segment vancouverOrlando = new Segment(aeroportYVR, aeroportMCO);
    public static Segment orlandoCancun = new Segment(aeroportMCO, aeroportCUN);
    public static Segment cancunParis = new Segment(aeroportCUN, aeroportCDG);

    // Creation d'itinéraires
    public static Itineraire constructeurItineraire(Segment... segments) {
        Itineraire itineraire = new Itineraire();

        for (Segment segment : segments) {
            itineraire.ajoutSegment(segment);
        }

        return itineraire;
    }

    public static Itineraire itineraireCourtQcMtl = constructeurItineraire(quebecMontreal);
    public static Itineraire itineraireLongQcMtlTorontoParisVancouver = constructeurItineraire(quebecMontreal, montrealToronto, torontoParis, parisVancouver);
}
