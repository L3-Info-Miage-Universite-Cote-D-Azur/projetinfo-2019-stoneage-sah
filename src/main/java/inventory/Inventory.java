package inventory;

import game.CarteCivilisation;
import game.Ressource;

/*
 * Inventory represente l'inventaire du joueur.
 */
public class Inventory extends InventoryStruct {
	/* FIELD */
	InventoryIA inventoryIA;
	private Tools tools;
	
	/* CONSTRUCTOR */
	public Inventory() {
		super();
		tools = new Tools();
		inventoryIA = new InventoryIA(tools.getToolsIA());
	}
	
	/* GETTERS */
	public Tools getTools() { return tools; }
	public InventoryIA getInventoryIA(){ return inventoryIA; }
	
	@Override
	public void addCardCivilisation(CarteCivilisation card){
		super.addCardCivilisation(card);
		inventoryIA.addCardCivilisation(card);
	}

	/**
	 * La fonction addRessource ajoute number ressource a l'inventaire du joueur. 
	 * @param ressource la ressource 
	 * @param number le nombre de ressources gagnee. 
	 */
	@Override
	public void addRessource(Ressource ressource,int number){
		super.addRessource(ressource,number);
		inventoryIA.addRessource(ressource,number);
	}

	/**
	 * La fonction subRessource soustrait number ressource a l'inventaire du joueur. 
	 * @param ressource la ressource
	 * @param number le nombre de ressources retiree. 
	 */
	@Override
	public void subRessource(Ressource ressource,int number){
		super.subRessource(ressource,number);
		inventoryIA.subRessource(ressource,number);
	}
	@Override
	public void incrementBuilding(){
		super.incrementBuilding();
		inventoryIA.incrementBuilding();
	}
	
	@Override
	public void incrementCardRessource(){
		super.incrementCardRessource();
		inventoryIA.incrementCardRessource();
	}
	
	@Override
	public void decrementCardRessource(){
		super.decrementCardRessource();
		inventoryIA.decrementCardRessource();
	}
}

