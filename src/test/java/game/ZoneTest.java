package Game;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ZoneTest {

	private Zone zone;
	
	@Test
	public void testHasEnoughSpace () {
		/* DEPENDENCIES INITIALISATION */
		
		/* UNIT TESTS */
		int MAX_SPACE = 10;
		this.zone = new Zone("Unit_Test", 5, MAX_SPACE);
		
		/* ASSERTS */
		assertEquals(false, this.zone.hasEnoughSpace(MAX_SPACE+1));
		assertEquals(true, this.zone.hasEnoughSpace(MAX_SPACE));
		assertEquals(true, this.zone.hasEnoughSpace(3));
		assertEquals(true, this.zone.hasEnoughSpace(0));
		assertEquals(false, this.zone.hasEnoughSpace(-1));
	}
	
	@Test
	public void testAddFigurine () {
		/* UNIT TESTS */
		int MAX_SPACE = 10;
		this.zone = new Zone("Unit_Test", 5, MAX_SPACE);
		
		/* ASSERTS */
		assertEquals(false, this.zone.addFigurine(MAX_SPACE+1));
		assertEquals(true, this.zone.addFigurine(0));
		assertEquals(false, this.zone.addFigurine(-1));
		assertEquals(true, this.zone.addFigurine(MAX_SPACE));
		assertEquals(false, this.zone.addFigurine(1)); // False because it is full
	}
	
	@Test
	public void testSubFigurine () {
		/* UNIT TESTS */
		int MAX_SPACE = 10;
		this.zone = new Zone("Unit_Test", 5, MAX_SPACE);
		
		/* ASSERTS */
		assertEquals(false, this.zone.subFigurine(MAX_SPACE+1));
		assertEquals(true, this.zone.subFigurine(MAX_SPACE));
		assertEquals(true, this.zone.subFigurine(3));
		assertEquals(true, this.zone.subFigurine(0));
		assertEquals(false, this.zone.subFigurine(-1));
	}

}
