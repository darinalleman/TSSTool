import java.util.Arrays;
import java.util.OptionalInt;
import java.util.stream.IntStream;

import com.garmin.fit.Mesg;
import com.garmin.fit.MesgListener;

/**
 * Decode the fit file and store time in each zone, then calculate TSS based on
 * http://home.trainingpeaks.com/blog/article/estimating-training-stress-score-
 * tss
 * 
 * @author Darin Alleman
 *
 */
public class ListenerDecodeHRZones implements MesgListener {
	private int[] heartRateZones;
	private int[] timeInZones;
	private int[] zoneEnds;

	public ListenerDecodeHRZones(int[] heartRateZones) {
		this.heartRateZones = heartRateZones;
		this.timeInZones = new int[heartRateZones.length];
		this.zoneEnds = new int[heartRateZones.length];
		
		buildHeartRateZones();
	}

	/**
	 * This method is called for each record (line) in the fit file. If the file
	 * has heart rate data, then since each record is data from one second, we
	 * can tally up the time spent in each training zone, broken up into 10
	 * zones from the 5 zones given. Each zone has an estimated Stress score for
	 * each second spent in that zone, so we can just add all of those scores up
	 * at the end.
	 */
	@Override
	public void onMesg(Mesg mesg) {
		final int heartRateField = 3;

		if (mesg.getNum() == 20) {
			if (mesg.getField(heartRateField) != null) {
				int heartRate = mesg.getField(heartRateField).getIntegerValue();
				
				assignHeartRateZone(heartRate);
			}
		}
		if (mesg.getNum() == 34) {
			// print out each time in zone for debug purposes
			for (int i = 0; i < timeInZones.length; i++) {
				System.out.println("Z" + (i + 1) + " :" + timeInZones[i]);
			}

			double tssTotal = 0;
			// Actually calculate the TSS per second using the chart, and add it
			// to the total
			tssTotal += timeInZones[0] * (20.0 / 3600);
			tssTotal += timeInZones[1] * (30.0 / 3600);
			tssTotal += timeInZones[2] * (40.0 / 3600);
			tssTotal += timeInZones[3] * (50.0 / 3600);
			tssTotal += timeInZones[4] * (60.0 / 3600);
			tssTotal += timeInZones[5] * (70.0 / 3600);
			tssTotal += timeInZones[6] * (80.0 / 3600);
			tssTotal += timeInZones[7] * (100.0 / 3600);
			tssTotal += timeInZones[8] * (120.0 / 3600);
			tssTotal += timeInZones[9] * (140.0 / 3600);

			System.out.println("TSS: " + tssTotal);
			// send the total back to the main method
			FitTSSFixer.estimatedTSS = (int) Math.round(tssTotal);
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

	/**
	 * Assign the heart rate to the proper hear rate zone
	 * 
	 * @param heartRate - the heartRate that will be assigned to a zone.
	 */
	private void assignHeartRateZone(int heartRate) {
		// this will find the first value in the zoneEnds array
		// that is greater than or equal to the heartRate.
		OptionalInt boundary = Arrays.stream(zoneEnds)
				.filter(value -> value >= heartRate)
				.findFirst();
		
		//if a value was found...
		if(boundary.isPresent()){
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
	
		// if the heart rate is in between one of the zones, increment
		// the number in that
		// TODO: Maybe try a switch statement block here instead? This
		// code may be unused after I calculate the curve for TSS per
		// zone as the other to do
//		if (isBetween(heartRate, 0, zoneEnds[0])) {
//			timeInZones[0]++;
//		} else if (isBetween(heartRate, zoneEnds[0], zoneEnds[1])) {
//			timeInZones[1]++;
//		} else if (isBetween(heartRate, zoneEnds[1], zoneEnds[2])) {
//			timeInZones[2]++;
//		} else if (isBetween(heartRate, zoneEnds[2], zoneEnds[3])) {
//			timeInZones[3]++;
//		} else if (isBetween(heartRate, zoneEnds[3], zoneEnds[4])) {
//			timeInZones[4]++;
//		} else if (isBetween(heartRate, zoneEnds[4], zoneEnds[5])) {
//			timeInZones[5]++;
//		} else if (isBetween(heartRate, zoneEnds[5], zoneEnds[6])) {
//			timeInZones[6]++;
//		} else if (isBetween(heartRate, zoneEnds[6], zoneEnds[7])) {
//			timeInZones[7]++;
//		} else if (isBetween(heartRate, zoneEnds[7], zoneEnds[8])) {
//			timeInZones[8]++;
//		} else if (isBetween(heartRate, zoneEnds[8], zoneEnds[9])) {
//			timeInZones[9]++;
//		}
	}

	/**
	 * Check if the input number (x) is in between or equal to the input upper
	 * and lower bound.
	 * 
	 * @param x
	 *            - the number to be checked
	 * @param lower
	 *            - the lower bound
	 * @param upper
	 *            - the upper bound
	 * @return true if the number is between (or equal to) the bounds, and false
	 *         if the number is not.
	 */
	public static boolean isBetween(int x, int lower, int upper) {
		return lower <= x && x <= upper;
	}
}
