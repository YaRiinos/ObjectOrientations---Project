package Filter;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class LocationFilter extends FilterCSV {
	
	public void FilterByLocation() throws FileNotFoundException, IOException{
		double radius;
		Scanner scanner = new Scanner( System.in );
		String line = "";
		String cvsSplitBy = ",";
		int counter=0;
		String locationInput=null;
		System.out.println("Type the exact Location: (Format:Lat,Lon)");
		locationInput= scanner.nextLine();
		System.out.println("Enter a valid radius: ");
		radius=scanner.nextDouble();
		try (BufferedReader br = new BufferedReader(new FileReader(firstLocation))){
			br.readLine(); // this will read the first line
			while ((line = br.readLine()) != null) {
				String[] column = line.split(cvsSplitBy);
				String[] locationSplit=locationInput.split(",");
				double dis=distFrom(Double.valueOf(locationSplit[0]), Double.valueOf(locationSplit[1]),
						Double.valueOf(column[2]), Double.valueOf(column[3]));
				if(dis<=radius){
					thePrint(column, firstLocation, lastLocation, counter);
					counter++;

				}
			}
		}
	}

}
