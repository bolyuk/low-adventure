package com.atr.lowadventure;

import java.util.HashMap;

public class User {


    double x = 0;
    double y= 0;
    double xv = 0;
    double yv = 0;
    int speed = 130;
    int colorFilter = 0;

    int wehicle=0;
    int rotation=0;
    float Wspeed=0;

    int hatcolor, haircolor, glasscolor, topcolor, midcolor, carcolor;

    String hat;
    String hatname;

    String hair;
    String hairname;

    String glass;
    String glassname;

    String top;
    String topname;

    String mid;
    String midname;

    int Yt=0;
    float tY=0;
    float tX=0;

    int targetX;
    int targetY;

    String texture = "pd1";
    String name;

    boolean runningFlag = false;
    boolean isDraw=false;

    public GameScreen gm;

    int[] inventory;
    int item = 1;

    public User()
    {
        inventory = new int[5];
    }

    public User(double x, double y, String t)
    {
        this.x=x;
        this.y=y;
        this.texture=t;
    }

    private void clothesHelper1()
    {

        mid=clothesHelper2(midname);
        top=clothesHelper2(topname);
        glass=clothesHelper2(glassname);
        hair=clothesHelper2(hairname);
        hat=clothesHelper2(hatname);
    }

    private String clothesHelper2(String cname)
    {
        char p = this.texture.charAt(1);
        char n = this.texture.charAt(2);
        if(gm.textures.containsKey(cname+p+n))
        {
            return cname+p+n;
        } else if(gm.textures.containsKey(cname+p))
        {
            return cname+p;
        } else {
            return null;
        }
    }

    public void update()
    {
        HashMap<String, Object> result = new HashMap<String, Object>();

        result.put("x", x);
        result.put("y", y);
        result.put("texture", texture);
        result.put("hatname", hatname);
        result.put("hairname", hairname);
        result.put("glassname", glassname);
        result.put("topname", topname);
        result.put("midname", midname);
        result.put("wehicle", wehicle);
        result.put("rotation", rotation);
        String id = com.google.firebase.auth.FirebaseAuth.getInstance().getCurrentUser().getUid();

        gm.updateHashMap(result, "LoW/games/"+gm.mapId +"/users/"+id);

        result.remove("hatname");
        result.remove("hairname");
        result.remove("glassname");
        result.remove("topname");
        result.remove("midname");

        result.put("value", 2);
        result.put("hat", hat);
        result.put("hair", hair);
        result.put("glass", glass);
        result.put("top", top);
        result.put("mid", mid);

        gm.setHashMap(result, "LoW/games/"+gm.mapId +"/request/"+id);
    }

    public void setC(long x, long y)
    {
        this.x=x;
        this.y=y;
        update();
    }

    public void walking ()
    {
        int size = gm.world.get("configuration").get("size");

        if(this.wehicle != 0)
        {
            gm.driveWehicle((float)this.xv,(float) this.yv);
        } else {

            String ntexture = "pd1";

            if(this.xv > 0)
            {
                ntexture=walkHelper1("r");
            } else if(this.xv < 0)
            {
                ntexture=walkHelper1("l");
            }

            if(this.yv > 0)
            {
                ntexture=walkHelper2("d");
            } else if(this.yv < 0)
            {
                ntexture=walkHelper2("u");
            }



            if(ntexture == this.texture || (this.texture != "pd1" && this.texture != "pu1" && this.texture != "pr1" && this.texture != "pl1") && gm.isAcess("isWalk", Integer.toString((int)(this.x+this.xv))+":"+Integer.toString((int)(this.y+this.yv)))&& gm.checkCollision())
            {
                if(this.x+this.xv >= -0.5  && this.x+this.xv <= size-0.5 && this.y+this.yv >= -0.5 && this.y+this.yv <= size-0.5)
                {
                    this.x+=this.xv;
                    this.y+=this.yv;
                }
            }
            this.texture = ntexture;
            clothesHelper1();

            update();
        }
    }

    public void walkingStop()
    {
        if(this.wehicle != 0)
        {
            gm.driveWehicle(0,0);
        } else {
            boolean ed=false;
            if(this.texture.equals("pd2") || this.texture.equals("pd3"))
            {
                this.texture = "pd1";
                ed=true;
            } else if(this.texture.equals("pl2") || this.texture.equals("pl3"))
            {
                this.texture = "pl1";
                ed=true;
            } else if(this.texture.equals("pu2") || this.texture.equals("pu3"))
            {
                this.texture = "pu1";
                ed=true;
            } else if(this.texture.equals("pr2") || this.texture.equals("pr3"))
            {
                this.texture = "pr1";
                ed=true;
            }
            clothesHelper1();

            if(ed)
            {
                update();
            }
        }
    }

    public void setVector(double xv, double yv)
    {
        this.xv = xv;
        this.yv = yv;
    }

    private String walkHelper1(String l)
    {
        if(this.texture.equals("p"+l+1))
        {
            if(this.runningFlag)
            {
                this.runningFlag = false;
                return ("p"+l+2);
            } else {
                this.runningFlag = true;
                return ("p"+l+3);
            }
        } else {
            return ("p"+l+1);
        }

    }

    private String walkHelper2(String l)
    {
        if(this.texture.equals("p"+l+1) ||  this.texture.equals("p"+l+3))
        {
            return ("p"+l+2);
        } else {
            return ("p"+l+3);
        }

    }

    public double[] getUserTarget()
    {

        double[] result = new double[2];
        result[0]=targetX;
        result[1]=targetY;

        return result;
    }

    public void setTarget(int x, int y){
        this.targetX=x;
        this.targetY=y;
    }

    public double[] getUserTranslation()
    {
        double[] result = new double[2];
        result[0]=this.x-(int)this.x;
        result[1]=this.y-(int)this.y;

        return result;
    }

}
