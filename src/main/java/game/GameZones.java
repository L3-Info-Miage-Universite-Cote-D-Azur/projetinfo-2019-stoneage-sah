package game;


import java.util.Arrays;

import game.zone.Zone;
import game.zone.ZoneBuilding;
import game.zone.ZoneCarteCivilisation;
import game.zone.ZoneField;
import game.zone.ZoneHunt;
import game.zone.ZoneHut;
import game.zone.ZoneRessource;
import game.zone.ZoneTool;
import inventory.Inventory;
import player.Player;
import printer.Printer;

/**
 * GameZones represente le plateau cote zone du jeu.
 * @author Mentra20
 *
 */
public class GameZones {

	/* FIELD */
	private Zone[] zones;//Tableau des zones.
	private CarteCivilisationManager cardManager; //gestion carte civilisation
	int numberPlayer;
	int numberSpecialZoneOccuped; //utile pour les regle a 2 ou 3 joueur
	private final Dice dice;

	/* CONSTRUCTOR */
	public GameZones(int numberPlayer, Dice dice) {
		this.numberPlayer=numberPlayer;
		this.dice = dice;
		initZone();
		numberSpecialZoneOccuped = 0;
	}

	/* GETTERS */
	public CarteCivilisationManager getCardManager() { return cardManager; }
	public Zone[] getZones() {return zones;}

	/**
	 * remet la valeur numberSpecialZoneOccuped a 0.
	 */
	public void resetNumberSpecialZoneOccuped() { numberSpecialZoneOccuped = 0; }
	/**
	 * gestion du placement du joueur dans une zone (selection du joueur + placement)
	 * @param player le joueur qui doit placer les figurine
	 * @return true : n'a plus de figurine a placer ; ; 
	 * false : a encore des figurine a placer
	 */
	public boolean playerPlaceFigurine(Player player) {
		Printer.getPrinter().println("\nC'est le tour de " + player.getName() + ".");
		int zoneIndex = zoneChoose(player);//On recupere l'indice de la zone
		int number = numberChoose(player,zones[zoneIndex]);//On recupere le nombre de figurines
		zones[zoneIndex].placeFigurine(number,player);//On place les figurines dans la zone. 
		if(zoneIndex>=5 && zoneIndex<=7) numberSpecialZoneOccuped+=1; //si c'est une zone speciale
		if(player.getCurrentFigurine() == 0) {//Si le joueur n'a plus de figurine
			return true;
		}
		return false;
	}


	/**
	 * Phase de recolte, le joueur selectionner recupere toutes ces figurine et recois les effet de la zone en question
	 * @param player le joueur qui recupere
	 * @param inventory l'inventaire du joueur en question
	 */
	public void playerHarvest(Player player, Inventory inventory,GamePlayers gamePlayers){
		Printer.getPrinter().println("\nC'est au tour de "+player.getName()+" :");
		int action;
		for(int i = 0; i < zones.length; i++) 
		{
			if(zones[i].howManyPlayerFigurine(player) != 0) //Si le joueur avait des figurines dans la zone. 
			{
				action = zones[i].playerRecoveryFigurine(player,inventory);//Il recupere ses figurines et les ressources.
				if (action>0 && zones[i] instanceof ZoneCarteCivilisation) {
					if (action==1) {
						CarteCivilisation cc = cardManager.getRandomCivilisationCard();
						Printer.getPrinter().println("Le joueur "+player.getName()+" obtient "+cc.getName()+" grace a la carte civilisation.");
						if (cc != null) inventory.addCardCivilisation(cc); //plus de carte dans le jeu
					}
					//tirage pour chaque joueur
					else if(action == 2) 
					{	
						Printer.getPrinter().println();
						Printer.getPrinter().println("Phase de Tirage");
						tirage(gamePlayers,player);
						Printer.getPrinter().println();
					}
				}
			}
		}
	}


	/**
	 * Initie le tableau zones. 
	 */
	public void initZone(){
		zones = new Zone[Settings.NB_ZONES-(4-numberPlayer)];
		ZoneCarteCivilisation[] zoneCarteCivilisation = new ZoneCarteCivilisation[4];

		//Les zones du jeu. 
		zones[0] = new ZoneRessource("Foret",Ressource.WOOD,Settings.MAX_ZONERESSOURCE_SPACE, dice, numberPlayer);
		zones[1] = new ZoneRessource("Glaisiere",Ressource.CLAY,Settings.MAX_ZONERESSOURCE_SPACE, dice, numberPlayer);
		zones[2] = new ZoneRessource("Carriere",Ressource.STONE,Settings.MAX_ZONERESSOURCE_SPACE, dice, numberPlayer);
		zones[3] = new ZoneRessource("Riviere",Ressource.GOLD,Settings.MAX_ZONERESSOURCE_SPACE, dice, numberPlayer);
		//La zone de chasse a : nombre de joueur x le nombre de figurines maximum d'espace. 
		zones[4] = new ZoneHunt("Chasse", Ressource.FOOD,numberPlayer * Settings.MAX_FIGURINE, dice);
		//zone speciale
		zones[5] = new ZoneField("Champs", Ressource.FIELD);
		zones[6] = new ZoneHut("Cabane de reproduction");
		zones[7] = new ZoneTool("Le Fabricant D'outils");

		//gestion des carte civilisation
		ZoneCarteCivilisation zcc;
		for(int i=0;i<4;i++) {
			zcc=new ZoneCarteCivilisation("Carte Civilisation "+ (i+1), i+1, dice);
			zoneCarteCivilisation[i]=zcc;
			zones[8+i]=zcc;
		}
		cardManager = new CarteCivilisationManager(zoneCarteCivilisation);
		for(int i=0;i<numberPlayer;i++) {
			zones[i+12] = new ZoneBuilding("Tuile Batiment "+ (i + 1));
		}
	}


	/**
	 * mise a jour des carte dans les zone carteCivilisation
	 */
	public void organizeCard() {
		cardManager.organizeCard();
	}


	/* END? */
	/**
	 * Renvoie true ou false, si la condition de victoire a ete effectue. 
	 * @return true si le jeu doit se terminer, false sinon
	 */
	public boolean isEnd(){
		// DANS LES CARTE CIVILISATION
		if(cardManager.isEmpty()) {
			Printer.getPrinter().println("\n--- PARTIE TERMINEE : Il n'y a plus de carte civilisation ---");
			return true;
		}

		// DANS LES ZONEBUILDING
		for (int i = 12; i < zones.length; i++)
		{
			// SI UNE ZONE EST VIDE
			if (((ZoneBuilding)zones[i]).isDeckEmpty() == true) {
				Printer.getPrinter().println("\n---- PARTIE TERMINEE : une des piles de batiment est vide ----");
				return true;
			}
		}
		return false;
	}


	/*==================================================
	 * Traitement choix de l'IA;
	 * ==================================================*/


	/**
	 * zoneChoose retourne la zone choisie par l'IA.
	 * @param player le joueur concerne
	 * @return l'indice de la zone choisie dans le tableau zones de Game.
	 */
	public int zoneChoose(Player player) {

		int choose = -1;
		boolean ok = false;
		//Variables utiles pour les fonctions de l'IA. 
		int[] zoneAvailableSpace = new int[zones.length];
		String[] zoneName = new String[zones.length];

		//Initialisation des variables pour l'IA
		for(int i = 0; i < zones.length; i++){
			
			if(ableToChooseZone(i,player)) zoneAvailableSpace[i] = zones[i].getAvailableSpace(); 
			else zoneAvailableSpace[i] = 0; //si le joueur ne peut pas placer dans la zone pour quelconc resaions
			zoneName[i] = zones[i].getName();
		}

		while(!ok){
			//copy
			ZoneBuilding[] buildingsCpy = new ZoneBuilding[numberPlayer];
			for (int i = 0; i < buildingsCpy.length; i++)
			{
				buildingsCpy[i] = new ZoneBuilding((ZoneBuilding)zones[i + 12]);
			}
			ZoneCarteCivilisation[] cVCpy = new ZoneCarteCivilisation[4];
			for (int i = 0; i < 4; i++)
			{
				cVCpy[i] = new ZoneCarteCivilisation((ZoneCarteCivilisation)zones[i + 8]);
			}
			
			choose = player.getIA().chooseZone(zoneAvailableSpace,zoneName, buildingsCpy, cVCpy);

			if((choose >= 0) && (choose<zones.length) && ableToChooseZone(choose, player)) ok=true;
			else Printer.getPrinter().println("/!\\ Zone "+zones[choose].getName()+" : Choix incorrecte ou zone pleine, veuillez reessayer./!\\");
		}
		return choose;
	}

	/**
	 * numberChoose retourne le nombre de figurines a posee choisie par l'IA.
	 * @param player le joueur concerne
	 * @param zone, la zone concernee
	 * @return Le nombre de figurines que l'IA veut poser. 
	 */
	public int numberChoose(Player player,Zone zone){
		int choose = -1;
		boolean ok = false;

		while(!ok){
			int max = Math.min(player.getCurrentFigurine(),zone.getAvailableSpace());
			int min = zone.getMinimalFigurineRequierement();

			//Pas besoin de demander a l'IA combien de figurines elle veut poser. 
			if(max == min) {
				return max; 
			}

			choose = player.getIA().chooseNumber(min,max);

			if(choose >= min && choose <= max) ok = true;
			else Printer.getPrinter().println("/!\\ Choix incorrecte, veuillez reessayer. /!\\");
		}
		return choose;
	}


	/**
	 * ableToPlaceFigurine() utilise les ableToPlaceFigurine() de zone et de player pour savoir si cela est possible. 
	 * @param zone : la zone concernee.
	 * @param player : le joueur concerne. 
	 * @param indexZone : index de la zone concernee
	 * @return true si possible de placer, false sinon.
	 */ 
	private boolean ableToChooseZone(int indexZone,Player player){
		if( numberPlayer < 4 && (indexZone>=5 && indexZone<=7) && numberSpecialZoneOccuped>=2) return false; //regle a 2 ou 3 joueur 2 zone speciale utiliser par tour
		return zones[indexZone].ableToChooseZone(player) && zones[indexZone].getMinimalFigurineRequierement()<=player.getCurrentFigurine();
	}

	/**
	 * Gestion du tirage d'obejet
	 * @param gamePlayers la liste de tout les joueur et leurs
	 * @param player le joueur qui a piocher la carte
	 */
	private void tirage(GamePlayers gamePlayers, Player player) {
		int index=0;
		for(int j = 0; j < gamePlayers.getNumberPlayers();j++ ) {
			if(gamePlayers.getPlayer(j)==player) index = j;
		}
		
		int[] diceRes = dice.rollDice(Settings.RAND, gamePlayers.getNumberPlayers());
		Printer.getPrinter().println("le tirage donne : "+Arrays.toString(diceRes));
		boolean[] alreadyChoose = new boolean[gamePlayers.getNumberPlayers()];
		for(int j = 0; j < gamePlayers.getNumberPlayers();j++ ) {
			int indexPlayerChoose = (j+index)%gamePlayers.getNumberPlayers();
			boolean correctChoose = false;
			int choose=-1;
			while(!correctChoose) {
				choose = gamePlayers.getPlayer(indexPlayerChoose).getIA().chooseTirage(diceRes.clone(),alreadyChoose.clone());
				if(choose>=0 && choose<diceRes.length && !alreadyChoose[choose]) {
					correctChoose = true;
					alreadyChoose[choose]=true;
				}
			}
			String str = "Le joueur "+gamePlayers.getPlayer(indexPlayerChoose).getName()+" a choisi : ";
			switch(diceRes[choose]) {
				case 1:
					gamePlayers.getInventory(indexPlayerChoose).addRessource(Ressource.WOOD, 1);
					Printer.getPrinter().println(str+Ressource.WOOD);
					break;
					
				case 2:
					gamePlayers.getInventory(indexPlayerChoose).addRessource(Ressource.CLAY, 1);
					Printer.getPrinter().println(str+Ressource.CLAY);
					break;
					
				case 3:
					gamePlayers.getInventory(indexPlayerChoose).addRessource(Ressource.STONE, 1);
					Printer.getPrinter().println(str+Ressource.STONE);
					break;
					
				case 4:
					gamePlayers.getInventory(indexPlayerChoose).addRessource(Ressource.GOLD, 1);
					Printer.getPrinter().println(str+Ressource.GOLD);
					break;
					
				case 5:
					gamePlayers.getInventory(indexPlayerChoose).getTools().incrementTool();
					Printer.getPrinter().println(str+"une augmentation d'outils");
					break;
					
				case 6:
					gamePlayers.getInventory(indexPlayerChoose).addRessource(Ressource.FIELD, 1);
					Printer.getPrinter().println(str+Ressource.FIELD);
					break;
				default:
					Printer.getPrinter().println("erreur");
			}
			
		}
	}
}

