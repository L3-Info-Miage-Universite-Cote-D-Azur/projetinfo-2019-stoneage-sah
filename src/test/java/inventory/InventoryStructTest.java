package inventory;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import game.CarteCivilisation;
import game.Ressource;

public class InventoryStructTest {
	
	InventoryStruct InventoryStructTest;

	@Test
	public void testAddRessource() {
		this.InventoryStructTest = new Inventory();
		
		//on test l'instanciation//
		
		assertEquals(this.InventoryStructTest.getRessource(Ressource.FOOD), 15);
		assertEquals(this.InventoryStructTest.getRessource(Ressource.WOOD), 0);
		assertEquals(this.InventoryStructTest.getRessource(Ressource.STONE), 0);
		assertEquals(this.InventoryStructTest.getRessource(Ressource.CLAY), 0);
		assertEquals(this.InventoryStructTest.getRessource(Ressource.GOLD), 0);

		//on test l'ajout de ressource//
		this.InventoryStructTest.addRessource(Ressource.WOOD, 5);
		assertEquals(this.InventoryStructTest.getRessource(Ressource.WOOD),5);
		
		
	}
	
	@Test
	public void testSubRessource() {
		this.InventoryStructTest = new Inventory();
		
		//on test l'instanciation//		
		assertEquals(this.InventoryStructTest.getRessource(Ressource.FOOD), 15);
		assertEquals(this.InventoryStructTest.getRessource(Ressource.WOOD), 0);
		assertEquals(this.InventoryStructTest.getRessource(Ressource.STONE), 0);
		assertEquals(this.InventoryStructTest.getRessource(Ressource.CLAY), 0);
		assertEquals(this.InventoryStructTest.getRessource(Ressource.GOLD), 0);
		
		this.InventoryStructTest.subRessource(Ressource.FOOD, 5);
		assertEquals(this.InventoryStructTest.getRessource(Ressource.FOOD),10);
		
	}
	
	@Test
	public void testAbleToSubRessource() {
		this.InventoryStructTest = new Inventory();
		
		//on test l'instanciation//		
		assertEquals(this.InventoryStructTest.getRessource(Ressource.FOOD), 15);
		assertEquals(this.InventoryStructTest.getRessource(Ressource.WOOD), 0);
		assertEquals(this.InventoryStructTest.getRessource(Ressource.STONE), 0);
		assertEquals(this.InventoryStructTest.getRessource(Ressource.CLAY), 0);
		assertEquals(this.InventoryStructTest.getRessource(Ressource.GOLD), 0);
		
		//test dans un cas qui marche
		this.InventoryStructTest.addRessource(Ressource.GOLD, 13);
		assertEquals(true,this.InventoryStructTest.ableToSubRessource(Ressource.GOLD, 7));
		
		//test de ne rien retirer
		assertEquals(true,this.InventoryStructTest.ableToSubRessource(Ressource.STONE, 0));
		
		//test dans le cas ou les ressource ne sont pas suffisantes
		this.InventoryStructTest.addRessource(Ressource.CLAY, 3);
		assertEquals(false,this.InventoryStructTest.ableToSubRessource(Ressource.CLAY, 4));
		
	}
	
	@Test
	public void testAddCardCivilisation() {
		this.InventoryStructTest = new Inventory();
		CarteCivilisation card = new CarteCivilisation(0, 0, 0, Ressource.WOOD, 3);
		this.InventoryStructTest.addCardCivilisation(card);
		// Test positif, on ajoute une carte
		assertEquals(this.InventoryStructTest.getCardCivilisation()[0], card);
	}
	
	@Test
	public void testAvailableResourceToFeed (){
		this.InventoryStructTest = new Inventory();
		this.InventoryStructTest.addRessource(Ressource.GOLD, 7);
		assertEquals(this.InventoryStructTest.availableResourceToFeed(), 7);
		this.InventoryStructTest.addRessource(Ressource.STONE, 4);
		assertEquals(this.InventoryStructTest.availableResourceToFeed(), 7 + 4);
	}

}
