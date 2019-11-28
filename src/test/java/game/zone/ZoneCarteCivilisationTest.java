package game.zone;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import game.CarteCivilisation;
import game.Dice;
import game.Ressource;
import inventory.Inventory;
import player.Player;

public class ZoneCarteCivilisationTest {

	ZoneCarteCivilisation zonetest;
	Player playertest;
	Inventory Inventorytest;
	CarteCivilisation cartetest;
	CarteCivilisation[] tabcarte;

	@Test
	public void testPlayerRecoveryFigurine() {
		zonetest = new ZoneCarteCivilisation("test", 4, new Dice());
		Inventorytest = new Inventory();
		playertest = new Player("test",Inventorytest.getInventoryIA());

		//La carte donne des ressources fixe.
		cartetest = new CarteCivilisation(0,0,0,Ressource.WOOD,4);

		zonetest.setCard(cartetest);
		zonetest.placeFigurine(1, playertest);
		zonetest.playerRecoveryFigurine(playertest, Inventorytest);

		//Cas ou le joueur ne peut pas acheter la carte.
		assertEquals(true, zonetest.howManyPlayerFigurine(playertest) == 0);
		tabcarte = Inventorytest.getCardCivilisation();
		assertEquals(true, tabcarte.length == 0);

		Inventorytest.addRessource(Ressource.STONE, 4);
		while(Inventorytest.getRessource(Ressource.STONE) == 4) {//Tant que le joueur ne prend pas la carte.
			assertEquals(true, tabcarte.length == 0);
			zonetest.placeFigurine(1, playertest);
			zonetest.playerRecoveryFigurine(playertest, Inventorytest);
		}

		tabcarte = Inventorytest.getCardCivilisation();
		assertEquals(1, tabcarte.length);
		assertEquals(4, Inventorytest.getRessource(Ressource.WOOD));
		Inventorytest.addRessource(Ressource.WOOD, -4);
		assertEquals(0, Inventorytest.getRessource(Ressource.WOOD));

		//#####################################
		//La carte donne des ressources variables.
		cartetest = new CarteCivilisation(0,0,0,Ressource.WOOD,0);
		zonetest.setCard(cartetest);

		Inventorytest.addRessource(Ressource.CLAY, 4);
		while(Inventorytest.getRessource(Ressource.CLAY) == 4) {//Tant que le joueur ne prend pas la carte.
			assertEquals(true, tabcarte.length == 1);
			zonetest.placeFigurine(1, playertest);
			zonetest.playerRecoveryFigurine(playertest, Inventorytest);
		}
		tabcarte = Inventorytest.getCardCivilisation();
		assertEquals(2, tabcarte.length);
		assertEquals(true, Inventorytest.getRessource(Ressource.WOOD) >= 0);
		assertEquals(true, Inventorytest.getRessource(Ressource.WOOD) <= 4);

		//#####################################
		//La carte donne des points de victoire
		cartetest = new CarteCivilisation(1,0,0,Ressource.WOOD,5);
		zonetest.setCard(cartetest);

		Inventorytest.addRessource(Ressource.CLAY, 4);
		while(Inventorytest.getRessource(Ressource.CLAY) == 4) {//Tant que le joueur ne prend pas la carte.
			assertEquals(true, tabcarte.length == 2);
			zonetest.placeFigurine(1, playertest);
			zonetest.playerRecoveryFigurine(playertest, Inventorytest);
		}
		tabcarte = Inventorytest.getCardCivilisation();
		assertEquals(3, tabcarte.length);
		assertEquals(5, playertest.getScore());
	}
}
