package game;
import game.Settings;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import client.RandomIA;

public class PlayerTest
{
    private Player player;

    @Test
    public void testIncreaseMaxFigurine() 
    {
        this.player = new Player("test",null);
        // Test de l'instanciation
        assertEquals(this.player.getMaxFigurine(), Settings.START_FIGURINE);

        // Resultat et application classiques attendus
        assertEquals(this.player.increaseMaxFigurine(), true);
        assertEquals(this.player.getCurrentFigurine(), Settings.START_FIGURINE + 1);
        assertEquals(this.player.getMaxFigurine(), Settings.START_FIGURINE + 1);

        // Mettre maxFigurine a Settings.MAX_FIGURINE manuellement avec un pas de 1
        int maxFigurineTmp = this.player.getMaxFigurine();
        for (int i = 0; i < Settings.MAX_FIGURINE - maxFigurineTmp; i++)
        {
            assertEquals(this.player.increaseMaxFigurine(), true);
        }
        
        // Essayer de mettre 1 figurine d'overflow
        assertEquals(this.player.increaseMaxFigurine(), false);
        // Regarder si quelque chose a change
        assertEquals(this.player.getMaxFigurine(), Settings.MAX_FIGURINE);
        assertEquals(this.player.getCurrentFigurine(), Settings.MAX_FIGURINE);
    }
    
    @Test
    public void testAbleToPlaceFigurine () 
    {
        this.player = new Player("test",null);
        // Test de l'instanciation
        assertEquals(this.player.ableToPlaceFigurine(0), true);

        // Dit qu'on a place sur la foret
        this.player.setHadPlaced(0, true);

        // Si on peut placer sur la foret
        assertEquals(this.player.ableToPlaceFigurine(0), false);
        // Si on peut placer autre part
        assertEquals(this.player.ableToPlaceFigurine(1), true);
        // Si on peut placer sur la derniere zone
        assertEquals(this.player.ableToPlaceFigurine(Settings.NB_ZONES - 1), true);

        // On a place de partout
        for (int i = 0; i < Settings.NB_ZONES; i++)
        {
            this.player.setHadPlaced(i, true);
        }

        // Check si on peut bien placer nul part
        for (int i = 0; i < Settings.NB_ZONES; i++)
        {
            assertEquals(this.player.ableToPlaceFigurine(i), false);
        }

        // On reset ce tableau des placements
        this.player.resetHadPlaced();

        // Check si on peut placer partout
        for (int i = 0; i < Settings.NB_ZONES; i++)
        {
            assertEquals(this.player.ableToPlaceFigurine(i), true);
        }
    }

}
