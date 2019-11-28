package game;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;



class GamePlayersTest {
    Dice d;
    GamePlayers g = new GamePlayers(2);
    int selectedPlayer=0;
    
    //Le test va verifier si les figurines sont nourris
    
    @Test
    void testPlayerFeedPhase() {
        assertEquals(true,g.getInventory(selectedPlayer).getRessource(Ressource.FOOD)==15);//nourriture disponible avant de nourrir pour joueur 0
        assertEquals(true,g.getInventory(1).getRessource(Ressource.FOOD)==15);//nourriture disponible avant de nourrir pour joueur 1
        g.playerFeedPhase(selectedPlayer);
        assertEquals(true,g.getInventory(selectedPlayer).getRessource(Ressource.FOOD)==15-g.getPlayer(selectedPlayer).getCurrentFigurine());//verifie qu'il a bien enlever le nombre de nourriture correspondant aux figurines nourris
        assertEquals(true,g.getInventory(1).getRessource(Ressource.FOOD)>g.getInventory(selectedPlayer).getRessource(Ressource.FOOD));//verifie que la nourriture de joueur 1 n'a pas etait retire, montre l'independance de la fonction
    }

}
