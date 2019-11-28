package game;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import inventory.Inventory;
import player.Player;

class GameZonesTest{
	Dice d;
	GameZones g = new GameZones(2, d);
	Inventory i = new Inventory();
	Player p = new Player("sardoche", i.getInventoryIA());
	
	@Test
	void testPlayerHarvest() {
		g.playerHarvest(p, i);
		assertEquals(p.getCurrentFigurine(),p.getMaxFigurine());
	}
	
	@Test
	void testPlaceFigurine() {
		g.playerPlaceFigurine(p);
		assertEquals(true, p.getCurrentFigurine()>=0);
	}

}
