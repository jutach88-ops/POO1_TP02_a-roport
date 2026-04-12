package ca.csfoy.w20.tp2avion;

import ca.csfoy.w20.tp2avion.itineraire.Aeroport;
import ca.csfoy.w20.tp2avion.itineraire.CodeAeroport;
import ca.csfoy.w20.tp2avion.itineraire.Itineraire;
import ca.csfoy.w20.tp2avion.itineraire.RegistresAeroport;
import ca.csfoy.w20.tp2avion.itineraire.Segment;

public class Factory {

    //Creation Aeroport

    public static final Aeroport AEROPORT_YQB = RegistresAeroport.initialiserAeroport(CodeAeroport.YQB);
    public static final Aeroport AEROPORT_YYZ = RegistresAeroport.initialiserAeroport(CodeAeroport.YYZ);
    public static final Aeroport AEROPORT_CDG = RegistresAeroport.initialiserAeroport(CodeAeroport.CDG);

    // Creation de Segments

    public static final Segment TORONTO_PARIS = new Segment(AEROPORT_YYZ, AEROPORT_CDG);
    public static  final Segment QUEBEC_TORONTO = new Segment(AEROPORT_YQB, AEROPORT_YYZ);

    // Creation d'itinéraires
    public static Itineraire constructeurItineraire(Segment... segments) {
        Itineraire itineraire = new Itineraire();

        for (Segment segment : segments) {
            itineraire.ajoutSegment(segment);
        }

        return itineraire;
    }

    public static final Itineraire
            ITINERAIRE_LONG_QC_TORONTO_PARIS = constructeurItineraire(QUEBEC_TORONTO, TORONTO_PARIS);
}


