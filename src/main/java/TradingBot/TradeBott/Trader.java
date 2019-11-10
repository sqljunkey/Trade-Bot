package TradingBot.TradeBott;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Trader extends Thread {
	Double bankRoll;
	List<SignalGenerator> signals = new ArrayList<SignalGenerator>();
	List<SignalGenerator> portfolio = new ArrayList<SignalGenerator>();

	public Trader() {
		bankRoll = 1000000.00;

		signals.add(new HiLoStrategy("HiLO", "CRUDE%20OIL%20-%20ELECTRONIC", 0.0002));
		signals.add(new HiLoStrategy("HiLODOW", "DOW%20JONES%20INDUSTRIAL%20FUTURES", 0.0002));
		signals.add(new HiLoStrategy("HiLOGold", "GCG9?", 0.0002));
		signals.add(new HiLoStrategy("HiLOGAZ", "NGH19", 0.0002));
		signals.add(new HiLoStrategy("HiLOCORN", "CH9", 0.0002));
		//signals.add(new ReversalStrategy("Reversal1", "15",15, 0.02));
		signals.add(new TimeOfDayStrategy("Time", "CRUDE%20OIL%20-%20ELECTRONIC", "22:31", "22:57"));
		for (SignalGenerator s : signals) {

			s.start();
		}
	}

	public void run() {

		while (true) {

			try {
				sleep(60000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			for (SignalGenerator signal : signals) {
				signal.updateSignal();
				if (signal.buy) {
					if ((bankRoll-signal.value()) > 0) {
						portfolio.add(signal);

						bankRoll -= signal.value();
					}
				}
				if (signal.sell) {

					Iterator<SignalGenerator> i = portfolio.iterator();
					while (i.hasNext()) {
						SignalGenerator f = i.next();
						if (f.getSignature() == signal.getSignature()) {
							bankRoll += signal.value();
							i.remove();
						}
					}

				}
			}

			Double valueInTrade = 0.0;

			for (SignalGenerator s : signals) {

				for (SignalGenerator v : portfolio) {

					if (v.getSignature().equals(s.getSignature())) {

						valueInTrade += s.value();
					}

				}

			}

			System.out.println("Portfolio: " +( bankRoll + valueInTrade) + " Size: " + portfolio.size());

		}

	}

}
