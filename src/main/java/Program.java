import java.io.IOException;

import game.Game;
import game.Settings;
import printer.Printer;
import statistics.Statistics;

public class Program {
    /**
     * Cette classe contient le main et lance la partie
     */

	/**
	 * classe main
	 * @param args 0 : nombre de joueur entre 2 et 4, 1: Statistique permet de lancer n fois la parti pour faire des statistique.
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
                
            }catch(Exception e) {
                //Choix invalide lors de l'appel du programme. 
                Printer.getPrinter().println("Choix de joueur invalide : "+e+" il est donc automatiquement initialiser a 4");
                numberPlayer = defaultNumber;
            }
        }
        //plusieur parti avec creation de fichier de statistique
        if(args.length==2 && args[1]=="Statistique") {
        	Printer.getPrinter().println("Statistique en cours");
        	Printer.getPrinter().setPrinterCanWrite(false);//pas besoins d'affichage pour faire n partie
        	Statistics stats = new Statistics(numberPlayer);
        
        	//a changer en fonction du nombre de joueur et faire de maniere random
        	int[] iaPlayers = new int[numberPlayer];
        	String[] ias = new String[numberPlayer];
        	for(int i = 0; i < numberPlayer; i++)
        	{
        		iaPlayers[i]=i; //on a 4 ia pour le moment il dest donc interesant de fixé les IA 
        		switch(iaPlayers[i]) {
	            	case 0: 
	            		ias[i] = "Random";
	            		break;
	            	case 1:
	            		ias[i] = "Debutant";
	            		break;
	            	case 2:
	            		ias[i] = "CarteCivilisation version Bois";
	            		break;
	            	case 3:
	            		ias[i] = "Civilisation";
	            		break;
	            	default:
	            		ias[i] = null;
        		}
        	}
        	
        	Printer.getPrinter().setPrinterCanWrite(true);
        	Printer.getPrinter().println("Partie en cours de simulation");
        	Printer.getPrinter().setPrinterCanWrite(false);
        	for (int i = 0; i < Settings.NB_GAMES; i++)
        	{
        		Game game = new Game(iaPlayers,stats);
        		game.gameLoop();//Lancement du jeu.
        		Settings.resetArrays();
        	}
        	Printer.getPrinter().setPrinterCanWrite(true);
        	Printer.getPrinter().println("Creation des fichier de statistique");
        	Printer.getPrinter().setPrinterCanWrite(false);
        	stats.createJITCurves(ias);
        	stats.createAverages(ias);
		stats.createVictoryRate(ias);
        	Printer.getPrinter().setPrinterCanWrite(true);
        	Printer.getPrinter().println("Fin du programme");
        }
        //parti normale
        else {
        	Game game = new Game(numberPlayer);
    		game.gameLoop();//Lancement du jeu.
        }
    }
}
