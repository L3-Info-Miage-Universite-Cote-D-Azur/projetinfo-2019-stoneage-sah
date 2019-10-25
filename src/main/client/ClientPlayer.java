package Client;

import java.util.Random;

import Game.Settings;

public class ClientPlayer {
	
	/* STATIC METHODS */
    public static void message (String msg) {
        System.out.println(msg);
    }

    public static int chooseZone () {
        return 0; // FOREST ZONE
    }
    
    public static int chooseNumber (int nbFigurineAvailable) {
    	if (nbFigurineAvailable <= 0 || nbFigurineAvailable > Settings.MAX_FIGURINE)
    		return -1;
    	Random r = new Random();
    	return r.nextInt(nbFigurineAvailable) + 1;
    }
}