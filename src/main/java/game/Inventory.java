package game;

/**
 * La classe Inventory gere l'inventaire du joueur.
 */

public class Inventory {
	
	
	/* FIELDS */
	// Les ressources que peut avoir le joueur. 
	private int[] ressources;
	
	/* CONSTRUCTOR */
	public Inventory () {
		ressources = new int[Settings.NB_RESSOURCES];
		ressources[Ressource.FOOD.getIndex()]=15;
	}
	
	//GETTERS
	public int getRessource(Ressource ressource){ return ressources[ressource.getIndex()] ;}
	public int[] getCopyRessources() {return ressources.clone();}
	
	//ADDERS
	/**
	 * La fonction addRessource ajoute number ressource a l'inventaire du joueur. 
	 * @param ressource la ressource 
	 * @param number le nombre de ressources gagnee. 
	 */
	public void addRessource(Ressource ressource,int number){
		ressources[ressource.getIndex()]+=number;
	}
	
	//SUBSTRACTORS
	/**
	 * La fonction addRessource soustrait number ressource a l'inventaire du joueur. 
	 * @param ressource la ressource
	 * @param number le nombre de ressources retiree. 
	 */
	public void subRessource(Ressource ressource,int number) {
		ressources[ressource.getIndex()]-=number;
	}
	
	/**
	 * Verification si la ressource reste en nombre possitif
	 * @param ressource Ressource que l'on dois soustraire.
	 * @param number Nombre de ressource a soustraire.
	 * @return true : Reste positif. false : deviens negatif.
	 * 
	 */
	public boolean ableToSubRessource(Ressource ressource,int number) {
		if(ressources[ressource.getIndex()] < number) return false;
		return true;
	}
	
	/**
	 * Ressource utilisable total 
	 * (utiliser pour voir si le joueur peut nourire toutes les figurine quand il a plus de nouriture)
	 * @return somme des ressource usable pour remplacer la nouriture
	 */
	public int availableResourceToFeed() {
		int sum=0;
		for(int i=0; i<=3;i++) {
			sum+=ressources[i];
		}
		return sum;
	}
}

