package game.zone;


import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import game.Player;

public class ZoneOnePlayerTest {
	
	private ZoneOnePlayer zoneOnePlayer;
	
	@Test
	public void howManyPlayerFigurineTest() {
		Player player1 = new Player(null, null);
		Player player2 = new Player(null, null);
		int numberFigurine = player1.getCurrentFigurine();
		zoneOnePlayer = new ZoneHut(null);
		int minimalFigurine=zoneOnePlayer.getMinimalFigurineRequierement();
		
		//intialisation la zone est vide
		assertEquals(true, zoneOnePlayer.howManyPlayerFigurine(player1) == 0);
		assertEquals(true, zoneOnePlayer.howManyPlayerFigurine(player2) == 0);
		
		//on place les figurine du joueur 1:
		zoneOnePlayer.placeFigurine(minimalFigurine, player1);
		assertEquals(true, zoneOnePlayer.howManyPlayerFigurine(player1) == minimalFigurine);
		assertEquals(true, zoneOnePlayer.howManyPlayerFigurine(player2) == 0);
		//le joueur a bien les figurine qui sont enlever
		assertEquals(true,player1.getCurrentFigurine()==numberFigurine-minimalFigurine);
		
	}
	
	@Test
	public void placeFigurineTest ()
	{

		Player player = new Player(null, null);
		int numberFigurine = player.getCurrentFigurine();
		zoneOnePlayer = new ZoneHut(null);
		// Test d'initialisation
		
		assertEquals(true, zoneOnePlayer.howManyPlayerFigurine(player) == 0);
		
		// Test si le joueur a placer le nombre de figurine requie
		//particularité ces zone le maximum de figurine a placer est aussi le minimum
		//cette fonction necesite un près traitement qui fait le verification(aucune verification a l'interrieur
		zoneOnePlayer.placeFigurine(zoneOnePlayer.getMinimalFigurineRequierement(), player);
		assertEquals(true, zoneOnePlayer.howManyPlayerFigurine(player) == zoneOnePlayer.getMinimalFigurineRequierement());
		assertEquals(true, player.getCurrentFigurine()==numberFigurine-zoneOnePlayer.getMinimalFigurineRequierement());
	}
	
	@Test
	public void removeFigurineTest() {
		Player player1 = new Player(null, null);
		Player player2 = new Player(null, null);
		int numberFigurine = player1.getCurrentFigurine();
		zoneOnePlayer = new ZoneHut(null);
		int minimalFigurine=zoneOnePlayer.getMinimalFigurineRequierement();
		
		//intialisation la zone est vide
		assertEquals(true, zoneOnePlayer.howManyPlayerFigurine(player1) == 0);
		assertEquals(true, zoneOnePlayer.howManyPlayerFigurine(player2) == 0);
		
		//on retire les figurine alors qu'il y en a pas (rien ne change)
		zoneOnePlayer.removeFigurine(player1);
		assertEquals(true, zoneOnePlayer.howManyPlayerFigurine(player1) == 0);
		assertEquals(true, zoneOnePlayer.howManyPlayerFigurine(player2) == 0);
		//le joueur n'a pas gagner/perdue de figurine
		assertEquals(true,player1.getCurrentFigurine()==numberFigurine);
		
		//on place les figurine du joueur 1:
		zoneOnePlayer.placeFigurine(minimalFigurine, player1);
		assertEquals(true, zoneOnePlayer.howManyPlayerFigurine(player1) == minimalFigurine);
		assertEquals(true, zoneOnePlayer.howManyPlayerFigurine(player2) == 0);
		//le joueur a bien les figurine qui sont enlever
		assertEquals(true,player1.getCurrentFigurine()==numberFigurine-minimalFigurine);
		
		//on retire les figurine du joueur 2(qui en a pas mis)
		zoneOnePlayer.removeFigurine(player2);
		assertEquals(true, zoneOnePlayer.howManyPlayerFigurine(player1) == minimalFigurine);
		assertEquals(true, zoneOnePlayer.howManyPlayerFigurine(player2) == 0);
		
		//on retire les figurine du joueur 1;
		zoneOnePlayer.removeFigurine(player1);
		assertEquals(true, zoneOnePlayer.howManyPlayerFigurine(player1) == 0);
		assertEquals(true, zoneOnePlayer.howManyPlayerFigurine(player2) == 0);
		//le joueur a bien recuperer ce figurine
		assertEquals(true,player1.getCurrentFigurine()==numberFigurine);
	}
	
}
