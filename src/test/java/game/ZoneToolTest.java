package game;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ZoneToolTest {
	
	private ZoneTool z;
	private Player player;

	//* Test pour verifier le minimum de figurines à placer dans la zone
	@Test
	public void testGetMinimalFigurineRequierement(){
		this.z = new ZoneTool(null);
		assertEquals(1, this.z.getMinimalFigurineRequierement());
	}
	//* Test pour placer les figurines dans la zone
	@Test
	public void testPlaceFigurine() {
		this.z = new ZoneTool(null);
		int number=1;
		assertEquals(this.z.getAvailableSpace()-number,0);
		assertEquals(this.z.figurineInZone()+number,1);
		assertEquals(this.z.occupated(),this.player);
	}
	//* Test pour voir si le joueur peut choisir ou non la zone
	@Test
	public void testAbleToChooseZone() {
		this.z = new ZoneTool(null);
		assertEquals(this.z.getAvailableSpace()>0,true);
	}
	//* test pour verfier s'il enleve bien les figurines sur la zone
	@Test
	public void testRemovePlayerFigurine () {
		this.z = new ZoneTool(null);
		assertEquals(this.z.getAvailableSpace(),1);
		assertEquals(this.z.figurineInZone(),0);
	}
	
	@Test
	public void testHowManyPlayerFigurine ()
	{
		this.z = new ZoneTool(null);
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
		this.z = new ZoneTool(null);
		this.player = new Player(null, null);
		this.z.placeFigurine(1, this.player);
		this.player.placeFigurine(1);
		assertEquals(this.z.howManyPlayerFigurine(this.player), 1);
		assertEquals(this.player.getCurrentFigurine(), Settings.START_FIGURINE - 1);
		this.z.playerRecoveryFigurine(this.player);
		assertEquals(this.z.howManyPlayerFigurine(this.player), 0);
		assertEquals(this.player.getCurrentFigurine(), Settings.START_FIGURINE);
	}
}