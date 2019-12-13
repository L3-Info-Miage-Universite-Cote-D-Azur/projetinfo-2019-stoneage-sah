package game.building;

import game.Ressource;
import game.Settings;

/**
 * Batiment qui contient directement un nombre de ressources non imposees mais entre 1 et 7 de base, point de victoire en fonction des ressources donnees
 */
public class BuildingRessourceChoosed extends BuildingSpecial 
{
	/* CONSTRUCTOR */
	public BuildingRessourceChoosed(int type, int lengthNeededRessource) {
		super(2);
		super.neededRessource = new Ressource[lengthNeededRessource];
		for (int i = 0; i < super.neededRessource.length; i++)
		{
			super.neededRessource[i] = null;
		}
	}

	public BuildingRessourceChoosed() {
		this(2, Settings.NB_MAX_RESSOURCE_BUILDING);
	}

	/* METHODS */
	/**
	 * Permet de verifier si le joueur a bien mis au moins 1 ressource
	 * @param inv la copie de l'inventaire du joueur
	 * @return regarde si le joueur peut recupere la carte
	 */
	public boolean checkNeededRessource (int[] inv) 
	{
		for (int i = 0; i < inv.length; i++)
		{
			if (inv[i] > 0)
				return true;
		}
		return false;
	}
	
	public boolean checkRessourceChoosed (Ressource[] ressources, int[] inv)
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
		
		// Check de la longueur
		if (ressources.length == 0 || ressources.length > this.neededRessource.length)
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
	
	public String getName() {return "'Batiment ressources au choix'";}
}
