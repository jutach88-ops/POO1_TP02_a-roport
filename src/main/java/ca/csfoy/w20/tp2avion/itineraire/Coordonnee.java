package ca.csfoy.w20.tp2avion.itineraire;

/**
 * Coordonnée géographique terrestre
 */
public class Coordonnee {

    public static final int RAYON_MOYEN_TERRE_EN_KM = 6371;

    public static final double LATITUDE_MIN = -90;
    public static final double LATITUDE_MAX = 90;

    public static final double LONGITUDE_MIN = -180;
    public static final double LONGITUDE_MAX = 180;

    private final double latitude;
    private final double longitude;

    public Coordonnee(double latitude, double longitude) {
        this.validerLatitude(latitude);
        this.validerLongitude(longitude);

        this.latitude = latitude;
        this.longitude = longitude;
    }

    private void validerLatitude(double latitude) {
        if (latitude < Coordonnee.LATITUDE_MIN || latitude > Coordonnee.LATITUDE_MAX) {
            throw new IllegalArgumentException(
                    "Latitude (" + latitude + ") invalide, non comprise entre les bornes [" + Coordonnee.LATITUDE_MIN + ", " + Coordonnee.LATITUDE_MAX + "]");
        }
    }

    private void validerLongitude(double longitude) {
        if (longitude < Coordonnee.LONGITUDE_MIN || longitude > Coordonnee.LONGITUDE_MAX) {
            throw new IllegalArgumentException(
                    "Longitude (" + longitude + ") invalide, non comprise entre les bornes [" + Coordonnee.LONGITUDE_MIN + ", " + Coordonnee.LONGITUDE_MAX + "]");
        }
    }

    /**
     * Utilise la formule Haversine pour calculer la distance euclidienne entre deux points sur une sphere
     *
     * @param Coordonnee
     *         objet avec lequel on veut calucler la distance
     * @return retourne la distance entre deux points en kilometres
     */
    public double distanceEnKm(Coordonnee autre) {

        double latitudeRadian = Math.toRadians(this.latitude);
        double autreLatitudeRadian = Math.toRadians(autre.latitude);
        double deltaLatitude = Math.toRadians(autre.latitude - this.latitude);
        double deltaLongitude = Math.toRadians(autre.longitude - this.longitude);

        double a = (Math.sin(deltaLatitude / 2) * Math.sin(deltaLatitude / 2))
                + (Math.cos(latitudeRadian) * Math.cos(autreLatitudeRadian)
                * Math.sin(deltaLongitude / 2) * Math.sin(deltaLongitude / 2));

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return Coordonnee.RAYON_MOYEN_TERRE_EN_KM * c;
    }

    @Override
    public String toString() {
        return "(" + this.latitude + ", " + this.longitude + ")";
    }
}
