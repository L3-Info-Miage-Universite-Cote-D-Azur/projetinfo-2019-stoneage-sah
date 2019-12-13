package statistics;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import game.Ressource;
import inventory.Inventory;
import player.Player;

public class StatisticsTest 
{
	@Test
	public void testEndGame () throws FileNotFoundException
	{
		Statistics stats = new Statistics(1);
		Inventory[] inv = new Inventory[1];
		Player[] p = new Player[1];
		int nbRounds = 1;
		
		inv[0] = new Inventory();
		inv[0].addRessource(Ressource.WOOD, 2);
		// ajoute 2 de bois au tour 1
		stats.updateTheStat(stats.getDatas()[1], 0, 1, 2, 1);
		// ajoute 4 de bois en plus pour le tour 2
		inv[0].addRessource(Ressource.WOOD, 4);
		stats.updateTheStat(stats.getDatas()[1], 0, 1, 6, 2);
		// Ajoute 0 de bois pour le tour 3
		stats.updateTheStat(stats.getDatas()[1], 0, 1, 0, 3);
		p[0] = new Player(null, inv[0].getInventoryIA());
		nbRounds++;
		// On espere 0: test d'initialisation
		assertEquals(stats.getDatas()[1].get(0)[1].get(2), 0);
		stats.endGame(inv, p, nbRounds);
		System.out.println(Arrays.toString(stats.getDatas()[1].get(0)));
		// On espere 6: derniere donnee  de l'inventaire
		assertEquals(stats.getDatas()[1].get(0)[1].get(2), 6);
	}
	
	@Test
	public void testUpdateTheStat () throws FileNotFoundException
	{
		Statistics stats = new Statistics(1);
		// AJOUTER 5 DE SCORE
		int currPlayer = 0;
		int currData = 0;
		int value = 5;
		int rounds = 1;
		stats.updateTheStat(stats.getDatas()[0], currPlayer, currData, value, rounds);
		assertEquals(stats.getDatas()[0].get(currPlayer)[currData].get(rounds - 1), value);
	}
	
	@Test
	public void testUpdateStats () throws FileNotFoundException
	{
		Statistics stats = new Statistics(1);
		Inventory[] inv = new Inventory[1];
		Player[] p = new Player[1];
		int rounds = 1;
		inv[0] = new Inventory();
		p[0] = new Player(null, inv[0].getInventoryIA());
		inv[0].addRessource(Ressource.WOOD, 2);
		// AJOUTER 5 DE SCORE
		stats.updateStats(inv, p, rounds);
		assertEquals(stats.getDatas()[0].get(0)[1].get(rounds - 1), 2);
	}
	
	@Test
	public void testUpdateVictoryRate () throws FileNotFoundException
	{
		Statistics stats = new Statistics(1);
		assertEquals(stats.getVictoryRate()[0], 0.0);
		stats.updateVictoryRate(0);
		assertEquals(stats.getVictoryRate()[0], 1.0);
	}
}
