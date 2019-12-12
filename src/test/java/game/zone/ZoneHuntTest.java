package game.zone;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import game.Ressource;
import game.Settings;
import player.Player;

public class ZoneHuntTest {

	private Zone zonehunt;
	@Test
	public void testAbleToChooseZone() {
		zonehunt = new ZoneHunt("Chasse", Ressource.FOOD,4 * Settings.MAX_FIGURINE, null);
		Player player = new Player("test", null);
		for(int i = 0; i < 100 ; i++) {
			assertEquals(true, zonehunt.ableToChooseZone(player));
		}
	}
}
