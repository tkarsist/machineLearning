package com.veikonkala.machineLearning;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
    }
    
    //Tämä centroidi laskee väärin. Se olettaa, että jos ei ole arvostellut niin arvo on 0, toinen olettaa että keskiarvo on sama jos pysyy samana
    public void testCalculateCentroid(){
        UniqueEntry e1=new UniqueEntry("Person1");
        e1.addEntry("linux",15.2);
        e1.addEntry("novell",1.3);
        e1.addEntry("menaiset",2.0);
        
        
        UniqueEntry e2=new UniqueEntry("Person2");
        e2.addEntry("kosmo",1.4);
        e2.addEntry("linux", 12.0);
        e2.addEntry("menaiset",1.5);
        e2.addEntry("novell",2.0);
        
        
        UniqueEntry e3=new UniqueEntry("Person3");
        e3.addEntry("kosmo",1.8);
        e3.addEntry("linux", 1.0);
        e3.addEntry("menaiset",0.0);
        e3.addEntry("novell",12.9);

        UniqueEntry e4=new UniqueEntry("Person3");
        e4.addEntry("kosmo",11.0);
        e4.addEntry("linux", 3.0);
        e4.addEntry("menaiset",8.0);
        

        
        Cluster testCluster=new Cluster(1);
        
        testCluster.addClusterEntry(e1);
        testCluster.addClusterEntry(e2);
        testCluster.addClusterEntry(e3);
        testCluster.addClusterEntry(e4);
        testCluster.calcClusterCentroid();
        //testCluster.printCentroid();
        
    	UniqueEntry reference=new UniqueEntry("centroid");
    	reference.addEntry("novell",4.05);
    	reference.addEntry("linux",7.8);
    	reference.addEntry("menaiset",2.875);
    	reference.addEntry("kosmo",3.55);
    	
    	//reference.printEntries();
    	//testCluster.getCentroid().printEntries();
    	
    	assertEquals(reference.getEntries(),testCluster.getCentroid().getEntries());
    	//assertTrue(reference.equals(testCluster.getCentroid()));
    }
    
    public void testAddItemAndCalculateCentroid(){
        UniqueEntry e1=new UniqueEntry("Person1");
        e1.addEntry("linux",15.2);
        e1.addEntry("novell",1.3);
        e1.addEntry("menaiset",2.0);
        
        
        UniqueEntry e2=new UniqueEntry("Person2");
        e2.addEntry("kosmo",1.4);
        e2.addEntry("linux", 12.0);
        e2.addEntry("menaiset",1.5);
        e2.addEntry("novell",2.0);
        
        
        UniqueEntry e3=new UniqueEntry("Person3");
        e3.addEntry("kosmo",1.8);
        e3.addEntry("linux", 1.0);
        e3.addEntry("menaiset",0.0);
        e3.addEntry("novell",12.9);

        UniqueEntry e4=new UniqueEntry("Person4");
        e4.addEntry("kosmo",11.0);
        e4.addEntry("linux", 3.0);
        e4.addEntry("menaiset",8.0);
        //e4.addEntry("novell",0.0);
        

        
        Cluster testCluster=new Cluster(1);
        
        testCluster.addClusterEntry(e1);
        testCluster.addClusterEntry(e2);
        testCluster.addClusterEntry(e3);
        //testCluster.addClusterEntry(e4);
        testCluster.calcClusterCentroid();
        testCluster.addClusterEntryAndMoveCentroid(e4);
        //testCluster.printCentroid();
        
    	UniqueEntry reference=new UniqueEntry("centroid");
    	reference.addEntry("novell",4.05);
    	reference.addEntry("linux",7.8);
    	reference.addEntry("menaiset",2.875);
    	reference.addEntry("kosmo",3.55);
     	
    	//reference.printEntries();
    	//testCluster.getCentroid().printEntries();
    	
    	assertEquals(reference.getEntries(),testCluster.getCentroid().getEntries());
    	//assertTrue(reference.equals(testCluster.getCentroid()));
    }
    
    public void testRemoveItemAndCalculateCentroid(){
        UniqueEntry e1=new UniqueEntry("Person1");
        e1.addEntry("linux",15.2);
        e1.addEntry("novell",1.3);
        e1.addEntry("menaiset",2.0);
        
        
        UniqueEntry e2=new UniqueEntry("Person2");
        e2.addEntry("kosmo",1.4);
        e2.addEntry("linux", 12.0);
        e2.addEntry("menaiset",1.5);
        e2.addEntry("novell",2.0);
        
        
        UniqueEntry e3=new UniqueEntry("Person3");
        e3.addEntry("kosmo",1.8);
        e3.addEntry("linux", 1.0);
        e3.addEntry("menaiset",0.0);
        e3.addEntry("novell",12.9);

        UniqueEntry e4=new UniqueEntry("Person4");
        e4.addEntry("kosmo",11.0);
        e4.addEntry("linux", 3.0);
        e4.addEntry("menaiset",8.0);
        //e4.addEntry("novell",0.0);
        

        
        Cluster testCluster=new Cluster(1);
        
        testCluster.addClusterEntry(e1);
        testCluster.addClusterEntry(e2);
        testCluster.addClusterEntry(e3);
        testCluster.addClusterEntry(e4);
        testCluster.calcClusterCentroid();
        testCluster.removeClusterEntryAndMoveCentroid(e4.getID());
        testCluster.printCentroid();
        
    	UniqueEntry reference=new UniqueEntry("centroid");
    	reference.addEntry("novell",5.3999999999999995);
    	reference.addEntry("linux",9.4);
    	reference.addEntry("menaiset",1.1666666666666667);
    	reference.addEntry("kosmo",1.0666666666666664);
    	
    	//reference.printEntries();
    	//testCluster.getCentroid().printEntries();
    	
    	assertEquals(reference.getEntries(),testCluster.getCentroid().getEntries());
    	//assertTrue(reference.equals(testCluster.getCentroid()));
    }
    
    public void testCalculateClusterDissimilation(){
        /*UniqueEntry e1=new UniqueEntry("1");
        e1.addEntry("x",10.0);
        e1.addEntry("y",10.0);

        UniqueEntry e2=new UniqueEntry("2");
        e2.addEntry("x",12.0);
        e2.addEntry("y",8.0);

        UniqueEntry e3=new UniqueEntry("3");
        e3.addEntry("x",11.0);
        e3.addEntry("y",9.0);

        UniqueEntry e4=new UniqueEntry("4");
        e4.addEntry("x",12.0);
        e4.addEntry("y",8.0);
        
        UniqueEntry e5=new UniqueEntry("5");
        e5.addEntry("x",15.0);
        e5.addEntry("y",15.0);
        
        UniqueEntry e6=new UniqueEntry("6");
        e6.addEntry("x",20.0);
        e6.addEntry("y",20.0);
        
        UniqueEntry e7=new UniqueEntry("7");
        e7.addEntry("x",19.0);
        e7.addEntry("y",19.0);

        UniqueEntry e8=new UniqueEntry("8");
        e8.addEntry("x",21.0);
        e8.addEntry("y",22.0);

        
        UniqueEntry e9=new UniqueEntry("9");
        e9.addEntry("x",50.0);
        e9.addEntry("y",50.0);
        
        UniqueEntry e10=new UniqueEntry("10");
        e10.addEntry("x",45.0);
        e10.addEntry("y",46.0);

        UniqueEntry e11=new UniqueEntry("11");
        e11.addEntry("x",47.0);
        e11.addEntry("y",43.0);

        UniqueEntry e12=new UniqueEntry("12");
        e12.addEntry("x",51.0);
        e12.addEntry("y",52.0);
        */

        KMeans kmeans=new KMeans(3);
        kmeans.randomizeClusters(false);
        kmeans.readUniqueEntriesFromFile("countries.data");
        /*
        kmeans.addUniqueEntry(e1);
        kmeans.addUniqueEntry(e2);
        kmeans.addUniqueEntry(e3);
        kmeans.addUniqueEntry(e4);
        kmeans.addUniqueEntry(e5);
        kmeans.addUniqueEntry(e6);
        kmeans.addUniqueEntry(e7);
        kmeans.addUniqueEntry(e8);
        kmeans.addUniqueEntry(e9);
        kmeans.addUniqueEntry(e10);
        kmeans.addUniqueEntry(e11);
        kmeans.addUniqueEntry(e12);
        */
        kmeans.generateClusters2();
        kmeans.printClusters();
        //kmeans.calculateClusterSilhouttes();
        kmeans.calculateClusterSilhouttes2();
        


    }
    
}
