package player;
import game.Settings;

/**
 * La classe abstract PlayerStruct represente le joueur dans le jeu.
 * @author Mentra20
 *
 */
public abstract class PlayerStruct {

	/* FIELDS */
	private int maxFigurine;
	private int currentFigurine;
	private String name;
	private int score;

	/* CONSTRUCTOR */
	protected PlayerStruct(String name)
	{
		this.name = name;
		// Nombre maximum de figurine dont le joueur dispose.
		this.maxFigurine = Settings.START_FIGURINE;
		// Nombre de figurine qu'il reste a placer. 
		this.currentFigurine = Settings.START_FIGURINE;
		score = 0;
	}

	/* METHODS */
	/**
	 * IncreaseMaxFigurine() incremente le nombre de figurine du joueur si cela est possible.
	 * @return Retourne faux si le nombre de Figurine max a ete atteind, vrai sinon.
	 */
	protected boolean increaseMaxFigurine () 
	{
		if (this.maxFigurine == Settings.MAX_FIGURINE)
			return false;
		this.maxFigurine++;
		this.currentFigurine++;
		return true;
	}

	/**
	 * Met a jour le nomber de figurine qui reste a placer. 
	 * @param number : le nombre de figurine a placer. 
	 */
	protected void placeFigurine (int number) 
	{
		this.currentFigurine -= number;
	}


	/**
	 * permet d'ajouter number figurine dans celles a placer. 
	 * @param number : le nombre de figurine a recuperer. 
	 */
	protected void recoveryFigurine (int number)
	{
		this.currentFigurine += number;
	}

	/* GETTERS */
	public int getMaxFigurine() {return this.maxFigurine;}
	public int getCurrentFigurine() {return this.currentFigurine;}
	public String getName() {return this.name;}
	public int getScore() { return score; }

	/* SETTERS */
	protected void addScore(int number) { this.score += number; }
	protected void subScore(int number) { this.score -= number; }

}