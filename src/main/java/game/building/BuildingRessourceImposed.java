package game.building;

import game.Ressource;

/**
 * Batiment qui contient directement un nombre de ressources imposees et un nombre de point de victoire connu
 */
public class BuildingRessourceImposed extends Building 
{
	/* CONSTRUCTOR */
	public BuildingRessourceImposed(int vP, Ressource... r)
	{
		super(0);
		super.victoryPoints = vP;
		super.neededRessource = new Ressource[r.length];
		for (int i = 0; i < r.length; i++)
		{
			super.neededRessource[i] = r[i];
		}
	}

	/**
	 * Verifie si le joueur peut retirer la carte et ses points
	 * @param ressources copie de l'inventaire du joueur
	 * @return true si il peut, false sinon
	 */
	public boolean checkNeededRessource (int[] ressources)
	{
		for (int i = 0; i < super.neededRessource.length; i++)
		{
			ressources[super.neededRessource[i].getIndex()] -= 1;
			if (ressources[super.neededRessource[i].getIndex()] < 0)
				return false;
		}
		return true;
	}
}
