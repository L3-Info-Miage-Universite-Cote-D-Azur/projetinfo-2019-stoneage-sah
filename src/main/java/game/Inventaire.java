package game;

/**
 * La classe Inventaire gère l'inventaire du joueur.
 */

public class Inventaire {
	
	/* FIELDS */
	// Les ressources que peut avoir le joueur. 
	private int wood;
	private int clay;
	private int stone;
	private int gold;
	
	/* CONSTRUCTOR */
	public Inventaire () {
		this.wood = 0;
		this.clay = 0;
		this.stone = 0;
		this.gold = 0;
	}
	
	/* GETTERS */
	public int getWood () {return this.wood;}
	public int getClay () {return this.clay;}
	public int getStone () {return this.stone;}
	public int getGold () {return this.gold;}
	
	/* ADDERS */
	/**
	 * addWood(int wood) ajoute le bois dans l'inventaire du joueur apres verification.
	 * @param wood est le nombre de bois que le joueur a obtenu dans la zone.
	 * @return -1 si erreur, sinon modifie le nombre de bois que le joueur possede et le retourne. 
	 */
	private int addWood (int wood) {
		if (wood >= 0 && wood <= (Settings.MAX_DICE * Settings.MAX_FIGURINE_IN_ZONE)) {
			this.wood += wood/3;
			return this.wood;
		}
		return -1;
	}
	
	/**
	 * addClay(int clay) ajoute l'argile dans l'inventaire du joueur apres verification.
	 * @param clay est le nombre d'argile que le joueur a obtenu dans la zone.
	 * @return -1 si erreur, sinon modifie le nombre d'argile que le joueur possede et le retourne. 
	 */
	private int addClay (int clay) {
		if (clay >= 0 && clay <= (Settings.MAX_DICE * Settings.MAX_FIGURINE_IN_ZONE)) {
			this.clay += clay/4;
			return this.clay;
		}
		return -1;
	}
	
	/**
	 * addStone(int stone) ajoute la pierre dans l'inventaire du joueur apres verification.
	 * @param stone est le nombre de pierre que le joueur a obtenu dans la zone.
	 * @return -1 si erreur, sinon modifie le nombre de pierre que le joueur possede et le retourne. 
	 */
	private int addStone (int stone) {
		if (stone >= 0 && stone <= (Settings.MAX_DICE * Settings.MAX_FIGURINE_IN_ZONE)) {
			this.stone += stone/5;
			return this.stone;
		}
		return -1;
	}
	
	/**
	 * addGold(int gold) ajoute l'or dans l'inventaire du joueur apres verification.
	 * @param gold est le nombre d'or que le joueur a obtenu dans la zone.
	 * @return -1 si erreur, sinon modifie le nombre d'or que le joueur possede et le retourne. 
	 */
	private int addGold (int gold) {
		if (gold >= 0 && gold <= (Settings.MAX_DICE * Settings.MAX_FIGURINE_IN_ZONE)) {
			this.gold += gold/6;
			return this.gold;
		}
		return -1;
	}
	
	/**
	 * addRessource(int n, int resourceIndex) ajoute des ressources au joueur en fonction du resultat du dé.
	 * 
	 * @param n le resultat du dé, ressourceIndex le type de la ressource obtenu.
	 * @return -1 si erreur, sinon retourne le resultat de la fonction d'ajout de
	 * la ressource selectionnee. 
	 */
	public int addResource (int n, int resourceIndex) {
		if (n <= 0 )
			return -1;
		if (resourceIndex == 0) {
			return this.addWood(n);
		} else if (resourceIndex == 1) {
			return this.addClay(n);
		} else if (resourceIndex == 2) {
			return this.addStone(n);
		} else if (resourceIndex == 3) {
			return this.addGold(n);
		} else {
			return -1;
		}
	}
}
