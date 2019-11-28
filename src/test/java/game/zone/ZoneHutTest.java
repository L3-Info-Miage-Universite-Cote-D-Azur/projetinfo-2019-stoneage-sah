package game.zone;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import player.Player;

public class ZoneHutTest {
	
	private ZoneHut zoneHut;
	
	@Test
	public void playerRecoveryFigurineTest() {
		Player player = new Player(null,null);
		Player player2 = new Player(null,null);
		int initFigurine = player.getCurrentFigurine();
		zoneHut = new ZoneHut(null);
		zoneHut.placeFigurine(zoneHut.getMinimalFigurineRequierement(), player);
		
		//le joueur 2 qui n'a pas de figurine dans la zone on verifie qu'il n'a pas gagner de figurine
		zoneHut.playerRecoveryFigurine(player2, null);
		assertEquals(true,player2.getCurrentFigurine()==initFigurine);
		
		//le joueur qui a placer dans la zonne recupere ces figurine + 1 (de la hut)
		zoneHut.playerRecoveryFigurine(player, null);
		assertEquals(true,player.getCurrentFigurine()==initFigurine+1);
		
		
		//reinitialisation du joueur pour un test jusqu'au maximum
		player = new Player(null,null);
		for(int i=1;i<9;i++) {
			zoneHut.placeFigurine(zoneHut.getMinimalFigurineRequierement(), player);
			zoneHut.playerRecoveryFigurine(player,null);
			
			if(i<=5) {
				assertEquals(true,player.getCurrentFigurine()==initFigurine+i);
			}
			//les joueur a le nombre de figurine max
			else {
				assertEquals(true,player.getCurrentFigurine()==10);
			}
		}
		

		
	}
}
