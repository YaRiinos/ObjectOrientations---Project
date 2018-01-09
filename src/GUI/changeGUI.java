package GUI;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import CSV.ReadAndRewriteCSV;

public class changeGUI {

	File theFile;

	public changeGUI(File file) {
		super();
		this.theFile = file;
	}

	public static boolean CheckFileDeleted(ArrayList<File> fileList, ArrayList<String> database) throws IOException {
		boolean modified = false;
		ArrayList<Integer> marks = new ArrayList<Integer>();
		ArrayList<File> tempfileList = new ArrayList<File>();
		for (int i = 0; i < fileList.size(); i++) {
			File file = fileList.get(i);
			if (!file.exists())
				modified = true;

			if (modified)
				marks.add(i);
		}

		database.clear();

		for (int i = 0; i < fileList.size(); i++) {
			String line = "";
			String tempFile = "c:/temp2/prep/tempo.csv";
			if (!marks.contains(i)) {
				// Add the file to the file list
				tempfileList.add(fileList.get(i));

				// Check if a file is before a format or after
				BufferedReader bf = new BufferedReader(new FileReader(fileList.get(i)));
				String checkFile = bf.readLine();

				// Read the first word, if it's like the format continue, else make it like the
				// format.
				String[] colS = checkFile.split(",");
				if (colS[0].equals("Time"))
					tempFile = fileList.get(i).getAbsolutePath();

				else// Use format on the file
					ReadAndRewriteCSV.writeFile(fileList.get(i).toString(), tempFile);

				bf.close();

				try (BufferedReader br = new BufferedReader(new FileReader(tempFile))) {

					// Read the first line
					br.readLine();

					while ((line = br.readLine()) != null)
						database.add(line);

				}
			}
		}

		fileList.clear();

		for (int i = 0; i < tempfileList.size(); i++) {
			if (!marks.contains(i))
				fileList.add(tempfileList.get(i));
		}

		return modified;
	}
}
