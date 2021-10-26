package com.atr.geometry;

import java.util.ArrayList;

public class Figure {
    ArrayList<Line> lines;

    public Figure(ArrayList<Line> lines)
    {
        this.lines=lines;
    }

    public boolean checkCollision(Figure b)
    {
        for(int i=0; i < this.lines.size(); i++)
        {
            for(int y=0; y < b.lines.size(); y++)
            {
                if(this.lines.get(i).checkCollision(b.lines.get(y)))
                {
                    return true;
                }
            }
        }
        return false;
    }

    public void rotate(int centerX, int centerY, double angle)
    {
        double sin = Math.sin(Math.toRadians(angle));
        double cos = Math.cos(Math.toRadians(angle));
        for(int i=0; i < this.lines.size(); i++)
        {
            lines.get(i).d1=new Dot((centerX-lines.get(i).d1.x)*cos-(centerY-lines.get(i).d1.y)*sin, (centerX-lines.get(i).d1.x)*sin+(centerY-lines.get(i).d1.y)*cos);
            lines.get(i).d2=new Dot((centerX-lines.get(i).d2.x)*cos-(centerY-lines.get(i).d2.y)*sin, (centerX-lines.get(i).d2.x)*sin+(centerY-lines.get(i).d2.y)*cos);
        }

    }

    public void multiple(double value)
    {

        for(int i=0; i < this.lines.size(); i++)
        {
            lines.get(i).d1=new Dot(lines.get(i).d1.x*value, lines.get(i).d1.y*value);
            lines.get(i).d2=new Dot(lines.get(i).d2.x*value, lines.get(i).d2.y*value);
        }

    }
}
