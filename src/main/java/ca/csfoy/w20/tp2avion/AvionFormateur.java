package ca.csfoy.w20.tp2avion;

public class AvionFormateur {

    public static final String FORMAT_DETAIL = """
            
            +++ AUTORIATION À DÉCOLER +++
            ==> %s <==
            +++++++++++++++++++++++++++++
            
            == Capacités ==
             * %.2f kg au total
             * %.2f litres dans la soute
            
            == Détail du vol ==
            Durée prévue %.2f heures à une vitesse de croisière de %.2f Km / h
            Une réserve de carburant supplémentaire %.2f %% est requise pour le décollage
            
            == Chargement ==
            Carburant: %.2f kg
            Passagers %.2f kg
            Bagages de cabine %.2f kg
            Bagages %.2f kg
            Poids total du chargement: %.2f kg (carburant exclu : %.2f kg)
            Volume occupé par les bagages en soute: %.2f L
            """;

    public static String detail(Avion avion) {
        return String.format(
                AvionFormateur.FORMAT_DETAIL,
                avion.autoriseADecoller() ? "OUI" : "NON",
                avion.getCapaciteChargementEnKg(),
                avion.getCapaciteChargementSouteEnLitres(),
                avion.getDureeVolPrevueEnHeures(),
                avion.getVitesseVolPrevue(),
                avion.getReserveCarburantRequise() * 100,
                avion.getPoidsCarburantEnKg(),
                avion.getPoidsPassagers(),
                avion.getPoidsBagagesEnCabine(),
                avion.getPoidsBagagesEnSoute(),
                avion.getPoidsChargementTotal(),
                avion.getPoidsChargementTotal() - avion.getPoidsCarburantEnKg(),
                avion.getVolumeOccupeSoute()
        );
    }
}
