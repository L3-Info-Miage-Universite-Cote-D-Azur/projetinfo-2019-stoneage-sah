package game;

/**
 * La classe GameUtility procure de fonctions utiles au deroulement du jeu. 
 * @author Mentra20
 */

public class GameUtility{

	/**
	 * Initie le tableau players de la classe game avec Settings.MAX_PLAYER joueurs. 
	 * @return tableau de Players (utilise dans Game). 
	 */
	public static Player[] initPlayer(){

		Player[] res = new Player[Settings.MAX_PLAYER];

		for(int i = 0; i < Settings.MAX_PLAYER; i++){
			//On attribut un nom et une IA au hasard. 
			res[i] = new Player(Settings.getRandomName(), Settings.getRandomIA());
			System.out.println("Le joueur " + res[i].getName() + " a ete creer avec la strategie " + res[i].getIA() + ".");
		}
		return res;
	}


	/**
	 * zoneChoose retourne la zone choisie par l'IA.
	 * @param player le joueur concerne
	 * @param zone, la zone concernee
	 * @return l'indice de la zone choisie dans le tableau zones de Game.
	 */
	public static int zoneChoose(Player player,Zone[] zone) {

		int choose = -1;
		boolean ok = false;
		//Variables utiles pour les fonctions de l'IA. 
		int[] zoneAvailableSpace = new int[zone.length];
		String[] zoneName = new String[zone.length];
		
		//Initialisation des variables pour l'IA
		for(int i = 0; i < zone.length; i++){
			zoneAvailableSpace[i] = zone[i].getAvailableSpace();
			zoneName[i] = zone[i].getName();
		}

		while(!ok){
			choose = player.getIA().chooseZone(zoneAvailableSpace,zoneName);

			if((choose >= 0) && (choose<zone.length) && FigurineManagement.ableToChooseZone(zone[choose], player, choose)) ok=true;
			else System.out.println("/!\\ Choix incorrecte ou zone pleine, veuillez reessayer./!\\");
		}
		return choose;
	}


	/**
	 * numberChoose retourne le nombre de figurines a posee choisie par l'IA.
	 * @param player le joueur concerne
	 * @param zone, la zone concernee
	 * @return Le nombre de figurines que l'IA veut poser. 
	 */
	public static int numberChoose(Player player,Zone zone){
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
			else System.out.println("/!\\ Choix incorrecte, veuillez reessayer. /!\\");
		}
		return choose;
	}
	
	/**
	 * Demande au joueur par quoi il veux remplacer la nourriture qui manque pour finir le tour et nourrire toutes les figurines
	 * cette fonction est appelee jusqu'a ce que ressourceToFood soit a 0 dans Game.feedPhase().
	 * @param player le joueur concerne.
	 * @param ressourceToFood la nouriture totale qu'il lui manque.
	 * @return renvoie le nombre de ressource que le joueur a utiliser a la place de la nouriture.
	 */
	public static int ressourceChoose(Player player,int ressourceToFood) {
		
		//cas normalement ignorer par game.feedPhase()
		if(player.getInventory().availableResourceToFeed()==0) {return 0;}
		
		int[] IAChoose;
		int[] ressourceNumber = new int[4];
		int[] copieInventory=player.getInventory().getCopyRessources();
		String[] ressourceName = new String[4];
		
		for(int i=0;i<4;i++) {
			ressourceNumber[i]=copieInventory[i];
			ressourceName[i]=Ressource.indexToRessource(i).toString();
		}
		
		do {
			IAChoose=player.getIA().chooseRessource(ressourceToFood, ressourceNumber, ressourceName);
		}while(IAChoose[0]<0 && IAChoose[0]>3 && IAChoose[1]>0 && IAChoose[1]<=Math.min(ressourceToFood, copieInventory[IAChoose[0]]));
		
		System.out.println("Le joueur "+player.getName()+ " nourris ses figurines avec "+ IAChoose[1]+" "+Ressource.indexToRessource(IAChoose[0])+".");
		player.getInventory().ableToSubRessource(Ressource.indexToRessource(IAChoose[0]), IAChoose[1]);
		player.getInventory().subRessource(Ressource.indexToRessource(IAChoose[0]), IAChoose[1]);
		
		return IAChoose[1];
	}
}
