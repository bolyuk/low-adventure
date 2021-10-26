package com.atr.lowadventure.singlemode.DBe3d;

import android.graphics.Canvas;

import java.util.ArrayList;

public  class DBe3d {

    DBe3c _camera;
    public void DBe3d(){

    }

    public void registerCamera(DBe3c camera){
        _camera=camera;
    }

    public void draw(ArrayList <DBe3obj> objects, Canvas canvas){
        for(int i=0; i <objects.size(); i++){
            objects.get(i).draw(canvas);
        }
    }
}
