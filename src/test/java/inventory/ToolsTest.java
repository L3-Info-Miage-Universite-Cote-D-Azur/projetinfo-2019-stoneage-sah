package inventory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import player.Player;

public class ToolsTest {

	private Tools outils;
	private Player Playertest;

	@Test
	public void testResetToolsUsed() {
		this.outils = new Tools();

		//on test l'instanciation
		assertEquals(this.outils.getTool(),0);
		assertEquals(this.outils.getTools()[0], 0);
		assertEquals(this.outils.getTools()[1], 0);
		assertEquals(this.outils.getTools()[2], 0);
		assertEquals(this.outils.getToolsUsed()[0],false);
		assertEquals(this.outils.getToolsUsed()[1],false);
		assertEquals(this.outils.getToolsUsed()[2],false);
		assertEquals(this.outils.getToolsIA().getTool(),0);
		assertEquals(this.outils.getToolsIA().getTools()[0], 0);
		assertEquals(this.outils.getToolsIA().getTools()[1], 0);
		assertEquals(this.outils.getToolsIA().getTools()[2], 0);
		assertEquals(this.outils.getToolsIA().getToolsUsed()[0],false);
		assertEquals(this.outils.getToolsIA().getToolsUsed()[1],false);
		assertEquals(this.outils.getToolsIA().getToolsUsed()[2],false);

		this.outils.toolsUsed[1] = true;
		assertEquals(this.outils.getToolsUsed()[1],true);
		assertEquals(this.outils.getToolsIA().getToolsUsed()[1],false);
		this.outils.resetToolsUsed();
		for (int i=0; i <this.outils.getToolsIA().toolsUsed.length; i++) {
			assertEquals(this.outils.getToolsIA().toolsUsed[i],false);
		}
	}

	@Test
	public void testIncrementTools() {
		this.outils = new Tools();

		//on test l'instanciation
		assertEquals(this.outils.getTool(),0);
		assertEquals(this.outils.getTools()[0], 0);
		assertEquals(this.outils.getTools()[1], 0);
		assertEquals(this.outils.getTools()[2], 0);
		assertEquals(this.outils.getToolsUsed()[0],false);
		assertEquals(this.outils.getToolsUsed()[1],false);
		assertEquals(this.outils.getToolsUsed()[2],false);
		assertEquals(this.outils.getToolsIA().getTool(),0);
		assertEquals(this.outils.getToolsIA().getTools()[0], 0);
		assertEquals(this.outils.getToolsIA().getTools()[1], 0);
		assertEquals(this.outils.getToolsIA().getTools()[2], 0);
		assertEquals(this.outils.getToolsIA().getToolsUsed()[0],false);
		assertEquals(this.outils.getToolsIA().getToolsUsed()[1],false);
		assertEquals(this.outils.getToolsIA().getToolsUsed()[2],false);

		//on incremente une fois et on verifie le resultat
		this.outils.incrementTool();
		assertEquals(this.outils.getTool(),1);
		assertEquals(this.outils.getTools()[0],1);
		assertEquals(this.outils.getToolsIA().getTool(),1);
		assertEquals(this.outils.getToolsIA().getTools()[0],1);

		//on test dans une petite boucle
		for (int i=1; i<3;i++) {
			if (this.outils.tool<12) {
				this.outils.incrementTool();
				assertEquals(this.outils.getToolsIA().getTool(),1+i);
				assertEquals(this.outils.getToolsIA().tools[(this.outils.getToolsIA().tool)%3]+=1,i%3);}
		}
	}

	@Test
	public void testUseTools() {
		Inventory inv = new Inventory();
		Playertest = new Player("test", inv.getInventoryIA());
		Tools outils = inv.getTools();

		//Cas avec 4 outils
		for(int i = 0; i < 4; i++){
			outils.incrementTool();
		}

		int res = outils.useTools(Playertest, 0, null);
		assertEquals(true, res >= 0);
		assertEquals(true, res <= 4);

		//Cas avec aucun outils
		inv = new Inventory();
		outils = inv.getTools();
		Playertest = new Player("test", inv.getInventoryIA());

		res = outils.useTools(Playertest, 0, null);
		assertEquals(true, res == 0);
		for(int i = 0; i<3; i++) {
			assertEquals(true, outils.getToolsUsed()[i] == false);  		
		}

		//cas avec un outils
		inv = new Inventory();
		outils = inv.getTools();
		Playertest = new Player("test", inv.getInventoryIA());
		outils.incrementTool();

		res = outils.useTools(Playertest, 0, null);
		assertEquals(true, res>= 0);
		assertEquals(true, res <= 1);
		assertEquals(true, outils.getToolsUsed()[0] == false || outils.getToolsUsed()[0] == true);
		for(int i = 1; i<3; i++) {
			assertEquals(true, outils.getToolsUsed()[i] == false);  		
		}
	}
	
	@Test
	public void addUniqueToolTest() {
		Tools outils = new Tools();
		//cas de base que les outils usable a chaque tour soit 3
		assertEquals(outils.tools.length,3);
		//de meme pour l'ia
		assertEquals(outils.getToolsIA().tools.length,3);
		//on ajoute un outil a usage uniquede valeur 5
		outils.addUniqueTool(5);
		assertEquals(outils.tools.length,4);
		assertEquals(outils.tools[3],5);
		//de meme pour l'ia
		assertEquals(outils.getToolsIA().tools.length,4);
		assertEquals(outils.getToolsIA().tools[3],5);
		//ajout d'un outil de valeur 1
		outils.addUniqueTool(1);
		assertEquals(outils.tools.length,5); //1 outils a etait rajouté
		assertEquals(outils.tools[3],5); //ne change pas l'outil precedent
		assertEquals(outils.tools[4],1); //possede le nouvelle outils de valeur 1
		//de meme pour l'ia
		assertEquals(outils.getToolsIA().tools.length,5); //1 outils a etait rajouté
		assertEquals(outils.getToolsIA().tools[3],5); //ne change pas l'outil precedent
		assertEquals(outils.getToolsIA().tools[4],1); //possede le nouvelle outils de valeur 1
	}
	

	
	@Test
	public void setToolsToUsedTest() {
		outils = new Tools();
		for(int i=0;i<5;i++) this.outils.incrementTool();// on met 5 niveau dans les outil (soit 2,2,1)
		assertEquals(this.outils.getTools()[0], 2);
		assertEquals(this.outils.getTools()[1], 2);
		assertEquals(this.outils.getTools()[2], 1);
		//de meme pour l'ia
		assertEquals(this.outils.getToolsIA().getTools()[0], 2);
		assertEquals(this.outils.getToolsIA().getTools()[1], 2);
		assertEquals(this.outils.getToolsIA().getTools()[2], 1);
		//aucun outils est utiliser
		assertEquals(this.outils.getToolsUsed()[0],false);
		assertEquals(this.outils.getToolsUsed()[1],false);
		assertEquals(this.outils.getToolsUsed()[2],false);
		//de meme pour l'ia
		assertEquals(this.outils.getToolsIA().getToolsUsed()[0],false);
		assertEquals(this.outils.getToolsIA().getToolsUsed()[1],false);
		assertEquals(this.outils.getToolsIA().getToolsUsed()[2],false);
		//on ajoute 2 outils a usage unique
		outils.addUniqueTool(5);
		outils.addUniqueTool(4);
		//pour les outils a usage permanent
		for(int j=0;j<3;j++) {
			int value=outils.setToolsToUsed(j);
			assertEquals(this.outils.getToolsUsed()[j],true);
			assertEquals(this.outils.getTools()[j], value);
			//de meme pour l'ia
			assertEquals(this.outils.getToolsIA().getToolsUsed()[j],true);
			assertEquals(this.outils.getToolsIA().getTools()[j], value);
		}
		//pour les outils a usage unique
		while(outils.tools.length>3) {
			int lenTools = outils.tools.length;
			int valueBefore = this.outils.getTools()[3];
			int value = outils.setToolsToUsed(3);
			assertEquals(this.outils.tools.length,lenTools-1);//1 outils a etait enlever car il son a usage unique
			assertEquals(valueBefore,value);//on recupere bien la valeur de l'outils utilise
			//de meme pour l'ia
			assertEquals(this.outils.getToolsIA().tools.length,lenTools-1);//1 outils a etait enlever car il son a usage unique
		}
	}
}