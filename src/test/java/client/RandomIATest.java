package client;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import Game.Settings;
import Client.IA;
import Client.RandomIA;

class RandomIATest {

	private IA ia = new RandomIA();
	
	@Test
	public void testRandomIA() {
		
		//chooseZone
		for(int i=0;i<100;i++) {
			int zone = ia.chooseZone();
			assertEquals(true,zone >=0 && zone <= Settings.NB_ZONES-1);
		}
		
		//chooseNumber
		assertEquals(-1,ia.chooseNumber(-1));
		assertEquals(-1,ia.chooseNumber(0));
		assertEquals(-1,ia.chooseNumber(Settings.MAX_FIGURINE+1));
		assertEquals(-1,ia.chooseNumber(Settings.MAX_FIGURINE+2));
		
		
		for(int j=1;j<=Settings.MAX_FIGURINE;j++) {
			for(int i=0;i<100;i++) {
				int numberFigures = ia.chooseNumber(j);
				assertEquals(true,numberFigures >=1 && numberFigures <= j);
			}
		}	
	}
}
