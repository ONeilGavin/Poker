public class Deck {
	private  Card[] cards;
	public static final int MAX_SIZE = 52;
	private int currentSize;
	private String[] suit = {"Hearts", "Clubs", "Spades", "Diamonds"};
	private String[] name = {"Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Jack", "Queen", "King", "Ace"};	
	
	public Deck() {
		cards = new Card[52];
		int suitCnt = 0;
		
		for(int i = 0; i < 4; i++) { //Goes through every suit
			for(int j = 0; j < 13; j++) { //Goes through each card 
				cards[suitCnt] = new Card(suit[i], name[j], j+2); 
				currentSize++;
				suitCnt++;
			}
			
		}
	}
	
	public Card deal() {
		Card retVal;
		if(currentSize > 0) {
		
		
		retVal = cards[currentSize -1];
		cards[currentSize -1] = null;
		currentSize -= 1;
		}
		else
			retVal = null;
		return retVal;
		
	}
	
	public boolean returnToDeck(Card c) {
		boolean retVal = false;
		
		if(currentSize + 1 < MAX_SIZE) {
			for(int i = 1; i < currentSize; i++) {
				cards[i] = cards[i + 1];
			}
			currentSize++;
			cards[0] = c;
			retVal = true;
		}
		
		return retVal;
		
	}
	
	public boolean returnToDeck(Card[] c) {
		boolean retVal = false;

		if(c.length + currentSize < MAX_SIZE) {
			retVal = true;
			for(int i =0; i < c.length; i++) {
				returnToDeck(c[i]);
				currentSize++;
				
			}
				
		}
			
		
		return retVal;
		
		
	}
	
	public void shuffle() {
	for(int j = 0; j < 100; j++) { //Performs shuffling 100 times
		for(int i = 0; i < currentSize-1; i++) {
			Card temp = cards[i];
			int pos = (int)(Math.random() * cards.length);
			cards[i] = cards[pos];
			cards[pos] = temp;
			
			
			}
		
		}
	}
	//Helper method to sort cards numerically by face value
	public static void sortCards(Card[] hand) {
		 int maxIndex = 0;
		 int placeCards = 0;
		 
		 while(placeCards < 5) {
			 int max = 0;
			 
			 for(int i = 0; i< hand.length-placeCards; i++) {
				 if(hand[i].getFaceValue() > max) {
					 max = hand[i].getFaceValue();
					 maxIndex = i;
				 }

			 }
			 
			 Card c = hand[hand.length-1-placeCards];
			 hand[hand.length-1-placeCards] = hand[maxIndex];
			 hand[maxIndex] = c;
			 placeCards++;
		 }
		 
		
		
	}
	//Helper method to move null cards to the end of the array
	public static void fixCards(Card[] hand) {
		for(int i = 0; i < hand.length-1; i++) {
			if(hand[i] == null && hand[i+1] != null) {
				hand[i] = hand[i+1];
				hand[i+1] = null;
			}
		}
	}
	
	@Override
	public String toString() {
		String retVal = "Size: " + currentSize + "\n";

		for(int i = 0; i < currentSize; i ++) {
			if(cards[i]!=null)
			  retVal += cards[i].toString();
		}
		
		return retVal;
	}
	
	public static void main(String[] args)
    {
       
      Card[] player1Hand = new Card[7];
      Card[] player2Hand = new Card[7];
      
    	Deck myDeck = new Deck();
   		
   	System.out.println("****New Deck *******");

    	System.out.println(myDeck);
    
    	myDeck.shuffle();
    	
    	System.out.println("****Shuffled Deck - ensure order changed *******");
    	System.out.println(myDeck);
    	
    	System.out.println("****Check Deck - ensure all cards here *******");
    	//System.out.println(myDeck.checkDeck());
    
    	for(int i=0; i<7; i++)
    	{
    		player1Hand[i] = myDeck.deal();
    	  player2Hand[i] = myDeck.deal();
    	}
    	
    	System.out.println("****Check Deck After Dealing 14 Cards *******");
    	//System.out.println(myDeck.checkDeck());
    
    	System.out.println("*****Deck After Dealing 14 ********");
    	System.out.println(myDeck);
    	myDeck.shuffle();//shuffle smaller deck
   
    	System.out.println("*******Shuffled Smaller Deck: ");
    	System.out.println(myDeck);	
    	System.out.println();
   
    	System.out.println("**********Player 1 Hand:*********");
    	for(int i=0; i<player1Hand.length; i++)
    	{
    		if(player1Hand[i]!=null) //Ensure we do not try to access null reference
    		   System.out.print(" " + player1Hand[i]);
    	}
    	sortCards(player1Hand);
      System.out.println("Sorted");
    	for(int i=0; i<player1Hand.length; i++)
    	{
    		if(player1Hand[i]!=null) //Ensure we do not try to access null reference
    		   System.out.print(" " + player1Hand[i]);
    	}	
    	
    	System.out.println("\n**********Player 2 Hand:*********");
    	for(int i=0; i<player2Hand.length; i++)
    	{
    			if(player2Hand[i]!=null)
    				System.out.print(" " + player2Hand[i]);
    	}
    	
      //Return player 1's middle card:
      myDeck.returnToDeck(player1Hand[player1Hand.length/2]);
      player1Hand[player1Hand.length/2]=null;
      
     	 System.out.println("\n***********player 1 hand after returning card**************:");
    	   
    		for(int i=0; i<player1Hand.length; i++)
    	  {
    			if(player1Hand[i]!=null) //needed so we don't access null Card
    				System.out.print(" " + player1Hand[i]);
    	  }
    	  System.out.println("\n**********Deck after having one card returned ***********//*");
    	  System.out.println("\n" + myDeck);
    	//  System.out.println(myDeck.checkDeck());
    	  
    	  Deck.fixCards(player1Hand);
    	  
    	  myDeck.returnToDeck(player1Hand);
    	  myDeck.returnToDeck(player2Hand);
 System.out.println("\n**********Deck after all cards returned ***********//*");
    	
    	 // System.out.println(myDeck.checkDeck());
    	  System.out.println(myDeck);
    	  
    }
}
	
