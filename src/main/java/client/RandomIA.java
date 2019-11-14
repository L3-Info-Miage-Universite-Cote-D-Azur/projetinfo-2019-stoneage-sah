package client;

import game.Settings;


/**
 * RandomIA correspond a l'IA comportant une strategie basee sur le hasard. 
 * @author Mentra20
 *
 */

public class RandomIA implements IA {

	/**
	 * chooseZone retourne l'indice de la zone choisie par l'IA (ici au hasard). 
	 * @param zoneAvailableSpace le tableau avec l'espace disponible de chaque zone. 
	 * @param zoneName le tableau des noms des zones.
	 * @return l'indice de la zone
	 */
	public int chooseZone (int[] zoneAvailableSpace,String[] zoneName) {
		return Settings.RAND.nextInt(zoneAvailableSpace.length);
	}

	/**
	 * chooseNumber retourne le nombre de figurines choisie par l'IA (ici au hasard). 
	 * @param min et max, le nombre minimum et maximum entre lesquels l'IA doit choisir. 
	 * @return l'indice de la zone
	 */
	public int chooseNumber (int min,int max) {
		return Settings.RAND.nextInt(max-min+1)+min;
	}

	public String toString(){
		return "Random";
	}
}
