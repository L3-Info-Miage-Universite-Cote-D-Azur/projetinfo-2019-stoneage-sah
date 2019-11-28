package inventory;

import game.Ressource;
import player.Player;
import printer.Printer;

/**
 * La classe Tools represente les outils du joueur. 
 * @author groupe SAH
 */

public class Tools extends ToolStruct{
	/* FIELDS */
	private ToolsIA toolsIA;

	/* CONSTRUCTOR */
	public Tools(){
		super();
		toolsIA = new ToolsIA();
	}

	/* GETTERS */
	public ToolsIA getToolsIA() { return toolsIA; }


	/**
	 * resetToolsUsed remet a false le tableau ToolsUsed.
	 */
	@Override
	public void resetToolsUsed() {
		super.resetToolsUsed();
		toolsIA.resetToolsUsed();
	}

	/**
	 * incrementTool augmente le nombre d'outils du joueur. 
	 */ 
	@Override
	public void incrementTool() {
		super.incrementTool();
		toolsIA.incrementTool();
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
				Printer.getPrinter().println(useToolsToString(player.getName(),tools[i]));
			}
		}

		if(sum > 0){
			toolsIA.useTools(choose);
		}
		return sum;
	}

}