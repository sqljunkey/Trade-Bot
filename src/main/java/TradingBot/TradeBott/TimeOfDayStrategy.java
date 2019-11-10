package TradingBot.TradeBott;



import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class TimeOfDayStrategy extends SignalGenerator {

	DateTime startTime;
	DateTime endTime;

	public TimeOfDayStrategy(String name, String ticker, String beginTime, String endTime) {

		super(name, ticker);
		
		DateTimeFormatter dtf =  DateTimeFormat.forPattern("HH:mm");
		try {

			this.startTime = dtf.parseDateTime(beginTime);

			this.endTime = dtf.parseDateTime(endTime);
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public void updateSignal() {

		DateTime nowTime = new DateTime();

		int nowHour =nowTime.getHourOfDay();
		int nowMinute = nowTime.getMinuteOfHour();
		
		int startHour = startTime.getHourOfDay();
		int startMinute = startTime.getMinuteOfHour();

		int endHour = endTime.getHourOfDay();
		int endMinute =endTime.getMinuteOfHour();
		
		super.price = getPrice();
		
		if(startHour<=nowHour && startMinute <=nowMinute
				&& endMinute>= nowMinute && endHour>= nowHour) {
			
		
			
			super.buy = true;
			super.sell = false;
			
			
		}
		else {
			
			super.sell=true;
			super.buy = false;
			
		}
		

	}

}
