package client;

import java.util.Random;

import game.Settings;

/**
 * RandomIA correspond a l'IA comportant une strategie basee sur le hasard. 
 * @author Mentra20
 *
 */

public class RandomIA implements IA {

    public int chooseZone () {
    	// La selection se fait au hasard. 
        return new Random().nextInt(Settings.NB_ZONES); // RANDOM ZONE
    }
    
    public int chooseNumber (int nbFigurineAvailable) {
    	if (nbFigurineAvailable <= 0 || nbFigurineAvailable > Settings.MAX_FIGURINE)
    		return -1;
    	// La selection se fait au hasard. 
    	Random r = new Random();
    	return r.nextInt(nbFigurineAvailable) + 1;
    }

    public String toString(){
        return "Random";
    }
}
