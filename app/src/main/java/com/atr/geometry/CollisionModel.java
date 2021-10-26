package com.atr.geometry;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class CollisionModel {
    Figure figure;
    public CollisionModel(Figure figure)
    {
        this.figure=figure;
    }

    public void draw(Canvas c, float x, float y)
    {
        Paint p= new Paint();
        p.setColor(Color.RED);
        for(int i= 0; i < figure.lines.size(); i++)
        {
            c.drawLine((float)(x+figure.lines.get(i).d1.x),(float)( y+figure.lines.get(i).d1.y),(float)( x+figure.lines.get(i).d2.x),(float)( y+figure.lines.get(i).d2.y), p);
        }
    }
}
