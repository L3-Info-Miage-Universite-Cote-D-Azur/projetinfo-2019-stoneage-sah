package game;

import client.IA;

/**
 * La classe Player represente le joueur et ses attributs. 
 */

public class Player 
{
    /* FIELDS */
    private Inventory inventory;
    private int maxFigurine;
    private int currentFigurine;
    private String name;
    private IA ia; 
    private boolean[] hadPlaced;
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
        this.inventory = new Inventory();
        this.hadPlaced = new boolean[Settings.NB_ZONES];
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
     * ableToPlaceFigurine() permet de savoir si il est possible de placer number figurine selon ce qui lui reste a placer. 
     * @param number : le nombre de figurine a placer. 
     * @return true si la soustraction donne un entier positif ou nul, false sinon
     */ 
    public boolean ableToPlaceFigurine (int indexZone)
    {
        return this.getCurrentFigurine() > 0 && this.hadPlaced[indexZone] != true;
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
    public Inventory getInventory() {return this.inventory;}
    public int getMaxFigurine() {return this.maxFigurine;}
    public int getCurrentFigurine() {return this.currentFigurine;}
    public String getName() {return this.name;}
    public IA getIA() {return this.ia;}
    public boolean[] getHadPlaced() {return this.hadPlaced;}
    public int getScore() { return score; }

    /* SETTERS */
    public void setHadPlaced (int index, boolean b) {this.hadPlaced[index] = b;}
    public void addScore(int number) { this.score += number; }
    public void subScore(int number) { this.score -= number; }

    /* RESETTERS */
    public void resetHadPlaced() 
    {
        for(int i = 0; i < this.hadPlaced.length; i++)
            this.hadPlaced[i] = false;
    }
    
    
}
