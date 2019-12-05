package statistics;

import java.io.*; 
import java.util.*; 
import game.Settings;
import game.Ressource;
import inventory.InventoryStruct;
import player.PlayerStruct;

public class Statistics
{
	/* FIELDS */

	/*
	 *	Fichiers dans cet ordre: Score, Wood, Clay, Stone, Gold, Food, Field
	 */
	private File[] average;
	private File[] JITCurves;
	private File victoryRate;
	private CSVUtility csv;

	/*
	 *  playersArrays est un ArrayList
	 *	playersArrays.get(0) represente un tableau d'ArrayList pour les stats du Joueur 0
	 *	playersArrays.get(0)[0] represente le premier ArrayList, a savoir celui pour le score du Joueur 0
	 *	playersArrays.get(0)[0].get(0) represente le score du Joueur 0 au tour 0, represente' par un ArrayList car le nombre de tour est UB (Undefined Behavior)
	 */
	private ArrayList<ArrayList<Integer>[]> playersArrays;
	private ArrayList<Integer>[] arrays;
	private int[] victoryRateArray;

	/* CONSTRUCTOR */
	@SuppressWarnings("unchecked")
	public Statistics (int nbPlayers)
	{
		// CREATING THE DIRECTORIES
		new File("./stats").mkdir();
		new File("./stats/averages").mkdir();
		new File("./stats/just-in-time-curves").mkdir();

		// INSTANCIATE THE FIELDS
		this.average = new File[Settings.FILE_NAMES.length];
		this.JITCurves = new File[Settings.FILE_NAMES.length];
		this.csv = new CSVUtility();

		for (int i = 0; i < Settings.FILE_NAMES.length; i++)
		{
			this.average[i] = new File("./stats/averages/" + Settings.FILE_NAMES[i]);
			this.JITCurves[i] = new File("./stats/just-in-time-curves/" + Settings.FILE_NAMES[i]);
		}

		this.playersArrays = new ArrayList<ArrayList<Integer>[]>();
		for (int k = 0; k < nbPlayers; k++)
		{
			// NEW REFERENCE EACH ITERATION
			this.arrays = new ArrayList[Settings.FILE_NAMES.length];
			
			for (int i = 0; i < this.arrays.length; i++)
			{
				this.arrays[i] = new ArrayList<Integer>();
			}
			this.playersArrays.add(this.arrays);
		}
		
		this.victoryRate = new File("./stats/victoryRate.csv");
		this.victoryRateArray = new int[nbPlayers];
	}

	/* METHODS */

	// On veut un fichier qui retourne pour chaque thematique, la caracteristique des 4 joueurs en fonction du nombre de tours
	// Donc quelque chose dans ce genre-la:
	// Tours, ScoreMoyen, RandomIA, NoobIA \n
	// 0, 10, 5, 15 \n
	// 1, 20, 10, 30 \n
	// etc
	/**
	 * Creer tous les fichiers dans ./just-in-time-curves/ qui representent la moyenne des ressources au cours des tours sur Settings.NB_GAMES 
	 * @param players le nom des IA de chaque joueur
	 * @throws IOException IO Exception
	 */
	public void createJITCurves (String[] players) throws IOException
	{
		// DONNER LES NOMS DE DEPART
		this.csv.addSomething(this.JITCurves[0], "Tours");
		this.csv.addElementToRow(this.JITCurves[0], "Score");
		for (int i = 0; i < players.length; i++)
			this.csv.addElementToRow(this.JITCurves[0], players[i]);
		this.csv.endRow(this.JITCurves[0]);

		for (int i = 1; i < this.JITCurves.length; i++)
		{
			this.csv.addElementToRow(this.JITCurves[i], "Tours");
			for (int k = 0; k < players.length; k++)
				this.csv.addElementToRow(this.JITCurves[i], players[k]);
			this.csv.addElementToRow(this.JITCurves[i], Ressource.indexToRessource(i-1).toString()+"Moyen");
			this.csv.endRow(this.JITCurves[i]);
		}

		// FIND THE MAX ROUNDS
		int max = 0;
		for (int i = 0; i < this.playersArrays.size(); i++)
			for (int k = 0; k < this.playersArrays.get(i).length; k++)
				if (max < this.playersArrays.get(i)[k].size())
					max = this.playersArrays.get(i)[k].size();

		// POUR LE NB DE TOUR MAX
		for (int nbRounds = 0; nbRounds < max; nbRounds++)
		{
			// POUR CHAQUE DATA
			for (int statDataIndex = 0; statDataIndex < this.playersArrays.get(0).length; statDataIndex++)
			{
				File tmpFile = this.JITCurves[statDataIndex];
				int averageStatData = 0;
				// AJOUT DU TOUR A CHAQUE DEBUT DE LIGNE
				this.csv.addSomething(tmpFile, Integer.toString(nbRounds));
				// POUR CHAQUE JOUEUR
				for (int currPlayer = 0; currPlayer < this.playersArrays.size(); currPlayer++)
				{

					// SI LE JOUEUR POSSEDE LA RESSOURCE A CE TOUR T
					if (this.playersArrays.get(currPlayer)[statDataIndex].size() > nbRounds)
					{
						// SI OUI, AJOUTER LA RESSOURCE
						this.csv.addElementToRow(tmpFile, Double.toString((double)this.playersArrays.get(currPlayer)[statDataIndex].get(nbRounds)/Settings.NB_GAMES));
						averageStatData += this.playersArrays.get(currPlayer)[statDataIndex].get(nbRounds);
					}
					else
					{
						// SI NON, NE RIEN AJOUTER
						this.csv.addNothing(tmpFile);
					}

				}
				// AJOUT DU TOTAL SUR LES JOUEURS
				this.csv.addElementToRow(tmpFile, Double.toString((double)averageStatData/(Settings.NB_GAMES * players.length)));
				this.csv.endRow(tmpFile);
			}
		}
	}
	
	/**
	 * Creer tous les fichiers dans ./just-in-time-curves/ qui representent la moyenne des ressources a la fin de la partie
	 * @param players le nom des IA de chaque joueur
	 * @throws IOException IO Exception
	 */
	public void createAverages (String[] players) throws IOException
	{
		// DONNER LES NOMS DE DEPART
		this.csv.addSomething(this.average[0], "Score");
		this.csv.addNothing(this.average[0]);
		for (int i = 0; i < players.length; i++)
		{
			this.csv.addSomething(this.average[0], players[i]);
			this.csv.addNothing(this.average[0]);
		}
		this.csv.endRow(this.average[0]);

		for (int i = 1; i < this.average.length; i++)
		{
			for (int k = 0; k < players.length; k++)
			{
				this.csv.addSomething(this.average[i], players[k]);
				this.csv.addNothing(this.average[i]);
			}
			this.csv.addSomething(this.average[i], Ressource.indexToRessource(i-1).toString()+"Moyen");
			this.csv.addNothing(this.average[i]);
			this.csv.endRow(this.average[i]);
		}
	
		// FIND THE MAX ROUNDS
		int max = 0;
		for (int i = 0; i < this.playersArrays.size(); i++)
			for (int k = 0; k < this.playersArrays.get(i).length; k++)
				if (max < this.playersArrays.get(i)[k].size())
					max = this.playersArrays.get(i)[k].size();

		// POUR CHAQUE DATA
		for (int statDataIndex = 0; statDataIndex < this.playersArrays.get(0).length; statDataIndex++)
		{
			File tmpFile = this.average[statDataIndex];
			int averageStatData = 0;
			// SI LE JOUEUR POSSEDE LA RESSOURCE A CE TOUR T
			if (this.playersArrays.get(0)[statDataIndex].size() > max - 1)
			{
				// SI OUI, AJOUTER LA RESSOURCE
				this.csv.addSomething(tmpFile, this.playersArrays.get(0)[statDataIndex].get(max - 1).toString());
				averageStatData += this.playersArrays.get(0)[statDataIndex].get(max - 1);
			}
			else
			{
				// SI NON, NE RIEN AJOUTER
				this.csv.addNothing(tmpFile);
			}
			// POUR CHAQUE JOUEUR
			for (int currPlayer = 1; currPlayer < this.playersArrays.size(); currPlayer++)
			{
				// SI LE JOUEUR POSSEDE LA RESSOURCE A CE TOUR T
				if (this.playersArrays.get(currPlayer)[statDataIndex].size() > max - 1)
				{
					// SI OUI, AJOUTER LA RESSOURCE
					this.csv.addElementToRow(tmpFile, Double.toString((double)this.playersArrays.get(currPlayer)[statDataIndex].get(max - 1)/Settings.NB_GAMES));
					averageStatData += this.playersArrays.get(currPlayer)[statDataIndex].get(max - 1);
				}
				else
				{
					// SI NON, NE RIEN AJOUTER
					this.csv.addNothing(tmpFile);
				}
			}
			// AJOUT DU TOTAL SUR LES JOUEURS
			this.csv.addElementToRow(tmpFile, Double.toString((double)averageStatData/(Settings.NB_GAMES * players.length)));
			this.csv.endRow(tmpFile);
		}
	}
	
	/**
	 * Creer un fichier victoryRate.csv qui montre le taux de victoire par IA
	 * @param players le nom des IA de chaque joueur
	 * @throws IOException IO Exception
	 */
	public void createVictoryRate (String[] players) throws IOException
	{
		this.csv.addSomething(this.victoryRate, players[0]);
		this.csv.addNothing(this.victoryRate);
		for (int i = 1; i < players.length; i++)
		{
			this.csv.addSomething(this.victoryRate, players[i]);
			this.csv.addNothing(this.victoryRate);
		}
		this.csv.endRow(this.victoryRate);
		
		this.csv.addSomething(this.victoryRate, Double.toString((double)((this.victoryRateArray[0] * 100)/Settings.NB_GAMES)) + "%");
		this.csv.addNothing(this.victoryRate);
		for (int i = 1; i < this.victoryRateArray.length; i++)
		{
			this.csv.addElementToRow(this.victoryRate, Double.toString((double)((this.victoryRateArray[i] * 100)/Settings.NB_GAMES)) + "%");
			this.csv.addNothing(this.victoryRate);
		}
		this.csv.endRow(this.victoryRate);
	}
	
	/**
	 * Ajoute une victoire au joueur dans le systeme de statistique
	 * @param index l'indice du joueur gagnant
	 */
	public void updateVictoryRate (int index)
	{
		this.victoryRateArray[index]++;
	}
	
	/**
	 * Permet de mettre a jour les statistiques en fonction du tour
	 * @param invs l'inventaire des joueurs
	 * @param players les joueurs
	 * @param nbRounds le nombre de tours
	 */
	public void updateStats (InventoryStruct[] invs, PlayerStruct[] players, int nbRounds)
	{
		// SCORE FIRST
		for (int i = 0; i < players.length; i++)
		{
			this.updateTheStat(i, 0, players[i].getScore(), nbRounds);
		}

		// RESSOURCES
		// POUR CHAQUE JOUEUR
		for (int i = 0; i < invs.length; i++)
		{
			// PRENDRE LA COPIE DE SES RESSOURCES
			int[] ressrc = invs[i].getCopyRessources();
			for (int k = 1; k < ressrc.length + 1; k++)
			{
				// AJOUTER LA RESSOURCE EN FIN D'ARRAYLIST
				this.updateTheStat(i, k, ressrc[k-1], nbRounds);
			}
		}
	}

	/**
	 * Permet soit d'ajouter la valeur si le tour n'existe pas, soit accumule les donnees
	 * @param currPlayer l'indice du joueur
	 * @param currData l'indice de la donnee
	 * @param value la valeur
	 * @param rounds le tour courant
	 */
	public void updateTheStat (int currPlayer, int currData, int value, int rounds)
	{
		//System.out.println(this.playersArrays.get(currPlayer)[currData].toArray().length + " " + Integer.toString(rounds));
		if (this.playersArrays.get(currPlayer)[currData].size() > rounds - 1 /*rounds commence a 1*/)
		{
			int res = this.playersArrays.get(currPlayer)[currData].get(rounds - 1) + value;
			this.playersArrays.get(currPlayer)[currData].set(rounds - 1, res);
			//System.out.println(this.playersArrays.get(currPlayer)[currData].get(rounds - 1).toString() + " " + Integer.toString(value));
		}
		else
		{
			this.playersArrays.get(currPlayer)[currData].add(value);
		}
	}

	/* GETTERS */
	public CSVUtility getCSV () {return this.csv;}
}
