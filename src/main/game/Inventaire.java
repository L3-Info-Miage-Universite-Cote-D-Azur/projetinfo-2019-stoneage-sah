package Game;

public class Inventaire {
	
	/* FIELDS */
	private int wood;
	
	/* CONSTRUCTOR */
	public Inventaire () {
		this.wood = 0;
	}
	
	/* GETTERS */
	public int getWood () {return this.wood;}
	
	/* ADDERS */
	public boolean addWood (int wood) {
		if (wood >= 0 && wood <= (Settings.MAX_DICE * Settings.MAX_FIGURINE_IN_ZONE) / 3) {
			this.wood = wood;
			return true;
		}
		return false;
		
	}
}
