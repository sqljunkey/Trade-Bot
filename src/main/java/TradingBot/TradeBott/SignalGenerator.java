package TradingBot.TradeBott;





import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class SignalGenerator extends Thread {

	String name;
	String ticker;
	Boolean buy;
	Boolean sell;
	
	
	Double price;
	Double lastChange;
	Double volume;

	public SignalGenerator(String name, String ticker) {
		super();
		this.ticker = ticker;
		this.name = name;
		this.price =100.0;
		this.buy = false;
		this.sell = false;
	}

	public String getSignature() {

		return name;
	}


	public Double value() {

		return price;
	}

	public Double getPrice() {
		Document doc = null;
		Double price = 0.0;


		
	
		try {

		
			doc = Jsoup.connect("https://quotes.wsj.com/futures/" + ticker).get();
			
			Element e = doc.getElementById("quote_val");

			System.out.println(e.text());
			price = Double.parseDouble(e.text());

		} catch (Exception e) {

			e.printStackTrace();
		}
		return price;
	}

	public void updateSignal() {

		System.out.println("Blank Signal");
	};

	Boolean getBuySignal() {

		return buy;
	}

	Boolean getSellSignal() {

		return sell;
	}

	public void run() {

		while (true) {

			try {
				sleep(60000);
				updateSignal();
			} catch (InterruptedException e) {

				e.printStackTrace();
			}

		}
	}
}
