package GUI;
import java.io.File;

import java.io.IOException;




public class fileChange extends Thread{
	private long timeStamp; 
	public File file=new File("c:/temp2/prep/tempo.csv");



	private boolean FileUpdated( ) {
		long timeStamp = file.lastModified();
		if( this.timeStamp != timeStamp ) {
			this.timeStamp = timeStamp;
			return true;
		}
		return false;

	}
	public void run() {
		while(true){
			if(FileUpdated()){
				File f = new File("c:/temp2/prep/tempo.csv");
				file=f;



			}

		}
	}

	}
