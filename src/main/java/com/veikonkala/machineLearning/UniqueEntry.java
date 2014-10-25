package com.veikonkala.machineLearning;


import java.util.HashMap;
import java.util.Map;

public class UniqueEntry {
	private String id;
	
	private Map<String,Double> entries;
	
	public UniqueEntry (String id){
		this.id=id;
		this.entries=new HashMap<String,Double>();
		
	}
	
	public void addEntry(String key, Double value){
		this.entries.put(key, value);
		
	}
	
	public void addEntries(Map<String,Double> entries){
		this.entries=entries;
	}
	
	public void removeEntry(String id){
		if(this.entries.containsKey(id)){
			System.out.println("removed key:"+id);
			this.entries.remove(id);
		
		//System.out.println("ekiksksk");
		//this.printEntries();
		//System.out.println("ekiksksk end");
		}
	}


	public Map<String,Double> getEntries(){
		return this.entries;
	}
	public String getID(){
		return this.id;
	}
	
	public Double getValue(String id){
		return this.entries.get(id);
	}
	
	public void printEntries(){
		System.out.println("");
		System.out.println("------------------");
		System.out.println("Unique Entry:"+this.id+"  -> Key:Value");
		System.out.println("------------------");
		for(Map.Entry<String, Double> entry: this.entries.entrySet()){
			System.out.println(entry.getKey()+" : "+entry.getValue());
		}
	}

}
