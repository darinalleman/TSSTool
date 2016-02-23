import com.garmin.fit.Field;
import com.garmin.fit.Mesg;
import com.garmin.fit.MesgListener;


public class ListenerDecodeElapsedTime implements MesgListener {

	@Override
	public void onMesg(Mesg mesg) {
		if (mesg.getNum() == 34)
		{
			System.out.println("---------Total Time In Seconds----------");
			System.out.println(mesg.getField(0).getDoubleValue().intValue());
			FitTSSFixer.totalMovingTime = mesg.getField(0).getDoubleValue().intValue();
		}

	}

}
