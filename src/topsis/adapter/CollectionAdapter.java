/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package topsis.adapter;

import java.text.DecimalFormat;

/**
 *
 * @author kingmaster
 */
public class CollectionAdapter {
    private DecimalFormat formatter = new DecimalFormat("#.####");
    private int alternativeCount, criteriaCount;
    private double[][] collectionList = new double[][]{
        {4,4,5,3,3},
        {3,3,4,2,3},
        {5,4,2,2,2},
        {5,2,3,2,4},
        {1,3,4,2,1},
        {2,2,3,1,5},
    };

    public CollectionAdapter() {
    }
    
    public CollectionAdapter(double[][] collectionList) {
        this.collectionList = collectionList;
        this.alternativeCount = collectionList.length;
        this.criteriaCount = collectionList[0].length;
    }
    
    public void convertToArray(double[] array, int index) {
        collectionList[index] = array;
    }
    
    public double[] toDoubleArray(String[] array) {
        double[] doubleArray = new double[array.length];
        
        for (int i=0; i<doubleArray.length; i++) {
            doubleArray[i] = Double.valueOf(array[i]);
        }
        
        return doubleArray;
    }
    
    public double[] toDoubleArray(int[] array) {
        double[] doubleArray = new double[array.length];
        
        for (int i=0; i<doubleArray.length; i++) {
            doubleArray[i] = Double.valueOf(array[i]);
        }
        
        return doubleArray;
    }
    
    public double[] toDoubleArray(float[] array) {
        double[] doubleArray = new double[array.length];
        
        for (int i=0; i<doubleArray.length; i++) {
            doubleArray[i] = Double.valueOf(array[i]);
        }
        
        return doubleArray;
    }
    
    public String[] toStringArray(int[] array) {
        String[] stringArray = new String[array.length];
        
        for (int i=0; i<stringArray.length; i++) {
            stringArray[i] = "" + array[i];
        }
        
        return stringArray;
    }
    
    public String[] toStringArray(float[] array) {
        String[] stringArray = new String[array.length];
        
        for (int i=0; i<stringArray.length; i++) {
            stringArray[i] = "" + array[i];
        }
        
        return stringArray;
    }
    
    public String[] toStringArray(double[] array, boolean isFormat) {
        String[] stringArray = new String[array.length];
        String formattedValue;
        
        for (int i=0; i<stringArray.length; i++) {
            formattedValue = isFormat ? "" + formatter.format(array[i]) : "" + array[i];
            stringArray[i] = formattedValue;
        }
        
        return stringArray;
    }

    public void setCollectionList(double[][] collectionList) {
        this.collectionList = collectionList;
    }

    public double[][] getCollectionList() {
        return collectionList;
    }
    
    public double[] getCollectionListByIndex(int index) {
        return collectionList[index];
    }

    public int getAlternativeCount() {
        return alternativeCount;
    }

    public int getCriteriaCount() {
        return criteriaCount;
    }
}
