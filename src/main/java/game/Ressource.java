package game;

/**
 * Ressource presente dans l'age de pierre.
 * @author Yohann
 *
 */
public enum Ressource {
    WOOD(0),CLAY(1),STONE(2),GOLD(3),FOOD(4),FIELD(5);

    private final int id;
    
    Ressource(int id){
        this.id = id;
    }
    /**
     * Representation textuel de la ressource.
     * @return le nom de la ressource.
     */
    public String toString(){
        String[] name = new String[] {"bois","argile","pierre","or","nourriture","marqueur nourriture"};
        return name[id];
    }

    /**
     * Fonction qui fait le raprochement entre une ressource et l'index de stockage dans l'inventaire
     * @return index dans l'inventaire
     */
    public int getIndex() {
        return id;
    }

    /**
     * Renvoie la ressource correspondant a l'index de l'inventaire
     * @param index de l'inventaire que l'on veux savoir la ressource
     * @return ressource selectionner par l'index
     */
    public static Ressource indexToRessource(int id) {
        Ressource[] ressource = new Ressource[]{WOOD,CLAY,STONE,GOLD,FOOD,FIELD};
        if(id<0 || id >=ressource.length) return null;
        return ressource[id];
    }

    /**
     * getDivisor renvoie le diviseur lie a la ressource premiere. 
     * @return int diviseur, le diviseur de la ressource. 
     */
    public int getDivisor(){
        int[] divisor = new int[] {3,4,5,6,2,1};
        return divisor[id];
    }
} 
