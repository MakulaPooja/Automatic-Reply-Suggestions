package SpeechAct.src;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.TreeMap;
import java.lang.Math;

public class Find_Distribution {
	
	/*
	 * forming up speech act code to speech act name dictionary
	 */
	
	public static HashMap<String, String> speech_acts = new HashMap<String, String>();
	public static HashMap<String, Integer> distribution = new HashMap<String, Integer>();
	public static int lines = 0;
	
	public static TreeMap<String, Integer> SortByValue (HashMap<String, Integer> map) 
	{
		ValueComparator vc =  new ValueComparator(map);
		TreeMap<String,Integer> sortedMap = new TreeMap<String,Integer>(vc);
		sortedMap.putAll(map);
		return sortedMap;
	}
	
	public static void initSpeechActs()
	{
		try{
			String path = "/home/akkisinghpanchaal/workspace/BTP/required_files/speech_acts/speech_acts.txt";
			BufferedReader file = new BufferedReader(new FileReader(path));
			
			String line = "";
			
			while((line = file.readLine()) != null)
			{
				String key = line.split("\t")[0];
				String value = line.split("\t")[1];
				//System.out.println(key + "\t" + value);
				/*
				 * put into the dictionary
				 */
				speech_acts.put(key,value);
			}
		}
		catch(Exception e){
			System.out.println(e);
		}
	}

	public static void main(String[] args) {
	try
	{
		initSpeechActs();
		String file = "/home/akkisinghpanchaal/workspace/BTP/required_files/training_set/annotated/corpus_annotated_v2.txt";
		String outputPath = "/home/akkisinghpanchaal/workspace/BTP/required_files/analysis/distribution_v2.txt";
		BufferedReader corpus = new BufferedReader(new FileReader(file));
		BufferedWriter analysis = new BufferedWriter(new FileWriter(outputPath));
		
		String line = corpus.readLine();
		
		while((line = corpus.readLine()) != null)
		{
			lines++;
        	String tag = line.split("\\_\\[")[1].replace("]", "").trim(); 
        	//System.out.println("tag : " + tag);
        	
        	if(distribution.containsKey(tag.trim()))
        	{
        		distribution.put(tag, distribution.get(tag)+1);
        	}
        	else
        	{
        		distribution.put(tag, 1);
        	}
		}
		
		TreeMap<String, Integer> sortedMap = SortByValue(distribution);  
		System.out.println("Speech Act\t\t\t"+ "Number of Instances" + "\t\t\t" + "Occurance");
		analysis.write("Speech Act\t\t\t"+ "Number of Instances" + "\t\t\t" + "Occurance" + "\n");
		for(HashMap.Entry<String, Integer> entry : sortedMap.entrySet())
		{
			double occurence = (double)((double)entry.getValue()/lines) * 100;
			occurence = (double) Math.round(occurence * 100) / 100;
			System.out.println(speech_acts.get(entry.getKey().trim())+"\t\t\t\t"+entry.getValue() + "\t\t\t" + occurence + "%");
			analysis.write(speech_acts.get(entry.getKey().trim())+"\t\t\t\t"+entry.getValue() + "\t\t\t" + occurence + "%" + "\n");
			//System.out.println(entry.getKey().trim()+"\t\t\t\t"+entry.getValue());
		}
		
		analysis.write("Total number of speech acts used\t" + speech_acts.size()+"\n");
		analysis.write("Total number of lines in the corpus\t" + lines);
		
		corpus.close();
		analysis.close();
	}
	catch(Exception e){}

	}

}
