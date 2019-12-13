package game.building;

import java.util.ArrayList;
import java.util.stream.IntStream;

import game.Ressource;

/**
 * Batiment qui contient directement un nombre de ressources non imposees et un nombre de point de victoire connu
 */
public class BuildingRessourceNotImposed extends BuildingSpecial
{
	private int howManyDifferentRessource;

	/* CONSTRUCTOR */
	public BuildingRessourceNotImposed(int howManyDifferentRessource, int lengthNeededRessource) {
		super(1);
		this.howManyDifferentRessource = howManyDifferentRessource;
		super.neededRessource = new Ressource[lengthNeededRessource];
	}

	/* METHODS */

	/**
	 * Permet de verifier si le joueur a bien mis le nombre de ressources differentes, et assez
	 * @return regarde si le joueur peut recupere la carte
	 */
	public boolean checkNeededRessource (int[] inv)
	{
		/* INITIALISATION */
		
		// LISTE QUI VA CONTENIR UNIQUEMENT howManyDifferentRessource RESSOURCE, ON CHECK AVEC .contains()
		ArrayList<Ressource> r = new ArrayList<Ressource>();
		for (int i = 0; i < this.howManyDifferentRessource; i++)
		{
			r.add(null);
		}
		
		/* TREATMENT */
		ArrayList<int[]> comb = MathPlus.combinaisons(inv, this.howManyDifferentRessource);
		for (int[] arr : comb)
		{
			boolean isContinue = false;
			for (int i = 0; i < arr.length; i++)
				if (arr[i] == 0)
				{
					isContinue = true;
					break;
				}
			if (isContinue == true)
				continue;
			if (IntStream.of(arr).sum() >= this.neededRessource.length)
			{
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Permet de verifier les ressources que le joueur veut miser
	 * @param ressources les ressources misees
	 * @param inv une copie de l'inventaire du joueur
	 * @return true s'il peut miser, false sinon
	 */
	public boolean checkRessourceNotImposed (Ressource[] ressources, int[] inv)
	{
		// Check des bonnes ressources
		for (Ressource r : ressources)
		{
			if (r != Ressource.WOOD &&
					r != Ressource.CLAY &&
					r != Ressource.STONE &&
					r != Ressource.GOLD)
				return false;
		}
		// Check si on a assez de ressources differentes
		ArrayList<Ressource> howManyRessources = new ArrayList<Ressource>();
		for (Ressource r : ressources)
		{
			if (howManyRessources.contains(r) == false)
			{
				howManyRessources.add(r);
			}
		}
		if (howManyRessources.size() != this.howManyDifferentRessource)
			return false;
		// Check de la longueur
		if (ressources.length != super.neededRessource.length)
			return false;
		
		// Check si le joueur possede ces ressources
		for (int i = 0; i < ressources.length; i++)
		{
			inv[ressources[i].getIndex()] -= 1;
			if (inv[ressources[i].getIndex()] < 0)
				return false;
		}
		return true;
	}

	/* GETTERS */
	public int getHowManyDifferentRessource () {return this.howManyDifferentRessource;}
	public String getName() {return "'Bâtiment ressources non imposees: " + this.neededRessource.length + " Ressources; " + this.howManyDifferentRessource +" Ressources differentes'";}	

	/* SETTERS */
	public void setHowManyDifferentRessource (int hmdr) {this.howManyDifferentRessource = hmdr;}
}
