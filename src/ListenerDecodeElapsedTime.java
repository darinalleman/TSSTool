package src;
import com.garmin.fit.Mesg;
import com.garmin.fit.MesgListener;


public class ListenerDecodeElapsedTime implements MesgListener {

	@Override
	public void onMesg(Mesg mesg) {
		//34 is the field number for total moving time
		if (mesg.getNum() == 34)
		{
			FitTSSFixer.totalMovingTime = mesg.getField(0).getDoubleValue().intValue();
		}

	}

}
