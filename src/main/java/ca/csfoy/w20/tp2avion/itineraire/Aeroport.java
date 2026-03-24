package ca.csfoy.w20.tp2avion.itineraire;

public class Aeroport {
    private final CodeAeroport code;
    private final String nom;
    private final Coordonnee coordonnee;

    public Aeroport(CodeAeroport code, String nom, Coordonnee coordonnee) {
        this.validerCode(code);
        this.validerNom(nom);
        this.validerCoordonnees(coordonnee);

        this.code = code;
        this.nom = nom;
        this.coordonnee = coordonnee;
    }

    private void validerCode(CodeAeroport code) {
        if (code == null) {
            throw new IllegalArgumentException("Le code est obligatoire");
        }
    }

    private void validerNom(String nom) {
        if (nom == null || nom.isBlank()) {
            throw new IllegalArgumentException("Le nom est obligatoire");
        }
    }

    private void validerCoordonnees(Coordonnee coordonnee) {
        if (coordonnee == null) {
            throw new IllegalArgumentException("Les coordonnées sont obligatoires");
        }
    }

    public double getDistance(Aeroport autre) {
        return this.coordonnee.distanceEnKm(autre.coordonnee);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Aeroport aeroport)) {
            return false;
        }
        return this.code.equals(aeroport.code);
    }

    public int hashCode() {
        return this.code.hashCode();
    }

    @Override
    public String toString() {
        return this.nom + "[" + this.code + "]" + this.coordonnee;
    }
}
