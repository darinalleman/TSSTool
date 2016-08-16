package src;
import com.garmin.fit.Field;
import com.garmin.fit.Mesg;
import com.garmin.fit.MesgListener;
import com.garmin.fit.MesgNum;
import com.garmin.fit.RecordMesg;

public class HeartRateMessageListener implements MesgListener {
	private HeartRateLogger logger;

	public HeartRateMessageListener(HeartRateLogger logger){
		this.logger = logger;
	}
	
	@Override
	public void onMesg(Mesg msg) {
		if (msg.getNum() == MesgNum.RECORD) {
			Field heartRateField = msg.getField(RecordMesg.HeartRateFieldNum);
			
			if (heartRateField != null) {
				int heartRate = heartRateField.getIntegerValue();
				
				logger.log(heartRate);
			}
		}
		
	}
}
