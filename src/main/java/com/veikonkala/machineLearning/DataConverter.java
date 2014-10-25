package com.veikonkala.machineLearning;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;

public class DataConverter {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		readUniqueEntriesFromFile("u.data");
	}
	
	public static void readUniqueEntriesFromFile(String file){
		//the linkedhashmap is not needed here (for testing purposes
    	Map<String,Double> dataSetKeys=new LinkedHashMap<String,Double>();
    	Map<String,Map<String,Double>> dataSet=new LinkedHashMap<String,Map<String,Double>>();
		
    	try {
			BufferedReader inFileReader=new BufferedReader(new FileReader(file));
			String line;
			int counter=0;
			while ((line = inFileReader.readLine()) !=null){
				
				String splitArray[] = line.split("\t");
					
				if(splitArray.length>1){
					
					if(!dataSetKeys.containsKey(splitArray[1].toString())){
						counter+=1;
						dataSetKeys.put(splitArray[1].toString(), 0.0);
						
					}
				}
				

			}
			
			inFileReader.close();
			System.out.println("Added unique entries:"+counter);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
    	for(Map.Entry<String,Double> keyEntry:dataSetKeys.entrySet()){
    		System.out.println(keyEntry.getKey()+" "+keyEntry.getValue());
    	}

    	try {
			BufferedReader inFileReader=new BufferedReader(new FileReader(file));
			String line;
			int counter=0;
			while ((line = inFileReader.readLine()) !=null){
				
				String splitArray[] = line.split("\t");
					
				if(splitArray.length>1){
					
					if(!dataSet.containsKey(splitArray[0].toString())){
						//dataSet.put(splitArray[0].toString(), dataSet.get(splitArray[0].toString()).put(splitArray[1].toString(), Double.parseDouble(splitArray[1].toString())));
						Map<String,Double> dataSetPerEntry=dataSetKeys;
						dataSetPerEntry.put(splitArray[1].toString(), Double.parseDouble(splitArray[2].toString()));
						dataSet.put(splitArray[0].toString(), dataSetPerEntry);
						//counter+=1;
						//dataSetKeys.put(splitArray[1].toString(), 0.0);
						
					}
					else{
						Map<String,Double> dataSetPerEntry=dataSet.get(splitArray[0].toString());
						dataSetPerEntry.put(splitArray[1].toString(), Double.parseDouble(splitArray[2].toString()));
						dataSet.put(splitArray[0].toString(), dataSetPerEntry);
					}
				}
				

			}
			
			inFileReader.close();
			System.out.println("Added unique entries:"+counter);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	try {
			PrintWriter out= new PrintWriter("output.txt");
			out.println("@RELATION MOVIES");
			out.println("@ATTRIBUTE ID STRING");
			for(Map.Entry<String, Double> keys:dataSetKeys.entrySet()){
				out.println("@ATTRIBUTE "+keys.getKey()+" NUMERIC");
			}
			out.println("@DATA");
			
			for(Map.Entry<String, Map<String,Double>> inEntry: dataSet.entrySet()){
				//String output="";
				//output.concat(inEntry.getKey());
				String output=inEntry.getKey();
				//System.out.println("");
				//System.out.print(inEntry.getKey());
				for(Map.Entry<String, Double> uniqs: inEntry.getValue().entrySet()){
					String temp=","+uniqs.getValue().toString();
					//output.concat(temp);
					output=output+temp;
					//System.out.print(","+uniqs.getValue().toString());
					
				}
				//System.out.println(output);
				out.println(output);
				
			}	

			out.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	


    	
    	
/*
		for(Map.Entry<String, Map<String,Double>> inEntry: dataSet.entrySet()){
			UniqueEntry x1=new UniqueEntry(inEntry.getKey());
			for(Map.Entry<String, Double> uniqs: inEntry.getValue().entrySet()){
				x1.addEntry(uniqs.getKey(), uniqs.getValue());
				
				
			}

		}
		*/
	}

}
