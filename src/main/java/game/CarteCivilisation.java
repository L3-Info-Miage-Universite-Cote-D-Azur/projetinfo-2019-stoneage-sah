package game;

/**
 * La Classe CarteCivilisation represente les cartes civilisations du jeu.
 * @author Mentra20
 *
 */
public class CarteCivilisation{

	private final int typeDownPart; // de 0 a 7 pour les cartes vertes, 8 a 11 pour les cartes jaunes. 
	/*
	 * 0 -> medecine
	 * 1 -> Art
	 * 2 -> Ecriture
	 * 3 -> Poterie
	 * 4 -> Cadran Solaire
	 * 5 -> Transport
	 * 6 -> Musique
	 * 7 -> Tissage
	 * 8 -> Sable paysan
	 * 9 -> Sable fabricant d'outil
	 * 10 -> Sable constructeur
	 * 11 -> Sable chamane
	 */
	private final int numberDownPart; // pour les cartes sables : le nombre de personnage sur la carte.
	private final int typeUpPart; 
	/*
    Pour typeUpPart:
    0: ressource instantanee joueur
    1: point de victoire
    2: pioche une carte civilisation supplementaire
    3: tout les joueur selectionne un objet parmi la liste
    4: carte outils temporaire.
    5 : carte ressources a utiliser quand on veut.
	 */

	private final Ressource ressource; //La ressrouce concernee par la carte. 
	private final int numberEffect; //Le nombre de l'effet de la carte.

	/* CONSTRUCTOR */
	public CarteCivilisation(int typeUp, int typeDown,int numberDownPart, Ressource ressource, int numberEffect){
		typeUpPart = typeUp;
		typeDownPart = typeDown;
		this.ressource = ressource;
		this.numberDownPart = numberDownPart;
		this.numberEffect = numberEffect;
	}
	
	/* GETTERS */
	public int getTypeDownPart() { return typeDownPart; }
	public int getNumberDownPart() { return numberDownPart; }
	public int getTypeUpPart() { return typeUpPart; }
	public Ressource getRessource() { return ressource; }
	public int getNumberEffect() { return numberEffect; }
	
	public String toString() {
		String string = "Carte ";
		switch (this.typeDownPart) {
		case 0:
			string +="Medecine;";
			break;
		case 1:
			string +="Art;";
			break;
		case 2:
			string +="Ecriture;";
			break;
		case 3:
			string +="Poterie;";
			break;
		case 4:
			string +="Cadran solaire;";
			break;
		case 5:
			string +="Transport;";
			break;
		case 6:
			string +="Musique;";
			break;
		case 7:
			string +="Tissage;";
			break;
		case 8:
			string +="Paysan;";
			break;
		case 9:
			string +="Fabricant d'outil;";
			break;
		case 10:
			string +="Constructeur;";
			break;
		case 11:
			string +="Chamane;";
			break;
		default:
			break;
		}
		
		string += " gain:";
		switch (this.typeUpPart) {
		case 0:
			if(numberEffect == 0) {
				string += " (chiffre du de divise par "+ressource.getDivisor()+")";
			}else {
				string += " "+numberEffect;
			}
			string += " "+ressource.toString()+";";
			break;
		case 1:
			string += " "+numberEffect+" points de victoire;";
			break;
		case 2:
			string += " pioche de carte civilisation;";
			break;
		case 3 :
			string += " tirage au sort;";
			break;
		case 4 : 
			string += " outils unique de valeur "+numberEffect+";";
			break;
		case 5 : 
			string += " ressources au choix;";
			break;
		default:
			break;
		}
		return string;
	}
}