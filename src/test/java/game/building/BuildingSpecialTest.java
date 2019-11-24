package game.building;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import game.Ressource;
import game.Settings;

public class BuildingSpecialTest 
{
	private BuildingSpecial bs;
	
	@Test
	public void testAddRessource ()
	{
		this.bs = (BuildingSpecial)new BuildingRessourceChoosed();
		
		// Ajout d'une ressource
		assertEquals(true, this.bs.addRessource(Ressource.WOOD));
		
		// Test positif, si le bois est bien en premiere position
		assertEquals(Ressource.WOOD, this.bs.getNeededRessource()[0]);
		
		this.bs = (BuildingSpecial)new BuildingRessourceChoosed();
		// Ajouter 1 de bois dans chaque case de neededRessource
		for (int i = 0; i < Settings.NB_MAX_RESSOURCE_BUILDING; i++)
		{
			assertEquals(true, this.bs.addRessource(Ressource.WOOD));
			assertEquals(Ressource.WOOD, this.bs.getNeededRessource()[i]);
		}
		
		// Ajouter 1 de bois en trop
		assertEquals(false, this.bs.addRessource(Ressource.WOOD));
		
	}
	
	@Test
	public void testComputeVP ()
	{
		this.bs = (BuildingSpecial)new BuildingRessourceChoosed();
		// Test d'initialisation
		assertEquals(0, this.bs.computeVP());
		for (int i = 0; i < Settings.NB_MAX_RESSOURCE_BUILDING; i++)
		{
			this.bs.addRessource(Ressource.GOLD);
		}
		// Test avec le maximum possible
		assertEquals(5 * Settings.NB_MAX_RESSOURCE_BUILDING, this.bs.computeVP());
	}
}
