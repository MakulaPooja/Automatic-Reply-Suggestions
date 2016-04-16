package SpeechAct.src;


//Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
//Jad home page: http://www.kpdus.com/jad.html
//Decompiler options: packimports(3) 
//Source File Name:   RunTagger.java

import cmu.arktweetnlp.Tagger;
import cmu.arktweetnlp.Twokenize;
import cmu.arktweetnlp.impl.ModelSentence;
import cmu.arktweetnlp.impl.Sentence;
import cmu.arktweetnlp.util.BasicFileIO;
import java.io.*;

import edu.stanford.nlp.io.EncodingPrintWriter.out;



//Referenced classes of package cmu.arktweetnlp:
//       Tagger, Twokenize

public class RunTagger
{
/* member class not found */
class Decoder {}


public static void die(String message)
{
   System.err.println(message);
   System.exit(-1);
}

public RunTagger() throws UnsupportedEncodingException
{
}

public static String runTagger(String inputFilename) throws IOException, ClassNotFoundException
{
	 	StringBuffer out=new StringBuffer();
	 	String output = "";
      //tagger = new Tagger();
      //tagger.loadModel("/cmu/arktweetnlp/model.20120919");
      LineNumberReader reader = new LineNumberReader(BasicFileIO.openFileToReadUTF8(inputFilename));
      String line;
      Sentence sentence;
      while((line = reader.readLine()) != null)
      {
      	//System.out.println("#####"+line);
          String text;
          text =line;
          sentence = new Sentence();
          sentence.tokens = Twokenize.tokenizeRawTweetText(text);
          ModelSentence modelSentence = null;
          if(sentence.T() > 0 )
          {
          	modelSentence = new ModelSentence(sentence.T());
          	//System.out.println("*****"+modelSentence.toString());
              tagger.featureExtractor.computeFeatures(sentence, modelSentence);
              goDecode(modelSentence);
              //System.out.println(outputJustTagging(sentencBufferedWriter outputFile = new BufferedWriter(new FileWriter("/home/akkisinghpanchaal/Desktop/output.txt"));e, modelSentence));
              out.append(outputJustTagging(sentence, modelSentence)+"\n"+System.lineSeparator());
              output += outputJustTagging(sentence, modelSentence)+"\n";
          }
      }
      reader.close();
      //return out.toString();
      return output;
}
public static void goDecode(ModelSentence mSent)
{
   tagger.model.viterbiDecode(mSent);
}
public static String outputJustTagging(Sentence lSent, ModelSentence mSent)
{
	 StringBuffer out=new StringBuffer();
   for(int t = 0; t < lSent.T(); t++)
   {
  	 out.append(lSent.tokens.get(t)+"\t"+tagger.model.labelVocab.name(mSent.labels[t])+"\n");
      /* outputStream.printf("%s\t%s", new Object[] 
      {
           lSent.tokens.get(t), tagger.model.labelVocab.name(mSent.labels[t])
       });
       outputStream.printf("\n", new Object[0]);*/
   }
   //outputStream.println("");
   //System.out.println();
   return out.toString();
}


public static void main(String args[]) throws IOException, ClassNotFoundException
{
	 //RunTagger tagger = new RunTagger();
   //tagger.inputFilename = "lib//intermediate//INPUT.txt";
   System.out.println(runTagger("/media/akkisinghpanchaal/CA081D76081D62AD/akshay/SEM-VI/CODE MIXING/corpus/annot_codemix.txt"));
   //Global.file_update("lib//intermediate//POS_FROM_FILE.txt", runTagger("lib//intermediate//INPUT.txt"));
   
}

public static String run_POS(String input)
{
	 String output="";
	 try
	 {
		 //RunTagger tagger = new RunTagger();
	     output=runTagger(input);
	     
	 }catch(Exception e){System.err.println("Error:"+e);e.printStackTrace();}
	 return output;
}

public static Tagger tagger;
}

