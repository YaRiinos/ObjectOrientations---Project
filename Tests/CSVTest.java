package Tests;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class CSVTest {
	CSV.ReadAndRewriteCSV junitCSV = new CSV.ReadAndRewriteCSV();
	CSV.MergeCSV junitCSV2 = new CSV.MergeCSV();
	@Test
	public void returnModTest(){
		String result =junitCSV.returnMod("c:/temp/file0.csv");
		assertEquals("model=LG-H815", result);
	}
	@Test
	public void writeFileTest() throws IOException {
		String expectedCSV="c:/temp2/NewC0.csv";
		String orgCSV="c:/temp/file0.csv";
		String testCSV="c:/temp2/testWriteFile.csv";
		junitCSV.writeFile(orgCSV, testCSV);
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
		List<String> mergedLines=junitCSV2.getMerged(paths);
		Files.write(Paths.get(testCSV), mergedLines);
		
		String line;
		BufferedReader expected = new BufferedReader(new FileReader(expectedCSV));
		BufferedReader newCsv = new BufferedReader(new FileReader(testCSV));
	    while ((line = expected.readLine()) != null) {
	      assertEquals(line, newCsv.readLine());
	    }
		
	}
}
