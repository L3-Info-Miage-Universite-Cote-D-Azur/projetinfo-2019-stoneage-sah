package statistics;

import java.io.*; 
import java.util.*; 
import com.opencsv.CSVWriter;

import game.Settings;
import game.Ressource;
import inventory.InventoryStruct;
import player.Player;
import player.PlayerStruct;
import printer.Printer;

public class Statistics
{
	/* FIELDS */

	/*
	 *	Fichiers dans cet ordre: Score, Wood, Clay, Stone, Gold, Food, Field
	 */
	private File[] average;
	private File[] JITCurves;
	private CSVUtility csv;

	/*
	 *  playersArrays est un ArrayList
	 *	playersArrays.get(0) represente un tableau d'ArrayList pour les stats du Joueur 0
	 *	playersArrays.get(0)[0] represente le premier ArrayList, a savoir celui pour le score du Joueur 0
	 *	playersArrays.get(0)[0].get(0) represente le score du Joueur 0 au tour 0, represente' par un ArrayList car le nombre de tour est UB (Undefined Behavior)
	 */
	private ArrayList<ArrayList<Integer>[]> playersArrays;
	private ArrayList<Integer>[] arrays;

	/* CONSTRUCTOR */
	public Statistics (int nbPlayers)
	{
		// CREATING THE DIRECTORIES
		new File("./averages").mkdir();
		new File("./just-in-time-curves").mkdir();

		// INSTANCIATE THE FIELDS
		this.average = new File[Settings.FILE_NAMES.length];
		this.JITCurves = new File[Settings.FILE_NAMES.length];
		this.csv = new CSVUtility();

		for (int i = 0; i < Settings.FILE_NAMES.length; i++)
		{
			this.average[i] = new File(Settings.FILE_NAMES[i]);
			this.JITCurves[i] = new File("just-in-time-curves/" + Settings.FILE_NAMES[i]);
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
	}

	/* METHODS */

	// On veut un fichier qui retourne pour chaque thematique, la caracteristique des 4 joueurs en fonction du nombre de tours
	// Donc quelque chose dans ce genre-la:
	// Tours, ScoreMoyen, RandomIA, NoobIA \n
	// 0, 10, 5, 15 \n
	// 1, 20, 10, 30 \n
	// etc
	public void createJITCurves (Player[] players) throws IOException
	{
		// DONNER LES NOMS DE DEPART
		this.csv.addElementToRow(this.JITCurves[0], "Tours");
		this.csv.addElementToRow(this.JITCurves[0], "Score");
		for (int i = 0; i < players.length; i++)
			this.csv.addElementToRow(this.JITCurves[0], players[i].getIA().toString());
		this.csv.endRow(this.JITCurves[0]);

		for (int i = 1; i < this.JITCurves.length; i++)
		{
			this.csv.addElementToRow(this.JITCurves[i], "Tours");
			for (int k = 0; k < players.length; k++)
				this.csv.addElementToRow(this.JITCurves[i], players[k].getIA().toString());
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
				int occ = 0;
				// AJOUT DU TOUR A CHAQUE DEBUT DE LIGNE
				this.csv.addSomething(tmpFile, Integer.toString(nbRounds));
				// POUR CHAQUE JOUEUR
				for (int currPlayer = 0; currPlayer < this.playersArrays.size(); currPlayer++)
				{

					// SI LE JOUEUR POSSEDE LA RESSOURCE A CE TOUR T
					if (this.playersArrays.get(currPlayer)[statDataIndex].size() > nbRounds)
					{
						// SI OUI, AJOUTER LA RESSOURCE
						this.csv.addElementToRow(tmpFile, this.playersArrays.get(currPlayer)[statDataIndex].get(nbRounds).toString());
						averageStatData += this.playersArrays.get(currPlayer)[statDataIndex].get(nbRounds);
						occ++;
					}
					else
					{
						// SI NON, NE RIEN AJOUTER
						this.csv.addNothing(tmpFile);
					}

				}
				// AJOUT DU TOTAL SUR LES JOUEURS
				this.csv.addElementToRow(tmpFile, Double.toString((double)averageStatData/occ));
				this.csv.endRow(tmpFile);
			}
		}
	}

	public void updateStats (InventoryStruct[] invs, PlayerStruct[] players)
	{
		// SCORE FIRST
		for (int i = 0; i < players.length; i++)
		{
			this.playersArrays.get(i)[0].add(players[i].getScore());
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
				this.playersArrays.get(i)[k].add(ressrc[k-1]);
			}
		}
	}

	/* GETTERS */
	public CSVUtility getCSV () {return this.csv;}
}
