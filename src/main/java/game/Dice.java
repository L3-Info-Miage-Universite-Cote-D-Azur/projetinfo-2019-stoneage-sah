package game;

import java.util.Random;

/**
 * La classe Dice represente le de non truque du plateau.  
 */

public class Dice
{

    /**
     * rollDice lance number fois le de et stocke les resultats dans un tableau.
     * @param r: instance de Random
     * @param number: le nombre de fois qu'il faut lancer le de. 
     * @return le tableau d'entier contenant les resultats du lancer de de number fois. 
     */ 
    public int[] rollDice(Random r, int number)
    {
        int[] tab = new int[number];

        for(int i = 0; i < number; i++) 
        {
            tab[i] = r.nextInt(6)+1;
            if (tab[i] < 1 || tab[i] > 6)
                throw new RuntimeException("Dice returns an incompatible value");
        }   
        return tab;
    }   
}
