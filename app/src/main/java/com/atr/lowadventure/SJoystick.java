package com.atr.lowadventure;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.ArrayList;

public class SJoystick
{
    float radius;

    float centerX;
    float centerY;
    float currentX;
    float currentY;

    Bitmap texture;
    Bitmap background;

    boolean pushed;
    boolean enabled=true;


    public SJoystick(float centerX, float centerY, float radius, Bitmap background, Bitmap texture)
    {
        this.background=background;
        this.centerX=centerX;
        this.centerY=centerY;
        this.radius=radius;
        this.texture=texture;
        this.currentX=this.centerX;
        this.currentY=this.centerY;
    }

    public void checkPushed(float x, float y)
    {
        if(Math.pow(x-this.centerX, 2)+Math.pow(y-this.centerY, 2) <= Math.pow(this.radius*3/2,2))
        {
            this.currentX=x;
            this.currentY=y;
            this.pushed=true;
        } else {
            this.currentX=this.centerX;
            this.currentY=this.centerY;
            this.pushed=false;
        }
    }

    public void checkPushed(ArrayList<Float> xlist, ArrayList<Float> ylist, int childCount)
    {

        boolean flag=false;
        try
        {
            for(int i=0; i < childCount; i++)
            {
                float x = xlist.get(i);
                float y = ylist.get(i);

                if(Math.pow(x-this.centerX, 2)+Math.pow(y-this.centerY, 2) <= Math.pow(this.radius*3/2,2))
                {
                    this.currentX=x;
                    this.currentY=y;
                    this.pushed=true;
                    flag=true;
                }
            }
        }catch(Exception err){}
        if(!flag)
        {
            this.currentX=this.centerX;
            this.currentY=this.centerY;
            this.pushed=false;
        }
    }


    public boolean getPushed()
    {
        return this.pushed;
    }

    public float[] getAxis()
    {
        float[] result = new float[2];
        result[0]=0;
        result[1]=0;
        float difX = this.currentX-this.centerX;
        float difY = this.currentY-this.centerY;

        if( difX> radius*1/6 || difX < -(radius*1/6))
        {
            if(difX <= radius && difX >= -radius)
            {
                result[0]=(float)((this.currentX-this.centerX)/radius);
            } else {
                result[0]=difX>=0?(float)0.6: (float)-0.6;
            }
        } else {
            result[0]=0;
        }

        if(difY > radius*1/6 || difY < -(radius*1/6))
        {
            if(difY <= radius && difY >= -radius)
            {
                result[1]=(float)((this.currentY-this.centerY)/radius);
            } else {
                result[1]=difY>=0?(float)0.6: (float)-0.6;
            }
        } else {
            result[1]=0;
        }

        return result;
    }

    public void draw(Canvas c, Paint p)
    {
        //c.drawCircle(this.centerX, this.centerY, radius, p);

        c.drawBitmap(Bitmap.createScaledBitmap(background,(int)(radius*2),(int)(radius*2),false), centerX-radius, centerY-radius, p);

        c.drawBitmap(texture,this.currentX-this.texture.getWidth()/2, this.currentY-this.texture.getHeight()/2, p);
    }
}
