package game.zone;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import game.Ressource;
import inventory.Inventory;
import player.Player;

public class ZoneFieldTest {
	ZoneField zoneField;
	
	@Test
	public void playerRecoveryFigurine() {
		//player 1
		Player player = new Player(null,null);
		Inventory inventory = new Inventory();
		
		//player 2
		Player player2 = new Player(null,null);
		Inventory inventoryP2 = new Inventory();
		
		zoneField = new ZoneField(null,Ressource.FIELD);
		zoneField.placeFigurine(zoneField.getMinimalFigurineRequierement(), player);
		
		//le joueur 2 qui n'a pas de figurine dans la zone on verifie qu'il n'a pas gagner de marqueur
		zoneField.playerRecoveryFigurine(player2, inventoryP2);
		assertEquals(true,inventoryP2.getRessource(Ressource.FIELD)==0);
		
		//le joueur qui a placer dans la zonne recupere ces figurine et 1 FIELD
		zoneField.playerRecoveryFigurine(player, inventory);
		assertEquals(true,inventory.getRessource(Ressource.FIELD)==1);
		
		
		//reinitialisation de l'invenaire pour un test avec plusieur FIELD
		inventory = new Inventory();
		for(int i=1;i<20;i++) {
			zoneField.placeFigurine(zoneField.getMinimalFigurineRequierement(), player);
			zoneField.playerRecoveryFigurine(player,inventory);
			assertEquals(true,inventory.getRessource(Ressource.FIELD)==i);
		}

	}
}
