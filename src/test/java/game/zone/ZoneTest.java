package game.zone;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import player.Player;
import game.Dice;
import game.Ressource;

public class ZoneTest {
	Zone zonetest;
	
	@Test
	public void testAbleToChooseZone() {
		zonetest = new ZoneRessource("test", Ressource.WOOD, 7, new Dice(),4);
		Player playertest = new Player("testplayer",null);
		
		//Cas ou le joueur n'as jamais rien placer.
		assertEquals(true, zonetest.ableToChooseZone(playertest));
		
		//Cas ou le joueur a deja placer. 
		zonetest.placeFigurine(5, playertest);
		assertEquals(false, zonetest.ableToChooseZone(playertest));
		
		//Cas ou un second joueur place et qu'il reste de la place
		Player playertest2 = new Player("testplayer2",null);
		assertEquals(true, zonetest.ableToChooseZone(playertest2));
		zonetest.placeFigurine(2,playertest2);
		
		//Cas ou le second joueur a deja placer.
		assertEquals(false, zonetest.ableToChooseZone(playertest2));
		
		//cas ou un troisieme joueur veut placer mais la zone est pleine
		Player playertest3 = new Player("testplayer3",null);
		assertEquals(false, zonetest.ableToChooseZone(playertest3));
	}
}
