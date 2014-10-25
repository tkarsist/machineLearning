package com.veikonkala.machineLearning;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Hello world!
 *
 */
public class MachineLearning 
{
    public static void main( String[] args )
    {
    	
    	
    	KMeans kmeans= new KMeans(3);
    	kmeans.readUniqueEntriesFromFile("countries.data");
		//kmeans.generateClustersStandard();
		//kmeans.generateClusters();
		kmeans.generateClusters2();
		//kmeans.printClusterCentroids();
		kmeans.printClusters();
		//kmeans.calculateClusterSilhouttes2();
		
		/*
		List<Double> averageClustering=new ArrayList<Double>();
		for(int i=0;i<10;i++){
			kmeans.generateClusters2();
			Double ClusterSum=0.0;
			for(Double silhoutteVal: kmeans.calculateClusterSilhouttes2().values()){
				ClusterSum+=silhoutteVal;
				
			}
			averageClustering.add(ClusterSum);
			
		}
		System.out.println("Best cluster sum: "+Collections.max(averageClustering));
        */
		
        UniqueEntry e1=new UniqueEntry("Timo");
        e1.addEntry("linux",15.2);
        e1.addEntry("novell",1.3);
        //e1.addEntry("kosmo",0.0);
        e1.addEntry("menaiset",2.0);
        //e1.printEntries();
        
        UniqueEntry e2=new UniqueEntry("Kaisa");
        //e2.addEntry("kosmo",1.4);
        e2.addEntry("linux", 12.0);
        //e2.addEntry("menaiset",1.5);
        e2.addEntry("novell",2.0);
        //e2.printEntries();
        
        UniqueEntry e3=new UniqueEntry("Kosmos");
        //e3.addEntry("kosmo",1.8);
        e3.addEntry("linux", 1.0);
        //e3.addEntry("menaiset",0.0);
        e3.addEntry("novell",12.9);
        //e3.printEntries();
        
        UniqueEntry e4=new UniqueEntry("Kosmost");
        //e4.addEntry("kosmo",0.0);
        e4.addEntry("linux", 1.1);
        //e4.addEntry("menaiset",0.0);
        e4.addEntry("novell",15.9);
        //e4.printEntries();
        
        UniqueEntry e5=new UniqueEntry("Jari");
        //e4.addEntry("kosmo",0.0);
        e5.addEntry("linux", 15.1);
        //e4.addEntry("menaiset",0.0);
        e5.addEntry("novell",2.9);
        //e5.printEntries();

        UniqueEntry e6=new UniqueEntry("Maito");
        //e4.addEntry("kosmo",0.0);
        e6.addEntry("linux", 2.1);
        e4.addEntry("menaiset",16.0);
        e6.addEntry("novell",12.9);

        
        
        KMeans test= new KMeans(2);
        test.addUniqueEntry(e1);
        test.addUniqueEntry(e2);
        test.addUniqueEntry(e3);
        test.addUniqueEntry(e4);
        test.addUniqueEntry(e5);
        test.addUniqueEntry(e6);
        /*
        test.generateClusters();
        test.printClusters();
        test.calculateClusterCentroids();
        test.printClusterCentroids();
        */
        
        /*
        
        Cluster cluster1=new Cluster(0);
        cluster1.addClusterEntry(e1);
        cluster1.addClusterEntry(e2);
        cluster1.printCluster();
        cluster1.calculateClusterCentroid();
        System.out.println("...... DELETE");
        //cluster1.removeClusterEntry("Timo");
        cluster1.printCluster();
        System.out.println("Distanssi -------");
        Double hassu= cluster1.calculateGeometricDistance("Timo");
        System.out.println("distanssi: "+hassu);
        */
    }
}
