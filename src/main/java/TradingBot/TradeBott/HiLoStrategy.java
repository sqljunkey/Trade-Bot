package TradingBot.TradeBott;

public class HiLoStrategy extends SignalGenerator {

	
	Double threshold;

	public HiLoStrategy(String name, String ticker, Double threshold) {
		super(name,ticker);
	
		this.threshold = threshold;
	}

	public void updateSignal() {

		Double price = getPrice();
		if (price != 0.0) {
			if(super.price == 0.0) {
				super.price = price;
			}
			lastChange = Math.log(price / super.price);
			super.price = price;
		} else {
			super.lastChange = 0.0;
		}

		if (super.lastChange > threshold) {
			System.out.println("high");
			System.out.println(lastChange);
			System.out.println(threshold);
			super.buy = true;
			super.sell = false;
		}

		else if (super.lastChange < 0) {
			super.sell = true;
			super.buy = false;
			System.out.println("low");
			System.out.println(lastChange);
			System.out.println(threshold);
			
		} else {
			super.sell = false;
			super.buy = false;
			System.out.println("nothing");
			System.out.println(lastChange);
			System.out.println(threshold);
			
		}

	}

}
