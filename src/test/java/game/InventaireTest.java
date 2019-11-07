package game;

import static org.junit.Assert.*;

import org.junit.Test;

public class InventaireTest {
	
	private Inventaire inv;

	@Test
	public void testAddWood () {
		/* DEPENDENCIES INITIALISATION */
		
		/* UNIT TESTS */
		int ret;
		this.inv = new Inventaire();
		
		ret = this.inv.addResource(9, 0);
		assertEquals(3,this.inv.getWood());
		assertEquals(3,ret);
		
		ret = this.inv.addResource(-1, 0);
		assertEquals(3, this.inv.getWood());
		assertEquals(-1, ret);
		
		
		ret = this.inv.addResource((Settings.MAX_DICE * Settings.MAX_FIGURINE_IN_ZONE) + 1,0);
		assertEquals(3, this.inv.getWood());
		assertEquals(-1, ret);
		
		ret = this.inv.addResource((Settings.MAX_DICE * Settings.MAX_FIGURINE_IN_ZONE),0);
		assertEquals((Settings.MAX_DICE * Settings.MAX_FIGURINE_IN_ZONE)/3 +3 , this.inv.getWood());
		assertEquals(17, ret);
		
		ret = this.inv.addResource(8, 1);
		assertEquals(2,this.inv.getClay());
		assertEquals(2,ret);
		
		ret = this.inv.addResource(-1, 1);
		assertEquals(2, this.inv.getClay());
		assertEquals(-1, ret);
		
		
		ret = this.inv.addResource((Settings.MAX_DICE * Settings.MAX_FIGURINE_IN_ZONE) + 1,1);
		assertEquals(2, this.inv.getClay());
		assertEquals(-1, ret);
		
		ret = this.inv.addResource((Settings.MAX_DICE * Settings.MAX_FIGURINE_IN_ZONE),1);
		assertEquals((Settings.MAX_DICE * Settings.MAX_FIGURINE_IN_ZONE)/4 +2, this.inv.getClay());
		assertEquals(12, ret);
		
		ret = this.inv.addResource(10, 2);
		assertEquals(2,this.inv.getStone());
		assertEquals(2,ret);
		
		ret = this.inv.addResource(-1, 0);
		assertEquals(2, this.inv.getStone());
		assertEquals(-1, ret);
		
		
		ret = this.inv.addResource((Settings.MAX_DICE * Settings.MAX_FIGURINE_IN_ZONE) + 1,2);
		assertEquals(2, this.inv.getStone());
		assertEquals(-1, ret);
		
		ret = this.inv.addResource((Settings.MAX_DICE * Settings.MAX_FIGURINE_IN_ZONE),2);
		assertEquals((Settings.MAX_DICE * Settings.MAX_FIGURINE_IN_ZONE)/5 +2, this.inv.getStone());
		assertEquals(10, ret);
		
		ret = this.inv.addResource(12, 3);
		assertEquals(2,this.inv.getGold());
		assertEquals(2,ret);
		
		ret = this.inv.addResource(-1, 3);
		assertEquals(2, this.inv.getGold());
		assertEquals(-1, ret);
		
		
		ret = this.inv.addResource((Settings.MAX_DICE * Settings.MAX_FIGURINE_IN_ZONE) + 1,3);
		assertEquals(2, this.inv.getGold());
		assertEquals(-1, ret);
		
		ret = this.inv.addResource((Settings.MAX_DICE * Settings.MAX_FIGURINE_IN_ZONE),3);
		assertEquals((Settings.MAX_DICE * Settings.MAX_FIGURINE_IN_ZONE)/6 +2, this.inv.getGold());
		assertEquals(9, ret);
	}

}
