package GUI;

import java.beans.Statement;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import CSV.ReadAndRewriteCSV;
import KML.ConvertToKML;

public class openFile {

	public openFile() {
		super();
	}

	// Declare Variable
	JFileChooser fileChooser = new JFileChooser();
	StringBuilder sb = new StringBuilder();

	String line = "";
	String cvsSplitBy = ",";
	// String fileName;
	protected String fileName;
	String tempFile = "c:/temp2/prep/tempo.csv";

	// Add one file to the database
	public void pickFile(ArrayList<String> database, ArrayList<File> fileList) throws Exception {

		// Set the dir to C
		fileChooser.setCurrentDirectory(new File("C:\\"));

		if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {

			// get the file
			java.io.File file = fileChooser.getSelectedFile();

			// Check if we have this file
			if (!fileList.contains(file)) {

				// Add the file to the file list
				fileList.add(file);

				// Check if a file is before a format or after
				BufferedReader bf = new BufferedReader(new FileReader(file));
				String checkFile = bf.readLine();

				// Read the first word, if it's like the format continue, else make it like the
				// format.
				String[] colS = checkFile.split(",");
				if (colS[0].equals("Time"))
					tempFile = file.getAbsolutePath();

				else// Use format on the file
					ReadAndRewriteCSV.writeFile(file.toString(), tempFile);

				bf.close();

				try (BufferedReader br = new BufferedReader(new FileReader(tempFile))) {

					// Read the first line
					br.readLine();

					while ((line = br.readLine()) != null)
						database.add(line);

				}
			} else
				JOptionPane.showMessageDialog(null, "The file you were trying to add is already in the database");

		} else {
			sb.append("No file was selected");
		}

	}

	public void pickFolder(ArrayList<String> database, ArrayList<File> fileList) throws Exception {

		// Set the dir to C
		fileChooser.setCurrentDirectory(new File("C:\\"));

		// Set the choosing option to be just directories
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {

			// Add all the files from the folder to array
			File theFolder = fileChooser.getSelectedFile();
			File[] files = theFolder.listFiles();

			for (int i = 0; i < files.length; i++) {

				// Add the file to the file list
				fileList.add(files[i]);

				// Check if a file is before a format or after
				BufferedReader bf = new BufferedReader(new FileReader(files[i]));
				String checkFile = bf.readLine();

				// Read the first word, if it's like the format continue, else make it like the
				// format.
				String[] colS = checkFile.split(",");
				if (colS[0].equals("Time"))
					tempFile = files[i].getAbsolutePath();

				else// Use format on file
					ReadAndRewriteCSV.writeFile(files[i].toString(), tempFile);

				bf.close();

				try (BufferedReader br = new BufferedReader(new FileReader(tempFile))) {

					br.readLine();

					while ((line = br.readLine()) != null)
						database.add(line);

				}
			}
		}

		else {
			sb.append("No folder was selected");
		}
	}

	public void pickSql(ArrayList<String> database) throws Exception {

		String _ip = "5.29.193.52";
		String _url = "jdbc:mysql://" + _ip + ":3306/oop_course_ariel";
		String _user = "oop1";
		String _password = "Lambda1();";
		Connection _con = null;

		java.sql.Statement st = null;
		ResultSet rs = null;
		int max_id = -1;

		try {
			_con = DriverManager.getConnection(_url, _user, _password);
			st = _con.createStatement();
			rs = ((java.sql.Statement) st).executeQuery(
					"SELECT UPDATE_TIME FROM information_schema.tables WHERE TABLE_SCHEMA = 'oop_course_ariel' AND TABLE_NAME = 'ex4_db'");
			if (rs.next()) {
				System.out.println("** Update: " + rs.getString(1));
			}

			PreparedStatement pst = _con.prepareStatement("SELECT * FROM ex4_db");
			rs = pst.executeQuery();
			int ind = 0;
			while (rs.next()) {
				int size = rs.getInt(7);
				int len = 7 + 2 * size;
				String newLine = "";
				for (int i = 2; i <= len; i++) {
					if (i == 8 || i == 9)
						newLine += " ,";
					newLine += rs.getString(i) + ",";

				}
				//System.out.print(newLine);
				database.add(newLine);
				//System.out.println();

				ind++;
			}
		} catch (SQLException ex) {
			Logger lgr = Logger.getLogger(openFile.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					(st).close();
				}
				if (_con != null) {
					_con.close();
				}
			} catch (SQLException ex) {

				Logger lgr = Logger.getLogger(openFile.class.getName());
				lgr.log(Level.WARNING, ex.getMessage(), ex);
			}
		}
		// return max_id;

	}

	public void saveFileCSV(ArrayList<String> database) throws Exception {

		// Delimiter used in CSV file
		String NEW_LINE_SEPARATOR = "\n";
		// CSV file header
		String FILE_HEADER = "Time, ID, Lat, Lon, Alt, #WiFi networks, SSID1, MAC1, Frequncy1, Signal1,"
				+ " SSID2, MAC2, Frequncy2, Signal2," + " SSID3, MAC3, Frequncy3, Signal3,"
				+ " SSID4, MAC4, Frequncy4, Signal4," + " SSID5, MAC5, Frequncy5, Signal5,"
				+ " SSID6, MAC6, Frequncy6, Signal6," + " SSID7, MAC7, Frequncy7, Signal7,"
				+ " SSID8, MAC8, Frequncy8, Signal8," + " SSID9, MAC9, Frequncy9, Signal9,"
				+ " SSID10, MAC10, Frequncy10, Signal10";

		JFileChooser chooser = new JFileChooser();
		// Set the dir to C
		chooser.setCurrentDirectory(new File("C:\\temp2"));

		int retrival = chooser.showSaveDialog(null);
		if (retrival == JFileChooser.APPROVE_OPTION) {
			try (FileWriter fw = new FileWriter(chooser.getSelectedFile() + ".csv")) {

				// Write the header once
				fw.write(FILE_HEADER.toString());
				fw.write(NEW_LINE_SEPARATOR.toString());

				// Write all the database to the file
				for (int i = 0; i < database.size(); i++) {
					fw.write(database.get(i).toString());
					fw.write(NEW_LINE_SEPARATOR.toString());

				}

				fw.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}

			// Show a win that the file has been created
			JOptionPane.showMessageDialog(null, "CSV has been saved", "File Saved", JOptionPane.INFORMATION_MESSAGE);
		}

	}

	public void saveFileKML(ArrayList<String> database) throws Exception {

		// Delimiter used in CSV file
		String NEW_LINE_SEPARATOR = "\n";
		// CSV file header
		String FILE_HEADER = "Time, ID, Lat, Lon, Alt, #WiFi networks, SSID1, MAC1, Frequncy1, Signal1,"
				+ " SSID2, MAC2, Frequncy2, Signal2," + " SSID3, MAC3, Frequncy3, Signal3,"
				+ " SSID4, MAC4, Frequncy4, Signal4," + " SSID5, MAC5, Frequncy5, Signal5,"
				+ " SSID6, MAC6, Frequncy6, Signal6," + " SSID7, MAC7, Frequncy7, Signal7,"
				+ " SSID8, MAC8, Frequncy8, Signal8," + " SSID9, MAC9, Frequncy9, Signal9,"
				+ " SSID10, MAC10, Frequncy10, Signal10";

		JFileChooser chooser = new JFileChooser();
		// Set the dir to C
		chooser.setCurrentDirectory(new File("C:\\temp2"));

		int retrival = chooser.showSaveDialog(null);

		if (retrival == JFileChooser.APPROVE_OPTION) {
			try (FileWriter fw = new FileWriter(chooser.getSelectedFile() + ".csv")) {

				// Get all the database to a CSV file
				fw.write(FILE_HEADER.toString());
				fw.write(NEW_LINE_SEPARATOR.toString());
				System.out.println(database.size());
				for (int i = 0; i < database.size(); i++) {
					System.out.println(database.get(i));
					fw.write(database.get(i).toString());
					fw.write(NEW_LINE_SEPARATOR.toString());
				}

				fw.close();
			} catch (Exception ex) {
				ex.printStackTrace();

			}

			File kmlLoc = new File(chooser.getSelectedFile() + ".kml");

			// Convert the CSV we created to KML
			ConvertToKML converter = new ConvertToKML(chooser.getSelectedFile() + ".csv", kmlLoc);
			converter.csvToKml();

			// Delete the CSV file
			File csvDelete = new File(chooser.getSelectedFile() + ".csv");
			csvDelete.delete();

			// Show a window that the KML has been created
			JOptionPane.showMessageDialog(null, "KML has been saved", "File Saved", JOptionPane.INFORMATION_MESSAGE);
		}

	}

}
