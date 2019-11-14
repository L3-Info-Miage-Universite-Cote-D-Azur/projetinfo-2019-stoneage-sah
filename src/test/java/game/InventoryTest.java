package game;
import game.Ressource;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class InventoryTest
{
	private Inventory inv;

	@Test
	public void testAddRessource ()
	{
		this.inv = new Inventory();
		// Test de l'instanciation
		assertEquals(this.inv.getRessource(Ressource.FOOD), 15);
		assertEquals(this.inv.getRessource(Ressource.WOOD), 0);
		assertEquals(this.inv.getRessource(Ressource.STONE), 0);
		assertEquals(this.inv.getRessource(Ressource.CLAY), 0);
		assertEquals(this.inv.getRessource(Ressource.GOLD), 0);

		// Test ajout de 100 bois un par un
		for (int i = 1; i <= 100; i++)
		{
			this.inv.addRessource(Ressource.WOOD, 1);
			assertEquals(this.inv.getRessource(Ressource.WOOD), i);
		}
	}

	@Test
	public void testSubRessource ()
	{
		this.inv = new Inventory();
		// Test de l'instanciation
		assertEquals(this.inv.getRessource(Ressource.FOOD), 15);
		assertEquals(this.inv.getRessource(Ressource.WOOD), 0);
		assertEquals(this.inv.getRessource(Ressource.STONE), 0);
		assertEquals(this.inv.getRessource(Ressource.CLAY), 0);
		assertEquals(this.inv.getRessource(Ressource.GOLD), 0);

		// Test soustraction de 100 bois un par un
		this.inv.addRessource(Ressource.WOOD, 100);
		for (int i = 99; i >= 0; i--)
		{
			this.inv.subRessource(Ressource.WOOD, 1);
			assertEquals(this.inv.getRessource(Ressource.WOOD), i);
		}
	}

	@Test
	public void testAbleToSubRessource ()
	{
		this.inv = new Inventory();
		// Test de l'instanciation
		assertEquals(this.inv.getRessource(Ressource.FOOD), 15);
		assertEquals(this.inv.getRessource(Ressource.WOOD), 0);
		assertEquals(this.inv.getRessource(Ressource.STONE), 0);
		assertEquals(this.inv.getRessource(Ressource.CLAY), 0);
		assertEquals(this.inv.getRessource(Ressource.GOLD), 0);

		// Test nombre trop grand
		this.inv.addRessource(Ressource.WOOD, 10);
		assertEquals(this.inv.ableToSubRessource(Ressource.WOOD, 20), false);

		// Test 0
		assertEquals(this.inv.ableToSubRessource(Ressource.CLAY, 0), true);

		// Test positif
		for (int i = 0; i <= 10; i++)
			assertEquals(this.inv.ableToSubRessource(Ressource.WOOD, i), true);

	}
}
