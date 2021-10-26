package com.atr.lowadventure;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.view.SurfaceHolder;

import com.atr.lowadventure.activity.GameActivity;

import java.text.DecimalFormat;

public class DrawThread extends Thread{

    private final GameActivity gameActivity;
    public GameScreen gm;
    public boolean runningFlag = false;
    public boolean pausedFlag = false;
    public Canvas canvas = new Canvas();
    public long drawTime;
    public Double fps = 0.0;
    public long down=0, up=0, shadow=0;
    boolean frameDrawed=true;
    Bitmap cvsBitmap;
    Canvas cvs;


    Paint paint = new Paint();



    private SurfaceHolder surfaceHolder;
    public Context context;

    public DrawThread(GameActivity gameActivity, SurfaceHolder surfaceHolder, GameScreen gm)
    {
        this.gameActivity = gameActivity;
        this.gm = gm;
        this.surfaceHolder = surfaceHolder;
        cvsBitmap = Bitmap.createBitmap((gm.renderX*2+1)*gm.textures.textureSize,(gm.renderY*2+1)*gm.textures.textureSize, Bitmap.Config.ARGB_8888);
        cvs = new Canvas(cvsBitmap);
    }

    public void setRunning(boolean bool)
    {
        this.runningFlag = bool;
    }

    @Override
    public void run()
    {
        paint.setTextSize(10);
        paint.setTypeface(gm.font);
        gameActivity._WriteLogInFile("run start");

         paint.setAntiAlias(false);
         paint.setDither(true);
         paint.setFilterBitmap(false);

        long lt1 = System.currentTimeMillis();

        while (this.runningFlag)
        {
            if(this.pausedFlag )
            {
                frameDrawed=false;
                if(drawTime==0) {
                    drawTime = System.currentTimeMillis();
                } else {
                    fps=1000/(double)(System.currentTimeMillis()-drawTime);
                    drawTime=System.currentTimeMillis();

                }
                if((gm.joystick.getPushed() || gm.user.wehicle != 0) && System.currentTimeMillis() >= gm.user.speed+lt1)
                {
                    lt1 = System.currentTimeMillis();
                    gm.user.walking();
                }else if(!gm.joystick.getPushed() && System.currentTimeMillis() >= gm.user.speed+lt1){
                    gm.user.walkingStop();
                    lt1 = System.currentTimeMillis();
                }


                canvas = null;
                try
                {
                    canvas = surfaceHolder.lockCanvas(null);
                    synchronized (surfaceHolder)
                    {
                        WorldRender();
                    }
                }
                finally
                {
                    try{
                        if (canvas != null)
                        {
                            surfaceHolder.unlockCanvasAndPost(canvas);
                        }
                    }catch(Exception err1){
                        gameActivity._WriteLogInFile(err1.toString());
                    }
                }
                frameDrawed=true;

            }

        }
    }

    public void WorldRender()
    {
        try{
            int sizeX=canvas.getWidth();
            int sizeY=canvas.getHeight();
            canvas.drawColor(Color.WHITE);
            canvas.save();
            canvas.translate(sizeX/2, sizeY/2);
            canvas.scale(gm.scaleSize,gm.scaleSize);
            drawDownLayer(canvas);
            if(gm.drawShadow) drawShadowLayer(cvs);
            drawUpLayer(canvas);
            canvas.restore();
            for(int i=0; i< gm.gamebuttons.size(); i++){
                gm.gamebuttons.get(i).Draw(canvas, paint);
            }
            drawGui(canvas);
        }catch(Exception err){
            gameActivity._WriteLogInFile(err.toString());}



    }

    public void drawClothes(String id, float x, float y, Canvas canvas)
    {
        if( id != null && gm.textures.containsKey(id))
        {
            canvas.drawBitmap(gm.textures.get(id), x-gm.textures.textureSize/4, y-gm.textures.textureSize/4, paint);
        }
    }

    public void drawGui(Canvas canvas)
    {
        for ( String k : gm.buttons.keySet() ) {
            gm.buttons.get(k).Draw(canvas, paint);
        }

        gm.joystick.draw(canvas, paint);

        for(int i=1; i < 6; i++)
        {
            float xi=(float)(gameActivity.gamelinear.getWidth()-gm.buttonSize*(i+4));
            float yi= gameActivity.gamelinear.getHeight()-gm.buttonSize;

            if(gm.user.item == i)
            {
                canvas.drawBitmap(gm.textures.get("inventory2"), xi, yi, paint );
            }

            if(gm.user.inventory[i-1] != 0)
            {
                canvas.drawBitmap(Bitmap.createScaledBitmap(gm.textures.get(gm.blocks.get(gm.user.inventory[i-1]).texture), gm.buttonSize-(gm.buttonSize/3) ,gm.buttonSize-(gm.buttonSize/3) ,false),xi+(gm.buttonSize/6), yi+(gm.buttonSize/6) , paint);
            }
        }


        canvas.drawText(new DecimalFormat("#0.00").format(fps)+" FPS"
                + "\n\n DOWN:" +down+" ms"
                +"\n SHADOW:"+shadow+" ms"+
                "\n UP: "+up+" ms"
                +"\n TARGET:"+gm.target,10,30,paint);
    }

    public void drawUser(float x, float y, User u, Canvas canvas)
    {

        if(u.wehicle == 0)
        {
            canvas.drawBitmap(gm.textures.get(u.texture),x, y, paint);
            drawClothes(u.mid, x, y, canvas);
            drawClothes(u.top, x, y, canvas);
            drawClothes(u.glass, x, y, canvas);
            drawClothes(u.hair, x, y, canvas);
            drawClothes(u.hat, x, y, canvas);
        } else {
            gm.drawWehicle(canvas, x, y, u);
        }
        u.isDraw=true;
    }
    public void drawDownLayer(Canvas canvas)
    {
        long time = System.currentTimeMillis();
        float Yst=0-(gm.renderY*gm.textures.textureSize);
        float Xst =0-(gm.renderX*gm.textures.textureSize);
         Xst+=(float)-gm.user.getUserTranslation()[0]*gm.textures.textureSize;
         Yst+=(float)-gm.user.getUserTranslation()[1]*gm.textures.textureSize;

        int Yc = (int)gm.user.y-gm.renderY;

        float Xcr = Xst;
        float Ycr = Yst;

        for(; Yc < (int)gm.user.y+gm.renderY; Yc++)
        {
            Xcr=Xst;
            for(int Xc = (int)gm.user.x-gm.renderX; Xc < (int)gm.user.x+gm.renderX; Xc++)
            {
                String current = Integer.toString((int)Xc)+":"+Integer.toString((int)Yc);
                    if(gm.world.containsKey(current) && gm.blocks.get(gm.world.get(current).get("mid")) != null && gm.textures.get(gm.blocks.get(gm.world.get(current).get("mid")).texture) != null)
                    {
                        if(gm.world.get(current).containsKey("top") && gm.blocks.containsKey(gm.world.get(current).get("top")) && gm.blocks.get(gm.world.get(current).get("top")).isStatic)
                        {
                            float tX=gm.blocks.get(gm.world.get(current).get("top")).translationX;
                            float tY = gm.blocks.get(gm.world.get(current).get("top")).translationY;
                            canvas.drawBitmap(gm.textures.get(gm.blocks.get(gm.world.get(current).get("top")).texture), Xcr+tX, Ycr+tY, paint);
                        } else {
                            canvas.drawBitmap(gm.textures.get(gm.blocks.get(gm.world.get(current).get("mid")).texture), Xcr, Ycr, paint);

                        }

                    } else {
                        canvas.drawBitmap(gm.textures.get("water"), Xcr, Ycr, null);
                    }
                Xcr+=(float)gm.textures.textureSize;
            }
            Ycr+=(float)gm.textures.textureSize;
        }
        down=System.currentTimeMillis()-time;
    }

    public void drawShadowLayer(Canvas canvas)
    {
        long time = System.currentTimeMillis();

        double bx = gm.user.getUserTarget()[0];
        double by = gm.user.getUserTarget()[1];

        String target =Integer.toString((int)bx)+":"+Integer.toString((int)by);

        long size = gm.world.get("configuration").get("size");

        float Yst =0-(gm.renderY*gm.textures.textureSize);
        float Xst =0-(gm.renderX*gm.textures.textureSize);

        String Xs = Double.toString(gm.user.x);
        String Ys = Double.toString(gm.user.y);

        int uCx = (int)gm.user.x;
        int uCy = (int)gm.user.y;

        Xst+=(float)-gm.user.getUserTranslation()[0]*gm.textures.textureSize;

        Yst+=(float)-gm.user.getUserTranslation()[1]*gm.textures.textureSize;

        float Xcr = Xst;
        float Ycr = Yst;

        for(float Yc =(float)(gm.user.y-gm.renderY);(int) Yc < (int)(gm.user.y+gm.renderY); Yc++)
        {
            Xcr=Xst;
            for(float Xc = (float) (gm.user.x-gm.renderX); Xc < (float)(gm.user.x+gm.renderX); Xc++)
            {
                String current = Integer.toString((int)Xc)+":"+Integer.toString((int)Yc);

                if(Xc >= 0  && (int)Xc <= size-1 && Yc >= 0 && (int)Yc <= size-1)
                {


                    if(gm.world.containsKey(current) && gm.blocks.get(gm.world.get(current).get("top")) != null && gm.blocks.get(gm.world.get(current).get("top")).canShadow)
                    {
                        float tX=gm.blocks.get(gm.world.get(current).get("top")).translationX;
                        float tY = gm.blocks.get(gm.world.get(current).get("top")).translationY;

                        Bitmap init2 = gm.textures.get(gm.blocks.get(gm.world.get(current).get("top")).texture+"_shadow");

                        Matrix matrix = new Matrix();

                        matrix.postSkew((float)Math.sin(Math.toRadians((double)gm.Shadow)),0 , init2.getWidth()/2, init2.getHeight()/2);

                        matrix.postTranslate(Xcr+tX, Ycr+tY);

                        canvas.drawBitmap(init2,matrix , paint);



                    }
                }
                Xcr+=(float)gm.textures.textureSize;
            }
            Ycr+=(float)gm.textures.textureSize;
        }
        shadow=System.currentTimeMillis()-time;
    }

    public void drawUpLayer(Canvas canvas)
    {
        long time = System.currentTimeMillis();

        double bx =gm.user.getUserTarget()[0];
        double by =gm.user.getUserTarget()[1];

        //String target =Integer.toString((int)bx)+":"+Integer.toString((int)by);

        long size = gm.world.get("configuration").get("size");

        float Yst =0-(gm.renderY*gm.textures.textureSize);
        float Xst =0-(gm.renderX*gm.textures.textureSize);

        String Xs = Double.toString(gm.user.x);
        String Ys = Double.toString(gm.user.y);

        int uYt = (int)Math.ceil(gm.user.y);

        Xst+=(float)-gm.user.getUserTranslation()[0]*gm.textures.textureSize;

        Yst+=(float)-gm.user.getUserTranslation()[1]*gm.textures.textureSize;

        try{
            if(gm.blocks.get(gm.world.get(Integer.toString((int)gm.user.x)+":"+Integer.toString((int)gm.user.y)).get("top")).translationY < 0)
            {
                uYt+=1;
            }
        }catch(Exception err){}

        float Xcr = Xst;
        float Ycr = Yst;

        gm.user.isDraw = false;

        for(String id : gm.dUsers.keySet())
        {
            gm.dUsers.get(id).isDraw = false;
        }



        for(float Yc =(float)(gm.user.y-gm.renderY);(int) Yc < (int)(gm.user.y+gm.renderY); Yc++)
        {
            Xcr=Xst;
            for(float Xc = (float) (gm.user.x-gm.renderX); Xc < (float)(gm.user.x+gm.renderX); Xc++)
            {
                String current = Integer.toString((int)Xc)+":"+Integer.toString((int)Yc);

                for(String id : gm.dUsers.keySet())
                {
                    int x = (int)gm.dUsers.get(id).Yt;
                    if((int)Yc == x && !gm.dUsers.get(id).isDraw)
                    {
                        float Xd = Xcr+(gm.dUsers.get(id).tX)+((int)(gm.dUsers.get(id).x)-(int)Xc)*gm.textures.textureSize;

                        float Yd = Ycr+(gm.dUsers.get(id).tY)+((int)gm.dUsers.get(id).y-(int)Yc)*gm.textures.textureSize-gm.textures.textureSize;

                        drawUser(Xd, Yd, gm.dUsers.get(id),canvas);

                        gm.dUsers.get(id).isDraw=true;
                    }
                }

                if((int)Yc == (int)uYt && !gm.user.isDraw)
                {
                    drawUser(0, 0-(int) gm.textures.textureSize ,gm.user, canvas);
                }

                if(Xc >= 0  && (int)Xc <= size-1 && Yc >= 0 && (int)Yc <= size-1)
                {
                    if(gm.world.containsKey(current) && gm.world.get(current).get("top") != 0)
                    {

                        if(gm.blocks.containsKey(gm.world.get(current).get("top")))
                        {
                            float tX=gm.blocks.get(gm.world.get(current).get("top")).translationX;
                            float tY = gm.blocks.get(gm.world.get(current).get("top")).translationY;
                            if(!gm.blocks.get(gm.world.get(current).get("top")).isStatic)
                            {
                                String texture="";
                                if(gm.attributes.containsKey(current) && gm.attributes.get(current).containsKey("texture") && gm.textures.containsKey(gm.attributes.get(current).get("texture")))
                                {
                                    texture =(String) gm.attributes.get(current).get("texture");
                                } else {
                                    texture = gm.blocks.get(gm.world.get(current).get("top")).texture;
                                }
                                if(gm.user.isDraw && gm.blocks.get(gm.world.get(current).get("top")).canTransparency && Math.pow(Xc-gm.user.x, 2)+Math.pow(Yc-gm.user.y, 2) <= Math.pow(gm.transparency, 2))
                                {
                                    texture+="_transparency";
                                }
                                canvas.drawBitmap(gm.textures.get(texture), Xcr+tX, Ycr+tY, paint);

                            }
                        } else {
                            canvas.drawBitmap(gm.textures.get("barrier"), Xcr, Ycr, null);
                        }
                    }
                }

                if(gm.user.wehicle == 0)
                {
                    if(current.equals(gm.target))
                    {
                        if(Xc >= 0  && (int)Xc <= size-1 && Yc >= 0 && (int)Yc <= size-1)
                        {

                            gm.buttons.get("delete").enabled=true;
                            gm.buttons.get("copy").enabled=true;
                            gm.buttons.get("gas").enabled=false;
                            gm.buttons.get("brake").enabled=false;

                            if(gm.world.containsKey(gm.target) && gm.world.get(gm.target).get("top") != 0 && gm.blocks.containsKey(gm.world.get(gm.target).get("top")) && gm.blocks.get(gm.world.get(gm.target).get("top")).isUsable && gm.isAcess("isUse", gm.target))
                            {
                                gm.buttons.get("use").enabled=true;
                                canvas.drawBitmap(gm.textures.get("indicator3"), Xcr, Ycr, null);

                            }
                            else
                            {
                                gm.buttons.get("use").enabled=false;
                            }

                            if(gm.world.containsKey(gm.target) && gm.world.get(gm.target).get("top") != 0 && gm.blocks.containsKey(gm.world.get(gm.target).get("top")) && gm.blocks.get(gm.world.get(gm.target).get("top")).isWehicle && gm.isAcess("isUse", gm.target))
                            {
                                gm.buttons.get("ride").enabled=true;
                            } else {
                                gm.buttons.get("ride").enabled=false;
                            }

                            if(gm.user.inventory[gm.user.item-1] != 0 &&  gm.isAcess("isEdit", gm.target))
                            {
                                gm.buttons.get("place").enabled=true;
                                if(gm.blocks.get(gm.user.inventory[gm.user.item-1]).canRotate)
                                {
                                    gm.buttons.get("rotate").enabled=true;
                                } else {
                                    gm.buttons.get("rotate").enabled=false;
                                }

                                if(gm.blocks.get(gm.user.inventory[gm.user.item-1]).isCombinable)
                                {
                                    gm.buttons.get("mode").enabled=true;
                                } else {
                                    gm.buttons.get("mode").enabled=false;
                                }

                                canvas.drawBitmap(gm.textures.get("indicator1"), Xcr, Ycr, null);
                            } else {
                                if( gm.isAcess("isEdit", gm.target))
                                {
                                    gm.buttons.get("mode").enabled=false;
                                    gm.buttons.get("rotate").enabled=false;
                                    gm.buttons.get("place").enabled=false;
                                    canvas.drawBitmap(gm.textures.get("showel"), Xcr, Ycr, null);
                                }
                            }

                        }
                    }
                } else {
                    gm.buttons.get("mode").enabled=false;
                    gm.buttons.get("rotate").enabled=false;
                    gm.buttons.get("place").enabled=false;
                    gm.buttons.get("delete").enabled=false;
                    gm.buttons.get("copy").enabled=false;
                    gm.buttons.get("gas").enabled=true;
                    gm.buttons.get("brake").enabled=true;
                }
                Xcr+=(float)gm.textures.textureSize;
            }
            Ycr+=(float)gm.textures.textureSize;
        }
        up=System.currentTimeMillis()-time;


    }
}
