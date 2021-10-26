package com.atr.lowadventure;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.ArrayList;

public class SButton {
    float x;
    float y;
    Bitmap b;
    boolean pushed;
    boolean enabled = true;
    public int argument1;
    public int argument2;

    public SButton(float x, float y, Bitmap b ) {
        this.x = x;
        this.y = y;
        this.b = b;
    }

    public SButton(float x, float y, Bitmap b, int argument1, int argument2)
    {
        this.x = x;
        this.y = y;
        this.b = b;
        this.argument1=argument1;
        this.argument2=argument2;
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
