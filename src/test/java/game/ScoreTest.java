package game;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import inventory.Inventory;
import player.Player;

public class ScoreTest {

	Inventory inventory = new Inventory();
	Player pTest = new Player("test", inventory.getInventoryIA());
	Score score = new Score();

	@Test
	public void testCalculScore() {
		// TEST DES RESSOURCES
		inventory.addRessource(Ressource.WOOD,10);
		
		score.calculScore(pTest, inventory);
		assertEquals(10, pTest.getScore());

		inventory.addRessource(Ressource.CLAY,10);
		inventory.addRessource(Ressource.GOLD,10);
		inventory.addRessource(Ressource.FIELD, 5);
		pTest.addScore(-10);//On remet a 0;
		
		score.calculScore(pTest, inventory);
		assertEquals(30, pTest.getScore());
		
		//TEST DE CARTE CIVILISATION JAUNE
		pTest.addScore(-30);//On remet a 0;
		//Test des paysans.
		inventory.addCardCivilisation(new CarteCivilisation(0, 8, 2, Ressource.WOOD, 3));
		score.calculScore(pTest, inventory);
		assertEquals(40, pTest.getScore());
		
		pTest.addScore(-40);//On remet a 0;
		
		//test des fabricant d'outils
		inventory.getTools().incrementTool();
		inventory.getTools().incrementTool();
		inventory.getTools().incrementTool();
		inventory.addCardCivilisation(new CarteCivilisation(0, 9, 2, null, 0));
		
		score.calculScore(pTest, inventory);
		assertEquals(46, pTest.getScore());
		pTest.addScore(-46);//On remet a 0;
		
		//test chamane
		inventory.addCardCivilisation(new CarteCivilisation(0, 11, 1, null, 0));
		score.calculScore(pTest, inventory);
		assertEquals(51, pTest.getScore());
		pTest.addScore(-51);//On remet a 0;
		
		//test constructeur de maisons.
		for(int i =0; i < 7; i++) {
			inventory.incrementBuilding();
		}
		inventory.addCardCivilisation(new CarteCivilisation(0, 10, 3, null, 0));
		score.calculScore(pTest, inventory);
		assertEquals(72, pTest.getScore());
		pTest.addScore(-72);//On remet a 0;
		
		
		//TEST DE CARTE CIVILISATION VERTE
		//Avec 8 cartes differentes mais pas plus.
		for(int i = 0; i<8; i++) {
			inventory.addCardCivilisation(new CarteCivilisation(0, i, 0, null, 0));
		}
		score.calculScore(pTest, inventory);
		assertEquals(136, pTest.getScore());
		pTest.addScore(-136);//On remet a 0;
		
		//Avec 8 cartes differentes puis 3 cartes differentes.
		for(int i = 0; i<3; i++) {
			inventory.addCardCivilisation(new CarteCivilisation(0, i, 0, null, 0));
		}
		score.calculScore(pTest, inventory);
		assertEquals(145, pTest.getScore());
		pTest.addScore(-145);//On remet a 0;
	}
	
	public void testClassement() {
		Inventory inv = new Inventory();
		Player player1 = new Player("test1", inv.getInventoryIA());
		Player player2 = new Player("test2", inv.getInventoryIA());
		Player player3 = new Player("test3", inv.getInventoryIA());
		Player player4 = new Player("test4", inv.getInventoryIA());
		
		Player[] tabPlayers = new Player[] {player1,player2};
		player1.addScore(50);
		player2.addScore(20);
		//test a deux joueurs
		assertEquals(0, score.classement(tabPlayers));
		
		tabPlayers = new Player[] {player1,player2,player3};
		player3.addScore(100);
		//test a 3 joueurs
		assertEquals(2, score.classement(tabPlayers));
		
		tabPlayers = new Player[] {player1,player2,player3,player4};
		player3.addScore(150);
		//test a 4 joueurs
		assertEquals(3, score.classement(tabPlayers));
		
		//Test avec d'autre score
		player2.addScore(200);
		assertEquals(1, score.classement(tabPlayers));
		player1.addScore(200);
		assertEquals(0, score.classement(tabPlayers));
		player3.addScore(200);
		assertEquals(2, score.classement(tabPlayers));
		player4.addScore(200);
		assertEquals(3, score.classement(tabPlayers));
	}
}
