package test;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

import src.FitTSSFixer;

public class TestTSSCalculator {
	
	/*
	 * Test that a file gets imported correctly
	 */
	@Test
	public void testImport()
	{
		FitTSSFixer app = new FitTSSFixer();
		app.fileSelected(new File("inFile.fit"));
		assertTrue(FitTSSFixer.inputFile != null);
	}
	/*
	 * There should be a runtime exception if the user 
	 * selects a file that isn't a .fit 
	 * (but that shouldn't be possible since the file chooser will filter
	 * and only show .fit files or directories...)
	 */
	@Test(expected=RuntimeException.class)
	public void testImportFailsOnWrongType()
	{
		FitTSSFixer app = new FitTSSFixer();
		app.fileSelected(new File("gobblygook.notafitfile"));
	}
}
