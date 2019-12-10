package client;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import game.Ressource;
import inventory.Inventory;
import player.Player;

public class CarteCivilisationWoodIATest {
	public Inventory inv = new Inventory();
	public Player play = new Player("Test", inv.getInventoryIA());
	public IA testIa = new CarteCivilisationWoodIA(play.getPlayerIA(),inv.getInventoryIA());

	@Test
	void testChooseZone() {
		for(int i = 1; i <100; i++) {
			//On incremente la taille du tableau a chaque iteration. 
			int[] tab1 = new int[] {7,7,7,7,5000,0,0,0,0,0,0,0,0,0,0};

			for(int j =0; j < 100; j++) {
				//On test 100 fois que l'indice renvoye est bien compris entre 0 et tab1.length
				int test = testIa.chooseZone(tab1, null ,null , null);
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
		testIa = new CarteCivilisationWoodIA(play.getPlayerIA(),inv.getInventoryIA());

		inv.addRessource(Ressource.WOOD, 1);
		inv.addRessource(Ressource.CLAY, 1);
		int i2 = 2;

		for(int j =0; j < 50; j++) {
			int[] test = testIa.chooseRessource(i2);
			assertEquals(true,test[0] < 2);
			assertEquals(true,test[0] >= 0);
			assertEquals(true, test[0] == 1);
			assertEquals(true,test[1] == 1);

			assertEquals(false,test[0] >= 2);
			assertEquals(false,test[0] < 0);
			assertEquals(false,test[1] < 0);
			assertEquals(false,test[1] >= 2);
		}
		//On redemande une deuxieme fois a l'IA de nourrir ses figurines.
		inv.subRessource(Ressource.CLAY, 1);
		i2 = 1;
		
		for(int j =0; j < 50; j++) {
			int[] test = testIa.chooseRessource(i2);
			assertEquals(true,test[1] == 1);
			assertEquals(true,test[0] == 0);

			assertEquals(false,test[0] >= 2);
			assertEquals(false,test[0] < 0);
			assertEquals(false,test[1] < 0);
			assertEquals(false,test[1] >= 2);
		}

		//CAS 3
		inv = new Inventory();
		testIa = new CarteCivilisationWoodIA(play.getPlayerIA(),inv.getInventoryIA());

		inv.addRessource(Ressource.WOOD, 3);
		int i3 = 3;

		for(int j =0; j < 50; j++) {
			int[] test = testIa.chooseRessource(i3);
			assertEquals(true,test[0] == 0);
			assertEquals(true,test[1] > 0);
			assertEquals(true,test[1] == i3);

			assertEquals(false,test[0] != 0);
			assertEquals(false,test[1] < 0);
			assertEquals(false,test[1] > i3);
		}
	}

	@Test 
	void testPickCard() {

		//Test dans un cas ou il dois depenser toutes ses ressources. 
		inv = new Inventory();
		testIa = new CarteCivilisationWoodIA(play.getPlayerIA(),inv.getInventoryIA());

		inv.addRessource(Ressource.GOLD, 4);

		int number = 4;
		int[] res;

		for(int j =0; j < 50; j++) {
			res = testIa.pickCard(number);

			assertEquals(true,res[0] == 0);
			assertEquals(true,res[1] == 0);
			assertEquals(true,res[2] == 0);
			assertEquals(true,res[3] == 4);
		}

		//Test dans un cas general
		inv = new Inventory();
		testIa = new CarteCivilisationWoodIA(play.getPlayerIA(),inv.getInventoryIA());

		inv.addRessource(Ressource.WOOD, 1);
		inv.addRessource(Ressource.CLAY, 2);
		inv.addRessource(Ressource.STONE, 3);
		inv.addRessource(Ressource.GOLD, 4);
		number = 5;

		for(int j =0; j < 50; j++) {
			res = testIa.pickCard(number);

			assertEquals(true,res[0] == 0);
			assertEquals(true,res[1] == 0);
			assertEquals(true,res[2] == 1);
			assertEquals(true,res[3] == 4);

			int sum = 0;
			for(int el : res) {
				sum += el;
			}
			assertEquals(true, sum == number);
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
		testIa = new CarteCivilisationWoodIA(play.getPlayerIA(),inv.getInventoryIA());
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
				assertEquals(true, res[0] == true);
			}
			if(testBooleans[1]) {
				assertEquals(true, res[1] == false);
			}else {
				assertEquals(true, res[1] == true);
			}

			if(testBooleans[2]) {
				assertEquals(true, res[2] == false);
			}else {
				assertEquals(true, res[2] == true);
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
			assertEquals(true, res == 0);
		}
	}
}
