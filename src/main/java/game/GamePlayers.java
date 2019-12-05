package game;

import inventory.Inventory;
import player.Player;
import printer.Printer;

/**
 * La classe GamePlayers gere ce qui se passe sur le plateau cote joueur.
 * @author Mentra20
 *
 */
public class GamePlayers {

	/* FIELDS */
	private Player[] players;//Tableau des joueurs.
	private Inventory[] inventories;//Tableau d'inventaire des joueurs.
	
	/* CONSTRUCTOR */
	public GamePlayers(int numberPlayer) {
		initInventories(numberPlayer); 
		initPlayer(numberPlayer);
	}

	/* GETTERS */
	public int getNumberPlayers() { return players.length; }
	public Player getPlayer(int index) {
		if(index >= 0 && index < players.length) { return players[index]; }
		return null;
	}
	public Inventory getInventory(int index) {
		if(index >= 0 && index < inventories.length) { return inventories[index]; }
		return null;
	}
	public Inventory[] getInventories () {return this.inventories;}
	public Player[] getPlayers () {return this.players;}


	/* INIT */
	/**
	 * Initie le tableau players de la classe game avec joueurs. 
	 */
	public void initPlayer(int numberPlayer){

		players = new Player[numberPlayer];

		for(int i = 0; i < numberPlayer; i++){
			//On attribut un nom et une IA au hasard. 
			players[i] = new Player(Settings.getRandomName(),inventories[i].getInventoryIA());
		}
	}

	/**
	 * Initie le tableau inventories 
	 */
	public void initInventories(int numberPlayer){
		this.inventories = new Inventory[numberPlayer];
		for(int i = 0; i < numberPlayer; i++){
			inventories[i] = new Inventory();
		}
	}

	/* FEEDPHASE */

	/**
	 * feedPhase correspond a la phase de nourrisage cote joueur. 
	 */
	public void playerFeedPhase(int selectedPlayer) {
		Player player = players[selectedPlayer];
		Inventory inventory = inventories[selectedPlayer];

		int food = inventory.getRessource(Ressource.FOOD);

		int figurinesToFeed = player.getMaxFigurine();
		figurinesToFeed -= inventory.getRessource(Ressource.FIELD);

		Printer.getPrinter().println("\nC'est au tour de "+player.getName()+" :");

		if(figurinesToFeed <= 0) {
			Printer.getPrinter().println("Le joueur "+player.getName()+" a nourris ses figurines.");
		}
		else if(food >= figurinesToFeed) {
			Printer.getPrinter().println("Le joueur "+player.getName()+" a nourris ses figurines avec "+figurinesToFeed+ " nourritures.");
			inventory.subRessource(Ressource.FOOD,figurinesToFeed);
		}
		else {
			if(food != 0 ) {
				Printer.getPrinter().println("Le joueur "+player.getName()+" nourris une partie de ses figurines avec "+food+ " nourritures.");
				figurinesToFeed -= food;
				inventory.subRessource(Ressource.FOOD,food);
			}

			int totalRessource = inventory.availableResourceToFeed();
			//Si le joueur n'a plus de ressource ou ne veut pas les depenser.
			if(totalRessource < figurinesToFeed) {//Voir plus tard si le joueur ne veux pas depenser.
				Printer.getPrinter().println("Le joueur "+ player.getName() +" ne peut pas nourrir ses figurines.");
				player.subScore(10);
				Printer.getPrinter().println("Le joueur "+ player.getName() + " a perdu 10 points de victoires");
				return;
			}
			boolean wantToFeed = player.getIA().useRessourceToFeed();
			if(!wantToFeed) {
				Printer.getPrinter().println("Le joueur "+ player.getName() +" ne veux pas nourrir ses figurines.");
				player.subScore(10);
				Printer.getPrinter().println("Le joueur "+ player.getName() + " a perdu 10 points de victoires");
				return;
			}


			//Si le joueur a des ressources et veut les depenser
			while(figurinesToFeed > 0) {
				int ressource = ressourceChoose(player,inventory, figurinesToFeed);
				figurinesToFeed -= ressource;
			}
			Printer.getPrinter().println("Le joueur "+player.getName()+" a nourris ses figurines.");
		}
	}

	/* RESET */
	public void resetTools() {
		for(Inventory inventory: inventories) {
			inventory.getTools().resetToolsUsed();
		}
	}

	/* Score */
	public void endGame() {
		Score score = new Score();
		for(int i = 0; i < players.length; i++) {
			Printer.getPrinter().println();
			score.calculScore(players[i],inventories[i]);
		}
		score.classement(players);
	}


	/*==================================================
	 * Traitement de l'IA;
	 * ==================================================*/


	/**
	 * Demande au joueur par quoi il veux remplacer la nourriture qui manque pour finir le tour et nourrire toutes les figurines
	 * cette fonction est appelee jusqu'a ce que ressourceToFood soit a 0 dans Game.feedPhase().
	 * @param player le joueur concerne.
	 * @param inventory : l'inventaire du joueur concerne.
	 * @param ressourceToFood la nouriture totale qu'il lui manque.
	 * @return renvoie le nombre de ressource que le joueur a utiliser a la place de la nouriture.
	 */
	public int ressourceChoose(Player player,Inventory inventory,int ressourceToFood) {

		//cas normalement ignorer par game.feedPhase()
		if(inventory.availableResourceToFeed() == 0) {return 0;}

		int[] IAChoose;
		int[] copieInventory=inventory.getCopyRessources();

		do {
			IAChoose = player.getIA().chooseRessource(ressourceToFood);
		}while(IAChoose[0] < 0 || IAChoose[0] > 3 || IAChoose[1] <= 0 || IAChoose[1] > Math.min(ressourceToFood, copieInventory[IAChoose[0]]));

		Printer.getPrinter().println("Le joueur "+player.getName()+ " nourris ses figurines avec "+ IAChoose[1]+" "+Ressource.indexToRessource(IAChoose[0])+".");
		inventory.ableToSubRessource(Ressource.indexToRessource(IAChoose[0]), IAChoose[1]);
		inventory.subRessource(Ressource.indexToRessource(IAChoose[0]), IAChoose[1]);

		return IAChoose[1];
	}
}

