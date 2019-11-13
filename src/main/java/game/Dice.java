package game;


import java.util.Random;

/**
 * La classe Dice represente le dé non truque du plateau.  
 */
 
class Dice{
    
    /**
     * rollDice lance number fois le de et stocke les resultats dans un tableau.
     * @param number: le nombre de fois qu'il faut lancer le de. 
     * @return le tableau d'entier contenant les resultats du lancer de de number fois. 
     */ 
    public static int[] rollDice(int number){
        int[] tab = new int[number];
        Random r = new Random();
        
        for(int i=0; i<number; i++) {
        	tab[i] = r.nextInt(6)+1;
        }
        return tab;
    }
}