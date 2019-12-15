package client;

import java.util.ArrayList;
import java.util.Random;
import java.util.stream.IntStream;

import game.CarteCivilisation;
import game.Ressource;
import game.Settings;
import game.building.Building;
import game.building.MathPlus;
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

	public RessourceIA (PlayerIA playerIA, InventoryIA inventoryIA) 
	{
		super(playerIA, inventoryIA);
	}


	/**
	 * chooseZone retourne l'indice de la zone choisie par l'IA . 
	 * @param zoneAvailableSpace le tableau avec l'espace disponible de chaque zone. 
	 * @param buildings la copie des zones batiment
	 * @param cV la copie des zones carte civilisation
	 * @return l'indice de la zone
	 */
	public int chooseZone (int[] zoneAvailableSpace, Building[] buildings, CarteCivilisation[] cV)
	{
		// RESET
		this.currentZone = -1;

		//ELLE PRENDS EN PRIORITE LES ZONES DE RESSOURCES
		if (zoneAvailableSpace[0] >= 1 && this.inventoryIA.getRessource(Ressource.WOOD)<20 )
		{
			if (this.rand.nextInt(2) == 1) this.currentZone = 0;
		}
		else if (zoneAvailableSpace[2] >= 1 && this.inventoryIA.getRessource(Ressource.STONE)<12 )
		{
			if (this.rand.nextInt(2) == 1) this.currentZone = 2;
		}
		else if (zoneAvailableSpace[3] >= 1 && this.inventoryIA.getRessource(Ressource.GOLD)<8 )
		{
			if (this.rand.nextInt(2) == 1) this.currentZone = 3;
		}
		else if (zoneAvailableSpace[1] >= 1 && this.inventoryIA.getRessource(Ressource.CLAY) <16 )
		{
			if (this.rand.nextInt(2) == 1) this.currentZone = 1;
		}

		// SI LES ZONES DE RESSOURCES SONT PLEINES, ON A UNE CHANCE DE PRENDRE UNE CARTE CIVILISATION, SINON ON VA DANS LA ZONE DE CHASSE
		else
		{
			// ON REGARDE LES BUILDING
			for (int i = 0; i < buildings.length; i++)
			{
				// SI ON A ASSEZ DE RESSOURCE, ON PRENDS LA CARTE
				if (IntStream.of(this.inventoryIA.getCopyRessources()).sum() >= 4 && zoneAvailableSpace[i+12] == 1)
				{
					if (this.rand.nextInt(2) == 1) this.currentZone = i + 12;
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
		return true; // l'ia essaye de ne pas 
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
		return true;
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
		int res = -1;
		int choose;
		choose = this.rand.nextInt(2);

		if(choose == 0) {
			res = this.rand.nextInt(4);
		}
		return res;
	}
	
	/**
	 * Renvoie le tableau des ressources utilisees pour les buildingsNotImposed
	 * @param nombreRessource le nombre de ressources
	 * @param combienDeRessourcesDifferentes le nombre de ressources differentes
	 * @return un tableau des ressources
	 */
	public Ressource[] chooseRessourceBuildingNotImposed (int nombreRessource, int combienDeRessourcesDifferentes)
	{
		/*
		 * EXPLICATION DE L'ALGORITHME:
		 * On fait la liste des combinaisons possibles de taille combienDeRessourcesDifferentes
		 * Si il y a un 0 dans la combinaison, donc que le joueur ne possede pas la ressource, on vire la combinaison
		 * Si la somme est inferieur a nombreRessource, on vire la combinaison
		 * Des qu'on a trouve 1 bonne combinaison, on la garde
		 * Avec la bonne combinaison, on met au moins 1 ressource de chaque dans la reponse
		 * Ensuite, on enleve les ressources une par une dans l'ordre de la combinaison
		 */
		
		// COMBINAISONS
		int[] cpy = this.inventoryIA.getCopyRessourcesLootable();
		//System.out.println(Arrays.toString(cpy));
		ArrayList<int[]> comb = MathPlus.combinaisons(this.inventoryIA.getCopyRessourcesLootable(), combienDeRessourcesDifferentes);
		Ressource[] res = new Ressource[nombreRessource];
		//System.out.println("[res] " + Arrays.toString(res));
		int i = 0;
		// ON CHERCHE 1 COMBINAISON BONNE
		for (; i < comb.size(); i++)
		{
			int retToStart = 0;
			for (int k = 0; k < comb.get(i).length; k++)
			{
				if (comb.get(i)[k] == 0)
					retToStart = 1;
			}
			if (retToStart == 1)
				continue;
			if (IntStream.of(comb.get(i)).sum() < nombreRessource)
				continue;
			break;
		}
		int[] tabAns = comb.get(i);
		Ressource[] correspondances = new Ressource[combienDeRessourcesDifferentes];
		
		// ON INITIALISE correspondances
		int eIndex = 0;
		int[] exceptionIndex = new int[combienDeRessourcesDifferentes]; // Contient les indices des correspondances dans cpy
		for (int k = 0; k < exceptionIndex.length; k++)
		{
			exceptionIndex[k] = -1;
		}

		for (int k = 0, r = 0 ; k < tabAns.length; k++)
		{
			for (int g = 0; g < cpy.length; g++)
			{
				if (tabAns[k] == cpy[g])
				{
					boolean breakNow = false;
					for (int n = 0; n < exceptionIndex.length; n++)
					{
						if (exceptionIndex[n] == g)
						{
							breakNow = true;
							break;
						}
					}
					if (breakNow == true)
						continue;
					exceptionIndex[eIndex] = g;
					eIndex++;
					cpy[g]--;
					correspondances[r] = Ressource.indexToRessource(g);
					r++;
					break;
				}
			}
		}
		
		// ON MET 1 RESSOURCE DE CHAQUE
		for (int k = 0; k < combienDeRessourcesDifferentes; k++)
		{
			res[k] = correspondances[k];
		}
		// ON FINIT
		for (int k = correspondances.length, g = 0; k < nombreRessource; k++)
		{
			//System.out.println("\t[cpy1] " + Arrays.toString(cpy) + " [idx] " + Arrays.toString(exceptionIndex) + "  [l,h] " + nombreRessource + " " + combienDeRessourcesDifferentes);
			//System.out.println("\t[!!!] " + Arrays.toString(tabAns));
			// On check pour chaque ressource si elle est dispo
			for (int idx : exceptionIndex)
			{
				// Si il nous reste une ressource
				if (cpy[idx] > 0)
				{
					// on assigne l'indice
					g = idx;
					// on retire la ressource de la copie
					cpy[g]--;
					//System.out.println("\t\t[cpy2] " + Arrays.toString(cpy));
					break;
				}
			}
			// on ajoute la ressource a notre reponse
			res[k] = Ressource.indexToRessource(g);
			
		}
		//System.out.println("[res] " + Arrays.toString(res));
		return res;
	}
	
	/**
	 * Renvoie le tableau des ressources utilisees pour les buildingsChoosed
	 * @return un tableau des ressources
	 */
	public Ressource[] chooseRessourceBuildingChoosed ()
	{
		/**
		 * Ressources choisies dans l'ordre: GOLD - STONE - CLAY - WOOD
		 */
		int[] inv = this.inventoryIA.getCopyRessourcesLootable();
		// res.length = [1; max <= 7]
		int sum = IntStream.of(inv).sum();
		int range = 0;
		if (sum > 7)
			range = 7;
		else
			range = sum;
		Ressource[] res = new Ressource[this.rand.nextInt(range + 1)];
		for (int i = 0, invIndex = 3; i < res.length;)
		{
			if (inv[invIndex] == 0)
			{
				invIndex--;
				if (invIndex == -1)
					throw new Error("Impossible de miser");
			}
			else
			{
				res[i] = Ressource.indexToRessource(invIndex);
				inv[invIndex]--;
				i++;
			}
		}
		return res;
	}

	public String toString () {return "Ressource";}

}
