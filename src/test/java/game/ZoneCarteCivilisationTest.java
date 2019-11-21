package game;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import client.RandomIA;

public class ZoneCarteCivilisationTest {
	
	private ZoneCarteCivilisation z;
	private Player player;

	//* Test pour verifier le minimum de figurines à placer dans la zone
	@Test
	public void testGetMinimalFigurineRequierement(){
		this.z = new ZoneCarteCivilisation(null, 0);
		assertEquals(1, this.z.getMinimalFigurineRequierement());
	}
	
	@Test
	public void testAbleToChooseZone ()
	{
		this.z = new ZoneCarteCivilisation(null, 0);
		// Test d'initialisation
		assertEquals(true, this.z.ableToChooseZone(0));
		this.z.placeFigurine(1, this.player);
		// Test si un joueur choisi la zone
		assertEquals(false, this.z.ableToChooseZone(0));
	}
	
	//* Test pour placer les figurines dans la zone
	@Test
	public void testPlaceFigurine() {
		this.z = new ZoneCarteCivilisation(null, 0);
		int number=1;
		assertEquals(this.z.getAvailableSpace()-number,0);
		assertEquals(this.z.figurineInZone()+number,1);
		assertEquals(this.z.occupated(),this.player);
	}
	//* test pour verfier s'il enleve bien les figurines sur la zone
	@Test
	public void testRemovePlayerFigurine () {
		this.z = new ZoneCarteCivilisation(null, 0);
		assertEquals(this.z.getAvailableSpace(),1);
		assertEquals(this.z.figurineInZone(),0);
	}
	
	@Test
	public void testHowManyPlayerFigurine ()
	{
		this.z = new ZoneCarteCivilisation(null, 0);
		this.z.placeFigurine(1, this.player);
		// On ajoute une figurine dans la zone, et on verifie le nombre de joueur
		assertEquals(this.z.howManyPlayerFigurine(this.player), 1);
		this.z.removePlayerFigurine(this.player);
		// On a enleve la figurine la figurine et on retest
		assertEquals(this.z.howManyPlayerFigurine(this.player), 0);
	}
	
	@Test
	public void testPlayerRecoveryFigurine ()
	{
		this.z = new ZoneCarteCivilisation(null, 1);
		
		// Test gain de ressource
		CarteCivilisation card = new CarteCivilisation(0, 0, 0, Ressource.WOOD, 3);
		this.z.setCard(card);
		this.player = new Player("Jean", new RandomIA());
		this.player.getInventory().addRessource(Ressource.WOOD, 5);
		this.z.placeFigurine(1, this.player);
		this.player.placeFigurine(1);
		// Test d'initialisation
		assertEquals(this.z.howManyPlayerFigurine(this.player), 1);
		assertEquals(this.player.getCurrentFigurine(), Settings.START_FIGURINE - 1);
		this.z.playerRecoveryFigurine(this.player);
		// Test apres fonction
		assertEquals(this.z.howManyPlayerFigurine(this.player), 0);
		assertEquals(this.player.getCurrentFigurine(), Settings.START_FIGURINE);
		
		// Test gain de points
		card = new CarteCivilisation(1, 0, 0, Ressource.WOOD, 3);
		this.z.setCard(card);
		this.player = new Player("Jean", new RandomIA());
		this.player.getInventory().addRessource(Ressource.WOOD, 5);
		this.z.placeFigurine(1, this.player);
		this.player.placeFigurine(1);
		// Test d'initialisation
		assertEquals(this.z.howManyPlayerFigurine(this.player), 1);
		assertEquals(this.player.getCurrentFigurine(), Settings.START_FIGURINE - 1);
		this.z.playerRecoveryFigurine(this.player);
		// Test apres fonction
		assertEquals(this.z.howManyPlayerFigurine(this.player), 0);
		assertEquals(this.player.getCurrentFigurine(), Settings.START_FIGURINE);
	}
}