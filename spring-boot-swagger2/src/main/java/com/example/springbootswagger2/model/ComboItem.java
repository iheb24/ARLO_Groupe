package com.example.springbootswagger2.model;

public class ComboItem
{
    private int key;
    private String value;

    public ComboItem(int key, String value)
    {
        this.key = key;
        this.value = value;
    }


    public int getKey()
    {
        return key;
    }

    public String getValue()
    {
        return value;
    }
}