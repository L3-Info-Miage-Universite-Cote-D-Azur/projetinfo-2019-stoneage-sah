package client;

import java.util.Random;


/**
 * RandomIA correspond a l'IA comportant une strategie basee sur le hasard. 
 * @author Mentra20
 *
 */

public class RandomIA implements IA {
	
	//Random de l'IA (elle ne peux pas acceder a Settings).
	private Random rand = new Random();
	
	/**
	 * chooseZone retourne l'indice de la zone choisie par l'IA (ici au hasard). 
	 * @param zoneAvailableSpace le tableau avec l'espace disponible de chaque zone. 
	 * @param zoneName le tableau des noms des zones.
	 * @return l'indice de la zone
	 */
	public int chooseZone (int[] zoneAvailableSpace,String[] zoneName) {
		return rand.nextInt(zoneAvailableSpace.length);
	}

	/**
	 * chooseNumber retourne le nombre de figurines choisie par l'IA (ici au hasard). 
	 * @param min et max, le nombre minimum et maximum entre lesquels l'IA doit choisir. 
	 * @return l'indice de la zone
	 */
	public int chooseNumber (int min,int max) {
		return rand.nextInt(max-min+1)+min;
	}
	
	/**
	 * chooseNumber retourne le nombre de ressource choisie par l'IA au hasard. 
	 * @param Le nombre de figurines a nourrir
	 * @param Un tableau contenant le nombre de ressource que l'IA possede par index.
	 * @param Un tableau contenant le nom des ressources que l'IA possede par index.
	 * @return l'indice de la zone
	 */
	public int[] chooseRessource(int figurinesToFeed, int[] RessourceNumber, String[] ressourceName) {
        int ressource;
        int index;
        do {
            index = rand.nextInt(RessourceNumber.length);
            ressource = RessourceNumber[index];
        }while(ressource==0);
        
        int nb = rand.nextInt(Math.min(ressource, figurinesToFeed))+1;
        
        int[] res = new int[] {index,nb};
        return res;
    }

	public String toString(){
		return "Random";
	}
}

