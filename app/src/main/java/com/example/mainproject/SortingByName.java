package com.example.mainproject;

import com.example.mainproject.models.ItemsItem;

import java.util.Comparator;

public class SortingByName implements Comparator<ItemsItem> {
    @Override
    public int compare(ItemsItem o1, ItemsItem o2) {
        String[] arr1 = o1.getRepo().split("/");
        String[] arr2 = o2.getRepo().split("/");
        String name1 = arr1[0];
        String name2 = arr2[0];
        return name1.toLowerCase().compareTo(name2.toLowerCase());
    }
}
