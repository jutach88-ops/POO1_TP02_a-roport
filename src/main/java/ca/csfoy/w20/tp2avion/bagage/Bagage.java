package ca.csfoy.w20.tp2avion.bagage;

public final class Bagage {

    public static final double SEUIL_POIDS_INVALIDE = 0;
    public static final double SEUIL_VOLUME_INVALIDE = 0;
    private final double poidsEnKg;
    private final double volumeEnLitres;

    public Bagage(double poidsEnKg, double volumeEnLitres) {
        this.validerPoids(poidsEnKg);
        this.validerVolume(volumeEnLitres);

        this.poidsEnKg = poidsEnKg;
        this.volumeEnLitres = volumeEnLitres;
    }

    private void validerPoids(double poidsEnKg) {
        if (poidsEnKg <= Bagage.SEUIL_POIDS_INVALIDE) {
            throw new IllegalArgumentException(
                    "Poids invalide, inférieur au minimum requis: " + Bagage.SEUIL_POIDS_INVALIDE + " (" + poidsEnKg + ")");
        }
    }

    private void validerVolume(double volumeEnLitres) {
        if (volumeEnLitres < Bagage.SEUIL_VOLUME_INVALIDE) {
            throw new IllegalArgumentException(
                    "Poids invalide, inférieur au minimum requis: " + Bagage.SEUIL_VOLUME_INVALIDE + " (" + volumeEnLitres + ")");
        }
    }

    public double getPoidsEnKg() {
        return this.poidsEnKg;
    }

    public double getVolumeEnLitres() {
        return this.volumeEnLitres;
    }
}
