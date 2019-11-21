import game.Game;

public class Program {
	/**
	 * Cette classe contient le main et lance la partie
	 */

    public static void main(String[] args) {
    	//System.setProperty( "file.encoding", "UTF-8" );
    	System.out.println("\n######## L'AGE DE PIERRE ########\n");
    	int defaultNumber = 4;
    	int numberPlayer;
    	if(args.length==0) { numberPlayer=defaultNumber; }
    	else{
    		try{
    			numberPlayer = Integer.parseInt(args[0]);
    		}catch(Exception e) {
    			System.out.println("Choix de joueur invalide : "+e+" il est donc automatiquement initialiser a 4");
    			numberPlayer = defaultNumber;
    		}
    	}
    	Game game;
    	if(numberPlayer>1 && numberPlayer<=4) {
    		game= new Game(numberPlayer);
    	}
    	else { 
    		System.out.println("Choix de joueur invalide il est donc automatiquement initialiser a 4");
    		game= new Game(numberPlayer);
			numberPlayer = defaultNumber;
    	}
    	game.gameLoop();
    }
}