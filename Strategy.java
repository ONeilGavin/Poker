public class Strategy {
	
	//Returns 0 to fold, 1 to match, 2 to raise, and 3 to go all in. Also returns bet amount
	public double[] bet(Card[] hand, double bal, double bet) {
		//Index 0 is the action, index 1 is how much to bet
		double[] actionAndBet = new double[2];
		
		//Default value when there is no need to say how much to bet
		actionAndBet[1] = -1;
		int[] score = GameRules.scoreCards(hand);
		
		if(score[0] >= 5) {
			actionAndBet[0] = 3;
			actionAndBet[1] = bal;
		}
		
		else if(score[0] >= 2) {
			actionAndBet[0] = 2;
			actionAndBet[1] = bal/3;
		}
		
		else if(score[0] >= 1){
			//If one of the pairs has a value greater than 5, match
			if(score[1] > 5) {
				actionAndBet[0] = 1;
			}
			
			else {
				actionAndBet[0] = 0;
			}
				
		}
		//Decide what to do with high card
		else {
			//Match if the high card is decent
			if(score[0] > 6) {
				actionAndBet[0] = 1;
			}
			
			else {
				actionAndBet[0] = 0;
			}
		}
		return actionAndBet;
	}
	
	public int[] cardsToDiscard(Card[] hand) {
		int[] cards = new int[5];
		int[] score = GameRules.scoreCards(hand);
		
		//Don't discard anything if the cards are good
		if(score[0] >= 5) {
			for(int i = 0; i < cards.length; i++) {
				cards[i] = -1;
			}
		}
		
		else {
			//Discard everything except high card
			if(score[0] == 1) {
				for(int i = 0; i != score[1]; i++) {
					cards[i] = i;
				}
			
			}
			
			else {
				//Discard cards that don't match
				for(int i = 0; i < hand.length; i++) {
					if(hand[0].getFaceValue() == score[1])
						cards[0] = -1;
					if(hand[i].getFaceValue() != score[1])
						cards[i] = i;
				}
			}

		}
		
		return cards;
	}
	

}
