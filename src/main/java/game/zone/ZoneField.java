package game.zone;

import game.Inventory;
import game.Player;
import game.Ressource;
import game.Settings;

/**
 * ZoneField represente la zone des champs dans le jeu. 
 * @author Groupe SAH
 */

public class ZoneField extends ZoneOnePlayer{
	/* FIELD */
	Ressource foodMark;//Les marqueurs de nourritures

	/* CONSTRUCTOR */
	public ZoneField(String name,Ressource ressource){
		super(name, Settings.MAX_ZONEFIELD_SPACE);
		this.foodMark = ressource;
	}

	/**
	 * playerRecoveryFigurine rend les figurines au joueur dans la zone. 
	 * @param player : le joueur concerne.
	 * @param inventory : l'inventaire du joueur concerne.
	 */
	public void playerRecoveryFigurine(Player player, Inventory inventory) {
		int number = super.howManyPlayerFigurine(player);

		if (number > 0){
			super.removeFigurine(player);
			inventory.addRessource(foodMark,1);
			System.out.println("Le joueur "+ player.getName() +" a maintenant "+ inventory.getRessource(foodMark)+ " "+ foodMark.toString()+".");
		}
	}
}
