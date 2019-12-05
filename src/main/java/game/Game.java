package game;

import game.zone.ZoneCarteCivilisation;
import inventory.Inventory;
import player.Player;
import printer.Printer;

/**
 * La classe Game represente le plateau du joueur et ce qui s'y passe.
 * @author Mentra20
 */
public class Game {

	/* FIELDS */
	private GamePlayers gamePlayers;
	private GameZones gameZones;
	private int nbTour;//Compteur du nombre de tours. 
	public final int numberPlayer;
	public final Dice dice;
	private Statistics statistics;

	/**
	 * Le constructeur de Game qui initialise players et zones.
	 */
	public Game(int numberPlayer, Statistics stats){
		this.numberPlayer=numberPlayer;
		this.dice = new Dice();
		gameZones = new GameZones(numberPlayer,dice);
		gamePlayers = new GamePlayers(numberPlayer);
		nbTour=1;
		this.statistics = stats;
	}


	/**
	 * gameLoop constitue la boucle du jeu. 
	 */
	public void gameLoop() {
		while(!this.isEnd()){
			Printer.getPrinter().println("\n\n####### TOUR : "+nbTour+" #######");
			this.statistics.updateStats(this.gamePlayers.getInventories(), this.gamePlayers.getPlayers());

			afficheInfo();
			
			Printer.getPrinter().println("\n\n--- PHASE DE PLACEMENT ---");
			placePhase();
			gameZones.resetNumberSpecialZoneOccuped();
			
			Printer.getPrinter().println("\n\n--- PHASE DE RECOLTE ---");
			harvestPhase();
			
			useCardRessource();//Demande si le joueur veut utiliser ses cartes ressources au choix.
			
			gameZones.organizeCard();
			
			if (gameZones.getCardManager().isEmpty()) {
				Printer.getPrinter().println("\n--- PARTIE TERMINEE : Il n'y a plus de carte civilisation ---");
				break;
			}
			//on deplace ici l'organisation des cartes pour arreter le jeu s'il n'y a plus de cartes civilisations avant de nourrir, d'apres les regles
			Printer.getPrinter().println("\n\n--- PHASE DE NOURRISAGE ---");
			feedPhase();

			gamePlayers.resetTools();
			//update carte civilisation (si necessaire)
			
			nbTour+=1;
		}
		Printer.getPrinter().println("\n=========================\nLa partie est fini\n=========================");
		this.statistics.createJITCurves(this.gamePlayers.getPlayers());
		gamePlayers.endGame();
	}

	/**
	 * placePhase correspond a la phase de placement. 
	 */
	public void placePhase(){

		boolean notFinish = true; 
		boolean[] haveNoFigurine = new boolean[numberPlayer];
		int selectedPlayer;

		while(notFinish)
		{
			notFinish = false;

			for(int i = 0; i < numberPlayer; i++) 
			{
				//Le joueur qui commence change a chaque tour. 
				selectedPlayer = (i + nbTour) % numberPlayer;//L'indice du joueur selectionne en fonction du tour. 

				if( !haveNoFigurine[selectedPlayer] )
				{
					notFinish = true;//Il reste encore des joueurs qui peuvent peut etre jouer. 
					haveNoFigurine[selectedPlayer]=gameZones.playerPlaceFigurine(gamePlayers.getPlayer(selectedPlayer));
				}
			}
		}
	}

	/**
	 * harverstPhase correspond a la phase de recolte. 
	 */
	public void harvestPhase(){

		for(int j = 0; j < numberPlayer; j++) {
			int  selectedPlayer = (j + nbTour) % numberPlayer;//L'indice du joueur selectionne en fonction du tour.
			gameZones.playerHarvest(gamePlayers.getPlayer(selectedPlayer), gamePlayers.getInventory(selectedPlayer),gamePlayers);
		}
	}

	/**
	 * feedPhase correspond a la phase de nourrisage. 
	 */
	public void feedPhase() {
		for(int i = 0; i < numberPlayer; i++) {
			int  selectedPlayer = (i + nbTour) % numberPlayer;//L'indice du joueur selectionne en fonction du tour. 
			gamePlayers.playerFeedPhase(selectedPlayer);
		}
	}

	/**
	 * Renvoie true ou false, si la condition de victoire a ete effectue. 
	 * @return true si le jeu doit se terminer, false sinon
	 */
	public boolean isEnd(){
		return gameZones.isEnd(); //La seul condition de victoire est en fonction des zone.
	}


	/**
	 * afficheInfo affiche des informations de la partie en debut de tour.
	 */
	public void afficheInfo() {
		Printer.getPrinter().println("\n--- INFORMATIONS ---");
		for (int i = 0; i < numberPlayer;i++) {
			Printer.getPrinter().println("Le joueur "+ gamePlayers.getPlayer(i).getName()+" a " + gamePlayers.getPlayer(i).getScore()+" points de victoire");
		}
		Printer.getPrinter().println();
		for (int i = 0; i < numberPlayer;i++) {
			Printer.getPrinter().println("Le joueur "+ gamePlayers.getPlayer(i).getName()+" a " + gamePlayers.getPlayer(i).getMaxFigurine()+" ouvriers");
		}
		Printer.getPrinter().println();
		for (int i = 0; i < numberPlayer;i++) {
			String string = "Le joueur "+gamePlayers.getPlayer(i).getName()+" a :";
			string += " "+gamePlayers.getInventory(i).getRessource(Ressource.WOOD)+" bois,";
			string += " "+gamePlayers.getInventory(i).getRessource(Ressource.CLAY)+" argiles,";
			string += " "+gamePlayers.getInventory(i).getRessource(Ressource.STONE)+" pierres,";
			string += " "+gamePlayers.getInventory(i).getRessource(Ressource.GOLD)+" or,";
			string += " "+gamePlayers.getInventory(i).getRessource(Ressource.FOOD)+" nourritures,";
			string += " et "+gamePlayers.getInventory(i).getRessource(Ressource.FIELD)+" marqueurs nourritures.";
			Printer.getPrinter().println(string);
		}
		Printer.getPrinter().println();
		for(int i = 0; i < 4; i++) {
			ZoneCarteCivilisation zoneciv = (ZoneCarteCivilisation) gameZones.getZones()[8+i];
			CarteCivilisation cc = zoneciv.getCard();
			Printer.getPrinter().println("Dans la zone "+zoneciv.getName()+ ": "+cc.toString()+" COUT: "+zoneciv.getNumberRessourceNeed()+".");
		}
	}
	
	
	public void useCardRessource() {
		for(int i = 0; i < numberPlayer; i++) {
			int  selectedPlayer = (i + nbTour) % numberPlayer;//L'indice du joueur selectionne en fonction du tour. 
			Inventory inv = gamePlayers.getInventory(selectedPlayer);
			Player player = gamePlayers.getPlayer(selectedPlayer);
			
			if(inv.getCardRessource() > 0) {
				Printer.getPrinter().println("\n---- PHASE RESSOURCE AU CHOIX ----");
				for(int j = 0; j < inv.getCardRessource(); j++) {
					
					int res = player.getIA().useRessourceCard();
					
					if(res != -1) {//Si l'IA utilise
						Printer.getPrinter().println("Le joueur "+player.getName()+
								" utilise sa carte ressource au choix pour obtenir 2 "+Ressource.indexToRessource(res).toString()+".");
						inv.addRessource(Ressource.indexToRessource(res), 2);//On ajoute toujours 2 de la ressource choisie.
						inv.decrementCardRessource();
					}
					else {
						Printer.getPrinter().println("Le joueur "+gamePlayers.getPlayer(selectedPlayer).getName()+
								" garde sa carte ressource au choix.");
					}
				}
			}
		}
	}

}
