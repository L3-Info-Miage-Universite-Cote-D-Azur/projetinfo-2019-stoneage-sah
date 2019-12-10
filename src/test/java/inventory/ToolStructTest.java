package inventory;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ToolStructTest {
	
	ToolStruct toolStructTest;

	@Test
	public void resetToolsUsedTest() {
		this.toolStructTest = new ToolStruct();
		
		//on test l'instanciation
		assertEquals(this.toolStructTest.getTool(),0);
		assertEquals(this.toolStructTest.getTools()[0], 0);
		assertEquals(this.toolStructTest.getTools()[1], 0);
		assertEquals(this.toolStructTest.getTools()[2], 0);
		assertEquals(this.toolStructTest.getToolsUsed()[0],false);
		assertEquals(this.toolStructTest.getToolsUsed()[1],false);
		assertEquals(this.toolStructTest.getToolsUsed()[2],false);
		
		this.toolStructTest.toolsUsed[1] = true;
		this.toolStructTest.resetToolsUsed();
		for (int i=0; i <this.toolStructTest.toolsUsed.length; i++) {
			assertEquals(this.toolStructTest.toolsUsed[i],false);
		}
	}
	
	@Test
	public void incrementToolTest() {
		this.toolStructTest = new ToolStruct();
		//on test l'instanciation
		assertEquals(this.toolStructTest.getTool(),0);
		assertEquals(this.toolStructTest.getTools()[0], 0);
		assertEquals(this.toolStructTest.getTools()[1], 0);
		assertEquals(this.toolStructTest.getTools()[2], 0);
		assertEquals(this.toolStructTest.getToolsUsed()[0],false);
		assertEquals(this.toolStructTest.getToolsUsed()[1],false);
		assertEquals(this.toolStructTest.getToolsUsed()[2],false);
		
		this.toolStructTest.incrementTool();
		assertEquals(this.toolStructTest.getTool(),1);
		assertEquals(this.toolStructTest.getTools()[0],1);
		for (int i=1; i<3;i++) {
			if (this.toolStructTest.tool<12) {
				this.toolStructTest.incrementTool();
				assertEquals(this.toolStructTest.getTool(),1+i);
				assertEquals(this.toolStructTest.tools[(this.toolStructTest.tool)%3]+=1,i%3);
			}
			
		}
	}
	
	@Test
	public void addUniqueToolTest() {
		toolStructTest = new ToolStruct();
		//cas de base que les outils usable a chaque tour soit 3
		assertEquals(toolStructTest.tools.length,3);
		//on ajoute un outil a usage uniquede valeur 5
		toolStructTest.addUniqueTool(5);
		assertEquals(toolStructTest.tools.length,4);
		assertEquals(toolStructTest.tools[3],5);
		//ajout d'un outil de valeur 1
		toolStructTest.addUniqueTool(1);
		assertEquals(toolStructTest.tools.length,5); //1 outils a etait rajouté
		assertEquals(toolStructTest.tools[3],5); //ne change pas l'outil precedent
		assertEquals(toolStructTest.tools[4],1); //possede le nouvelle outils de valeur 1
	}
	
	@Test
	public void subUniqueToolTest() {
		toolStructTest = new ToolStruct();
		//supression index non valide
		toolStructTest.subUniqueTool(1);
		assertEquals(toolStructTest.tools.length,3); //rien ne change
		//supression index non valide
		toolStructTest.subUniqueTool(3);
		assertEquals(toolStructTest.tools.length,3); //rien ne change
		//on ajoute 2 outils
		toolStructTest.addUniqueTool(5);
		toolStructTest.addUniqueTool(4);
		assertEquals(toolStructTest.tools.length,5);
		assertEquals(toolStructTest.tools[3],5);
		assertEquals(toolStructTest.tools[4],4);
		
		//on supprime l'outils de valeur 5 (index 3)
		toolStructTest.subUniqueTool(3);
		assertEquals(toolStructTest.tools.length,4);
		assertEquals(toolStructTest.tools[3],4);//il reste loutil de valeur 4
		//on supprime le dernier outils
		toolStructTest.subUniqueTool(3);
		assertEquals(toolStructTest.tools.length,3);
		
	}
	
	@Test
	public void setToolsToUsedTest() {
		toolStructTest = new ToolStruct();
		for(int i=0;i<5;i++) this.toolStructTest.incrementTool();// on met 5 niveau dans les outil (soit 2,2,1)
		assertEquals(this.toolStructTest.getTools()[0], 2);
		assertEquals(this.toolStructTest.getTools()[1], 2);
		assertEquals(this.toolStructTest.getTools()[2], 1);
		//aucun outils est utiliser
		assertEquals(this.toolStructTest.getToolsUsed()[0],false);
		assertEquals(this.toolStructTest.getToolsUsed()[1],false);
		assertEquals(this.toolStructTest.getToolsUsed()[2],false);
		//on ajoute 2 outils a usage unique
		toolStructTest.addUniqueTool(5);
		toolStructTest.addUniqueTool(4);
		//pour les outils a usage permanent
		for(int j=0;j<3;j++) {
			int value=toolStructTest.setToolsToUsed(j);
			assertEquals(this.toolStructTest.getToolsUsed()[j],true);
			assertEquals(this.toolStructTest.getTools()[j], value);
		}
		//pour les outils a usage unique
		while(toolStructTest.tools.length>3) {
			int lenTools = toolStructTest.tools.length;
			int valueBefore = this.toolStructTest.getTools()[3];
			int value = toolStructTest.setToolsToUsed(3);
			assertEquals(this.toolStructTest.tools.length,lenTools-1);//1 outils a etait enlever car il son a usage unique
			assertEquals(valueBefore,value);//on recupere bien la valeur de l'outils utilise
		}
	}
	
	@Test
	public void emptyToolsToUse() {
		toolStructTest = new Tools();
		assertEquals(toolStructTest.emptyToolsToUse(),false);// de base il n'a rien a utiliser
		//on ajoute un outil il a donc des outils a utiliser
		toolStructTest.incrementTool();
		assertEquals(toolStructTest.emptyToolsToUse(),true);
		//il utilise l'outil il en a donc plus a utiliser
		toolStructTest.setToolsToUsed(0);
		assertEquals(toolStructTest.emptyToolsToUse(),false);
		//il gagne un outils temporaire il en a a utiliser
		toolStructTest.addUniqueTool(5);
		assertEquals(toolStructTest.emptyToolsToUse(),true);
		//une fois utiliser on a de nouveau plus d'outils a utiliser
		toolStructTest.setToolsToUsed(3);
		assertEquals(toolStructTest.emptyToolsToUse(),false);
	}
}