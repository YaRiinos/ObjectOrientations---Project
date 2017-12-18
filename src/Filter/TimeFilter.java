package Filter;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class TimeFilter extends FilterCSV{

	public void FilterByTime() throws FileNotFoundException, IOException{
		Scanner scanner = new Scanner( System.in );
		String line = "";
		String cvsSplitBy = ",";
		int counter=0;
		String dateInput=null;
		System.out.println("Type the exact date and time: (Format:2017-10-30 18:10:33)");
		dateInput= scanner.nextLine();

		try (BufferedReader br = new BufferedReader(new FileReader(firstLocation))){
			br.readLine(); // this will read the first line
			while ((line = br.readLine()) != null) {
				String[] column = line.split(cvsSplitBy);
				if(column[0].equals(dateInput)) {
					thePrint(column, firstLocation, lastLocation, counter);
					counter++;
				}
			}
		}
	}
}
