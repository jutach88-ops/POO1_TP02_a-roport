package ca.csfoy.w20.tp2avion;

import ca.csfoy.w20.tp2avion.itineraire.Aeroport;
import ca.csfoy.w20.tp2avion.itineraire.CodeAeroport;
import ca.csfoy.w20.tp2avion.itineraire.Itineraire;
import ca.csfoy.w20.tp2avion.itineraire.RegistresAeroport;
import ca.csfoy.w20.tp2avion.itineraire.Segment;

public class TestFactory {

    public static final double DISTANCE_QUEBEC_MONTREAL = 232.93;
    public static final double DISTANCE_QUEBEC_MONTREAL_TORONTO_PARIS_VANCOUVER = 14660.11;

    //===============================
    //      Creation Aeroport
    //===============================
    public static final Aeroport AEROPORT_YQB = RegistresAeroport.initialiserAeroport(CodeAeroport.YQB);
    public static final Aeroport AEROPORT_YUL = RegistresAeroport.initialiserAeroport(CodeAeroport.YUL);
    public static final Aeroport AEROPORT_YYZ = RegistresAeroport.initialiserAeroport(CodeAeroport.YYZ);
    public static final Aeroport AEROPORT_YVR = RegistresAeroport.initialiserAeroport(CodeAeroport.YVR);
    public static final Aeroport AEROPORT_CDG = RegistresAeroport.initialiserAeroport(CodeAeroport.CDG);

    //===============================
    //     Creation de Segments
    //===============================
    public static final Segment QUEBEC_MONTREAL = new Segment(AEROPORT_YQB, AEROPORT_YUL);
    public static final Segment QUEBEC_TORONTO = new Segment(AEROPORT_YQB, AEROPORT_YYZ);
    public static final Segment MONTREAL_TORONTO = new Segment(AEROPORT_YUL, AEROPORT_YYZ);
    public static final Segment TORONTO_PARIS = new Segment(AEROPORT_YYZ, AEROPORT_CDG);
    public static final Segment PARIS_VANCOUVER = new Segment(AEROPORT_CDG, AEROPORT_YVR);

    //===============================
    //    Creation d'itinéraires
    //===============================
    public static Itineraire constructeurItineraire(Segment... segments) {
        Itineraire itineraire = new Itineraire();

        for (Segment segment : segments) {
            itineraire.ajoutSegment(segment);
        }

        return itineraire;
    }

    public static final Itineraire ITINERAIRE_COURT_QC_MTL = constructeurItineraire(QUEBEC_MONTREAL);
    public static final Itineraire ITINERAIRE_LONG_QC_MTL_TORONTO_PARIS_VANCOUVER =
            constructeurItineraire(QUEBEC_MONTREAL, MONTREAL_TORONTO, TORONTO_PARIS, PARIS_VANCOUVER);

    //===============================
    // Création de plan de vol
    //===============================
    public static final int VITESSE_DE_CROISIERE = 800;
    public static final double RESERVE_DE_CARBURANT_MINIMAL = 0.18;
    public static final double SANS_RESERVE_DE_CARBURANT = 0.0;

    public static final PlanVol
            PLAN_VOL_QC_MTL =
            new PlanVol(ITINERAIRE_COURT_QC_MTL, TestFactory.VITESSE_DE_CROISIERE,
                    TestFactory.SANS_RESERVE_DE_CARBURANT);
    public static final PlanVol
            PLAN_VOL_QC_MTL_SANS_RESERVE =
            new PlanVol(ITINERAIRE_COURT_QC_MTL, TestFactory.VITESSE_DE_CROISIERE,
                    TestFactory.SANS_RESERVE_DE_CARBURANT);
    public static final PlanVol
            PLAN_VOL_QC_MTL_TOR_PA_VAN_AVEC_RESERVE =
            new PlanVol(ITINERAIRE_LONG_QC_MTL_TORONTO_PARIS_VANCOUVER, TestFactory.VITESSE_DE_CROISIERE,
                    TestFactory.RESERVE_DE_CARBURANT_MINIMAL);

    // ========================================
    //      Pour tests PlanVol et Tests Avion
    // ========================================
    public static final PlanVol
            PLAN_VOL_QC_MTL_AVEC_RESERVE = new PlanVol(ITINERAIRE_COURT_QC_MTL, TestFactory.VITESSE_DE_CROISIERE,
            TestFactory.RESERVE_DE_CARBURANT_MINIMAL);

    //====================================================================
    // Creation de durée prévue en seconde pour un itinéraire donné.
    //====================================================================
    public static long dureePrevuEnSeconde(PlanVol planVol) {
        return planVol.getDureePrevue();
    }

    // ===========================================
    //      Pour test Carburant et Tests Avions
    // ===========================================
    public static final long DUREE_SECONDE_PREVU_QC_MTL = dureePrevuEnSeconde(PLAN_VOL_QC_MTL);
    public static final long DUREE_PREVU_QC_MTL_MOIN_UNE_SECONDE = dureePrevuEnSeconde(PLAN_VOL_QC_MTL) - 1;
    public static final long DUREE_SECONDE_PREVU_QC_MTL_TOR_PA_VAN = dureePrevuEnSeconde(PLAN_VOL_QC_MTL_TOR_PA_VAN_AVEC_RESERVE);

    //====================================================================
    // Calculer autonomie exacte en carburant d'un itinéraire
    //====================================================================
    public static final double MASSE_VOLUMIQUE_KEROSENE_15_C = 0.804;
    public static final double CONSOMMATION_PAR_HEURE_EN_KG = 2400;
    public static final int SECONDES_PAR_HEURE = 3600;

    public static double calculerAutonomieExacteCarburantItineraire(long dureeSecondeItineraire, PlanVol planVol) {
        return
                ((dureeSecondeItineraire * TestFactory.CONSOMMATION_PAR_HEURE_EN_KG)
                        / TestFactory.SECONDES_PAR_HEURE)
                        / TestFactory.MASSE_VOLUMIQUE_KEROSENE_15_C * (1 + planVol.getReserveCarburantMinimale());
    }

    // =============================
    //      Pour test Carburant
    // =============================
    public static final double CARBURANT_EXACTEMENT_NECESSAIRE_QC_MTL =
            calculerAutonomieExacteCarburantItineraire(DUREE_SECONDE_PREVU_QC_MTL, PLAN_VOL_QC_MTL);
    public static final double CARBURANT_MOINS_UNE_SECONDE_QC_MTL =
            calculerAutonomieExacteCarburantItineraire(DUREE_PREVU_QC_MTL_MOIN_UNE_SECONDE, PLAN_VOL_QC_MTL);

    // =========================================
    //      Pour test PlanVol et Tests Avion
    // =========================================
    public static final double LITRES_CARBURANT_NECESSAIRE_QC_MTL_NON_AJUSTE =
            calculerAutonomieExacteCarburantItineraire(DUREE_SECONDE_PREVU_QC_MTL, PLAN_VOL_QC_MTL_SANS_RESERVE);
    public static final double LITRE_CARBURANT_NECESSAIRE_QC_MTL_AJUSTE =
            calculerAutonomieExacteCarburantItineraire(DUREE_SECONDE_PREVU_QC_MTL, PLAN_VOL_QC_MTL_AVEC_RESERVE);
    public static final double LITRE_CARBURANT_QC_MTL_MOINS_UNE_SECONDE =
            calculerAutonomieExacteCarburantItineraire(DUREE_PREVU_QC_MTL_MOIN_UNE_SECONDE,
                    PLAN_VOL_QC_MTL_AVEC_RESERVE);
    public static final double LITRE_CARBURANT_QC_MTL_TOR_PA_VAN =
            calculerAutonomieExacteCarburantItineraire(DUREE_SECONDE_PREVU_QC_MTL_TOR_PA_VAN,
                    PLAN_VOL_QC_MTL_TOR_PA_VAN_AVEC_RESERVE);

}
