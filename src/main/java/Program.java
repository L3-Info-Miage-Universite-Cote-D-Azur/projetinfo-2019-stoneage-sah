import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import game.Game;
import printer.Printer;

public class Program {
	/**
	 * Cette classe contient le main et lance la partie
	 */

	public static void main(String[] args) {
		try {
    			System.setOut(new PrintStream(System.out, true, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
    			e.printStackTrace();
		} 
		
		//si on veut que l'affichage soit active ou non
		//true : affichage / false : pas d'affichage
		Printer.Instance(true);
		
		Printer.getPrinter().println("\n######## L'AGE DE PIERRE ########\n");
		int defaultNumber = 4;//Nombre de joueurs par defaut.
		int numberPlayer;
		if(args.length==0) { numberPlayer=defaultNumber; }//Si le programme est appelle sans argument.
		else{
			try{
				numberPlayer = Integer.parseInt(args[0]);
				
			}catch(Exception e) {
				//Choix invalide lors de l'appel du programme. 
				Printer.getPrinter().println("Choix de joueur invalide : "+e+" il est donc automatiquement initialiser a 4");
				numberPlayer = defaultNumber;
			}
		}
		Game game;
		if(numberPlayer>1 && numberPlayer<=4) {
			game= new Game(numberPlayer);//Initialisation du jeu.
		}
		else { 
			Printer.getPrinter().println("Choix de joueur invalide il est donc automatiquement initialiser a "+defaultNumber);
			numberPlayer = defaultNumber;
			game= new Game(numberPlayer);//Initialisation du jeu.
		}
		game.gameLoop();//Lancement du jeu.
	}
}
