package main;
import java.io.IOException;

import game.Game;
import game.Settings;
import printer.Printer;
import statistics.Statistics;

/**
 * Cette classe permet de lancer une parti pour faire des statistique
 * @author Yohann
 *
 */
public class ProgramStatistique {

	/**
	 * classe main
	 * @param args 0 : nombre de joueur entre 2 et 4
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
		Printer.getPrinter().println("Statistique en cours");
		Printer.getPrinter().setPrinterCanWrite(false);//pas besoins d'affichage pour faire n partie
		Statistics stats = new Statistics(numberPlayer);

		//a changer en fonction du nombre de joueur et faire de maniere random
		int[] iaPlayers = new int[numberPlayer];
		String[] ias = new String[numberPlayer];
		String nameIa[] = {"Random","Debutant","CarteCivilisation version Bois","Civilisation"};
		for(int i = 0; i < numberPlayer; i++)
		{
			if(numberPlayer==4) iaPlayers[i]=i;//on a 4 ia pour le moment il est donc interesant de fixe les IA 
			else if(numberPlayer<4) iaPlayers[i]=Settings.RAND.nextInt(4);
			ias[i] = nameIa[iaPlayers[i]];
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
		stats.createAll(ias);
		Printer.getPrinter().setPrinterCanWrite(true);
		Printer.getPrinter().println("Fin du programme");
	}
}
