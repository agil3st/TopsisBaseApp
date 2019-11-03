/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package topsis.adapter;

/**
 *
 * @author kingmaster
 */
public interface Sortable {
    public static final int MODE_ASCENDING = 1;
    public static final int MODE_DESCENDING = 2;
    public void sort();
    public void sort(int mode);
}
