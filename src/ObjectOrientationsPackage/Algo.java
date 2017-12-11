package ObjectOrientationsPackage;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.*;

public class Algo {

	String fileLoc, MacAddr;

	//Create a constructor
	public Algo() {
		fileLoc = "c:/temp2/merged.csv";
	}

	//Create a constructor with new csv file and MAC address
	public Algo(String newFileLocation, String newMacAdr) {
		fileLoc = newFileLocation;
		MacAddr = newMacAdr;

	}

	////Create a constructor with MAC address
	public Algo(String newMacAdr) {
		MacAddr = newMacAdr;
		fileLoc = "c:/temp2/merged.csv";

	}

	/**
	 * This function get an MAC address and return the assumed location of this MAC
	 * @return Assumed location of this MAC
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public double[] firstAlgo() throws FileNotFoundException, IOException {
		// Array to save the final location
		double[] assumedLocation = new double[3];
		
		double[][] maxMacFreq = new double[4][4];
		Scanner scanner = new Scanner(System.in);

		// If we don't have MAC addr, ask it from user
		if (MacAddr == null) {
			System.out.println("Enter MAC address");
			MacAddr = scanner.nextLine();
		}

		// Delimiter used in CSV file
		String line = "";
		String cvsSplitBy = ",";
		int count = 0;

		try (BufferedReader br = new BufferedReader(new FileReader(fileLoc))) {
			br.readLine(); // this will read the first line

			while ((line = br.readLine()) != null) {
				String[] column = line.split(cvsSplitBy);

				for (int i = 0; i < Integer.parseInt(column[5]) - 1; i++) {
					//reading all the csv file, check if we have the same MAC address,
					//if so write it to maxMacFreq and then sort it by the signal.
					if (count <= 3 && column[7 + i * 4].equals(MacAddr)) {

						maxMacFreq[count][0] = Double.valueOf(column[2]);// Lat
						maxMacFreq[count][1] = Double.valueOf(column[3]);// Lon
						maxMacFreq[count][2] = Double.valueOf(column[4]);// Alt
						maxMacFreq[count][3] = Double.valueOf(column[9 + i * 4]);// Sig
						sort2DArr(maxMacFreq);
						count++;

					}
					
					else if (column[7 + i * 4].equals(MacAddr) && count > 3) {
						if (Double.valueOf(column[9 + i * 4]) > maxMacFreq[3][3]) {
							maxMacFreq[3][0] = Double.valueOf(column[2]);// Lat
							maxMacFreq[3][1] = Double.valueOf(column[3]);// Lon
							maxMacFreq[3][2] = Double.valueOf(column[4]);// Alt
							maxMacFreq[3][3] = Double.valueOf(column[9 + i * 4]);// Sig
							sort2DArr(maxMacFreq);
						}
					}
					
				}

				

			} // End - While next line

		} // End - read

		// Rearrange the max Signal Array and sort it if needed
		int finalCounterArray = 0;
		for (int j = 0; j < 4; j++) {
			if (maxMacFreq[j][0] != 0)
				finalCounterArray++;
		}

		double[][] weight = new double[finalCounterArray][4];
		double[] sumW = new double[4];

		// Calculate the Weight
		for (int i = 0; i < finalCounterArray; i++) {
			weight[i][3] = 1 / (maxMacFreq[i][3] * maxMacFreq[i][3]);
			weight[i][0] = weight[i][3] * maxMacFreq[i][0];// wLat
			weight[i][1] = weight[i][3] * maxMacFreq[i][1];// wLon
			weight[i][2] = weight[i][3] * maxMacFreq[i][2];// wAlt
			sumW[0] += weight[i][0];
			sumW[1] += weight[i][1];
			sumW[2] += weight[i][2];
			sumW[3] += weight[i][3];

		}
		// Calculate the final Location
		assumedLocation[0] = sumW[0] / sumW[3];
		assumedLocation[1] = sumW[1] / sumW[3];
		assumedLocation[2] = sumW[2] / sumW[3];

		 System.out.println(Arrays.toString(assumedLocation));
		return assumedLocation;
	}

	/**
	 * This function get an 2D array and sort it
	 * 
	 * @param arr The array you want to sort    
	 */
	public static void sort2DArr(double arr[][]) {
		Arrays.sort(arr, new Comparator<double[]>() {
			@Override
			public int compare(final double[] entry1, final double[] entry2) {
				final double freq1 = entry1[3];
				final double freq2 = entry2[3];
				return Double.compare(freq1, freq2);
			}
		});
	}

}
