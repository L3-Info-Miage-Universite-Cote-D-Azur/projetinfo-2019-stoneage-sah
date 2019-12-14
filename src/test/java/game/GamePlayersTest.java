package game;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import inventory.Inventory;
import player.Player;



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
    
    @Test
    void testRessourceChoose() {
    	Inventory inventory;
    	Player player;
 
    	int nbRessourceBefore;
    	int nbRessourceAfter;
    	int nbRessourceGive;
    	int nbForFeed;
    	
    	for(int i = 0; i < 100; i++) {
    		inventory = new Inventory();
    		player = new Player("test", inventory.getInventoryIA());//L'IA change a chaque nouveau test.
    		
    		inventory.addRessource(Ressource.STONE, 5);
        	inventory.addRessource(Ressource.GOLD, 4);
        	inventory.addRessource(Ressource.WOOD, 7);
        	inventory.addRessource(Ressource.CLAY, 2);
        	
        	nbRessourceBefore = inventory.availableResourceToFeed();// vaut 18.
        	nbForFeed = Settings.RAND.nextInt(18) + 1;//Le nombre de ressource que le joueur va devoir sacrifier.
        	
        	while(nbRessourceBefore > 0) {//Tant que le joueur n'as pas tout nourris.
        		nbRessourceGive = g.ressourceChoose(player, inventory, nbForFeed);
            	nbRessourceAfter = inventory.availableResourceToFeed();
            	//Test qu'on enleve bien ce qui est renvoye.
            	assertEquals(true, nbRessourceBefore == nbRessourceAfter + nbRessourceGive);
            	
            	nbRessourceBefore = nbRessourceAfter;
        	}
        	//Test qu'il a bien tout nourris
        	assertEquals(0, nbRessourceBefore);
    	}	
    }
}
