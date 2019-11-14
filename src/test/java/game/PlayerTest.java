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
        this.player = new Player(Settings.getRandomName(), new RandomIA());
        // INIT TEST
        assertEquals(this.player.getMaxFigurine(), Settings.START_FIGURINE);

        // TEST
        assertEquals(this.player.increaseMaxFigurine(), true);
        assertEquals(this.player.getCurrentFigurine(), Settings.START_FIGURINE + 1);

        // FILL IT
        for (int i = 0; i < Settings.MAX_FIGURINE - this.player.getMaxFigurine(); i++)
        {
            assertEquals(this.player.increaseMaxFigurine, true);
        }

        // ERROR TEST
        assertEquals(this.player.increaseMaxFigurine(), false);
        // IS IT FILLED
        assertEquals(this.player.getMaxFigurine(), Settings.MAX_FIGURINE);
        assertEquals(this.player.getCurrentFigurine(), Settings.MAX_FIGURINE);
    }
    
    @Test
    public void testAbleToPlaceFigurine () 
    {
        this.player = new Player(Settings.getRandomName(), new RandomIA());
        
        assertEquals(this.player.ableToPlaceFigurine(0), true);

        this.player.setHadPlace(0, true);

        assertEquals(this.player.ableToPlaceFigurine(0), false);
        assertEquals(this.player.ableToPlaceFigurine(1), true);
        assertEquals(this.player.ableToPlaceFigurine(Settings.NB_ZONES - 1), true);

        for (int i = 0; i < Settings.NB_ZONES; i++)
        {
            this.player.setHadPlace(i, true);
        }

        for (int i = 0; i < Settings.NB_ZONES; i++)
        {
            assertEquals(this.player.ableToPlaceFigurine(i), false);
        }

        this.player.resetHadPlaced();

        for (int i = 0; i < Settings.NB_ZONES; i++)
        {
            assertEquals(this.player.ableToPlaceFigurine(i), true);
        }
    }

}
