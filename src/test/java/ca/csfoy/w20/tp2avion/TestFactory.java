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

    // Création de plan de vol
    public static final int VITESSE_DE_CROISIERE = 800;
    public static final double RESERVE_DE_CARBURANT_MINIMAL = 0.18;
    public static final double SANS_RESERVE_DE_CARBURANT = 0.0;

    public static PlanVol
            planVolQcMtl = new PlanVol(itineraireCourtQcMtl, TestFactory.VITESSE_DE_CROISIERE, TestFactory.SANS_RESERVE_DE_CARBURANT);
    public static PlanVol
            planVolQcMtlAvecReserve = new PlanVol(itineraireCourtQcMtl, TestFactory.VITESSE_DE_CROISIERE, TestFactory.RESERVE_DE_CARBURANT_MINIMAL);
    public static PlanVol
            planVolQcMtlSansReserve = new PlanVol(itineraireCourtQcMtl, TestFactory.VITESSE_DE_CROISIERE, TestFactory.SANS_RESERVE_DE_CARBURANT);

    // Creation de durée prévue en seconde pour un itinéraire donné.
    public static long dureePrevuEnSeconde(PlanVol planVol) {
        return planVol.getDureePrevue();
    }

    // =============================
    //      Pour test Carburant
    // =============================
    public static long dureeSecondePrevuQcMtl = dureePrevuEnSeconde(planVolQcMtl);
    public static long dureePrevuQcMtlMoinUneSeconde = dureePrevuEnSeconde(planVolQcMtl) - 1;

    // Calculer autonomie exacte en carburant d'un itinéraire
    public static final double MASSE_VOLUMIQUE_KEROSENE_15_C = 0.804;
    public static final double CONSOMMATION_PAR_HEURE_EN_KG = 2400;
    public static final int SECONDES_PAR_HEURE = 3600;


    public static double calculerAutonomieExacteCarburantItineraire(long dureeSecondeItineraire, PlanVol planVol) {
        return Math.round(
                ((dureeSecondeItineraire * TestFactory.CONSOMMATION_PAR_HEURE_EN_KG)
                        /TestFactory.SECONDES_PAR_HEURE)
                        /TestFactory.MASSE_VOLUMIQUE_KEROSENE_15_C) * (1 + planVol.getReserveCarburantMinimale());
    }

    // =============================
    //      Pour test Carburant
    // =============================
    public static double carburantExactementNecessaireQcMtl = calculerAutonomieExacteCarburantItineraire(dureeSecondePrevuQcMtl, planVolQcMtl);
    public static double carburantMoinsUneSecondeQcMtl = calculerAutonomieExacteCarburantItineraire(dureePrevuQcMtlMoinUneSeconde, planVolQcMtl);

    // =============================
    //      Pour test PlanVol
    // =============================
    public static double carburantNecessaireQcMtlNonAjuste = calculerAutonomieExacteCarburantItineraire(dureeSecondePrevuQcMtl, planVolQcMtlSansReserve);
    public static double carburantNecessaireQcMtlAjuste = calculerAutonomieExacteCarburantItineraire(dureeSecondePrevuQcMtl, planVolQcMtlAvecReserve);
    public static double carburantQcMtlMoinsUneSeconde = calculerAutonomieExacteCarburantItineraire(dureePrevuQcMtlMoinUneSeconde, planVolQcMtlAvecReserve);

}
