package com.veikonkala.machineLearning;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class KMeans {
	private int ClusterAmount;
	private List<UniqueEntry> uniqueEntryList;
	private List<Cluster> clusterList;
	private int clusterMoves;
	private boolean randomizeClusters;
	private int maxLoops;

	public KMeans(){
		this.ClusterAmount=2;
		this.uniqueEntryList=new ArrayList<UniqueEntry>();
		this.clusterMoves=0;
		this.randomizeClusters=true;
		this.maxLoops=10000;

	}
	public KMeans(int ClusterAmount){
		this();
		this.ClusterAmount=ClusterAmount;
	}

	public void randomizeClusters(boolean randomizeClusters){
		this.randomizeClusters=randomizeClusters;
	}
	public void addUniqueEntries(List<UniqueEntry> uniqueEntryList){
		this.uniqueEntryList=new ArrayList<UniqueEntry>();
		this.uniqueEntryList=uniqueEntryList;

	}

	public void addUniqueEntry(UniqueEntry uniqueEntry){
		this.uniqueEntryList.add(uniqueEntry);

	}

	//this adds the entries one by one, (was like this: but calculates the centroid after each move). Uses shuffle that calculates the new centroid based on the vector
	
	public void generateClusters2(){

		this.clusterList=new ArrayList<Cluster>();

		//luodaan klusterit
		for(int i=0;i<this.ClusterAmount;i++){
			this.clusterList.add(new Cluster(i));
		}

		//shuffle the cluster
		if(this.randomizeClusters){
			Collections.shuffle(this.uniqueEntryList);
		}
		
		//put the first entries one to each cluster and let them be the centroids.
		Iterator it=this.uniqueEntryList.iterator();
		for(Cluster cluster:this.clusterList){
			cluster.addClusterEntryAndMoveCentroid((UniqueEntry)it.next());
		}
		this.printClusters();
		
		this.shuffleClusters2();

		
	}
	
	public void shuffleClusters2(){
		//tee tähän sellainen setti, jossa iteroidaan siis entrylista koko settiin ja sitten päätellään mihinkä clusteriin se kannattaa laittaa
		//toinen versio on se, joka laskee joka välissä sen centroidin uusiksi (joka siirrolla), toinen laittaa vain entry sisään
		
		int clusterMoves=0;
		for(Cluster cluster: this.clusterList){
			cluster.calcClusterCentroid();
		}
		
		for(UniqueEntry uniqueEntry: this.uniqueEntryList){
			int fromCluster=-1;
			int toCluster=-1;
			double minValue=Double.MAX_VALUE;
			
			Map<Integer, Double> distanceFromCentroid= new HashMap<Integer,Double>();
			for(Cluster cluster: this.clusterList){
				
				//if in cluster save the cluster id
				if(cluster.hasClusterEntry(uniqueEntry.getID())){
					fromCluster=cluster.getID();
				}
				//calculate all distances to centroids
				distanceFromCentroid.put(cluster.getID(), cluster.calculateGeometricDistance(uniqueEntry));
				
			}
			for(Map.Entry<Integer, Double> entry:distanceFromCentroid.entrySet()){
				if(minValue>entry.getValue()){
					minValue=entry.getValue();
					toCluster=entry.getKey();
				}
			}
			if(toCluster>-1&&toCluster!=fromCluster){
				clusterMoves+=1;
				if(fromCluster>-1){
					for (Cluster cluster: this.clusterList){
						if(cluster.getID()==fromCluster){
							cluster.removeClusterEntry(uniqueEntry.getID());
						}
					}
				}
				//else
					//System.out.println("to be moved not in any cluster");
				for(Cluster cluster:this.clusterList){
					if(cluster.getID()==toCluster){
						cluster.addClusterEntry(uniqueEntry);
						//System.out.println("Added to cluster: "+toCluster+" entryID: "+uniqueEntry.getID());
					}
				}
			}
			
		}
		if(clusterMoves>0){
			//System.out.println("Shuffle on going: "+clusterMoves+" moves");
			this.shuffleClusters2();
		}
	}
	
	public void generateClusters(){
		this.clusterList=new ArrayList<Cluster>();

		//luodaan klusterit
		for(int i=0;i<this.ClusterAmount;i++){
			this.clusterList.add(new Cluster(i));
		}

		//kuinka monta per klusteri aluksi
		Double entriesPerCluster=(1.0*this.uniqueEntryList.size())/(1.0*this.ClusterAmount);

		//shuffle the cluster
		if(this.randomizeClusters){
			Collections.shuffle(this.uniqueEntryList);
		}
		
		
		//tuupataan eka iteraatio klusteriin siten, etta jaetaan entryt randomisti sisaan
		int entriesInCluster=0;
		Iterator it = clusterList.iterator();
		Cluster cluster=(Cluster)it.next();
		for(UniqueEntry uniqueEntry: this.uniqueEntryList){

			if(entriesInCluster<entriesPerCluster){
				//cluster.addClusterEntryAndMoveCentroid(uniqueEntry);
				cluster.addClusterEntry(uniqueEntry);
				entriesInCluster=entriesInCluster+1;
			}
			else{
				if(it.hasNext()){
					cluster=(Cluster) it.next();
					//cluster.addClusterEntryAndMoveCentroid(uniqueEntry);
					cluster.addClusterEntry(uniqueEntry);
					entriesInCluster=1;

				}
			}

		}
		//this.printClusters();
		//this.calculateClusterCentroids();

		//System.out.println("First centroids");
		//this.printClusterCentroids();
		this.calculateClusterCentroids();
		//System.out.println("Second centroids");
		//this.printClusterCentroids();

		this.shuffleClusters();
	}


	//This adds one by one to cluster and calculates the centroid after the run, uses shuffle that calculates all items

	/*
	public void generateClustersStandard(){
		this.clusterList=new ArrayList<Cluster>();

		//luodaan klusterit
		for(int i=0;i<this.ClusterAmount;i++){
			this.clusterList.add(new Cluster(i));
		}

		//kuinka monta per klusteri aluksi
		Double entriesPerCluster=(1.0*this.uniqueEntryList.size())/(1.0*this.ClusterAmount);

		//tuupataan eka iteraatio klusteriin siten, etta jaetaan entryt randomisti sisaan
		Collections.shuffle(uniqueEntryList);
		int entriesInCluster=0;
		Iterator it = clusterList.iterator();
		Cluster cluster=(Cluster)it.next();
		for(UniqueEntry uniqueEntry: this.uniqueEntryList){
			//System.out.println("laskuri: "+entriesInCluster);
			if(entriesInCluster<entriesPerCluster){
				cluster.addClusterEntry(uniqueEntry);
				entriesInCluster=entriesInCluster+1;
				//System.out.println("Id klusterille - lisays: " + cluster.getID());
				//System.out.println("addiotion");
			}
			else{
				if(it.hasNext()){
					cluster=(Cluster) it.next();
					//System.out.println("Id klusterille: " + cluster.getID());
					cluster.addClusterEntry(uniqueEntry);
					entriesInCluster=1;

				}
			}

		}
		//this.printClusters();
		this.calculateClusterCentroids();
		this.printClusterCentroids();
		//this.shuffleClusters();
		this.shuffleClustersStandard();
	}

	 */

	
	public Double calculateSilhoutte(UniqueEntry uniqueEntry){
		double silhoutte=-2;
		List<Double> bList= new ArrayList<Double>();
		Double a=0.0;
		
		for(Cluster cluster: this.clusterList){
			if(cluster.hasClusterEntry(uniqueEntry.getID())){
				a=cluster.calculateAverageDissimilarity(uniqueEntry);
				//System.out.println("a: "+a);
			}
			else{
				if(cluster.getClusterEntries().size()>0){
					bList.add(cluster.calculateAverageDissimilarity(uniqueEntry));
				}
				//System.out.println("b: "+cluster.calculateAverageDissimilarity(uniqueEntry));
			}
			
		}
		double b=Collections.min(bList);
		silhoutte=(b-a)/Math.max(a, b);
		
		return silhoutte;
	}
	
	public Map<Integer,Double> calculateClusterSilhouttes2(){
		Map<Integer,Double> silhouttes=new HashMap<Integer,Double>();
		Double silhoutteSum=0.0;
		for(Cluster cluster:this.clusterList){
			silhoutteSum=0.0;
			for(UniqueEntry uniqueEntry:cluster.getClusterEntries()){
				//System.out.println("Silhoutte: "+this.calculateSilhoutte(uniqueEntry));
				silhoutteSum+=this.calculateSilhoutte(uniqueEntry);
			}
			//System.out.println("Summaa "+silhoutteSum);
			if(cluster.getClusterEntries().size()>0){
				silhouttes.put(cluster.getID(), (silhoutteSum/cluster.getClusterEntries().size()));
			}
		}
		
		for(Map.Entry<Integer, Double> roska: silhouttes.entrySet()){
			System.out.println("Dissimilaatio Average klusteri:"+roska.getKey()+" "+roska.getValue());
		}

		
		return silhouttes;
	}
	
	public Map<Integer,Double> calculateClusterSilhouttes(){
		Map<Integer,Double> silhouttes=new HashMap<Integer,Double>();
		Map<Integer,Integer> clusterSize=new HashMap<Integer,Integer>();
		
		for(Cluster cluster2:this.clusterList){
			clusterSize.put(cluster2.getID(), cluster2.getClusterEntries().size());
			
		}
		
		for(Cluster cluster:this.clusterList){
			List<UniqueEntry> clusterEntries=cluster.getClusterEntries();
			if(clusterEntries.size()>0){
			Map<Integer,Double> silhouttesSum=new HashMap<Integer,Double>();
			for(UniqueEntry uniqueEntry: clusterEntries){
				if(silhouttesSum.containsKey(cluster.getID())){
					silhouttesSum.put(cluster.getID(), silhouttesSum.get(cluster.getID())+cluster.calculateGeometricDistance(uniqueEntry));	
				}
				else
					silhouttesSum.put(cluster.getID(), cluster.calculateGeometricDistance(uniqueEntry));


				for(Cluster clusterInner:this.clusterList){
					if(clusterInner.getClusterEntries().size()>0){
					if(cluster.getID()!=clusterInner.getID()){
						if(silhouttesSum.containsKey(clusterInner.getID())){
							silhouttesSum.put(clusterInner.getID(), silhouttesSum.get(clusterInner.getID())+clusterInner.calculateGeometricDistance(uniqueEntry));	
						}
						else
							silhouttesSum.put(clusterInner.getID(), clusterInner.calculateGeometricDistance(uniqueEntry));
					}
					}
				}
			}
			double smallestNeighbour=-1000000000;;
			for(Map.Entry<Integer,Double> neighbors: silhouttesSum.entrySet()){
				if(neighbors.getKey()!=cluster.getID()){
					double average=neighbors.getValue()/clusterSize.get(neighbors.getKey());
					System.out.println(average);
					if(smallestNeighbour<average){
						smallestNeighbour=average;
					}
				}
			}
			double a=silhouttesSum.get(cluster.getID())/clusterSize.get(cluster.getID());
			double b=smallestNeighbour;
			System.out.println("a: "+a+" b:"+b);
			silhouttes.put(cluster.getID(),((b-a)/Math.max(a, b)));
			}
			
		}

		for(Map.Entry<Integer, Double> roska: silhouttes.entrySet()){
			System.out.println("Dissimilaatio klusteri:"+roska.getKey()+" "+roska.getValue());
		}
		return silhouttes;

	}

	public void calculateClusterCentroids(){
		for(Cluster cluster:this.clusterList){
			//cluster.calculateClusterCentroid();
			cluster.calcClusterCentroid();
		}

	}

	public void shuffleClusters(){

		this.clusterMoves+=1;
		Map<UniqueEntry,int[]> transferMap=new HashMap<UniqueEntry,int[]>();
		for(Cluster cluster:this.clusterList){
			//cluster.calculateClusterCentroid();
			List<UniqueEntry> clusterEntries=cluster.getClusterEntries();

			for(UniqueEntry uniqueEntry: clusterEntries){
				double curGD=cluster.calculateGeometricDistance(uniqueEntry);
				for(Cluster clusterInner:this.clusterList){
					if(clusterInner.getClusterEntries().size()>0){
					double entryGD=clusterInner.calculateGeometricDistance(uniqueEntry);
					//System.out.println("curgd: "+curGD+" entryGD "+entryGD);

					//tama on vaarin, pitaisi etsia klusteri joka on lahinna, nyt voi tehda monta siirtoa
					if(cluster.getID()!=clusterInner.getID() && curGD>(entryGD)){
						//System.out.println(uniqueEntry.getID()+" to "+ clusterInner.getID()+" curgd: "+curGD+" entryGD "+entryGD);
						curGD=entryGD;
						ArrayList<Integer> temp=new ArrayList<Integer>();
						temp.add(cluster.getID());
						temp.add(clusterInner.getID());
						//tan pitaisi kylla olla sama, koska kyseessa on update arvoon
						transferMap.put(uniqueEntry, new int[]{cluster.getID(),clusterInner.getID()});


					}
					}

				}

			}
		}
		for(Map.Entry<UniqueEntry, int[]> entry: transferMap.entrySet()){
			//System.out.println("Entry: "+entry.getKey().getID()+ " ClusterFrom: "+entry.getValue()[0]+ " ClusterTo: "+entry.getValue()[1]);
			for(Cluster cluster: this.clusterList){
				if(cluster.getID()==entry.getValue()[1]){
					//cluster.addClusterEntry(entry.getKey());
					cluster.addClusterEntryAndMoveCentroid(entry.getKey());
					//System.out.println("Moved:"+entry.getKey().getID()+" to cluster: "+cluster.getID());
				}
			}
			for(Cluster cluster: this.clusterList){
				if(cluster.getID()==entry.getValue()[0]){
					cluster.removeClusterEntryAndMoveCentroid(entry.getKey().getID());
					//System.out.println("delete");
				}
			}
		}
		//if(this.clusterMoves<=this.maxLoops && transferMap.size()>0){
		if(transferMap.size()>0){
			System.out.println("Shuffle ONGOING with "+this.clusterMoves+" shuffles and to be Moved:"+transferMap.size());
			this.shuffleClusters();

		}
		else{
			System.out.println("Shuffle complete with "+this.clusterMoves+" shuffles.");
			//this.calculateClusterCentroids();
			//this.printClusterCentroids();
		}
		//}

	}
	/*
	public void shuffleClustersStandard(){

		this.clusterMoves+=1;
		Map<UniqueEntry,int[]> transferMap=new HashMap<UniqueEntry,int[]>();
		for(Cluster cluster:this.clusterList){
			//cluster.calculateClusterCentroid();
			List<UniqueEntry> clusterEntries=cluster.getClusterEntries();

			for(UniqueEntry uniqueEntry: clusterEntries){
				double curGD=cluster.calculateGeometricDistance(uniqueEntry);
				for(Cluster clusterInner:this.clusterList){
					double entryGD=clusterInner.calculateGeometricDistance(uniqueEntry);
					//System.out.println("curgd: "+curGD+" entryGD "+entryGD);

					//tama on vaarin, pitaisi etsia klusteri joka on lahinna, nyt voi tehda monta siirtoa
					if(cluster.getID()!=clusterInner.getID() && curGD>(entryGD)){
						//System.out.println(uniqueEntry.getID()+" to "+ clusterInner.getID()+" curgd: "+curGD+" entryGD "+entryGD);
						curGD=entryGD;
						ArrayList<Integer> temp=new ArrayList<Integer>();
						temp.add(cluster.getID());
						temp.add(clusterInner.getID());
						//tan pitaisi kylla olla sama, koska kyseessa on update arvoon
						transferMap.put(uniqueEntry, new int[]{cluster.getID(),clusterInner.getID()});


					}

				}

			}
		}
		for(Map.Entry<UniqueEntry, int[]> entry: transferMap.entrySet()){
			//System.out.println("Entry: "+entry.getKey().getID()+ " ClusterFrom: "+entry.getValue()[0]+ " ClusterTo: "+entry.getValue()[1]);
			for(Cluster cluster: this.clusterList){
				if(cluster.getID()==entry.getValue()[1]){
					cluster.addClusterEntry(entry.getKey());
					//cluster.addClusterEntryAndMoveCentroid(entry.getKey());
					//System.out.println("Moved:"+entry.getKey().getID()+" to cluster: "+cluster.getID());
				}
			}
			for(Cluster cluster: this.clusterList){
				if(cluster.getID()==entry.getValue()[0]){
					cluster.removeClusterEntry(entry.getKey().getID());
					//cluster.removeClusterEntryAndMoveCentroid(entry.getKey().getID());
					//System.out.println("delete");
				}
			}
		}

		this.calculateClusterCentroids();

		//for(Cluster cluster: this.clusterList){
			//cluster.calculateClusterCentroid();
		//}


		if(transferMap.size()>0){
			System.out.println("Shuffle ONGOING with "+this.clusterMoves+" shuffles.");
			this.shuffleClustersStandard();

		}
		else{
			System.out.println("Shuffle complete with "+this.clusterMoves+" shuffles.");
			//this.calculateClusterCentroids();
			//this.printClusterCentroids();
		}
		//}

	}	
	 */
	/*
	public void addEntryToCluster(UniqueEntry uniqueEntry,int toCluster){
		for(Cluster cluster: this.clusterList){
			if(cluster.getID()==toCluster){
				cluster.addClusterEntry(uniqueEntry);

			}
		}

	}
	 */
	/*
	private void moveUniqueEntryToCluster(String uniqueEntryID,int fromCluster,int toCluster){

		for(Cluster cluster: this.clusterList){
			if(cluster.getID()==fromCluster){
				UniqueEntry uniqueEntry=cluster.getClusterEntry(uniqueEntryID);
				cluster.removeClusterEntry(uniqueEntryID);
				this.addEntryToCluster(uniqueEntry, toCluster);
			}
		}
	}
	 */
	public void printClusterCentroids(){
		for(Cluster cluster: this.clusterList){
			cluster.printCentroid();
			cluster.printClusterItems();
		}
	}
	public void printClusters(){




		for(Cluster cluster2:this.clusterList){
			System.out.println("--- CLUSTER START ---");
			cluster2.printCluster();
			System.out.println("--- CLUSTER END ---");
		}

	}
	
	public void readUniqueEntriesFromFile(String file){
		//the linkedhashmap is not needed here (for testing purposes
    	Map<String,Map<String,Double>> dataSet=new LinkedHashMap<String,Map<String,Double>>();
    	
		try {
			BufferedReader inFileReader=new BufferedReader(new FileReader(file));
			String line;
			int counter=0;
			while ((line = inFileReader.readLine()) !=null){
				counter+=1;
				String splitArray[] = line.split("\t");
					
				if(splitArray.length>1){
					
					if(dataSet.containsKey(splitArray[0].toString())){
						dataSet.get(splitArray[0].toString()).put(splitArray[1].toString(), Double.parseDouble(splitArray[2]));
					}
					else{
						//the linkedhashmap is not needed here (for testing purposes
						Map<String,Double> temp=new LinkedHashMap<String,Double>();
						temp.put(splitArray[1].toString(), Double.parseDouble(splitArray[2]));
						
						dataSet.put(splitArray[0].toString(),temp);
					}				
				}
				

			}
			
			inFileReader.close();
			System.out.println("Added "+counter);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for(Map.Entry<String, Map<String,Double>> inEntry: dataSet.entrySet()){
			UniqueEntry x1=new UniqueEntry(inEntry.getKey());
			for(Map.Entry<String, Double> uniqs: inEntry.getValue().entrySet()){
				x1.addEntry(uniqs.getKey(), uniqs.getValue());
				
				
			}
			this.addUniqueEntry(x1);
		}
	}

}
