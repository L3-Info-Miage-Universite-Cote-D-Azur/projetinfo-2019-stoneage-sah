package game;

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
	
	public BuildingRessourceChoosed(int type) {
		this(type, Settings.NB_MAX_RESSOURCE_BUILDING);
	}
	
	/* METHODS */
	/**
	 * Permet de verifier si le joueur a bien mis au moins 1 ressource
	 * @return
	 */
	public boolean checkNeededRessource () 
	{
		return super.neededRessource[0] != null;
	}
}
