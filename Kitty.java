public class Kitty {
	private double total;
	
	public void update(double m) {
		total += m;
	}
	
	public double payout() {
		double retVal = total;
		total = 0;
		return retVal;
	}
	
	public double getTotal() {
		return total;
	}
	
	public void setTotal(double t) {
		total = t;
	}

}
