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
	
	@Test 
	void testPickCard() {
		
		//Test dans un cas ou il dois depenser toutes ses ressources. 
		int[] ressource = new int[]{0,0,0,4};
		int number = 4;
		int[] res;
		
		for(int j =0; j < 50; j++) {
			res = testIa.pickCard(ressource.clone(), number);
			
			assertEquals(true,res[0] == 0);
			assertEquals(true,res[1] == 0);
			assertEquals(true,res[2] == 0);
			assertEquals(true,res[3] == 4 || res[3] == 0);
		}
		
		//Test dans un cas general
		ressource = new int[] {1,2,3,4};
		number = 5;
		
		for(int j =0; j < 50; j++) {
			res = testIa.pickCard(ressource.clone(), number);
			
			assertEquals(true,res[0] >= 0 && res[0] <= 1);
			assertEquals(true,res[1] >= 0 && res[1] <= 2);
			assertEquals(true,res[2] >= 0 && res[2] <= 3);
			assertEquals(true,res[3] >= 0 && res[3] <= 4);
			
			int sum = 0;
			for(int el : res) {
				sum += el;
			}
			assertEquals(true, sum == number || sum == 0);
		}
		
		//Le cas ou l'ia n'a pas assez de ressource est tester en amont et donnerais une boucle infinie ici. 
	}
	
	@Test
	void pickTools(){
		
		//Cas ou il n'as pas d'outils
		int[] tools = new int[] {0,0,0};
		boolean [] toolDispo = new boolean[] {true,true,true};
		boolean[] res;
		
		for(int j =0; j < 50; j++) {
			res = testIa.pickTools(tools, toolDispo);
			
			assertEquals(true, res[0] == false);
			assertEquals(true, res[1] == false);
			assertEquals(true, res[2] == false);
		}
		
		//Cas general 
		tools = new int[] {2,2,1};
		toolDispo = new boolean[] {false, true, false};
		
		for(int j =0; j < 50; j++) {
			res = testIa.pickTools(tools, toolDispo);
			
			assertEquals(true, res[0] == false);
			assertEquals(true, res[1] == false || res[1] == true);
			assertEquals(true, res[2] == false);
		}
		
		//Cas general 2
		tools = new int[] {1,0,0};
		toolDispo = new boolean[] {true, true, true};
		
		for(int j =0; j < 50; j++) {
			res = testIa.pickTools(tools, toolDispo);
			
			assertEquals(true, res[0] == false || res[0] == true);
			assertEquals(true, res[1] == false);
			assertEquals(true, res[2] == false);
		}
	}
}
