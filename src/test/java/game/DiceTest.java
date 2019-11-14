package game;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class DiceTest {

	@Test
	//* Test pour verifier si le lancers de de sont bien entre 1 et 6 dans tout le tableau, et si le tableau est de la bonne longueur.
	public void testRollDice() {
		int number=6;
		int[] tab = Dice.rollDice(number);
		for (int i=0;i<number;i++) {
			assertEquals(true, tab[i] <= 6 && tab[i] >=1);
		}
		assertEquals(true,tab.length==number);
	}

}
