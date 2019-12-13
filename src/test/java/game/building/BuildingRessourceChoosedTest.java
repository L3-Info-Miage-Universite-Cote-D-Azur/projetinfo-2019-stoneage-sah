package game.building;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import game.Ressource;
import inventory.Inventory;

public class BuildingRessourceChoosedTest 
{
	private BuildingRessourceChoosed brc;
	
	@Test
	public void testCheckNeededRessource ()
	{
		Inventory inv = new Inventory();
		this.brc = new BuildingRessourceChoosed();
		// TEST INITIALISATION
		assertEquals(false, this.brc.checkNeededRessource(inv.getCopyRessourcesLootable()));
		// AJOUTER 1 D'OR
		inv.addRessource(Ressource.GOLD, 1);
		assertEquals(true, this.brc.checkNeededRessource(inv.getCopyRessourcesLootable()));
	}
	
	@Test
	public void testCheckRessourceChoosed ()
	{
		// TROP DE RESSOURCE
		this.brc = new BuildingRessourceChoosed();
		Ressource[] ressources = new Ressource[] {Ressource.WOOD, Ressource.WOOD, Ressource.WOOD, Ressource.WOOD, Ressource.WOOD, Ressource.WOOD, Ressource.WOOD, Ressource.WOOD};
		int[] inv = new int[] {8, 0, 0, 0};
		assertEquals(false, this.brc.checkRessourceChoosed(ressources, inv));
		
		// JUSTE 1 RESSOURCE
		ressources = new Ressource[] {Ressource.WOOD};
		inv = new int[] {1, 0, 0, 0};
		assertEquals(true, this.brc.checkRessourceChoosed(ressources, inv));
		
		// LE JOUEUR N'A PAS LA RESSOURCE
		ressources = new Ressource[] {Ressource.WOOD};
		inv = new int[] {0, 0, 0, 0};
		assertEquals(false, this.brc.checkRessourceChoosed(ressources, inv));
		
		// TABLEAU VIDE
		ressources = new Ressource[] {};
		inv = new int[] {7, 7, 7, 7};
		assertEquals(false, this.brc.checkRessourceChoosed(ressources, inv));
		
		// TEST CORRECT
		ressources = new Ressource[] {Ressource.WOOD, Ressource.WOOD, Ressource.GOLD, Ressource.GOLD, Ressource.GOLD, Ressource.GOLD, Ressource.GOLD};
		inv = new int[] {2, 7, 7, 5};
		assertEquals(true, this.brc.checkRessourceChoosed(ressources, inv));
		
		// TEST AVEC UNE RESSOURCE INTERDITE
		ressources = new Ressource[] {Ressource.FOOD, Ressource.WOOD, Ressource.GOLD, Ressource.GOLD, Ressource.GOLD, Ressource.GOLD, Ressource.GOLD};
		inv = new int[] {1, 7, 7, 5};
		assertEquals(false, this.brc.checkRessourceChoosed(ressources, inv));
	}
	
	
}
