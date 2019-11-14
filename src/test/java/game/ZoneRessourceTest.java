package game;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import client.RandomIA;

public class ZoneRessourceTest {
	private ZoneRessource zoneR;
	private Player player;
	private Player player2;

	@Test
	public void testPlaceFigurine(){
		this.player = new Player(Settings.getRandomName(), new RandomIA());
		this.zoneR = new ZoneRessource("Foret",Ressource.WOOD,3,Settings.MAX_ZONERESSOURCE_SPACE);
		//cas de base
		assertEquals(true,zoneR.getAvailableSpace()==Settings.MAX_ZONERESSOURCE_SPACE);
		
		//on ajoute 3 figurine et on verifie que la place diminue de 3
		zoneR.placeFigurine(3, player);
		assertEquals(true,zoneR.getAvailableSpace()==Settings.MAX_ZONERESSOURCE_SPACE-3);
		
		//on ajoute 2 figurine et on verifie que la place diminue de 2
		zoneR.placeFigurine(2, player);
		assertEquals(true,zoneR.getAvailableSpace()==Settings.MAX_ZONERESSOURCE_SPACE-3-2);
	}
	
	@Test
	public void testAbleToChooseZone(){
		this.zoneR = new ZoneRessource("Foret",Ressource.WOOD,3,Settings.MAX_ZONERESSOURCE_SPACE);
		//cas de base
		assertEquals(true,zoneR.ableToChooseZone(1)); //parametre inutiliser dans cette fonction
		
		this.player = new Player(Settings.getRandomName(), new RandomIA());
		//on remplie la zone et normalement on peut plus placer dedans
		zoneR.placeFigurine(Settings.MAX_ZONERESSOURCE_SPACE, player);
		assertEquals(false,zoneR.ableToChooseZone(1));
	}
	
	public void testRemovePlayerFigurine(){
		this.player = new Player(Settings.getRandomName(), new RandomIA());
		this.zoneR = new ZoneRessource("Foret",Ressource.WOOD,3,Settings.MAX_ZONERESSOURCE_SPACE);	
		
		//remove sans que le joueur ai de figurine dans la zone rien ne ce passe
		zoneR.removePlayerFigurine(player);
		assertEquals(true,zoneR.getAvailableSpace()==Settings.MAX_ZONERESSOURCE_SPACE);
		
		//on ajoute 3 figurine
		zoneR.placeFigurine(3, player);
		//on remove les figurine du joueur
		zoneR.removePlayerFigurine(player);
		//on verifie que l'on a de nouveau le max
		assertEquals(true,zoneR.getAvailableSpace()==Settings.MAX_ZONERESSOURCE_SPACE);
		
		//parreil avec 2 joueur remove les figurine d'un joueur dois remove que celle du joueur en question
		this.player2 = new Player(Settings.getRandomName(), new RandomIA());
		zoneR.placeFigurine(3, player);
		zoneR.placeFigurine(2, player2);
		
		//on remove player:
		zoneR.removePlayerFigurine(player);
		//on verifie que l'on a bien les 2 joueur de player 2 a l'interieur
		assertEquals(true,zoneR.getAvailableSpace()==Settings.MAX_ZONERESSOURCE_SPACE-2);
		
		//on remove player2:
		zoneR.removePlayerFigurine(player2);
		assertEquals(true,zoneR.getAvailableSpace()==Settings.MAX_ZONERESSOURCE_SPACE);
	}
	
	public void testHowManyPlayerFigurine() {
		this.player = new Player(Settings.getRandomName(), new RandomIA());
		this.zoneR = new ZoneRessource("Foret",Ressource.WOOD,3,Settings.MAX_ZONERESSOURCE_SPACE);	
		this.player2 = new Player(Settings.getRandomName(), new RandomIA());
		
		//cas de base
		assertEquals(true,zoneR.howManyPlayerFigurine(player)==0);
		
		zoneR.placeFigurine(3, player);
		zoneR.placeFigurine(2, player2);
		//après ajout
		assertEquals(true,zoneR.howManyPlayerFigurine(player)==3);
		assertEquals(true,zoneR.howManyPlayerFigurine(player2)==2);
		
		//après remove player2:
		zoneR.removePlayerFigurine(player2);
		assertEquals(true,zoneR.howManyPlayerFigurine(player2)==0);
		assertEquals(true,zoneR.howManyPlayerFigurine(player)==3);
	}
	
	public void testPlayerRecoveryFigurine() {
		this.player = new Player(Settings.getRandomName(), new RandomIA());
		this.zoneR = new ZoneRessource("Foret",Ressource.WOOD,3,Settings.MAX_ZONERESSOURCE_SPACE);	
		zoneR.placeFigurine(3, player);
		assertEquals(true,player.getInventory().getRessource(Ressource.WOOD)==0);
		//avec 3 joueur le joueur est obliger de recolter un minimum de 1 de bois car 
		//les 3 des a 1 = 3*1==3 et 3/3=1;
		zoneR.playerRecoveryFigurine(player);
		assertEquals(true,player.getInventory().getRessource(Ressource.WOOD)>0);
	}
	@Test
	public void testGetMinimalFigurineRequierement(){
		this.zoneR = new ZoneRessource("Foret",Ressource.WOOD,3,Settings.MAX_ZONERESSOURCE_SPACE);
		assertEquals(1, this.zoneR.getMinimalFigurineRequierement());
	}
	
	
}
