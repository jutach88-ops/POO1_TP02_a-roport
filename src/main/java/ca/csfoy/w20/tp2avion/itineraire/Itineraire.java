package ca.csfoy.w20.tp2avion.itineraire;

import java.util.ArrayList;

public class Itineraire {

    private final ArrayList<Segment> segments = new ArrayList<>();

    private double distanceEnKm;
    private int nombreDeSegment = 0;

    public void ajoutSegment(Segment segment) {
        validerSegment(segment);
        this.segments.add(segment);
        this.calculerLongueurItineraireKm(segment);
        this.nombreDeSegment++;
    }

    public void validerSegment(Segment segment) {
        if (segment == null) {
            throw new IllegalArgumentException("Le segment est invalide, veuillez entrer un segment valide.");
        } else if (this.segments.contains(segment)) {
            throw new IllegalArgumentException("Un itinéraire ne peut pas avoir deux fois le même segment.");
        }

        /*
         *   J'ai laissé un commentaire dans cette section, juste pour montrer comment j'aurais implémenté le code
         *   différemment. Évidemment, c'est uniquement l'idée, le code n'est pas final et brise certain principe
         *   vu en classe. L'idée que j'ai eu est que le code actuellement avec les demandes de l'énoncé accepte un
         *   état illégal de l'objet itinéraire jusqu'à la validation appelé par PlanVol. Je crois que ça aurait pu
         *   être judicieux de directement lancer une exception lors de l'ajout d'un segment qui ne connecte pas.
         */

        //        else if (!this.segments.isEmpty() && !this.segments.getLast().getArrive().equals(segment.getDepart())) {
        //            throw new IllegalArgumentException("Un itinéraire doit être continu pour être valide");
        //        }
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
        ArrayList<Aeroport> aeroportsDeparts = getDepartsItineraire();
        ArrayList<Aeroport> aeroportsArrivees = getArriveesItineraire();

        if (!estListeDepartsEgalListeArrives()) {
            return false;
        }
        return estChaineConnectee(aeroportsDeparts, aeroportsArrivees)
                && estChaineConnectee(aeroportsArrivees, aeroportsDeparts);
    }

    private boolean estListeDepartsEgalListeArrives() {
        return getDepartsItineraire().size() == getArriveesItineraire().size();
    }

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

    //    private boolean estChaineConnectee() {
    //        int aeroportDepartManquant = 0;
    //        int aeroportArriveManquant = 0;
    //
    //        for (Aeroport aeroportDepart : getDepartsItineraire()) {
    //            if (!getArriveesItineraire().contains(aeroportDepart)) {
    //                aeroportDepartManquant++;
    //            }
    //        }
    //
    //        for (Aeroport aeroportArrive : getArriveesItineraire()) {
    //            if (!getDepartsItineraire().contains(aeroportArrive)) {
    //                aeroportArriveManquant++;
    //            }
    //        }
    //
    //        return aeroportDepartManquant == 1 && aeroportArriveManquant == 1;
    //    }

    private boolean estChaineConnectee(ArrayList<Aeroport> listeSource, ArrayList<Aeroport> listeAValider) {
        int aeroportManquant = 0;

        for (Aeroport aeroport : listeSource) {
            if (!listeAValider.contains(aeroport)) {
                aeroportManquant++;
            }
        }
        return aeroportManquant == 1;
    }

    /*
     * Concernant les .getDepart() et .getArrive() pour les liste d'aéroport, j'ai tenté de prendre la décision directement dans
     * Segment pour tenter de ne pas briser le TDA, cependant je me suis butté à plusieurs problèmes qui se retrouvaient à être
     * pire, soit en autre en exposant la liste de segment d'Itinéraire à Segment.
     * */

    /* R8 – Aide à la validation de l'itinéraire
     * Permet de récupérer l'ensemble des départs sans doublons
     * private ArrayList<Aeroport> getDeparts() { ... }
     */
    private ArrayList<Aeroport> getDepartsItineraire() {
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
    private ArrayList<Aeroport> getArriveesItineraire() {
        ArrayList<Aeroport> arrives = new ArrayList<>();
        for (Segment segment : this.segments) {
            if (!arrives.contains(segment.getArrive())) {
                arrives.add(segment.getArrive());
            }
        }
        return arrives;
    }

}
