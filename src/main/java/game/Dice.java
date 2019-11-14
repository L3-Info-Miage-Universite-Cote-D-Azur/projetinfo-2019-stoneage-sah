package game;

/**
 * La classe Dice represente le de non truque du plateau.  
 */
 
class Dice{
    
    /**
     * rollDice lance number fois le de et stocke les resultats dans un tableau.
     * @param number: le nombre de fois qu'il faut lancer le de. 
     * @return le tableau d'entier contenant les resultats du lancer de de number fois. 
     */ 
      public static int[] rollDice(int number){
        int[] tab = new int[number];
        
        for(int i=0; i<number; i++) {
        	tab[i] = Settings.RAND.nextInt(6)+1;
        }
        return tab;
    }
}
