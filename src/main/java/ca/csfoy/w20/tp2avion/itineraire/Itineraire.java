package ca.csfoy.w20.tp2avion.itineraire;

import java.util.ArrayList;

public class Itineraire {

    private final ArrayList<Segment> segments = new ArrayList<>();

    private double distanceEnKm;
    private int nombreDeSegment = 0;

    public void ajoutSegment(Segment segment) {
        if (segments.contains(segment)) {
            throw new IllegalArgumentException("Un itinéraire ne peut pas avoir deux fois le même segment.");
        }
        validerSegment(segment);
        this.segments.add(segment);
        this.calculerLongueurItineraireKm(segment);
        this.nombreDeSegment++;
    }

    public void validerSegment(Segment segment) {
        if (segment == null) {
            throw new IllegalArgumentException("Le segment est invalide, veuillez entrer un segment valide.");
        }
    }

    public int getNombreDeSegment() {
        return this.nombreDeSegment;
    }

    public double getDistance() {
        return this.distanceEnKm;
    }

    private void calculerLongueurItineraireKm(Segment segment) {
        this.distanceEnKm += segment.calculerDistanceAeroport();
    }

    public boolean estItineraireValide() {
        return estChaineConnectee() && estListeDepartsEgalListeArrives();
    }

    private boolean estListeDepartsEgalListeArrives() {
        return getDeparts().size() == getArrivees().size();
    }

//    private boolean estMemeAeroportPresent() {
//        return getDeparts().contains()
//    }
    /* R8 – Implémenter la validation en vous servant du pseudo-code ci-dessous et les 2 méthodes fournies (getDeparts et
     * getArrivees()
     *
     * si le nombre de départs est différent du nombre d'arrivées
     *     retourner faux
     * si la différence entre les deux listes n'est pas unique dans les deux sens (départ -> arrivée et arrivée -> départ)
     *     retourner faux
     * retourner vrai
     *
     *
     * pour chaque aéroport dans comparaison
     * si l'aéroport n'est pas dans source
     *    incrémenter le compteur
     * retourner vrai si le compteur est exactement 1
     *
     * Vérifier si tous les segments sont connectés
     * A -> B, B -> C, C -> D [Départs A, B, C] [Arrivées B, C, D]
     *
     * Départs -> Arrivées -> A manquant (1 seul)
     * Arrivées -> Départs -> D manquant (1 seul)
     *
     * Itinéraire: OK
     *
     */

    private boolean estChaineConnectee() {
        int aeroportDepartManquant = 0;
        int aeroportArriveManquant = 0;

        for (Aeroport aeroportDepart : getDeparts()) {
            if (!getArrivees().contains(aeroportDepart)) {
                aeroportDepartManquant ++;
            }
        }

        for (Aeroport aeroportArrive : getArrivees()) {
            if (!getDeparts().contains(aeroportArrive)) {
                aeroportArriveManquant ++;
            }
        }

        return aeroportDepartManquant == 1 && aeroportArriveManquant == 1;
    }

    /* R8 – Aide à la validation de l'itinéraire
     * Permet de récupérer l'ensemble des départs sans doublons
     * private ArrayList<Aeroport> getDeparts() { ... }
     */
    private ArrayList<Aeroport> getDeparts() {
        ArrayList<Aeroport> departs = new ArrayList<>();
        for (Segment segment : this.segments) {
            if (!departs.contains(segment.getDepart())) {
                departs.add(segment.getDepart());
            }
        }
        return departs;
    }

    /* R8 – Aide à la validation de l'itinéraire
     * Permet de récupérer l'ensemble des arrivées sans doublons
     * private ArrayList<Aeroport> getArrivees() { ... }
     */
    private ArrayList<Aeroport> getArrivees() {
        ArrayList<Aeroport> arrives = new ArrayList<>();
        for (Segment segment : this.segments) {
            if (!arrives.contains(segment.getArrive())) {
                arrives.add(segment.getArrive());
            }
        }
        return arrives;
    }

}
