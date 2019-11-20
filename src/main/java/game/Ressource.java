package game;

/**
 * Ressource presente dans l'age de pierre.
 * @author Yohann
 *
 */
public enum Ressource {
    WOOD,CLAY,STONE,GOLD,FOOD,FIELD;
	
	/**
	 * Representation textuel de la ressource.
	 * @return le nom de la ressource.
	 */
    public String toString(){
        switch(this){
            case WOOD:
                return "bois";
            case CLAY:
                return "argile";
            case STONE :
                return "pierre";
            case GOLD :
                return "or";
            case FOOD:
            	return "nourriture";
            case FIELD:
            	return "marqueur nourriture";
            default :
                return "ERROR!!";
        }
    }
    
    /**
     * Fonction qui fait le raprochement entre une ressource et l'index de stockage dans l'inventaire
     * @return index dans l'inventaire
     */
    public int getIndex() {
    	switch(this){
        case WOOD:
            return 0;
        case CLAY:
            return 1;
        case STONE :
            return 2;
        case GOLD :
            return 3;
        case FOOD:
        	return 4;
        case FIELD:
        	return 5;
        default :
            return -1;
    	}
    }

    /**
     * Renvoie la ressource correspondant a l'index de l'inventaire
     * @param index de l'inventaire que l'on veux savoir la ressource
     * @return ressource selectionner par l'index
     */
    public static Ressource indexToRessource(int index) {
    	switch(index){
        case 0:
            return WOOD;
        case 1:
            return CLAY;
        case 2 :
            return STONE;
        case 3 :
            return GOLD;
        case 4:
        	return FOOD;
        case 5:
        	return FIELD;
        default :
            return null;
    	}
    }
    
    /**
     * getDivisor renvoie le diviseur lie a la ressource premiere. 
     * @return int diviseur, le diviseur de la ressource. 
     */
    public int getDivisor(){
        switch(this){
            case WOOD:
                return 3;
            case CLAY:
                return 4;
            case STONE :
                return 5;
            case GOLD :
                return 6;
            case FOOD:
                return 2;
            case FIELD:
                return 1;
            default :
                System.out.println("ERROR");
                return -1;
        }
    }
}