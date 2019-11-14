package game;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

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
	
	/**
	 * Renvoie la liste des ressource premiere (sans outil ou Field)
	 * @return liste des ressource premiere 
	 */
	public String[] toArrayRessource() {
		Set<Ressource> setRessources = ressources.keySet();
		String[] ressource = new String[setRessources.size()];

		Iterator<Ressource> it;
		it=setRessources.iterator(); // on cree un iterator sur les clés de ton hashmap
		int i=0;
		while(it.hasNext())
		{
			Ressource r = (Ressource) it.next();
			if(r!=Ressource.FIELD) {ressource[i]=r.toString();}
			i+=1;
		}
		return ressource;
		
	}
	
	/**
	 * Renvoie la liste des ressource premiere (sans outil ou Field)
	 * elle fonctionne avec toArrayRessource pour avoir a quel ressource l'indice du tableau correspond.
	 * @return nombre de ressource.
	 */
	public int[] toArrayNumber() {
		Set<Ressource> setRessources = ressources.keySet();
		int[] number = new int[setRessources.size()];

		Iterator<Ressource> it;
		it=setRessources.iterator(); // on cree un iterator sur les clés de ton hashmap
		int i=0;
		while(it.hasNext())
		{
			Ressource r = (Ressource) it.next();
			if(r!=Ressource.FIELD) {number[i]=ressources.get(r);}
			i+=1;
		}
		return number;
		
	}
	
	/**
	 * Renvoie la liste des ressource premiere (sans outil ou Field)
	 * elle fonctionne avec toArrayRessource et toArrayNumber pour avoir a quel ressource l'indice du tableau correspond.
	 * @return nombre de ressource.
	 */
	public Ressource[] toArrayRessourceEnum() {
		Set<Ressource> setRessources = ressources.keySet();
		Ressource[] ressource = new Ressource[setRessources.size()];

		Iterator<Ressource> it;
		it=setRessources.iterator(); // on cree un iterator sur les clés de ton hashmap
		int i=0;
		while(it.hasNext())
		{
			Ressource r = (Ressource) it.next();
			if(r!=Ressource.FIELD) {ressource[i]=r;}
			i+=1;
		}
		return ressource;
		
	}
}
