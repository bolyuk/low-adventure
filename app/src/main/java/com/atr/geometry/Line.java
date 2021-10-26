package com.atr.geometry;

public class Line {
    Dot d1;
    Dot d2;
    public Line(Dot d1, Dot d2)
    {
        this.d1=d1;
        this.d2=d2;
    }

    public boolean checkCollision(Line b)
    {

        double A1 = (d1.y - b.d1.y) / (d1.x - b.d1.x);
        double A2 = (d2.y - b.d2.y) / (d2.x - b.d2.x);
        double b1 = d1.y - A1 * d1.x;
        double b2 = b.d2.y - A2 * b.d2.x;

        if (A1 == A2) {
                return false;
        }


        double Xa = (b2 - b1) / (A1 - A2);

        if ((Xa < Math.max(d1.x, b.d2.x)) || (Xa > Math.min( d2.x, b.d2.x))) {
                return false;
        }
        else {
                return true;
        }
    }
}
