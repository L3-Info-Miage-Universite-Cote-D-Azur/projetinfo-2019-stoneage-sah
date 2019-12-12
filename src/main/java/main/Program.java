package main;

import java.io.IOException;

import game.Game;
import printer.Printer;

/**
 * Classe qui contient le main
 * @author Yohann
 *
 */
public class Program {


	/**
	 * classe main
	 * @param args 0 : nombre de joueur entre 2 et 4.
	 * @throws IOException IO exception
	 */
    public static void main(String[] args) throws IOException{
        
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
                if(numberPlayer<2 || numberPlayer>4) numberPlayer=defaultNumber;
                
            }catch(Exception e) {
                //Choix invalide lors de l'appel du programme. 
                Printer.getPrinter().println("Choix de joueur invalide : "+e+" il est donc automatiquement initialiser a 4");
                numberPlayer = defaultNumber;
                
            }
        }
        Game game = new Game(numberPlayer);
    	game.gameLoop();//Lancement du jeu.
    }
}
