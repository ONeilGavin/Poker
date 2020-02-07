public class GameRules {
	private double ante;

    public GameRules(double ante) {
        this.ante = ante;
    }
    
    public double getAnte() {
    	return ante;
    }

    public void raiseAnte(double amount) {
        ante += amount;
    }
    
	public static int[] scoreCards(Card[] hand) {
		int[] sumAndHighCard = new int[2];
		int highC = 0;
		int sum = 0;
		int pairs = 0;
		int fourOfAKindValue = 0, threeOfAKindValue = 0, pairValue = 0;
		boolean flush = false;
		boolean straight = false;
		boolean royal = false;
		boolean fourOfAKind = false, threeOfAKind = false;
		
		Deck.sortCards(hand);
		
		
		 //Check if flush
		for(int i = 1; i < hand.length-1; i++) {
			//All suits have to be the same
			if(hand[i].getSuit().equals(hand[i+1].getSuit()) && hand[i].getSuit().equals(hand[i-1].getSuit()))
				flush = true;
			else
				flush = false;
		}
		
		//Check if straight
		for(int i = 1; i < hand.length-1; i++) {
			//Numbers have to be in ascending order
			if(hand[i].getFaceValue() == hand[i+1].getFaceValue() - 1 && hand[i].getFaceValue() == hand[i-1].getFaceValue() + 1)
				straight = true;
			else
				straight = false;
			
		}
		
		//Check if four of a kind
		for(int i = 0; i < 2; i++){
            int temp = hand[i].getFaceValue();
            
            if(hand[i+1].getFaceValue() == temp && hand[i+2].getFaceValue() == temp && hand[i+3].getFaceValue() == temp) {
            	fourOfAKind = true;
            	fourOfAKindValue = hand[i].getFaceValue();
            }
        }
		//Check if three of a kind
		for(int i = 0; i < 3; i++){
            int temp = hand[i].getFaceValue();
            
            if(hand[i+1].getFaceValue() == temp && hand[i+2].getFaceValue() == temp) {
            	threeOfAKind = true;
            	threeOfAKindValue = hand[i].getFaceValue();
            }
        }

		//Find number of pairs
		for(int i = 0; i < hand.length - 1; i++){
            if(hand[i].getFaceValue() == hand[i + 1].getFaceValue()) {
            	pairValue = hand[i].getFaceValue();
                pairs++;
                
            }
        }
	
		//Check for royal flush
		if(straight && flush) {
			for(int i = 0; i < hand.length - 1; i++) {
				if(hand[0].getFaceValue() == 10) {
					if(hand[i].getFaceValue() + 1 == hand[i+1].getFaceValue())
						royal = true;
				}
			}
		}
		
		//Royal flush
		if(royal) {
			sum = 10;
			highC = 14;
		}
		//Straight flush
		else if(straight && flush) {
			sum = 9;
			//Set the highest card
			highC = hand[hand.length-1].getFaceValue();
		}
		//Just flush
		else if(flush) {
			sum = 6;
			highC = hand[hand.length-1].getFaceValue();
		}
		//Just straight
		else if(straight) {
			sum = 5;
			highC = hand[hand.length-1].getFaceValue();
		}
		//Full house
		else if(threeOfAKind && (hand[0].getFaceValue() == hand[1].getFaceValue() || hand[3].getFaceValue() == hand[4].getFaceValue())) {
			sum = 7;
			if(hand[0].getFaceValue() > hand[hand.length-1].getFaceValue())
				highC = hand[0].getFaceValue();
			else
				highC = hand[hand.length-1].getFaceValue();
		}
		//Four of a kind
		else if(fourOfAKind) {
			sum = 8;
			highC = fourOfAKindValue;
		}
		
		//Three of a kind
		else if(threeOfAKind) {
			sum = 4;
			highC = threeOfAKindValue;
		}
		//Two pair
		else if(pairs == 2) {
			sum = 3;
			highC = pairValue;
		}
		//One pair
		else if(pairs == 1) {
			sum = 2;
			highC = pairValue;
		}
		//High card
		else {
			sum = 1;
			highC = hand[hand.length-1].getFaceValue();
		}
		//"High card" is the highest card out of pairs, or the value of the cards in a pair. It can also just be the highest value card in the deck.
		sumAndHighCard[0] = sum;
		System.out.println(sumAndHighCard[0]);
		sumAndHighCard[1] = highC;
		return sumAndHighCard;		
		
	}
	/* 
	 *
	 * 2 pair
	 * pair
	 */
	//1 if hand 1 wins, 1 if hand 2 wins, 0 if they are the same
	public static int breakTie(Card[] hand1, Card[] hand2) {
		int[] score = scoreCards(hand1);
		int[] score2 = scoreCards(hand2);	
		int retVal = 0;
		
		if(score[1] > score2[1])
			retVal = 1;
		else if(score[1] < score[2])
			retVal = 2;
		else {
			
			//Four of a kind
			if(score[0] == 8) {
				
				int high1, high2;
				if(hand1[0].getFaceValue() != score[1])
					high1 = hand1[0].getFaceValue();
				else
					high1 = hand1[hand1.length-1].getFaceValue();
				
				if(hand2[0].getFaceValue() != score2[1])
					high2 = hand2[0].getFaceValue();
				else
					high2 = hand2[hand2.length-1].getFaceValue();
				
				if(high1 > high2)
					retVal = 1;
				else if(high1 < high2)
					retVal = 2;
				else
					retVal = 0;
			}
			//Three of a kind
			else if(score[0] == 4) {
				int sum1 = 0, sum2 = 0;
				
				for(int i = 0; i < hand1.length; i++) {
					if(hand1[i].getFaceValue() != score[1])
						sum1 += hand1[i].getFaceValue();
				}
				
				for(int i = 0; i < hand2.length; i++) {
					if(hand2[i].getFaceValue() != score2[1])
						sum2 += hand2[i].getFaceValue();
				}
				
				if(sum1 > sum2)
					retVal = 1;
				else if(sum1 < sum2)
					retVal = 2;
				else
					retVal = 0;
			}
			//One pair
			else if(score[0] == 2) {
				int sum1 = 0, sum2 = 0;
				
				for(int i = 0; i < hand1.length; i++) {
					if(hand1[i].getFaceValue() != score[1])
						sum1 += hand1[i].getFaceValue();
				}
				
				for(int i = 0; i < hand2.length; i++) {
					if(hand2[i].getFaceValue() != score2[1])
						sum2 += hand2[i].getFaceValue();
				}
				
				if(sum1 > sum2)
					retVal = 1;
				else if(sum1 < sum2)
					retVal = 2;
				else
					retVal = 0;
				
			}
			
			else
				retVal = 0;
			
		}
		return retVal;
			
	}
}
