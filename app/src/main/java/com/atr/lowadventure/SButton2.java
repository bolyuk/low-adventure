package com.atr.lowadventure;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.ArrayList;

public class SButton2 {
    float x;
    float y;
    Bitmap b;
    boolean pushed;
    boolean enabled = true;
    public String argument="";

    public SButton2(float x, float y, Bitmap b ) {
        this.x = x;
        this.y = y;
        this.b = b;
    }

    public SButton2(float x, float y, Bitmap b, String argument)
    {
        this.x = x;
        this.y = y;
        this.b = b;
        this.argument=argument;
    }

    public void checkPushed(ArrayList<Float> xlist, ArrayList<Float> ylist, int childCount)
    {
        boolean flag=false;
        try{
            for(int i=0; i < childCount; i++)
            {
                float x = xlist.get(i);
                float y = ylist.get(i);
                if(enabled && x > this.x && x < this.x+b.getWidth() && y > this.y && y < this.y+b.getHeight())
                {
                    this.pushed = true;
                    flag=true;
                }
            }
        }catch(Exception err){}
        if(!flag)
        {
            this.pushed=false;
        }
    }

    public void checkPushed(float x, float y)
    {
        if(enabled && x > this.x && x < this.x+b.getWidth() && y > this.y && y < this.y+b.getHeight())
        {
            this.pushed = true;
        }
        else
        {
            this.pushed = false;
        }
    }

    public boolean getPushed()
    {
        return this.pushed;
    }

    public void setEnabled(boolean b)
    {
        this.enabled = b;
    }

    public void Draw(Canvas ca, Paint p)
    {
        if(enabled)
        {
            ca.drawBitmap(b, x, y, p);
        }
    }

}
