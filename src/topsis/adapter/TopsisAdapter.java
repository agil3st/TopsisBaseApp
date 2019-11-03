/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package topsis.adapter;

import java.text.DecimalFormat;
import java.util.Arrays;

/**
 *
 * @author kingmaster
 */
public class TopsisAdapter {
    private CollectionAdapter collection;
    private DecimalFormat decimalFormat;
    private int alternativeCount, criteriaCount;
    private double finalPreference;
    private double[] weight = new double[]{1,2,3,2,4}, temp,
            positiveIdealSolution, 
            negativeIdealSolution,
            positiveDistance,
            negativeDistance,
            finalPreferences,
            sortedPreferences;
    private double[][] baseMatrix, normalizedMatrix, inversedMatrix;

    public TopsisAdapter() {
        collection = new CollectionAdapter();
        baseMatrix = collection.getCollectionList();
        this.weight = new double[]{1,2,3,2,4};
        alternativeCount = baseMatrix[0].length;
        criteriaCount = baseMatrix.length;
    }
    
    public TopsisAdapter(double[] weight) {
        collection = new CollectionAdapter();
        baseMatrix = collection.getCollectionList();
        this.weight = weight;
        alternativeCount = baseMatrix.length;
        criteriaCount = baseMatrix[0].length;
    }
    
    public TopsisAdapter(CollectionAdapter collection, double[] weight) {
        this.collection = collection;
        this.weight = weight;
        this.baseMatrix = collection.getCollectionList();
        this.alternativeCount = collection.getAlternativeCount();
        this.criteriaCount = collection.getCriteriaCount();
    }
    
    public void runTopsis() {
        normalize();
        normalizeWeight();
        calculatePositiveSolution();
        calculateNegativeSolution();
        calculateWeightPositiveDistance();
        calculateWeightNegativeDistance();
        calculatePreferences();
//        sortPreferences();
    }
    
    public void runTopsisAndPrint() {
        normalize();
        printNormalizedMatrix(" ");
        
        normalizeWeight();
        printNormalizedWeightMatrix(" ");
        
        calculatePositiveSolution();
        printPositiveSolution();
        
        calculateNegativeSolution();
        printNegativeSolution();
        printNormalizedWeightMatrix(" ");
        
        calculateWeightPositiveDistance();
        printPositiveDistance();
        
        calculateWeightNegativeDistance();
        printNegativeDistance();
        
        calculatePreferences();
        printFinalPreferences();
        
        sortPreferences();
        printSortedPreferences();
    }
    
    public void normalize() {
        double temp;
        normalizedMatrix = new double[alternativeCount][criteriaCount];
        this.temp = new double[criteriaCount];
        
        for (int col=0; col<criteriaCount; col++) {
            
            temp = 0;
            for (int row=0;  row<alternativeCount;  row++) {
                temp = temp + Math.pow(baseMatrix[row][col], 2);
            }
            
            this.temp[col] = Math.sqrt(temp);
            for (int row=0;  row<baseMatrix.length;  row++) {
                normalizedMatrix[row][col] = baseMatrix[row][col] / this.temp[col];
            }
            
        }
    }
    
    public void normalizeWeight() {
        int col, row;
        
        for (col=0; col<criteriaCount; col++) {
            for (row=0; row<alternativeCount; row++) {
                normalizedMatrix[row][col] = weight[col] * normalizedMatrix[row][col];
            }
        }
    }
    
    private void inverseMatrix() {
        inversedMatrix = new double[criteriaCount][alternativeCount];
        for (int row = 0; row < criteriaCount; row++) {
            for (int col = 0; col < alternativeCount; col++) {
                inversedMatrix[row][col] = normalizedMatrix[col][row];
            }
        }
    }
    
    public void calculatePositiveSolution() {
        positiveIdealSolution = new double[criteriaCount];
        inverseMatrix();
        for (int col=0; col<criteriaCount; col++) {
            if (col == criteriaCount-1) {
                positiveIdealSolution[col] = min(inversedMatrix[col], inversedMatrix[col].length);
            } else {
                positiveIdealSolution[col] = max(inversedMatrix[col], inversedMatrix[col].length);
            }
        }
    }
    
    public void calculateNegativeSolution() {
        negativeIdealSolution = new double[criteriaCount];
        inverseMatrix();
        for (int col=0; col<criteriaCount; col++) {
            if (col == criteriaCount-1) {
                negativeIdealSolution[col] = max(inversedMatrix[col], inversedMatrix[col].length);
            } else {
                negativeIdealSolution[col] = min(inversedMatrix[col], inversedMatrix[col].length);
            }
        }
    }
    
    public void calculateWeightPositiveDistance() {
        positiveDistance = new double[alternativeCount];
        double temp;
        for (int row=0; row<alternativeCount; row++) {
            temp = 0;
            for (int col=0; col<criteriaCount; col++) {
                temp = temp + Math.pow((positiveIdealSolution[col] - normalizedMatrix[row][col]), 2);
            }
            
            positiveDistance[row] = Math.sqrt(temp);
        }
    }
    
    public void calculateWeightNegativeDistance() {
        negativeDistance = new double[alternativeCount];
        double temp;
        for (int row=0; row<alternativeCount; row++) {
            temp = 0;
            for (int col=0; col<negativeIdealSolution.length; col++) {
                temp = temp + Math.pow((normalizedMatrix[row][col] - negativeIdealSolution[col]), 2);
            }
            
            negativeDistance[row] = Math.sqrt(temp);
        }
    }
    
    public void calculatePreferences() {
        finalPreferences = new double[alternativeCount];
        for (int row=0; row<alternativeCount; row++) {
            finalPreferences[row] = negativeDistance[row] / (negativeDistance[row] + positiveDistance[row]);
        }
        
        finalPreference = max(finalPreferences, finalPreferences.length);
    }
    
    public void sortPreferences() {
        sortedPreferences = new double[alternativeCount];
        sortedPreferences = finalPreferences;
//        Arrays.sort(sortedPreferences);
        
        double temp;
        for (int i=0; i<sortedPreferences.length; i++) {
            for (int j=i; j>0; j--) {
                if (sortedPreferences[j] > sortedPreferences[j-1]) {
                    temp = sortedPreferences[j];
                    sortedPreferences[j] = sortedPreferences[j-1];
                    sortedPreferences[j-1] = temp;
                }
            }
        }
    }
    
    private double min(double[] x, int n) {
        // if size = 0 means whole array 
        // has been traversed 
        if(n == 1) {
            return x[0]; 
        }
        
        return Math.min(x[n-1], min(x, n-1)); 
    }
    
    private double max(double[] x, int n) {
        // if size = 0 means whole array 
        // has been traversed 
        if(n == 1) {
            return x[0]; 
        }
        
        return Math.max(x[n-1], max(x, n-1)); 
    }
    
    public void printBaseMatrix(String separator) {
        int row, col;
        
        System.out.print("\nBase Matrix\n");
        
        for (row=0; row<alternativeCount; row++) {
            for (col=0; col<criteriaCount; col++) {
                System.out.print(baseMatrix[row][col] + separator);        
            }
            System.out.println("");
        }
        System.out.println("");
    }
    
    public void printNormalizedMatrix(String separator) {
        int row, col;
        decimalFormat = new DecimalFormat("#.####");
        
        System.out.print("Normalized Matrix\n");
        
        for (row=0; row<alternativeCount; row++) {
            for (col=0; col<criteriaCount; col++) {
                System.out.print(decimalFormat.format(normalizedMatrix[row][col]) 
                        + separator);
            }
            System.out.println("");
        }
        System.out.println("");
    }
    
    public void printNormalizedWeightMatrix(String separator) {
        int row, col;
        decimalFormat = new DecimalFormat("#.####");
        
        System.out.print("Normalized Weight Matrix\n");
        
        for (row=0; row<alternativeCount; row++) {
            for (col=0; col<criteriaCount; col++) {
                System.out.print(decimalFormat.format(normalizedMatrix[row][col]) 
                        + separator);
            }
            System.out.println("");
        }
        System.out.println("");
    }
    
    public void printInvertedMatrix(String separator) {
        int row, col;
        decimalFormat = new DecimalFormat("#.####");
        
        System.out.print("\nNormalized Weight Matrix (Inverse)\n");
        
        for (row=0; row<criteriaCount; row++) {
            for (col=0; col<alternativeCount; col++) {
                System.out.print(decimalFormat.format(inversedMatrix[row][col]) 
                        + separator);
            }
            System.out.println("");
        }
        System.out.println("");
    }
    
    private void printList(double[] list, String separator) {
        int i;
        decimalFormat = new DecimalFormat("#.####");
        
        for (i=0; i<list.length; i++) {
            System.out.print(decimalFormat.format(list[i]) + separator);
        }
        
        System.out.println();
    }
    
    public void printCustomWeight() {
        System.out.print("\nCustom Weight\n");
        printList(weight, " ");
    }
        
    public void printMatrixByIndex(int index) {
        System.out.print("\nList on index " + index + "\n");
        printList(inversedMatrix[index], " ");
    }
    
    public void printPositiveSolution() {
        System.out.println("Positive Solution");
        printList(positiveIdealSolution, " ");
        System.out.println();
    }
    
    public void printNegativeSolution() {
        System.out.println("Negative Solution");
        printList(negativeIdealSolution, " ");
        System.out.println();
    }
    
    public void printPositiveDistance() {
        System.out.println("Positive Distance");
        printList(positiveDistance, " ");
        System.out.println();
    }
    
    public void printNegativeDistance() {
        System.out.println("Negative Distance");
        printList(negativeDistance, " ");
    }
    
    public void printFinalPreferences() {
        System.out.print("\nFinal Preferences\n");
        printList(finalPreferences, " ");
    }
    
    public void printSortedPreferences() {
        System.out.print("\nSorted Preferences\n");
        printList(sortedPreferences, " ");
    }
    
    public void printTableDescription() {
        System.out.println("Table Size: " + criteriaCount + " x " + alternativeCount);
        System.out.println("Alternative Count (rows): " + alternativeCount);
        System.out.println("Criteria Count (cols): " + criteriaCount);
    }

    public double getFinalPreference() {
        return Double.parseDouble(decimalFormat.format(finalPreference));
    }

    public int getAlternativeCount() {
        return alternativeCount;
    }

    public int getCriteriaCount() {
        return criteriaCount;
    }

    public double[] getWeight() {
        return weight;
    }

    public double[] getPositiveIdealSolution() {
        return positiveIdealSolution;
    }

    public double[] getNegativeIdealSolution() {
        return negativeIdealSolution;
    }

    public double[] getPositiveDistance() {
        return positiveDistance;
    }

    public double[] getNegativeDistance() {
        return negativeDistance;
    }

    public double[] getFinalPreferences() {
        return finalPreferences;
    }

    public double[] getFinalSortedPreferences() {
        return sortedPreferences;
    }

    public double[][] getBaseMatrix() {
        return baseMatrix;
    }

    public double[][] getNormalizedMatrix() {
        return normalizedMatrix;
    }

    public double[][] getInversedMatrix() {
        return inversedMatrix;
    }

    public int getChosenAlternative() {
        return Arrays.binarySearch(finalPreferences, Double.valueOf(decimalFormat.format(finalPreference)));
    }

    public void setWeight(double[] weight) {
        this.weight = weight;
    }

    public void setBaseMatrix(double[][] baseMatrix) {
        this.baseMatrix = baseMatrix;
    }
    
}
