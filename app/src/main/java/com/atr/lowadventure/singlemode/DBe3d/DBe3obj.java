package com.atr.lowadventure.singlemode.DBe3d;

import android.graphics.Canvas;
import android.graphics.Paint;

public class DBe3obj {

    public float[][] _dots;
    public int _fillType;
    public int _color;

    public void DBe3obj(){

    }

    public void setFilltype(int filltype){
        _fillType=filltype;
    }

    public void setColor(int color){
        _color=color;
    }

    public void setObj(float[][] dots){
        _dots=dots;
    }

    public void draw(Canvas canvas){
        Paint _p = new Paint();
        _p.setColor(_color);
        for(int i=0;i< _dots.length-1; i++){
            canvas.drawLine(_dots[i][0],_dots[i][1], _dots[i+1][0],_dots[i+1][1], _p);
        }
        int i=_dots.length-1;
        canvas.drawLine(_dots[i][0],_dots[i][1], _dots[0][0],_dots[0][1], _p);
    }
}
