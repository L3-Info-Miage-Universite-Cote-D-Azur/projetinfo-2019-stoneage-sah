package game;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import client.RandomIA;

public class ZoneBuildingTest
{
	private ZoneBuilding zb;
	
	@Test
	public void testAbleToChooseZone ()
	{
		reset();
		this.zb = new ZoneBuilding();
		// Test d'initialisation
		assertEquals(true, this.zb.ableToChooseZone(0));
		this.zb.placeFigurine(0, new Player(Settings.getRandomName(), new RandomIA()));
		// Test si un joueur choisi la zone
		assertEquals(false, this.zb.ableToChooseZone(0));
	}
	
	@Test
	public void testHowManyPlayerFigurine ()
	{
		reset();
		this.zb = new ZoneBuilding();
		Player p = new Player(Settings.getRandomName(), new RandomIA());
		// Test d'initialisation
		assertEquals(0, this.zb.howManyPlayerFigurine(p));
		this.zb.placeFigurine(0, p);
		// Test si un joueur choisi la zone
		assertEquals(1, this.zb.howManyPlayerFigurine(p));
	}
	
	@Test
	public void testPlaceFigurine ()
	{
		reset();
		this.zb = new ZoneBuilding();
		Player p = new Player(Settings.getRandomName(), new RandomIA());
		// Test d'initialisation
		assertEquals(true, this.zb.ableToChooseZone(0));
		// Test si un joueur choisi la zone
		this.zb.placeFigurine(0, p);
		assertEquals(false, this.zb.ableToChooseZone(0));
	}
	
	@Test
	public void testPlayerRecoveryFigurine ()
	{
		reset();
		Player p = new Player(Settings.getRandomName(), new RandomIA());
		p.getInventory().addRessource(Ressource.WOOD, 2);
		p.getInventory().addRessource(Ressource.CLAY, 1);
		this.zb = new ZoneBuilding();
		this.zb.placeFigurine(1, p);
		p.placeFigurine(1);
		
		// Test d'initialisation
		assertEquals(false, this.zb.ableToChooseZone(0));
		assertEquals(Settings.START_FIGURINE - 1, p.getCurrentFigurine());
		
		this.zb.playerRecoveryFigurine(p);
		
		// Test apres fonction
		assertEquals(true, this.zb.ableToChooseZone(0));
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
