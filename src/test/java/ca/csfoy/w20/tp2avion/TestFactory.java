package ca.csfoy.w20.tp2avion;

import ca.csfoy.w20.tp2avion.bagage.Bagage;
import ca.csfoy.w20.tp2avion.itineraire.Aeroport;
import ca.csfoy.w20.tp2avion.itineraire.CodeAeroport;
import ca.csfoy.w20.tp2avion.itineraire.Itineraire;
import ca.csfoy.w20.tp2avion.itineraire.RegistresAeroport;
import ca.csfoy.w20.tp2avion.itineraire.Segment;

import java.util.ArrayList;
import java.util.Random;

public class TestFactory {

    public static final double DISTANCE_QUEBEC_MONTREAL = 232.93;
    public static final double DISTANCE_QUEBEC_MONTREAL_TORONTO_PARIS_VANCOUVER = 14660.11;

    public static final int VITESSE_DE_CROISIERE = 800;
    public static final double RESERVE_DE_CARBURANT_MINIMAL = 0.18;
    private static final double SANS_RESERVE_DE_CARBURANT = 0.0;

    private static final double MASSE_VOLUMIQUE_KEROSENE_15_C = 0.804;
    private static final double CONSOMMATION_PAR_HEURE_EN_KG = 2400;
    private static final int SECONDES_PAR_HEURE = 3600;

    private static final double VOLUME_MAXIMAL_EN_LITRES_ENREGISTRE = 100;
    private static final double VOLUME_MAXIMAL_EN_LITRES_EN_CABINE = 30;

    private static final double CAPACITE_POIDS_KG = 37_000.0;
    private static final double CAPACITE_SOUTE_LITRES = 27_000.0;

    //===============================
    //      Creation Aeroport
    //===============================
    private static final Aeroport AEROPORT_YQB = RegistresAeroport.initialiserAeroport(CodeAeroport.YQB);
    private static final Aeroport AEROPORT_YUL = RegistresAeroport.initialiserAeroport(CodeAeroport.YUL);
    private static final Aeroport AEROPORT_YYZ = RegistresAeroport.initialiserAeroport(CodeAeroport.YYZ);
    private static final Aeroport AEROPORT_YVR = RegistresAeroport.initialiserAeroport(CodeAeroport.YVR);
    private static final Aeroport AEROPORT_CDG = RegistresAeroport.initialiserAeroport(CodeAeroport.CDG);
    private static final Aeroport AEROPORT_AMS = RegistresAeroport.initialiserAeroport(CodeAeroport.AMS);

    //===============================
    //     Creation de Segments
    //===============================
    public static final Segment QUEBEC_MONTREAL = new Segment(AEROPORT_YQB, AEROPORT_YUL);
    public static final Segment QUEBEC_TORONTO = new Segment(AEROPORT_YQB, AEROPORT_YYZ);
    public static final Segment MONTREAL_TORONTO = new Segment(AEROPORT_YUL, AEROPORT_YYZ);
    public static final Segment TORONTO_PARIS = new Segment(AEROPORT_YYZ, AEROPORT_CDG);
    public static final Segment PARIS_VANCOUVER = new Segment(AEROPORT_CDG, AEROPORT_YVR);
    public static final Segment VANCOUVER_AMSTERDAM = new Segment(AEROPORT_YVR, AEROPORT_AMS);
    public static final Segment PARIS_TORONTO = new Segment(AEROPORT_CDG, AEROPORT_YYZ);

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

    // ==========================================
    //      Pour tests PlanVol, Tests Avion, Test Itinéraires
    // ==========================================
    public static final Itineraire ITINERAIRE_COURT_QC_MTL =
            constructeurItineraire(QUEBEC_MONTREAL);
    public static final Itineraire ITINERAIRE_LONG_QC_MTL_TORONTO_PARIS_VANCOUVER =
            constructeurItineraire(QUEBEC_MONTREAL, MONTREAL_TORONTO, TORONTO_PARIS, PARIS_VANCOUVER);
    public static final Itineraire ITINERAIRE_LONG_QC_TOR_PA =
            constructeurItineraire(QUEBEC_TORONTO, TORONTO_PARIS);

    //===============================
    // Création de plan de vol
    //===============================
    private static final PlanVol
            PLAN_VOL_QC_MTL =
            new PlanVol(ITINERAIRE_COURT_QC_MTL, TestFactory.VITESSE_DE_CROISIERE,
                    TestFactory.SANS_RESERVE_DE_CARBURANT);
    private static final PlanVol
            PLAN_VOL_QC_MTL_SANS_RESERVE =
            new PlanVol(ITINERAIRE_COURT_QC_MTL, TestFactory.VITESSE_DE_CROISIERE,
                    TestFactory.SANS_RESERVE_DE_CARBURANT);
    private static final PlanVol
            PLAN_VOL_QC_TOR_PA_AVEC_RESERVE =
            new PlanVol(ITINERAIRE_LONG_QC_TOR_PA, TestFactory.VITESSE_DE_CROISIERE,
                    TestFactory.RESERVE_DE_CARBURANT_MINIMAL);

    // ==========================================
    //      Pour tests PlanVol et Tests Avion
    // ==========================================
    public static final PlanVol
            PLAN_VOL_QC_MTL_AVEC_RESERVE = new PlanVol(ITINERAIRE_COURT_QC_MTL, TestFactory.VITESSE_DE_CROISIERE,
            TestFactory.RESERVE_DE_CARBURANT_MINIMAL);

    //====================================================================
    // Creation de durée prévue en seconde pour un itinéraire donné.
    //====================================================================
    private static long dureePrevuEnSeconde(PlanVol planVol) {
        return planVol.getDureePrevue();
    }

    // ===========================================
    //      Pour test Carburant et Tests Avions
    // ===========================================
    public static final long DUREE_SECONDE_PREVU_QC_MTL = dureePrevuEnSeconde(PLAN_VOL_QC_MTL);
    public static final long DUREE_PREVU_QC_MTL_MOIN_UNE_SECONDE = dureePrevuEnSeconde(PLAN_VOL_QC_MTL) - 1;
    public static final long DUREE_SECONDE_PREVU_QC_TOR_PA = dureePrevuEnSeconde(PLAN_VOL_QC_TOR_PA_AVEC_RESERVE);

    //====================================================================
    // Calculer autonomie exacte en carburant d'un itinéraire
    //====================================================================
    private static double calculerAutonomieExacteCarburantItineraire(long dureeSecondeItineraire, PlanVol planVol) {
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
    public static final double LITRE_CARBURANT_QC_TOR_PA =
            calculerAutonomieExacteCarburantItineraire(DUREE_SECONDE_PREVU_QC_TOR_PA,
                    PLAN_VOL_QC_TOR_PA_AVEC_RESERVE);

    // =========================================
    //      Création de bagages pour test
    // =========================================
    private static ArrayList<Bagage> genererBagages(int min, int max, int poidsMaximum, double volumeMaximum) {
        ArrayList<Bagage> bagages = new ArrayList<>();

        Random random = new Random();
        int count = random.nextInt(min, max);
        for (int i = 0; i < count; i++) {
            bagages.add(new Bagage(
                    Math.round(random.nextDouble(5, poidsMaximum)),
                    Math.round(random.nextDouble(4, volumeMaximum))));
        }

        return bagages;
    }

    private static final ArrayList<Bagage> BAGAGES_ANIMAUX = genererBagages(0, 4, 7, 10);
    private static final ArrayList<Bagage> BAGAGES_CABINE =
            genererBagages(20, 30, 15, VOLUME_MAXIMAL_EN_LITRES_EN_CABINE);
    private static final ArrayList<Bagage> BAGAGES_SOUTE =
            genererBagages(30, 120, 22, VOLUME_MAXIMAL_EN_LITRES_ENREGISTRE);

    private static void ajouterAnimaux(Avion avion, ArrayList<Bagage> bagages) {

        for (Bagage bagage : bagages) {
            avion.ajouterAnimalEnSoute(bagage);
        }
    }

    private static void ajouterBagageCabine(Avion avion, ArrayList<Bagage> bagages) {

        for (Bagage bagage : bagages) {
            avion.ajouterBagageEnCabine(bagage);
        }
    }

    private static void ajouterBagageSoute(Avion avion, ArrayList<Bagage> bagages) {

        for (Bagage bagage : bagages) {
            avion.ajouterBagageEnSoute(bagage);
        }
    }

    // =========================================
    //      Création d'Avions pour test
    // =========================================
    private static Avion avionBuilder(
            PlanVol planVol,
            double capacitePoidsKG,
            double capaciteSouteLitres,
            ArrayList<Bagage> animauxEnSoute,
            ArrayList<Bagage> bagagesCabine,
            ArrayList<Bagage> bagagesSoute,
            int nombreDePassager,
            Carburant carburant
    ) {

        Avion avion = new Avion(planVol, capacitePoidsKG, capaciteSouteLitres);
        ajouterAnimaux(avion, animauxEnSoute);
        ajouterBagageCabine(avion, bagagesCabine);
        ajouterBagageSoute(avion, bagagesSoute);
        avion.ajouterPassagers(nombreDePassager);
        avion.remplir(carburant);

        return avion;
    }

    // =========================================
    //      Avions Pour Tests Avion
    // =========================================

    private static final Carburant CARBURANT_QC_TOR_PA = new Carburant(TestFactory.LITRE_CARBURANT_QC_TOR_PA);

    public static final Avion AVION_AUTORISEE_DECOLLER_QC_TOR_PA = avionBuilder(
            PLAN_VOL_QC_TOR_PA_AVEC_RESERVE,
            CAPACITE_POIDS_KG,
            CAPACITE_SOUTE_LITRES,
            BAGAGES_ANIMAUX,
            BAGAGES_CABINE,
            BAGAGES_SOUTE,
            100,
            CARBURANT_QC_TOR_PA);

}