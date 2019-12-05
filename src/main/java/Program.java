import java.io.IOException;

import client.NoobIA;
import client.RandomIA;
import game.Game;
import game.Settings;
import player.Player;
import printer.Printer;
import statistics.Statistics;

public class Program {
    /**
     * Cette classe contient le main et lance la partie
     * @throws IOException 
     */

    public static void main(String[] args) throws IOException {
        
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
        
        Statistics stats = new Statistics(numberPlayer);
        
        //a changer en fonction du nombre de joueur et faire de maniere random
        int[] iaPlayers = new int[]{0,0,0,0};
        String[] ias = new String[iaPlayers.length];
        for(int i = 0; i < iaPlayers.length; i++)
        {
        	 switch(iaPlayers[i]) {
	             case 0: 
	            	 ias[i] = "Random";
	                 break;
	             case 1:
	            	 ias[i] = "Debutant";
	                 break;
	             default:
	            	 ias[i] = null;
        	 }
        }
        
        for (int i = 0; i < Settings.NB_GAMES; i++)
        {
            Game game = new Game(iaPlayers,stats);
            game.gameLoop();//Lancement du jeu.
            Settings.resetArrays();
        }
        stats.createJITCurves(ias);
        stats.createAverages(ias);
    }
}
