package player;

import client.CarteCivilisationWoodIA;
import client.CiviIA;
import client.IA;
import client.NoobIA;
import client.RandomIA;
import client.RessourceIA;
import game.Settings;
import inventory.InventoryIA;
import printer.Printer;

/**
 * Player represente le joueur sur le plateau.
 * @author Mentra20
 *
 */
public class Player extends PlayerStruct {

	/* FIELD */
	private IA ia;
	private PlayerIA playerIA;

	/* CONSTRUCTOR */
	public Player(String name,InventoryIA inventoryIA)
	{
		super(name);
		playerIA = new PlayerIA(name);
		this.initIA(inventoryIA);
	}
	
	/* CONSTRUCTOR FOR STATS*/
    public Player(String name,InventoryIA inventoryIA,int iaPlayer)
    {
        super(name);
        playerIA = new PlayerIA(name);
		switch(iaPlayer) {
		case 0: 
			this.ia = new RandomIA(this.getPlayerIA(),inventoryIA);
			break;
		case 1:
			this.ia = new NoobIA(this.getPlayerIA(),inventoryIA);
			break;
		case 2:
			this.ia = new CarteCivilisationWoodIA(this.getPlayerIA(),inventoryIA);
			break;
		case 3:
			this.ia = new CiviIA(this.getPlayerIA(),inventoryIA);
			break;
		case 4: //IA qui ne finit pas la parti seul donc non utiliser pour cette iteration
			this.ia = new RessourceIA(this.getPlayerIA(),inventoryIA);
			break;
		default:
			this.ia = null;
		}
    }

	/* METHODS */
	/**
	 * IncreaseMaxFigurine() incremente le nombre de figurine du joueur si cela est possible.
	 * @return Retourne faux si le nombre de Figurine max a ete atteind, vrai sinon.
	 */
	@Override
	public boolean increaseMaxFigurine () 
	{
		playerIA.increaseMaxFigurine();
		return super.increaseMaxFigurine();
	}

	/**
	 * Met a jour le nomber de figurine qui reste a placer. 
	 * @param number : le nombre de figurine a placer. 
	 */
	@Override
	public void placeFigurine (int number) 
	{
		super.placeFigurine(number);
		playerIA.placeFigurine(number);
	}


	/**
	 * permet d'ajouter number figurine dans celles a placer. 
	 * @param number : le nombre de figurine a recuperer. 
	 */
	@Override
	public void recoveryFigurine (int number)
	{
		super.recoveryFigurine(number);
		playerIA.recoveryFigurine(number);
	}

	/* GETTERS */
	public IA getIA(){return this.ia;}
	public PlayerIA getPlayerIA() { return playerIA; }

	/* SETTERS */
	@Override
	public void addScore(int number) 
	{ 
		super.addScore(number);
		playerIA.addScore(number);
	}
	@Override
	public void subScore(int number) 
	{ 
		super.subScore(number);
		playerIA.subScore(number);
	}

	public void setIA(IA ia) {
		this.ia = ia;
	}
	
	/**
	 * Renvoie une IA parmi celles presentes alÃƒÂ©atoirement
	 * @return Renvoie une instance implementant l'interface IA
	 */
	public void initIA(InventoryIA inventoryIA) 
	{
		int numberIA = 4; //nombre d'ia possible
		
		int choose = Settings.RAND.nextInt(numberIA);
		switch(choose) {
		case 0: 
			this.ia = new RandomIA(this.getPlayerIA(),inventoryIA);
			break;
		case 1:
			this.ia = new NoobIA(this.getPlayerIA(),inventoryIA);
			break;
		case 2:
			this.ia = new CarteCivilisationWoodIA(this.getPlayerIA(),inventoryIA);
			break;
		case 3:
			this.ia = new CiviIA(this.getPlayerIA(),inventoryIA);
			break;
		case 4: //IA qui ne finit pas la parti seul donc non utiliser pour cette iteration
			this.ia = new RessourceIA(this.getPlayerIA(),inventoryIA);
			break;
		default:
			this.ia = null;
		}
		Printer.getPrinter().println("Le joueur " + this.getName() + " a ete creer avec la strategie " + this.getIA() + ".");


	}

}
