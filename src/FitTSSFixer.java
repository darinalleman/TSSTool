package src;
////////////////////////////////////////////////////////////////////////////////
// The following FIT Protocol software provided may be used with FIT protocol
// devices only and remains the copyrighted property of Dynastream Innovations Inc.
// The software is being provided on an "as-is" basis and as an accommodation,
// and therefore all warranties, representations, or guarantees of any kind
// (whether express, implied or statutory) including, without limitation,
// warranties of merchantability, non-infringement, or fitness for a particular
// purpose, are specifically disclaimed.
//
// Copyright 2015 Dynastream Innovations Inc.
////////////////////////////////////////////////////////////////////////////////
// ****WARNING****  This file is auto-generated!  Do NOT edit this file.
// Profile Version = 15.20Release
// Tag = development-akw-15.20.00-0
////////////////////////////////////////////////////////////////////////////////



import com.garmin.fit.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import javax.swing.JApplet;

public class FitTSSFixer extends JApplet {
	//TODO: clean this all up for information hiding
	public static File inputFile;
	public static File outputFile;
	public static FileInputStream inputStream;
    public static FileOutputStream outputFileStream;
    public static FileEncoder encode;
    public static int averagePower;
    public static int totalMovingTime;
    public static int[] hrZones;
    public static int FTP;
    public static Properties prop;
    static OutputStream configOutput;
    
    public FitTSSFixer()
    {   	
		PropertiesReader pr = new PropertiesReader();
		try {
			pr.getPropValues();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public double calculateTSSFromHR()
    {
    	checkAndReadFile();
    	
		Decode decode = new Decode();
		MesgBroadcaster mesgBroadcaster = new MesgBroadcaster(decode);
		HeartRateLogger hrLogger = new HeartRateLogger();
		
		TrainingStressScoreCalculator calculator = new TrainingStressScoreCalculator(hrLogger);
		HeartRateMessageListener hrMessageListener = new HeartRateMessageListener(hrLogger);
		mesgBroadcaster.addListener(hrMessageListener);
		
		decodeFile(mesgBroadcaster);
		
		int estimatedTSS = (int) calculator.calculateScore();
		System.out.println("tss: " + estimatedTSS);
		return estimatedTSS;
    }
	private void findElapsedTime() 
	{
		Decode decodeForTotalTime = new Decode();
		MesgBroadcaster mesgBroadcaster = new MesgBroadcaster(decodeForTotalTime);
		ListenerDecodeElapsedTime listenerGetSeconds = new ListenerDecodeElapsedTime();
		mesgBroadcaster.addListener((MesgListener) listenerGetSeconds);
		decodeFile(mesgBroadcaster);
	}
	
	public void fileSelected(File inputFile) 
	{
		FitTSSFixer.inputFile = inputFile;
		checkAndReadFile();
		findElapsedTime();
	}

	public double calculatePowerFromTSS(String TSS) {
		findElapsedTime();
		double dblTSS = Double.parseDouble(TSS);
		averagePower = (int) (Math.sqrt(FTP*FTP*dblTSS*36/totalMovingTime));
		try {
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		checkAndReadFile();
		writeDataToOutput();
		System.out.println(averagePower);
		return averagePower;
	}

	public void addPower(String power) {
		averagePower = Integer.parseInt(power);
		checkAndReadFile();
		writeDataToOutput();

	}

	private void writeDataToOutput() 
	{
		setUpOutputFile();
		Decode decodeForExport = new Decode();
		MesgBroadcaster mesgBroadcasterForExport = new MesgBroadcaster(decodeForExport);
	    ListenerEncodePower listenerEncoder = new ListenerEncodePower();
	    mesgBroadcasterForExport.addListener((MesgListener) listenerEncoder);
	    decodeFile(mesgBroadcasterForExport);
	    closeIO();
	}

public static void main(String[] args) throws IOException {

   }

private static void closeIO() {
	try {
         inputStream.close();
      } catch (java.io.IOException e) {
         throw new RuntimeException(e);
      }
      try{
    	  encode.close();
      } catch(FitRuntimeException e) {
    	  System.err.println("Error closing encode");
      }
      try {
		outputFileStream.close();
	} catch (IOException e) {
		e.printStackTrace();
	}
}
private static void decodeFile(MesgBroadcaster mesgBroadcaster) {
	try {
         mesgBroadcaster.run(inputStream);
    } 
	catch (FitRuntimeException e) {
		System.err.print("Exception decoding file: ");
        System.err.println(e.getMessage());
        try {
            inputStream.close();
        } catch (java.io.IOException f) {
        	throw new RuntimeException(f);
        }
	}
}
private static void checkAndReadFile() {
	try {
         inputStream = new FileInputStream(inputFile);
      } catch (java.io.IOException e) {
         throw new RuntimeException("Error opening file");
      }

      checkFileType(inputStream);

      try {
         inputStream = new FileInputStream(inputFile);
      } catch (java.io.IOException e) {
         throw new RuntimeException("Error opening file");
      }
}
private static void setUpOutputFile() {
	try {
		outputFile = new File("adjusted_" + inputFile.getName());
		outputFileStream = new FileOutputStream(outputFile); //create an output file
	  } catch (FileNotFoundException e1) {
		throw new RuntimeException("Error creating new file");
	  }
      try {
         encode = new FileEncoder(outputFile); //set the output file as the encode target
      } catch (FitRuntimeException e) {
         System.err.println("Error opening file .fit");
      }
}
private static void checkFileType(FileInputStream in) {
	try {
         if (!Decode.checkIntegrity((InputStream) in))
            throw new RuntimeException("FIT file integrity failed.");
      }  catch (RuntimeException e) {
         System.err.print("Exception Checking File Integrity: ");
         System.err.println(e.getMessage());
         System.err.println("Trying to continue...");
      }
      finally {
         try {
            in.close();
         } catch (java.io.IOException e) {
            throw new RuntimeException(e);
         }
      }
}	




}
