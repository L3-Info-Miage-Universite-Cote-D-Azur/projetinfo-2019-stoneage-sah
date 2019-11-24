package game.building;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import game.Ressource;

public class BuildingRessourceImposedTest 
{
	private BuildingRessourceImposed bri;
	
	@Test
	public void testCheckNeededRessource ()
	{
		/* 0:bois ; 1:clay ; 2:stone ; 3:gold ; 4:food ; 5:field */
		
		int[] inv = new int[] {5, 5, 5, 5, 0, 0};
		this.bri = new BuildingRessourceImposed(10, Ressource.WOOD, Ressource.CLAY, Ressource.GOLD);
		// TEST INITIALISATION
		assertEquals(true, this.bri.checkNeededRessource(inv));
		
		// TEST SANS LE BOIS
		inv = new int[] {0, 5, 5, 5, 0, 0};
		assertEquals(false, this.bri.checkNeededRessource(inv));
	}
}
