package game;

import java.util.Hashtable;

/**
 * La classe Inventory gere l'inventaire du joueur.
 */

public class Inventory {
	
	
	/* FIELDS */
	// Les ressources que peut avoir le joueur. 
	private Hashtable<Ressource,Integer> ressources;
	
	/* CONSTRUCTOR */
	public Inventory () {
		ressources = new Hashtable<Ressource,Integer>();
	}
	
	//GETTERS
	public int getRessource(Ressource ressource){
	    if(ressources.get(ressource) == null){
	        return 0;
	    }
	    return ressources.get(ressource);
	}
	
	//ADDERS
	/**
	 * La fonction addRessource ajoute number ressource a l'inventaire du joueur. 
	 * @param ressource la ressource et number le nombre de ressources gagnee. 
	 */
	public void addRessource(Ressource ressource,int number){
	    ressources.put(ressource,getRessource(ressource)+number);
	}
	
	//SUBSTRACTORS
	/**
	 * La fonction addRessource soustrait number ressource a l'inventaire du joueur. 
	 * @param ressource la ressource et number le nombre de ressources retiree. 
	 */
	public void subRessource(Ressource ressource,int number) {
	    ressources.put(ressource,getRessource(ressource)-number);
	}
	
	/**
	 * Verification si la ressource reste en nombre possitif
	 * @param ressource Ressource que l'on dois soustraire.
	 * @param number Nombre de ressource a soustraire.
	 * @return true : Reste positif. false : deviens negatif.
	 * 
	 */
	public boolean ableToSubRessource(Ressource ressource,int number) {
		if(ressources.get(ressource) < number) return false;
		return true;
	}
}