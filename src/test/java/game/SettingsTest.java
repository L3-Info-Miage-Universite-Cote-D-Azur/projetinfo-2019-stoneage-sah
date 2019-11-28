package game;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import game.building.*;

public class SettingsTest {
	@Test
	// Test si le nom retourne a bien etait retire de la liste initiale, et s'il appartennait bien a la liste.
	public void testGetRandomName() {
		
		int len = Settings.PLAYER_NAME.size();
		ArrayList<String> liste = new ArrayList<String>(Settings.PLAYER_NAME);
		
		for(int i = 0;i<len;i++) {
			String nom = Settings.getRandomName();
			assertEquals(false, Settings.PLAYER_NAME.contains(nom));
			assertEquals(true,liste.contains(nom));
		}
		assertEquals(0,Settings.PLAYER_NAME.size());
	}
	
	@Test
	public void testGetRandomDeck()
	{
		resetBuildings();
		// Test d'initialisation
		assertEquals(Settings.BUILDINGS.size(), Settings.NB_BUILDING_CARD_IN_DECK * Settings.NB_BUILDING_DECK);
		for (int i = 0; i < Settings.NB_BUILDING_DECK; i++)
		{
			// Test sur la taille de chaque ArrayList
			assertEquals(Settings.getRandomDeck().size(), Settings.NB_BUILDING_CARD_IN_DECK);
		}
		// Test sur une liste vide
		assertEquals(Settings.getRandomDeck().size(), 0);
	}
	
	public void resetBuildings ()
	{
		Settings.BUILDINGS = new ArrayList<Building>(Arrays.asList(
				new BuildingRessourceImposed(10, Ressource.WOOD, Ressource.WOOD, Ressource.CLAY),
				new BuildingRessourceImposed(11, Ressource.WOOD, Ressource.WOOD, Ressource.STONE),
				new BuildingRessourceImposed(12, Ressource.WOOD, Ressource.WOOD, Ressource.GOLD),
				new BuildingRessourceImposed(11, Ressource.WOOD, Ressource.CLAY, Ressource.CLAY),
				new BuildingRessourceImposed(12, Ressource.WOOD, Ressource.CLAY, Ressource.STONE),
				new BuildingRessourceImposed(12, Ressource.WOOD, Ressource.CLAY, Ressource.STONE),
				new BuildingRessourceImposed(13, Ressource.CLAY, Ressource.CLAY, Ressource.STONE),
				new BuildingRessourceImposed(14, Ressource.WOOD, Ressource.STONE, Ressource.GOLD),
				new BuildingRessourceImposed(14, Ressource.WOOD, Ressource.STONE, Ressource.GOLD),
				new BuildingRessourceImposed(13, Ressource.WOOD, Ressource.STONE, Ressource.STONE),
				new BuildingRessourceImposed(13, Ressource.WOOD, Ressource.CLAY, Ressource.GOLD),
				new BuildingRessourceImposed(13, Ressource.WOOD, Ressource.CLAY, Ressource.GOLD),
				new BuildingRessourceImposed(16, Ressource.STONE, Ressource.STONE, Ressource.GOLD),
				new BuildingRessourceImposed(15, Ressource.CLAY, Ressource.STONE, Ressource.GOLD),
				new BuildingRessourceImposed(15, Ressource.CLAY, Ressource.STONE, Ressource.GOLD),
				new BuildingRessourceImposed(14, Ressource.CLAY, Ressource.STONE, Ressource.STONE),
				new BuildingRessourceImposed(14, Ressource.CLAY, Ressource.CLAY, Ressource.GOLD),
				new BuildingRessourceChoosed(),
				new BuildingRessourceChoosed(),
				new BuildingRessourceChoosed(),
				new BuildingRessourceNotImposed(2, 5),
				new BuildingRessourceNotImposed(1, 5),
				new BuildingRessourceNotImposed(4, 4),
				new BuildingRessourceNotImposed(3, 4),
				new BuildingRessourceNotImposed(2, 4),
				new BuildingRessourceNotImposed(1, 4),
				new BuildingRessourceNotImposed(4, 5),
				new BuildingRessourceNotImposed(3, 5)
				));
	}
}
