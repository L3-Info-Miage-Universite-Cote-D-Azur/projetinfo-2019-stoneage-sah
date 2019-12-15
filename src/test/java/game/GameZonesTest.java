package game;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import org.junit.jupiter.api.Test;

import client.IA;
import client.RandomIA;
import game.building.Building;
import game.zone.Zone;
import game.zone.ZoneCarteCivilisation;
import inventory.Inventory;
import game.zone.ZoneBuilding;
import player.Player;

public class GameZonesTest{
	GameZones gameZones;
	Dice d = new Dice();


	@Test
	public void buildingCopyTest() {
		//peut importe le nombre de joueur(nombre valide entre 2 et 4)
		for(int i=2;i<=4;i++)
		{
			Settings.resetArrays();
			gameZones = new GameZones(i,null);
			//on a bien i building == nombre de joueur
			int nbBuildings = gameZones.buildingCopy().length;
			assertEquals(true , i == nbBuildings);
		}

		//les carte sont bien les meme que dans les zoneBuilding
		Settings.resetArrays();
		gameZones = new GameZones(4,null);
		Zone[] Zone = gameZones.getZones();
		Building[] buildings = gameZones.buildingCopy();
		assertEquals(((ZoneBuilding)Zone[12]).getBuilding() == buildings[0] , true);
		assertEquals(((ZoneBuilding)Zone[13]).getBuilding() == buildings[1] , true);
		assertEquals(((ZoneBuilding)Zone[14]).getBuilding() == buildings[2] , true);
		assertEquals(((ZoneBuilding)Zone[15]).getBuilding() == buildings[3] , true);
	}

	@Test
	public void carteCivilisationCopyTest() {

		//les carte sont bien les meme que dans les Zone carte civilisation
		gameZones = new GameZones(4,null);
		Zone[] Zone = gameZones.getZones();
		CarteCivilisation[] cartesCV = gameZones.carteCivilisationCopy();
		assertEquals(((ZoneCarteCivilisation) Zone[8]).getCard() == cartesCV[0] , true);
		assertEquals(((ZoneCarteCivilisation) Zone[9]).getCard() == cartesCV[1] , true);
		assertEquals(((ZoneCarteCivilisation) Zone[10]).getCard() == cartesCV[2] , true);
		assertEquals(((ZoneCarteCivilisation) Zone[11]).getCard() == cartesCV[3] , true);
	}

	@Test
	public void zoneChooseVerifTest() {
		Settings.resetArrays();
		int numberPlayer = 4;
		gameZones = new GameZones(numberPlayer,null);
		Player player = new Player(null,null);
		Player otherPlayer = new Player(null,null);
		//Premier joueur a choisi il peut poser ou il veux
		for(int i = 0; i<gameZones.getZones().length;i++) {
			assertEquals(gameZones.zoneChooseVerif(player, i),true);
		}
		//Si les index de son choix n'est pas un index valide (<0 ou >nombre de zone) la fonction renvoie faux
		for(int i = -5; i<0;i++) {
			assertEquals(gameZones.zoneChooseVerif(player, i),false);
		}
		for(int i = gameZones.getZones().length; i<20;i++) {
			assertEquals(gameZones.zoneChooseVerif(player, i),false);
		}

		//si le joueur n'a plus de figurine il ne peut  pas poser
		Player playerMock = mock(Player.class);
		when(playerMock.getCurrentFigurine()).thenReturn(0);
		for(int i = 0; i<gameZones.getZones().length;i++) {
			assertEquals(gameZones.zoneChooseVerif(playerMock, i),false);
		}
		//si le joueur n'a pas asser de figurine (si il en a plus que 1 a placer) pour joueur sur zone hut (indice 6)
		playerMock = mock(Player.class);
		when(playerMock.getCurrentFigurine()).thenReturn(1);
		assertEquals(gameZones.zoneChooseVerif(playerMock, 6),false); // il peut pas choisir cette zone

		Settings.resetArrays();
		gameZones = new GameZones(numberPlayer,null);
		//Si il a deja poser dans la zone il ne peut pas de nouveau poser (sauf pour la chasse)
		playerMock = mock(Player.class);
		IA iaMock = mock(RandomIA.class);
		when(playerMock.getIA()).thenReturn(iaMock);
		when(playerMock.getCurrentFigurine()).thenReturn(10);//il a des figurine
		when(iaMock.chooseNumber(anyInt(), anyInt())).thenReturn(1);//il place 1 figurine dans la zone (sauf si il en faut 2 minimum comme pour zone hut)

		for(int i = 0; i<gameZones.getZones().length;i++) {
			when(iaMock.chooseZone(any(int[].class), (Building[]) any(Object[].class), (CarteCivilisation[]) any(Object[].class))).thenReturn(i);
			gameZones.playerPlaceFigurine(playerMock);
			//index de la zone chasse
			if(i==4) assertEquals(gameZones.zoneChooseVerif(playerMock, i),true);
			else assertEquals(gameZones.zoneChooseVerif(playerMock, i),false);
			if (i<=4) assertEquals(gameZones.zoneChooseVerif(otherPlayer, i),true); //les autre joueur peuvent encore si c'est une zone many player
		}

		//test des regle pour 3 joueur
		numberPlayer=3;
		Settings.resetArrays();
		gameZones = new GameZones(numberPlayer,d);

		//on crée des mock qui vont simuler un joueur qui place sur 2 zone special different la 3eme ne serat donc plus accessible par n'importe quel joueur
		playerMock = mock(Player.class);
		iaMock = mock(RandomIA.class);
		when(playerMock.getIA()).thenReturn(iaMock);
		when(playerMock.getCurrentFigurine()).thenReturn(10);
		when(iaMock.chooseZone(any(int[].class), (Building[]) any(Object[].class), (CarteCivilisation[]) any(Object[].class))).thenReturn(5,6);
		// 2 placement dans les zone: champs et cabane de reproduciton (5,6)
		gameZones.playerPlaceFigurine(playerMock);
		gameZones.playerPlaceFigurine(playerMock);
		assertEquals(gameZones.zoneChooseVerif(playerMock, 7),false); //on peut plus prendre la 3eme zone speciale
		assertEquals(gameZones.zoneChooseVerif(otherPlayer, 7),false); //peut importe le joueur il n'ets plus possible de prendre cette zone


		//test des regle pour 2 joueur
		numberPlayer=2;
		Settings.resetArrays();
		gameZones = new GameZones(numberPlayer,d);

		//on crée des mock qui vont simuler un joueur qui place sur 2 zone special different la 3eme ne serat donc plus accessible par n'importe quel joueur
		playerMock = mock(Player.class);
		iaMock = mock(RandomIA.class);
		when(playerMock.getIA()).thenReturn(iaMock);
		when(playerMock.getCurrentFigurine()).thenReturn(10);
		when(iaMock.chooseZone(any(int[].class), (Building[]) any(Object[].class), (CarteCivilisation[]) any(Object[].class))).thenReturn(5,7);
		// 2 placement dans les zone: champs et cabane de reproduciton (5,7)
		gameZones.playerPlaceFigurine(playerMock);
		gameZones.playerPlaceFigurine(playerMock);
		assertEquals(gameZones.zoneChooseVerif(playerMock, 6),false); //on peut plus prendre la 3eme zone speciale
		assertEquals(gameZones.zoneChooseVerif(otherPlayer, 6),false); //peut importe le joueur il n'ets plus possible de prendre cette zone

	}



	@Test
	public void zoneAvailableSpaceForPlayerTest() {
		//test initial personne a placer, parti a 4 joueur
		int numberPlayer = 4;
		gameZones = new GameZones(numberPlayer,null);
		Player player = new Player(null,null);
		// on regarde si le nombre de place est bien egale au nombre de place initial
		assertEquals(Arrays.equals(gameZones.zoneAvailableSpaceForPlayer(player),new int[] {Settings.MAX_ZONERESSOURCE_SPACE , Settings.MAX_ZONERESSOURCE_SPACE , Settings.MAX_ZONERESSOURCE_SPACE , Settings.MAX_ZONERESSOURCE_SPACE ,
				numberPlayer * Settings.MAX_FIGURINE, 1,2,1,1,1,1,1,1,1,1,1}),true);

		//test initial personne a placer, parti a 3 joueur (uniquement 3 zone building )
		numberPlayer = 3;
		gameZones = new GameZones(numberPlayer,null);
		player = new Player(null,null);
		// on regarde si le nombre de place est bien egale au nombre de place initial
		assertEquals(Arrays.equals(gameZones.zoneAvailableSpaceForPlayer(player),new int[] {Settings.MAX_ZONERESSOURCE_SPACE , Settings.MAX_ZONERESSOURCE_SPACE , Settings.MAX_ZONERESSOURCE_SPACE , Settings.MAX_ZONERESSOURCE_SPACE ,
				numberPlayer * Settings.MAX_FIGURINE, 1,2,1,1,1,1,1,1,1,1}), true);

		//test initial personne a placer, parti a 2 joueur (uniquement 2 zone building )
		numberPlayer = 2;
		gameZones = new GameZones(numberPlayer,null);
		player = new Player(null,null);
		// on regarde si le nombre de place est bien egale au nombre de place initial
		assertEquals(Arrays.equals(gameZones.zoneAvailableSpaceForPlayer(player),new int[] {Settings.MAX_ZONERESSOURCE_SPACE , Settings.MAX_ZONERESSOURCE_SPACE , Settings.MAX_ZONERESSOURCE_SPACE , Settings.MAX_ZONERESSOURCE_SPACE ,
				numberPlayer * Settings.MAX_FIGURINE, 1,2,1,1,1,1,1,1,1}),true);

		//si un joueur place dans une zone ce meme joueur ne peut pas replacer la liste contient alors des 0 au endroit ou il peut pas placer.
		numberPlayer = 4;
		gameZones = new GameZones(numberPlayer,null);
		player = new Player(null,null);
		Zone[] zones = gameZones.getZones();
		zones[0].placeFigurine(1, player);
		assertEquals(Arrays.equals(gameZones.zoneAvailableSpaceForPlayer(player),new int[] {0 , Settings.MAX_ZONERESSOURCE_SPACE , Settings.MAX_ZONERESSOURCE_SPACE , Settings.MAX_ZONERESSOURCE_SPACE ,
				numberPlayer * Settings.MAX_FIGURINE, 1,2,1,1,1,1,1,1,1,1,1}),true);
		zones[5].placeFigurine(1, player);
		assertEquals(Arrays.equals(gameZones.zoneAvailableSpaceForPlayer(player),new int[] {0 , Settings.MAX_ZONERESSOURCE_SPACE , Settings.MAX_ZONERESSOURCE_SPACE , Settings.MAX_ZONERESSOURCE_SPACE ,
				numberPlayer * Settings.MAX_FIGURINE, 0,2,1,1,1,1,1,1,1,1,1}),true);
		//sauf pour la chasse:
		zones[4].placeFigurine(1, player);
		assertEquals(Arrays.equals(gameZones.zoneAvailableSpaceForPlayer(player),new int[] {0 , Settings.MAX_ZONERESSOURCE_SPACE , Settings.MAX_ZONERESSOURCE_SPACE , Settings.MAX_ZONERESSOURCE_SPACE ,
				numberPlayer * Settings.MAX_FIGURINE-1, 0,2,1,1,1,1,1,1,1,1,1}),true);


		//test des regle pour 3 joueur
		numberPlayer=3;
		Settings.resetArrays();
		gameZones = new GameZones(numberPlayer,d);

		//on crée des mock qui vont simuler un joueur qui place sur 2 zone special different la 3eme ne serat donc plus accessible par n'importe quel joueur
		Player playerMock = mock(Player.class);
		IA iaMock = mock(RandomIA.class);
		when(playerMock.getIA()).thenReturn(iaMock);
		when(playerMock.getCurrentFigurine()).thenReturn(10);
		when(iaMock.chooseZone(any(int[].class), (Building[]) any(Object[].class), (CarteCivilisation[]) any(Object[].class))).thenReturn(5,6);
		// 2 placement dans les zone: champs et cabane de reproduciton (5,6)
		gameZones.playerPlaceFigurine(playerMock);
		gameZones.playerPlaceFigurine(playerMock);
		//la 3eme zone n'est plus accesible par ce joueur d'ou [..., 0 , 0 , 0 , ...]
		assertEquals(Arrays.equals(gameZones.zoneAvailableSpaceForPlayer(player),new int[] {Settings.MAX_ZONERESSOURCE_SPACE , Settings.MAX_ZONERESSOURCE_SPACE , Settings.MAX_ZONERESSOURCE_SPACE , Settings.MAX_ZONERESSOURCE_SPACE ,
				numberPlayer * Settings.MAX_FIGURINE, 0,0,0,1,1,1,1,1,1,1}),true);
		//la 3eme zone n'est plus accesible par un autre joueur d'ou [..., 0 , 0 , 0 , ...]
		assertEquals(Arrays.equals(gameZones.zoneAvailableSpaceForPlayer(new Player(null,null)),new int[] {Settings.MAX_ZONERESSOURCE_SPACE , Settings.MAX_ZONERESSOURCE_SPACE , Settings.MAX_ZONERESSOURCE_SPACE , Settings.MAX_ZONERESSOURCE_SPACE ,
				numberPlayer * Settings.MAX_FIGURINE, 0,0,0,1,1,1,1,1,1,1}),true);



		//test des regle pour 2 joueur
		numberPlayer=2;
		Settings.resetArrays();
		gameZones = new GameZones(numberPlayer,d);

		//on crée des mock qui vont simuler un joueur qui place sur 2 zone special different la 3eme ne serat donc plus accessible par n'importe quel joueur
		playerMock = mock(Player.class);
		iaMock = mock(RandomIA.class);
		when(playerMock.getIA()).thenReturn(iaMock);
		when(playerMock.getCurrentFigurine()).thenReturn(10);
		when(iaMock.chooseZone(any(int[].class), (Building[]) any(Object[].class), (CarteCivilisation[]) any(Object[].class))).thenReturn(5,6);
		// 2 placement dans les zone: champs et cabane de reproduciton (5,6)
		gameZones.playerPlaceFigurine(playerMock);
		gameZones.playerPlaceFigurine(playerMock);
		//la 3eme zone n'est plus accesible par ce joueur d'ou [..., 0 , 0 , 0 , ...]
		assertEquals(Arrays.equals(gameZones.zoneAvailableSpaceForPlayer(player),new int[] {Settings.MAX_ZONERESSOURCE_SPACE , Settings.MAX_ZONERESSOURCE_SPACE , Settings.MAX_ZONERESSOURCE_SPACE , Settings.MAX_ZONERESSOURCE_SPACE ,
				numberPlayer * Settings.MAX_FIGURINE, 0,0,0,1,1,1,1,1,1}),true);
		//la 3eme zone n'est plus accesible par un autre joueur d'ou [..., 0 , 0 , 0 , ...]
		assertEquals(Arrays.equals(gameZones.zoneAvailableSpaceForPlayer(new Player(null,null)),new int[] {Settings.MAX_ZONERESSOURCE_SPACE , Settings.MAX_ZONERESSOURCE_SPACE , Settings.MAX_ZONERESSOURCE_SPACE , Settings.MAX_ZONERESSOURCE_SPACE ,
				numberPlayer * Settings.MAX_FIGURINE, 0,0,0,1,1,1,1,1,1}),true);

	}


	@Test
	public void testNumberChoose() {
		int numberPlayer = 4;
		Settings.resetArrays();
		gameZones = new GameZones(numberPlayer,d);
		Player player = new Player(null,null);

		//pour les zone ou le joueur ne peut pas choisir le nombre de figurine (les zone farm,hut,tools,building,carteCivi)
		assertEquals(gameZones.numberChoose(player,gameZones.getZones()[5]),1);
		assertEquals(gameZones.numberChoose(player,gameZones.getZones()[6]),2);//2 car cabanne de reproduction
		assertEquals(gameZones.numberChoose(player,gameZones.getZones()[8]),1);
		assertEquals(gameZones.numberChoose(player,gameZones.getZones()[12]),1);

		//pour les zone ressource
		Player playerMock = mock(Player.class);
		IA iaMock = mock(RandomIA.class);
		when(playerMock.getIA()).thenReturn(iaMock);
		when(playerMock.getCurrentFigurine()).thenReturn(3);//nombre de figurine
		when(iaMock.chooseNumber(anyInt(), anyInt())).thenReturn(-2,-1,0,1); 
		//le seul nombre qui vas etre accepter est 1 car si la zone est selectionner il dois poser au moin 1 figurine
		assertEquals(gameZones.numberChoose(playerMock,gameZones.getZones()[0]),1);
		//de meme si il na pas asser de figurine
		when(iaMock.chooseNumber(anyInt(), anyInt())).thenReturn(10,5,4,3); 
		//il possede 3 figurine chooseNumber vas refuser jusqua que l'ia choisis un nombre possible
		assertEquals(gameZones.numberChoose(playerMock,gameZones.getZones()[0]),3);

		//de meme si c'est la zone qui n'a pas asser de place (ou les 2)
		when(playerMock.getCurrentFigurine()).thenReturn(10);
		when(iaMock.chooseNumber(anyInt(), anyInt())).thenReturn(12,10,8,7); 
		assertEquals(gameZones.numberChoose(playerMock,gameZones.getZones()[0]),7); //7 = nb de place possible sur le bois

		//si un autre joueur a deja placer sur la zone le nombre de place max de la zone diminue car il a deja des figurine dans la zone
		Player otherPlayerMock = mock(Player.class);
		IA otherIaMock = mock(RandomIA.class);
		when(otherPlayerMock.getIA()).thenReturn(otherIaMock);
		when(otherPlayerMock.getCurrentFigurine()).thenReturn(10);//nombre de figurine
		when(otherIaMock.chooseZone(any(int[].class), (Building[]) any(Object[].class), (CarteCivilisation[]) any(Object[].class))).thenReturn(0);//il choisi la zone 0
		when(otherIaMock.chooseNumber(anyInt(), anyInt())).thenReturn(1); //il met 1 figurine
		gameZones.playerPlaceFigurine(otherPlayerMock);
		when(iaMock.chooseNumber(anyInt(), anyInt())).thenReturn(12,10,8,7,6); 
		assertEquals(gameZones.numberChoose(playerMock,gameZones.getZones()[0]),6); //6 car il y a une place en moins
	}

	@Test
	public void playerPlaceFigurineTest() {
		/*
		 * On a deja test toutes les fonction qui compose cette classe il nous reste juste a regarder ca valeur en retoure
		 * et si les figurine son bien placer dans la zone.
		 */
		int numberPlayer = 4;
		Settings.resetArrays();
		gameZones = new GameZones(numberPlayer,d);
		Player playerMock = mock(Player.class);
		IA iaMock = mock(RandomIA.class);
		when(playerMock.getIA()).thenReturn(iaMock);
		when(playerMock.getCurrentFigurine()).thenReturn(10);
		when(iaMock.chooseNumber(anyInt(), anyInt())).thenReturn(1); //il met 1 figurine (si il dois choisir)
		for(int i=0;i<gameZones.getZones().length;i++) {
			when(iaMock.chooseZone(any(int[].class), (Building[]) any(Object[].class), (CarteCivilisation[]) any(Object[].class))).thenReturn(i);//il choisi la zone i
			gameZones.playerPlaceFigurine(playerMock);
			assertEquals(gameZones.getZones()[i].howManyPlayerFigurine(playerMock)>0,true);// il a bien placer des figurine dans la zone i
		}



		Player player = new Player("test",null,0); //on prend une random ia
		boolean returnValue;
		do {
			returnValue=gameZones.playerPlaceFigurine(player);
			if(player.getCurrentFigurine()>0) assertEquals(returnValue,false); // si il a encore de figurine il renvoie false
			else assertEquals(returnValue,true); //sinon il renvoie true

		}while(!returnValue);
	}
	
	@Test
	public void testTirageInProgress() {
		gameZones = new GameZones(4,d);
		Player playerMock = mock(Player.class);
		IA iaMock = mock(RandomIA.class);
		//Tirage : bois/outils/or/argile
		int[] diceRes = new int[]{1,5,4,2};
		//Tout est dispo
		boolean[] alreadyChoose = new boolean[]{false,false,false,false};
		Inventory inventory = new Inventory();
		
		when(playerMock.getIA()).thenReturn(iaMock);
		
		//On test que le joueur n'a rien.
		assertEquals(0,inventory.getTools().getTools()[0]);
		assertEquals(0, inventory.getRessource(Ressource.WOOD));
		assertEquals(0, inventory.getRessource(Ressource.CLAY));
		assertEquals(0, inventory.getRessource(Ressource.GOLD));
		
		//On force le choix au premier du tirage
		when(iaMock.chooseTirage(any(int[].class), any(boolean[].class))).thenReturn(0);
		gameZones.tirageInProgress(playerMock, inventory, diceRes, alreadyChoose);
		//Tout est dispo sauf le premier
		assertEquals(alreadyChoose[0], true);
		assertEquals(alreadyChoose[1], false);
		assertEquals(alreadyChoose[2], false);
		assertEquals(alreadyChoose[3], false);
		//Il a un de bois
		assertEquals(1, inventory.getRessource(Ressource.WOOD));
		assertEquals(0,inventory.getTools().getTools()[0]);
		assertEquals(0, inventory.getRessource(Ressource.CLAY));
		assertEquals(0, inventory.getRessource(Ressource.GOLD));
		
		//On force le choix au deuxieme du tirage
		when(iaMock.chooseTirage(any(int[].class), any(boolean[].class))).thenReturn(1);
		gameZones.tirageInProgress(playerMock, inventory, diceRes, alreadyChoose);
		assertEquals(alreadyChoose[0], true);
		assertEquals(alreadyChoose[1], true);
		assertEquals(alreadyChoose[2], false);
		assertEquals(alreadyChoose[3], false);
		assertEquals(1, inventory.getRessource(Ressource.WOOD));
		//Il a un outils en plus
		assertEquals(1,inventory.getTools().getTools()[0]);
		assertEquals(0, inventory.getRessource(Ressource.CLAY));
		assertEquals(0, inventory.getRessource(Ressource.GOLD));
		
		//On force le choix au troisieme du tirage
		when(iaMock.chooseTirage(any(int[].class), any(boolean[].class))).thenReturn(2);
		gameZones.tirageInProgress(playerMock, inventory, diceRes, alreadyChoose);
		assertEquals(alreadyChoose[0], true);
		assertEquals(alreadyChoose[1], true);
		assertEquals(alreadyChoose[2], true);
		assertEquals(alreadyChoose[3], false);
		assertEquals(1, inventory.getRessource(Ressource.WOOD));
		assertEquals(1,inventory.getTools().getTools()[0]);
		assertEquals(0, inventory.getRessource(Ressource.CLAY));
		//Il a de l'or
		assertEquals(1, inventory.getRessource(Ressource.GOLD));
		
		//On force le choix au quatrieme du tirage
		when(iaMock.chooseTirage(any(int[].class), any(boolean[].class))).thenReturn(3);
		gameZones.tirageInProgress(playerMock, inventory, diceRes, alreadyChoose);
		//Plus rien est dispo
		assertEquals(alreadyChoose[0], true);
		assertEquals(alreadyChoose[1], true);
		assertEquals(alreadyChoose[2], true);
		assertEquals(alreadyChoose[3], true);
		assertEquals(1, inventory.getRessource(Ressource.WOOD));
		assertEquals(1,inventory.getTools().getTools()[0]);
		//Il a de l'argile
		assertEquals(1, inventory.getRessource(Ressource.CLAY));
		assertEquals(1, inventory.getRessource(Ressource.GOLD));
	}
	
	@Test
	public void testActionRecovery() {
		gameZones = new GameZones(4,d); 
		int action;
		GamePlayers gamePlayers = new GamePlayers(4);
		Inventory inventory = new Inventory();
		Player player = new Player("test", inventory.getInventoryIA());
		
		//TEST POUR LE CAS ACTION == 1
		action = 1;
		//Test que le joueur n'as pas de carte civivilisation et la taille du deck.
		assertEquals(32, gameZones.getCardManager().getDeck().size());
		assertEquals(0, inventory.getCardCivilisation().length);
		
		gameZones.actionRecovery(action, player, inventory, gamePlayers);
		//Le joueur a maintenant une carte civilisation et le deck une en moins.
		assertEquals(1, inventory.getCardCivilisation().length);
		assertEquals(true, inventory.getCardCivilisation()[0] instanceof CarteCivilisation);
		assertEquals(31, gameZones.getCardManager().getDeck().size());
		
		//TEST POUR LE CAS ACTION == 2
		action = 2;
		// rien ne dois changer (appel de tirage)
		gameZones.actionRecovery(action, player, inventory, gamePlayers);
		assertEquals(1, inventory.getCardCivilisation().length);
		assertEquals(31, gameZones.getCardManager().getDeck().size());
	}
}
