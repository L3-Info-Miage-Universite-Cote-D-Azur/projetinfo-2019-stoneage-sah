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

	@Override
	public String toString();
}
