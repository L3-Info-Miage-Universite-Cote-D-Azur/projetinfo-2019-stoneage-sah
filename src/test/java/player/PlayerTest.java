package player;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;


public class PlayerTest {
	Player playertest = new Player(null, null);
	
	@Test
	public void testIncreaseMaxFigurine() {
		for(int i=0;i<5;i++) {
			assertEquals(true, playertest.increaseMaxFigurine());
			//le nb de figurine de l'ia reste le meme
			assertEquals(true, playertest.getPlayerIA().getCurrentFigurine()==playertest.getCurrentFigurine());
		}
		assertEquals(false, playertest.increaseMaxFigurine());
		//le nb de figurine de l'ia reste le meme
		assertEquals(true, playertest.getPlayerIA().getCurrentFigurine()==playertest.getCurrentFigurine());
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
			//le nb de figurine de l'ia reste le meme
			assertEquals(true, playertest.getPlayerIA().getCurrentFigurine()==playertest.getCurrentFigurine());
			playertest.recoveryFigurine(i);
			assertEquals(true, playertest.getCurrentFigurine() == 10);
			//le nb de figurine de l'ia reste le meme
			assertEquals(true, playertest.getPlayerIA().getCurrentFigurine()==playertest.getCurrentFigurine());
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
			//le nb de figurine de l'ia reste le meme
			assertEquals(true, playertest.getPlayerIA().getCurrentFigurine()==playertest.getCurrentFigurine());
			playertest.recoveryFigurine(i);
			assertEquals(true, playertest.getCurrentFigurine() == 10);
			//le nb de figurine de l'ia reste le meme
			assertEquals(true, playertest.getPlayerIA().getCurrentFigurine()==playertest.getCurrentFigurine());
		}
	}
}
