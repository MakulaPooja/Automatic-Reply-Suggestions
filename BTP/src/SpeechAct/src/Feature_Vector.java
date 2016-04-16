package SpeechAct.src;
import java.io.*;
import java.util.*;


public class Feature_Vector {
	

	public static HashMap<String,String> dict=new HashMap<String,String>();
	public static HashMap<String,Integer> list=new HashMap<String,Integer>();
	public static HashMap<String, String> speechActsMap = new HashMap<String, String>();
	public static ArrayList<String> Wh_ques = new ArrayList<String>();
	public static ArrayList<String> sentiWords = new ArrayList<String>();
	public static ArrayList<String> appreciationWords = new ArrayList<String>();
	public static ArrayList<String> apologyWords = new ArrayList<String>();
	public static ArrayList<String> thankWords = new ArrayList<String>();
	

	public static void features()
	{
		int n=1;
		Global.file_append("required_files/program_files/features/Feature_Vector_qm_wh_senti_appre_apology_thank.arff", "@relation Features");
		try
        {
            FileInputStream fis = new FileInputStream("required_files/program_files/features/features_v2.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(fis,"UTF-8"));
            
            FileInputStream fp = new FileInputStream("required_files/program_files/features/wh_features_v1.txt");
            BufferedReader file = new BufferedReader(new InputStreamReader(fp,"UTF-8"));
            
            FileInputStream sp = new FileInputStream("required_files/program_files/features/SentiWords.txt");
            BufferedReader senti = new BufferedReader(new InputStreamReader(sp,"UTF-8"));
            
            FileInputStream ap = new FileInputStream("required_files/program_files/features/Appreciation_Features_final.txt");
            BufferedReader appre = new BufferedReader(new InputStreamReader(ap,"UTF-8"));
            
            FileInputStream sr = new FileInputStream("required_files/program_files/features/Sorry_features.txt");
            BufferedReader sorry = new BufferedReader(new InputStreamReader(sr,"UTF-8"));
            
            FileInputStream th = new FileInputStream("required_files/program_files/features/Thanks_features.txt");
            BufferedReader thank = new BufferedReader(new InputStreamReader(th,"UTF-8"));
            
            String line;
            while((line = br.readLine())!=null)
            {
            	line=line.substring(0,line.indexOf(":")).trim();
            	//System.out.println(line);
            	dict.put(line, "");
				System.out.println(line);
            	Global.file_append("required_files/program_files/features/Feature_Vector_qm_wh_senti_appre_apology_thank.arff", "@ATTRIBUTE word"+n+"  NUMERIC");
			    n++;
            }
            
            Global.file_append("required_files/program_files/features/Feature_Vector_qm_wh_senti_appre_apology_thank.arff", "@ATTRIBUTE word"+ n++ +"  NUMERIC");
            Global.file_append("required_files/program_files/features/Feature_Vector_qm_wh_senti_appre_apology_thank.arff", "@ATTRIBUTE word"+ n++ +"  NUMERIC");
            Global.file_append("required_files/program_files/features/Feature_Vector_qm_wh_senti_appre_apology_thank.arff", "@ATTRIBUTE word"+ n++ +"  NUMERIC");
            Global.file_append("required_files/program_files/features/Feature_Vector_qm_wh_senti_appre_apology_thank.arff", "@ATTRIBUTE word"+ n++ +"  NUMERIC");
            Global.file_append("required_files/program_files/features/Feature_Vector_qm_wh_senti_appre_apology_thank.arff", "@ATTRIBUTE word"+ n++ +"  NUMERIC");
            Global.file_append("required_files/program_files/features/Feature_Vector_qm_wh_senti_appre_apology_thank.arff", "@ATTRIBUTE word"+ n++ +"  NUMERIC");
            
            /*
             * adding the sentiment words into the dictionary
             */
            while((line=senti.readLine()) != null)
            {
            	sentiWords.add(line.trim());
            	System.out.println(line);
            }
            
            while((line = sorry.readLine()) != null)
            {
            	apologyWords.add(line.trim().toLowerCase());
            	System.out.println(line);
            }
            
            /*
             * adding thanking words to the dictionary
             */
            while((line = thank.readLine()) != null)
            {
            	thankWords.add(line.trim().toLowerCase());
            	System.out.println(line);
            }
            
            
            /*
             * adding wh word features into the dictionary
             */
            while((line=file.readLine()) != null)
            {
            	Wh_ques.add(line.trim().toLowerCase());
            	System.out.println(line);
            }
            
            /*
             * adding appreciation words to the dictionary
             */
            while((line = appre.readLine()) != null)
            {
            	appreciationWords.add(line.trim().toLowerCase());
            	System.out.println(line);
            }
            
            fis.close();
            file.close();
            senti.close();
            appre.close();
            sorry.close();
            thank.close();
            
        }catch(IOException f){}    
	}
	
	
	public static void print_map(HashMap<String, String> map)
	{
		for(Map.Entry<String, String> entry : map.entrySet())
		{
			System.out.println(entry.getKey() + " : "+ entry.getValue());
		}
	}
	
	
	public static void main(String[] args) throws Exception
	{
		features();
		
		//load speech acts codes into the map for ease of encoding them into the Featre_Vector.arff
		speechActsMap = map.initialize_map();
		//print_map(speechActsMap);
		Global.file_append("required_files/program_files/features/Feature_Vector_qm_wh_senti_appre_apology_thank.arff", "@ATTRIBUTE class {A,C,F,G,I,L,N,Q,V,AP,AQ}\n");
		Global.file_append("required_files/program_files/features/Feature_Vector_qm_wh_senti_appre_apology_thank.arff", "@Data");
		    	//System.out.println(file);
		        //System.out.println(file.getName());
		    	String row="";
		        try
		        {
		            FileInputStream fis = new FileInputStream("required_files/training_set/annotated/corpus_annotated_v2.txt");
		            BufferedReader br = new BufferedReader(new InputStreamReader(fis,"UTF-8"));
		            String line;
		            int count = 0;
		            while((line = br.readLine())!=null)
		            {
		            	String words[]=line.toLowerCase().trim().split(" ");
		            	System.out.println(line);
		            	list.clear();
		            	row="";
		            	count++;
		            	System.out.println(count);
		            	
		            	for(int i=0;i<words.length;i++)
		            	{
		            			list.put(words[i], 1);
		            	}
		            	System.out.println(words[words.length -1]);
		            	String tag = words[words.length - 1].split("\\_\\[")[1].replace("]", "").trim();
		            	System.out.println("tag : " + tag);
		            	
		            	Map<String, String> treeMap = new TreeMap<String, String>(dict);
						
						for (Map.Entry<String, String> entry : treeMap.entrySet())
						{
						    String x=entry.getKey();
						    if(list.containsKey(x))
						    {
						    	row+="1,";
						    }
						    else
						    {
						    	row+="0,";
						    }
						}
						/*
						 * check the presence of wh words at the starting of the sentence
						 */
						if(Wh_ques.contains(words[0].trim()))
						{
							row += "1,";
						}
						else
						{
							row += "0,";
						}
						
						/*
						 * check if question mark is there at the last of the line
						 */
						//System.out.println("+++++++++++++++++++++"+words[words.length - 2].trim());
						String target = words[words.length - 2].trim();
						
						if(target.contains("?"))
						{
						//	System.out.println("|||||||||\n|||||||||||\n||||||||||||\n||||||||||");
							row += "1,";
						}
						else
						{
							row += "0,";
						}
						
						
						/*
						 * check if any sentiment word is present there
						 */
						boolean found = false;
						for(Map.Entry<String,  Integer> entry : list.entrySet())
						{
							if(sentiWords.contains(entry.getKey()))
							{
								row += "1,";
								found = true;
								break;
							}
						}
						if(!found)
							row += "0,";
						
						
						/*
						 * appreciation features presence probe
						 */
						boolean apprfound = false;
						for(Map.Entry<String,  Integer> entry : list.entrySet())
						{
							if(appreciationWords.contains(entry.getKey()))
							{
								row += "1,";
								apprfound = true;
								break;
							}
						}
						if(!apprfound)
							row += "0,";
						
						/*
						 * checking the presence of apology words
						 */
						boolean sorryfound = false;
						for(Map.Entry<String,  Integer> entry : list.entrySet())
						{
							if(apologyWords.contains(entry.getKey()))
							{
								row += "1,";
								sorryfound = true;
								break;
							}
						}
						if(!sorryfound)
							row += "0,";
						
						/*
						 * checking the presence of the Thanking words
						 */
						boolean thankfound = false;
						for(Map.Entry<String,  Integer> entry : list.entrySet())
						{
							if(thankWords.contains(entry.getKey()))
							{
								row += "1,";
								thankfound = true;
								System.out.println("+++++\n+++++\n+++++");
								//System.exit(0);
								break;
							}
						}
						if(!thankfound){
							row += "0,";
							System.out.println("-------\n-------\n-------");
						}
						
						System.out.println(tag + " : "+speechActsMap.get(tag));
						row += speechActsMap.get(tag);
						Global.file_append("required_files/program_files/features/Feature_Vector_qm_wh_senti_appre_apology_thank.arff", row);
		            }
		            fis.close();
		        }catch(IOException f){} 
		}
}