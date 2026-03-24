package ca.csfoy.w20.tp2avion.itineraire;

public class RegistresAeroport {
    public static Aeroport initialiserAeroport(CodeAeroport code) {
        return switch (code) {
            case YQB -> new Aeroport(CodeAeroport.YQB, "Québec", new Coordonnee(46.81, -71.20));
            case YUL -> new Aeroport(CodeAeroport.YUL, "Montréal", new Coordonnee(45.50, -73.56));
            case YYZ -> new Aeroport(CodeAeroport.YYZ, "Toronto", new Coordonnee(43.65, -79.38));
            case YVR -> new Aeroport(CodeAeroport.YVR, "Vancouver", new Coordonnee(49.28, -123.12));
            case MCO -> new Aeroport(CodeAeroport.MCO, "Orlando", new Coordonnee(28.53, -81.37));
            case CUN -> new Aeroport(CodeAeroport.CUN, "Cancun", new Coordonnee(21.16, -86.85));
            case CDG -> new Aeroport(CodeAeroport.CDG, "Paris Charles-de-Gaulle", new Coordonnee(48.85, 2.35));
            case AMS -> new Aeroport(CodeAeroport.AMS, "Amsterdam", new Coordonnee(52.36, 4.90));
        };
    }
}
