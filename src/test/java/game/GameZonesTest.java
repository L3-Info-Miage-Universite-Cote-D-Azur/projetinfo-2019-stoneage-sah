package game;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import inventory.Inventory;
import player.Player;
import game.GamePlayers;

class GameZonesTest{
	Dice d;
	GameZones g = new GameZones(2, d);
	GamePlayers gh = new GamePlayers(4);
	Inventory i = new Inventory();
	Player p = new Player("sardoche", i.getInventoryIA());
	
	@Test
	void testPlayerHarvest() {
		g.playerHarvest(p, i, gh);
		assertEquals(p.getCurrentFigurine(),p.getMaxFigurine());
	}

}
