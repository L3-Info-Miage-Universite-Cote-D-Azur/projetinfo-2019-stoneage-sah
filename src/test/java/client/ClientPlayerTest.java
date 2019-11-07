package Client;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import Game.Settings;

class ClientPlayerTest {

	@Test
	public void testChooseNumber () {
		for (int i = -100; i < 100; i++)
		{
			int ret = ClientPlayer.chooseNumber(i);
			if (i <= 0 || i > Settings.MAX_FIGURINE)
			{
				/* INTERDICTION */
				assertEquals(-1, ret);
			}
			else
			{
				/* OK */
				assertEquals(true, ret > 0 && ret <= Settings.MAX_FIGURINE);
			}
			
		}
	}

}
