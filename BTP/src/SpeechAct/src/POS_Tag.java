package SpeechAct.src;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
public class POS_Tag
{
	public static HashMap<String, Integer> word_freq = new HashMap<String, Integer>();
	public static void main(String[] args) throws IOException 
	{
		String output = "";
		//BufferedWriter outputFile = new BufferedWriter(new FileWriter("/home/akkisinghpanchaal/Desktop/output.txt"));
		// TODO Auto-generated method stub
		RunTagger.tagger = new cmu.arktweetnlp.Tagger();		
		try
		{
			RunTagger.tagger.loadModel("model.20120919");
			//RunTagger.tagger.loadModel("POS_model/model.irc.20121211");
		}catch(Exception e){System.err.println("POS Loading Error:"+e);e.printStackTrace();}
		output = Flatten(RunTagger.run_POS("required_files/training_set/corpus.txt"));
		output = output.toLowerCase();
		System.out.println(output);
		//System.out.println(Flatten(RunTagger.run_POS("/media/akkisinghpanchaal/CA081D76081D62AD/akshay/SEM-VI/CODE MIXING/corpus/annot_codemix.txt")));
		//outputFile.write(output);
		//outputFile.close();
		word_freq = map.make_features(output);
	}
	public static String Flatten(String text)
	{
		//System.out.println("*****"+text);
		String out="";
		
		text=text.replaceAll("\t", "/");
		text=text.replaceAll("\n", " ");

		out=text.trim();
		
		//System.out.println("*****"+out);
		
		return out;
	}
}