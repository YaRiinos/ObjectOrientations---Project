package ObjectOrientationsPackage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import de.micromata.opengis.kml.v_2_2_0.Document;
import de.micromata.opengis.kml.v_2_2_0.Folder;
import de.micromata.opengis.kml.v_2_2_0.Icon;
import de.micromata.opengis.kml.v_2_2_0.Kml;
import de.micromata.opengis.kml.v_2_2_0.Placemark;
import de.micromata.opengis.kml.v_2_2_0.Style;

/**
 * Converting, sorting and filtering a CSV file to a KML file
 * 
 * @author Yarin
 * 
 * @see <b>CSV:</b> https://en.wikipedia.org/wiki/Comma-separated_values
 *      <P>
 *      <b>KML:</b> https://en.wikipedia.org/wiki/Keyhole_Markup_Language
 *
 * @version 1.0
 */

public class CSVtoKML {

	public static void main(String[] args) throws IOException {

		// Get the list of file in the 'temp' folder and changing them to the
		// format
		File[] fileList = new File("c:/temp").listFiles();

		for (int i = 0; i < fileList.length; i++) {
			String newCsv = "c:/temp2/NewC" + i + ".csv";
			ReadAndRewriteCSV MakeCSV = new ReadAndRewriteCSV("c:/temp/file" + i + ".csv");
			MakeCSV.writeFile(MakeCSV.getCsvFile(), newCsv);
		}

		// Get the new CSV files and merge them to one file
		MergeCSV mergeCSVOp = null;
		List<Path> paths = Arrays.asList(Paths.get("c:/temp2/NewC0.csv"), Paths.get("c:/temp2/NewC1.csv"));
		List<String> mergedLines = mergeCSVOp.getMerged(paths);
		Path target = Paths.get("c:/temp2/merged.csv");
		Files.write(target, mergedLines);

		// Define merge and filtered CSV
		String mergeCSV = "c:/temp2/merged.csv";
		String filterCSV = "c:/temp2/filterCSV.csv";

		// Use filter function [Input, Output]
		// Filter(mergeCSV, filterCSV);
		FilterCSV FilterObj = new FilterCSV();
		FilterObj.Filter();

		// Define the Kml location
		File kmlLoc = new File("c:/temp2/final.kml");

		// Check if filter CSV is exist, if so convert him to KML
		// else convert the merge CSV to KML
		ConvertToKML Converter = new ConvertToKML();
		File f = new File(filterCSV);
		if (f.exists() && !f.isDirectory()) {
			Converter.setCsvFile(filterCSV);
			Converter.csvToKml();
		} else
			Converter.csvToKml();

		System.out.println("Done\n");

		Algo a = new Algo("c:/temp2/merged.csv", "1c:b9:c4:14:33:c8");
		a.firstAlgo();

		Algo2 x = new Algo2();
		x.secondAlgo();

	}// END MAIN

}// END PROGRAM
