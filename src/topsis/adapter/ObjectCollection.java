/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package topsis.adapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author kingmaster
 */
public class ObjectCollection implements Sortable{
    private HashMap<String, String> item;
    private List<HashMap<String, String>> list;
    private String[] listHeader, itemsName, itemsValue;
    private int[] itemsImage;

    public ObjectCollection() {
        list = new ArrayList<>();
    }
    
    public ObjectCollection(String[] listHeader, String[] itemsName, String[] itemsValue) {
        list = new ArrayList<>();
        this.listHeader = listHeader;
        this.itemsName = itemsName;
        this.itemsValue = itemsValue;
        addAllItem();
    }
    
    public ObjectCollection(String[] listHeader, int[] itemsImage, String[] itemsName, String[] itemsValue) {
        list = new ArrayList<>();
        this.listHeader = listHeader;
        this.itemsImage = itemsImage;
        this.itemsName = itemsName;
        this.itemsValue = itemsValue;
        addAllItemWithImage();
    }
    
    private void addAllItem() {
        for (int i=0; i<itemsName.length; i++) {
            item = new HashMap<>();
            item.put(listHeader[0], itemsName[i]);
            item.put(listHeader[1], itemsValue[i]);
            list.add(item);
        }
    }
    
    private void addAllItemWithImage() {
        for (int i=0; i<itemsName.length; i++) {
            item = new HashMap<>();
            item.put(listHeader[0], "" + itemsImage[i]);
            item.put(listHeader[1], itemsName[i]);
            item.put(listHeader[2], itemsValue[i]);
            list.add(item);
        }
    }

    @Override
    public void sort() {
        sort(1);
    }

    @Override
    public void sort(int mode) {
        switch(mode) {
            case 1:
                Collections.sort(list, (HashMap<String, String> item1, HashMap<String, String> item2) -> item1.get("criteria").compareTo(item2.get("criteria")));
                break;
            case 2:
                Collections.sort(list, (HashMap<String, String> item1, HashMap<String, String> item2) -> item2.get("criteria").compareTo(item1.get("criteria")));
                break;
            default:
                Collections.sort(list, new Comparator<HashMap<String, String>>(){ 
                    @Override
                    public int compare(HashMap<String, String> item1, HashMap<String, String> item2) { 
                        return item1.get("criteria").compareTo(item2.get("criteria"));
                    } 
                });
                break;
        }
    }

    public HashMap<String, String> getItem() {
        return item;
    }

    public void setItem(HashMap<String, String> item) {
        this.item = item;
    }

    public List<HashMap<String, String>> getList() {
        return list;
    }
    
    public String[] getListStrings(String headerName) {
        String[] listValue = new String[itemsName.length];
        for (int i=0; i<itemsName.length; i++) {
            listValue[i] = list.get(i).get(headerName);
        }
        
        return listValue;
    }
    
    public int[] getList(String headerName) {
        int[] listValue = new int[itemsName.length];
        for (int i=0; i<itemsName.length; i++) {
            listValue[i] = Integer.parseInt(list.get(i).get(headerName));
        }
        
        return listValue;
    }

    public void setList(List<HashMap<String, String>> list) {
        this.list = list;
    }

    public String[] getListHeader() {
        return listHeader;
    }

    public void setListHeader(String[] listHeader) {
        this.listHeader = listHeader;
    }
}
