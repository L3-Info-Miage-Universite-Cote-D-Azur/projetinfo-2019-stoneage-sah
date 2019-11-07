package Game;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class InventaireTest {

	private Inventaire inv;
	
	@Test
	public void testAddWood () {
		/* DEPENDENCIES INITIALISATION */
		
		/* UNIT TESTS */
		boolean ret;
		this.inv = new Inventaire();
		
		ret = this.inv.addWood(-1);
		assertEquals(0, this.inv.getWood());
		assertEquals(false, ret);
		
		
		ret = this.inv.addWood((Settings.MAX_DICE * Settings.MAX_FIGURINE_IN_ZONE)/3 + 1);
		assertEquals(0, this.inv.getWood());
		assertEquals(false, ret);
		
		ret = this.inv.addWood((Settings.MAX_DICE * Settings.MAX_FIGURINE_IN_ZONE)/3);
		assertEquals((Settings.MAX_DICE * Settings.MAX_FIGURINE_IN_ZONE)/3, this.inv.getWood());
		assertEquals(true, ret);
	}

}
