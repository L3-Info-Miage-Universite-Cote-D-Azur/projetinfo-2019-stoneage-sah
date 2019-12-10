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
	public boolean[] getToolsUsed() {return toolsUsed;}

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

	/**
	 * addUniqueTool ajoute l'outils unique de valeur val a Tools.
	 * @param val : la valeur de l'outils unique.
	 */
	public void addUniqueTool(int val) {
		int[] res = new int[tools.length + 1];
		int i = 0;
		while(i < tools.length) {
			res[i] = tools[i];
			i++;
		}
		res[i] = val;
		this.tools = res;
	}

	/**
	 * subUniqueTool supprime l'outils unique de valeur val;
	 * @param indexToErase : index de l'outil a supprimer
	 */
	public void subUniqueTool(int indexToErase) {
		if(tools.length > 3 ) {
			int[] res = new int[tools.length - 1];

			for(int j =0; j<3; j++) {// On copie les outils ordinaires.
				res[j] = tools[j];
			}

			int i = 3;
			int k = 3;
			while(k < tools.length) {
				if(i != indexToErase) {
					res[i] = tools[k];
					i++;
				}
				k++;
			}
			this.tools = res;
		}
	}
	
	/**
	 * Met a true les outils utilises.
	 * @param index l'index de l'outils selectionner par l'ia.
	 * @return renvoie la valeur de l'outils utiliser.
	 */
	protected int setToolsToUsed(int index) {
		int value = 0;
		if(index<3 && !toolsUsed[index])
		{
			toolsUsed[index] = true; //L'outils est maintenant utilise.
			value = tools[index];
		}
		else if (tools.length>index){
			value =tools[index];
			subUniqueTool(index);
		}
		return value;
	}
	
	/**
	 * Verification si le joueur a des outils a utiliser
	 * @return true il en a; false il en a pas.
	 */
	public boolean emptyToolsToUse() {
		for(int i=0; i< tools.length; i++){ 
			if((i>=3 ||!toolsUsed[i]) && tools[i]>0) return true; 
		}
		return false;
	}
}