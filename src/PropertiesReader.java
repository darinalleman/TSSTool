package src;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;
 
 
public class PropertiesReader {
	String result = "";
	InputStream inputStream;
 
	public String getPropValues() throws IOException {
 
		try {

			Properties prop = new Properties();
			String propFileName = "config.properties";
 
			inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				//TODO: Create the file if it doesn't exist yet
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}
			
			int hrZones[] = new int[5];

			hrZones[0] = Integer.parseInt(prop.getProperty("zone1"));
			hrZones[1] = Integer.parseInt(prop.getProperty("zone2"));
			hrZones[2] = Integer.parseInt(prop.getProperty("zone3"));
			hrZones[3] = Integer.parseInt(prop.getProperty("zone4"));
			hrZones[4] = Integer.parseInt(prop.getProperty("zone5"));
			
			FitTSSFixer.hrZones = hrZones;
			
			FitTSSFixer.FTP = Integer.parseInt(prop.getProperty("ftp"));
			
			result = "Zones = " + hrZones[0] + ", " + hrZones[1] + ", " + hrZones[2]
					+ ", " + hrZones[3] + ", " + hrZones[4] 
					+ "\nFTP = " + FitTSSFixer.FTP;
			System.out.println(result);
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		} finally {
			inputStream.close();
		}
		return result;
	}
}