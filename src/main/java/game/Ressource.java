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
}