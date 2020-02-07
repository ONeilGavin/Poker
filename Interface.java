import java.util.Scanner;
public class Interface {
	private static Scanner sc;
	private static Deck deck;
	private static PokerPlayer player;
	private static ComputerPokerPlayer computer;
	private static GameRules rules;
	private static Kitty kitty;
	public static void main(String[] args) {

		boolean keepGoing = true;
		kitty = new Kitty();
		rules = new GameRules(2);
		deck = new Deck();
		sc = new Scanner(System.in);
		double playerBet = 0;
		double computerBet = 0;
		
		while(keepGoing) {
			kitty.update(4.0);
			System.out.println("Welcome to 5 card poker!\nWhat is your name?");
			String playerName = sc.nextLine();
			System.out.println("Please press enter to start");
			sc.nextLine();
			
			System.out.println("How much money do you have to bet?");
			double playerMoney = sc.nextDouble();
			player = new PokerPlayer(playerName, 5, playerMoney);
			computer = new ComputerPokerPlayer(5, playerMoney);
			
			System.out.println("Dealing cards...");
			deck.shuffle();
			dealHands();
			System.out.println("Here are your cards, ");
			System.out.println("\n"+player.showHand());
			
			System.out.println("You are the dealer, " + player.getName() + "\nWould you like to fold or bet? enter F to fold or B to bet.");

			
			boolean round = true;
			while(round) {
			String playerChoice = sc.nextLine();
			
			if(playerChoice.equalsIgnoreCase("F")) {
				System.out.println("\nYou folded, the computer wins the game.");
				computer.increase(kitty.payout());
				
				System.out.println("Would you like to play another game? Y or N");
				String choice = sc.next();
				if(choice.equalsIgnoreCase("y"))
					keepGoing = true;
				else if(choice.equalsIgnoreCase("n")) {
					System.out.println("Thanks for playing!");
					keepGoing = false;
					round = false;
					break;
				}
				else
					System.out.println("Please enter a valid response, Y or N");
				
			}
			
			else if(playerChoice.equalsIgnoreCase("B")) {
				boolean bet = true;
				while(bet) {
				System.out.println("Would you like to go all in or raise? Enter A to go all in, R to raise");
				String betChoice = sc.nextLine();
				
				if(betChoice.equalsIgnoreCase("a")) {
					kitty.update(player.getMoney());
					player.deduct(player.getMoney());
					System.out.println("You have decided to go all in, the Kitty is now " + kitty.getTotal());
					round = false;
					bet = false;
				}
				
				else if(betChoice.equalsIgnoreCase("r")) {
				System.out.println("Enter amount to raise");
				playerBet = sc.nextDouble();
				boolean coverBet = player.canCoverBet(playerBet);
				
				if(coverBet) {
					
					player.deduct(playerBet);
					kitty.update(playerBet);
					System.out.println("The Kitty is now " + kitty.getTotal());
					round = false;
					bet = false;
					}
				else if(coverBet == false) {
					System.out.println("You can not afford this bet, please enter something under your balance of " + player.getMoney());
					}
				else 
					System.out.println("Please enter a valid number");
					}

				else {
					System.out.println("Please enter A or R");
				}
			}
		}
			
			
				
					
				}
			boolean round2 = true;
			while(round2) {
				double compBal = computer.getMoney();
				double computerChoice = computer.computerBet(computer.returnHand(), compBal, kitty.getTotal());
				
				if(computerChoice == 0) {
					System.out.println("\nThe computer folded, you win the game!");
					computer.increase(kitty.payout());
					
					System.out.println("Would you like to play another round? Y or N");
					String choice = sc.next();
					if(choice.equalsIgnoreCase("y"))
						keepGoing = true;
					else if(choice.equalsIgnoreCase("n")) {
						System.out.println("Thanks for playing!");
						keepGoing = false;
						round2 = false;
						break;
					}
					else
						System.out.println("Please enter a valid response, Y or N");
					
				}
				
				else {
					if(computerChoice == 1) {
						kitty.update(playerBet);
						System.out.println("The computer has decided to match\nThe Kitty is now " + kitty.getTotal());
						computerBet = playerBet;
					}
					
					if(computerChoice == 3) {
						kitty.update(computer.getMoney());
						System.out.println("The computer has decided to go all in\nThe Kitty is now " + kitty.getTotal());
						computerBet = computer.getMoney();
					}
					else {
						kitty.update(computerChoice);
						System.out.println("The computer has decided to raise by " + computerChoice + " the new Kitty is " + kitty.getTotal());
						computerBet = computerChoice;
				} 
				
				}
				
				int discardAmt=0;
				Card[] discardPile= new Card[52];
				int sizeOfDiscard=0;
				  do{
				     try{
				    	    
				    		System.out.println(player.getName()+ ", How many cards would you like to trade? (0-5)");
				    		discardAmt = sc.nextInt();
				    		sc.nextLine(); //to clean up new line character not picked up by nextInt()
				    	  }
				    	  catch(Exception e)
				    	  {
				    	    	System.out.println("Invalid response, try again");
				    	    	sc.nextLine();
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
				    					 cardsToDiscardInts[j] = sc.nextInt();
				    					 j++;
				    				}
				    					for(int i=0; i<discardAmt; i++)
				    				{
				    			
				    					discardPile[sizeOfDiscard]=player.discard(cardsToDiscardInts[i]);
				    	
				    					sizeOfDiscard++;
				    				}
				    			  
				    			deck.fixCards(player.returnHand()); //Remove the nulls
				   				//Give p1 new cards to replace the discarded cards.
				    			for(int i=0; i<discardAmt; i++)
				    			{
				    				player.setCard(deck.deal());    	
				    		
				    			}
				    		}
				    		catch(Exception e)
				    		{
				    			System.out.println("An exception occurred, make sure you are entering numbers of cards to delete");
				    			return;
				    		}
				    		
			    			System.out.println("This is your new hand \n" + player.showHand());

				    }
				   
				   Card[] computerDiscardPile= new Card[52];
				   int[] computerDiscard = computer.playHand(computer.returnHand());
				   int computerSizeOfDiscard = 0;
				   for(int i=0; i < computerDiscard.length -1; i++)
   					{
   			
   					computerDiscardPile[computerSizeOfDiscard]=player.discard(computerDiscard[i]);
   	
   					sizeOfDiscard++;
   					}
   			  
				   deck.fixCards(computer.returnHand()); //Remove the nulls
  				//Give p1 new cards to replace the discarded cards.
				   for(int i=0; i<discardAmt; i++)
				   {
					   computer.setCard(deck.deal());    	
   		
				   	}
				   System.out.println("The computer has traded " + computerDiscard.length + " card(s).");
				   round2 = false;
				}
				
			boolean round3 = true;
			while(round3) {
				System.out.println("It is your turn, would you like to fold or bet? enter F to fold or B to bet.");
				String playerChoice = sc.nextLine();
				
				if(playerChoice.equalsIgnoreCase("F")) {
					System.out.println("\nYou folded, the computer wins the game.");
					computer.increase(kitty.payout());
					
					System.out.println("Would you like to play another game? Y or N");
					String choice = sc.next();
					if(choice.equalsIgnoreCase("y"))
						keepGoing = true;
					else if(choice.equalsIgnoreCase("n")) {
						System.out.println("Thanks for playing!");
						keepGoing = false;
						round3 = false;
						break;
					}
					else
						System.out.println("Please enter a valid response, Y or N");
					
				}
				
				else if(playerChoice.equalsIgnoreCase("B")) {
					boolean bet = true;
					while(bet) {
					System.out.println("Would you like to go all in, raise, or match? Enter A to go all in, R to raise, and M to match");
					String betChoice = sc.nextLine();
					
					if(betChoice.equalsIgnoreCase("a")) {
						kitty.update(player.getMoney());
						player.deduct(player.getMoney());
						System.out.println("You have decided to go all in, the Kitty is now " + kitty.getTotal());
						round3 = false;
						bet = false;
					}
					
					else if(betChoice.equalsIgnoreCase("r")) {
					System.out.println("Enter amount to raise");
					playerBet = sc.nextDouble();
					boolean coverBet = player.canCoverBet(playerBet);
					
					if(coverBet) {
						
						player.deduct(playerBet);
						kitty.update(playerBet);
						System.out.println("The Kitty is now " + kitty.getTotal());
						round3 = false;
						bet = false;
						}
					else if(coverBet == false) {
						System.out.println("You can not afford this bet, please enter something under your balance of " + player.getMoney());
						}
					else 
						System.out.println("Please enter a valid number");
						}
					
					else if(betChoice.equalsIgnoreCase("m")) {
						kitty.update(computerBet);
						player.deduct(computerBet);
						System.out.println("You have decided to match, the Kitty is now " + kitty.getTotal());
						round3 = false;
						bet = false;
					}
					else {
						System.out.println("Please enter A or R");
					}
				}
			}
				
			}
			
			boolean round4 = true;
			while(round4) {
				System.out.println("It is the computer's turn");
				double compBal = computer.getMoney();
				double computerChoice = computer.computerBet(computer.returnHand(), compBal, kitty.getTotal());
				
				if(computerChoice == 0) {
					System.out.println("\nThe computer folded, you win the game!");
					computer.increase(kitty.payout());
					
					System.out.println("Would you like to play another round? Y or N");
					String choice = sc.next();
					if(choice.equalsIgnoreCase("y"))
						keepGoing = true;
					else if(choice.equalsIgnoreCase("n")) {
						System.out.println("Thanks for playing!");
						keepGoing = false;
						round4 = false;
						break;
					}
					else
						System.out.println("Please enter a valid response, Y or N");
					
				}
				
				else {
					if(computerChoice == 1) {
						kitty.update(playerBet);
						System.out.println("The computer has decided to match\nThe Kitty is now " + kitty.getTotal());
						round4 = false;
					}
					
					if(computerChoice == 3) {
						kitty.update(computer.getMoney());
						System.out.println("The computer has decided to go all in\nThe Kitty is now " + kitty.getTotal());
						round4 = false;

					}
					else {
						kitty.update(computerChoice);
						System.out.println("The computer has decided to raise by " + computerChoice + " the new Kitty is " + kitty.getTotal());
						round4 = false;

				} 
				
				}
			}
			
			int[] pScore = GameRules.scoreCards(player.returnHand());
			int[] cScore = GameRules.scoreCards(computer.returnHand());
			
			boolean pWin = false;
			boolean cWin = false;
			boolean tie = false;
			if(pScore[0] == cScore[0]) { //Tie
				int tieWin = GameRules.breakTie(player.returnHand(), computer.returnHand());
				if(tieWin == 1) 
					pWin = true;
				else if(tieWin == 2)
					cWin = true;
				else
					tie = true;	
			}
			else if(pScore[0] < cScore[0])//Computer wins
				cWin = true;
			else 
				pWin = true;
			
			System.out.println("Time to reveal the cards!");
			System.out.println("Computer's cards:\n" + computer.showHand());
			System.out.println("Your cards:\n" + player.showHand());
			
			if(pWin) { //Player wins game
				System.out.println(player.getName() + ", you win the Kitty of " + kitty.getTotal() + ", congrats!");
				player.increase(kitty.getTotal());
				kitty.setTotal(0);
			}
			
			else if(cWin) { //Computer wins game
				System.out.println("The computer wins the Kitty of " + kitty.getTotal());
				computer.increase(kitty.getTotal());
				kitty.setTotal(0);
			}
			
			else {
				System.out.println("It's a tie!. Both players split the Kitty of " + kitty.getTotal());
				double split = kitty.getTotal() / 2;
				player.increase(split);
				computer.increase(split);
				kitty.setTotal(0);
			}
			
			System.out.println("Would you like to play another game? Y or N");
			String finalChoice = sc.next();
			if(finalChoice.equalsIgnoreCase("y"))
				keepGoing = true;
			else if(finalChoice.equalsIgnoreCase("n")) {
				System.out.println("Thanks for playing!");
				keepGoing = false;
				break;
			}
			else
				System.out.println("Please enter a valid response, Y or N");
			
			}
		System.out.println("Goodbye, thanks for playing!");
			
	}
	
	private static void dealHands() {
		for(int i = 0; i < 5; i++) {
			computer.setCard(deck.deal());
			player.setCard(deck.deal());
		}
	}

}
