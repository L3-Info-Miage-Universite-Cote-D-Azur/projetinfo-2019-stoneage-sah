package game.zone;

import java.util.Arrays;

import game.Dice;
import game.Ressource;
import inventory.Inventory;
import player.Player;
import printer.Printer;

/**
 * La classe ZoneRessource represente les zones de matieres premieres du jeu. 
 * @author groupe SAH
 */

public class ZoneRessource extends ZoneManyPlayer{

	private int divisor;//Le diviseur des ressources de la zone.
	private Ressource ressource;//La ressource de la zone.
	private final Dice dice; 

	/* CONSTRUCTOR */
	public ZoneRessource(String name, Ressource ressource, int availableSpace, Dice dice){
		super(name,availableSpace);
		this.ressource = ressource;
		this.divisor= ressource.getDivisor();
		this.dice = dice;
	}



	/**
	 * playerRecoveryFigurine rend les figurines au joueur dans la zone. 
	 * @param player : le joueur concerne.
	 */
	public void playerRecoveryFigurine(Player player, Inventory inventory) {
		int number = super.howManyPlayerFigurine(player);

		if (number > 0)
		{
			int[] diceValue = dice.rollDice(number);
			int sum=0;

			for (int value : diceValue) { sum += value; }
			sum += inventory.getTools().useTools(player,sum,ressource);//On demande si le joueur veut utiliser ses outils.
			int total=(int) (sum/divisor);

			super.removeFigurine(player);
			inventory.addRessource(ressource, total);//On ajoute la ressource au joueur.
			Printer.getPrinter().println("Le joueur "+player.getName()+" a eu comme lancer de de: "+Arrays.toString(diceValue));
			Printer.getPrinter().println("Il recolte donc: "+total+" "+ressource);
		}
	}
}