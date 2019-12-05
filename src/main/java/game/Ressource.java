package game;

/**
 * Ressource presente dans l'age de pierre.
 * @author Yohann
 *
 */
public enum Ressource {
  	//ressource est definit par 3 champ : (son id, son nom, son diviseur)
    WOOD(0,"bois",3),CLAY(1,"argile",4),STONE(2,"pierre",5),GOLD(3,"or",6),FOOD(4,"nourriture",2),FIELD(5,"marqueur nourriture",1);

    private final int id;
	private final String name;
    private final int divisor;
    
    Ressource(int id,String name, int divisor){
        this.id = id;
      	this.name = name;
    	this.divisor = divisor;
    }
    /**
     * Representation textuel de la ressource.
     * @return le nom de la ressource.
     */
    public String toString(){
        return name;
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
        return divisor;
    }
} 