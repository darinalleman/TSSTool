import java.util.ArrayList;
import java.util.Arrays;
import java.util.OptionalInt;
import java.util.stream.IntStream;

public class TrainingStressScoreCalculator {
	private ArrayList<Integer> heartRates;
	private int[] heartRateZones = {162, 176, 188, 200, 215};
	private int[] timeInZones;
	private int[] zoneEnds;
	private double trainingStressScore;
	
	public TrainingStressScoreCalculator(HeartRateLogger logger){
		this.heartRates = logger.getHeartRates();
		this.zoneEnds = new int[10];
		this.timeInZones = new int[10];
		
		buildHeartRateZones();
	}
	
	public double getTrainingStressScore(){
		if(trainingStressScore == 0){
			calculateScore();
		}
		
		return trainingStressScore;
	}
	public double calculateScore(){
		heartRates.forEach(x -> assignHeartRateZone(x));
		
		trainingStressScore = 0;
		
		trainingStressScore += timeInZones[0] * (20.0 / 3600);
		trainingStressScore += timeInZones[1] * (30.0 / 3600);
		trainingStressScore += timeInZones[2] * (40.0 / 3600);
		trainingStressScore += timeInZones[3] * (50.0 / 3600);
		trainingStressScore += timeInZones[4] * (60.0 / 3600);
		trainingStressScore += timeInZones[5] * (70.0 / 3600);
		trainingStressScore += timeInZones[6] * (80.0 / 3600);
		trainingStressScore += timeInZones[7] * (100.0 / 3600);
		trainingStressScore += timeInZones[8] * (120.0 / 3600);
		trainingStressScore += timeInZones[9] * (140.0 / 3600);
		
		return trainingStressScore;
	}
	
	/**
	 * Assign the heart rate to the proper hear rate zone
	 * 
	 * @param heartRate - the heartRate that will be assigned to a zone.
	 */
	private void assignHeartRateZone(int heartRate) {
		System.out.println("heartRate: " + heartRate);
		// this will find the first value in the zoneEnds array
		// that is greater than or equal to the heartRate.
		OptionalInt boundary = Arrays.stream(zoneEnds)
				.filter(value -> value >= heartRate)
				.findFirst();
		
		//if a value was found...
		if(boundary.isPresent()){
			System.out.println("assignHeartRateZone: " + boundary.getAsInt());
			// find the index in the zoneEnds array
			// of the boundary value found from the 
			// the above stream.
			OptionalInt zoneIndex = IntStream.range(0, zoneEnds.length)
				.filter(i -> zoneEnds[i] == boundary.getAsInt())
				.findFirst();
			
			if(zoneIndex.isPresent()){
				timeInZones[zoneIndex.getAsInt()]++;
			}
		}
	}
	
	/**
	 * Set the end of each heart rate zone as the chart indicates
	 */
	private void buildHeartRateZones() {
		zoneEnds[0] = heartRateZones[0] - 40;
		zoneEnds[1] = heartRateZones[0] - 10;
		zoneEnds[2] = heartRateZones[0];
		zoneEnds[3] = heartRateZones[1] - ((heartRateZones[1] - heartRateZones[0]) / 2);
		zoneEnds[4] = heartRateZones[1];
		zoneEnds[5] = heartRateZones[2];
		zoneEnds[6] = heartRateZones[3];
		zoneEnds[7] = heartRateZones[3] + (heartRateZones[4] - heartRateZones[3]) / 3;
		zoneEnds[8] = heartRateZones[3] + 2 * (heartRateZones[4] - heartRateZones[3]) / 3;
		zoneEnds[9] = heartRateZones[4];
	}
}
