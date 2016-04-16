package SpeechAct.src;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;

public class Extract_sentiWords {
	
	public static ArrayList<String> sentiWords = new ArrayList<String>();

	public static void main(String[] args) {
			
		String inPath = "required_files/program_files/features/SentiWordNet.txt";
		String outPath = "required_files/program_files/features/SentiWords.txt";
		//String sentiWordPath = "required_files/program_files/features/SentiWordNet_polar.txt";
		
		try{
			
			BufferedReader inFile = new BufferedReader(new FileReader(inPath));
			BufferedWriter outFile = new BufferedWriter(new FileWriter(outPath));
			//BufferedWriter sentiWordNet = new BufferedWriter(new FileWriter(sentiWordPath));
			
			String line = inFile.readLine();
			ArrayList<String> words = new ArrayList<String>();
			while((line = inFile.readLine()) != null)
			{
				String[] parts = line.trim().split("\t");
				if ((parts[2].trim().equals("0")) && (parts[3].trim().equals("0"))){
					//System.out.println("==========neutral word=========");
					//System.out.println(parts[2] + "\t"+ parts[3]);
				}
				else
				{
					words.add(parts[4].trim());
					System.out.println(parts[2] + "\t"+ parts[3] + "\t" + parts[4].trim());
					
					String[] sentiWordCollectionRough = parts[4].trim().split(" ");
					
					for(int i = 0; i < sentiWordCollectionRough.length; i++)
					{
						sentiWordCollectionRough[i] = sentiWordCollectionRough[i].split("#")[0];
						sentiWordCollectionRough[i] = sentiWordCollectionRough[i].replace('_', ' ');
						System.out.println(sentiWordCollectionRough[i]);
						
						outFile.write(sentiWordCollectionRough[i] + "\n");
					}
				}
			}
			//System.out.println(words);
			
		}
		catch(Exception e){
			System.out.println(e);
		}

	}

}
