package client;

import java.util.Random;

import game.Settings;


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

	/**
	 * useRessourceToFeed renvoie true ou false selon le choix de l'IA pour nourrir 
	 * ses figurines avec des ressources. Le choix se fait au hasard.  
	 * @param ressourceNumber Le tableau du nombre de ressource possedee par le joueur. 
	 * @return Booleen : true si le joueur veut utiliser ses ressources pour nourrir, false sinon.
	 */
	public boolean useRessourceToFeed(int[] ressourceNumber) {
		return rand.nextInt(2)==1;
	}
	
	/**
	 * pickCard renvoie les ressources donnee pour l'achat de la carte civilisation. 
	 * Si l'IA renvoie un tableau vide, elle refuse l'achat de la carte. 
	 * @param ressourceNumber : les ressources premieres du joueur
	 * @param numberRessourceRequire : le nombre de ressource pour l'achat de la carte. 
	 * @return res : tableau des ressources donnes pour la carte. 
	 */
	public int[] pickCard(int[] ressourceNumber,int numberRessourceRequire) {
		int[] res = new int[] {0,0,0,0};
		
		if(rand.nextInt(2) == 1) {
			while(numberRessourceRequire > 0) {
				int index = -1;
				
				while(index == -1 || ressourceNumber[index] == 0) {
					index = rand.nextInt(res.length);
				}
				int number = rand.nextInt(Math.min(numberRessourceRequire, ressourceNumber[index])) + 1;
				
				ressourceNumber[index] -= number;
				res[index] += number;
				numberRessourceRequire -= number;
			}
		}
		return res; 
	}
	
	/**
	 * Renvoie un choix aleatoire sur la prise d'une carte batiment
	 * @return true ou false, aleatoirement
	 */
	public boolean pickBuilding ()
	{
		return Settings.RAND.nextBoolean();
	}
	
	public String toString(){
		return "Random";
	}
}
