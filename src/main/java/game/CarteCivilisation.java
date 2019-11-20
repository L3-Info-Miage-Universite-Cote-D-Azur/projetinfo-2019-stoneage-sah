package game;

public class CarteCivilisation{
    
    private final int typeDownPart; // de 0 a 7 pour les cartes vertes, 8 a 11 pour les cartes jaunes. 
    /*
     * 0 -> medecine
     * 1 -> Art
     * 2 -> Ecriture
     * 3 -> Poterie
     * 4 -> Cardran Solaire
     * 5 -> Transport
     * 6 -> Musique
     * 7 -> Tissage
     * 8 -> Sable paysan
     * 9 -> Sable fabricant d'outil
     * 10 -> Sable constructeur
     * 11 -> Sable chamane
     */
    private final int numberDownPart; // pour les cartes sables : le nombre de personnage sur la carte.
    private final int typeUpPart; 
    /*
    Pour typeUpPart:
    0: ressource instantanée joueur
    1: point de victoire
    Ajout des autres cartes plus tard. 
    */
    
    private final Ressource ressource; //La ressrouce concernee par la carte. 
    private final int numberEffect; //Le nombre de l'eefet de la carte.
    
    /* CONSTRUCTOR */
     CarteCivilisation(int typeUp, int typeDown,int numberDownPart, Ressource ressource, int numberEffect){
         typeUpPart = typeUp;
         typeDownPart = typeDown;
         this.ressource = ressource;
         this.numberDownPart = numberDownPart;
         this.numberEffect = numberEffect;
     }

	public int getTypeDownPart() { return typeDownPart; }
	public int getNumberDownPart() { return numberDownPart; }
	public int getTypeUpPart() { return typeUpPart; }
	public Ressource getRessource() { return ressource; }
	public int getNumberEffect() { return numberEffect; }
    
    
}