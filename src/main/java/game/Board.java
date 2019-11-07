package game;

/**
 * La classe Board gere tout ce qui se passe sur le plateau.
 * Elle gere l'initialisation du jeu, son deroulemement 
 * et la fin du jeu. 
 */


public class Board {
	/* FIELDS */
	private Player[] players;
	private Zone currentZone;

	private Zone forest;
	private Zone clay;
	private Zone cobblestone;
	private Zone gold;
	
	/* GAME */

	public void run () {
		this.init(); 
		
		/* PHASE 1: PLACEMENT DES FIGURINES PAR LE JOUEUR*/
		while (this.remainingFigurinesInPlayers() == true) 
		{
			System.out.println("-- PHASE 1 --");
			for (int i = 0; i < Settings.MAX_PLAYERS; i++) 
			{
				if (this.players[i].getFigurine() > 0) 
				{
					System.out.println("[*] C'est au tour de " + this.players[i].getName() + "!");
					int chooseZone = this.checkPlayerZoneChoice(i);

					int figurinesToPut = this.askPutFigurinesOnZone(i, currentZone);
					this.updateFigurineAndZone(chooseZone, figurinesToPut, i);
				}
			}
		}
		
        /* PHASE 2: RECOLTE DES RESSOURCES */
		System.out.println("-- PHASE 2 --");

		for (int currentPlayer = 0; currentPlayer < this.players.length; currentPlayer++) {
			// POUR CHAQUE JOUEUR
			System.out.println("[*] C'est au tour " + this.players[currentPlayer].getName() + "!");

			for (int i = 0; i < this.players[currentPlayer].getFigurineInZone().length; i++) {
				// RETIRER LES JOUEURS ET RESSOURCES
				if (this.players[currentPlayer].getFigurineInZone()[i] > 0) {
					// ROLL DICE
					int diceResult = Dice.roll(this.players[currentPlayer].getFigurineInZone()[i]);
					System.out.println(this.players[currentPlayer].getName() + " a fait " + diceResult + " !");

					int nbRessource = this.players[currentPlayer].getInv().addResource(diceResult, i);
					System.out.println(this.players[currentPlayer].getName() + " a maintenant " + nbRessource + " de " + Settings.RESSOURCES_NAMES[i]);

					this.players[currentPlayer].subFigurineInZone(i);
				}
			}
		}

		this.endGame();
	}
	
	/* INITIALISATION */
	private void init () {
		this.players = new Player[Settings.MAX_PLAYERS];

		for (int i = 0; i < Settings.MAX_PLAYERS; i++)
		{
			this.players[i] = new Player(Settings.getRandomName(),Settings.getRandomIA());
			System.out.println("Le joueur " + this.players[i].getName() + " a �t� cr�� avec la strat�gie " + this.players[i].getIA().toString()+ " .");
		}

		this.forest = new Zone(Settings.ZONE_NAMES[0], 3, Settings.MAX_FIGURINE_IN_ZONE);
		this.clay = new Zone(Settings.ZONE_NAMES[1], 4, Settings.MAX_FIGURINE_IN_ZONE);
		this.cobblestone = new Zone(Settings.ZONE_NAMES[2], 5, Settings.MAX_FIGURINE_IN_ZONE);
		this.gold = new Zone(Settings.ZONE_NAMES[3], 6, Settings.MAX_FIGURINE_IN_ZONE);
	}
	
	/* METHODS */
	/**
	 * Verification que la zone existe.
	 * @param currentPlayer Joueur qui fait le choix
	 * @return Renvoie la zone choisie par le joueur
	 */
	private int checkPlayerZoneChoice (int currentPlayer) {
		int chooseZone = this.players[currentPlayer].getIA().chooseZone();

        if (chooseZone == 0) 
        {
        	this.currentZone = this.forest;
        } 
        else if (chooseZone == 1 ) 
        {
        	this.currentZone = this.clay;
        } 
        else if (chooseZone == 2) 
        {
        	this.currentZone = this.cobblestone;
        } 
        else if (chooseZone == 3) 
        {
        	this.currentZone = this.gold;
        } 
        else 
        {
        	chooseZone = -1;
        }

        if (chooseZone == -1)
        {
        	System.out.println(this.players[currentPlayer].getName() + " a choisi une zone inexistante: " + chooseZone);
       	 	System.exit(2);
       	}
        else
        	System.out.println(this.players[currentPlayer].getName() + " a choisi la zone " + currentZone.getName());
        return chooseZone;
	}
	
	/**
	 * choix du nombre de figurine a placer sur la zone.
	 * @param currentPlayer Le joueur qui fait le choix.
	 * @param zone la zone dans la quel il verifie si il peut placer.
	 * @return le nombre de figurine que le joueur place dans la zone.
	 */
	private int askPutFigurinesOnZone (int currentPlayer, Zone zone) {
		int figurineNb = -1;

        while (figurineNb <= 0 || figurineNb > this.players[currentPlayer].getFigurine())
        	figurineNb = this.players[currentPlayer].getIA().chooseNumber(Math.min(this.players[currentPlayer].getFigurine(), zone.getAvailableSpace()));

        System.out.println(this.players[currentPlayer].getName() + " a choisi de placer " + figurineNb + " figurine(s) dans la zone " + this.currentZone.getName());
        return figurineNb;
	}
	
	/**
	 * change le nombre de figurine dans la zone(du joueur) et diminue le nombre figurine disponnible.
	 * @param zone zone dans la quel les figurine sont place.
	 * @param figurineNb nombre de figurine palcer.
	 * @param currentPlayer joueur qui place les figurine dans la zone.
	 */
	private void updateFigurine (int zone, int figurineNb, int currentPlayer)  {
        this.players[currentPlayer].addFigurineInZone(zone, figurineNb);
        System.out.println(this.players[currentPlayer].getFigurine() + " figurine(s) restante pour " + this.players[currentPlayer].getName());
	}
	
	/**
	 * met a jour le nombre de figurine encore placable dans la zone.
	 * @param figurineNb nombre figurine qui on etait placer.
	 */
	private void updateZone (int figurineNb)  {
        this.currentZone.addFigurine(figurineNb);
        System.out.println(this.currentZone.getAvailableSpace() + " place(s) restante dans la zone " + this.currentZone.getName());
	}
	
	/**
	 * Fait toutes les modification lié au placement d'un joueur.
	 * @param zone zone du placement des figurine.
	 * @param figurineNb nombre de figurine a place.
	 * @param currentPlayer le joueur qui a fait le placement.
	 */
	private void updateFigurineAndZone (int zone, int figurineNb, int currentPlayer) {
		if (this.currentZone.hasEnoughSpace(figurineNb) == true) 
		{
			this.updateFigurine(zone, figurineNb, currentPlayer);
			this.updateZone(figurineNb);
		}
	}
	
	/**
	 * Return a boolean, if there are figurines left in one player
	 * @return true if there is a figurine left, else false
	 */
	private boolean remainingFigurinesInPlayers () {
		for (int i = 0; i < this.players.length; i++) {
			if (this.players[i].getFigurine() > 0) {
				return true;
			}
		}
		return false;
	}
	
	private boolean winCondition (Player p) {
		return p.getInv().getWood() >= 1;		
	}
	
	private void endGame () {
		for (Player player : this.players) {
			if (winCondition(player) == true) {
				System.out.println("Le joueur " + player.getName() + " remporte la partie");
			}
		}
	}
}
