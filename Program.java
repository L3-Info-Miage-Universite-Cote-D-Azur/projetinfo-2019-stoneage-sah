import game.Game;

public class Program {
	/**
	 * Cette classe contient le main et lance la partie
	 */

    public static void main(String[] args) {
    	//System.setProperty( "file.encoding", "UTF-8" );
    	System.out.println("\n######## L'AGE DE PIERRE ########\n");
    	Game game= new Game();
    	game.gameLoop();
    }
}