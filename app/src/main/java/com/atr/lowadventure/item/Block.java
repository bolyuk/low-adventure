package com.atr.lowadventure.item;

import com.atr.lowadventure.GameScreen;

public class Block extends Item {

    public String texture;
    public String name;
    public String author="";
    public boolean isUsable=false;
    public boolean isPlacable;
    public boolean isStatic;
    public boolean isWall = false;
    public boolean isWehicle=false;
    public boolean canTransparency=false;
    public boolean xWall;
    public boolean yWall;
    public boolean isCombinable = false;
    public boolean canShadow=false;
    public boolean canRotate=false;
    public float translationX=0;
    public float translationY=0;
    public int color;

    public class dummy{
        float tX;
        float tY;
    }

    public void setWall(boolean x, boolean y)
    {
        this.xWall=x;
        this.yWall=y;
    }

    public Block(String name, String texture)
    {
        super(name, texture);
        this.name=name;
        this.texture=texture;
    }
    public Block(String name, String texture, float tX, float tY, boolean[] arr, int color, GameScreen gm)
    {
        super(name, texture);
        setter(name, texture, tX, tY, arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7],arr[8], color, gm);
    }

    public Block(String name, String texture, float tX, float tY , boolean isPlacable, boolean isStatic, boolean canShadow, boolean isUsable, boolean isWall, boolean isCombinable, boolean canTransparency, boolean canRotate, boolean isWehicle, int color, GameScreen gm)
    {
        super(name, texture);
        setter(name, texture, tX, tY, isPlacable, isStatic, canShadow, isUsable, isWall, isCombinable, canTransparency, canRotate,isWehicle, color, gm);
    }

    private void setter(String name, String texture, float tX, float tY ,boolean isPlacable,  boolean isStatic, boolean canShadow, boolean isUsable, boolean isWall,boolean isCombinable, boolean canTransparency, boolean canRotate, boolean isWehicle, int color, GameScreen gm)
    {
        this.name = name;
        this.texture=texture;
        this.translationX=tX;
        this.translationY=tY;
        this.isWehicle=isWehicle;
        this.canTransparency=canTransparency;
        this.canRotate=canRotate;
        this.isStatic=isStatic;
        this.isUsable=isUsable;
        this.canShadow=canShadow;
        this.isPlacable=isPlacable;
        this.isWall =isWall;
        this.isCombinable=isCombinable;
        this.color=color;


        if(canTransparency)
        {
            gm.textures.put(this.texture+"_transparency", gm.createTransparency(texture, name));
        }

        if(canShadow)
        {
            gm.textures.put(this.texture+"_shadow", gm.createShadow(texture, name));
        }

    }
}
