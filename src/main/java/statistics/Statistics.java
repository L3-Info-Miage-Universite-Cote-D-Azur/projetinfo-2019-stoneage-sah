package statistics;

import java.io.*; 
import java.util.*; 
import game.Settings;
import game.Ressource;
import inventory.InventoryStruct;
import player.PlayerStruct;
import printer.Printer;

public class Statistics
{
	/* FIELDS */

	/*
	 *	Fichiers dans cet ordre: Score, Wood, Clay, Stone, Gold, Food, Field
	 */
	// CONTIENT LA MOYENNE FINALE DES RESSOURCES
	private File[] average;
	// CONTIENT LES DONNEES BRUTS
	private File[] curvesDuringRounds;
	// CONTIENT LES DONNEES TEL QU'A LA FIN D'UNE PARTIE, LA DERNIERE RESSOURCE PERDURE SUR LES PROCHAINS TOUS
	// Si la partie se termine avec [2, 3, 4], et si sur l'autre on aura 5 tours, on aura: [2, 3, 4, 4, 4] + [autre partie sur 5 tours]
	private File[] curvesWithSameRounds;
	// CONTIENT LE TAUX DE VICTOIRE
	private File victoryRate;
	private CSVUtility csv;

	/*
	 *  playersArrays est un tableau d'ArrayList
	 *  playersArrays[0] represente un ArrayList
	 *	playersArrays[0].get(0) represente un tableau d'ArrayList pour les stats du Joueur 0
	 *	playersArrays[0].get(0)[0] represente le premier ArrayList, a savoir celui pour le score du Joueur 0
	 *	playersArrays[0].get(0)[0].get(0) represente le score du Joueur 0 au tour 0, represente' par un ArrayList car le nombre de tour est UB (Undefined Behavior)
	 */
	private ArrayList<ArrayList<Integer>[]>[] playersArrays;
	// CONTIENT LE TAUX DE VICTOIRE
	private double[] victoryRateArray;

	/* CONSTRUCTORS */
	public Statistics (int nbPlayers) throws FileNotFoundException
		{this(nbPlayers, false);}
	@SuppressWarnings("unchecked")
	public Statistics (int nbPlayers, boolean append) throws FileNotFoundException
	{
		// CREATING THE DIRECTORIES
		new File("./stats").mkdir();
		new File("./stats/averages").mkdir();
		new File("./stats/curves-during-rounds").mkdir();
		new File("./stats/curves-with-same-number-of-rounds").mkdir();

		// INSTANCIATE THE FIELDS
		this.average = new File[Settings.FILE_NAMES.length];
		this.curvesDuringRounds = new File[Settings.FILE_NAMES.length];
		this.curvesWithSameRounds = new File[Settings.FILE_NAMES.length];
		this.csv = new CSVUtility();

		for (int i = 0; i < Settings.FILE_NAMES.length; i++)
		{
			this.average[i] = new File("./stats/averages/" + Settings.FILE_NAMES[i]);
			this.curvesDuringRounds[i] = new File("./stats/curves-during-rounds/" + Settings.FILE_NAMES[i]);
			this.curvesWithSameRounds[i] = new File("./stats/curves-with-same-number-of-rounds/" + Settings.FILE_NAMES[i]);
			
			if (append == false)
			{
				this.csv.clearFile(this.average[i]);
				this.csv.clearFile(this.curvesDuringRounds[i]);
				this.csv.clearFile(this.curvesWithSameRounds[i]);
			}
		}

		this.playersArrays = new ArrayList[2];
		for (int i = 0; i < this.playersArrays.length; i++)
		{
			this.playersArrays[i] = new ArrayList<ArrayList<Integer>[]>();
			for (int k = 0; k < nbPlayers; k++)
			{
				this.playersArrays[i].add(new ArrayList[Settings.FILE_NAMES.length]);
				for (int n = 0; n < Settings.FILE_NAMES.length; n++)
				{
					this.playersArrays[i].get(k)[n] = new ArrayList<Integer>();
				}
			}
		}

		this.victoryRate = new File("./stats/victoryRate.csv");
		this.victoryRateArray = new double[nbPlayers];
	}

	/* METHODS */

	// On veut un fichier qui retourne pour chaque thematique, la caracteristique des 4 joueurs en fonction du nombre de tours
	// Donc quelque chose dans ce genre-la:
	// Tours, ScoreMoyen, RandomIA, NoobIA \n
	// 0, 10, 5, 15 \n
	// 1, 20, 10, 30 \n
	// etc
	/**
	 * Creer tous les fichiers dans le dossier de "files" qui representent la moyenne des ressources au cours des tours sur Settings.NB_GAMES 
	 * @param players le nom des IA de chaque joueur
	 * @param playersArrays la liste de joueur
	 * @param files le fichier
	 * @throws IOException IO Exception
	 */
	public void createJITCurves (String[] players, ArrayList<ArrayList<Integer>[]> playersArrays, File[] files) throws IOException
	{
		// DONNER LES NOMS DE DEPART
		this.csv.addSomething(files[0], "tours");
		for (int i = 0; i < players.length; i++)
			this.csv.addElementToRow(files[0], players[i]);
		this.csv.addElementToRow(files[0], "scoreMoyen");
		this.csv.endRow(files[0]);

		for (int i = 1; i < files.length; i++)
		{
			this.csv.addElementToRow(files[i], "Tours");
			for (int k = 0; k < players.length; k++)
				this.csv.addElementToRow(files[i], players[k]);
			this.csv.addElementToRow(files[i], Ressource.indexToRessource(i-1).toString()+"Moyen");
			this.csv.endRow(files[i]);
		}

		// FIND THE MAX ROUNDS
		int max = 0;
		for (int i = 0; i < playersArrays.size(); i++)
			for (int k = 0; k < playersArrays.get(i).length; k++)
				if (max < playersArrays.get(i)[k].size())
					max = playersArrays.get(i)[k].size();

		// POUR LE NB DE TOUR MAX
		for (int nbRounds = 0; nbRounds < max; nbRounds++)
		{
			// POUR CHAQUE DATA
			for (int statDataIndex = 0; statDataIndex < playersArrays.get(0).length; statDataIndex++)
			{
				File tmpFile = files[statDataIndex];
				int averageStatData = 0;
				// AJOUT DU TOUR A CHAQUE DEBUT DE LIGNE
				this.csv.addSomething(tmpFile, Integer.toString(nbRounds));
				// POUR CHAQUE JOUEUR
				for (int currPlayer = 0; currPlayer < playersArrays.size(); currPlayer++)
				{

					// SI LE JOUEUR POSSEDE LA RESSOURCE A CE TOUR T
					if (playersArrays.get(currPlayer)[statDataIndex].size() > nbRounds)
					{
						// SI OUI, AJOUTER LA RESSOURCE
						this.csv.addElementToRow(tmpFile, Double.toString((double)playersArrays.get(currPlayer)[statDataIndex].get(nbRounds)/Settings.NB_GAMES));
						averageStatData += playersArrays.get(currPlayer)[statDataIndex].get(nbRounds);
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
		for (int i = 0; i < players.length; i++)
		{
			this.csv.addSomething(this.average[0], players[i]);
			this.csv.addNothing(this.average[0]);
		}
		this.csv.addSomething(this.average[0], "scoreMoyen");
		this.csv.addNothing(this.average[0]);
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
		for (int i = 0; i < this.playersArrays[1].size(); i++)
			for (int k = 0; k < this.playersArrays[1].get(i).length; k++)
				if (max < this.playersArrays[1].get(i)[k].size())
					max = this.playersArrays[1].get(i)[k].size();

		// POUR CHAQUE DATA
		for (int statDataIndex = 0; statDataIndex < this.playersArrays[1].get(0).length; statDataIndex++)
		{
			File tmpFile = this.average[statDataIndex];
			int averageStatData = 0;
			// SI LE JOUEUR POSSEDE LA RESSOURCE A CE TOUR T
			if (this.playersArrays[1].get(0)[statDataIndex].size() > max - 1)
            {
                // SI OUI, AJOUTER LA RESSOURCE
                this.csv.addSomething(tmpFile, Double.toString((double)(this.playersArrays[1].get(0)[statDataIndex].get(max - 1))/(double)(Settings.NB_GAMES)));
                averageStatData += this.playersArrays[1].get(0)[statDataIndex].get(max - 1);
            }
			else
			{
				// SI NON, NE RIEN AJOUTER
				this.csv.addNothing(tmpFile);
			}
			// POUR CHAQUE JOUEUR
			for (int currPlayer = 1; currPlayer < this.playersArrays[1].size(); currPlayer++)
			{
				// SI LE JOUEUR POSSEDE LA RESSOURCE A CE TOUR T
				if (this.playersArrays[1].get(currPlayer)[statDataIndex].size() > max - 1)
				{
					// SI OUI, AJOUTER LA RESSOURCE
					this.csv.addElementToRow(tmpFile, Double.toString((double)this.playersArrays[1].get(currPlayer)[statDataIndex].get(max - 1)/Settings.NB_GAMES));
					averageStatData += this.playersArrays[1].get(currPlayer)[statDataIndex].get(max - 1);
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
		
		this.csv.addSomething(this.victoryRate, Double.toString((double)((double)(this.victoryRateArray[0] * 100)/(double)Settings.NB_GAMES)) + "%");
		this.csv.addNothing(this.victoryRate);
		for (int i = 1; i < this.victoryRateArray.length; i++)
		{
			this.csv.addElementToRow(this.victoryRate, Double.toString((double)((this.victoryRateArray[i] * 100)/Settings.NB_GAMES)) + "%");
			this.csv.addNothing(this.victoryRate);
		}
		this.csv.endRow(this.victoryRate);
	}
	
	/**
	 * Creer tous les fichiers statistiques
	 * @param players les noms des IAs des joueurs
	 * @throws IOException IO Exception
	 */
	public void createAll (String[] players) throws IOException
	{
		/*
		 * Ici on va remplir chaque ArrayList avec leur derniere valeur jusqu'a que
		 * tous les ArrayList aient la meme taille
		 */
		for (int i = 0; i < this.playersArrays[1].size(); i++)
		{
			int maxIndex = ArrayListUtility.getMaxLength(this.playersArrays[1].get(i));
			int[] values = ArrayListUtility.getLastValuesOf(this.playersArrays[1].get(i));
			
			ArrayListUtility.fillArraysUpTo(this.playersArrays[1].get(i), maxIndex, values);
			//Printer.getPrinter().println(this.playersArrays.get(i)[0].toString() + " " + this.playersArrays.get(i).length);
		}
		Printer.getPrinter().println("$PATH=" + this.average[0].getAbsolutePath());
		this.createAverages(players);
		this.createJITCurves(players, this.playersArrays[0], this.curvesDuringRounds);
		this.createJITCurves(players, this.playersArrays[1], this.curvesWithSameRounds);
		this.createVictoryRate(players);
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
			for (int k = 0; k < this.playersArrays.length; k++)
				this.updateTheStat(this.playersArrays[k], i, 0, players[i].getScore(), nbRounds);
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
				for (int n = 0; n < this.playersArrays.length; n++)
					this.updateTheStat(this.playersArrays[n], i, k, ressrc[k-1], nbRounds);
			}
		}
	}

	/**
	 * Permet soit d'ajouter la valeur si le tour n'existe pas, soit accumule les donnees
	 * @param list une liste d'entier
	 * @param currPlayer l'indice du joueur
	 * @param currData l'indice de la donnee
	 * @param value la valeur
	 * @param rounds le tour courant
	 */
	public void updateTheStat (ArrayList<ArrayList<Integer>[]> list, int currPlayer, int currData, int value, int rounds)
	{
		//Printer.getPrinter().println("BEFORE: " + this.playersArrays.get(currPlayer)[currData].toString());
		if (rounds == 1 && list.get(currPlayer)[currData].size() == 0)
		{
			list.get(currPlayer)[currData].add(value);
			return;
		}
		
		//Printer.getPrinter().println(this.playersArrays.get(currPlayer)[currData].toArray().length + " " + Integer.toString(rounds));
		if (list.get(currPlayer)[currData].size() > rounds - 1)
		{
			int res = list.get(currPlayer)[currData].get(rounds - 1);
			//Printer.getPrinter().println("1 RES: " + res + " " + this.playersArrays.get(currPlayer)[currData].toString());
			list.get(currPlayer)[currData].set(rounds - 1, res + value);
			//Printer.getPrinter().println("2 RES: " + res + " " + this.playersArrays.get(currPlayer)[currData].toString());
			//Printer.getPrinter().println(this.playersArrays.get(currPlayer)[currData].get(rounds - 1).toString() + " " + Integer.toString(value));
		}
		else
		{
			// PREND LA DERNIERE VALEUR ET L'AJOUTE AVEC LA NOUVELLE
			int lv = ArrayListUtility.getLastValueOf(list.get(currPlayer)[currData]);
			//Printer.getPrinter().println("3 LV: " + lv + " " + this.playersArrays.get(currPlayer)[currData].toString());
			if (list == this.playersArrays[0])
				list.get(currPlayer)[currData].add(lv + value);
			else
				list.get(currPlayer)[currData].add(value);
			//Printer.getPrinter().println("4 LV: " + lv + " " + this.playersArrays.get(currPlayer)[currData].toString());
		}
	}
	
	/**
	 * Permet lorsque la partie est finie, de conserver les donnees jusqu'au tour max
	 * @param invs l'inventaire des joueurs
	 * @param players les joueurs
	 * @param nbRounds le nombre de tours
	 */
	public void endGame (InventoryStruct[] invs, PlayerStruct[] players, int nbRounds)
	{
		if (nbRounds - 1 == this.playersArrays[1].get(0)[0].size())
			return;
		
		// SCORE FIRST
		for (int i = 0; i < players.length; i++)
		{
			//Printer.getPrinter().println(this.playersArrays.get(i)[0].toString());
			ArrayListUtility.addFromTo(this.playersArrays[1].get(i)[0], nbRounds - 1, this.playersArrays[1].get(i)[0].size(), players[i].getScore());
			//Printer.getPrinter().println(this.playersArrays.get(i)[0].toString());
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
				ArrayListUtility.addFromTo(this.playersArrays[1].get(i)[k], nbRounds - 1, this.playersArrays[1].get(i)[k].size(), ressrc[k-1]);
			}
		}
	}

	/* GETTERS */
	public CSVUtility getCSV () {return this.csv;}
	public ArrayList<ArrayList<Integer>[]>[] getDatas () {return this.playersArrays;}
	public double[] getVictoryRate() {return this.victoryRateArray;}
}
