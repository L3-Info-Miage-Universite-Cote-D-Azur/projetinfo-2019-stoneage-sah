package game;


/**
 * Les Building sont les batiments, ils sont caracterises par leur type:
 * -1: abstract Building
 * 0: BuildingRessourceImposed: batiment dont les points de victoire et ressources sont imposes
 * 1: BuildingRessourceNotImposed: batiment dont les ressources ne sont pas imposees, mais compte un nombre de ressources differentes a poser
 * 2: BuildingRessourceChoosed: batiment unique, il permet de deposer entre 1 et 7 ressources
 */
public abstract class Building 
{	
	/* FIELDS */
	protected int victoryPoints;
	// Tableau des ressources misees par le joueur
	protected Ressource[] neededRessource;
	protected int type;
	
	/* CONSTRUCTOR */
	public Building (int type)
	{
		this.type = type;
	}
	
	/* GETTERS */
	protected int getVP () {return this.victoryPoints;}
	protected Ressource[] getNeddedRessource() {return this.neededRessource;}
	protected int getType() {return this.type;}
	
	/* SETTERS */
	protected void setVP (int VP) {this.victoryPoints = VP;}
	protected void setNeededRessource (Ressource[] r) {this.neededRessource = r;}
	protected void setType (int t) {this.type = t;}
}