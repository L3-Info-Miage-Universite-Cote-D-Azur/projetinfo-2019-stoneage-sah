package game;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import game.Dice;

public class DiceTest {

	@Test
	public void testRoll() {
		for (int i =0; i<100; i++) {
			int dice = Dice.roll();
			assertEquals(true, dice <= 6 && dice >=1);
		}
	}

}
