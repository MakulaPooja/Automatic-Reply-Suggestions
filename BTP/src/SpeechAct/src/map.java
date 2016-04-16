package SpeechAct.src;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.io.*;


public class map {

	/**
	 * @param args
	 */
	public static HashMap<String, String> map = new HashMap<String, String>();
	
	public static HashMap<String, Integer> word_freq= new HashMap<String, Integer>();
	
	public static HashMap<Integer, String> wekaToSPact = new HashMap<Integer, String>();
	
	public static HashMap<String, String> initialize_map() throws Exception {
		// TODO Auto-generated method stub
		try{
			//int count = 0;
			String line;
			BufferedReader mapreader = new BufferedReader(new FileReader("speech_acts_map.txt"));
			line = mapreader.readLine();
			while(line != null){
				//map.put(line.split(":")[0],line.split(":")[1]);
				map.put(line.split(":")[0].trim(), line.split(":")[1].trim());
				line = mapreader.readLine();
			}
			mapreader.close();
		}
		catch(Exception e){}
		//System.out.println(map);
		return map;
	}
	
	public static HashMap<String, Integer> make_features(String text)
	{
		try{
			String[] words = text.split(" ");
			System.out.println(words.length);
			int len = words.length;
			for(int ind = 0 ; ind < len - 1 ;  ind ++)
			{
				String word = words[ind].trim(); 
				String[] parts = word.split("\\/");
				//System.out.println(parts.length);
				if( parts.length == 2)
				{
				//	System.out.println("first part is : "+parts[0]+" and the second part is: "+parts[1]);
					if(!parts[1].equals(","))
					{
				//		System.out.println(ind);
				//		System.out.println(parts[0].trim());
						
						if (word_freq.containsKey(parts[0].trim()))
						{
							word_freq.put(parts[0].trim(),word_freq.get(parts[0].trim())+1);
						}
						else
						{
							word_freq.put(parts[0].trim(), 1);
						}
					}
				}			
			}
		}
		catch(Exception e){
			System.out.println(e);
		}
		
		System.out.println();
		return word_freq;
	}
	
	
	public static void show_map(HashMap<String, Integer> map)
	{
		for(Map.Entry<String, Integer> entry : map.entrySet())
		{
			System.out.println(entry.getKey() + " : "+ entry.getValue());
		}
	}
	
	public static TreeMap<String, Integer> SortByValue (HashMap<String, Integer> map) 
	{
		ValueComparator vc =  new ValueComparator(map);
		TreeMap<String,Integer> sortedMap = new TreeMap<String,Integer>(vc);
		sortedMap.putAll(map);
		return sortedMap;
	}

	
	public static void main(String[] args)
	{
		try
		{
			String str = "";
			BufferedReader fp= new BufferedReader(new FileReader("/home/akkisinghpanchaal/Desktop/output.txt"));
			str = fp.readLine().trim();
			System.out.println(str);
			fp.close();
			HashMap<String, Integer> test_map = make_features(str);
			//show_map(test_map);
			
			// sorting the map according to the values
			TreeMap<String, Integer> sortedMap = SortByValue(test_map);  
			double counter=0,top20=sortedMap.size()*(0.2);
			System.out.println(top20);
			for (Map.Entry<String, Integer> entry : sortedMap.entrySet())
			{
			    System.out.println(entry.getKey()+" : "+entry.getValue());
			    Global.file_append("required_files/program_files/features/features.txt", entry.getKey()+" : "+entry.getValue());
			    counter++;
			    if(counter > top20)break;
			}
		}
		catch(Exception e)
		{
			System.out.println("oops");
		}

	}
		
}
