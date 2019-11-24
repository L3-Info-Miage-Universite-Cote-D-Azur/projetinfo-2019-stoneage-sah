package game;

import java.util.Arrays;

/**
 * La classe Tools represente les outils dans le jeu. 
 * @author groupe SAH
 */

public class Tools{
	/* FIELDS */
	private int[] tools;
	private int tool;
	private boolean[] toolsUsed;

	/* CONSTRUCTOR */
	public Tools(){
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
	public void resetToolsUsed() {
		for (int i=0; i <toolsUsed.length; i++) {
			toolsUsed[i]=false;
		}
	}

	/**
	 * incrementTool augmente le nombre d'outils du joueur. 
	 */ 
	public void incrementTool() {
		if (tool < 12) {
			this.tool += 1;
			this.tools[(tool-1) % 3] +=1;
		}		
	}


	/**
	 * useTools renvoie le tableau correspondant aux outils utilise et choisis par l'IA
	 * @param player : le joueur concerne. 
	 */
	public int	useTools(Player player,int sumDice, Ressource ressource){
		boolean correctChoose = false;
		boolean[] choose = new boolean[]{false,false,false};

		int sum=0;
		for(int i=0; i< tools.length; i++){ if(!toolsUsed[i]) sum += tools[i]; }
		if(sum == 0) return 0;//cas ou l'utilisateur n'a pas d'outil a utiliser.


		while(!correctChoose){//Tant qu'il y a un probleme

			correctChoose = true;
			//On demande a l'IA les outils qu'elle veut utiliser.
			choose = player.getIA().pickTools(tools.clone(),toolsUsed.clone());

			if(choose.length == tools.length) 
			{
				for(int i = 0; i<tools.length; i++){
					if(choose[i] && toolsUsed[i]) correctChoose = false;//Probleme dans la reponse de l'IA.
				}
			}
		}
		//La reponse de l'IA est correcte.
		sum = 0;
		for(int i = 0; i<tools.length; i++)
		{
			if(choose[i]==true)
			{
				toolsUsed[i] = true; //L'outils est maintenant utilise.
				sum += tools[i];//La valeur bonus des outils
				System.out.println(useToolsToString(player.getName(),tools[i]));
			}
		}
		return sum;
	}
	
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
	
	public String useToolsToString(String name,int tools) {
		return "Le joueur "+name+" a utiliser un outils de niveau "+tools+".\n";
	}
}