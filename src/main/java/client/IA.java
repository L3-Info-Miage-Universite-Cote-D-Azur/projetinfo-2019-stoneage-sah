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
	
	public boolean useRessourceToFeed(int[] RessourceNumber);

	@Override
	public String toString();
}

