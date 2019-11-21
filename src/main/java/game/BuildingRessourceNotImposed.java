package game;

import java.util.ArrayList;

/**
 * Batiment qui contient directement un nombre de ressources imposees et un nombre de point de victoire connu
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
	 * @return
	 */
	public boolean checkNeededRessource ()
	{
		/* INITIALISATION */
		// LISTE QUI VA CONTENIR UNIQUEMENT howManyDifferentRessource RESSOURCE, ON CHECK AVEC .contains()
		ArrayList<Ressource> r = new ArrayList<Ressource>();
		for (int i = 0; i < this.howManyDifferentRessource; i++)
		{
			r.add(null);
		}
		
		/* TREATMENT */
		for (int i = 0, r_index = 0; i < super.neededRessource.length; i++)
		{
			// SI LA RESSOURCE N'EST PAS DANS NOTRE LISTE
			if (super.neededRessource[i] != null  &&
					r.contains(super.neededRessource[i]) == false)
			{			
				// SI ON A UNE RESSOURCE DIFFERENTE DE TROP
				if (r_index == this.howManyDifferentRessource)
				{
					// IL Y A ERREUR
					return false;
				}
				
				// ON L'AJOUTE
				r.set(r_index, super.neededRessource[i]);
				r_index++;
			}
		}
		
		// SI ON A UN EMPLACEMENT VIDE
		if (r.get(this.howManyDifferentRessource - 1) == null)
		{
			// ON A PAS ASSEZ DE RESSOURCE DIFFERENTES
			return false;
		}
		
		return true;
	}
	
	/* GETTERS */
	public int getHowManyDifferentRessource () {return this.howManyDifferentRessource;}
	
	/* SETTERS */
	public void setHowManyDifferentRessource (int hmdr) {this.howManyDifferentRessource = hmdr;}
}
