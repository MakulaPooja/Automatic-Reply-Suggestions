package SpeechAct.src;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import weka.classifiers.functions.SMO;
import weka.classifiers.trees.RandomForest;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class Lang_Detect {
	
	public static TreeMap<String,Integer> sorted_map = new TreeMap<String,Integer>();
	public static TreeMap<String,Integer> sorted_map_words = new TreeMap<String,Integer>();
	public static HashMap<String, String> SpeechActsMap = new HashMap<String, String>();
	public static weka.classifiers.Classifier c;
	
    public static void read()
    {
    	try
        {
            //FileInputStream fis = new FileInputStream("Trigram_1000.txt");
    		FileInputStream fis = new FileInputStream("/media/akkisinghpanchaal/CA081D76081D62AD/akshay/SEM-VI/CODE MIXING/Files/Trigram_20.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(fis,"UTF-8"));
            String line;
            while((line = br.readLine())!=null)
            {
            	sorted_map.put(line.trim().substring(0,line.indexOf("\t")).trim(),0);
            }
            fis.close();
        }catch(IOException f){}  
    }
    public static void read_words()
    {
    	int k=0;
    	int counter = 0;
    	try
        {
    		//here give the path of the features.txt that was newly generated
    		FileInputStream fis = new FileInputStream("/home/akkisinghpanchaal/workspace/BTP/required_files/program_files/features/features.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(fis,"UTF-8"));
            String line;
            while((line = br.readLine())!=null)
            {
            	counter++;
            	String words[]=line.split(":");
            	//System.out.println(words[0]);
            	sorted_map_words.put(words[0].toLowerCase().trim(),k);
            	System.out.println(counter + " : " + words[0].toLowerCase().trim());
            }
        
            	System.out.println(counter+" words in "+ sorted_map_words.keySet());
        
            fis.close();
        }catch(IOException f){}  
    }
    
    public static String fetchKey(Map<String, String> map, String value)
    {
    	String key = "";
    	for (Map.Entry<String, String> entry : map.entrySet())
    	{
    		if (entry.getValue().equals(value))
    		{
    			key = entry.getKey();
    			break;
    		}
    	}
    	System.out.println("key = "+key);
    	return key;
    }
    
    public static RandomForest loadModel(String path) throws Exception 
	{

	    /*SMO classifier;

	    FileInputStream fis = new FileInputStream(path);
	    ObjectInputStream ois = new ObjectInputStream(fis);

	    classifier = (SMO) ois.readObject();
	    ois.close();

	    return classifier;*/
    	RandomForest classifier;

	    FileInputStream fis = new FileInputStream(path);
	    ObjectInputStream ois = new ObjectInputStream(fis);
	   // System.out.println(fis + " " + ois);

	    classifier = (RandomForest) ois.readObject();
	    ois.close();

	    return classifier;
	}
    public static String vector_represent(String sentence)
    {
    //	System.out.println("vector_reperesent");
    	String vector="";
    	String[] words = sentence.split(" ");
    	TreeMap<String,Integer> vctr = new TreeMap<String,Integer>();
    	vctr.putAll(sorted_map_words);
    //	System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$ "+sorted_map_words.entrySet().size());
    //	System.out.println("##########################"+sorted_map_words.size());
    //	System.out.println("**************************"+vctr.size());
    //	System.out.println(vctr.keySet());
    //	System.out.println(vctr.values());
    	for (int index = 0; index < words.length; index++)
    	{
    		words[index] = words[index].toLowerCase();
    //		System.out.println("^^^^^^^^^^^^^^^^^^^"+words[index]);
    		if (vctr.containsKey(words[index].toLowerCase()))
    		{
    	//			System.out.println("inside if");
    					vctr.put(words[index],1);
    				
    		}
    		else
    		{
    	//		System.out.println("inside else");
    			//		vctr.put(words[index], 0);
    		}
    	}
    	//char[] y=word.toCharArray();
        
        //System.out.println(word+"\t"+tag);

    /*    for(int j=0;j<y.length-2;j++)
        {
       	 	String x=y[j]+""+y[j+1]+""+y[j+2]+"";
       	 	
       	 	if(vctr.containsKey(x))
       	 	{
       	 		int k=vctr.get(x);
       	 		if(k==1)
       	 		{
       	 		}
       	 		else
       	 		{
	       	 		k=k+1;
	       	 		vctr.put(x, k);
       	 		}
       	 	}
        }  */
        int entry_count = 0;
      //  System.out.println("!!!!!!!!!!!!!!!!!!!!!!! "+vctr.entrySet().size());
       // System.out.println("@@@@@@@@@@@@@@@@@@@@@@@"+vctr.keySet().size());
		for (Map.Entry entry : vctr.entrySet()) 
		{
			entry_count++;
		//	System.out.println(entry_count);
			vector+=entry.getValue()+",";
		}
		
		//System.out.println(vector);
		//vector=word+","+vector;

    	return vector;
    }

    public static void initiate() throws Exception
    {	
    //	System.out.println("initiate");
		//read();
	//	System.out.println("read");
		read_words();
	//	System.out.println("read_words");
	   // c = loadModel("/media/akkisinghpanchaal/CA081D76081D62AD/akshay/SEM-VI/CODE MIXING/Files/Model/RF.model"); // loads smo model
		//here give the path of the model which we newly created before dinner i.e., RF_v1.model
	    c = loadModel("/home/akkisinghpanchaal/workspace/BTP/required_files/Models/RF_v1.model");
		//System.out.println(c);
	    //c = loadModel(null);
	 //   System.out.println("loadmodel");
    }
    public static String[] lang_tag(String sentence) throws Exception
    {
    	//System.out.println("lang_tag::::::::::::::::");
    	sentence=Twokenize.tokenized(sentence);
    	
    	String tagged_sentence="";
		int l=0,cntr=0;;
        String words[]=sentence.toLowerCase().split(" ");
		StringBuffer str=new StringBuffer();

        str.append("@relation Language\n");
		

		for (Map.Entry entry : sorted_map_words.entrySet()) 
		{
			//System.out.println(entry.getKey());
			str.append("@ATTRIBUTE WORD"+l+"  NUMERIC\n");
			l++;
		}
		
		//str.append("@ATTRIBUTE WORD_ORI NUMERIC\n");

		str.append("@ATTRIBUTE class        {A,C,P,N,Q,O,G,I,AQ,Y,L,AP,J,F,AI,AE,AF,V,AG}\n");

		str.append("@DATA\n");
        
      /*  for(int i=0;i<words.length;i++)
        {
        	int x=0;
            if(sorted_map_words.containsKey(words[i]))
           	 x=sorted_map_words.get(words[i]);
            
            str.append(vector_represent(words[i])+","+x+",?\n");
        }*/
		
		str.append(vector_represent(sentence)+"?");
		//here give the path of the unlabelled.arff file
        Global.file_update("/media/akkisinghpanchaal/CA081D76081D62AD/akshay/SEM-VI/CODE MIXING/Files/unlabeled.arff",str.toString());
	    Instances unlabeled = new Instances(
                new BufferedReader(//here give the path of the unlabelled.arff
                  new FileReader("/media/akkisinghpanchaal/CA081D76081D62AD/akshay/SEM-VI/CODE MIXING/Files/unlabeled.arff")));

	    // set class attribute
	    unlabeled.setClassIndex(unlabeled.numAttributes() - 1);

	    Instances labeled = new Instances(unlabeled);
	    
	    // label instances
	    String tag="";
	    int ins_count = 0;
	    double wekaOutput;
	    double clsLabel = 0;
	    for (int i = 0; i < unlabeled.numInstances(); i++) 
	    {
	    	ins_count++;
	      clsLabel = c.classifyInstance(unlabeled.instance(i));
	      
	      
	      if(clsLabel==0.0)
	    	  tag="Statement_non_opinion";
	      else if(clsLabel==1.0)
	    	  tag="Statement_opinion";
	      else if(clsLabel==2.0)
	    	  tag="Declarative_Yes_No_Question";
	      else if(clsLabel==3.0)
	    	  tag="Response_Acknowledgement";
	      else if(clsLabel==4.0){
	    	  //System.out.println("beee");
	      	  tag="Other";}
	      else if(clsLabel==5.0)
	    	  tag="Hedge";
	      else if(clsLabel==6.0)
	    	  tag="Yes_No_Question";
	      else if(clsLabel==7.0)
	    	  tag="Yes_answers";
	      else if(clsLabel==8.0)
	    	  tag="Thanking";
	      else if(clsLabel==9.0)
	    	  tag="Open_Question";
	      else if(clsLabel==10.0)
	    	  tag="Wh_question";
	      else if(clsLabel==11.0)
	    	  tag="Apology";
	      else if(clsLabel==12.0)
	    	  tag="Conventional_closing";
	      else if(clsLabel==13.0)
	    	  tag="Appreciation";
	      else if(clsLabel==14.0)
	    	  tag="3rd_Party_Talk";
	      else if(clsLabel==15.0)
	    	  tag="Other_answers";
	      else if(clsLabel==16.0)
	    	  tag="Conventional_opening";
	      else if(clsLabel==17.0)
	    	  tag="Action_derective";
	      else if(clsLabel==18.0)
	    	  tag="Or_clause";
	      
	      //System.out.println(words[i]+"/"+tag);
	      
	      //if(words[i].contains(".") || )
	      /*Pattern pat = Pattern.compile("[:?!@#$%^&*().]");
	      Matcher m = pat.matcher(words[i]);
	      if (m.find()) 
	      {
		      tagged_sentence+=words[i]+"/"+"univ ";
	      }
	      else
	      {
	    	  tagged_sentence+=words[i]+"/"+tag+" ";
	      }*/
	      //labeled.instance(i).setClassValue(clsLabel);
	    }
	 //   System.out.println(ins_count);
	    tagged_sentence = tag;
	    System.out.println("clsLabel : " + clsLabel);
    	return new String[] {tagged_sentence.trim(), String.valueOf((int)clsLabel)};
    }
	public static void main(String[] args) throws Exception 
	{
	//	System.out.println("main::::::");
		//System.out.println("main2");
		initiate();
	//	System.out.println("after initialization");
		try{
			// give the path of your result set in the below line
			BufferedWriter resultset = new BufferedWriter(new FileWriter("result.txt"));
			//give the path of your test set in the below line
			BufferedReader testSet = new BufferedReader(new FileReader("testset.txt"));
			String line;
			String prediction;
			String annot_structure = "\\";
			String[] code = {"A","C","P","N","Q","O","G","I","AQ","Y","L","AP","J","F","AI","AE","AF","V","AG"};
			SpeechActsMap = map.initialize_map();
			Feature_Vector.print_map(SpeechActsMap);
			while ((line = testSet.readLine()) != null)
			{
				
				String processed_line= Twokenize.tokenized(line.trim());
				String[] prediction_weka = lang_tag(processed_line);
				prediction = prediction_weka[0];
				System.out.println(prediction);
				resultset.write(line.trim()+" "+annot_structure+prediction+"_["+fetchKey(SpeechActsMap,code[Integer.valueOf(prediction_weka[1])]) + "]\n");
			}
			resultset.close();
			testSet.close();
		}
		
		catch(Exception e){
			System.out.println("done");
		}
		
	}
}
