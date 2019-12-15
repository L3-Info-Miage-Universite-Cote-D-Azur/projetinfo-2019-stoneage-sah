package game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import game.Dice;
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
		assertEquals(true,ccm.getDeck().size() == 36);
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
	
	@Test
	public void organizeCard() {
		ZoneCarteCivilisation cv1 = new ZoneCarteCivilisation(null,1,null);
		ZoneCarteCivilisation cv2 = new ZoneCarteCivilisation(null,2,null);
		ZoneCarteCivilisation cv3 = new ZoneCarteCivilisation(null,3,null);
		ZoneCarteCivilisation cv4 = new ZoneCarteCivilisation(null,4,null);
		ZoneCarteCivilisation[] allcv = new ZoneCarteCivilisation[] {cv1,cv2,cv3,cv4};
		CarteCivilisationManager manager = new CarteCivilisationManager(allcv);
		CarteCivilisation[] oldCartes = new CarteCivilisation[] {cv1.getCard(),cv2.getCard(),cv3.getCard(),cv4.getCard()};

		//cas de base pas de carte recuperer les carte reste les meme
		manager.organizeCard();
		CarteCivilisation[] newCartes = new CarteCivilisation[] {cv1.getCard(),cv2.getCard(),cv3.getCard(),cv4.getCard()};
		assertEquals(Arrays.equals(oldCartes, newCartes),true);
		
		//on eneleve la carte de la zone cv1
		cv1.setCard(null);
		manager.organizeCard();
		//les carte 2 3 et 4 se sont decaler vers la zone 1 un nouvelle carte est apparue a la zone 4:
		newCartes = new CarteCivilisation[] {cv1.getCard(),cv2.getCard(),cv3.getCard(),cv4.getCard()};
		for(int i=0; i<3; i++) {
			assertEquals(newCartes[i],oldCartes[i+1]); // on a bien les carte 2 3 et 4 qui ce sont decaler
		}
		//comme le paquet n'est pas vide une nouvelle carte a etait ajouté en cv4:
		assertEquals(oldCartes[3]!=null,true);
		
		//si on enleve plusieur carte ne meme temps:
		oldCartes = new CarteCivilisation[] {cv1.getCard(),cv2.getCard(),cv3.getCard(),cv4.getCard()};
		cv2.setCard(null); // un joueur a pris la carte de la zone 2
		cv3.setCard(null); // un joueur a pris la carte de la zone 3
		manager.organizeCard();
		//la carte de la zone 1 ne dois pas avoir bouger tandis que celle de la zone 4 dois etre sur la zone 2.
		assertEquals(oldCartes[0],cv1.getCard());
		assertEquals(oldCartes[3],cv2.getCard());
		// 2 nouvelle carte dans les zone 3 et 4
		assertEquals(oldCartes[3]!=null,true);
		assertEquals(oldCartes[2]!=null,true);
		
		
	}
}
