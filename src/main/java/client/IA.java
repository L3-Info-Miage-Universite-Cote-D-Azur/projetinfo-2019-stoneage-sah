package client;

/**
 * Interface pour l'IA du jeu. 
 * @author Mentra20
 *
 */

public interface IA{
	
	/**
	 * chooseZone retourne l'indice de la zone choisie par l'IA . 
	 * @param zoneAvailableSpace le tableau avec l'espace disponible de chaque zone. 
	 * @param zoneName le tableau des noms des zones.
	 * @return l'indice de la zone
	 */
	public int chooseZone(int[] zoneAvailableSpace,String[] zoneName);
	
	/**
	 * chooseNumber retourne le nombre de figurines choisie par l'IA. 
	 * @param min et max, le nombre minimum et maximum entre lesquels l'IA doit choisir. 
	 * @return l'indice de la zone
	 */
	public int chooseNumber(int min,int max);
	
	/**
	 * chooseNumber retourne le nombre de res choisie par l'IA. 
	 * @param Le nombre de figurines a nourrir
	 * @param Un tableau contenant le nombre de ressource que l'IA possede par index.
	 * @param Un tableau contenant le nom des ressources que l'IA possede par index.
	 * @return l'indice de la zone
	 */
	public int[] chooseRessource(int figurinesToFeed, int[] RessourceNumber, String[] ressourceName);
	
	/**
	 * useRessourceToFeed renvoie true ou false selon le choix de l'IA pour nourrir 
	 * ses figurines avec des ressources. 
	 * @param RessourceNumber Le tableau du nombre de ressource possedee par le joueur. 
	 * @return Booleen : true si le joueur veut utiliser ses ressources pour nourrir, false sinon.
	 */
	public boolean useRessourceToFeed(int[] ressourceNumber);

	/**
	 * pickCard renvoie les ressources donnee pour l'achat de la carte civilisation. 
	 * Si l'IA renvoie un tableau vide, elle refuse l'achat de la carte. 
	 * @param ressourceNumber : les ressources premieres du joueur
	 * @param numberRessourceRequire : le nombre de ressource pour l'achat de la carte. 
	 * @return res : tableau des ressources donnes pour la carte. 
	 */
	public int[] pickCard(int[] ressourceNumber,int numberRessourceRequire);
	
	@Override
	public String toString();
}

