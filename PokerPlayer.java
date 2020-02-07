public class PokerPlayer extends Player {
	private double money;
	
	public PokerPlayer(String n, int max, double money) {
		super(n, max);
		this.money = money;
		
	}

	public boolean canCoverBet(double amt) {
		boolean retVal = false;
		if(amt < money)
			retVal = true;
		
		return retVal;
	}
	
	public double deduct(double amt) {
		double retVal = -1;
		if(canCoverBet(amt)) {
			money -= amt;
			retVal = money;
		}
		return retVal;
	}
	
	public void increase(double amt) {
		money += amt;
	}
	
	public double getMoney() {
		return money;
	}

}
