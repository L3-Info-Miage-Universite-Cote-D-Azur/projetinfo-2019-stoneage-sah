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
}