package client;

import game.CarteCivilisation;
import game.Ressource;
import game.building.Building;
import inventory.InventoryIA;
import player.PlayerIA;
import printer.Printer;

public class CarteCivilisationWoodIA extends IA {

	private int FigurineForFeed; //le nombre de figurine minimale que le joueur dois mettre sur la chasse
	private int nbPeasantToHave = 3;//Le nombre de paysans a avoir pour continuer a obtenir des marqueurs nourritures.
	private int currentZone;//La zone choisie precedemment.
	private int nbTurnWithoutCC = 0;//Le nombre de tour sans avoir pris de carte civilisation.

	public CarteCivilisationWoodIA(PlayerIA playerIA, InventoryIA inventoryIA) {
		super(playerIA, inventoryIA);
	}

	/* SOUS FONCTIONS */
	/**
	 * Retourne le nombre de figurines necessaire pour obtenir le nombre de nourriture en parametre selon une strategie choisie. 
	 * @param foodNeedToFeed : le nombre de nourriture voulu. 
	 * @return le nombre de figurines necessaire.
	 */
	private void nbFiguresForFeed(int foodNeedToFeed){
		FigurineForFeed = 0;

		if(foodNeedToFeed > 0){//Si il y a besoin d'obtenir de la nourriture.
			FigurineForFeed = (int) Math.ceil(foodNeedToFeed * (float) 2/3);
		}
	}
	/**
	 * wanToPickCard renvoie true ou false selon si l'IA veut la carte ou non selon ses criteres.
	 * @param cc : la carte civilisation a prendre ou non.
	 * @return true si on prend la carte, false sinon
	 */
	private boolean wantToPickCard(CarteCivilisation cc){
		//L'IA n'aura jamais d'outils ni de batiments.
		//Si la carte n'est pas 'constructeur de batiment' ou 'fabricant d'outils'.
		if(cc.getTypeDownPart() != 10 && cc.getTypeDownPart() != 9) {
			return true;
		}
		
		//Si cela fait 4 tour que l'IA n'a pas pris de carte civilisation, qu'importe son genre on la veut.
		if(nbTurnWithoutCC >= 4) {
			nbTurnWithoutCC = 0; //On le reinitialise.
			return true;
		}
		return false;
	}

	/**
	 * hasManyPeasant renvoie vrai ou faux selon si l'IA a assez de paysans pour sa strategie.
	 * @return true si le nombre de paysans est satisfaisant , false sinon.
	 */
	private boolean hasManyPeasant() {
		int sum = 0;
		for(CarteCivilisation cc : inventoryIA.getCardCivilisation()) {
			if(cc.getTypeDownPart() == 8) {
				sum += cc.getNumberDownPart();
			}
		}
		return sum >= nbPeasantToHave;
	}


	/* FONCTION DE L'IA */

	@Override
	/**
	 * chooseZone retourne l'indice de la zone choisie par l'IA . 
	 * @param zoneAvailableSpace le tableau avec l'espace disponible de chaque zone. 
	 * @param zoneName le tableau des noms des zones.
	 * @param buildings la copie des zones batiment
	 * @param cV la copie des zones carte civilisation
	 * @return l'indice de la zone
	 */
	public int chooseZone(int[] zoneAvailableSpace, Building[] buildings, CarteCivilisation[] cV) {
		//calcule du nombre de figurine a garder pour la nouriture
		int foodNeedToFeed = playerIA.getMaxFigurine() - inventoryIA.getRessource(Ressource.FIELD) - inventoryIA.getRessource(Ressource.FOOD);
		nbFiguresForFeed(foodNeedToFeed);

		// Si il lui reste des figurines en plus de celles pour nourrir.
		if(playerIA.getCurrentFigurine()-FigurineForFeed>=1){
			//champ si utile et dispo.
			if(inventoryIA.getRessource(Ressource.FIELD) < playerIA.getMaxFigurine() && zoneAvailableSpace[5] > 0) {
				currentZone = 5;
				return 5;
			}
			//reproduction si utile et dispo.
			if(playerIA.getMaxFigurine() != 10 && playerIA.getCurrentFigurine()-FigurineForFeed >=2 && zoneAvailableSpace[6] > 0) {
				currentZone = 6;
				return 6;
			}

			//DEBUT DE PARTIE.
			if(playerIA.getMaxFigurine() < 8){
				//carteCivilisation cout 1
				if(inventoryIA.getRessource(Ressource.WOOD) >= 1 && zoneAvailableSpace[8] > 0 && wantToPickCard(cV[0])) {
					currentZone = 8;
					return 8;
				}
				//carteCivilisation cout 2
				if(inventoryIA.getRessource(Ressource.WOOD) >= 2 && zoneAvailableSpace[9] > 0 && wantToPickCard(cV[1])) {
					currentZone = 9;
					return 9;
				}
			}
			//PARTIE AVANCEE.
			if(playerIA.getMaxFigurine() >= 8) {
				//carteCivilisation cout 1
				if(inventoryIA.getRessource(Ressource.WOOD) >= 1 && zoneAvailableSpace[8] > 0 && wantToPickCard(cV[0])) {
					currentZone = 8;
					nbTurnWithoutCC = 0;//On reinitialise.
					return 8;
				}
				//carteCivilisation cout 2
				if(inventoryIA.getRessource(Ressource.WOOD) >= 2 && zoneAvailableSpace[9] > 0 && wantToPickCard(cV[1])) {
					currentZone = 9;
					nbTurnWithoutCC = 0;//On reinitialise.
					return 9;
				}
				//carteCivilisation cout 3
				if(inventoryIA.getRessource(Ressource.WOOD) >= 3 && zoneAvailableSpace[10] > 0 && wantToPickCard(cV[2])) {
					currentZone = 10;
					nbTurnWithoutCC = 0;//On reinitialise.
					return 10;
				}
				//carteCivilisation cout 4
				if(inventoryIA.getRessource(Ressource.WOOD) >= 4 && zoneAvailableSpace[11] > 0 && wantToPickCard(cV[3])) {
					currentZone = 11;
					nbTurnWithoutCC = 0;//On reinitialise.
					return 11;
				}
				
				//On ne regarde pas pour le debut de partie.
				nbTurnWithoutCC++; //On a selectionner aucune carte civilisation.

				//Champ si bcp de carteCivilisation paysans.
				if(hasManyPeasant() && zoneAvailableSpace[5] > 0) {
					currentZone = 5;
					return 5;
				}
			}

			//recolte de bois
			if(zoneAvailableSpace[0] > 0) {
				currentZone = 0;
				return 0;
			}
			//autre zone sinon par ordre de diviseur croissant.
			if(zoneAvailableSpace[1] > 0) {
				currentZone = 1;
				return 1;
			}
			if(zoneAvailableSpace[2] > 0) {
				currentZone = 2;
				return 2;
			}
			if(zoneAvailableSpace[3] > 0) {
				currentZone = 3;
				return 3;
			}
		}
		//si plus de figurines dispo pour mettre ailleurs (ou pas possible).
		currentZone = 4;
		return 4;//LA CHASSE.

		//REGARDE SON BOIS.
		//VA EN RECOLTER SI <4.
		// si pas dispo ... pierre , argile, or. 
		//ESSAYE D'AVOIR PLUS DE FIGURINES SI < 10.
		//ESSAYE DE PRENDRE LES CARTES CIV DU MOINS CHER AU PLUS.
		//NE PREND PAS CARTE 'CONSTRUCTEUR BATIMENT' ni 'PAYSANS', ni 'FABRIQUANT OUTILS' sauf si focus champs et outils 
		//VA CHERCHER NOURRITURES ou peut etre champs
	}



	/**
	 * chooseNumber retourne le nombre de figurines choisie par l'IA. 
	 * @param min : le nombre de figurines minimum.
	 * @param max : le nombre de figurines maximum. 
	 * @return le nombre de figurines que l'IA place.
	 */
	@Override
	public int chooseNumber(int min, int max) {
		//Si la zone est une zone a un joueur.
		if(currentZone == 5 || currentZone == 6 || (currentZone >= 8 && currentZone <= 11)) {
			return max;
		}
		if(currentZone < 4) {//Si la zone est une zone de ressource premiere.
			return Math.min((playerIA.getCurrentFigurine() - FigurineForFeed),max);
		}
		if(currentZone == 4) {//Si la zone est la chasse.
			if(FigurineForFeed==0) return 1;
			return Math.min(FigurineForFeed,max);
		}
		else {
			Printer.getPrinter().println("ERREUR");
			return -1;
		}
	}

	/**
	 * chooseNumber retourne le nombre de res choisie par l'IA. 
	 * @param figurinesToFeed Le nombre de figurines a nourrir
	 * @return le tableau des ressources sacrifiee.
	 */
	@Override
	public int[] chooseRessource(int figurinesToFeed) {
		for(int i = 3; i >= 0; i--) {//On essaye de nourrir les figurines avec les ressources les plus couteuse. 
			int nbRessource = inventoryIA.getRessource(Ressource.indexToRessource(i));
			if(nbRessource != 0) {
				return new int[] {i,Math.min(nbRessource, figurinesToFeed)};
			}
		}
		Printer.getPrinter().println("ERROR");
		return null;
	}

	/**
	 * useRessourceToFeed renvoie true ou false selon le choix de l'IA pour nourrir 
	 * ses figurines avec des ressources. Le choix se fait au hasard.  
	 * @return Booleen : true si le joueur veut utiliser ses ressources pour nourrir, false sinon.
	 */
	@Override
	public boolean useRessourceToFeed() {
		return true;//L'IA essaye toujours de nourrir ses figurines.
	}

	/**
	 * pickCard renvoie les ressources donnee pour l'achat de la carte civilisation. 
	 * Si l'IA renvoie un tableau vide, elle refuse l'achat de la carte. 
	 * @param numberRessourceRequire : le nombre de ressource pour l'achat de la carte. 
	 * @return res : tableau des ressources donnes pour la carte. 
	 */
	@Override
	public int[] pickCard(int numberRessourceRequire) {
		int[] res = new int[] {0,0,0,0};
		int[] copyRessource = inventoryIA.getCopyRessources();


		for(int i = 3; i >= 0; i--) {
			if(numberRessourceRequire == 0) break;
			int nb = Math.min(copyRessource[i], numberRessourceRequire);

			res[i] = nb;
			numberRessourceRequire -= nb;
			copyRessource[i] -= nb;
		}
		return res;
	}

	/**
	 * Renvoie un choix sur la prise d'une carte batiment
	 * @return true ou false
	 */
	@Override
	public boolean pickBuilding() {
		return false;//L'IA ne prend jamais de batiments.
	}

	/**
	 * pickTools renvoie un tableau de boolean pour dire quel outils l'IA utilise.
	 * @return tableau des outils que le joueur veux utiliser
	 */
	@Override
	public boolean[] pickTools() {
		int[] toolsToUse = inventoryIA.getTools().getTools();
		boolean[] useTools = inventoryIA.getTools().getToolsUsed();

		boolean[] res = new boolean[toolsToUse.length];

		/* Il faut savoir que cette IA n'est pas cense avoir d'outils donc qu'importe sur quoi elle les utilisent. 
		 * Il y a beaucoup de chance que cela soit du bois si jamais cela arrive.
		 */
		for(int i = 0; i < toolsToUse.length; i++) {
			if((i>=3 || !useTools[i]) && toolsToUse[i] > 0 && res[i] == false){
				//L'IA utilise tout ses outils immediatement (elle ne les utilisent que sur la zone de ressource premiere).
				res[i] = true;
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
		//L'IA veut d'abord un marqueur de nourriture, des outils , du bois puis le reste.
		int[] preference = new int[] {6,5,1,4,3,2};
		
		for(int pref : preference) {
			for(int i = 0; i < listeTirage.length; i++) {
				if(!alreadyChoose[i] && listeTirage[i] == pref) {
					return i;
				}
			}
		}
		Printer.getPrinter().println("ERROR.");
		return -1;
	}
	
	/**
	 * useRessourceCard demande a l'ia si elle veut utiliser ses ressources au choix. 
	 * Si oui, elle demande lesquelles (id de la ressource).
	 * @return ressource que le joueur veux -1 pour ne pas l'utiliser
	 */
	@Override
	public int useRessourceCard() {
		return 0;//L'IA utilise directement en demandant du bois.
	}
	
	/**
	 * Renvoie le tableau des ressources utilisees pour les buildingsNotImposed
	 * @param nombreRessource le nombre de ressources
	 * @param combienDeRessourcesDifferentes le nombre de ressources differentes
	 * @return un tableau des ressources
	 */
	public  Ressource[] chooseRessourceBuildingNotImposed (int nombreRessource, int combienDeRessourcesDifferentes) {
		return new Ressource[] {};
		// L'ia ne prend jamais de batiments.
	}
	
	/**
	 * Renvoie le tableau des ressources utilisees pour les buildingsChoosed
	 * @return un tableau des ressources
	 */
	public  Ressource[] chooseRessourceBuildingChoosed () {
		return new Ressource[] {};
		// L'ia ne prend jamais de batiments.
	}

	@Override
	public String toString() {
		return "CarteCivilisation_Version_Bois";
	}

}
