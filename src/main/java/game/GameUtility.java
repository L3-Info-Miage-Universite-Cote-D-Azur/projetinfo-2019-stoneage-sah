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
	 * Initie le tableau zones de la classe game. 
	 * @return tableau de Zone (utiliser dans Game).
	 */
	public static Zone[] initZone(){
		Zone[] res = new Zone[7];

		//Les zones du jeu. 
		res[0] = new ZoneRessource("Foret",Ressource.WOOD,3,Settings.MAX_ZONERESSOURCE_SPACE);
		res[1] = new ZoneRessource("Glaisiere",Ressource.CLAY,4,Settings.MAX_ZONERESSOURCE_SPACE);
		res[2] = new ZoneRessource("Carriere",Ressource.STONE,5,Settings.MAX_ZONERESSOURCE_SPACE);
		res[3] = new ZoneRessource("Riviere",Ressource.GOLD,6,Settings.MAX_ZONERESSOURCE_SPACE);
		//La zone de chasse a : nombre de joueur x le nombre de figurines maximum d'espace. 
		res[4] = new ZoneRessource("Chasse", Ressource.FOOD, 2, Settings.MAX_PLAYER * Settings.MAX_FIGURINE);
		res[5] = new ZoneField("Champs", Ressource.FIELD);
		res[6] = new ZoneHut("Cabane de reproduction");

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
			else System.out.println("Choix incorrecte ou zone pleine, veuillez reessayer.\n");
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
			else System.out.println("Choix incorrecte, veuillez reessayer.\n");
		}
		return choose;
	}
	
	public static int ressourceChooze(Player player,int ressourceToFood) {
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
		player.getInventory().ableToSubRessource(Ressource.indexToRessource(IAChoose[0]), IAChoose[1]);
		player.getInventory().subRessource(Ressource.indexToRessource(IAChoose[0]), IAChoose[1]);
		return IAChoose[1];
	}
}
