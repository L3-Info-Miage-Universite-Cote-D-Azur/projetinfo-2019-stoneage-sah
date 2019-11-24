package game.zone;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import game.Player;
import game.Ressource;

public class ZoneManyPlayerTest {
	Zone zonetest;
	Player playertest;
	Player playertest2;
	
	@Test
	public void testPlaceFigurine() {
		zonetest = new ZoneRessource("test", Ressource.WOOD, 7);
		playertest = new Player("testplayer",null);
		
		//Cas ou le joueur n'a pas place
		assertEquals(true, zonetest.howManyPlayerFigurine(playertest) == 0);
		assertEquals(true, zonetest.getAvailableSpace() == 7);
		
		//Un joueur a placer.
		zonetest.placeFigurine(3, playertest);
		assertEquals(true, zonetest.howManyPlayerFigurine(playertest) == 3);
		assertEquals(true, zonetest.getAvailableSpace() == 4);
		
		//Un autre joueur place.
		playertest2 = new Player("testplayer2", null);
		zonetest.placeFigurine(4, playertest2);
		assertEquals(true, zonetest.howManyPlayerFigurine(playertest) == 3);
		assertEquals(true, zonetest.howManyPlayerFigurine(playertest2) == 4);
		assertEquals(true, zonetest.getAvailableSpace() == 0);
		
		
		//Cas ou un joueur peut placer plusieur fois.
		zonetest = new ZoneHunt("testhunt", Ressource.FOOD, 40);
		//Normalement la place correspond a : NBPLAYER * NBMAXFIGURES
		playertest = new Player("testplayer",null);
		
		zonetest.placeFigurine(3, playertest);
		assertEquals(true, zonetest.howManyPlayerFigurine(playertest) == 3);
		assertEquals(true, zonetest.getAvailableSpace() > 0);
		zonetest.placeFigurine(2, playertest);
		assertEquals(true, zonetest.howManyPlayerFigurine(playertest) == 5);
		assertEquals(true, zonetest.getAvailableSpace() > 0);
	}
	
	@Test
	public void testHowManyPlayerFigurine() {
		zonetest = new ZoneRessource("test", Ressource.WOOD, 7);
		playertest = new Player("testplayer",null);
		
		//Cas ou le joueur n'a pas place
		assertEquals(true, zonetest.howManyPlayerFigurine(playertest) == 0);
		
		//Un joueur a placer.
		zonetest.placeFigurine(3, playertest);
		assertEquals(true, zonetest.howManyPlayerFigurine(playertest) == 3);
		
		//Un autre joueur place.
		playertest2 = new Player("testplayer2", null);
		zonetest.placeFigurine(4, playertest2);
		assertEquals(true, zonetest.howManyPlayerFigurine(playertest) == 3);
		assertEquals(true, zonetest.howManyPlayerFigurine(playertest2) == 4);
		
		//Cas ou un joueur retire ses figurines
		zonetest.removeFigurine(playertest);
		zonetest.removeFigurine(playertest2);
		assertEquals(true, zonetest.howManyPlayerFigurine(playertest) == 0);
		assertEquals(true, zonetest.howManyPlayerFigurine(playertest2) == 0);
		
		
		//Cas ou un joueur peut placer plusieur fois.
		zonetest = new ZoneHunt("testhunt", Ressource.FOOD, 40);
		//Normalement la place correspond a : NBPLAYER * NBMAXFIGURES
		playertest = new Player("testplayer",null);
		
		zonetest.placeFigurine(3, playertest);
		assertEquals(true, zonetest.howManyPlayerFigurine(playertest) == 3);
		zonetest.placeFigurine(2, playertest);
		assertEquals(true, zonetest.howManyPlayerFigurine(playertest) == 5);
		
		//Cas ou un joueur retire ses figurines
		zonetest.removeFigurine(playertest);
		assertEquals(true, zonetest.howManyPlayerFigurine(playertest) == 0);
	}
	
	@Test
	public void testRemoveFigurine() {
		zonetest = new ZoneRessource("test", Ressource.WOOD, 7);
		playertest = new Player("testplayer",null);
		
		//Cas ou un joueur place puis retire.
		zonetest.placeFigurine(5, playertest);
		zonetest.removeFigurine(playertest);
		assertEquals(true, zonetest.howManyPlayerFigurine(playertest) == 0);
		assertEquals(true, zonetest.getAvailableSpace() == 7);
		
		//Cas ou deux joueur place et retire.
		playertest2 = new Player("testplayer2", null);
		zonetest.placeFigurine(5, playertest);
		zonetest.placeFigurine(2, playertest2);
		
		zonetest.removeFigurine(playertest);
		assertEquals(true, zonetest.howManyPlayerFigurine(playertest) == 0);
		assertEquals(true, zonetest.getAvailableSpace() == 5);
		zonetest.removeFigurine(playertest2);
		assertEquals(true, zonetest.howManyPlayerFigurine(playertest) == 0);
		assertEquals(true, zonetest.getAvailableSpace() == 7);
	}
}
