package game.zone;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import inventory.Inventory;
import player.Player;

public class ZoneToolTest {
	
	ZoneTool zoneTool;
	
	@Test
	public void playerRecoveryFigurine() {
		//player 1
		Player player = new Player(null,null);
		Inventory inventory = new Inventory();
			
		//player 2
		Player player2 = new Player(null,null);
		Inventory inventoryP2 = new Inventory();
			
		zoneTool = new ZoneTool(null);
		zoneTool.placeFigurine(zoneTool.getMinimalFigurineRequierement(), player);
			
		//le joueur 2 qui n'a pas de figurine dans la zone on verifie qu'il n'a pas gagner de marqueur
		zoneTool.playerRecoveryFigurine(player2, inventoryP2);
		assertEquals(true,inventoryP2.getTools().getTool()==0);
			
		//le joueur qui a placer dans la zonne recupere ces figurine et 1 FIELD
		zoneTool.playerRecoveryFigurine(player, inventory);
		assertEquals(true,inventory.getTools().getTool()==1);
			
			
		//reinitialisation de l'invenaire pour un test avec plusieur FIELD
		inventory = new Inventory();
		for(int i=1;i<20;i++) {
			zoneTool.placeFigurine(zoneTool.getMinimalFigurineRequierement(), player);
			zoneTool.playerRecoveryFigurine(player,inventory);
			if(i<12) {
				assertEquals(true,inventory.getTools().getTool()==i);
			}
			//cas ou on a atteint le max d'outils
			else {
				assertEquals(true,inventory.getTools().getTool()==12);
			}
		}

	}
}
