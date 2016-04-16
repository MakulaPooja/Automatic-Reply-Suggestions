package SpeechAct.src;
import java.io.*;
import java.util.*;

public class Create_ARFF 
{
	public static HashMap<String,Integer> list=new HashMap<String,Integer>();
	public static void main(String[] args) 
	{
		//File folder = new File("required_files/training_set/");
		//File[] listOfFiles = folder.listFiles();
		//System.out.print("hi");
		//for (File file : listOfFiles) 
		//{
			//System.out.println(file.getName());
		  //  if (file.isFile()) 
		   // {
		        //System.out.println(file.getName());
		    	//System.out.print("hi");
		    	//System.out.println("/home/pooja/nlp/train/Spam/"+file.getName());
		    	try
		        {
		    		//System.out.print("hi");
		    		FileInputStream fis = new FileInputStream("required_files/training_set/corpus.txt");
		    	//	System.out.println(file.getName());
		            BufferedReader br = new BufferedReader(new InputStreamReader(fis,"UTF-8"));
		            String line;
		            //System.out.print("hi");
		            System.out.println("required_files/training_set/corpus.txt");
		            while((line = br.readLine())!=null)
		            {
		            	line=line.trim();
		            	line=Twokenize.tokenized(line);
		            	//System.out.println(line);
		            	String words[]=line.split(" ");
		            	
		            //	System.out.println("=> "+words[0]+"	"+words[words.length-1]);
		            	for(int i=0;i<words.length-2;i++)
		            	{
		            		if(list.containsKey(words[i]))
		            		{
		            			int k=list.get(words[i]);
		            			System.out.println(words[i]);
		            			k=k+1;
		            			list.put(words[i], k);
		            		}
		            		else
		            		{
		            			list.put(words[i], 1);
		            		}
		            	}
		            }
		            fis.close();
		        }catch(IOException f){}
		
		
		TreeMap<String, Integer> sortedMap = SortByValue(list);  
		double counter=0,top20=sortedMap.size()*(0.2);
		System.out.println(top20);
		for (Map.Entry<String, Integer> entry : sortedMap.entrySet())
		{
		    System.out.println(entry.getKey()+" : "+entry.getValue());
		    Global.file_append("required_files/program_files/pooja.txt", entry.getKey()+" : "+entry.getValue());
		    counter++;
		    if(counter > top20)break;
		}
	}
	public static TreeMap<String, Integer> SortByValue (HashMap<String, Integer> map) 
	{
		ValueComparator vc =  new ValueComparator(map);
		TreeMap<String,Integer> sortedMap = new TreeMap<String,Integer>(vc);
		sortedMap.putAll(map);
		return sortedMap;
	}
}

class ValueComparator implements Comparator<String> 
{
	 
    Map<String, Integer> map;
 
    public ValueComparator(Map<String, Integer> base) {
        this.map = base;
    }
 
    public int compare(String a, String b) {
        if (map.get(a) >= map.get(b)) {
            return -1;
        } else {
            return 1;
        } // returning 0 would merge keys 
    }
}