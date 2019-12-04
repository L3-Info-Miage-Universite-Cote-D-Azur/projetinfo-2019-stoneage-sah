package client;

import java.util.Random;

import game.Ressource;
import game.Settings;
import game.zone.ZoneBuilding;
import game.zone.ZoneCarteCivilisation;
import inventory.InventoryIA;
import player.PlayerIA;


/**
 * RandomIA correspond a l'IA comportant une strategie basee sur le hasard. 
 * @author Mentra20
 *
 */

public class RandomIA extends IA {

	public RandomIA(PlayerIA playerIA, InventoryIA inventoryIA) {
		super(playerIA,inventoryIA);
	}

	//Random de l'IA (elle ne peux pas acceder a Settings).
	private Random rand = new Random();

    /**
	 * chooseZone retourne l'indice de la zone choisie par l'IA . 
	 * @param zoneAvailableSpace le tableau avec l'espace disponible de chaque zone. 
	 * @param zoneName le tableau des noms des zones.
	 * @param buildings la copie des zones batiment
	 * @param cV la copie des zones carte civilisation
	 * @return l'indice de la zone
	 */
    public int chooseZone (int[] zoneAvailableSpace, String[] zoneName, ZoneBuilding[] buildings, ZoneCarteCivilisation[] cV)
    {
		return rand.nextInt(zoneAvailableSpace.length);
	}

	/**
	 * chooseNumber retourne le nombre de figurines choisie par l'IA. 
	 * @param min : le nombre de figurines minimum.
	 * @param max : le nombre de figurines maximum. 
	 * @return l'indice de la zone
	 */
	public int chooseNumber (int min,int max) {
		return rand.nextInt(max-min+1)+min;
	}

	/**
	 * chooseNumber retourne le nombre de ressource choisie par l'IA au hasard. 
	 * @param Le nombre de figurines a nourrir
	 * @return l'indice de la zone
	 */
	public int[] chooseRessource(int figurinesToFeed) {
		int ressource;
		int index;
		do {
			index = rand.nextInt(4);
			ressource = inventoryIA.getRessource(Ressource.indexToRessource(index));
		}while(ressource == 0);

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
	public boolean useRessourceToFeed() {
		return rand.nextInt(2)==1;
	}

	/**
	 * pickCard renvoie les ressources donnee pour l'achat de la carte civilisation. 
	 * Si l'IA renvoie un tableau vide, elle refuse l'achat de la carte. 
	 * @param numberRessourceRequire : le nombre de ressource pour l'achat de la carte. 
	 * @return res : tableau des ressources donnes pour la carte. 
	 */
	public int[] pickCard(int numberRessourceRequire) {

		int[] res = new int[] {0,0,0,0};
		
		int[] ressourceNumber = new int[4];
		for(int i = 0; i<4; i++) {
			ressourceNumber[i] = inventoryIA.getRessource(Ressource.indexToRessource(i));
		}

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

	/**
	 * pickTools renvoie un tableau de boolean pour dire quel outils l'IA utilise.
	 * @return
	 */
	public boolean[] pickTools() {
		int[] toolsToUse = inventoryIA.getTools().getTools();
		boolean[] useTools = inventoryIA.getTools().getToolsUsed();
		int usableTools = 0;
		
		for(int i = 0;i < toolsToUse.length;i++){
			if(((i>=3) || !useTools[i]) && toolsToUse[i] > 0) usableTools+=1;
		}

		//cas ou il n'y a pas d'outil a utiliser
		if (usableTools == 0) {
			return new boolean[toolsToUse.length];
		}

		int numberTools = Settings.RAND.nextInt(usableTools+1);
		boolean[] res = new boolean[toolsToUse.length];

		for(int i = numberTools; 0 < i; i --){
			boolean hasChoose = false;
			while(!hasChoose){
				int choose = Settings.RAND.nextInt(toolsToUse.length);
				if((choose>=3 || !useTools[choose]) && toolsToUse[choose] > 0 && res[choose] == false){
					res[choose] = true;
					hasChoose = true;
				}
			}
		}
		return res;
	}
	
	/**
	 * Phase de tirage
	 * @param listeTirage des dée tirée
	 * @param alreadyChoose si un autre joueur l'a deja choisi ou non
	 * @return l'index de ce que veut le joueur dans le tirage
	 */
	@Override
	public int chooseTirage(int[] listeTirage, boolean[] alreadyChoose) {
		int choose;
		do {
			choose = Settings.RAND.nextInt(alreadyChoose.length);
		}while(alreadyChoose[choose]);
			
		return choose;
	}
	
	/**
	 * useRessourceCard demande a l'ia si elle veut utiliser ses ressources au choix. 
	 * Si oui, elle demande lesquelles.
	 * @return
	 */
	public int useRessourceCard() {
		int res = -1;
		int choose;
		choose = this.rand.nextInt(2);
		
		if(choose == 0) {
			res = this.rand.nextInt(4);
		}
		return res;
	}
	

	public String toString(){
		return "Random";
	}

}
