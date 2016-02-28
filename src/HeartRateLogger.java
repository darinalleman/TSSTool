import java.util.ArrayList;

public class HeartRateLogger {
	ArrayList<Integer> heartRates;
	
	public HeartRateLogger(){
		heartRates = new ArrayList<Integer>();
	}
	
	public void log(Integer heartRate){
		heartRates.add(heartRate);
	}
	
	public ArrayList<Integer> getHeartRates(){
		return heartRates;
	}
}
