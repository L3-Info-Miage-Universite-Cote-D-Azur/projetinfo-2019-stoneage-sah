package Game;

public class Player {
    
    private int figurine;
    private int figurineInZone[];
    private Inventaire inv;
    
    /* CONSTRUCTOR */
    public Player () {
        this.figurine = Settings.START_FIGURINE;
        this.figurineInZone = new int[Settings.NB_ZONES];
        this.inv = new Inventaire();
    }
    
    /* METHODS */
    public void addFigurineInZone(int nbZone, int n) {
        if ((nbZone<Settings.NB_ZONES && nbZone>=0) && (n<=this.figurine && n>0)) {
            this.figurineInZone[nbZone] += n;
            this.figurine -= n;
        }
    }
    
    public void subFigurineInZone(int nbZone) {
        if (nbZone<Settings.NB_ZONES && nbZone>=0) {
            this.figurine += this.figurineInZone[nbZone];
            this.figurineInZone[nbZone] = 0;
        }
    }
    
    /* GETTERS */
    public int getFigurine() { return figurine; }
    public int[] getFigurineInZone () {return this.figurineInZone;}
    public Inventaire getInv () {return this.inv;}
}