//javac -cp .:stanford-corenlp-3.8.0.jar Dependancy.java
//java -cp .:stanford-corenlp-3.8.0.jar:stanford-corenlp-3.8.0-models.jar Dependancy

import edu.stanford.nlp.ling.*;
	import edu.stanford.nlp.ling.CoreAnnotations.LemmaAnnotation;
	import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
	import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
	import edu.stanford.nlp.process.Tokenizer;
	import edu.stanford.nlp.process.DocumentPreprocessor;
	import edu.stanford.nlp.tagger.maxent.MaxentTagger;
	import edu.stanford.nlp.trees.*;
	import edu.stanford.nlp.util.CoreMap;

	import java.io.*;
	import java.nio.channels.Pipe;
	import java.util.*;
	

	//import edu.oswego.cs.dl.util.concurrent.FJTask.Par2;
	import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
	import edu.stanford.nlp.parser.nndep.DependencyParser;
	import edu.stanford.nlp.pipeline.Annotation;
	import edu.stanford.nlp.process.PTBTokenizer;
	 import java.text.BreakIterator;

public class Dependancy {

	public static String neg(String Parsed_text, String word)
	{
		int st=0;
		String first,second;
		 while(st<Parsed_text.length())
		    {
		   int index=Parsed_text.indexOf("neg",st);
		   if(index!=-1)
		   {
			   int bracket=Parsed_text.indexOf("(",index);
			   int hyphen=Parsed_text.indexOf("-",bracket);
			   first=Parsed_text.substring(bracket+1,hyphen);
			   int space=Parsed_text.indexOf(" ",hyphen);
			   hyphen=Parsed_text.indexOf("-",space);
			   second=Parsed_text.substring(space+1,hyphen);
			   //negations
			   if(first.equals(word))
			   	return second;
			   st=hyphen;
		   }
		   else
		   	return " ";

	}
	return "";
}
public static String conj(String Parsed_text, String word)
	{
		int st=0;
		String first,second;
		 while(st<Parsed_text.length())
		    {
		   int index=Parsed_text.indexOf("conj",st);
		   if(index!=-1)
		   {
			   int bracket=Parsed_text.indexOf("(",index);
			   int hyphen=Parsed_text.indexOf("-",bracket);
			   first=Parsed_text.substring(bracket+1,hyphen);
			   int space=Parsed_text.indexOf(" ",hyphen);
			   hyphen=Parsed_text.indexOf("-",space);
			   second=Parsed_text.substring(space+1,hyphen);
			   //negations
			   if(first.equals(word))
			   	return second;
			   st=hyphen;
		   }
		   else
		   	return " ";

	}
	return "";
}
	
		public static void main(String[] args) throws IOException {
			
			
			
			
		// Enter the sample review in String l1
		String l1="Our room was not clean and tidy. Location was soothing. Food served was amazing. The service was slow but appreciable at that price. Checkin facility was tiring and we stood in rows"; 

			Reader reader = new StringReader(l1);
			DocumentPreprocessor dp = new DocumentPreprocessor(reader);
			List<String> Unparsed_Sentences = new ArrayList<String>();

			for (List<HasWord> sentence : dp) {
			   // SentenceUtils not Sentence
			   String sentenceString = SentenceUtils.listToString(sentence);
			   Unparsed_Sentences.add(sentenceString);
			}
			// LinkedList<String> Parsed_Sentences = new LinkedList<String>();
			// Unparsed_Sentences.add(l1.trim());
			 String modelPath = DependencyParser.DEFAULT_MODEL;
			 DependencyParser parser = DependencyParser.loadFromModelFile(modelPath);
		
				HashMap<String, List<String>> keyword = new HashMap<String, List<String>>();//This is one instance of the  map you want to store in the above list.
				HashMap<String, List<String>> ans = new HashMap<String, List<String>>();
				keyword.put("Value",Arrays.asList("value","evaluate", "assess", "estimate", "appraise", "assay"," rate", "price","cost"));
				ans.put("Value",new ArrayList<String>());
				
				keyword.put("Room",Arrays.asList("room","lodging", "quarter","accommodation", " place", "billet", "suite","apartment", "informaldig"," formalabode","board","suite"));
				ans.put("Room",new ArrayList<String>());
		
				keyword.put("Location",Arrays.asList("position", "place", "situation", "site", "locality", "locale", "spot", "whereabouts", "point", "placement", "scene", "setting","area", "environment" ,"bearings","Location", "orientation","venue", "address"));
				ans.put("Location",new ArrayList<String>());
				
				keyword.put("Cleanliness",Arrays.asList("freshness", "purity", "sanitation", "asepsis", "disinfection", "immaculateness", "nattiness", "neatness" ,"spotlessness" ,"spruceness" ,"sterility","tidiness","cleanliness"));
				ans.put("Cleanliness",new ArrayList<String>());
				
				keyword.put("CheckIn",Arrays.asList("arrive","report","sign in","checkin","frontdesk"));
				ans.put("CheckIn",new ArrayList<String>());
				
				keyword.put("Service",Arrays.asList("service","housekeeping","hospitality"));
				ans.put("Service",new ArrayList<String>());
				
				keyword.put("BusinessService",Arrays.asList("business"));
				ans.put("BusinessService",new ArrayList<String>());
				
				keyword.put("Facility",Arrays.asList("facility"));
				ans.put("Facility",new ArrayList<String>());
				
				keyword.put("Guest",Arrays.asList("guest","Backpackers", "Family", "travellers","seniors"));
				ans.put("Guest",new ArrayList<String>());
				
				keyword.put("Meal",Arrays.asList("breakfast","brunch"," dessert","dinner","fare","feast","lunch","picnic","refreshment","snack","supper","table","tea","board","cookout","eatery","spread"));
				ans.put("Meal",new ArrayList<String>());
				
		for(int i=0;i<Unparsed_Sentences.size();i++)
		 {
			 List<MyPair> a=new ArrayList<MyPair>();

		 String text = Unparsed_Sentences.get(i).toString();
		System.out.println(text+'\n');
		 String Parsed_text = "";
		     MaxentTagger tagger = new MaxentTagger("edu/stanford/nlp/models/pos-tagger/english-left3words/english-left3words-distsim.tagger");
		     DocumentPreprocessor tokenizer = new DocumentPreprocessor(new StringReader(text));
		     //ArrayList<TypedDependency> dep_list = new ArrayList<TypedDependency>();
		     for (List<HasWord> sentence : tokenizer) 
		     {
		      List<TaggedWord> tagged = tagger.tagSentence(sentence); 
		      GrammaticalStructure gs = parser.predict(tagged);
		       
		      // Print typed dependencies     
		      Parsed_text = gs.typedDependencies().toString();
		      //dep_list = (ArrayList<TypedDependency>) gs.typedDependencies();
		     }
		     String first,second;
		     //amod
		     int st=0;
		    while(st<Parsed_text.length())
		    {
		   int index=Parsed_text.indexOf("amod",st);
		   if(index!=-1)
		   {
			   int bracket=Parsed_text.indexOf("(",index);
			   int hyphen=Parsed_text.indexOf("-",bracket);
			   first=Parsed_text.substring(bracket+1,hyphen);
			   int space=Parsed_text.indexOf(" ",hyphen);
			   hyphen=Parsed_text.indexOf("-",space);
			   second=Parsed_text.substring(space+1,hyphen);
			    System.out.println("word  "+first+" "+second+"\n");
			   //negations
			   String no=neg(Parsed_text,second);
			   if(no!=" ")
			   	a.add(new MyPair(first,no+" "+second));
			   else
			   	a.add(new MyPair(first,second));
			   //conjuctions
			   String same=conj(Parsed_text,second);
			   if(same!=" ")
			   	a.add(new MyPair(first,same));
			   
			   st=hyphen;
		   }
		   else
			   break;
		    }
		    //acomp
		  st=0;
		    while(st<Parsed_text.length())
		    {
		   int index=Parsed_text.indexOf("acomp",st);
		   if(index!=-1)
		   {
			  int bracket=Parsed_text.indexOf("(",index);
			   int hyphen=Parsed_text.indexOf("-",bracket);
			   first=Parsed_text.substring(bracket+1,hyphen);
			   int space=Parsed_text.indexOf(" ",hyphen);
			   hyphen=Parsed_text.indexOf("-",space);
			   second=Parsed_text.substring(space+1,hyphen);
			   System.out.println("word  "+first+" "+second+"\n");
			   //negations
			   String no=neg(Parsed_text,second);
			   if(no!=" ")
			   	a.add(new MyPair(first,no+" "+second));
			   else
			   	a.add(new MyPair(first,second));
			   //conjuctions
			   String same=conj(Parsed_text,second);
			   if(same!=" ")
			   	a.add(new MyPair(first,same));
			   
			   st=hyphen;
		   }
		   else
			   break;
		    }
		    //nsubj
		    st=0;
		    while(st<Parsed_text.length())
		    {
		   int index=Parsed_text.indexOf("nsubj",st);
		   if(index!=-1)
		   {
			   int bracket=Parsed_text.indexOf("(",index);
			   int hyphen=Parsed_text.indexOf("-",bracket);
			   second=Parsed_text.substring(bracket+1,hyphen);
			   int space=Parsed_text.indexOf(" ",hyphen);
			   hyphen=Parsed_text.indexOf("-",space);
			   first=Parsed_text.substring(space+1,hyphen);
			    System.out.println("word  "+first+" "+second+"\n");
			   //negations
			   String no=neg(Parsed_text,second);
			   if(no!=" ")
			   	a.add(new MyPair(first,no+" "+second));
			   else
			   	a.add(new MyPair(first,second));
			   //conjuctions
			   String same=conj(Parsed_text,second);
			   if(same!=" ")
			   	a.add(new MyPair(first,same));
			   
			   st=hyphen;
		   }
		   else
			   break;
		    }
		   
		   
	     System.out.println("Only Typed dependencies => "+Parsed_text);
//		      System.out.println(Parsed_text);
//		     Parsed_Sentences.add(Parsed_text);
		
	

		

    // prepare the BreakIterator
    BreakIterator wb = BreakIterator.getWordInstance();
    wb.setText(text);
for(MyPair item:a)
    		{
    			System.out.println(item.key+" "+item.value+"\n");
    		  }

    // iterate word by word
    int start = wb.first();
   for (int end = wb.next(); end != BreakIterator.DONE; start = end, end = wb.next()){

        String word = text.substring(start, end);
      
        if (!(word==" ")){
        	
      
        	
        Iterator it = keyword.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry pair = (Map.Entry)it.next();
            List<String>temp=new ArrayList<String>();
            temp=(List<String>) pair.getValue();
       if(temp.contains(word)){
    	   
    		List<String> val= new ArrayList<String>();
    		val=(List<String>)ans.get((String)pair.getKey());
    		 List<String> valnew= new ArrayList<String>();
    		 valnew=val;
    		// System.out.println(word+"=>"+amod_a+" "+amod_b+" "+acomp_a+" "+acomp_b+" "+nsubj_a+" "+nsubj_b+"\n");
    		
    		for(MyPair item:a)
    		{
    			if (item.key.toLowerCase().equals(word.toLowerCase()))
    		          valnew.add(item.value);
    		  }
    		ans.replace((String)pair.getKey(), val,valnew);
         
            }
                     }
                  }
   }
         
     }   
       
   
   System.out.println(l1+'\n');
    Iterator it = ans.entrySet().iterator();
    while (it.hasNext())
    {
        Map.Entry pair = (Map.Entry)it.next();
        System.out.println(pair.getKey() + " = " + pair.getValue());
    }
    
 }

}
