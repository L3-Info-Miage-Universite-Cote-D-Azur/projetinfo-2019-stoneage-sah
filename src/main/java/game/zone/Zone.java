package game.zone;

import inventory.Inventory;
import player.Player;

/**
 * Zone est une classe abstract qui definie les zones du jeu de maniere generale.  
 * @author Groupe SAH
 */

public abstract class Zone {
	/* FIELD */
	private String name; 
	protected int availableSpace; 
	private int minimalFigurineRequierement;

	/* CONSTRUCTOR */
	protected Zone(String name, int availableSpace, int minimalFigurineRequierement){
		this.name = name;
		this.availableSpace = availableSpace;
		this.minimalFigurineRequierement = minimalFigurineRequierement;
	}

	/* GETTERS */
	public int getAvailableSpace(){return availableSpace;}
	public String getName(){return name;}
	public int getMinimalFigurineRequierement(){return minimalFigurineRequierement;}

	/* METHOD */
	/**
	 * ableToChooseZone renvoie true ou false si le joueur peut placer ses figurines ou non sur la zone. 
	 * @param player : le joueur concerne.
	 * @return Boolean : true si il peut choisir, false sinon.
	 */ 
	public boolean ableToChooseZone(Player player){
		return (this.availableSpace > 0) && (howManyPlayerFigurine(player) == 0);
	}

	/* STRING */
	/**
	 * Affichage textuel du joueur qui place dans la zone un nombre de figurine
	 * @param player : player a afficher
	 * @param number : nombre a qui a etait placer
	 * @return phrase qui resume l'action
	 */
	public String stringPlaceFigurine(int number,Player player){
		String str = "Le joueur "+ player.getName() + " a place "+ number +" figurines dans la zone "+getName()+".";
		return str;
	}

	/* ABSTRACT METHODS */
	public abstract void placeFigurine(int number, Player player);
	public abstract int howManyPlayerFigurine(Player player);
	public abstract int playerRecoveryFigurine(Player player, Inventory inventory);
	public abstract void removeFigurine(Player player);

}