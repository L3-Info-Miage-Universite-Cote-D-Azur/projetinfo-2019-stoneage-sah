package client;

import game.CarteCivilisation;
import game.Ressource;
import game.building.Building;
import inventory.InventoryIA;
import player.PlayerIA;

/**
 * Asbtract pour l'IA du jeu. 
 * @author Mentra20
 *
 */

public abstract class IA{

	PlayerIA playerIA; 
	InventoryIA inventoryIA;

	public IA(PlayerIA playerIA, InventoryIA inventoryIA){
		this.playerIA = playerIA;
		this.inventoryIA = inventoryIA;
	}

    /**
	 * chooseZone retourne l'indice de la zone choisie par l'IA . 
	 * @param zoneAvailableSpace le tableau avec l'espace disponible de chaque zone. 
	 * @param zoneName le tableau des noms des zones.
	 * @param buildings la copie des zones batiment
	 * @param cV la copie des zones carte civilisation
	 * @return l'indice de la zone
	 */
    public abstract int chooseZone (int[] zoneAvailableSpace, Building[] buildings, CarteCivilisation[] cV);

	/**
	 * chooseNumber retourne le nombre de figurines choisie par l'IA. 
	 * @param min : le nombre de figurines minimum.
	 * @param max : le nombre de figurines maximum. 
	 * @return le nombre de figurines que l'IA place.
	 */
	public abstract int chooseNumber(int min,int max);

	/**
	 * chooseRessouce retourne le nombre de ressources choisie par l'IA. 
	 * @param figurinesToFeed Le nombre de figurines a nourrir
	 * @return le tableau des ressources sacrifiee.
	 */
	public abstract int[] chooseRessource(int figurinesToFeed);

	/**
	 * useRessourceToFeed renvoie true ou false selon le choix de l'IA pour nourrir 
	 * ses figurines avec des ressources. 
	 * @return Booleen : true si le joueur veut utiliser ses ressources pour nourrir, false sinon.
	 */
	public abstract boolean useRessourceToFeed();

	/**
	 * pickCard renvoie les ressources donnee pour l'achat de la carte civilisation. 
	 * Si l'IA renvoie un tableau vide, elle refuse l'achat de la carte. 
	 * @param numberRessourceRequire : le nombre de ressource pour l'achat de la carte. 
	 * @return res : tableau des ressources donnes pour la carte. 
	 */
	public abstract int[] pickCard(int numberRessourceRequire);

	/**
	 * Renvoie un choix aleatoire sur la prise d'une carte batiment
	 * @return true ou false, aleatoirement
	 */
	public abstract boolean pickBuilding ();

	/**
	 * pickTools renvoie un tableau de boolean pour dire quel outils l'IA utilise.
	 * @return tableau des outils que le joueur veux utiliser
	 */
	public abstract boolean[] pickTools();

	/**
	 * Phase de tirage
	 * @param listeTirage des dée tirée
	 * @param alreadyChoose si un autre joueur l'a deja choisi ou non
	 * @return l'index de ce que veut le joueur dans le tirage
	 */
	public abstract int chooseTirage(int[] listeTirage, boolean[] alreadyChoose);
	
	/**
	 * useRessourceCard demande a l'ia si elle veut utiliser ses ressources au choix. 
	 * Si oui, elle demande lesquelles (id de la ressource).
	 * @return ressource que le joueur veux -1 pour ne pas l'utiliser
	 */
	public abstract int useRessourceCard();
	
	/**
	 * Renvoie le tableau des ressources utilisees pour les buildingsNotImposed
	 * @param nombreRessource le nombre de ressources
	 * @param combienDeRessourcesDifferentes le nombre de ressources differentes
	 * @return un tableau des ressources
	 */
	public abstract Ressource[] chooseRessourceBuildingNotImposed (int nombreRessource, int combienDeRessourcesDifferentes);
	
	/**
	 * Renvoie le tableau des ressources utilisees pour les buildingsChoosed
	 * @return un tableau des ressources
	 */
	public abstract Ressource[] chooseRessourceBuildingChoosed ();


	@Override
	public abstract String toString();
}
