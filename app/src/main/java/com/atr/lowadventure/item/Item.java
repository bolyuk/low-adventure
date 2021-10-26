package com.atr.lowadventure.item;

public class Item {

    public String name;
    public String texture;
    public String author;

    public Item(String name){
        this.name=name;
    }

    public Item(String name, String texture){
       this.name=name;
       this.texture=texture;
    }

    public Item(String name, String texture, String author){
        this.name=name;
        this.texture=texture;
        this.author=author;
    }
}
