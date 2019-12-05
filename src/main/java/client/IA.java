package client;

import game.zone.ZoneBuilding;
import game.zone.ZoneCarteCivilisation;
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
    public abstract int chooseZone (int[] zoneAvailableSpace, String[] zoneName, ZoneBuilding[] buildings, ZoneCarteCivilisation[] cV);

	/**
	 * chooseNumber retourne le nombre de figurines choisie par l'IA. 
	 * @param min : le nombre de figurines minimum.
	 * @param max : le nombre de figurines maximum. 
	 * @return le nombre de figurines que l'IA place.
	 */
	public abstract int chooseNumber(int min,int max);

	/**
	 * chooseNumber retourne le nombre de res choisie par l'IA. 
	 * @param Le nombre de figurines a nourrir
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
	 * @param ressourceNumber : les ressources premieres du joueur
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
	 * @param toolsToUse : le tableau des outils avec leurs niveau.
	 * @param useTools : le tableau des outils deja utilises et non disponible.
	 * @return
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
	 * Si oui, elle demande lesquelles.
	 * @return
	 */
	public abstract int useRessourceCard();


	@Override
	public abstract String toString();
}