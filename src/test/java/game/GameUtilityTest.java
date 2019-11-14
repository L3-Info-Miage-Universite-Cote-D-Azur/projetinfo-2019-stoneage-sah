package game;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import client.RandomIA;

public class GameUtilityTest {
	@Test
	public void testInitPlayer() {
		ArrayList<String> liste = new ArrayList<String>(Settings.PLAYER_NAME);
		
		Player[] test = GameUtility.initPlayer(); 
		
		//Test que la longueur est bonne
		assertEquals(2, test.length);
		
		//Test que les prenoms sont correctes
		assertEquals(true, liste.contains(test[0].getName()));
		assertEquals(true, liste.contains(test[1].getName()));
		
		//Test que l'IA est correcte. 
		assertEquals(true, test[0].getIA() == Settings.IA_list[0]);
		assertEquals(true, test[1].getIA() == Settings.IA_list[0]);
	}
	
	@Test
	public void testInitZone() {
		//On test si les zone creer sont correcte. 
		Zone[] test = GameUtility.initZone();
		
		assertEquals("Foret", test[0].getName());
		assertEquals(Settings.MAX_ZONERESSOURCE_SPACE, test[0].getAvailableSpace());
		assertEquals(1, test[0].getMinimalFigurineRequierement());

		assertEquals("Glaisiere", test[1].getName());
		assertEquals(Settings.MAX_ZONERESSOURCE_SPACE, test[1].getAvailableSpace());
		assertEquals(1, test[1].getMinimalFigurineRequierement());

		assertEquals("Carriere", test[2].getName());
		assertEquals(Settings.MAX_ZONERESSOURCE_SPACE, test[2].getAvailableSpace());
		assertEquals(1, test[2].getMinimalFigurineRequierement());
		
		assertEquals("Riviere", test[3].getName());
		assertEquals(Settings.MAX_ZONERESSOURCE_SPACE, test[3].getAvailableSpace());
		assertEquals(1, test[3].getMinimalFigurineRequierement());
		
		assertEquals("Chasse", test[4].getName());
		assertEquals(Settings.MAX_PLAYER * Settings.MAX_FIGURINE, test[4].getAvailableSpace());
		assertEquals(1, test[4].getMinimalFigurineRequierement());
		
		assertEquals("Champs", test[5].getName());
		assertEquals(Settings.MAX_ZONEFIELD_SPACE, test[5].getAvailableSpace());
		assertEquals(1, test[5].getMinimalFigurineRequierement());
		
		assertEquals("Cabane de reproduction", test[6].getName());
		assertEquals(Settings.MAX_ZONEHUT_SPACE, test[6].getAvailableSpace());
		assertEquals(2, test[6].getMinimalFigurineRequierement());
		
	}
	
	@Test
	public void testZoneChoose() {
		Player player = new Player("Jean", new RandomIA());
		Zone[] zone = GameUtility.initZone();
		
		int test = GameUtility.zoneChoose(player, zone);
		
		//Test pour verifier la validite du return
		assertEquals(true, test >= 0 );
		assertEquals(true, test < zone.length );
		assertEquals(true, zone[test].getAvailableSpace() > 0 );
	}
	
	@Test
	public void testNumberChoose() {
		Player player = new Player("Jean", new RandomIA());
		Zone[] zone = GameUtility.initZone();
		
		for(int i = 0; i < zone.length; i++) {
			int max = Math.min(player.getCurrentFigurine(),zone[i].getAvailableSpace());
			int min = zone[i].getMinimalFigurineRequierement();
			
			int test = GameUtility.numberChoose(player, zone[i]);
			
			//Test pour verifier la validite du return
			assertEquals(true, test >= min && test <= max);
		}
	}
	
	@Test
	public void testRessourceChoose() {
		Player player = new Player("Jean", new RandomIA());
		player.getInventory().addRessource(Ressource.CLAY, 5);
		int ressourceToFood = 100;
		int test; 
		
		for(int i=0; i < 50; i++) {
			test = GameUtility.ressourceChoose(player, ressourceToFood);
			//Test quand la limite viens d'une ressource.
			assertEquals(true, test <= 5);
		}
		
		ressourceToFood = 3;
		for(int i=0; i < 50; i++) {
			test = GameUtility.ressourceChoose(player, ressourceToFood);
			//Test quand la limite viens de ressourceToFood
			assertEquals(true, test <= 3);
		}
		
	}
}
