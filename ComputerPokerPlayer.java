public class ComputerPokerPlayer extends PokerPlayer {
	Strategy s;
	
	public ComputerPokerPlayer(int MAX_SIZE, double money) {
		super("Computer", MAX_SIZE, money);
		this.s = new Strategy();
	}
	
	//Returns 0 to fold, 1 to match, the amount raising, and 3 to go all in.
	public double computerBet(Card[] hand, double bal, double bet) {
		double[] play = s.bet(hand, bal, bet);
		double retVal;
		
		if(play[0] == 0)
			retVal = 0;
		else if(play[0] == 1)
			retVal = 1;
		else if(play[0] == 2)
			retVal = play[1];
		else if(play[0] == 3)
			retVal = 3;
		else
			retVal = -1;
		return retVal;
	}
	
	public int[] playHand(Card[] hand) {
		int[] indices = s.cardsToDiscard(hand);
		int[] retVal = new int[findArrayLength(indices)];
		
		for(int i = 0; i < retVal.length-1; i++) {
			if(indices[i] != -1)
				retVal[i] = indices[i];
		}
		return retVal;
		
	}
	//Finds the non-null elements in the array of indices to find the length of the array playHand will return
	private static int findArrayLength(int[] indices) {
		int retVal = 0;
		for(int i = 0; i < indices.length-1; i++) {
			if(indices[i] != 0)
				retVal++;
		}
		return retVal;
	}

}
