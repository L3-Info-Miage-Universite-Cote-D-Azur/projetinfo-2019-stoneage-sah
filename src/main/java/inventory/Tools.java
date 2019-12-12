package inventory;
import client.IA;
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
	 * addUniqueTool ajoute l'outils unique de valeur val a Tools.
	 * @param val : la valeur de l'outils unique.
	 */
	public void addUniqueTool(int val) {
		super.addUniqueTool(val);
		toolsIA.addUniqueTool(val);
		
	}
	
	
	/**
	 * Met a jour les outils utiliser du joueur et aussi pour son ia
	 * @param index l'index de l'outils selectionner par l'ia.
	 * @return renvoie la valeur de l'outils utiliser.
	 */
	@Override
	public int setToolsToUsed(int index) {
		toolsIA.setToolsToUsed(index);
		return super.setToolsToUsed(index);
	}
	
	
	/**
	 * Change tous les outils selectionner par le joueur en uses et renvoie la somme de ces outils.
	 * @param choose la liste des choix du joueur (une liste valide).
	 * @param player le nom du joueur que a utiliser les outils.
	 * @return la somme des outils utiliser.
	 */
	public int setSelectToolsToUsed(boolean[] choose,String player) {
		int sum = 0;
		for(int i = tools.length-1; i>=0; i--)
		{
			if(choose[i]==true)
			{
				if(i < 3) {
					Printer.getPrinter().println(useToolsToString(player,tools[i]));
				}else {
					Printer.getPrinter().println("Le joueur "+player+" a utiliser un outils unique de niveau "+tools[i]+".");
				}
				sum += setToolsToUsed(i);
			}
		}
		
		return sum;
	}
	
	
	/**
	 * Verifie si le choix de la liste des choix est correcte
	 * @param choose liste des choix a verifier
	 * @return true : Le choix est correct; False le choix est incorrect
	 */
	public boolean playerChooseToolsVerif(boolean[] choose) {
		if(choose.length != tools.length) return false;
		//on verifie si tout les choix son valide
		for(int i = 0; i<tools.length; i++){
			if(choose[i] && (i<3 && toolsUsed[i])) return false;//Probleme dans la reponse de l'IA.
		}
		return true;
	}
	
	/**
	 * Demande au joueur les outils qu'il veux choisir
	 * @param ia IA qui dois faire le choix
	 * @return liste des choix (valide)
	 */
	public boolean[] playerChooseTools(IA ia) {
		boolean[] choose;
		do {
			choose = ia.pickTools();
		}while(!playerChooseToolsVerif(choose));
		return choose;
	}
	
	
	/**
	 * 	 * useTools renvoie le tableau correspondant aux outils utilise et choisis par l'IA
	 * @param player : le joueur concerne. 
	 * @param sumDice : la somme des valeurs des outils utilises.
	 * @param ressource : ressource pour la quel vas etre utiliser l'outil.
	 * @return valeur des outils utiliser.
	 */
	public int	useTools(Player player,int sumDice, Ressource ressource){
		if(!emptyToolsToUse()) return 0;
		boolean[] choose = playerChooseTools(player.getIA());
		return setSelectToolsToUsed(choose,player.getName());
	}

}
