package player;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import inventory.Inventory;

public class PlayerStructTest {
	Inventory inventory = new Inventory();
	Player playertest = new Player("test", inventory.getInventoryIA());
	
	@Test
	public void testIncreaseMaxFigurine() {
		for(int i=0;i<5;i++) {
			assertEquals(true, playertest.increaseMaxFigurine());
		}
		assertEquals(false, playertest.increaseMaxFigurine());
	}
	
	@Test 
	public void testPlaceFigurine() {
		//Le joueur a 10 figurines.
		for(int i=0;i<5;i++) {
			playertest.increaseMaxFigurine();
		}
		
		for(int i= 1; i<11; i++) {
			assertEquals(10, playertest.getCurrentFigurine());
			playertest.placeFigurine(i);
			assertEquals(true, playertest.getCurrentFigurine() == 10-i);
			playertest.recoveryFigurine(i);
			assertEquals(true, playertest.getCurrentFigurine() == 10);
		}
	}
	
	@Test 
	public void testRecoveryFigurine() {
		//Le joueur a 10 figurines.
		for(int i=0;i<5;i++) {
			playertest.increaseMaxFigurine();
		}
		
		for(int i= 1; i<11; i++) {
			assertEquals(10, playertest.getCurrentFigurine());
			playertest.placeFigurine(i);
			assertEquals(true, playertest.getCurrentFigurine() == 10-i);
			playertest.recoveryFigurine(i);
			assertEquals(true, playertest.getCurrentFigurine() == 10);
		}
	}
}
