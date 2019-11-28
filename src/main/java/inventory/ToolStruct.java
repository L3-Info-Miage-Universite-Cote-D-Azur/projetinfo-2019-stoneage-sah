package inventory;

/**
 * ToolStruct est une classe abstract qui represente les outils dans le jeu.
 * @author Mentra20
 *
 */
public abstract class ToolStruct {
	/* FIELDS */
	protected int[] tools;
	protected int tool;
	protected boolean[] toolsUsed;

	/* CONSTRUCTOR */
	public ToolStruct(){
		tools = new int[] {0,0,0};
		tool = 0;
		toolsUsed = new boolean[] {false,false,false};
	}

	/* GETTERS */
	public int getTool() {return tool;}
	public int[] getTools() {return tools;}

	/**
	 * resetToolsUsed remet a false le tableau ToolsUsed.
	 */
	protected void resetToolsUsed() {
		for (int i=0; i <toolsUsed.length; i++) {
			toolsUsed[i]=false;
		}
	}

	/**
	 * incrementTool augmente le nombre d'outils du joueur. 
	 */ 
	protected void incrementTool() {
		if (tool < 12) {
			this.tool += 1;
			this.tools[(tool-1) % 3] +=1;
		}		
	}


	/**
	 * incrementToolsToString creer un chaine de caractere correspondant a l'augmentation des outils.
	 * @param name le nom du joueur
	 * @return la chaine de caracteres pour l'incrementation des outils.
	 */
	public String incrementToolsToString(String name) {
		String str="";
		for(int i=0; i< tools.length; i++) {
			if(tools[i] != 0) 
			{
				str+="Le joueur "+name+" a maintenant un outils de niveau "+tools[i]+".\n";
			}
		}
		return str;
	}

	/**
	 * useToolsToString creer une chaine de caratere correspondnat a l'utilisation des outils.
	 * @param name le nom du joueur.
	 * @param tools le niveau de l'outils
	 * @return la chaine de caracteres pour l'utilisation des outils.
	 */
	public String useToolsToString(String name,int tools) {
		return "Le joueur "+name+" a utiliser un outils de niveau "+tools+".";
	}

}