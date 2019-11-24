package game;

import client.IA;

/**
 * La classe Player represente le joueur et ses attributs. 
 */

public class Player 
{
    /* FIELDS */
    private int maxFigurine;
    private int currentFigurine;
    private String name;
    private IA ia; 
    private int score;
    
    /* CONSTRUCTOR */
    public Player(String name, IA ia)
    {
        this.name = name;
        // Nombre maximum de figurine dont le joueur dispose.
        this.maxFigurine = Settings.START_FIGURINE;
        // Nombre de figurine qu'il reste a placer. 
        this.currentFigurine = Settings.START_FIGURINE;
        this.ia = ia;
        score = 0;
    }

    /* METHODS */
    /**
     * IncreaseMaxFigurine() incremente le nombre de figurine du joueur si cela est possible.
     * @return Retourne faux si le nombre de Figurine max a ete atteind, vrai sinon.
     */
    public boolean increaseMaxFigurine () 
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
    public void placeFigurine (int number) 
    {
        this.currentFigurine -= number;
    }
    
    
    /**
     * permet d'ajouter number figurine dans celles a placer. 
     * @param number : le nombre de figurine a recuperer. 
     */
    public void recoveryFigurine (int number)
    {
        this.currentFigurine += number;
    }

    /* GETTERS */
    public int getMaxFigurine() {return this.maxFigurine;}
    public int getCurrentFigurine() {return this.currentFigurine;}
    public String getName() {return this.name;}
    public IA getIA() {return this.ia;}
    public int getScore() { return score; }

    /* SETTERS */
    public void addScore(int number) { this.score += number; }
    public void subScore(int number) { this.score -= number; }


    
}
