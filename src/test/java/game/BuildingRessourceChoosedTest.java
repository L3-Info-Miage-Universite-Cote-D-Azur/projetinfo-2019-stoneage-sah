package game;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class BuildingRessourceChoosedTest 
{
	private BuildingRessourceChoosed brc;
	
	@Test
	public void testCheckNeededRessource ()
	{
		this.brc = new BuildingRessourceChoosed();
		// TEST INITIALISATION
		assertEquals(false, this.brc.checkNeededRessource());
		// AJOUTER 1 DE BOIS
		this.brc.addRessource(Ressource.WOOD);
		assertEquals(true, this.brc.checkNeededRessource());
	}
}
