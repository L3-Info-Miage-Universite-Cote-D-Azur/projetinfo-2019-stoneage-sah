package game;
import game.Settings;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import client.RandomIA;

public class PlayerTest {
    private Player player;
    @Test
    public void testAddFigurineInZone() {
        this.player = new Player(Settings.getRandomName(), new RandomIA());
        
        // Zone Inexistante
        this.player.addFigurineInZone(Settings.NB_ZONES,1);
        assertEquals(Settings.START_FIGURINE,this.player.getFigurine());
        this.player.addFigurineInZone(-2,1);
        assertEquals(Settings.START_FIGURINE,this.player.getFigurine());
        
        this.player = new Player(Settings.getRandomName(), new RandomIA());
        // Figurine hors de portee
        this.player.addFigurineInZone(0,Settings.START_FIGURINE + 1);
        assertEquals(Settings.START_FIGURINE,this.player.getFigurine());
        this.player = new Player(Settings.getRandomName(), new RandomIA());
        this.player.addFigurineInZone(0,-2);
        assertEquals(Settings.START_FIGURINE,this.player.getFigurine());
        
        // Expected in program
        this.player.addFigurineInZone(0,1);
        System.out.print(this.player.getFigurine());
        assertEquals(Settings.START_FIGURINE - 1,this.player.getFigurine());
    }
    
    @Test
    public void testSubFigurineInZone() {
        this.player = new Player(Settings.getRandomName(), new RandomIA());
        // Rien a recuperer
        this.player.subFigurineInZone(0);
        assertEquals(Settings.START_FIGURINE,this.player.getFigurine());
        
        
        this.player.addFigurineInZone(0,1);
        // Zone Inexistante
        this.player.subFigurineInZone(Settings.NB_ZONES);
        assertEquals(Settings.START_FIGURINE - 1,this.player.getFigurine());
        
        this.player.subFigurineInZone(-5);
        assertEquals(Settings.START_FIGURINE - 1,this.player.getFigurine());
        
        // Expected in program
        this.player.subFigurineInZone(0);
        assertEquals(Settings.START_FIGURINE,this.player.getFigurine());
        
    }

}
