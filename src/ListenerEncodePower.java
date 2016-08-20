package src;
import com.garmin.fit.Factory;
import com.garmin.fit.Field;
import com.garmin.fit.Mesg;
import com.garmin.fit.MesgListener;

public class ListenerEncodePower implements MesgListener {

	public enum Fields {
		   POWER (7),
		   HEARTRATE (3);
		   private final int fieldNum;
		   Fields(int fieldNum)
		   {
			   this.fieldNum = fieldNum;
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
