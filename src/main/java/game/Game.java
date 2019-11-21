package game;


public class Game {
	
	/* FIELDS */
	private Player[] players;//Tableau des joueurs.
	private Zone[] zones;//Tableau des zones.
	private ZoneCarteCivilisation[] zoneCarteCivilisation;
	private int nbTour;//Compteur du nombre de tours. 

	/**
	 * Le constructeur de Game qui initialise players et zones.
	 */
	public Game(){
		players=GameUtility.initPlayer();
		initZone();
		nbTour=1;
	}

	/**
	 * gameLoop constitue la boucle du jeu. 
	 */
	public void gameLoop() {
		while(!this.isEnd()){
			System.out.println("\n\n####### TOUR : "+nbTour+" #######");
			
			afficheInfo();
			
			System.out.println("\n\n--- PHASE DE PLACEMENT ---");
			placePhase();

			System.out.println("\n\n--- PHASE DE RECOLTE ---");
			harvestPhase();
			
			System.out.println("\n\n--- PHASE DE NOURRISAGE ---");
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
			int  selectedPlayer = (i + nbTour) % players.length;//L'indice du joueur selectionne en fonction du tour. 
			
			Player player = players[selectedPlayer];
			
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
				if(food != 0 ) {
					System.out.println("Le joueur "+player.getName()+" nourris une partie de ses figurines avec "+food+ " nourritures.");
					figurinesToFeed -= food;
					inventory.subRessource(Ressource.FOOD,food);
				}
				
				int totalRessource = inventory.availableResourceToFeed();
				//Si le joueur n'a plus de ressource ou ne veut pas les depenser.
				if(totalRessource < figurinesToFeed) {//Voir plus tard si le joueur ne veux pas depenser.
					System.out.println("Le joueur "+ player.getName() +" ne peut pas nourrir ses figurines.");
					player.subScore(10);
					System.out.println("Le joueur"+player.getName() + "a perdu 10 points de victoires");
					continue;
				}
				boolean wantToFeed = player.getIA().useRessourceToFeed(inventory.getCopyRessources());
				if(!wantToFeed) {
					System.out.println("Le joueur "+ player.getName() +" ne veux pas nourrir ses figurines.");
					player.subScore(10);
					System.out.println("Le joueur"+player.getName() + "a perdu 10 points de victoires");
					continue;
				}
				
				
				//Si le joueur a des ressources et veut les depenser
				while(figurinesToFeed > 0) {
					int ressource = GameUtility.ressourceChoose(player, figurinesToFeed);
					figurinesToFeed -= ressource;
				}
				System.out.println("Le joueur "+player.getName()+" a nourris ses figurines.");
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
				System.out.println("\nVICTOIRE :"+player.getName()+" a gagne.");
				isOver = true; 
			}
		}
		return isOver; 
	}
	
	/**
	 * Initie le tableau zones. 
	 */
	public void initZone(){
		zones = new Zone[11];
		zoneCarteCivilisation = new ZoneCarteCivilisation[4];

		//Les zones du jeu. 
		zones[0] = new ZoneRessource("Foret",Ressource.WOOD,Settings.MAX_ZONERESSOURCE_SPACE);
		zones[1] = new ZoneRessource("Glaisiere",Ressource.CLAY,Settings.MAX_ZONERESSOURCE_SPACE);
		zones[2] = new ZoneRessource("Carriere",Ressource.STONE,Settings.MAX_ZONERESSOURCE_SPACE);
		zones[3] = new ZoneRessource("Riviere",Ressource.GOLD,Settings.MAX_ZONERESSOURCE_SPACE);
		//La zone de chasse a : nombre de joueur x le nombre de figurines maximum d'espace. 
		zones[4] = new ZoneRessource("Chasse", Ressource.FOOD,Settings.MAX_PLAYER * Settings.MAX_FIGURINE);
		zones[5] = new ZoneField("Champs", Ressource.FIELD);
		zones[6] = new ZoneHut("Cabane de reproduction");
		
		//gestion des carte civilisation
		ZoneCarteCivilisation zcc;
		for(int i=0;i<4;i++) {
			zcc=new ZoneCarteCivilisation("Carte Civilisation: "+ (i+1), i+1);
			zoneCarteCivilisation[i]=zcc;
			zones[7+i]=zcc;
		}
	}
	
	public void afficheInfo() {
		for (int i=0; i<players.length;i++) {
			System.out.println("Le joueur "+ players[i].getName()+" a " + players[i].getScore()+" points de victoire");
		}
	}
}