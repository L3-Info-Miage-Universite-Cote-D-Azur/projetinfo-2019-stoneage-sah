package client;

import java.util.Random;
import java.util.stream.IntStream;

import game.Ressource;
import game.Settings;
import game.zone.*;
import inventory.InventoryIA;
import player.PlayerIA;


/**
 * Priorite decroissante:
 * ZoneRessource:	[0-3]
 * ZoneCarteCiv:	[8-11]
 * ZoneHunt:		[4]
 */

public class RessourceIA extends IA 
{

	//Random de l'IA (elle ne peux pas acceder a Settings).
	private Random rand = new Random();
	private int currentZone;
	private int choosedRessource;

	public RessourceIA (PlayerIA playerIA, InventoryIA inventoryIA) 
	{
		super(playerIA, inventoryIA);
		this.choosedRessource = 4;
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
		//pour eviter les parti infinies
		if(inventoryIA.availableResourceToFeed()>150) {
			// CHECK DES BUILDINGS
			for (int i = 0; i < buildings.length; i++)
			{
				// SI Y'A DE LA PLACE
				if (zoneAvailableSpace[i+12] >= 1)
				{
					this.currentZone = i + 12;
					return currentZone;
				}
			}
		}
		// RESET
		this.currentZone = -1;

		//ELLE PRENDS EN PRIORITE LES ZONES DE RESSOURCES
		if (zoneAvailableSpace[0] >= 1)
		{
			if (this.rand.nextInt(2) == 1) this.currentZone = 0;
		}
		else if (zoneAvailableSpace[2] >= 1)
		{
			if (this.rand.nextInt(2) == 1) this.currentZone = 2;
		}
		else if (zoneAvailableSpace[3] >= 1)
		{
			if (this.rand.nextInt(2) == 1) this.currentZone = 3;
		}
		else if (zoneAvailableSpace[1] >= 1)
		{
			if (this.rand.nextInt(2) == 1) this.currentZone = 1;
		}

		// SI LES ZONES DE RESSOURCES SONT PLEINES, ON A UNE CHANCE DE PRENDRE UNE CARTE CIVILISATION, SINON ON VA DANS LA ZONE DE CHASSE
		else
		{
			// ON REGARDE LES CARTES CIVILISATION
			for (int i = 0; i < cV.length; i++)
			{
				// SI ON A ASSEZ DE RESSOURCE, UNE CHANCE SUR 4 DE PRENDRE LA CARTE
				if (IntStream.of(this.inventoryIA.getCopyRessources()).sum() >= 4 - i && zoneAvailableSpace[i] == 1)
				{
					if (this.rand.nextInt(5) == 1) this.currentZone = i + 8;
					break;
				}
			}
		}

		// A LA FIN, IL RESTE LA CHASSE
		if (this.currentZone == -1)
			this.currentZone = 4;

		return this.currentZone;
	}

	/**
	 * chooseNumber retourne le nombre de figurines choisie par l'IA. 
	 * @param min : le nombre de figurines minimum.
	 * @param max : le nombre de figurines maximum. 
	 * @return le nombre de figurines que l'IA place.
	 */
	public int chooseNumber (int min, int max) 
	{
		//IL RENVOIE UN NOMBRE ALEATOIRE
		return this.rand.nextInt(max - min + 1) + min;
	}

	/**
	 * chooseRessource retourne le nombre de ressources choisie par l'IA au hasard. 
	 * @param figurinesToFeed Le nombre de figurines a nourrir
	 * @return le tableau des ressources sacrifiee.
	 */
	public int[] chooseRessource(int figurinesToFeed) {
		int ressource;
		int index = 4;
		// GOLD - STONE - CLAY - WOOD
		do {
			index--;
			ressource = inventoryIA.getRessource(Ressource.indexToRessource(index));
		}while(ressource==0);

		int nb = this.rand.nextInt(Math.min(ressource, figurinesToFeed))+1;

		int[] res = new int[] {index,nb};
		return res;
	}

	/**
	 * useRessourceToFeed renvoie true ou false selon le choix de l'IA pour nourrir 
	 * ses figurines avec des ressources. Le choix se fait au hasard.  
	 * @return Booleen : true si le joueur veut utiliser ses ressources pour nourrir, false sinon.
	 */
	public boolean useRessourceToFeed()
	{
		// LE JOUEUR NOURRI LES FIGURINES UNE FOIS SUR DEUX
		if (this.rand.nextInt(3) == 1) {
			return true;
		}
		return false;
	}

	/**
	 * pickCard renvoie les ressources donnee pour l'achat de la carte civilisation. 
	 * Si l'IA renvoie un tableau vide, elle refuse l'achat de la carte. 
	 * @param numberRessourceRequire : le nombre de ressource pour l'achat de la carte. 
	 * @return res : tableau des ressources donnes pour la carte. 
	 */
	public int[] pickCard(int numberRessourceRequire) 
	{    
		int[] res = new int[] {0,0,0,0};
		int[] ressourceNumber = new int[4];
		for(int i = 0; i<4; i++) {
			ressourceNumber[i] = inventoryIA.getRessource(Ressource.indexToRessource(i));
		}
		if(this.rand.nextInt(2) == 1) {
			while(numberRessourceRequire > 0) {
				int index = 0;
				// CHOISI UNE RESSOURCE ALEATOIRE
				while(index == -1 || ressourceNumber[index] == 0) {
					index = rand.nextInt(res.length);
				}
				// CB DE RESSOURCE
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
		// IL NE PASSE JAMAIS SUR LES BATIMENTS
		return false;
	}

	/**
	 * pickTools renvoie un tableau de boolean pour dire quel outils l'IA utilise.
	 * @return tableau des outils que le joueur veux utiliser
	 */
	public boolean[] pickTools() {
		int[] toolsToUse = inventoryIA.getTools().getTools();
		boolean[] useTools = inventoryIA.getTools().getToolsUsed();
		boolean[] res = new boolean[toolsToUse.length];

		for (int i = 0; i < useTools.length; i++)
		{
			if (useTools[i] == false && toolsToUse[i]>0)
			{
				res[i] = true;
			}
			else
			{
				res[i] = false;
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
	public int chooseTirage(int[] listeTirage, boolean[] alreadyChoose) 
	{
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
	// GOLD - STONE - CLAY - WOOD
	public int useRessourceCard() {
		this.choosedRessource--;
		return this.choosedRessource;
	}

	public String toString () {return "Ressource";}

}
