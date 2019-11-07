package client;

/**
 * Interface de l'IA comportant les fonctions utiles.
 * @author Mentra20
 *
 */

public interface IA {
	
	/**
	 * choozeZone choisie une zone selon la strategie de l'IA. 
	 * @return int :zone la zone choisie.
	 */
    public int chooseZone ();
    
    /**
	 * choozeNumber(int) choisi le nombre de figurines a mettre dans la zone selon la strategie de l'IA. 
	 * @return int : le nombre de figurine choisies. 
	 */
    public int chooseNumber (int nbFigurineAvailable);

    @Override
    public String toString();
}