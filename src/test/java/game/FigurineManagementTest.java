package game;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import client.RandomIA;

public class FigurineManagementTest {
	private ZoneRessource zoneR;
	private ZoneRessource zoneR2;
	private Player player;

	
	@Test
	public void TestPlaceFigurine(){
		//placeFigurine(Zone zone, Player player,int number,int indexZone)
		this.zoneR = new ZoneRessource("Foret",Ressource.WOOD,Settings.MAX_ZONERESSOURCE_SPACE);	
		this.zoneR2 = new ZoneRessource("Glaisiere",Ressource.CLAY,Settings.MAX_ZONERESSOURCE_SPACE);
		this.player = new Player(Settings.getRandomName(), new RandomIA());
		int forestIndex=0;
		int glaiIndex=1;
		
		//cas de base
		assertEquals(true,zoneR.howManyPlayerFigurine(player)==0);
		assertEquals(true,player.getCurrentFigurine()==5);
		
		//placement dans la zone
		FigurineManagement.placeFigurine(zoneR, player, 1, forestIndex);
		assertEquals(true,zoneR.howManyPlayerFigurine(player)==1);
		assertEquals(true,player.getCurrentFigurine()==4);
		
		//ajout dans une deusieme zone:
		FigurineManagement.placeFigurine(zoneR2, player, 2, glaiIndex);
		assertEquals(true,zoneR2.howManyPlayerFigurine(player)==2);
		assertEquals(true,player.getCurrentFigurine()==2);
		
		//ne modifie pas les valeur du coup d'avant
		assertEquals(true,zoneR.howManyPlayerFigurine(player)==1);
	}
	
	@Test
	public void testAbleToChooseZone(){
		this.player = new Player(Settings.getRandomName(), new RandomIA());
		this.zoneR = new ZoneRessource("Foret",Ressource.WOOD,Settings.MAX_ZONERESSOURCE_SPACE);	
		this.zoneR2 = new ZoneRessource("Chasse", Ressource.FOOD,Settings.MAX_PLAYER * Settings.MAX_FIGURINE);
		int forestIndex=0;
		int chasseIndex=4;
		//cas de base
		assertEquals(true,FigurineManagement.ableToChooseZone(zoneR,player, forestIndex));
		
		//ajout d'une figurine, on peut plus placer dans la meme zone
		FigurineManagement.placeFigurine(zoneR, player, 1, forestIndex);
		assertEquals(false,FigurineManagement.ableToChooseZone(zoneR,player, forestIndex));
		
		//mais possible pour la chasse
		FigurineManagement.placeFigurine(zoneR2, player, 1, chasseIndex);
		assertEquals(true,FigurineManagement.ableToChooseZone(zoneR2,player, chasseIndex));
		
		//ne peut plus posé si plus de figurine
		FigurineManagement.placeFigurine(zoneR2, player, 3, chasseIndex);
		assertEquals(true,player.getCurrentFigurine()==0);
		assertEquals(false,FigurineManagement.ableToChooseZone(zoneR2,player, chasseIndex));
		
	}
	
	
}
