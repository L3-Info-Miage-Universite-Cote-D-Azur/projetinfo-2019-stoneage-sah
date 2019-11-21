package game;

/**
 * Cette class permet d'ajouter des methodes specifiques aux class BuildingRessourceNotImposed et BuildingRessourceChoosed
 * Attention, ici super.neededRessource represente les ressources que que le joueur investis
 */
public abstract class BuildingSpecial extends Building 
{
	/* CONSTRUCTOR */
	public BuildingSpecial(int type) {
		super(type);
	}
	
	/* METHODS */

	/**
	 * Cette methode ajoute une ressource dans super.neededRessource si une place exite
	 * @param r de type Ressource, elle definit la ressource a ajouter
	 * @return true si la ressource a ete ajoutee, false si il n'y a plus de place dans super.neededRessource
	 */
	protected boolean addRessource (Ressource r)
	{
		for (int i = 0; i < super.neededRessource.length; i++)
		{
			if (super.neededRessource[i] == null)
			{
				super.neededRessource[i] = r;
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Retourne le nombre de points de victoire en fonction de ce qu'il y a dans neededRessource
	 * @return
	 */
	protected int computeVP ()
	{
		int res = 0;
		for (Ressource r: super.neededRessource)
		{
			if (r == Ressource.WOOD)
				res += 3;
			else if (r != null)
				res += 5;
		}
		return res;
	}
	
	/* AFFECTATOR */
	
	/**
	 * Permet de mettre a jour les victoryPoints, est utilise une fois que le joueur a finit de mettre les ressources demandees
	 */
	protected void updateVP ()
	{
		super.victoryPoints = this.computeVP();
	}
}
