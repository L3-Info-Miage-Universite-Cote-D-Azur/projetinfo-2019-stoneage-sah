
package game.zone;

import inventory.Inventory;
import player.Player;
import printer.Printer;

/**
 * ZoneOnePlayer est une classe abstract qui represente les zone ou il ne peut y avoir qu'un joueur.
 * @author Groupe SAH
 */

public abstract class ZoneOnePlayer extends Zone {
	/* FIELD */
	protected Player occupated;
	private int figurineInZone;

	/* CONSTRUCTOR */
	protected ZoneOnePlayer(String name, int availableSpace){
		super(name, availableSpace, availableSpace);
		figurineInZone=0;
	}

	/* METHODS */

	/**
	 * placeFigurine(int, Player) place number figurine appartenant a player dans la zone. 
	 * @param number : le nombre de figurine a mettre dans la zone.
	 * @param player : le joueur qui les mets. 
	 */ 
	public void placeFigurine(int number,Player player){
		Printer.getPrinter().println(super.stringPlaceFigurine(number,player));
		super.availableSpace -= number;
		figurineInZone += number;
		occupated = player;
		player.placeFigurine(number);
	}

	/**
	 * Retourne le nombre de figurines que player a dans la zone, 0 si il n'occupe pas la zone.
	 * @param player: le joueur dont on veut savoir le nombre de figurines dans la zone. 
	 * @return int : le nombre de figurine que player a dans la zone. 
	 */ 
	public int howManyPlayerFigurine(Player player){
		if(figurineInZone == 0 || occupated != player){
			return 0;
		}
		return figurineInZone;
	}

	/**
	 * Enleve les figurines du joueur player dans la zone. 
	 * @param player : le joueur a qui on retire les figurines de la zone. 
	 */ 
	public void removeFigurine(Player player){
		int number = howManyPlayerFigurine(player);
		if(number > 0)
		{
			figurineInZone -= number;
			super.availableSpace += number;
			occupated = null;
			player.recoveryFigurine(number);
		}
	}

	/* ABSTRACT METHODS */
	public abstract void playerRecoveryFigurine(Player player, Inventory inventory);

}