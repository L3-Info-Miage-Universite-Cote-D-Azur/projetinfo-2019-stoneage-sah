package game.zone;

import game.Settings;
import inventory.Inventory;
import inventory.Tools;
import player.Player;
import printer.Printer;

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
			Printer.getPrinter().print(tools.incrementToolsToString(player.getName()));
		}
	}
}