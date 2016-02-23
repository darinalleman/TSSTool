import com.garmin.fit.Factory;
import com.garmin.fit.Field;
import com.garmin.fit.Mesg;
import com.garmin.fit.MesgListener;


public class ListenerEncodePower implements MesgListener {

	public enum Fields {
		   POWER (7, "power"),
		   HEARTRATE (3, "heart_rate");
		   private final int fieldNum;
		   private final String fieldName;
		   Fields(int fieldNum, String fieldName)
		   {
			   this.fieldNum = fieldNum;
			   this.fieldName = fieldName;
		   }
	   }
	@Override
	public void onMesg(Mesg mesg) {
		if (mesg.getNum() == 20)
		{
			if (mesg.getField(Fields.POWER.fieldNum) == null)
			{
				Field powerField = Factory.createField(mesg.getNum(), Fields.POWER.fieldNum);
				powerField.setValue(FitTSSFixer.averagePower);
				mesg.addField(powerField);
			}
		}
		FitTSSFixer.encode.write(mesg);
	}

}
