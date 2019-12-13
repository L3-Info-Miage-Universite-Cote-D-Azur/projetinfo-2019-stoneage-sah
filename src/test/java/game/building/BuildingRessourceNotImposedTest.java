package game.building;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import game.Ressource;
import inventory.Inventory;

public class BuildingRessourceNotImposedTest 
{
	private BuildingRessourceNotImposed brni;
	
	@Test
	public void testCheckNeededRessource ()
	{
		Inventory inv = new Inventory();
		// 2 differents, 5 ressources
		this.brni = new BuildingRessourceNotImposed(2, 5);
		inv.addRessource(Ressource.WOOD, 1);
		inv.addRessource(Ressource.WOOD, 1);
		inv.addRessource(Ressource.WOOD, 1);
		inv.addRessource(Ressource.WOOD, 1);
		inv.addRessource(Ressource.CLAY, 1);
		// Test positif
		assertEquals(true, this.brni.checkNeededRessource(inv.getCopyRessourcesLootable()));
		

		inv = new Inventory();
		this.brni = new BuildingRessourceNotImposed(2, 5);
		inv.addRessource(Ressource.WOOD, 1);
		inv.addRessource(Ressource.WOOD, 1);
		inv.addRessource(Ressource.WOOD, 1);
		inv.addRessource(Ressource.WOOD, 1);
		inv.addRessource(Ressource.WOOD, 1);
		// Test avec 5 de bois, donc 1 seule ressource
		assertEquals(false, this.brni.checkNeededRessource(inv.getCopyRessourcesLootable()));
		

		inv = new Inventory();
		this.brni = new BuildingRessourceNotImposed(2, 5);
		inv.addRessource(Ressource.CLAY, 1);
		inv.addRessource(Ressource.STONE, 1);
		inv.addRessource(Ressource.WOOD, 1);
		inv.addRessource(Ressource.WOOD, 1);
		inv.addRessource(Ressource.WOOD, 1);
		// Test avec 3 ressources differentes
		assertEquals(false, this.brni.checkNeededRessource(inv.getCopyRessourcesLootable()));
	}
	
	@Test
	public void testCheckRessourceNotImposed ()
	{
		// TROP DE RESSOURCE
		this.brni = new BuildingRessourceNotImposed(3, 4);
		Ressource[] ressources = new Ressource[] {Ressource.WOOD, Ressource.CLAY, Ressource.GOLD, Ressource.WOOD, Ressource.WOOD, Ressource.WOOD, Ressource.WOOD, Ressource.WOOD};
		int[] inv = new int[] {8, 1, 0, 1};
		assertEquals(false, this.brni.checkRessourceNotImposed(ressources, inv));
		
		// JUSTE 3 RESSOURCES SUR LES 4
		ressources = new Ressource[] {Ressource.WOOD, Ressource.CLAY, Ressource.GOLD};
		inv = new int[] {1, 1, 0, 1};
		assertEquals(false, this.brni.checkRessourceNotImposed(ressources, inv));
		
		// LE JOUEUR N'A PAS LA RESSOURCE
		ressources = new Ressource[] {Ressource.WOOD, Ressource.CLAY, Ressource.GOLD, Ressource.WOOD};
		inv = new int[] {0, 0, 0, 0};
		assertEquals(false, this.brni.checkRessourceNotImposed(ressources, inv));
		
		// TABLEAU VIDE
		ressources = new Ressource[] {};
		inv = new int[] {7, 7, 7, 7};
		assertEquals(false, this.brni.checkRessourceNotImposed(ressources, inv));
		
		// TEST CORRECT
		ressources = new Ressource[] {Ressource.WOOD, Ressource.CLAY, Ressource.GOLD, Ressource.WOOD};
		inv = new int[] {2, 1, 0, 1};
		assertEquals(true, this.brni.checkRessourceNotImposed(ressources, inv));
		
		// TEST AVEC UNE RESSOURCE INTERDITE
		ressources = new Ressource[] {Ressource.FOOD, Ressource.CLAY, Ressource.GOLD, Ressource.WOOD};
		inv = new int[] {1, 7, 7, 5};
		assertEquals(false, this.brni.checkRessourceNotImposed(ressources, inv));
	}
}
