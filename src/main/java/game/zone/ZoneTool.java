package game.zone;

import game.Inventory;
import game.Player;
import game.Settings;
import game.Tools;

/**
 * La classe represente la zone du fabricant d'outils. 
 * @author groupe SAH
 */
public class ZoneTool extends ZoneOnePlayer{
	/* CONSTRUCTOR */
	public ZoneTool(String name){
		super(name,Settings.MAX_ZONETOOL_SPACE);
	}


	/**
	 * playerRecoveryFigurine rend les figurines au joueur dans la zone. 
	 * @param player : le joueur concerne.
	 */ 
	public void playerRecoveryFigurine(Player player, Inventory inventory) {
		int number = super.howManyPlayerFigurine(player);

		if (number > 0){
			super.removeFigurine(player);
			Tools tools = inventory.getTools();
			tools.incrementTool();//On incremente son nombre d'outils.
			System.out.println(tools.incrementToolsToString(player.getName()));
		}
	}
}