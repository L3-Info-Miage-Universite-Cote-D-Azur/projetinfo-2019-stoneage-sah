package client;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class RandomIATest {
	public IA testIa = new RandomIA();
	
	@Test
	void testChooseZone() {
		for(int i = 1; i <= 10; i++) {
			//On incremente la taille du tableau a chaque iteration. 
			int[] tab1 = new int[i];
			String[] tab2 = new String[i];
			
			for(int j =0; j < 100; j++) {
				//On test 100 fois que l'indice renvoye est bien compris entre 0 et tab1.length
				int test = testIa.chooseZone(tab1, tab2);
				assertEquals(true,test < tab1.length);
				assertEquals(true,test >= 0);
			}
		}
	}
	
	@Test
	void testChooseNumber() {
		for(int j =0; j < 100; j++) {
			//On test 100 fois que le nombre renvoye est bien compris entre min et max sur 3 cas differents. 
			int test = testIa.chooseNumber(1, 7);
			assertEquals(true,test <= 7);
			assertEquals(true,test >= 1);
			
			int test2 = testIa.chooseNumber(1, 2);
			assertEquals(true,test2 <= 7);
			assertEquals(true,test2 >= 1);
			
			int test3 = testIa.chooseNumber(2, 2);
			assertEquals(true,test3 <= 7);
			assertEquals(true,test3 >= 1);
		}
	}
	
	@Test
	void testChooseRessource() {
		
		//On test 50 fois que l'indice renvoye est bien compris en 0 et tab.length
		//On test 50 fois que le nombre recupere est bien entre 1 et 3.
		int[] tab1 = new int[] {1,2,3};
		String[] tab2 = new String[3];
		int i1 = 5;
			
		for(int j =0; j < 50; j++) {
			int[] test = testIa.chooseRessource(i1, tab1, tab2);
			assertEquals(true,test[0] < tab1.length);
			assertEquals(true,test[0] >= 0);
			assertEquals(true,test[1] > 0);
			assertEquals(true,test[1] <= 3);
			
			assertEquals(false,test[0] >= tab1.length);
			assertEquals(false,test[0] < 0);
			assertEquals(false,test[1] < 0);
			assertEquals(false,test[1] > 3);
		}
		
		//On test 50 fois que l'indice renvoye est bien compris en 0 et tab.length
		//On test 50 fois que le nombre recupere est bien 1.
		int[] tab3 = new int[] {1,1,1,1,1};
		String[] tab4 = new String[5];
		int i2 = 2;
			
		for(int j =0; j < 50; j++) {
			int[] test = testIa.chooseRessource(i2, tab3, tab4);
			assertEquals(true,test[0] < tab3.length);
			assertEquals(true,test[0] >= 0);
			assertEquals(true,test[1] == 1);
			
			assertEquals(false,test[0] >= tab3.length);
			assertEquals(false,test[0] < 0);
			assertEquals(false,test[1] < 0);
			assertEquals(false,test[1] >= 2);
		}
		
		//On test 50 fois que l'indice renvoye est bien compris en 0 et tab.length
		//On test 50 fois que le nombre recupere est bien entre 1 et i3.
		int[] tab5 = new int[] {5,6,7,4};
		String[] tab6 = new String[4];
		int i3 = 3;
			
		for(int j =0; j < 50; j++) {
			int[] test = testIa.chooseRessource(i3, tab5, tab6);
			assertEquals(true,test[0] < tab5.length);
			assertEquals(true,test[0] >= 0);
			assertEquals(true,test[1] > 0);
			assertEquals(true,test[1] <= i3);
			
			assertEquals(false,test[0] >= tab5.length);
			assertEquals(false,test[0] < 0);
			assertEquals(false,test[1] < 0);
			assertEquals(false,test[1] > i3);
		}
	}
}
