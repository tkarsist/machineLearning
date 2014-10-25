package com.veikonkala.machineLearning;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Cluster {
	private List<UniqueEntry> clusterEntries;
	private Map<String,Integer> clusterItems;
	private int id;
	private UniqueEntry clusterCentroid;
	private Map<String,Double> clusterCentroidSum;
	
	public Cluster(){
		this.clusterEntries=new ArrayList<UniqueEntry>();
		this.clusterCentroid=new UniqueEntry("centroid");
		this.clusterCentroidSum=new HashMap<String,Double>();
		this.clusterItems=new HashMap<String,Integer>();
	}
	public Cluster(int id){
		this();
		this.id=id;
	}
	
	public int getID(){
		return this.id;
	}
	public void addClusterEntry(UniqueEntry uniqueEntry){
		this.clusterEntries.add(uniqueEntry);
		this.addToClusterCentroidSum(uniqueEntry);
		this.addClusterItems(uniqueEntry.getEntries());
	}
	
	public List<UniqueEntry> getClusterEntries(){
		return this.clusterEntries;
	}
		
	public UniqueEntry getClusterEntry(String uniqueEntryID){
		UniqueEntry uniqueEntryRet=new UniqueEntry("-null");
		
		for(UniqueEntry uniqueEntry: this.clusterEntries){
			if(uniqueEntry.getID().equals(uniqueEntryID)){
				uniqueEntryRet=uniqueEntry;
			}
		}
		
		return uniqueEntryRet;
	}
	
	public Boolean hasClusterEntry(String uniqueEntryID){
		Boolean hasEntry=false;
		for(UniqueEntry uniqueEntry: this.clusterEntries){
			if(uniqueEntry.getID().equals(uniqueEntryID)){
				hasEntry=true;
			}
		}
		return hasEntry;
	}
	
	public void removeClusterEntry(String id){
		Iterator<UniqueEntry> it = this.clusterEntries.iterator();
		while (it.hasNext()) {
		  UniqueEntry uniqueEntry = it.next();
		  if (uniqueEntry.getID().equals(id)) {
			  this.removeFromClusterCentroidSum(uniqueEntry);
			  this.removeClusterItems(uniqueEntry.getEntries());
			  it.remove();
		  }
		}
	}
	private void addClusterItems(Map<String,Double> items){
		for(String id:items.keySet()){
					
			if(this.clusterItems.containsKey(id)){
				this.clusterItems.put(id, this.clusterItems.get(id)+1);
			}
			else
				this.clusterItems.put(id, 1);
		}
		
	}
	
	private void removeClusterItems(Map<String,Double> items){
		for(String id:items.keySet()){
			if(this.clusterItems.containsKey(id)){
				if(this.clusterItems.get(id)==1){
					this.clusterItems.remove(id);
					
				}
				else{
					this.clusterItems.put(id,this.clusterItems.get(id)-1);
					
				}
			}
		}
		
	}
	
	public void printClusterItems(){
		//System.out.println("huuhaa");
		for(Map.Entry<String, Integer> items: this.clusterItems.entrySet()){
			System.out.println("ClusterItem: "+items.getKey()+" "+items.getValue());
		}
	}

	//public Map<String,Double> calculateClusterSilhoutte(UniqueEntry potentUnique)
	
	public Double calculateGeometricDistance(UniqueEntry potentUnique){
		
		
		
		Double gd=0.0;
		Map<String,Double> uniqMap=potentUnique.getEntries();
		Map<String,Double> centroid=this.clusterCentroid.getEntries();
		for(Map.Entry<String, Double> uniqMapEntry: uniqMap.entrySet()){
					if(centroid.containsKey(uniqMapEntry.getKey())){ //WTF!!!!
					gd+=(centroid.get(uniqMapEntry.getKey())-uniqMapEntry.getValue())*(centroid.get(uniqMapEntry.getKey())-uniqMapEntry.getValue());}
		}
		gd=Math.sqrt(gd);
		//pitaa katsoa jos pitaa olla jotain
		/*if(gd==0.0){
			gd=10000000.0;
		}*/
		return gd;
	}
	
	public static Double calculateGeometricDistance(UniqueEntry uniqueEntry1,UniqueEntry uniqueEntry2){
		double gd=0.0;
		Map<String,Double> uniqMap1=uniqueEntry1.getEntries();
		Map<String,Double> uniqMap2=uniqueEntry2.getEntries();
		for(Map.Entry<String, Double> uniqMapEntry: uniqMap1.entrySet()){
					if(uniqMap2.containsKey(uniqMapEntry.getKey())){ //WTF!!!!
					gd+=(uniqMap2.get(uniqMapEntry.getKey())-uniqMapEntry.getValue())*(uniqMap2.get(uniqMapEntry.getKey())-uniqMapEntry.getValue());}
		}
		gd=Math.sqrt(gd);
		
		return gd;
	}
	
	public Double calculateAverageDissimilarity(UniqueEntry uniqueEntry){
		Double averageDissimilarity=0.0;
		int divider=0;
		for(UniqueEntry clusterEntry: this.clusterEntries){
			//do not calculate distance to itself
			if(!uniqueEntry.getID().equals(clusterEntry.getID())){
				averageDissimilarity+=Cluster.calculateGeometricDistance(uniqueEntry, clusterEntry);
				divider+=1;
			}
		}
		if(divider==0||averageDissimilarity==0.0){
			return 0.0;
		}
		
		return (averageDissimilarity/divider);
				
		
		
	}
	
	public Double calculateClusterDistortion(){
		Double distortion=0.0;
		
		
		return distortion;
	}
	public void addClusterEntryAndMoveCentroid(UniqueEntry uniqueEntry){
		/*
		if(this.clusterEntries.size()<1){
			//this.clusterCentroid=new UniqueEntry("centroid");
			//this.clusterCentroid.addEntries(uniqueEntry.getEntries());
			//this.printCentroid();
			
		}
		else{
			//this.moveClusterCentroid(uniqueEntry,true);
		}
		*/
		
		//this.addClusterItems(uniqueEntry.getEntries());
		//this.addToClusterCentroidSum(uniqueEntry);
		//this.clusterEntries.add(uniqueEntry);
		this.addClusterEntry(uniqueEntry);
		this.calcClusterCentroid();
		
		
		//this.addClusterEntry(uniqueEntry);
		//this.addClusterItem(uniqueEntry.getID());
	}
	
	public void removeClusterEntryAndMoveCentroid(String id){
		
		this.removeClusterEntry(id);
		this.calcClusterCentroid();
		/*
		Iterator<UniqueEntry> it = this.clusterEntries.iterator();
		while (it.hasNext()) {
		  UniqueEntry uniqueEntry = it.next();
		  if (uniqueEntry.getID().equals(id)) {
			  this.moveClusterCentroid(uniqueEntry, false);
			  //this.removeClusterItem(uniqueEntry.getID());
			  this.removeClusterItems(uniqueEntry.getEntries());
			  this.removeFromClusterCentroidSum(uniqueEntry);
			  //this.clusterCentroid.removeEntries(id);
			  it.remove();
		  }
		}
		*/
	}


	/*
	public void moveClusterCentroid(UniqueEntry potentUnique, boolean add){
		Map<String,Double> uniqMap=potentUnique.getEntries();
		Map<String,Double> resultSet=this.clusterCentroid.getEntries();
		
		int clusterSize=this.clusterEntries.size();
		
		if(add){
			for(Map.Entry<String, Double> uniqMapEntry: uniqMap.entrySet()){
				  if (resultSet.containsKey(uniqMapEntry.getKey())) {
					    
					  resultSet.put(uniqMapEntry.getKey(), 
					    		resultSet.get(uniqMapEntry.getKey())
					    		*(1.0*clusterSize/(1.0*clusterSize+1.0))
					    		+uniqMapEntry.getValue()
					    		*(1.0/(1.0*clusterSize+1.0))
					    		);
					  }
					  else{
						  resultSet.put(uniqMapEntry.getKey(), uniqMapEntry.getValue()*(1.0/(clusterSize+1.0)));
						  //System.out.println("Tänne tuli "+this.id+" Avain: "+uniqMapEntry.getKey()+" arvo: "+resultSet.get(uniqMapEntry.getKey()));
						  
					  }
				
			}
		}
		else{ //tähän pitäisi laittaa että lisää muihin arvoihin lisää, koska jos poistuu niin arvot ei ole enää oikein
			List<String> removeList=new ArrayList<String>();
			//System.out.println("Jee");
			for(Map.Entry<String, Double> entry: resultSet.entrySet()){
				if(!uniqMap.containsKey(entry.getKey())){
					resultSet.put(entry.getKey(), entry.getValue()*clusterSize/(clusterSize-1));
				}
			}
			
			for(Map.Entry<String, Double> uniqMapEntry: uniqMap.entrySet()){
				  
				if (resultSet.containsKey(uniqMapEntry.getKey())) {
					  //System.out.println("TAulussa: "+this.clusterItems.get(uniqMapEntry.getKey()));
					  if(this.clusterItems.containsKey(uniqMapEntry.getKey())&&this.clusterItems.get(uniqMapEntry.getKey())!=1){
						  //System.out.println("TAulussa: "+this.clusterItems.get(uniqMapEntry.getKey())+ " "+uniqMapEntry.getKey());
					    resultSet.put(uniqMapEntry.getKey(), 
					    		(
								  resultSet.get(uniqMapEntry.getKey())
								  -(1.0/clusterSize)*uniqMapEntry.getValue()
								)   
								  *1.0*clusterSize/(clusterSize-1.0)
					);
					  }
					  else{
						  removeList.add(uniqMapEntry.getKey());
						  //this.clusterCentroid.removeEntries(uniqMapEntry.getKey());
						  //remove=true;
						  //System.out.println("Discard: "+this.clusterItems.get(uniqMapEntry.getKey())+ " "+uniqMapEntry.getKey());
						  //System.out.println("dadaa"+resultSet.containsKey(uniqMapEntry.getKey()));
					  }
				  }
				  
				  //if(remove){
					 // resultSet.remove(uniqMapEntry.getKey());
					//  remove=false;
				//	  System.out.println("dadaa"+resultSet.containsKey(uniqMapEntry.getKey()));
				  //}
				  
				  
			}
			for(String key: removeList){
				this.clusterCentroid.removeEntry(key);
			}
			
		}
		//System.out.println("Centroidin nimi on: "+this.clusterCentroid.getID());
		this.clusterCentroid.addEntries(resultSet);
		//this.clusterCentroid.printEntries();
		
		
	}
	*/
	
	public void calcClusterCentroid(){
		Map<String,Double> resultSet=new HashMap<String,Double>();

		//calculate the AVG of the values
		int clusterSize=clusterEntries.size();
		
		Iterator it=clusterCentroidSum.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry<String, Double> resultEntry=(Map.Entry) it.next();
			//System.out.println(resultEntry.getKey()+" "+resultEntry.getValue()+" / "+clusterSize);
			resultSet.put(resultEntry.getKey(), resultEntry.getValue()/(1.0*clusterSize));
		}
		this.clusterCentroid.addEntries(resultSet);
				
				
	}
	
	public void addToClusterCentroidSum(UniqueEntry entry){
		
		for(Map.Entry<String, Double> uniqMapEntry: entry.getEntries().entrySet()){
			  if (this.clusterCentroidSum.containsKey(uniqMapEntry.getKey())) {
				  //System.out.println(uniqMapEntry.getKey()+" "+uniqMapEntry.getValue()+" "+this.clusterCentroidSum.get(uniqMapEntry.getKey()));
				  this.clusterCentroidSum.put(uniqMapEntry.getKey(), this.clusterCentroidSum.get(uniqMapEntry.getKey())+uniqMapEntry.getValue());
				  
				  }
			  else{
				  //System.out.println("to map: "+uniqMapEntry.getKey()+" "+uniqMapEntry.getValue());  
				  this.clusterCentroidSum.put(uniqMapEntry.getKey(), uniqMapEntry.getValue());
				  }
			
		}

	
	}
	
	public void removeFromClusterCentroidSum(UniqueEntry entry){
		
		for(Map.Entry<String, Double> uniqMapEntry: entry.getEntries().entrySet()){
			  if (this.clusterCentroidSum.containsKey(uniqMapEntry.getKey())) {
				  //System.out.println(this.clusterCentroidSum.get(uniqMapEntry.getKey())+" - "+uniqMapEntry.getValue());
				  this.clusterCentroidSum.put(uniqMapEntry.getKey(), this.clusterCentroidSum.get(uniqMapEntry.getKey())-uniqMapEntry.getValue());
				  if(this.clusterCentroidSum.get(uniqMapEntry.getKey())<=0.0){
					  //System.out.println("taalla");
					  this.clusterCentroidSum.remove(uniqMapEntry.getKey());
				  }
			  }
			
		}
	}

/*	
	public void calculateClusterCentroid(){
		this.clusterCentroid=new UniqueEntry("centroid");
		Map<String,Double> resultSet=new HashMap<String,Double>();
		
		//Calculate sum of the centroid
		for(UniqueEntry entry: clusterEntries){
			Map<String,Double> uniqMap=new HashMap<String,Double>();
			uniqMap=entry.getEntries();
			
			for(Map.Entry<String, Double> uniqMapEntry: entry.getEntries().entrySet()){
				  if (resultSet.containsKey(uniqMapEntry.getKey())) {
					    resultSet.put(uniqMapEntry.getKey(), resultSet.get(uniqMapEntry.getKey())+uniqMapEntry.getValue());
					  }
					  else{
						  resultSet.put(uniqMapEntry.getKey(), uniqMapEntry.getValue());
					  }
				
			}
			
		}
		//calculate the AVG of the values
		int clusterSize=clusterEntries.size();
		Iterator it=resultSet.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry<String, Double> resultEntry=(Map.Entry) it.next();
			resultSet.put(resultEntry.getKey(), resultEntry.getValue()/clusterSize);
		}
		
		this.clusterCentroid.addEntries(resultSet);
		
		//this.clusterCentroid.printEntries();
		
		

		
	}
	*/
	
	public void printCluster(){
		System.out.println("Clustername: "+this.id+" entries:"+this.clusterEntries.size());
		//this.clusterCentroid.printEntries();
		System.out.println("");
		System.out.println("Members:");
		for(UniqueEntry uniqueEntry:this.clusterEntries){
			//uniqueEntry.printEntries();
			System.out.println(uniqueEntry.getID());
		}
	}
	public void printCentroid(){
		System.out.println("CCENTROID");
		this.clusterCentroid.printEntries();
	}
	
	public UniqueEntry getCentroid(){
		return this.clusterCentroid;
	}
}
