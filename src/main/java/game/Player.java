package game;
import client.*;

/**
 * La class Player est une class qui sert a  gerer un joueur, le joueur contient un inventaire, 
 * un nombre de figurines et un tableau dans lequel il a place ses figurines
 * @author theoricien
 */
public class Player {
    
    private int figurine;
    private int figurineInZone[];

    private Inventaire inv;
    private String name;
    private IA ia; 
    
    /* CONSTRUCTOR */
    public Player (String name, IA ia) {
        this.figurine = Settings.START_FIGURINE;
        this.figurineInZone = new int[Settings.NB_ZONES];
        this.inv = new Inventaire();
        this.name = name;
        this.ia = ia;
    }
    
    /* METHODS */
    /**
     * addFigurineInZone(int nbZone, int n) est une fonction qui mise des ouvriers dans une zone
     * @param nbZone represente le numero de la zone
     * @param n represente le nombre de figurine a placer
     */
    public void addFigurineInZone(int nbZone, int n) {
        if ((nbZone < Settings.NB_ZONES && nbZone >= 0) &&
        		(n <= this.figurine && n > 0)) 
        {
            this.figurineInZone[nbZone] += n;
            this.figurine -= n;
        }
    }
    
    /**
     * subFigurineInZone(int nbZone, int n) est une fonction qui recupere les ouvriers dans une zone
     * @param nbZone represente le numero de la zone
     */
    public void subFigurineInZone(int nbZone) {
        if (nbZone < Settings.NB_ZONES && nbZone >= 0) 
        {
            this.figurine += this.figurineInZone[nbZone];
            this.figurineInZone[nbZone] = 0;
        }
    }
    
    /* GETTERS */
    public int getFigurine() { return this.figurine; }
    public int[] getFigurineInZone () {return this.figurineInZone;}
    public Inventaire getInv () {return this.inv;}
    public String getName () {return this.name;}
    public IA getIA() { return this.ia;}
}
