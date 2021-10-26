package com.atr.lowadventure.singlemode.classes;

public class Entity {
   public int _id;
   public double _x;
   public double _y;
   public double _z;

    public Entity(int id){
    _id=id;
    }

    public void setX(double x){
        _x=x;
    }

    public void setY(double y) {
        _y = y;
    }

    public void setZ(double z) {
        _z = z;
    }

    public void setCoord(int x, int y, int z){
        setX(x);
        setY(y);
        setZ(z);
    }
}
