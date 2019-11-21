package game;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import client.RandomIA;

public class GameUtilityTest {
	@Test
	public void testInitPlayer() {
		ArrayList<String> liste = new ArrayList<String>(Settings.PLAYER_NAME);
		int maxPlayer = 4;
		Player[] test = GameUtility.initPlayer(maxPlayer); 
		
		//Test que la longueur est bonne
		assertEquals(maxPlayer, test.length);
		
		//Test que les prenoms sont correctes
		assertEquals(true, liste.contains(test[0].getName()));
		assertEquals(true, liste.contains(test[1].getName()));
		
		//Test que l'IA est correcte. 
		assertEquals(true, test[0].getIA() == Settings.IA_list[0]);
		assertEquals(true, test[1].getIA() == Settings.IA_list[0]);
	}
	
	@Test
	public void testZoneChoose() {
		int maxPlayer = 4;
		Player player = new Player("Jean", new RandomIA());
		CarteCivilisationManager cardManager;
		Zone[] zones = new Zone[Settings.NB_ZONES];
		ZoneCarteCivilisation[] zoneCarteCivilisation = new ZoneCarteCivilisation[4];

		//Les zones du jeu. 
		zones[0] = new ZoneRessource("Foret",Ressource.WOOD,Settings.MAX_ZONERESSOURCE_SPACE);
		zones[1] = new ZoneRessource("Glaisiere",Ressource.CLAY,Settings.MAX_ZONERESSOURCE_SPACE);
		zones[2] = new ZoneRessource("Carriere",Ressource.STONE,Settings.MAX_ZONERESSOURCE_SPACE);
		zones[3] = new ZoneRessource("Riviere",Ressource.GOLD,Settings.MAX_ZONERESSOURCE_SPACE);
		//La zone de chasse a : nombre de joueur x le nombre de figurines maximum d'espace. 
		zones[4] = new ZoneRessource("Chasse", Ressource.FOOD,maxPlayer * Settings.MAX_FIGURINE);
		zones[5] = new ZoneField("Champs", Ressource.FIELD);
		zones[6] = new ZoneHut("Cabane de reproduction");
		zones[7] = new ZoneTool("Le Fabricant D'outils");
		
		//gestion des carte civilisation
		ZoneCarteCivilisation zcc;
		for(int i=0;i<4;i++) {
			zcc=new ZoneCarteCivilisation("Carte Civilisation: "+ (i+1), i+1);
			zoneCarteCivilisation[i]=zcc;
			zones[8+i]=zcc;
		}
		cardManager = new CarteCivilisationManager(zoneCarteCivilisation);
		
		zones[12] = new ZoneBuilding();
		zones[13] = new ZoneBuilding();
		zones[14] = new ZoneBuilding();
		zones[15] = new ZoneBuilding();
		
		int test = GameUtility.zoneChoose(player, zones);
		
		//Test pour verifier la validite du return
		assertEquals(true, test >= 0 );
		assertEquals(true, test < zones.length );
		assertEquals(true, zones[test].getAvailableSpace() > 0 );
	}
	
	@Test
	public void testNumberChoose() {
		int maxPlayer = 4;
		Player player = new Player("Jean", new RandomIA());
		CarteCivilisationManager cardManager;
		Zone[] zones = new Zone[Settings.NB_ZONES];
		ZoneCarteCivilisation[] zoneCarteCivilisation = new ZoneCarteCivilisation[4];

		//Les zones du jeu. 
		zones[0] = new ZoneRessource("Foret",Ressource.WOOD,Settings.MAX_ZONERESSOURCE_SPACE);
		zones[1] = new ZoneRessource("Glaisiere",Ressource.CLAY,Settings.MAX_ZONERESSOURCE_SPACE);
		zones[2] = new ZoneRessource("Carriere",Ressource.STONE,Settings.MAX_ZONERESSOURCE_SPACE);
		zones[3] = new ZoneRessource("Riviere",Ressource.GOLD,Settings.MAX_ZONERESSOURCE_SPACE);
		//La zone de chasse a : nombre de joueur x le nombre de figurines maximum d'espace. 
		zones[4] = new ZoneRessource("Chasse", Ressource.FOOD,maxPlayer * Settings.MAX_FIGURINE);
		zones[5] = new ZoneField("Champs", Ressource.FIELD);
		zones[6] = new ZoneHut("Cabane de reproduction");
		zones[7] = new ZoneTool("Le Fabricant D'outils");
		
		//gestion des carte civilisation
		ZoneCarteCivilisation zcc;
		for(int i=0;i<4;i++) {
			zcc=new ZoneCarteCivilisation("Carte Civilisation: "+ (i+1), i+1);
			zoneCarteCivilisation[i]=zcc;
			zones[8+i]=zcc;
		}
		cardManager = new CarteCivilisationManager(zoneCarteCivilisation);
		
		zones[12] = new ZoneBuilding();
		zones[13] = new ZoneBuilding();
		zones[14] = new ZoneBuilding();
		zones[15] = new ZoneBuilding();
		
		for(int i = 0; i < zones.length; i++) {
			int max = Math.min(player.getCurrentFigurine(),zones[i].getAvailableSpace());
			int min = zones[i].getMinimalFigurineRequierement();
			
			int test = GameUtility.numberChoose(player, zones[i]);
			
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
