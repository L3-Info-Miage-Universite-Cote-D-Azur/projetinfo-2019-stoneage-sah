package game;

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
}
