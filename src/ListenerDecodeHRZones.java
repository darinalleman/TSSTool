import com.garmin.fit.Mesg;
import com.garmin.fit.MesgListener;

/**
 * Decode the fit file and store time in each zone, then calculate TSS based on 
 * http://home.trainingpeaks.com/blog/article/estimating-training-stress-score-tss
 * @author Darin Alleman
 *
 */
public class ListenerDecodeHRZones implements MesgListener {
	int[] hrZones = FitTSSFixer.hrZones;	
	int[] timeInZones = new int[10];
	int[] zoneEnds = new int[10];
	
	/**
	 * This method is called for each record (line) in the fit file. If the file has heart rate data,
	 * then since each record is data from one second, we can tally up the time spent in each training 
	 * zone, broken up into 10 zones from the 5 zones given. Each zone has an estimated Stress score for 
	 * each second spent in that zone, so we can just add all of those scores up at the end.
	 */
	@Override
	public void onMesg(Mesg mesg) {
		//set the heart rate and the end of each zone as the chart indicates 
		//TODO: Calculate the slope of the curve that this makes and use that instead as a TSS multiplier
		zoneEnds[0] = hrZones[0]-40;
		zoneEnds[1] = hrZones[0]-10;
		zoneEnds[2] = hrZones[0];
		zoneEnds[3] = hrZones[1] - ((hrZones[1] - hrZones[0])/2);
		zoneEnds[4] = hrZones[1];
		zoneEnds[5] = hrZones[2];
		zoneEnds[6] = hrZones[3];
		zoneEnds[7] = hrZones[3] + (hrZones[4]-hrZones[3])/3;
		zoneEnds[8] = hrZones[3] + 2*(hrZones[4]-hrZones[3])/3;
		zoneEnds[9] = hrZones[4];
		
		if (mesg.getNum() == 20)
		{
			//field 3 is the HR field
			if (mesg.getField(3) != null)
			{
				//if the heart rate is inbetween one of the zones, increment the number in that 
				//TODO: Maybe try a switch statement block here instead? This code may be unused after I calculate the curve for TSS per zone as the other todo
				int hr = mesg.getField(3).getIntegerValue();
				if (isBetween(hr,0,zoneEnds[0]))
				{
					timeInZones[0]++;
				}
				else if (isBetween(hr,zoneEnds[0],zoneEnds[1]))
				{
					timeInZones[1]++;
				}
				else if (isBetween(hr,zoneEnds[1],zoneEnds[2]))
				{
					timeInZones[2]++;
				}
				else if (isBetween(hr,zoneEnds[2],zoneEnds[3]))
				{
					timeInZones[3]++;
				}
				else if (isBetween(hr,zoneEnds[3],zoneEnds[4]))
				{
					timeInZones[4]++;
				}
				else if (isBetween(hr,zoneEnds[4],zoneEnds[5]))
				{
					timeInZones[5]++;
				}
				else if (isBetween(hr,zoneEnds[5],zoneEnds[6]))
				{
					timeInZones[6]++;
				}
				else if (isBetween(hr,zoneEnds[6],zoneEnds[7]))
				{
					timeInZones[7]++;
				}
				else if (isBetween(hr,zoneEnds[7],zoneEnds[8]))
				{
					timeInZones[8]++;
				}
				else if (isBetween(hr,zoneEnds[8],zoneEnds[9]))
				{
					timeInZones[9]++;
				}
			}
		}
		if (mesg.getNum() == 34)
		{
			//print out each time in zone for debug purposes
			for (int i = 0; i < timeInZones.length; i++)
			{
				System.out.println("Z" + (i+1) + " :" + timeInZones[i]);
			}
					
			double tssTotal = 0;
			//Actually calculate the TSS per second using the chart, and add it to the total
			tssTotal += timeInZones[0]*(20.0/3600);
			tssTotal += timeInZones[1]*(30.0/3600);
			tssTotal += timeInZones[2]*(40.0/3600);
			tssTotal += timeInZones[3]*(50.0/3600);
			tssTotal += timeInZones[4]*(60.0/3600);
			tssTotal += timeInZones[5]*(70.0/3600);
			tssTotal += timeInZones[6]*(80.0/3600);
			tssTotal += timeInZones[7]*(100.0/3600);
			tssTotal += timeInZones[8]*(120.0/3600);
			tssTotal += timeInZones[9]*(140.0/3600);
			
			System.out.println("TSS: " + tssTotal);
			//send the total back to the main method
			FitTSSFixer.estimatedTSS = (int) Math.round(tssTotal);
		}

	}
	/**
	 * Check if the input number (x) is in between or equal to the input upper and lower bound.
	 * @param x - the number to be checked
	 * @param lower - the lower bound
	 * @param upper - the upper bound
	 * @return true if the number is between (or equal to) the bounds, and false if the number is not.
	 */
	public static boolean isBetween(int x, int lower, int upper) {
		  return lower <= x && x <= upper;
		}

}
