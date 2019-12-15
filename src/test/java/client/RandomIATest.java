package client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


import org.junit.jupiter.api.Test;

import client.IA;
import client.RandomIA;
import game.Ressource;
import inventory.Inventory;
import player.Player;

public class RandomIATest {
	
	public Inventory inv = new Inventory();
	public IA testIa = new RandomIA(null,inv.getInventoryIA());

	@Test
	void testChooseZone() {
		for(int i = 1; i <= 10; i++) {
			//On incremente la taille du tableau a chaque iteration. 
			int[] tab1 = new int[i];

			for(int j =0; j < 100; j++) {
				//On test 100 fois que l'indice renvoye est bien compris entre 0 et tab1.length
				int test = testIa.chooseZone(tab1,null , null);
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
		//CAS 1
		inv.addRessource(Ressource.WOOD, 1);
		inv.addRessource(Ressource.CLAY, 2);
		inv.addRessource(Ressource.STONE, 3);
		
		int i1 = 3;
		
		for(int j =0; j < 50; j++) {
			int[] test = testIa.chooseRessource(i1);
			assertEquals(true,test[0] < 3);
			assertEquals(true,test[0] >= 0);
			assertEquals(true,test[1] > 0);
			assertEquals(true,test[1] <= 3);

			assertEquals(false,test[0] >= 3);
			assertEquals(false,test[0] < 0);
			assertEquals(false,test[1] < 0);
			assertEquals(false,test[1] > 3);
		}
		
		
		//CAS 2
		inv = new Inventory();
		testIa = new RandomIA(null,inv.getInventoryIA());
		
		inv.addRessource(Ressource.WOOD, 1);
		inv.addRessource(Ressource.CLAY, 1);
		int i2 = 2;

		for(int j =0; j < 50; j++) {
			int[] test = testIa.chooseRessource(i2);
			assertEquals(true,test[0] < 2);
			assertEquals(true,test[0] >= 0);
			assertEquals(true,test[1] == 1);

			assertEquals(false,test[0] >= 2);
			assertEquals(false,test[0] < 0);
			assertEquals(false,test[1] < 0);
			assertEquals(false,test[1] >= 2);
		}

		//CAS 3
		inv = new Inventory();
		testIa = new RandomIA(null,inv.getInventoryIA());
		
		inv.addRessource(Ressource.WOOD, 3);
		int i3 = 3;

		for(int j =0; j < 50; j++) {
			int[] test = testIa.chooseRessource(i3);
			assertEquals(true,test[0] == 0);
			assertEquals(true,test[1] > 0);
			assertEquals(true,test[1] <= i3);

			assertEquals(false,test[0] != 0);
			assertEquals(false,test[1] < 0);
			assertEquals(false,test[1] > i3);
		}
	}

	@Test 
	void testPickCard() {

		//Test dans un cas ou il dois depenser toutes ses ressources. 
		inv = new Inventory();
		testIa = new RandomIA(null,inv.getInventoryIA());
		
		inv.addRessource(Ressource.GOLD, 4);
		
		int number = 4;
		int[] res;

		for(int j =0; j < 50; j++) {
			res = testIa.pickCard(number);

			assertEquals(true,res[0] == 0);
			assertEquals(true,res[1] == 0);
			assertEquals(true,res[2] == 0);
			assertEquals(true,res[3] == 4 || res[3] == 0);
		}

		//Test dans un cas general
		inv = new Inventory();
		testIa = new RandomIA(null,inv.getInventoryIA());
		
		inv.addRessource(Ressource.WOOD, 1);
		inv.addRessource(Ressource.CLAY, 2);
		inv.addRessource(Ressource.STONE, 3);
		inv.addRessource(Ressource.GOLD, 4);
		number = 5;

		for(int j =0; j < 50; j++) {
			res = testIa.pickCard(number);

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
		boolean[] res;

		for(int j =0; j < 50; j++) {
			res = testIa.pickTools();

			assertEquals(true, res[0] == false);
			assertEquals(true, res[1] == false);
			assertEquals(true, res[2] == false);
		}
		
		//Cas general 
		inv = new Inventory();
		testIa = new RandomIA(null,inv.getInventoryIA());
		Player testPlayer = new Player("test",inv.getInventoryIA());
		
		for(int i =0; i<5;i++) {
			inv.getTools().incrementTool();
		}
		inv.getTools().useTools(testPlayer, 0, null);
		boolean[] testBooleans = inv.getTools().getToolsUsed();

		for(int j =0; j < 50; j++) {
			res = testIa.pickTools();
			
			if(testBooleans[0]) {
				assertEquals(true, res[0] == false);
			}else {
				assertEquals(true, res[0] == false || res[0] == true);
			}
			if(testBooleans[1]) {
				assertEquals(true, res[1] == false);
			}else {
				assertEquals(true, res[1] == false || res[1] == true);
			}
			
			if(testBooleans[2]) {
				assertEquals(true, res[2] == false);
			}else {
				assertEquals(true, res[2] == false || res[2] == true);
			}
		}
	}
	
	@Test
	void testChooseTirage() {
		//TEST SUR LA VALEUR DU TIRAGE CHOISI.
		int[] tirage = new int[] {5,5,5,5};
		boolean[] alreadyChoose = new boolean[] {false,false,false,false};

		int res = testIa.chooseTirage(tirage, alreadyChoose);
		for(int i=0; i <100; i++) {
			assertEquals(true, tirage[res] == 5);
			assertEquals(true, res >=0 && res < tirage.length);
		}
		
		tirage = new int[] {1,2,3,4};//TEST POUR CHAQUE TIRAGE.
		
		alreadyChoose = new boolean[] {false,true,true,true};
		res = testIa.chooseTirage(tirage, alreadyChoose);
		for(int i=0; i <100; i++) {
			assertEquals(true, tirage[res] == 1);
			assertEquals(true, res == 0);
		}
		
		alreadyChoose = new boolean[] {true,false,true,true};
		res = testIa.chooseTirage(tirage, alreadyChoose);
		for(int i=0; i <100; i++) {
			assertEquals(true, tirage[res] == 2);
			assertEquals(true, res == 1);
		}
		
		alreadyChoose = new boolean[] {true,true,false,true};
		res = testIa.chooseTirage(tirage, alreadyChoose);
		for(int i=0; i <100; i++) {
			assertEquals(true, tirage[res] == 3);
			assertEquals(true, res == 2);
		}
		
		alreadyChoose = new boolean[] {true,true,true,false};
		res = testIa.chooseTirage(tirage, alreadyChoose);
		for(int i=0; i <100; i++) {
			assertEquals(true, tirage[res] == 4);
			assertEquals(true, res == 3);
		}
	}
	
	@Test
	void testUseRessourceCard() {
		int res;
		for(int i = 0; i <100; i++) {
			res = testIa.useRessourceCard();
			assertEquals(true, (res >=0 && res < 4) || (res == -1));
		}
	}
	
	
	@Test
	public void testChooseRessourceBuildingNotImposed ()
	{
		this.inv = new Inventory();
		this.inv.addRessource(Ressource.WOOD, 1);
		this.inv.addRessource(Ressource.CLAY, 3);
		this.inv.addRessource(Ressource.STONE, 2);
		testIa = new RandomIA(null,inv.getInventoryIA());
		
		Ressource[] res = this.testIa.chooseRessourceBuildingNotImposed(4, 3);
		// TEST TAILLE
		assertEquals(res.length, 4);
		
		// TEST VALEUR ATTENDUES
		Ressource[] nbOcc = new Ressource[3];
		int index = 0;
		for(int i = 0; i < res.length; i++) {
			boolean find = false;
			for(int j = 0; j<nbOcc.length; j++) {
				if(res[i] == nbOcc[j]) {
					find = true;
				}
			}
			if(!find) {
				nbOcc[index] = res[i];
				index++;
			}
		}
		assertNotNull(nbOcc);
	}
	
	@Test
	public void testChooseRessourceBuildingChoosed ()
	{
		this.inv = new Inventory();
		this.inv.addRessource(Ressource.WOOD, 0);
		this.inv.addRessource(Ressource.CLAY, 0);
		this.inv.addRessource(Ressource.STONE, 0);
		this.inv.addRessource(Ressource.GOLD, 4);
		testIa = new RandomIA(null,inv.getInventoryIA());

		for(int i = 0; i<50; i++) {
			Ressource[] res = this.testIa.chooseRessourceBuildingChoosed();
			// TEST TAILLE
			assertEquals(true,res.length >= 0 && res.length < 8);
			
			// TEST VALEUR ATTENDUES
			for (int j = 0; j < res.length; j++)
			{
				assertEquals(Ressource.GOLD, res[j]);
			}
		}
	}
}
