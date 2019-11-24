package game.building;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import game.Ressource;

public class BuildingRessourceNotImposedTest 
{
	private BuildingRessourceNotImposed brni;
	
	@Test
	public void testCheckNeededRessource ()
	{
		// 2 differents, 5 ressources
		this.brni = new BuildingRessourceNotImposed(2, 5);
		assertEquals(true, this.brni.addRessource(Ressource.WOOD));
		assertEquals(true, this.brni.addRessource(Ressource.WOOD));
		assertEquals(true, this.brni.addRessource(Ressource.WOOD));
		assertEquals(true, this.brni.addRessource(Ressource.WOOD));
		assertEquals(true, this.brni.addRessource(Ressource.CLAY));
		// Test positif
		assertEquals(true, this.brni.checkNeededRessource());
		
		this.brni = new BuildingRessourceNotImposed(2, 5);
		assertEquals(true, this.brni.addRessource(Ressource.WOOD));
		assertEquals(true, this.brni.addRessource(Ressource.WOOD));
		assertEquals(true, this.brni.addRessource(Ressource.WOOD));
		assertEquals(true, this.brni.addRessource(Ressource.WOOD));
		assertEquals(true, this.brni.addRessource(Ressource.WOOD));
		// Test avec 5 de bois, donc 1 seule ressource
		assertEquals(false, this.brni.checkNeededRessource());
		
		this.brni = new BuildingRessourceNotImposed(2, 5);
		assertEquals(true, this.brni.addRessource(Ressource.CLAY));
		assertEquals(true, this.brni.addRessource(Ressource.STONE));
		assertEquals(true, this.brni.addRessource(Ressource.WOOD));
		assertEquals(true, this.brni.addRessource(Ressource.WOOD));
		assertEquals(true, this.brni.addRessource(Ressource.WOOD));
		// Test avec 3 ressources differentes
		assertEquals(false, this.brni.checkNeededRessource());
	}
}
