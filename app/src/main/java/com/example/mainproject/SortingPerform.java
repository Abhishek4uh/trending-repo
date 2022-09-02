package com.example.mainproject;

import com.example.mainproject.models.ItemsItem;

import java.util.Collections;
import java.util.Comparator;

public class SortingPerform implements Comparator<ItemsItem> {

//    ItemsItem itemsItem=new ItemsItem();
//    String repo =itemsItem.getRepo();
//    String[] arr = repo.split("/");

    @Override
    public int compare(ItemsItem o1, ItemsItem o2) {
        String temp1 = o1.getStars().replace(",","");
        String temp2 = o2.getStars().replace(",","");
        int n1=Integer.parseInt(temp1);
        int n2=Integer.parseInt(temp2);
        return Integer.compare(n1, n2);
    }



}
