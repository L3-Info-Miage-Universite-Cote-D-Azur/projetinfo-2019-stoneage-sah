package Game;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class PlayerTest {
    private Player player;
    @Test
    public void testAddFigurineInZone() {
        this.player = new Player();
        
        //zone inexistante
        this.player.addFigurineInZone(2,1);
        assertEquals(1,this.player.getFigurine());
        this.player.addFigurineInZone(-2,1);
        assertEquals(1,this.player.getFigurine());
        
        //nb de figurine trop elever/negatif pour le joueur (possede 1)
        this.player.addFigurineInZone(0,5);
        assertEquals(1,this.player.getFigurine());
        this.player.addFigurineInZone(0,-2);
        assertEquals(1,this.player.getFigurine());
        
        //fonctionnement normal
        this.player.addFigurineInZone(0,1);
        System.out.print(this.player.getFigurine());
        assertEquals(0,this.player.getFigurine());
    }
    
    @Test
    public void testSubFigurineInZone() {
        this.player = new Player();
        //rien a recuperer
        this.player.subFigurineInZone(0);
        assertEquals(1,this.player.getFigurine());
        
        
        this.player.addFigurineInZone(0,1);
        //zone inexistante
        this.player.subFigurineInZone(9);
        assertEquals(0,this.player.getFigurine());
        
        this.player.subFigurineInZone(-5);
        assertEquals(0,this.player.getFigurine());
        
        //fonctionnement correct:
        this.player.subFigurineInZone(0);
        assertEquals(1,this.player.getFigurine());
        
    }

}
