package inventory;


import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

import game.CarteCivilisation;
import game.Ressource;

public class InventoryTest {
	
	private Inventory inv;

	@Test
	public void testAddRessource() {
		this.inv = new Inventory();

		//on test l'instanciation//
		assertEquals(this.inv.inventoryIA.getRessource(Ressource.FOOD), 15);
		assertEquals(this.inv.inventoryIA.getRessource(Ressource.WOOD), 0);
		assertEquals(this.inv.inventoryIA.getRessource(Ressource.STONE), 0);
		assertEquals(this.inv.inventoryIA.getRessource(Ressource.CLAY), 0);
		assertEquals(this.inv.inventoryIA.getRessource(Ressource.GOLD), 0);
		assertEquals(this.inv.getRessource(Ressource.FOOD), 15);
		assertEquals(this.inv.getRessource(Ressource.WOOD), 0);
		assertEquals(this.inv.getRessource(Ressource.STONE), 0);
		assertEquals(this.inv.getRessource(Ressource.CLAY), 0);
		assertEquals(this.inv.getRessource(Ressource.GOLD), 0);
		
		//on test l'ajout de ressource
		this.inv.addRessource(Ressource.WOOD, 5);
		this.inv.inventoryIA.addRessource(Ressource.WOOD, 5);
		assertEquals(this.inv.getRessource(Ressource.WOOD),5);
		assertEquals(this.inv.inventoryIA.getRessource(Ressource.WOOD),10);
	}
	
	@Test
	public void testSubRessource() {
		this.inv = new Inventory();
		
		//on test l'instanciation
		assertEquals(this.inv.getRessource(Ressource.FOOD), 15);
		assertEquals(this.inv.getRessource(Ressource.WOOD), 0);
		assertEquals(this.inv.getRessource(Ressource.STONE), 0);
		assertEquals(this.inv.getRessource(Ressource.CLAY), 0);
		assertEquals(this.inv.getRessource(Ressource.GOLD), 0);
		assertEquals(this.inv.inventoryIA.getRessource(Ressource.FOOD), 15);
		assertEquals(this.inv.inventoryIA.getRessource(Ressource.WOOD), 0);
		assertEquals(this.inv.inventoryIA.getRessource(Ressource.STONE), 0);
		assertEquals(this.inv.inventoryIA.getRessource(Ressource.CLAY), 0);
		assertEquals(this.inv.inventoryIA.getRessource(Ressource.GOLD), 0);
		
		this.inv.addRessource(Ressource.WOOD, 7);
		this.inv.subRessource(Ressource.WOOD, 5);
		assertEquals(this.inv.getRessource(Ressource.WOOD),2);
		assertEquals(this.inv.getRessource(Ressource.WOOD),this.inv.inventoryIA.getRessource(Ressource.WOOD));
	}
	
	@Test
	public void testAbleToSubRessource() {
		this.inv = new Inventory();
		
		//on test l'instanciation
		assertEquals(this.inv.getRessource(Ressource.FOOD), 15);
		assertEquals(this.inv.getRessource(Ressource.WOOD), 0);
		assertEquals(this.inv.getRessource(Ressource.STONE), 0);
		assertEquals(this.inv.getRessource(Ressource.CLAY), 0);
		assertEquals(this.inv.getRessource(Ressource.GOLD), 0);
		assertEquals(this.inv.inventoryIA.getRessource(Ressource.FOOD), 15);
		assertEquals(this.inv.inventoryIA.getRessource(Ressource.WOOD), 0);
		assertEquals(this.inv.inventoryIA.getRessource(Ressource.STONE), 0);
		assertEquals(this.inv.inventoryIA.getRessource(Ressource.CLAY), 0);
		assertEquals(this.inv.inventoryIA.getRessource(Ressource.GOLD), 0);
		
		//test d'un cas qui marche
		this.inv.addRessource(Ressource.GOLD, 7);
		this.inv.inventoryIA.addRessource(Ressource.GOLD, 7);
		assertEquals(true,this.inv.ableToSubRessource(Ressource.GOLD, 6));
		assertEquals(true,this.inv.inventoryIA.ableToSubRessource(Ressource.GOLD, 6));
		
		//test de ne rien retirer
		assertEquals(true,this.inv.ableToSubRessource(Ressource.CLAY, 0));
		assertEquals(true,this.inv.inventoryIA.ableToSubRessource(Ressource.CLAY, 0));
		
		//test dans le cas de ressources insufissantes
		this.inv.addRessource(Ressource.STONE, 4);
		this.inv.inventoryIA.addRessource(Ressource.STONE, 4);
		assertEquals(false,this.inv.ableToSubRessource(Ressource.STONE, 12));
		assertEquals(false,this.inv.inventoryIA.ableToSubRessource(Ressource.STONE, 12));
	}
	
	@Test
	public void testAddCardCivilisation() {
		this.inv = new Inventory();
		CarteCivilisation card = new CarteCivilisation(0, 0, 0, Ressource.WOOD, 3);
		this.inv.addCardCivilisation(card);
		this.inv.inventoryIA.addCardCivilisation(card);
		assertEquals(this.inv.inventoryIA.getCardCivilisation()[0],card);
	}
	
	@Test
	public void testAvailableRessourceToFeed() {
		this.inv = new Inventory();
		this.inv.addRessource(Ressource.CLAY, 3);
		assertEquals(this.inv.availableResourceToFeed(), 3);
		assertEquals(this.inv.inventoryIA.availableResourceToFeed(), 3);
		this.inv.addRessource(Ressource.STONE, 3);
		assertEquals(this.inv.availableResourceToFeed(), 6);
		assertEquals(this.inv.inventoryIA.availableResourceToFeed(), 6);
	}

}
