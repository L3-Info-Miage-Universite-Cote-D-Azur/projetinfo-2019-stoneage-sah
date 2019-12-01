package game;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import game.zone.ZoneCarteCivilisation;

public class CarteCivilisationManagerTest {
	
	Dice dice = new Dice();
	
	@Test
	public void testInitDeck(){
		ZoneCarteCivilisation[] zcc = new ZoneCarteCivilisation[4];

		for(int i=0;i<4;i++) {
			zcc[i]=new ZoneCarteCivilisation("Carte Civilisation: "+ (i+1), i+1,dice);
		}
		
		CarteCivilisationManager ccm = new CarteCivilisationManager(zcc);
		
		ccm.initDeck();
		//Test de la longueur
		assertEquals(true,ccm.getDeck().size() == 35);
	}
	
	@Test
	public void testGetRandomCivilisationCard() {
		ZoneCarteCivilisation[] zcc = new ZoneCarteCivilisation[4];
		for(int i=0;i<4;i++) {
			zcc[i]=new ZoneCarteCivilisation("Carte Civilisation: "+ (i+1), i+1,dice);
		}
		CarteCivilisationManager ccm = new CarteCivilisationManager(zcc);
		
		ccm.initDeck();
		int taille = ccm.getDeck().size();
		ArrayList<CarteCivilisation> cloneDeck = new ArrayList<CarteCivilisation>(ccm.getDeck());
		
		for(int i = 0; i<taille; i++) {
			CarteCivilisation cv = ccm.getRandomCivilisationCard();
			assertEquals(false, ccm.getDeck().contains(cv));
			assertEquals(true, cloneDeck.contains(cv));
		}
		assertEquals(true, ccm.getDeck().size() == 0);
	}
}
