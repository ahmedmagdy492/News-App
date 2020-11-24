package com.migo.apitest;

import java.util.ArrayList;

public class StaticStrings {
    private StaticStrings(){}

    public static final ArrayList<String> categories = new ArrayList<>();

    static{
        categories.add("Top Headlines");
        categories.add("All");
    }
}
