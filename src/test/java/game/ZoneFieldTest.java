package game;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ZoneFieldTest {
	
	private ZoneField z;
	private Player player;

	//* Test pour verifier le minimum de figurines à placer dans la zone
	@Test
	public void testGetMinimalFigurineRequierement(){
		this.z = new ZoneField(null, null);
		assertEquals(1, this.z.getMinimalFigurineRequierement());
	}
	//* Test pour placer les figurines dans la zone
	@Test
	public void testPlaceFigurine() {
		this.z = new ZoneField(null,null);
		int number=1;
		assertEquals(this.z.getAvailableSpace()-number,0);
		assertEquals(this.z.figurineInZone()+number,1);
		assertEquals(this.z.occupated(),this.player);
	}
	//* Test pour voir si le joueur peut choisir ou non la zone
	@Test
	public void testAbleToChooseZone() {
		this.z = new ZoneField(null,null);
		assertEquals(this.z.getAvailableSpace()>0,true);
	}
	//* test pour verfier s'il enleve bien les figurines sur la zone
	@Test
	public void testRemovePlayerFigurine() {
		this.z = new ZoneField(null,null);
		assertEquals(this.z.getAvailableSpace(),1);
		assertEquals(this.z.figurineInZone(),0);
	}
}