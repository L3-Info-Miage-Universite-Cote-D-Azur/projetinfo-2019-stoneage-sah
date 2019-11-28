package game.zone;

import java.util.Arrays;

import game.Dice;
import game.Inventory;
import game.Player;
import game.Ressource;

/**
 * La classe ZoneRessource represente les zones de matieres premieres du jeu. 
 * @author groupe SAH
 */

public class ZoneRessource extends ZoneManyPlayer{

	private int divisor;//Le diviseur des ressources de la zone.
	private Ressource ressource;//La ressource de la zone.

	/* CONSTRUCTOR */
	public ZoneRessource(String name, Ressource ressource, int availableSpace){
		super(name,availableSpace);
		this.ressource = ressource;
		this.divisor= ressource.getDivisor();
	}

	
	
	/**
	 * playerRecoveryFigurine rend les figurines au joueur dans la zone. 
	 * @param player : le joueur concerne.
	 */
	public void playerRecoveryFigurine(Player player, Inventory inventory) {
		int number = super.howManyPlayerFigurine(player);

		if (number > 0)
		{
			int[] dice = Dice.rollDice(Settings.RAND, number);
			int sum=0;

			for (int value : dice) { sum += value; }
			sum += inventory.getTools().useTools(player,sum,ressource);//On demande si le joueur veut utiliser ses outils.
			int total=(int) (sum/divisor);

			super.removeFigurine(player);
			inventory.addRessource(ressource, total);//On ajoute la ressource au joueur.
			System.out.println("Le joueur "+ressource+" a eu comme lancer de de: "+Arrays.toString(dice));
			System.out.println("Il recolte donc: "+total+" "+ressource);
		}
	}
}
