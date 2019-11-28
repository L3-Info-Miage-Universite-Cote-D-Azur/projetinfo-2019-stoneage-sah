package game.zone;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import game.Ressource;
import game.Settings;
import game.building.Building;
import game.building.BuildingRessourceChoosed;
import game.building.BuildingRessourceImposed;
import game.building.BuildingRessourceNotImposed;
import inventory.Inventory;
import player.Player;

public class ZoneBuildingTest
{
	private ZoneBuilding zb;
	
	@Test
	public void testPlayerRecoveryFigurine ()
	{
		reset();
		Inventory inventory = new Inventory();
		Player p = new Player(Settings.getRandomName(),inventory.getInventoryIA());
		inventory.addRessource(Ressource.WOOD, 2);
		inventory.addRessource(Ressource.CLAY, 1);
		this.zb = new ZoneBuilding("testZone");
		this.zb.placeFigurine(1, p);
		
		// Test d'initialisation
		assertEquals(false, this.zb.ableToChooseZone(p));
		assertEquals(Settings.START_FIGURINE - 1, p.getCurrentFigurine());
		
		this.zb.playerRecoveryFigurine(p,inventory);
		
		// Test apres fonction
		assertEquals(true, this.zb.ableToChooseZone(p));
		assertEquals(Settings.START_FIGURINE, p.getCurrentFigurine());
	}
	
	public void reset ()
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
		
		Settings.PLAYER_NAME = new ArrayList<String>(Arrays.asList("Robert","Sardoche","Doritos","JeanCharles","Mclito&Carfly","Helico42"));

	}
}
