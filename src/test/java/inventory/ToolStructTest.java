package inventory;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ToolStructTest {
	
	ToolStruct ToolStructTest;

	@Test
	public void testResetToolsUsed() {
		this.ToolStructTest = new Tools();
		
		//on test l'instanciation
		assertEquals(this.ToolStructTest.getTool(),0);
		assertEquals(this.ToolStructTest.getTools()[0], 0);
		assertEquals(this.ToolStructTest.getTools()[1], 0);
		assertEquals(this.ToolStructTest.getTools()[2], 0);
		assertEquals(this.ToolStructTest.getToolsUsed()[0],false);
		assertEquals(this.ToolStructTest.getToolsUsed()[1],false);
		assertEquals(this.ToolStructTest.getToolsUsed()[2],false);
		
		this.ToolStructTest.toolsUsed[1] = true;
		this.ToolStructTest.resetToolsUsed();
		for (int i=0; i <this.ToolStructTest.toolsUsed.length; i++) {
			assertEquals(this.ToolStructTest.toolsUsed[i],false);
		}
	}
	
	@Test
	public void TestIncrementTool() {
		this.ToolStructTest = new Tools();
		//on test l'instanciation
		assertEquals(this.ToolStructTest.getTool(),0);
		assertEquals(this.ToolStructTest.getTools()[0], 0);
		assertEquals(this.ToolStructTest.getTools()[1], 0);
		assertEquals(this.ToolStructTest.getTools()[2], 0);
		assertEquals(this.ToolStructTest.getToolsUsed()[0],false);
		assertEquals(this.ToolStructTest.getToolsUsed()[1],false);
		assertEquals(this.ToolStructTest.getToolsUsed()[2],false);
		
		this.ToolStructTest.incrementTool();
		assertEquals(this.ToolStructTest.getTool(),1);
		assertEquals(this.ToolStructTest.getTools()[0],1);
		for (int i=1; i<3;i++) {
			if (this.ToolStructTest.tool<12) {
				this.ToolStructTest.incrementTool();
				assertEquals(this.ToolStructTest.getTool(),1+i);
				assertEquals(this.ToolStructTest.tools[(this.ToolStructTest.tool)%3]+=1,i%3);
			}
			
		}
	}
	
}