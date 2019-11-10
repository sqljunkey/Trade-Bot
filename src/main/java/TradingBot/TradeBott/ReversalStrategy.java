package TradingBot.TradeBott;

import java.util.Iterator;
import java.util.Queue;
import org.apache.commons.collections4.queue.CircularFifoQueue;

public class ReversalStrategy extends SignalGenerator {

	Queue<Double> fifoPrice = null;
	int limit; 
	Double threshold;

	public ReversalStrategy(String name, String ticker, int limit, Double threshold) {
		super(name, ticker);
		this.limit = limit;
		this.threshold = threshold;
		fifoPrice = new CircularFifoQueue<Double>(limit);

	}

	public static <T> T getLastElement(final Iterable<T> elements) {
		final Iterator<T> itr = elements.iterator();
		T lastElement = itr.next();

		while (itr.hasNext()) {
			lastElement = itr.next();
		}

		return lastElement;
	}

	public static <T> T getFirstElement(final Iterable<T> elements) {
		if (elements == null)
			return null;

		return elements.iterator().next();
	}

	public void updateSignal() {

		Double price = getPrice();
		super.price = price;
		fifoPrice.add(price);
		
		if (fifoPrice.size() == limit) {

		//	System.out.println("ABLE!");
			Double last = getLastElement(fifoPrice);
			Double first= getFirstElement(fifoPrice);
			
			lastChange = Math.log(first/last);
			
			if(lastChange<-threshold) {
				super.buy = true;
				super.sell = false;
				
			}
			else if(lastChange>threshold) {
				super.sell = true;
				super.buy = false;
				
			}
			else {
				
				super.sell = false;
				super.buy = false;
			}
			
			
			
			

		}

	}

}
