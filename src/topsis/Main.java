/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package topsis;

import topsis.adapter.TopsisAdapter;
import topsis.adapter.CollectionAdapter;
import topsis.adapter.ObjectCollection;

/**
 *
 * @author kingmaster
 */
public class Main {
    private TopsisAdapter topsisAdapter;
    private CollectionAdapter collection;
    
    private void run() {
        // You may custom the weight
        double[][] collectionList = new double[][]{
            {4,3,3,3,3},
            {1,3,5,3,3},
            {2,5,2,2,2},
            {1,3,3,2,4},
            {1,3,4,2,1},
            {3,2,3,1,5},
        };
        
        double[] weight = new double[]{2,1,1,4,5};
        
        String[] productName = new String[]{
            "Keping Cokelat",
            "Nastar Keju",
            "Blinjo Vanili",
            "Melinjo Jagung",
            "Kayu Manis Mente",
            "Coco Cris"
        };
        
        String[] productHeader = new String[]{"name", "criteria"};
        
        collection = new CollectionAdapter(collectionList);
        
        topsisAdapter = new TopsisAdapter(collection, weight);
        
//        topsisAdapter.printTableDescription();
//        topsisAdapter.printCustomWeight();
//        topsisAdapter.printBaseMatrix(" ");
        
        topsisAdapter.runTopsis();
        
//        System.out.println("\nFinal: " + topsisAdapter.getFinalPreference());

        ObjectCollection productCollection = new ObjectCollection(
                productHeader, 
                productName, 
                collection.toStringArray(topsisAdapter.getFinalPreferences(), false));  

        productCollection.sort(2);

        System.out.println("Header: " + productCollection.getList("name")[4]);
//        System.out.println("Collection Size: " + productCollection.getList().size());
        for (int i=0; i<productCollection.getList().size(); i++) {
            System.out.println("name\t\t: "+productCollection.getList().get(i).get("name"));
            System.out.println("criteria\t: "+productCollection.getList().get(i).get("criteria"));
        }
    }
    
    public void start() {
        run();
    }
}
