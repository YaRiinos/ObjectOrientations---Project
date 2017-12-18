package Filter;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FilterCSV {

	String firstLocation, lastLocation;
	
	public FilterCSV() {
		firstLocation="c:/temp2/merged.csv";
		lastLocation="c:/temp2/filterCSV.csv";
	}
	
	public FilterCSV(String newfirstLocation, String newlastLocation) {
		firstLocation=newfirstLocation;
		lastLocation=newlastLocation;
	}
	
	/**
	 * This is the filter function.
	 * The function get an input from the user and according to that using the filter.
	 * @param firstLocation
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public  String Filter() throws FileNotFoundException, IOException {
		Scanner scanner = new Scanner( System.in );
		
		TimeFilter tf = new TimeFilter();
		ModelFilter mf = new ModelFilter();
		LocationFilter lf = new LocationFilter();
		
		//Using the filter
		System.out.println("Do you want to use filter before making the KML file?\nType (Y/N)");
		String input = scanner.nextLine();

		if(input.equals("y")||input.equals("Y")) {

			//get the input from the user
			System.out.println("What are you looking for?\n(1)Time (2)ID (3)Location ");
			input = scanner.nextLine();

			

			//Get time
			if(input.equals("1")) 
				tf.FilterByTime();
			

			//Get ID/Model
			else if(input.equals("2")) 
				mf.FilterByModel();
				

			//Get location
			else if(input.equals("3")) {
				lf.FilterByLocation();
			}

			else
				System.out.println("You didn't put a right number, going back without filtering\n");

		}



		return lastLocation;
	}

	

	/**
	 * This function print the filtered CSV file if the user want to filter.
	 * @param column
	 * @param firstLocation
	 * @param lastLocation
	 * @param counter
	 * @throws IOException
	 */
	public static void thePrint(String column[], String firstLocation, String lastLocation, int counter) throws IOException {
		//Delimiter used in CSV file
		String COMMA_DELIMITER = ",";
		String NEW_LINE_SEPARATOR = "\n";
		//CSV file header
		String FILE_HEADER = "Time, ID, Lat, Lon, Alt,#WiFi networks, SSID, MAC, Frequncy, Signal";
		FileWriter fileWriter = null;


		if(counter<1) {
			try {

				fileWriter = new FileWriter(lastLocation);
				//Write the CSV file header
				fileWriter.append(FILE_HEADER.toString());
				//Add a new line separator after the header
				fileWriter.append(NEW_LINE_SEPARATOR);
				try (BufferedReader br = new BufferedReader(new FileReader(firstLocation))) {
					br.readLine(); // this will read the first line
					fileWriter.append(column[0]);//time
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append(column[1]);//model
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append(column[2]);//lat
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append(column[3]);//lon
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append(column[4]);//alt
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append(column[5]);//wifi#
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append(column[6]);//wifi
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append(column[7]);//MAC
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append(column[8]);//frq
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append(column[9]);//signal
					fileWriter.append(NEW_LINE_SEPARATOR);   
				} 
				System.out.println("Filtered CSV file was created successfully!"); 
			} 

			catch (Exception e) {
				System.out.println("Error in CsvFileWriter!");
				e.printStackTrace();
			}

			finally {
				try {
					fileWriter.flush();
					fileWriter.close();
				}
				catch (IOException e) {
					System.out.println("Error while flushing/closing fileWriter!");
					e.printStackTrace();
				}
			}


		}

		else {//if this is not the first line
			try{
				String filename= "c:/temp2/filterCSV.csv";
				FileWriter fw = new FileWriter(filename,true); 
				fw.write(column[0]);//time
				fw.write(COMMA_DELIMITER);
				fw.write(column[1]);//model
				fw.write(COMMA_DELIMITER);
				fw.write(column[2]);//lat
				fw.write(COMMA_DELIMITER);
				fw.write(column[3]);//lon
				fw.write(COMMA_DELIMITER);
				fw.write(column[4]);//alt
				fw.write(COMMA_DELIMITER);
				fw.write(column[5]);//wifi#
				fw.write(COMMA_DELIMITER);
				fw.write(column[6]);//wifi
				fw.write(COMMA_DELIMITER);
				fw.write(column[7]);//MAC
				fw.write(COMMA_DELIMITER);
				fw.write(column[8]);//frq
				fw.write(COMMA_DELIMITER);
				fw.write(column[9]);//signal
				fw.write(NEW_LINE_SEPARATOR);
				fw.close();
			}
			catch(IOException ioe){
				System.err.println("IOException: " + ioe.getMessage());
			}
		}

	}
	
	public String getFirstLocation() {
		return firstLocation;
	}

	public void setFirstLocation(String firstLocation) {
		this.firstLocation = firstLocation;
	}

	public String getLastLocation() {
		return lastLocation;
	}

	public void setLastLocation(String lastLocation) {
		this.lastLocation = lastLocation;
	}
	
	public static double distFrom(double lat1, double lng1, double lat2, double lng2) {
	    double earthRadius = 3958.75; // miles (or 6371.0 kilometers)
	    double dLat = Math.toRadians(lat2-lat1);
	    double dLng = Math.toRadians(lng2-lng1);
	    double sindLat = Math.sin(dLat / 2);
	    double sindLng = Math.sin(dLng / 2);
	    double a = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)
	            * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2));
	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
	    double dist = earthRadius * c;

	    return dist;
	    }
	
}
