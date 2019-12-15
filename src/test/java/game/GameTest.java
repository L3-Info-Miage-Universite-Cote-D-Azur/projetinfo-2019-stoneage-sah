package game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.FieldSetter;
import org.mockito.internal.verification.Times;

import client.IA;
import inventory.Inventory;
import player.Player;
public class GameTest {
	
	Game game;
	
	@Mock
	GamePlayers gpMock;
	GameZones gzMock;
	Game gameSpy;
	
	
	@Test
	public void GameLoopTest(){
		//deroulement de une game loop peut importe le nombre de joueur
		for(int i = 2;i<=4;i++) {
			Settings.resetArrays();
			game = new Game(i);
			Settings.resetArrays();
			gpMock = Mockito.spy(new GamePlayers(i));
			gzMock = Mockito.spy(new GameZones(i,new Dice()));
			CarteCivilisationManager cvMock = Mockito.mock(CarteCivilisationManager.class);
			when(gzMock.getCardManager()).thenReturn(cvMock);
			when(cvMock.isEmpty()).thenReturn(false);
			try {
				FieldSetter.setField(game, game.getClass().getDeclaredField("gamePlayers"),gpMock );
				FieldSetter.setField(game, game.getClass().getDeclaredField("gameZones"),gzMock );
			} catch (NoSuchFieldException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SecurityException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			Game gameSpy = Mockito.spy(game);
			when(gameSpy.isEnd()).thenReturn(false,true); // on fait un seul game loop
			assertEquals(gameSpy.getNbTour(),1);//on fait le tour 1
			
			try {
				gameSpy.gameLoop();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			assertEquals(gameSpy.getNbTour(),2);//on finit au tour 2
			//on a bien toutes les fonction qui sont appeler
			Mockito.verify(gameSpy,new Times(1)).afficheInfo();
			Mockito.verify(gameSpy,new Times(1)).afficheInfoEndGame();
			Mockito.verify(gameSpy,new Times(1)).placePhase();
			Mockito.verify(gameSpy,new Times(1)).harvestPhase();
			Mockito.verify(gameSpy,new Times(1)).useCardRessource();
			Mockito.verify(gameSpy,new Times(1)).feedPhase();
			Mockito.verify(gzMock,new Times(1)).resetNumberSpecialZoneOccuped();
			Mockito.verify(gzMock,new Times(1)).organizeCard();
			Mockito.verify(gpMock,new Times(1)).resetTools();
		}
		
		//si il a plus de carte civilisation la parti s'arrte sans la feedPhase
		for(int i = 2;i<=4;i++) {
			Settings.resetArrays();
			game = new Game(i);
			Settings.resetArrays();
			gpMock = Mockito.spy(new GamePlayers(i));
			gzMock = Mockito.spy(new GameZones(i,new Dice()));
			CarteCivilisationManager cvMock = Mockito.mock(CarteCivilisationManager.class);
			when(gzMock.getCardManager()).thenReturn(cvMock);
			when(cvMock.isEmpty()).thenReturn(true);//la parti vas s'arrte immediatement pas de feedPhase
			try {
				FieldSetter.setField(game, game.getClass().getDeclaredField("gamePlayers"),gpMock );
				FieldSetter.setField(game, game.getClass().getDeclaredField("gameZones"),gzMock );
			} catch (NoSuchFieldException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SecurityException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			Game gameSpy = Mockito.spy(game);
			when(gameSpy.isEnd()).thenReturn(false,true); // on fait un seul game loop
			assertEquals(gameSpy.getNbTour(),1);//on fait le tour 1
			
			try {
				gameSpy.gameLoop();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//on a bien la feed phase qui ne s'effectue pas
			Mockito.verify(gameSpy,never()).feedPhase();
		}
	}
	
	@Test
	public void placePhaseTest() {
		
		Player player1 = Mockito.mock(Player.class);
		Player player2 = Mockito.mock(Player.class);
		Settings.resetArrays();
		game = new Game(2);
		Settings.resetArrays();
		gpMock = Mockito.spy(new GamePlayers(2));
		gzMock = Mockito.mock(GameZones.class);
		//on assigne nos joueur mock
		when(gpMock.getPlayer(0)).thenReturn(player1);
		when(gpMock.getPlayer(1)).thenReturn(player2);
		when(gzMock.playerPlaceFigurine(player1)).thenReturn(false,false,true);
		when(gzMock.playerPlaceFigurine(player2)).thenReturn(true);
		try {
			FieldSetter.setField(game, game.getClass().getDeclaredField("gamePlayers"),gpMock );
			FieldSetter.setField(game, game.getClass().getDeclaredField("gameZones"),gzMock );
		} catch (NoSuchFieldException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SecurityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Game gameSpy = Mockito.spy(game);
		gameSpy.placePhase();
		//soit la fonction vas etre appeler 3 fois pour le joueur 1 et 1 pour le joueur 2
		Mockito.verify(gzMock,new Times(3)).playerPlaceFigurine(player1);
		Mockito.verify(gzMock,new Times(1)).playerPlaceFigurine(player2);
	}
	
	@Test
	public void harvestPhaseTest(){
		//on verifie que tout les joueur peuve recuperer leur figurine dans les zone
		for(int i = 2;i<=4;i++) {
			Settings.resetArrays();
			game = new Game(i);
			gzMock = Mockito.spy(new GameZones(i,new Dice()));
			try {
				FieldSetter.setField(game, game.getClass().getDeclaredField("gameZones"),gzMock );
			} catch (NoSuchFieldException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SecurityException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			Game gameSpy = Mockito.spy(game);
			gameSpy.harvestPhase();
			//on a bien tout les joueur qui recolte peut importe le bombre de joueur
			Mockito.verify(gzMock,new Times(i)).playerHarvest(any(Player.class),any(Inventory.class), any(GamePlayers.class));

		}
	}
	
	@Test
	public void feedPhaseTest(){
		//on verifie que tout les joueur peuve recuperer leur figurine dans les zone
		for(int i = 2;i<=4;i++) {
			Settings.resetArrays();
			game = new Game(i);
			Settings.resetArrays();
			gpMock = Mockito.spy(new GamePlayers(i));
			try {
				FieldSetter.setField(game, game.getClass().getDeclaredField("gamePlayers"),gpMock );
			} catch (NoSuchFieldException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SecurityException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			Game gameSpy = Mockito.spy(game);
			gameSpy.feedPhase();
			//on a bien tout les joueur qui recolte peut importe le bombre de joueur
			Mockito.verify(gpMock,new Times(i)).playerFeedPhase(anyInt());

		}
	}
	
	@Test
	public void useCardRessourceTest() {
		Player player1 = Mockito.mock(Player.class);
		IA ia1 = Mockito.mock(IA.class);
		Inventory inv1 = Mockito.mock(Inventory.class);
		when(player1.getIA()).thenReturn(ia1);//on afecte l'ia 1 au joueur
		when(ia1.useRessourceCard()).thenReturn(0);//l'ia prend du bois
		when(inv1.getCardRessource()).thenReturn(1);//il possede une ressourceCard
		Settings.resetArrays();
		game = new Game(1);
		Settings.resetArrays();
		gpMock = Mockito.spy(new GamePlayers(1));
		gzMock = Mockito.mock(GameZones.class);
		//on assigne nos joueur mock
		when(gpMock.getPlayer(0)).thenReturn(player1);
		when(gpMock.getInventory(0)).thenReturn(inv1);
		try {
			FieldSetter.setField(game, game.getClass().getDeclaredField("gamePlayers"),gpMock );
			FieldSetter.setField(game, game.getClass().getDeclaredField("gameZones"),gzMock );
		} catch (NoSuchFieldException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SecurityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Game gameSpy = Mockito.spy(game);
		gameSpy.useCardRessource();
		//le joueur a bien utiliser ca carte ressource et ces ressource on augmenté
		Mockito.verify(inv1,new Times(1)).addRessource(any(Ressource.class),anyInt());
		Mockito.verify(inv1,new Times(1)).decrementCardRessource();
		
		
		//cas ou le joueur selectionne une ressource invalide:
		player1 = Mockito.mock(Player.class);
		ia1 = Mockito.mock(IA.class);
		inv1 = Mockito.mock(Inventory.class);
		when(player1.getIA()).thenReturn(ia1);//on afecte l'ia 1 au joueur
		when(ia1.useRessourceCard()).thenReturn(-5,-2,4,3);//il prend 3 ressource invalide puis de l'or
		when(inv1.getCardRessource()).thenReturn(1);//il possede une ressourceCard
		Settings.resetArrays();
		game = new Game(1);
		Settings.resetArrays();
		gpMock = Mockito.spy(new GamePlayers(1));
		gzMock = Mockito.mock(GameZones.class);
		//on assigne nos joueur mock
		when(gpMock.getPlayer(0)).thenReturn(player1);
		when(gpMock.getInventory(0)).thenReturn(inv1);
		try {
			FieldSetter.setField(game, game.getClass().getDeclaredField("gamePlayers"),gpMock );
			FieldSetter.setField(game, game.getClass().getDeclaredField("gameZones"),gzMock );
		} catch (NoSuchFieldException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SecurityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		gameSpy = Mockito.spy(game);
		gameSpy.useCardRessource();
		
		//le joueur recupere le gold les erreur d'index sont ignore
		Mockito.verify(inv1,new Times(1)).addRessource(any(Ressource.class),anyInt());
		Mockito.verify(inv1,new Times(1)).decrementCardRessource();
		
		
		//cas ou le joueur a pas de ressource card:
		player1 = Mockito.mock(Player.class);
		ia1 = Mockito.mock(IA.class);
		inv1 = Mockito.mock(Inventory.class);
		when(player1.getIA()).thenReturn(ia1);//on afecte l'ia 1 au joueur
		when(inv1.getCardRessource()).thenReturn(0);//il possede pas de ressourceCard
		Settings.resetArrays();
		game = new Game(1);
		Settings.resetArrays();
		gpMock = Mockito.spy(new GamePlayers(1));
		gzMock = Mockito.mock(GameZones.class);
		//on assigne nos joueur mock
		when(gpMock.getPlayer(0)).thenReturn(player1);
		when(gpMock.getInventory(0)).thenReturn(inv1);
		try {
			FieldSetter.setField(game, game.getClass().getDeclaredField("gamePlayers"),gpMock );
			FieldSetter.setField(game, game.getClass().getDeclaredField("gameZones"),gzMock );
		} catch (NoSuchFieldException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SecurityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		gameSpy = Mockito.spy(game);
		gameSpy.useCardRessource();
		//le joueur ne choisi pas et aucune incrementation/decrementation et fait
		Mockito.verify(ia1,never()).useRessourceCard();
		Mockito.verify(inv1,never()).addRessource(any(Ressource.class),anyInt());
		Mockito.verify(inv1,never()).decrementCardRessource();
	}
	
	
}
