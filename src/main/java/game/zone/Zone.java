package game.zone;

import game.Inventory;
import game.Player;

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
	 * @param Player player : le joueur concerne.
	 * @return Boolean : true si il peut choisir, false sinon.
	 */ 
	public boolean ableToChooseZone(Player player){
		return (this.availableSpace > 0) && (howManyPlayerFigurine(player) == 0);
	}

	/* ABSTRACT METHODS */
	public abstract void placeFigurine(int number, Player player);
	public abstract int howManyPlayerFigurine(Player player);
	public abstract void playerRecoveryFigurine(Player player, Inventory inventory);
	public abstract void removeFigurine(Player player);
}