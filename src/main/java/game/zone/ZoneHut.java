package game.zone;

import game.Inventory;
import game.Player;
import game.Settings;

/**
 * ZoneHut represente la zone de la cabane de reproduction du jeu. 
 * @author Groupe SAH
 */

public class ZoneHut extends ZoneOnePlayer{

	/* CONSTRUCTOR */
	public ZoneHut(String name){
		super(name,Settings.MAX_ZONEHUT_SPACE);
	}

	/**
	 * playerRecoveryFigurine rend les figurines au joueur dans la zone. 
	 * @param player : le joueur concerne.
	 * @param inventory : l'inventaire du joueur concerne. (inutile ici)
	 */ 
	public void playerRecoveryFigurine(Player player, Inventory inventory) {
		int number = super.howManyPlayerFigurine(player);
		
		if (number > 0){
			super.removeFigurine(player);
			boolean ok = player.increaseMaxFigurine();//Est ce que le joueur a augmenter son nombre de figurines ? 

			if(ok) { System.out.println("Le joueur "+ player.getName() +" a maintenant "+ player.getMaxFigurine() +" figurine(s)");}
			else { System.out.println("Le joueur "+ player.getName() +" a deja le maximum de figurine");}
		}
	}
}