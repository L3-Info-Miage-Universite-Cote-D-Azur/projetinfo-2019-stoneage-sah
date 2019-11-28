package inventory;

/**
 * InventoryIA represente l'inventaire de l'IA (qui doit etre egal a celui du joueur sauf si triche)
 * @author Mentra20
 *
 */
public class InventoryIA extends InventoryStruct{
	/* FIELD */
	private ToolsIA toolsIA;
	
	/* CONSTRUCTOR */
	public InventoryIA(ToolsIA toolsIA) {
		super();
		this.toolsIA = toolsIA;
	}
	
	/* GETTERS */
	public ToolsIA getTools() { return toolsIA; }
}
