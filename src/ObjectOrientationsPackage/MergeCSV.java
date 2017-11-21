package ObjectOrientationsPackage;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class MergeCSV {

	
	/**
	 * This function get all the new CSV (after converting them according to the format)
	 * and merge them to one file
	 * @param paths The list where all file locations are stored 
	 * @return The new String list containing all the info
	 * @throws IOException
	 */
	public static List<String> getMerged(List<Path> paths) throws IOException {
		List<String> mergedLines = new ArrayList<> ();
		for (Path p : paths){
			List<String> lines = Files.readAllLines(p, Charset.forName("UTF-8"));
			if (!lines.isEmpty()) {
				if (mergedLines.isEmpty()) {
					mergedLines.add(lines.get(0)); //add header only once
				}
				mergedLines.addAll(lines.subList(1, lines.size()));
			}
		}
		return mergedLines;
	}

	public MergeCSV() {
		//super();
		// TODO Auto-generated constructor stub
	}
	
}
