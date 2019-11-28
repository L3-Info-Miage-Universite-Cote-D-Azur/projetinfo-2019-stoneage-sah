package inventory;

/*
 * La classe ToolsIA represente les outils de l'IA (doit etre egal a ceux du joueur.
 */
public class ToolsIA extends ToolStruct {
	
	/* CONSTRUCTOR */
	protected ToolsIA() {
		super();
	}
	
	/**
	 * Met a true les outils utilises.
	 * @param choose les outils choisis par l'IA
	 */
	protected void useTools(boolean[] choose) {
		for(int i = 0; i<tools.length; i++)
		{
			if(choose[i]==true)
			{
				toolsUsed[i] = true; //L'outils est maintenant utilise.
			}
		}
	}
}
