package ObjectOrientationsPackage;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;


public class ProjectTest {
	
	CSVtoKML junit = new CSVtoKML();
	ReadAndRewriteCSV junit2 = new ReadAndRewriteCSV();
	MergeCSV junit3 = new MergeCSV();
	ConvertToKML junit4 = new ConvertToKML();
	FilterCSV junit5 = new FilterCSV();
	
	
	@Test
	public void returnModTest(){
		String result =junit2.returnMod("c:/temp/file0.csv");
		assertEquals("model=LG-H815", result);
	}
	
	@Test
	public void writeFileTest() throws IOException {
		String expectedCSV="c:/temp2/NewC0.csv";
		String orgCSV="c:/temp/file0.csv";
		String testCSV="c:/temp2/testWriteFile.csv";
		junit2.writeFile(orgCSV, testCSV);
		String line;
		BufferedReader expected = new BufferedReader(new FileReader(expectedCSV));
		BufferedReader newCsv = new BufferedReader(new FileReader(testCSV));
	    while ((line = expected.readLine()) != null) {
	      assertEquals(line, newCsv.readLine());
	    }
		
	}
	
	@Test
	public void getMergedTest() throws IOException {
		String expectedCSV="c:/temp2/merged.csv";
		String orgCSV0="c:/temp2/NewC0.csv";
		String orgCSV1="c:/temp2/NewC1.csv";
		String testCSV="c:/temp2/testGetMerged.csv";
		List<Path> paths=Arrays.asList(Paths.get(orgCSV0),Paths.get(orgCSV1));
		List<String> mergedLines=junit3.getMerged(paths);
		Files.write(Paths.get(testCSV), mergedLines);
		
		String line;
		BufferedReader expected = new BufferedReader(new FileReader(expectedCSV));
		BufferedReader newCsv = new BufferedReader(new FileReader(testCSV));
	    while ((line = expected.readLine()) != null) {
	      assertEquals(line, newCsv.readLine());
	    }
		
	}
	
	@Test
	public void csvToKmlTest() throws IOException {
		String sourceCsv="c:/temp2/merged.csv";
		String expectedKml="c:/temp2/final.kml";
		File newKml=new File("c:/temp2/csvToKmlTest.kml");
		junit4.setKmlLoc(newKml);
		junit4.setCsvFile(sourceCsv);
		junit4.csvToKml();
		
		String line;
		BufferedReader expected = new BufferedReader(new FileReader(expectedKml));
		BufferedReader newFile = new BufferedReader(new FileReader(newKml));
	    while ((line = expected.readLine()) != null) {
	      assertEquals(line, newFile.readLine());
	    }
	}
	
	@Test
	public void filterTest() throws IOException {
		String sourceCsv="c:/temp2/merged.csv";
		String expectedCsv="c:/temp2/filterCSV.csv";
		String testCSV="c:/temp2/TESTfilterCSV.csv";
		junit5.setLastLocation(testCSV);
		junit5.Filter();
		
		String line;
		BufferedReader expected = new BufferedReader(new FileReader(expectedCsv));
		BufferedReader newFile = new BufferedReader(new FileReader(testCSV));
	    while ((line = expected.readLine()) != null) {
	      assertEquals(line, newFile.readLine());
	    }
	}
	

}
