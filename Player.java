import java.util.Scanner;
public class Player {
	
	private Card[] hand;
	private final int MAX_SIZE = 5;
	private int currentSize;
	private String name;
	
	public Player(String n, int max) {
		name = n;
		hand = new Card[max];
		
	}
	
	public void setCard(Card c) {
		
		if(currentSize<MAX_SIZE) {
			
            for (int i = 0; i < MAX_SIZE; i++) {
                if (hand[i] == null) {
                    hand[i] = c;
                    currentSize++;
                    break;
                }
            }
        }
	}
	
	public Card discard(int i) {
		Card retVal = null;
		
		if(hand[i] != null) {
		currentSize--;
		hand[i] = null;
		retVal = hand[i];
		}
		
		return retVal;
	}
	
	public Card[] discard() {
		for(int i = 0; i < hand.length; i++) {
			discard(i);
		}
		return hand;
	}
	
	public Card[] returnHand() {
		return hand;
	}
	
	public String getName() {
		return name;
	}
	public String showHand() {
		String retVal = "";
		for(int i = 0; i < hand.length; i++) {
			if(hand[i] != null)
			retVal += hand[i].toString();
		}
		
		return retVal;
	}
	
	/*Modify tester so that you instantiate a Deck and a Player. 
	Deal 5  Cards  to the Player
	Show the Cards
	Allow the Player to choose to discard up to 5 Cards and deal Player new Cards so they have 5. 
	Create a “discarded Cards pile”.
	Show new hand.
	Show deck //verify the dealt Cards are not in deck.
	Return both hands and discarded Cards back to deck.
	Show deck //verify full Deck.
	*/
	public static void main(String[] args)
	{
		Card[] discardPile= new Card[52]; //Create discard pile so that we have a place to put discarded cards
		int sizeOfDiscard=0; //starts at 0
	  Scanner reader = new Scanner(System.in);
		
		Deck myDeck = new Deck();
		System.out.println(myDeck);
		
		myDeck.shuffle();
		Player p1 = new Player("fred", 5); //sets player name and max number cards
		Player p2 = new Player("wilma", 5); 
		
		//Deal out 5 cards to each player
		for(int i=0; i<5; i++)
		{
			p1.setCard(myDeck.deal());
			p2.setCard(myDeck.deal());
		}
		System.out.println("player 1: " + p1.showHand() + "\nPress enter when ready"); //Show user player 1 hand
	  reader.nextLine();
	    
		System.out.println("player 2: " + p2.showHand() + "\nPress enter when ready"); //Show user player 1 hand
	  reader.nextLine();

		System.out.println("Remaining Deck\n: " + "\nVerify p1 and p2 cards missing, press enter when ready");
	  reader.nextLine();
	  
		System.out.println("player 1: " + p1.showHand()); //Show user player 1 hand
	  
		int discardAmt=0;
	  do{
	     try{
	    	    
	    		System.out.println("P1: How many cards would you like to trade? (0-5)");
	    		discardAmt = reader.nextInt();
	    		reader.nextLine(); //to clean up new line character not picked up by nextInt()
	    	  }
	    	  catch(Exception e)
	    	  {
	    	    	System.out.println("Invalid response, try again");
	    	    	reader.nextLine();
	    	    	discardAmt=6;
	    	  }
	   }while(discardAmt>5||discardAmt<0); //Stay in this loop if invalid discardAmt entered
	    		
	   if(discardAmt>0)
	    {
	    		try{
	    			
	    				
	    				System.out.println("Enter card numbers, separated by space");
	    				int cardsToDiscardInts[] = new int[discardAmt];
	    				int j=0;
	    	
	    				while(j<discardAmt){    				
	    					 cardsToDiscardInts[j] = reader.nextInt();
	    					 j++;
	    				}
	    					for(int i=0; i<discardAmt; i++)
	    				{
	    			
	    					discardPile[sizeOfDiscard]=p1.discard(cardsToDiscardInts[i]);
	    	
	    					sizeOfDiscard++;
	    				}
	    			  
	    			myDeck.fixCards(p1.returnHand()); //Remove the nulls
	  				System.out.println("**Verify Player 1 Cards now ** Press enter when ready");
	  				reader.nextLine();
	  				System.out.println(p1.showHand());
	   
	   				//Give p1 new cards to replace the discarded cards.
	    			for(int i=0; i<discardAmt; i++)
	    			{
	    				p1.setCard(myDeck.deal());    	
	    		
	    			}
	    		}
	    		catch(Exception e)
	    		{
	    			System.out.println("An exception occurred, make sure you are entering numbers of cards to delete");
	    			return;
	    		}
	    	
	    	
	    	
	    }
	    
	    System.out.println("player 2: " + p2.showHand());
	    System.out.println("player 1: " + p1.showHand());
	    System.out.println("press enter when ready");
	    reader.nextLine();
	    
	       System.out.println("*******Verify Deck is missing p1 and p2 cards plus the cards p1 discarded******");
	      //myDeck.checkDeck();//
	      System.out.println("Press enter when ready");
	      reader.nextLine();
	      
	    	myDeck.returnToDeck(p1.discard());
	    	myDeck.returnToDeck(p2.discard());
	    
	    	myDeck.returnToDeck(discardPile);
	    	
	      System.out.println("*******Verify all cards have been returned******");
	  	


	}
	    
	}
