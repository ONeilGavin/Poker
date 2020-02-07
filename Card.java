public class Card {

	final String suit;
	final String name;
	final int faceValue;
	
	public Card(String s, String n, int p) {
		suit = s;
		name = n;
		faceValue = p;
	}
	
	public String getSuit() {
		return suit;
	}
	
	public String getName() {
		return name;
	}
	
	public int getFaceValue() {
		return faceValue;
	}

	@Override
	public String toString() {
		return name + " " + suit + " " + faceValue + "\n";
	}
	
}
