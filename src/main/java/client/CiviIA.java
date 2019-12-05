package client;

import java.util.Random;

/**
 * Priorite decroissante:

 * ZoneHut: 		[6]
 * ZoneCarteCiv     [9]
 * ZoneField: 		[5]
 * ZoneHunt:		[4]
 * ZoneRessource:	[0-3]
 * ZoneTool:		[7]
 */

import game.Ressource;
import game.Settings;
import game.zone.ZoneBuilding;
import game.zone.ZoneCarteCivilisation;
import inventory.InventoryIA;
import player.PlayerIA;

public class CiviIA extends IA {

	//Random de l'IA (elle ne peux pas acceder a Settings).
	private Random rand = new Random();
	private int currentZone;

	public CiviIA(PlayerIA playerIA, InventoryIA inventoryIA) {
		super(playerIA,inventoryIA);
	}

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
		// RESET
		this.currentZone = -1;

		// ELLE PREND EN PRIORITE LA CABANE TANT QU'ELLE N'A PAS LE MAX DE FIGURINES
		if (zoneAvailableSpace[6] >= 2 && this.playerIA.getMaxFigurine() < 10) {
			this.currentZone = 6;
		}
		
		// ELLE VA EN PRIORITE A LA DEUXIEME CARTE CIVILISATION
		else if (zoneAvailableSpace[9] >= 1) this.currentZone = 9;
		
		// ELLE VA A LA CHASSE
		else if (zoneAvailableSpace[4] >= 1 && inventoryIA.getRessource(Ressource.FOOD)<10)
		{
			if (this.rand.nextInt(2) == 1) this.currentZone = 4;
		}

		// ELLE RECOLTE DES RESSOURCES
		else if (zoneAvailableSpace[0] >= 1)
		{
			if (this.rand.nextInt(2) == 1) this.currentZone = 0;
		}
		
		else if (zoneAvailableSpace[1] >= 1)
		{
			if (this.rand.nextInt(2) == 1) this.currentZone = 1;
		}

		else if (zoneAvailableSpace[2] >= 1)
		{
			if (this.rand.nextInt(2) == 1) this.currentZone = 2;
		}

		else if (zoneAvailableSpace[3] >= 1)
		{
			if (this.rand.nextInt(2) == 1) this.currentZone = 3;
		}
		
		// ELLE AUGMENTE SA NOURRITURE A LA FIN DU TOUR POUR NE PAS UTILISER LES RESSOURCES DANS LE PIRE DES CAS
		else if (zoneAvailableSpace[5] >= 1) this.currentZone = 5;
		
		// ELLE RECOLTE UN OUTIL
		else if (zoneAvailableSpace[7] >= 1)
		{
			if (this.rand.nextInt(2) == 1) this.currentZone = 7;
		}
		
		// SINON ELLE VA A LA CHASSE
		else if (this.currentZone == -1) this.currentZone = 4;

		return this.currentZone;

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
	 * chooseRessource retourne le nombre de ressource choisie par l'IA au hasard. 
	 * @param figurinesToFeed Le nombre de figurines a nourrir
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
	 * @return Booleen : true si le joueur veut utiliser ses ressources pour nourrir, false sinon.
	 */
	public boolean useRessourceToFeed() {
		return true;
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
		// ELLE VA TOUJOURS CHOISIR D'ACHETER UNE CARTE CIVILISATION SI ELLE A LES RESSOURCES QUI FAUT
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
		return res; 
	}

	/**
	 * Renvoie un choix aleatoire sur la prise d'une carte batiment
	 * @return true ou false, aleatoirement
	 */
	public boolean pickBuilding ()
	{
		//ELLE NE PREND JAMAIS LES CARTES BATIMENTS
		return false;
	}

	/**
	 * pickTools renvoie un tableau de boolean pour dire quel outils l'IA utilise.
	 * @return tableau des outils que le joueur veux utiliser
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
	 * @param listeTirage des des tires
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
	 * Si oui, elle demande lesquelles (id de la ressource).
	 * @return ressource que le joueur veux -1 pour ne pas l'utiliser
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
		return "Civilisation";
	}
}
