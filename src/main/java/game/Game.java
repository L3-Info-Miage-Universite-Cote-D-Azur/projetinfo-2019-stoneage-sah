package game;


public class Game {
	
	/* FIELDS */
	private Player[] players;//Tableau des joueurs.
	private Zone[] zones;//Tableau des zones.
	private int nbTour;//Compteur du nombre de tours. 

	/**
	 * Le constructeur de Game qui initialise players et zones.
	 */
	public Game(){
		players=GameUtility.initPlayer();
		zones=GameUtility.initZone();
		nbTour=1;
	}

	/**
	 * gameLoop constitue la boucle du jeu. 
	 */
	public void gameLoop() {
		while(!this.isEnd()){
			System.out.println("\n\n####### TOUR : "+nbTour+" #######");

			System.out.println("\n--- Phase de placement ---");
			placePhase();

			System.out.println("\n--- Phase de recolte ---");
			harvestPhase();
			
			System.out.println("\n--- Phase de nourrisage ---");
			feedPhase();
			
			nbTour+=1;
		}
	}
	
	/**
	 * placePhase correspond a la phase de placement. 
	 */
	public void placePhase(){
		
		boolean notFinish = true; 
		boolean[] haveNoFigurine = new boolean[players.length];
		Player player;
		int selectedPlayer;
		
		while(notFinish){
			
			notFinish = false;
			
			for(int i = 0; i < players.length; i++) {
				
				//Le joueur qui commence change a chaque tour. 
				selectedPlayer = (i + nbTour) % players.length;//L'indice du joueur selectionne en fonction du tour. 
				
				if( !haveNoFigurine[selectedPlayer] ){
					
					player = players[selectedPlayer];
					System.out.println("\nC'est le tour de " + player.getName() + ".");
					
					notFinish = true;//Il reste encore des joueurs qui peuvent peut etre jouer. 

					int zoneIndex = GameUtility.zoneChoose(player,zones);//On recupere l'indice de la zone
					int number = GameUtility.numberChoose(player,zones[zoneIndex]);//On recupere le nombre de figurines
					FigurineManagement.placeFigurine(zones[zoneIndex],player,number,zoneIndex);//On place les figurines dans la zone. 

					if(player.getCurrentFigurine() == 0) {//Si le joueur n'a plus de figurine
						haveNoFigurine[selectedPlayer]=true;
					}
				}
			}
		}
	}

	/**
	 * harverstPhase correspond a la phase de recolte. 
	 */
	public void harvestPhase(){
		
		Player player;
		
		for(int j = 0; j < players.length; j++) {
			
			player = players[(j + nbTour) % players.length];//L'indice du joueur selectionne en fonction du tour. 
			System.out.println("\nC'est au tour de "+player.getName()+" :");

			for(int i = 0; i < zones.length; i++) {
				if(zones[i].howManyPlayerFigurine(player) != 0) {//Si le joueur avait des figurines dans la zone. 
					FigurineManagement.recoveryFigurineInZone(zones[i], player);//Il recupere ses figurines et les ressources.
				}
			}
			player.resetHadPlaced();
		}
	}
	
	/**
	 * feedPhase correspond a la phase de nourrisage. 
	 */
	public void feedPhase() {
		for(int i = 0; i < players.length; i++) {
			Player player = players[i];
			Inventory inventory = player.getInventory();
			int food = inventory.getRessource(Ressource.FOOD);
			
			int figurinesToFeed = player.getMaxFigurine();
			figurinesToFeed -= inventory.getRessource(Ressource.FIELD);
			
			System.out.println("\nC'est au tour de "+player.getName()+" :");
			
			if(figurinesToFeed <= 0) {
				System.out.println("Le joueur "+player.getName()+" a nourris ses figurines.");
			}
			else if(food >= figurinesToFeed) {
				System.out.println("Le joueur "+player.getName()+" a nourris ses figurines avec "+figurinesToFeed+ " nourritures.");
				inventory.subRessource(Ressource.FOOD,figurinesToFeed);
			}
			else {
				System.out.println("Le joueur "+player.getName()+" nourris ses figurines avec "+food+ " nourritures.");
				figurinesToFeed -= food;
				inventory.subRessource(Ressource.FOOD,food);
				
				int[] tabRessource = inventory.toArrayNumber();
				
				//Si le joueur n'a plus de ressource ou ne veut pas les depenser.
				if(tabRessource.length == 0) {//Voir plus tard si le joueur ne veux pas depenser.
					System.out.println("Le joueur ne peut pas nourrir ses figurines.");
					//travail sur le score plus tard.
					return;
				}
				
				//Si le joueur a des ressources et veut les depenser
				while(figurinesToFeed > 0) {
					int ressource = GameUtility.ressourceChooze(player, figurinesToFeed);
					figurinesToFeed -= ressource;
				}
				System.out.println("Le joueur "+player.getName()+" nourris ses figurines.");
			}
		}
	}

	/**
	 * Renvoie true ou false, si la condition de victoire a ete effectue. 
	 * @return isOver, qui vaut true ou false celon la condition de victoire. 
	 */
	public boolean isEnd(){
		boolean isOver = false;
		
		if(nbTour == 100) isOver = true; //Si il y a un probleme.
		
		for(Player player : players){
			if(player.getInventory().getRessource(Ressource.WOOD) > 1) {//La condition de victoire. 
				System.out.println("\n"+player.getName()+" a gagne.");
				isOver = true; 
			}
		}
		return isOver; 
	}
}
