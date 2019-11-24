package game.zone;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import game.Inventory;
import game.Player;
import game.Ressource;

public class ZoneRessourceTest {
	Inventory inventoryTest;
	Inventory inventoryTest2;
	Zone zonetest;
	Player playertest;
	Player playertest2;
	
	@Test
	public void testPlayerRecoveryFigurine() {
		zonetest = new ZoneRessource("test", Ressource.WOOD, 7);
		inventoryTest = new Inventory();
		inventoryTest2 = new Inventory();
		
		playertest = new Player("test", null);
		playertest2 = new Player("test2", null);
		
		assertEquals(true, inventoryTest.getRessource(Ressource.WOOD) == 0 );
		
		//Cas ou le joueur recupere se figurines et ses ressources.
		for(int i = 0; i < 100; i++) {
			//On reinitialise.
			inventoryTest.addRessource(Ressource.WOOD, -1*inventoryTest.getRessource(Ressource.WOOD));
			inventoryTest2.addRessource(Ressource.WOOD, -1*inventoryTest2.getRessource(Ressource.WOOD));
			assertEquals(true, inventoryTest.getRessource(Ressource.WOOD) == 0);
			assertEquals(true, inventoryTest2.getRessource(Ressource.WOOD) == 0);
			
			//On place puis recupere.
			zonetest.placeFigurine(3, playertest);//3 figurines pour que le joueur recupere au minimum 1 de bois.
			zonetest.placeFigurine(3, playertest2);
			zonetest.playerRecoveryFigurine(playertest, inventoryTest);
			zonetest.playerRecoveryFigurine(playertest2, inventoryTest2);
			
			//On test qu'il a bien gagner au moins une ressource et moins que le maximum. 
			assertEquals(true, inventoryTest.getRessource(Ressource.WOOD) > 0 );
			assertEquals(true, inventoryTest.getRessource(Ressource.WOOD) <= 18/3 );
			//On test qu'il n'y a plus de figurines du joueur.
			assertEquals(true, zonetest.howManyPlayerFigurine(playertest) == 0);
			
			assertEquals(true, inventoryTest2.getRessource(Ressource.WOOD) > 0 );
			assertEquals(true, inventoryTest2.getRessource(Ressource.WOOD) <= 18/3 );
			assertEquals(true, zonetest.howManyPlayerFigurine(playertest2) == 0);
		}
	}
}
