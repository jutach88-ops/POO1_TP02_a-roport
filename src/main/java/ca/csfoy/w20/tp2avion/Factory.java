package ca.csfoy.w20.tp2avion;

import ca.csfoy.w20.tp2avion.itineraire.Aeroport;
import ca.csfoy.w20.tp2avion.itineraire.CodeAeroport;
import ca.csfoy.w20.tp2avion.itineraire.Itineraire;
import ca.csfoy.w20.tp2avion.itineraire.RegistresAeroport;
import ca.csfoy.w20.tp2avion.itineraire.Segment;

public class Factory {

    //Creation Aeroport

    public static final Aeroport aeroportYQB = RegistresAeroport.initialiserAeroport(CodeAeroport.YQB);
    public static final Aeroport aeroportYYZ = RegistresAeroport.initialiserAeroport(CodeAeroport.YYZ);
    public static final Aeroport aeroportCDG = RegistresAeroport.initialiserAeroport(CodeAeroport.CDG);

    // Creation de Segments

    public static Segment torontoParis = new Segment(aeroportYYZ, aeroportCDG);
    public static Segment quebecToronto = new Segment(aeroportYQB, aeroportYYZ);

    // Creation d'itinéraires
    public static Itineraire constructeurItineraire(Segment... segments) {
        Itineraire itineraire = new Itineraire();

        for (Segment segment : segments) {
            itineraire.ajoutSegment(segment);
        }

        return itineraire;
    }

    public static Itineraire itineraireLongQcTorontoParis = constructeurItineraire(quebecToronto, torontoParis);
}


