package com.atr.lowadventure;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;


import androidx.core.graphics.ColorUtils;

import com.atr.geometry.CollisionModel;
import com.atr.geometry.Dot;
import com.atr.geometry.Figure;
import com.atr.geometry.Line;
import com.atr.lowadventure.activity.GameActivity;
import com.atr.lowadventure.activity.SignPageActivity;
import com.atr.lowadventure.item.Block;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;


public class GameScreen extends SurfaceView implements SurfaceHolder.Callback {
    public final GameActivity gameActivity;
    public DrawThread drawThread;
    public User user;
    public GameScreen screen;
    public int scaleSize, buttonSize, guiSize;
    public int renderX, renderY;
    public boolean drawShadow;
    public int transparency;
    public Typeface font;
    public blockSorter sorter;
    public String mapId;

    public String target="";

    final LinearLayout gamelinear;

    public Context context;
    public int Shadow =0;
    public boolean isOnline = true;

    public SJoystick joystick;
    public HashMap<String, CollisionModel> collisions;
    public HashMap<String, SButton> buttons;
    public Textures textures;
    public HashMap<Integer, Block> blocks;
    public ArrayList<SButton> gamebuttons;


    World world;
    HashMap<String, HashMap<String, Object>> attributes;

    HashMap<String, User> users;
    HashMap<String, User> dUsers;

    boolean push = false;
    int AnimateType = 0;

    public GameScreen(GameActivity gameActivity, Context context, final LinearLayout v)
    {
        super(context);
        this.gameActivity = gameActivity;
        this.context = context;
        this.gamelinear=v;
        font = Typeface.createFromAsset(context.getAssets(),
                "font/retrogaming.ttf");
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        drawThread = new DrawThread(gameActivity, getHolder(), this);
        gameActivity._WriteLogInFile("surface create");
        drawThread.pausedFlag=true;
        drawThread.runningFlag=true;
        drawThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }


    @Override
    public boolean onTouchEvent(MotionEvent event)
    {

        ArrayList<Float> xlist = new ArrayList<Float>();

        ArrayList<Float> ylist = new ArrayList<Float>();

        int up = -1;

        int actionMask = event.getActionMasked();
        int actionIndex =event.getActionIndex();
            int pointerCount = event.getPointerCount();
           if(pointerCount == 0)
        {
            push = false;
            joystick.pushed=false;
        }
            switch (actionMask) {
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_POINTER_DOWN:
                    push=true;
            break;
                case MotionEvent.ACTION_UP:
              push=false;
            pointerCount=-1;
              case MotionEvent.ACTION_POINTER_UP:
                up=actionIndex;
            pointerCount--;
                case MotionEvent.ACTION_MOVE:

            xlist.clear();
            ylist.clear();

                  for (int i = 0; i < pointerCount; i++) {
                if(i != up)
                {         try{
                                  xlist.add(event.getX(i));
                                  ylist.add(event.getY(i));
                    }catch(Exception err){}
                }
                      }

                  boolean push1=false;
            for(String k : buttons.keySet())
            {
                buttons.get(k).checkPushed(xlist, ylist, pointerCount);
                if(buttons.get(k).getPushed()) push1 = true;
            }



            joystick.checkPushed(xlist, ylist, pointerCount);
                    if(joystick.getPushed()) push1 = true;

                    for(int i=0; i < gamebuttons.size(); i++) {
                        gamebuttons.get(i).checkPushed(xlist, ylist, pointerCount);
                        if(gamebuttons.get(i).getPushed() && !push1){
                            user.setTarget((int)(user.x)+gamebuttons.get(i).argument1,(int)(user.y)+gamebuttons.get(i).argument2);
                            target=Integer.toString((int)(user.x)+gamebuttons.get(i).argument1)+":"+Integer.toString((int)(user.y)+gamebuttons.get(i).argument2);
                        }
                    }

                if(pointerCount == 0)
            {
                push = false;
                joystick.pushed=false;
            }
            if(push)
            {
                if(buttons.get("pallete").getPushed())
                {
                    push = false;
                    ColorPickerDialog.OnColorChangedListener listener = new ColorPickerDialog.OnColorChangedListener() {
                        @Override
                        public void colorChanged(String key, int color) {
                            user.colorFilter=color;
                        }
                    };
                    LoWDialog low =new LoWDialog(context);
                    ColorPickerDialog.ColorPickerView pick = new ColorPickerDialog.ColorPickerView(context, listener,user.colorFilter,user.colorFilter);
                    pick.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                    low.setView(pick);
                    low.setBackground(textures.get("dialogbackground"));
                    low.setIcon(textures.get("palletebutton"));
                    low.setTitle("Выбор цвета");
                    low.show();
                }
                if(buttons.get("map").getPushed())
                {
                    push = false;
                    ShowMap(100);
                } if(buttons.get("inventory").getPushed())
                {
                    push = false;
                    ShowInventory();
                } if(buttons.get("login").getPushed())
                {
                    push = false;
                    Intent intent = new Intent(context, SignPageActivity.class);
                    gameActivity.startActivity(intent);
                    gameActivity.finish();
                }  if(buttons.get("ride").getPushed())
                {
                    push=false;
                    useWehicle();
                }if(buttons.get("use").getPushed())
                {
                    push=false;
                    useBlock();
                } if(buttons.get("delete").getPushed())
                {
                    push=false;
                    WorldEdit("delete");
                } if(buttons.get("mode").getPushed())
                {
                    push=false;
                    WorldEdit("mode");
                } if(buttons.get("place").getPushed())
                {
                    push=false;
                    WorldEdit("place");
                } if(buttons.get("copy").getPushed())
                {
                    push=false;
                    WorldEdit("copy");
                } if(buttons.get("gas").getPushed())
                {
                    user.xv=0.5;
                } if(buttons.get("brake").getPushed())
                {
                    user.xv=-0.5;
                }
                if(!buttons.get("brake").getPushed() && !buttons.get("gas").getPushed())
                {
                    user.xv=0;
                }

                if(joystick.getPushed())
                {
                    if(user.wehicle==0)
                    {
                        user.setVector(joystick.getAxis()[0],joystick.getAxis()[1]);
                    } else {
                        user.yv=joystick.getAxis()[0];
                    }
                } else
                {
                    if(user.wehicle==0)
                    {
                        user.xv=0;
                        user.yv=0;
                    } else {
                        user.yv=0;
                    }
                    joystick.pushed=false;
                }


                for(int z=1; z < 6; z++)
                {
                    if(buttons.get("inventory"+z).getPushed())
                    {
                        user.item=z;

                    }
                }
            } else {
                if(user.wehicle==0)
                {
                    user.xv=0;
                    user.yv=0;
                } else {
                    user.yv=0;
                }
                joystick.pushed=false;
            }
                  break;
                }



        return true;
                }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
                boolean retry = true;
                // завершаем работу потока
                drawThread.setRunning(false);
                while (retry) {
                        try {
                                drawThread.join();
                                retry = false;
                            } catch (Exception e) {
                                // если не получилось, то будем пытаться еще и еще
                            }
                    }
            }

    public void start()
    {
        gameActivity._WriteLogInFile("surface created");
        initMap();
        gameActivity._WriteLogInFile("buttons init done");
        loadUser();
        loadAttributes();
    }
    public void initMap()
    {
        world = new World(this);
        attributes = new HashMap<String, HashMap<String, Object>>();
        users = new HashMap<String, User>();
        dUsers = new HashMap<String, User>();
    }
    public void usersInit()
    {
        dUsers.clear();

        for(String id : users.keySet())
        {
            if(users.get(id).x >= user.x-renderX && users.get(id).x <= user.x+renderX && users.get(id).y >= user.y-renderY && users.get(id).y <= user.y+renderY)
            {

                dUsers.put(id, users.get(id));

                dUsers.get(id).tX=0;
                dUsers.get(id).tY=0;
                dUsers.get(id).Yt=0;

                dUsers.get(id).Yt=(int)dUsers.get(id).y;
                try{
                    if(blocks.get(world.get(Integer.toString((int)dUsers.get(id).x)+":"+Integer.toString((int)dUsers.get(id).y)).get("top")).translationY < 0)
                    {
                        dUsers.get(id).Yt++;
                    }
                }catch(Exception err){}

                String Xs = Double.toString(dUsers.get(id).x);
                String Ys = Double.toString(dUsers.get(id).y);


                dUsers.get(id).tX=(float)-dUsers.get(id).getUserTranslation()[0]*textures.textureSize;

                dUsers.get(id).tY=(float)-dUsers.get(id).getUserTranslation()[1]*textures.textureSize;
                dUsers.get(id).Yt+=(int)Math.ceil(dUsers.get(id).y)-(int)dUsers.get(id).y;


            }
        }

    }

    public void initButtons(){
        gamebuttons=new ArrayList<SButton>();

        int rendX=2;
        int rendY=2;
        float Yst=gamelinear.getHeight()/2-(rendY*textures.textureSize*scaleSize);
        float Xst =gamelinear.getWidth()/2-(rendX*textures.textureSize*scaleSize);
        Xst+=(float)-user.getUserTranslation()[0]*textures.textureSize*scaleSize;
        Yst+=(float)-user.getUserTranslation()[1]*textures.textureSize*scaleSize;

        float Xcr = Xst;
        float Ycr = Yst;

        for(int y=-rendY; y<= rendY; y++){
            Xcr=Xst;
            for(int x=-rendX; x<= rendX; x++) {
                gamebuttons.add(new SButton(Xcr, Ycr, Bitmap.createBitmap(textures.textureSize*scaleSize,textures.textureSize*scaleSize, Bitmap.Config.ARGB_8888), x, y));
                Xcr+=textures.textureSize*scaleSize;
            }
            Ycr+=textures.textureSize*scaleSize;
        }
        user.setTarget((int)user.x,(int) user.y);
        target=(int)user.getUserTarget()[0]+":"+(int)user.getUserTarget()[1];
    }

    public void loadMap()
    {
        try{
            com.google.firebase.database.DatabaseReference connectedRef = com.google.firebase.database.FirebaseDatabase.getInstance().getReference("LoW/games/"+ mapId +"/map");

            connectedRef.addListenerForSingleValueEvent(new ValueEventListener() {
                  @Override
                  public void onDataChange(DataSnapshot dataSnapshot)
                {
                    world.setWorld(dataSnapshot);
                    gameActivity._WriteLogInFile("starting...");
                    requestInit();
                    usersInit();
                    initButtons();
                    gamelinear.removeAllViews();
                    gamelinear.addView(screen);

                } @Override public void onCancelled(com.google.firebase.database.DatabaseError err) {
                    gameActivity._WriteLogInFile(err.toString());}});
        }catch(Exception err){  gameActivity._WriteLogInFile("LOADMAP: "+err.toString());}
    }

    public void loadUser()
    {
        com.google.firebase.database.DatabaseReference connectedRef = com.google.firebase.database.FirebaseDatabase.getInstance().getReference("LoW/games/"+ mapId +"/users");

        connectedRef.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
              @Override
              public void onDataChange(com.google.firebase.database.DataSnapshot ds1)
            {

                String id = com.google.firebase.auth.FirebaseAuth.getInstance().getCurrentUser().getUid();
                if(!ds1.child(id).exists())
                {
                    user.x=50;
                    user.y=50;
                    buttonSize=160;
                    scaleSize=3;
                    renderX=7;
                    renderY=6;
                    user.texture="pu1";
                    drawShadow=true;
                    transparency=3;

                                      HashMap<String, Object> result = new HashMap<String, Object>();
                    result.put("renderX", renderX);
                    result.put("renderY", renderY);
                    result.put("buttonSize", buttonSize);
                    result.put("scaleSize", scaleSize);
                    result.put("texture", user.texture);
                    result.put("drawShadow", drawShadow);
                    result.put("transparency", transparency);

                    updateHashMap(result, "LoW/games/test/users/"+id);

                } else {

                    com.google.firebase.database.DataSnapshot ds = ds1.child(id);

                    double x;
                    double y;

                    if(ds.child("x").getValue() instanceof Double)
                    {
                        x= (Double)isValid(ds, "x", (Double)0.0);
                    } else {
                        x= ((Long)isValid(ds, "x", (long)0)).doubleValue();
                    }

                    if(ds.child("y").getValue() instanceof Double)
                    {
                        y= (Double)isValid(ds, "y", (Double)0.0);
                    } else {
                        y= ((Long)isValid(ds, "y", (long)0)).doubleValue();
                    }
                    user.x=x;
                    user.y=y;

                    if((String)isValid(ds, "name", null) == null)
                    {

                    }
                    user.hatname=(String)isValid(ds, "hatname", "none");
                    user.hairname=(String)isValid(ds, "hairname", "none");
                    user.glassname=(String)isValid(ds, "glassname", "none");
                    user.topname=(String)isValid(ds, "topname", "none");
                    user.midname=(String)isValid(ds, "midname", "none");

                    buttonSize=(int)((long)isValid(ds, "buttonSize", (long)160));
                    scaleSize =(int)((long)isValid(ds, "scaleSize", (long)3));
                    renderX=(int)((long)isValid(ds, "renderX", (long)7));
                    renderY=(int)((long)isValid(ds, "renderY", (long)5));
                    drawShadow=(boolean)isValid(ds, "drawShadow", true);
                    transparency = (int)((long)isValid(ds, "transparency", (long)3));
                }
                gameActivity._WriteLogInFile("user loaded");
                textures = new Textures(GameScreen.this);
                textures.init();
                BlocksInit();
                SButtonInit();
                blockSorterInit();
                collisionInit();
                loadMap();


            } @Override public void onCancelled(com.google.firebase.database.DatabaseError err) {
                gameActivity._WriteLogInFile(err.toString());}});

    }

    public void loadAttributes()
    {
        com.google.firebase.database.DatabaseReference connectedRef = com.google.firebase.database.FirebaseDatabase.getInstance().getReference("LoW/games/"+ mapId +"/attributes");

        connectedRef.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
              @Override
              public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot)
            {
                for (com.google.firebase.database.DataSnapshot ds : dataSnapshot.getChildren())
                {
                    String id = ds.getKey();
                    if(! attributes.containsKey(id))
                    {
                        attributes.put(id, new HashMap<String, Object>());
                    }
                    attributes.get(id).put("text", (String)isValid(ds,"text",""));
                    attributes.get(id).put("link", (String)isValid(ds,"link",""));
                    attributes.get(id).put("texture", (String)isValid(ds,"texture",""));
                    attributes.get(id).put("x", (long)isValid(ds,"x",(long)0));
                    attributes.get(id).put("y", (long)isValid(ds,"y",(long)0));
                    attributes.get(id).put("size", (long)isValid(ds,"size",(long)0));
                    attributes.get(id).put("author", (String)isValid(ds,"author",""));
                    attributes.get(id).put("whitelist", (String)isValid(ds,"whitelist",""));
                    attributes.get(id).put("isWalk", (boolean)isValid(ds,"isWalk",true));
                    attributes.get(id).put("isEdit", (boolean)isValid(ds,"isEdit",true));
                    attributes.get(id).put("isUse", (boolean)isValid(ds,"isUse",true));
                    attributes.get(id).put("isOn", (boolean)isValid(ds,"isOn",true));
                }

            } @Override public void onCancelled(com.google.firebase.database.DatabaseError err) {
                gameActivity._WriteLogInFile(err.toString());}});

    }
    public void requestInit()
    {
        com.google.firebase.database.DatabaseReference connectedRef = com.google.firebase.database.FirebaseDatabase.getInstance().getReference("LoW/games/"+ mapId +"/request");

        com.google.firebase.database.ValueEventListener listener = connectedRef.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
                    @Override
                    public void onDataChange(com.google.firebase.database.DataSnapshot snapshot) {
                for (com.google.firebase.database.DataSnapshot ds : snapshot.getChildren())
                {
                    long value = (long)isValid(ds, "value", (long)1);

                    if(value == 1)
                    {
                        long mid = (long)isValid(ds, "mid", (long)7);
                        long top = (long)isValid(ds, "top", (long)0);
                        String coordinate = ds.getKey();

                        if(!world.containsKey(coordinate))
                        {
                            world.put(coordinate, new HashMap<String, Integer>());
                        }

                        world.get(coordinate).put("mid",(int) mid);
                        world.get(coordinate).put("top",(int) top);
                    } else if(value == 2)
                    {
                        if(!ds.getKey().equals(com.google.firebase.auth.FirebaseAuth.getInstance().getCurrentUser().getUid()))
                        {
                            double x;
                            double y;

                            if(ds.child("x").getValue() instanceof Double)
                            {
                                x= (Double)isValid(ds, "x", (Double)0.0);
                            } else {
                                x= ((Long)isValid(ds, "x", (long)0)).doubleValue();
                            }

                            if(ds.child("y").getValue() instanceof Double)
                            {
                                y= (Double)isValid(ds, "y", (Double)0.0);
                            } else {
                                y= ((Long)isValid(ds, "y", (long)0)).doubleValue();
                            }


                            String id = ds.getKey();
                            String texture = (String)isValid(ds, "texture", (String)"pd1");
                            String hat = (String)isValid(ds, "hat", null);
                            String hair = (String)isValid(ds, "haie", null);
                            String glass = (String)isValid(ds, "glass", null);
                            String top = (String)isValid(ds, "top", null);
                            String mid = (String)isValid(ds, "mid", null);
                            String name = (String)isValid(ds, "name", null);
                            if(!users.containsKey(id))
                            {
                                users.put(id, new User(x, y, texture));
                            }
                            users.get(id).x=x;
                            users.get(id).y=y;
                            users.get(id).texture=texture;
                            users.get(id).hat=hat;
                            users.get(id).hair=hair;
                            users.get(id).glass=glass;
                            users.get(id).top=top;
                            users.get(id).mid=mid;
                            users.get(id).name=name;

                        }

                    } else if(value == 3)
                    {
                        String id = ds.getKey();
                        if(!attributes.containsKey(id))
                        {
                            attributes.put(id, new HashMap<String, Object>());
                        }
                        attributes.get(id).put("text", (String)isValid(ds,"text",""));
                        attributes.get(id).put("link", (String)isValid(ds,"link",""));
                        attributes.get(id).put("texture", (String)isValid(ds,"texture",""));
                        attributes.get(id).put("x", (long)isValid(ds,"x",(long)0));
                        attributes.get(id).put("y", (long)isValid(ds,"y",(long)0));
                        attributes.get(id).put("size", (long)isValid(ds,"size",(long)0));
                        attributes.get(id).put("author", (String)isValid(ds,"author",""));
                        attributes.get(id).put("whitelist", (String)isValid(ds,"whitelist",""));
                        attributes.get(id).put("isWalk", (boolean)isValid(ds,"isWalk",true));
                        attributes.get(id).put("isEdit", (boolean)isValid(ds,"isEdit",true));
                        attributes.get(id).put("isUse", (boolean)isValid(ds,"isUse",true));
                        attributes.get(id).put("isOn", (boolean)isValid(ds,"isOn",true));

                    }
                }

                if(snapshot.getChildrenCount() > 30)
                {
                    setHashMap(null, "LoW/games/test/request");
                }
                usersInit();
            }
            @Override public void onCancelled(com.google.firebase.database.DatabaseError err) {
                gameActivity._WriteLogInFile(err.toString());}});
    }
    public boolean checkCollision()
    {
        double x = user.x;
        double y= user.y;
        double xv = user.xv;
        double yv=user.yv;

        double nx = x;
        double ny=y;

        if(xv>0)
        {
            nx+=xv;
        }

        if(yv>0)
        {
            ny+=yv;
        }

        String target=Integer.toString((int)nx)+":"+Integer.toString((int)ny);

        boolean b1;
        boolean b2;

        if(world.containsKey(target))
        {

            if( world.get(target).containsKey("top") && blocks.containsKey(world.get(target).get("top")))
            {
                b1 = blocks.get(world.get(target).get("top")).isWall;
            } else {
                b1=false;
            }

            if( world.get(target).containsKey("mid") && blocks.containsKey(world.get(target).get("mid")))
            {
                b2 =blocks.get(world.get(target).get("mid")).isWall;
            } else {
                b2=false;
            }
        } else { return false;}

        return !(b1 || b2);


    }
    public boolean checkCollision2(float x, float y)
    {


        String target=Integer.toString((int)Math.ceil(x))+":"+Integer.toString((int)Math.ceil(y));

        boolean b1;
        boolean b2;

        if(world.containsKey(target))
        {

            if( world.get(target).containsKey("top") && blocks.containsKey(world.get(target).get("top")))
            {
                b1 = blocks.get(world.get(target).get("top")).isWall;
            } else {
                b1=false;
            }

            if( world.get(target).containsKey("mid") && blocks.containsKey(world.get(target).get("mid")))
            {
                b2 =blocks.get(world.get(target).get("mid")).isWall;
            } else {
                b2=false;
            }
        } else { return false;}

        return !(b1 || b2);


    }
    public Object isValid(com.google.firebase.database.DataSnapshot ds, String key, Object elseValue)
    {
        if(ds.hasChild(key))
        {

            return ds.child(key).getValue(Object.class);

        }
        return elseValue;
    }
    public void setHashMap(HashMap<String, Object> result, String uri)
    {
        com.google.firebase.database.DatabaseReference mDatabase = com.google.firebase.database.FirebaseDatabase.getInstance().getReference();

        mDatabase.child(uri).setValue(result).addOnSuccessListener(new com.google.android.gms.tasks.OnSuccessListener<Void>()
        {@Override public void onSuccess(Void aVoid) {}}).addOnFailureListener(new com.google.android.gms.tasks.OnFailureListener()
        {@Override public void onFailure(Exception e){}});
    }
    public void updateHashMap(HashMap<String, Object> result, String uri)
    {
        com.google.firebase.database.DatabaseReference mDatabase = com.google.firebase.database.FirebaseDatabase.getInstance().getReference();

        mDatabase.child(uri).updateChildren(result).addOnSuccessListener(new com.google.android.gms.tasks.OnSuccessListener<Void>()
        {@Override public void onSuccess(Void aVoid) {}}).addOnFailureListener(new com.google.android.gms.tasks.OnFailureListener()
        {@Override public void onFailure(Exception e){}});
    }
    public void updateHashMap1(HashMap<String,HashMap<String, Object>> result, String uri)
    {
        for(String id : result.keySet())
        {
            updateHashMap(result.get(id), uri+"/"+id);
        }
    }
    public Object getter(String coord, String key, boolean b)
    {
        if(world.containsKey(coord) && world.get(coord).containsKey(key))
        {
            return world.get(coord).get(key);
        } else if(key == "top" && !b && (String)getter(coord, "link", true) != "")
        {
            return world.get((String)getter(coord, "link", true)).get(key);
        }
        return null;
    }
    public boolean containsGuard(String target)
    {
        String id = com.google.firebase.auth.FirebaseAuth.getInstance().getCurrentUser().getUid();
        if(attributes.containsKey(target) && attributes.get(target).get("link") != null && attributes.containsKey((String)attributes.get(target).get("link")) && attributes.get((String)attributes.get(target).get("link")).get("isOn") != null  && !((String)(attributes.get((String)attributes.get(target).get("link")).get("author"))).equals(id))
        {
            return false;
        }
        return true;
    }
    public boolean isAcess(String key, String target)
    {
        String id = com.google.firebase.auth.FirebaseAuth.getInstance().getCurrentUser().getUid();
        if(attributes.containsKey(target) && attributes.get(target).get("link") != null && attributes.containsKey((String)attributes.get(target).get("link")) && (boolean)(attributes.get((String)attributes.get(target).get("link")).get("isOn")) &&!(boolean)(attributes.get((String)attributes.get(target).get("link")).get(key)) && !((String)(attributes.get((String)attributes.get(target).get("link")).get("author"))).equals(id))
        {
            return false;
        }
        return true;
    }
    //Новые функции инициализации
    public void useWehicle()
    {
        if(user.wehicle == 0)
        {

            user.wehicle=world.get(target).get("top");
            user.rotation=0;
            user.x=user.getUserTarget()[0];
            user.y=user.getUserTarget()[1];
            user.update();

            HashMap<String, Object> result = new HashMap<String, Object>();
            result.put("top", 0);
            result.put("mid",world.get(target).get("mid"));

            updateHashMap(result,"LoW/games/"+ mapId +"/map/"+target);
            result.put("value", 1);
            updateHashMap(result,"LoW/games/"+ mapId +"/request/"+target);

            switch(user.wehicle)
            {
                case 65:
                user.texture="car1r1";
                user.update();
                break;
            }
        } else {
            leaveWehicle();
        }
    }
    public void leaveWehicle()
    {

        String coordinate =Integer.toString((int)user.x)+":"+Integer.toString((int)user.y);

        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("top",user.wehicle);
        result.put("color", user.carcolor);
        result.put("rotation", user.rotation);
        if(world.containsKey(coordinate) && world.get(coordinate).containsKey("mid"))
        {
            result.put("mid",world.get(coordinate).get("mid"));
        } else {
            result.put("mid", 7);
        }
        updateHashMap(result,"LoW/games/"+ mapId +"/map/"+coordinate);

        result.put("value", 1);
        updateHashMap(result,"LoW/games/"+ mapId +"/request/"+coordinate);

        user.texture="pd1";
        user.rotation=0;
        user.carcolor=0;
        user.wehicle=0;
        user.Wspeed=0;
        user.update();
    }
    public void drawWehicle(Canvas c, float x, float y, User u)
    {
        switch(u.wehicle)
        {
            case 65:
            if(u.rotation > 360) {u.rotation=u.rotation-360;}
            if(u.rotation < 0) {u.rotation=360+u.rotation;}
            float a;

            if(u.rotation < 90 || u.rotation > 270)
            {
                a= u.rotation;
                if(u.yv > 0)
                {
                    if(u.xv>0)
                    {
                        u.texture = "car1r4";
                    } else {
                        u.texture = "car1r5";
                    }
                } else if(u.yv<0){
                    if(u.xv > 0)
                    {
                        u.texture = "car1r5";
                    } else {
                        u.texture = "car1r4";
                    }
                }else{
                    if(u.Wspeed ==0)
                    {
                        u.texture = "car1r1";
                    } else {
                        u.texture=(gameActivity.getRandom(0,1)==0)?"car1r2":"car1r3";
                    }
                }
            } else {
                a= u.rotation-180;
                if(u.yv > 0)
                {
                    if(u.xv > 0)
                    {
                        u.texture = "car1l5";
                    } else {
                        u.texture = "car1l4";
                    }
                } else if(u.yv<0){
                    if(u.xv > 0)
                    {
                        u.texture = "car1l4";
                    } else {
                        u.texture = "car1l5";
                    }
                }else{
                    if(u.Wspeed ==0)
                    {
                        u.texture = "car1l1";
                    } else {
                        u.texture=(gameActivity.getRandom(0,1)==0)?"car1l2":"car1l3";
                    }
                }
            }
            Paint p = new Paint();
            if(u.colorFilter != 0) p.setColor(u.colorFilter);
            c.drawBitmap(rotateBitmap(textures.get(u.texture), (int)a),x, y-textures.textureSize/2,  p);
            break;
        }
    }
    public void driveWehicle(float xv, float yv)
    {
        float au;
        float ab;

        float a=0;

        float brake;
        float inertion;
        float maxu;
        float maxb;

        switch(user.wehicle)
        {
            case 65:

            au=(float)0.7;
            ab=(float)0.4;
            brake=(float)5;
            inertion=5;
            maxu=15;
            maxb=2;

            float w=0 ;

            if(user.Wspeed != 0)
            {
                if(user.Wspeed > 2 || user.Wspeed < -2)
                {
                    w=(float)(Math.abs(user.Wspeed)*1.5*(user.rotation+yv-user.rotation));
                } else {
                    w=(float)(Math.pow(user.Wspeed, 4)*(user.rotation+yv-user.rotation));
                }
            }
            user.rotation+=w;

            float xa =(float) Math.cos(Math.toRadians((double)user.rotation));
            float ya =(float) Math.sin(Math.toRadians((double)user.rotation));



            if(xv > 0){
                if(user.Wspeed >= 0)
                {if(user.Wspeed < maxu)
                    {user.Wspeed+=au*xv;
                    } else { user.Wspeed=maxu;}
                    a=au;
                }else if(user.Wspeed < 0) {user.Wspeed+=brake*xv;
                    a=brake;
                }
            }else if(xv < 0)
            {
                if(user.Wspeed > 0)
                {
                    user.Wspeed+=brake*xv;
                    a=brake;
                } else if(user.Wspeed <= 0)
                {
                    if(user.Wspeed> -maxb)
                    {
                        user.Wspeed+=ab*xv;
                    } else {
                        user.Wspeed=-maxb;
                    }
                    a=ab;


                }
            } else {
                if(user.Wspeed > 0 && user.Wspeed-inertion*0.1+(inertion*0.1)/2 >= 0) {
                    user.Wspeed-=inertion*0.1+(inertion*0.1)/2;
                    a=inertion;
                } else if(user.Wspeed < 0 && user.Wspeed+inertion*0.1+(inertion*0.1)/2 <= 0){
                    user.Wspeed+=inertion*0.1+(inertion*0.1)/2;
                    a=inertion;
                }
            }
            if(checkCollision2((float)(user.x+xa*(user.Wspeed*0.1+2)+(a*0.1)/2), (float)(user.y+user.Wspeed*0.1*ya+(a*0.1)/2)-1)){
                user.x+=user.Wspeed*0.1*xa+(a*0.1)/2*xa;
                user.y+=user.Wspeed*0.1*ya+(a*0.1)/2*ya;
            }else{user.Wspeed=0;}


            break;
        }
        user.update();
    }
    public void SButtonInit()
    {
        buttons = new HashMap<String, SButton>();

        joystick = new SJoystick(buttonSize*2, gamelinear.getHeight()-buttonSize*2, buttonSize*3/2, textures.get("circlebutton"), textures.get("circlebutton"));

        buttons.put("ride", new SButton((float)(gamelinear.getWidth()-buttonSize*2),(float) (gamelinear.getHeight()/2-buttonSize) , textures.get("ridebutton")));

        buttons.put("map", new SButton((float)(gamelinear.getWidth()-buttonSize*2),0 , textures.get("map")));

        buttons.put("login", new SButton((float)(gamelinear.getWidth()-buttonSize*3),0 , textures.get("atr")));

        buttons.put("gas", new SButton((float)(gamelinear.getWidth()-buttonSize*2),(float) (gamelinear.getHeight()/2+buttonSize) , textures.get("gasbutton")));

        buttons.put("brake", new SButton((float)(gamelinear.getWidth()-buttonSize*3),(float) (gamelinear.getHeight()/2+buttonSize) , textures.get("brakebutton")));

        buttons.put("delete", new SButton((float)(gamelinear.getWidth()-buttonSize*2),(float) (gamelinear.getHeight()/2+buttonSize) , textures.get("minusbutton")));

        buttons.put("rotate", new SButton((float)(gamelinear.getWidth()-buttonSize*3),(float) (gamelinear.getHeight()/2) , textures.get("buttonrotate")));

        buttons.put("mode", new SButton((float)(gamelinear.getWidth()-buttonSize*3),(float) (gamelinear.getHeight()/2-buttonSize) , textures.get("button1")));

        buttons.put("place", new SButton((float)(gamelinear.getWidth()-buttonSize*2),(float) (gamelinear.getHeight()/2) , textures.get("plusbutton")));

        buttons.put("use", new SButton((float)(gamelinear.getWidth()-buttonSize*2),(float) (gamelinear.getHeight()/2-buttonSize) , textures.get("usebutton")));

        buttons.put("copy", new SButton((float)(gamelinear.getWidth()-buttonSize*3),(float) (gamelinear.getHeight()/2+buttonSize) , textures.get("copybutton")));

        buttons.put("pause", new SButton((float)(gamelinear.getWidth()-buttonSize),0 , textures.get("pause")));

        buttons.put("pallete", new SButton((float)(gamelinear.getWidth()-buttonSize*3),gamelinear.getHeight()-buttonSize, textures.get("palletebutton")));

        buttons.put("inventory", new SButton((float)(gamelinear.getWidth()-buttonSize*4),gamelinear.getHeight()-buttonSize, textures.get("inventory")));

        for(int i=5; i< 10; i++)
        {
            int x=i-4;
            buttons.put("inventory"+x, new SButton((float)(gamelinear.getWidth()-buttonSize*i),gamelinear.getHeight()-buttonSize, textures.get("inventory1")));
        }

       /* for(SButton b : buttons.values())
        {
          b.x-=gamelinear.getWidth()/2;
          b.y-=gamelinear.getHeight()/2;
        }*/

    }
    public void WorldEdit(String op)
    {

        int mode = (buttons.get("mode").b == textures.get("button1")) ? 1 : 2;


        int size = world.get("configuration").get("size");

        HashMap<String, Object> result = new HashMap<String, Object>();

        double bx = user.getUserTarget()[0];
        double by = user.getUserTarget()[1];

        boolean edited = false;



        String coordinate = target;
        if(isAcess("isEdit", coordinate))
        {
            switch(op)
            {
                case "delete":

                if(bx >= 0 && bx < size && by >= 0 && by < size && isAcess("isEdit", coordinate) ){

                    if(world.containsKey(coordinate))
                    {

                        if(world.get(coordinate).get("top")!= 0)
                        {
                            result.put("top", 0);
                            result.put("mid", world.get(coordinate).get("mid"));
                            edited = true;

                        } else if(world.get(coordinate).get("mid")!= 7) {
                            result.put("mid", 7);
                            edited = true;
                        }
                    } else {
                        result.put("mid", 7);
                        result.put("top", 0);
                        edited = true;
                    }
                    if(attributes.containsKey(coordinate) && attributes.get(coordinate).containsKey("link"))
                    {
                        HashMap<String, Object> result3 = new HashMap<String, Object>();
                        result3.put("link",attributes.get(coordinate).get("link"));
                        setHashMap(result3,"LoW/games/"+ mapId +"/attributes/"+coordinate);
                    } else {
                        setHashMap(null,"LoW/games/"+ mapId +"/attributes/"+coordinate);
                    }
                }

                break;
                case "place":
                if(bx >= 0 && bx < size && by >= 0 && by < size && isAcess("isEdit", coordinate)){
                    if(user.inventory[user.item-1] != 0)
                    {




                        if(blocks.get(user.inventory[user.item-1]).isPlacable)
                        {
                            result.put("mid", user.inventory[user.item-1]);
                            if(world.containsKey(coordinate))
                            {
                                result.put("top", world.get(coordinate).get("top"));
                            } else {
                                result.put("top", 0);
                            }
                            edited = true;
                        } else {
                            if(blocks.get(user.inventory[user.item-1]).isUsable)
                            {
                                placeUsableBlock(user.inventory[user.item-1], coordinate);
                            } else {
                                result.put("top", user.inventory[user.item-1]);
                                if(world.containsKey(coordinate))
                                {
                                    result.put("mid", world.get(coordinate).get("mid"));
                                }
                                edited = true;
                            }
                        }
                    }

                }
                break;
                case "copy":

                if(bx >= 0 && bx < size && by >= 0 && by < size ){
                    if(world.containsKey(coordinate))
                    {
                        if(world.get(coordinate).get("top")!= 0)
                        {
                            user.inventory[user.item-1]=world.get(coordinate).get("top");
                        } else if(world.get(coordinate).get("mid")!= 0) {
                            user.inventory[user.item-1]=world.get(coordinate).get("mid");
                        }
                    }
                }
                break;
                case "mode":
                if(mode  == 1)
                {
                    buttons.get("mode").b = textures.get("button2");
                } else {
                    buttons.get("mode").b = textures.get("button1");
                }
                break;
            }
        }
        if(edited)
        {
            updateHashMap(result, "LoW/games/"+ mapId +"/map/"+coordinate);
            result.put("value", 1);
            setHashMap(result, "LoW/games/"+ mapId +"/request/"+coordinate);
        }
    }

    public void useBlock()
    {
        LoWDialog dialog =new LoWDialog(context);

        dialog.setBackground(textures.get("dialogbackground"));

        int color = getResources().getColor(R.color.colorGreen);

        final EditText edittext1= new EditText(context);
        edittext1.setTextColor(color);
        edittext1.setTypeface(font);
        edittext1.setHintTextColor(color);

        TextView t1 = new TextView(context);
        t1.setTypeface(font);
        t1.setTextColor(color);
        TextView t2 = new TextView(context);
        t2.setTypeface(font);
        t2.setTextColor(color);
        TextView t3 = new TextView(context);
        t3.setTypeface(font);
        t3.setTextColor(color);
        TextView t4  = new TextView(context);
        t4.setTypeface(font);
        t4.setTextColor(color);
        TextView t5  = new TextView(context);
        t5.setTypeface(font);
        t5.setTextColor(color);

        final Switch s1 = new Switch(context);
        s1.setTypeface(font);
        s1.setTextColor(color);
        final Switch s2 = new Switch(context);
        s2.setTypeface(font);
        s2.setTextColor(color);
        final Switch s3 = new Switch(context);
        s3.setTypeface(font);
        s3.setTextColor(color);
        final Switch s4 = new Switch(context);
        s4.setTypeface(font);
        s4.setTextColor(color);

        final SeekBar sk1 = new SeekBar(context,null,0,R.style.SeekBar);
        final SeekBar sk2 = new SeekBar(context,null,0,R.style.SeekBar);
        final SeekBar sk3 = new SeekBar(context,null,0,R.style.SeekBar);
        final SeekBar sk4 = new SeekBar(context,null,0,R.style.SeekBar);
        final SeekBar sk5 = new SeekBar(context,null,0,R.style.SeekBar);


        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);

        double bx = user.getUserTarget()[0];
        double by = user.getUserTarget()[1];

        final String target =Integer.toString((int)bx)+":"+Integer.toString((int)by);

        long size = world.get("configuration").get("size");
        if(isAcess("isUse", target))
        {
            switch(world.get(target).get("top"))
            {
                case 22:
                s1.setText("isWalk");
                s2.setText("isEdit");
                s3.setText("isUse");
                s4.setText("isOn");

                edittext1.setHint("whiteList");

                s1.setChecked((boolean)attributes.get(target).get("isWalk"));
                s2.setChecked((boolean)attributes.get(target).get("isEdit"));
                s3.setChecked((boolean)attributes.get(target).get("isUse"));
                s4.setChecked((boolean)attributes.get(target).get("isOn"));
                edittext1.setText((String)attributes.get(target).get("whiteList"));

                layout.addView(s1);
                layout.addView(s2);
                layout.addView(s3);

                layout.addView(edittext1);

                layout.addView(s4);

                dialog.setView(layout);

                    dialog.setTitle("GUARD:Block");
                            dialog.setIcon(textures.get("guardblock"));
                            dialog.setPositiveButton("ок", new LoWDialog.OnClickListener() {
                                    public void onClick(DialogInterface dialog1) {

                        HashMap<String, Object> result = new HashMap<String, Object>();
                        try{

                            if(!s4.isChecked())
                            {
                                result.put("texture", "guardblockbad");
                            } else {
                                result.put("texture", "");
                            }
                            result.put("whiteList", edittext1.getText().toString());
                            result.put("isWalk", s1.isChecked());
                            result.put("isEdit", s2.isChecked());
                            result.put("isUse", s3.isChecked());
                            result.put("isOn", s4.isChecked());
                            result.put("author", com.google.firebase.auth.FirebaseAuth.getInstance().getCurrentUser().getUid());

                            setHashMap(result, "LoW/games/"+ mapId +"/attributes/"+target);

                            result.put("value", 3);

                            setHashMap(result, "LoW/games/"+ mapId +"/request/"+target);

                        }catch (Exception err){
                            result.put("texture", "guardblockbad");

                            updateHashMap(result, "LoW/games/"+ mapId +"/attributes/"+target);

                            result.put("value", 3);

                            updateHashMap(result, "LoW/games/"+ mapId +"/request/"+target);

                        }
                                            dialog1.cancel();
                                        }
                                });
                dialog.setNegativeButton("отмена", new LoWDialog.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog1) {
                                        dialog1.dismiss();
                                    }});


                dialog.show();

                break;
                case 21:
                String ltarget= Integer.toString((int)bx-1)+":"+Integer.toString((int)by);
                if(world.containsKey(ltarget) && world.get(ltarget).containsKey("mid") && blocks.containsKey(world.get(ltarget).get("mid")) && blocks.get(world.get(ltarget).get("mid")).name != null)
                {
                    String text = "MID:\nID: "+world.get(ltarget).get("mid")+"\nNAME: "+blocks.get(world.get(ltarget).get("mid")).name;
                    if(blocks.containsKey(world.get(ltarget).get("top")) && blocks.get(world.get(ltarget).get("top")).name != null)
                    {
                        text+="\n\nTOP:\nID: "+world.get(ltarget).get("top")+"\nNAME: "+blocks.get(world.get(ltarget).get("top")).name;
                    }
                    for(String id : dUsers.keySet())
                    {
                        if(dUsers.get(id).x == bx-1 && dUsers.get(id).y == by)
                        {
                            text+="\n\nUSER:\nID: "+id;
                        }
                    }
                    dialog.setText(text);
                    dialog.setTitle("SCANNER:Block");
                     dialog.setIcon(textures.get("scannerblock"));
                                dialog.setPositiveButton("ок", new LoWDialog.OnClickListener() {
                                        public void onClick(DialogInterface dialog) {
                                                // Закрываем окно
                                                dialog.cancel();
                                            }
                                    });



                    dialog.show();
                } else {
                    HashMap<String, Object> result = new HashMap<String, Object>();
                    result.put("texture", "scannerblockbad");

                    updateHashMap(result, "LoW/games/"+ mapId +"/attributes/"+target);

                    result.put("value", 3);

                    updateHashMap(result, "LoW/games/"+ mapId +"/request/"+target);
                }
                break;
                case 20:
                dialog.setText("ID: "+com.google.firebase.auth.FirebaseAuth.getInstance().getCurrentUser().getUid());
                dialog.setTitle("PROFILE:Block");
                dialog.setIcon(textures.get("profileblock"));
                            dialog.setPositiveButton("ок", new LoWDialog.OnClickListener() {
                                    public void onClick(DialogInterface dialog) {
                                            // Закрываем окно
                                            dialog.cancel();
                                        }
                                });



                dialog.show();
                break;
                case 19:
                s1.setText("drawShadow");
                s1.setChecked(drawShadow);

                t1.setText("renderX");
                sk1.setProgress(renderX);
                sk1.setMax(32);

                t2.setText("renderY");
                sk2.setProgress(renderY);
                sk2.setMax(32);

                t3.setText("buttonSize");
                sk3.setProgress(buttonSize/16);
                sk3.setMax(16);

                t4.setText("scaleSize");
                sk4.setProgress(scaleSize);
                sk4.setMax(16);

                t5.setText("transparency");
                sk5.setProgress(transparency);
                sk5.setMax(16);

                layout.addView(t1);
                layout.addView(sk1);
                layout.addView(t2);
                layout.addView(sk2);
                layout.addView(t3);
                layout.addView(sk3);
                layout.addView(t4);
                layout.addView(sk4);
                layout.addView(t5);
                layout.addView(sk5);
                layout.addView(s1);

                dialog.setView(layout);
                    dialog.setTitle("SETTINGS:Block");
                            dialog.setIcon(textures.get("settingsblock"));
                            dialog.setPositiveButton("сохранить", new LoWDialog.OnClickListener() {
                                    public void onClick(DialogInterface dialog) {
                                           HashMap<String, Object> result = new HashMap<String, Object>();

                        if(sk1.getProgress() == 0) {sk1.setProgress(3);}
                        if(sk2.getProgress() == 0) {sk2.setProgress(3);}
                         if(sk3.getProgress() == 0) {sk3.setProgress(3);}
                         if(sk4.getProgress() == 0) {sk4.setProgress(1);}

                        result.put("renderX", sk1.getProgress());
                        result.put("renderY", sk2.getProgress());
                        result.put("buttonSize", sk3.getProgress()*16);
                        result.put("scaleSize", sk4.getProgress());
                        result.put("transparency", sk5.getProgress());
                        result.put("drawShadow", s1.isChecked());

                        updateHashMap(result, "LoW/games/"+ mapId +"/users/"+com.google.firebase.auth.FirebaseAuth.getInstance().getCurrentUser().getUid());

                        transparency=sk5.getProgress();
                        drawShadow=s1.isChecked();
                        renderX=sk1.getProgress();
                        renderY=sk2.getProgress();
                        buttonSize=sk3.getProgress()*16;
                        scaleSize =sk4.getProgress();

                        textures.init();
                        usersInit();
                        BlocksInit();
                        SButtonInit();

                        //_toast("Изменения вступят в силу после перезапуска!");
                                            dialog.cancel();
                                        }
                                });
                            dialog.setNegativeButton("отмена", new LoWDialog.OnClickListener() {
                                    public void onClick(DialogInterface dialog) {
                                            // Закрываем окно
                                            dialog.cancel();
                                        }
                                });



                dialog.show();
                break;
                case 2:
                if(attributes.containsKey(target) && attributes.get(target).containsKey("text"))
                {

                    dialog.setText((String)attributes.get(target).get("text"));
                    dialog.setBackground(textures.get("tablebackground"));
                    dialog.setIcon((Bitmap)null);
                    dialog.setTextColor(Color.BLACK);

                                dialog.setPositiveButton("ок", new LoWDialog.OnClickListener() {
                                        public void onClick(DialogInterface dialog) {
                                                // Закрываем окно
                                                dialog.cancel();
                                            }
                                    });



                    dialog.show();
                }
                break;
                case 5:
                String log;
                if (FileUtil.isExistFile(FileUtil.getPackageDataDir(gameActivity.getApplicationContext()).concat("/log.txt"))) {
                                log = FileUtil.readFile(FileUtil.getPackageDataDir(gameActivity.getApplicationContext())+"/log.txt");
                        } else {
                    log="LOG:Block : пусто!";
                }

                dialog.setText(log);
                    dialog.setTitle("Лог");
                            dialog.setIcon(textures.get("logblock"));
                            dialog.setPositiveButton("ок", new LoWDialog.OnClickListener() {
                                    public void onClick(DialogInterface dialog) {
                                            // Закрываем окно
                                            dialog.cancel();
                                        }
                                });
                            dialog.setNegativeButton("очистить", new LoWDialog.OnClickListener() {
                                    public void onClick(DialogInterface dialog) {
                                      FileUtil.writeFile(FileUtil.getPackageDataDir(gameActivity.getApplicationContext())+"/log.txt", "");
                                            dialog.cancel();
                                        }
                                });


                dialog.show();
                break;
                case 10:
                if(attributes.containsKey(target) && attributes.get(target).containsKey("x") && attributes.get(target).containsKey("y"))
                {
                    try{
                        int Nx=(int)((long)attributes.get(target).get("x"));
                        int Ny=(int)((long)attributes.get(target).get("y"));

                        HashMap<String, Object> result = new HashMap<String, Object>();

                        if(Nx < size && Nx > -1 && Ny < size && Ny > -1)
                        {
                            user.setC(Nx,Ny);
                            result.put("texture", "");
                        } else {
                            result.put("texture", "teleportbad");
                        }
                        updateHashMap(result, "LoW/games/"+ mapId +"/attributes/"+target);

                        result.put("value", 3);
                        result.put("x", Nx);
                        result.put("y", Ny);

                        setHashMap(result, "LoW/games/"+ mapId +"/request/"+target);
                    }catch(Exception err)
                    {
                        gameActivity._WriteLogInFile(err.toString());
                        HashMap<String, Object> result = new HashMap<String, Object>();
                        result.put("texture", "teleportbad");

                        updateHashMap(result, "LoW/games/"+ mapId +"/attributes/"+target);

                        result.put("value", 3);

                        updateHashMap(result, "LoW/games/"+ mapId +"/request/"+target);
                    }
                } else {
                    HashMap<String, Object> result = new HashMap<String, Object>();
                    result.put("texture", "teleportbad");

                    updateHashMap(result, "LoW/games/"+ mapId +"/attributes/"+target);

                    result.put("value", 3);

                    updateHashMap(result, "LoW/games/"+ mapId +"/request/"+target);
                }
                break;
                case 13:

                String guard ="";
                if(attributes.containsKey(target) && attributes.get(target).containsKey("link"))
                {
                    guard="\n\nGUARD :"+attributes.get(target).get("link");
                }
                dialog.setText("X:Y - "+target+"\n SIZE: "+size+(!guard.equals("")?guard: ""));
                    dialog.setTitle("COMPASS:Block");
                            dialog.setIcon(textures.get("compassblock"));
                           dialog.setPositiveButton("ок", new LoWDialog.OnClickListener() {
                                    public void onClick(DialogInterface dialog) {
                                            // Закрываем окно
                                            dialog.cancel();
                                        }
                                });



                dialog.show();
                break;
                case 16:
                if(world.containsKey(target) && world.get(target).containsKey("mid") && blocks.containsKey(world.get(target).get("mid")) && blocks.get(world.get(target).get("mid")).name != null)
                {
                    dialog.setText("ID: "+world.get(target).get("mid")+"\nNAME: "+blocks.get(world.get(target).get("mid")).name);
                    dialog.setTitle("ID:Block");
                                dialog.setIcon(textures.get("idblock"));

                                        dialog.setPositiveButton("ок", new LoWDialog.OnClickListener() {
                                        public void onClick(DialogInterface dialog) {
                                                // Закрываем окно
                                                dialog.cancel();
                                            }
                                    });



                    dialog.show();
                } else  {
                    HashMap<String, Object> result = new HashMap<String, Object>();
                    result.put("texture", "idblockbad");

                    updateHashMap(result, "LoW/games/"+ mapId +"/attributes/"+target);

                    result.put("value", 3);

                    updateHashMap(result, "LoW/games/"+ mapId +"/request/"+target);
                }

                break;
                case 17:
                ShowMapBlock(target);
                break;
            }
        }
    }
    public void collisionInit()
    {
        collisions = new HashMap<String, CollisionModel>();

        ArrayList<Line> blockl = new ArrayList<Line>();
        blockl.add(new Line(new Dot(0,0), new Dot(0, 16)));
        blockl.add(new Line(new Dot(0, 16), new Dot(16,16)));
        blockl.add(new Line(new Dot(16,16), new Dot(16, 0)));
        blockl.add(new Line(new Dot(16,0), new Dot(0,0)));
        CollisionModel block= new CollisionModel(new Figure(blockl));

    }

    public void placeUsableBlock(final int id, final String coordinate )
    {

        LoWDialog dialog =new LoWDialog(context);

        final double bx =user.getUserTarget()[0];
        final double by =user.getUserTarget()[1];

        int color = getResources().getColor(R.color.colorGreen);

        final EditText edittext1= new EditText(context);
        edittext1.setTextColor(color);
        edittext1.setTypeface(font);
        edittext1.setHintTextColor(color);
        final EditText edittext2= new EditText(context);
        edittext2.setTextColor(color);
        edittext2.setTypeface(font);
        edittext2.setHintTextColor(color);
        final EditText edittext3= new EditText(context);
        edittext3.setTextColor(color);
        edittext3.setTypeface(font);
        edittext3.setHintTextColor(color);

        TextView t1 = new TextView(context);
        t1.setTypeface(font);
        t1.setTextColor(color);
        TextView t2 = new TextView(context);
        t2.setTypeface(font);
        t2.setTextColor(color);
        TextView t3 = new TextView(context);
        t3.setTypeface(font);
        t3.setTextColor(color);
        TextView t4  = new TextView(context);
        t4.setTypeface(font);
        t4.setTextColor(color);
        TextView t5  = new TextView(context);
        t5.setTypeface(font);
        t5.setTextColor(color);

        final Switch s1 = new Switch(context);
        s1.setTypeface(font);
        s1.setTextColor(color);
        final Switch s2 = new Switch(context);
        s2.setTypeface(font);
        s2.setTextColor(color);
        final Switch s3 = new Switch(context);
        s3.setTypeface(font);
        s3.setTextColor(color);
        final Switch s4 = new Switch(context);
        s4.setTypeface(font);
        s4.setTextColor(color);

        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams lpar = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.setBackground(textures.get("dialogbackground"));



        switch(id)
        {
            case 22:
            s1.setText("isWalk");
            s2.setText("isEdit");
            s3.setText("isUse");
            s4.setText("isOn");

            edittext1.setLayoutParams(lpar);
            edittext1.setHint("radius");

            edittext2.setLayoutParams(lpar);
            edittext2.setHint("whiteList");


            layout.addView(s1);
            layout.addView(s2);
            layout.addView(s3);

            layout.addView(edittext1);
            layout.addView(edittext2);

            layout.addView(s4);

            dialog.setView(layout);
                dialog.setTitle("Создание GUARD:Block");
                        dialog.setIcon(textures.get("guardblock"));
                        dialog.setPositiveButton("ок", new LoWDialog.OnClickListener() {
                                public void onClick(DialogInterface dialog) {

                    HashMap<String, Object> result = new HashMap<String, Object>();

                    try{



                        if(world.containsKey(coordinate))
                        {
                            result.put("mid", world.get(coordinate).get("mid"));
                        }

                        result.put("top", id);

                        updateHashMap(result, "LoW/games/"+mapId+"/map/"+coordinate);

                        result.put("value", 1);

                        setHashMap(result, "LoW/games/"+mapId+"/request/"+coordinate);

                        result.remove("value");

                        result.put("size", Integer.parseInt(edittext1.getText().toString()));
                        result.put("whiteList", edittext2.getText().toString());
                        result.put("isWalk", s1.isChecked());
                        result.put("isEdit", s2.isChecked());
                        if(!s4.isChecked())
                        {
                            result.put("texture", "guardblockbad");
                        } else {
                            result.put("texture", "");
                        }
                        result.put("isUse", s3.isChecked());
                        result.put("isOn", s4.isChecked());
                        result.put("author", com.google.firebase.auth.FirebaseAuth.getInstance().getCurrentUser().getUid());

                        setHashMap(result, "LoW/games/"+mapId+"/attributes/"+coordinate);

                        result.put("value", 3);

                        setHashMap(result, "LoW/games/"+mapId+"/request/"+coordinate);

                        if(Integer.parseInt(edittext1.getText().toString()) <= 20)
                        {
                            HashMap<String, HashMap<String, Object>> result1 = new HashMap<String, HashMap<String, Object>>();

                            final int radius = Integer.parseInt(edittext1.getText().toString());
                            for(int y=(int)by-radius; y <= (int)by+radius; y++)
                            {
                                for(int x=(int)bx-radius; x <= (int)bx+radius; x++)
                                {
                                    if(Math.pow(x-(int)bx, 2)+Math.pow(y-(int)by, 2) <= Math.pow(radius, 2) && containsGuard(Integer.toString(x)+":"+Integer.toString(y)))
                                    {
                                        String target = Integer.toString(x)+":"+Integer.toString(y);

                                        result1.put(target, new HashMap<String, Object>());
                                        result1.get(target).put("link", coordinate);
                                    }
                                }
                            }
                            updateHashMap1(result1, "LoW/games/"+mapId+"/attributes");

                            result.put("value", 3);

                            updateHashMap1(result1, "LoW/games/"+mapId+"/request");
                        } else {
                            result.put("texture", "guardblockbad");

                            updateHashMap(result, "LoW/games/"+mapId+"/attributes/"+coordinate);

                            result.put("value", 3);

                            updateHashMap(result, "LoW/games/"+mapId+"/request/"+coordinate);

                        }
                    }catch (Exception err){
                        result.put("texture", "guardblockbad");

                        updateHashMap(result, "LoW/games/"+mapId+"/attributes/"+coordinate);

                        result.put("value", 3);

                        updateHashMap(result, "LoW/games/"+mapId+"/request/"+coordinate);

                    }

                                        dialog.cancel();
                                    }
                            });
            dialog.setNegativeButton("отмена", new LoWDialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog) {
                                    dialog.dismiss();
                                }});
            dialog.show();


            break;
            case 2:
            final EditText edittext = new EditText(context);
            edittext.setLayoutParams(lpar);
            edittext.setTypeface(font);
            edittext.setHint("Текст таблички");


            dialog.setView(edittext);
                dialog.setTitle("Создание таблички");
                dialog.setBackground(textures.get("tablebackground"));
                        dialog.setIcon((Bitmap)null);
                        dialog.setPositiveButton("ок", new LoWDialog.OnClickListener() {
                                public void onClick(DialogInterface dialog) {
                    if(edittext.getText().toString() != null)
                    {
                        HashMap<String, Object> result = new HashMap<String, Object>();

                        result.put("top", id);
                        if(world.containsKey(coordinate))
                        {
                            result.put("mid", world.get(coordinate).get("mid"));
                        }
                        updateHashMap(result, "LoW/games/"+mapId+"/map/"+coordinate);

                        result.put("value", 1);

                        setHashMap(result, "LoW/games/"+mapId+"/request/"+coordinate);

                        result.clear();

                        result.put("text", edittext.getText().toString());
                        setHashMap(result, "LoW/games/"+mapId+"/attributes/"+coordinate);

                        result.put("value", 3);

                        setHashMap(result, "LoW/games/"+mapId+"/request/"+coordinate);
                    }


                                        dialog.cancel();
                                    }
                            });
            dialog.setNegativeButton("отмена", new LoWDialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog) {
                                    dialog.dismiss();
                                }});
            dialog.show();
            break;
            case 10:

            edittext1.setHint("X");
            layout.addView(edittext1);

            edittext2.setHint("Y");
            layout.addView(edittext2);

            dialog.setView(layout);
                dialog.setTitle("Создание TELEPORT:Block");
                        dialog.setIcon(textures.get("teleport"));
                        dialog.setPositiveButton("ок", new LoWDialog.OnClickListener() {
                                public void onClick(DialogInterface dialog) {

                    HashMap<String, Object> result = new HashMap<String, Object>();

                    result.put("top", id);
                    if(world.containsKey(coordinate))
                    {
                        result.put("mid", world.get(coordinate).get("mid"));
                    }
                    updateHashMap(result, "LoW/games/"+mapId+"/map/"+coordinate);

                    result.put("value", 1);

                    setHashMap(result, "LoW/games/"+mapId+"/request/"+coordinate);

                    result.clear();
                    try{
                        result.put("x", Integer.parseInt(edittext1.getText().toString()));
                        result.put("y", Integer.parseInt(edittext2.getText().toString()));
                    }catch(Exception err){}

                    setHashMap(result, "LoW/games/"+mapId+"/attributes/"+coordinate);

                    result.put("value", 3);

                    setHashMap(result, "LoW/games/"+mapId+"/request/"+coordinate);



                                        dialog.cancel();
                                    }
                            });
            dialog.setNegativeButton("отмена", new LoWDialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog) {
                                    dialog.dismiss();
                                }});
            dialog.show();
            break;
            case 17:

            edittext1.setHint("X");
            layout.addView(edittext1);


            edittext2.setHint("Y");
            layout.addView(edittext2);


            edittext3.setHint("Size");
            layout.addView(edittext3);

            dialog.setView(layout);
                dialog.setTitle("MAP:Block");
                        dialog.setIcon(textures.get("mapblock"));
                        dialog.setPositiveButton("ок", new LoWDialog.OnClickListener() {
                                public void onClick(DialogInterface dialog) {

                    HashMap<String, Object> result = new HashMap<String, Object>();

                    result.put("top", id);
                    if(world.containsKey(coordinate))
                    {
                        result.put("mid", world.get(coordinate).get("mid"));
                    }
                    updateHashMap(result, "LoW/games/"+mapId+"/map/"+coordinate);

                    result.put("value", 1);

                    setHashMap(result, "LoW/games/"+mapId+"/request/"+coordinate);

                    result.clear();
                    try{
                        result.put("x", Integer.parseInt(edittext1.getText().toString()));
                        result.put("y", Integer.parseInt(edittext2.getText().toString()));
                        result.put("size", Integer.parseInt(edittext3.getText().toString()));
                    }catch(Exception err){}

                    setHashMap(result, "LoW/games/"+mapId+"/attributes/"+coordinate);

                    result.put("value", 3);

                    setHashMap(result, "LoW/games/"+mapId+"/request/"+coordinate);



                                        dialog.cancel();
                                    }
                            });
            dialog.setNegativeButton("отмена", new LoWDialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog) {
                                    dialog.dismiss();
                                }});
            dialog.show();
            break;
            default:
            HashMap<String, Object> result = new HashMap<String, Object>();
            result.put("top", id);
            if(world.containsKey(coordinate))
            {
                result.put("mid", world.get(coordinate).get("mid"));
            }
            updateHashMap(result, "LoW/games/"+mapId+"/map/"+coordinate);
            result.put("value", 1);
            setHashMap(result, "LoW/games/"+mapId+"/request/"+coordinate);
        }

    }
    public void ShowInventory()
    {
        LoWDialog dialog = new LoWDialog(context);

        dialog.setBackground(textures.get("dialogbackground"));
        dialog.setIcon(textures.get("inventory"));
        dialog.setTitle("Инвентарь");

        final ArrayList<Integer> indexs = sorter.getIndex();


        final inventoryAdapter inventory= new inventoryAdapter(gameActivity, context, indexs, sorter.getFakeBlocks(), textures);


        dialog.setAdapter(inventory, new LoWDialog.OnItemSelected() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                ShowInventory2(sorter.getSort(indexs.get(which)));
                dialog.dismiss();

            }});
        dialog.setNeutralButton("очистить инвентарь", new LoWDialog.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog) {
                user.inventory = new int[5];
                dialog.dismiss();
            }});
        dialog.setNegativeButton("отмена", new LoWDialog.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog) {
                dialog.dismiss();
            }});
        dialog.show();


    }
    public void ShowInventory2(final ArrayList<Integer> indexs)
    {
        LoWDialog dialog = new LoWDialog(context);

        dialog.setBackground(textures.get("dialogbackground"));
        dialog.setIcon(textures.get("inventory"));
        dialog.setTitle("Инвентарь");

        final inventoryAdapter inventory= new inventoryAdapter(gameActivity, context, indexs, blocks, textures);

        dialog.setAdapter(inventory, new LoWDialog.OnItemSelected() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                          user.inventory[user.item-1] = indexs.get(which);
                          dialog.dismiss();
            }});
        dialog.setNeutralButton("назад", new LoWDialog.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog) {
                                ShowInventory();
                                dialog.dismiss();
                            }});
        dialog.setNegativeButton("отмена", new LoWDialog.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog) {
                                dialog.dismiss();
                            }});
        dialog.show();


    }
    public void ShowMap(int size)
    {
        int sX= (int)(user.x-size/2);
        int sY= (int)(user.y-size/2);

        MiniMapTask map = new MiniMapTask(context, this, sX, sY, size);
        map.execute();
    }
    public void ShowMapBlock(String target)
    {
        try{
            int sX=(int)((long)attributes.get(target).get("x"));
            int sY=(int)((long)attributes.get(target).get("y"));
            int size = (int)((long)attributes.get(target).get("size"));

            MiniMapTask map = new MiniMapTask(context, this, sX, sY, size);
            map.execute();

        }catch(Exception err){
            HashMap<String, Object> result = new HashMap<String, Object>();
            result.put("texture", "mapblockbad");

            updateHashMap(result, "LoW/games/"+ mapId +"/attributes/"+target);

            result.put("value", 3);

            updateHashMap(result, "LoW/games/"+ mapId +"/request/"+target);
            gameActivity._WriteLogInFile(err.toString());
        }
    }
    public Bitmap rotateBitmap(Bitmap source, int angle)
    {
              Matrix matrix = new Matrix();
              matrix.postRotate(angle);
              return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }
    Bitmap flipBitmap(Bitmap src)
    {
            Matrix m = new Matrix();
            m.preScale(-1, 1);
            Bitmap dst = Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), m, false);
            dst.setDensity(DisplayMetrics.DENSITY_DEFAULT);
            return dst;
    }
    public Bitmap createTransparency(String texture, String name){
        Bitmap init1 = textures.get(texture);
        Bitmap init2 = Bitmap.createBitmap(init1.getWidth(), init1.getHeight(), Bitmap.Config.ARGB_8888);

        int[] result = new int[init1.getWidth()*init1.getHeight()];
        init1.getPixels(result, 0, init1.getWidth(), 0, 0, init1.getWidth(), init1.getHeight());
        for(int i=0; i < result.length; i++) {
            if (result[i] != 0) {
                result[i]= ColorUtils.setAlphaComponent(result[i],128);
            }
        }
        init2.setPixels(result, 0, init1.getWidth(), 0, 0, init1.getWidth(), init1.getHeight());
        return init2;
    }

    public Bitmap createShadow(String texture, String name)
    {
        Bitmap init1 = textures.get(texture);


        Bitmap init2 = Bitmap.createBitmap(init1.getWidth(), init1.getHeight()*2, Bitmap.Config.ARGB_8888);

        int[] result = new int[init1.getWidth()*init1.getHeight()];

        init1.getPixels(result, 0, init1.getWidth(), 0, 0, init1.getWidth(), init1.getHeight());
        for(int i=0; i < result.length; i++)
        {
            if (result[i] != 0) {
                result[i]= Color.parseColor("#8C000000");
            }
        }
        init2.setPixels(result, 0, init2.getWidth(), 0, 0, init1.getWidth(), init1.getHeight());


        return init2;
    }
    public void BlocksInit()
    {
        blocks = new HashMap<Integer, Block>();

        boolean[] defaultBlock = {true, true,false, false,false,false,false, true, false};

        boolean[] plantsCollision = {false, false, true, false, true, false,false,false, false};

        boolean[] decorCollision = {false, false, true, false, true, false,false,true, false};

        boolean[] plantsNoCollision = {false, false, true, false, false, false,false, false, false};

        boolean[] atr = {false, false, true, true, true, false, false, false, false};

        boolean[] Wall = {false, false, true, false, true, false,true,false, false};

        boolean[] WallRotated = {false, false, true, false, true, false,true,true, false};

        boolean[] WalkCombinabled = {false, false, true, false, true, true,true,false, false};

        boolean[] Wehicle = {false, false, true, false, true, false,false,false, true};

        blocks.put(106, new Block("Блок Асфальта", "asphalt54", 0,0,defaultBlock, Color.parseColor("#757575"), null));

        blocks.put(105, new Block("Блок Асфальта", "asphalt53", 0,0,defaultBlock, Color.parseColor("#757575"), null));

        blocks.put(104, new Block("Блок Асфальта", "asphalt52", 0,0,defaultBlock, Color.parseColor("#757575"), null));

        blocks.put(103, new Block("Блок Асфальта", "asphalt51", 0,0,defaultBlock, Color.parseColor("#757575"), null));

        blocks.put(102, new Block("Блок Асфальта", "asphalt50", 0,0,defaultBlock, Color.parseColor("#757575"), null));

        blocks.put(101, new Block("Блок Асфальта", "asphalt49", 0,0,defaultBlock, Color.parseColor("#757575"), null));

        blocks.put(100, new Block("Блок Асфальта", "asphalt48", 0,0,defaultBlock, Color.parseColor("#757575"), null));

        blocks.put(99, new Block("Блок Асфальта", "asphalt47", 0,0,defaultBlock, Color.parseColor("#757575"), null));

        blocks.put(98, new Block("Блок Асфальта", "asphalt46", 0,0,defaultBlock, Color.parseColor("#757575"), null));

        blocks.put(97, new Block("Блок Асфальта", "asphalt45", 0,0,defaultBlock, Color.parseColor("#757575"), null));

        blocks.put(96, new Block("Блок Асфальта", "asphalt44", 0,0,defaultBlock, Color.parseColor("#757575"), null));

        blocks.put(95, new Block("Блок Асфальта", "asphalt43", 0,0,defaultBlock, Color.parseColor("#757575"), null));

        blocks.put(94, new Block("Блок Асфальта", "asphalt42", 0,0,defaultBlock, Color.parseColor("#757575"), null));

        blocks.put(93, new Block("Блок Асфальта", "asphalt41", 0,0,defaultBlock, Color.parseColor("#757575"), null));

        blocks.put(92, new Block("Блок Асфальта", "asphalt40", 0,0,defaultBlock, Color.parseColor("#757575"), null));

        blocks.put(91, new Block("Блок Асфальта", "asphalt39", 0,0,defaultBlock, Color.parseColor("#757575"), null));

        blocks.put(90, new Block("Блок Асфальта", "asphalt38", 0,0,defaultBlock, Color.parseColor("#757575"), null));

        blocks.put(89, new Block("Блок Асфальта", "asphalt37", 0,0,defaultBlock, Color.parseColor("#757575"), null));

        blocks.put(88, new Block("Блок Асфальта", "asphalt36", 0,0,defaultBlock, Color.parseColor("#757575"), null));

        blocks.put(87, new Block("Блок Асфальта", "asphalt35", 0,0,defaultBlock, Color.parseColor("#757575"), null));

        blocks.put(86, new Block("Блок Асфальта", "asphalt34", 0,0,defaultBlock, Color.parseColor("#757575"), null));

        blocks.put(85, new Block("Блок Асфальта", "asphalt33", 0,0,defaultBlock, Color.parseColor("#757575"), null));

        blocks.put(84, new Block("Блок Асфальта", "asphalt32", 0,0,defaultBlock, Color.parseColor("#757575"), null));

        blocks.put(83, new Block("Блок Асфальта", "asphalt31", 0,0,defaultBlock, Color.parseColor("#757575"), null));

        blocks.put(82, new Block("Блок Асфальта", "asphalt31", 0,0,defaultBlock, Color.parseColor("#757575"), null));

        blocks.put(81, new Block("Блок Асфальта", "asphalt30", 0,0,defaultBlock, Color.parseColor("#757575"), null));

        blocks.put(80, new Block("Блок Асфальта", "asphalt29", 0,0,defaultBlock, Color.parseColor("#757575"), null));

        blocks.put(79, new Block("Блок Асфальта", "asphalt29", 0,0,defaultBlock, Color.parseColor("#757575"), null));

        blocks.put(78, new Block("Блок Асфальта", "asphalt28", 0,0,defaultBlock, Color.parseColor("#757575"), null));

        blocks.put(77, new Block("Блок Асфальта", "asphalt27", 0,0,defaultBlock, Color.parseColor("#757575"), null));

        blocks.put(76, new Block("Блок Асфальта", "asphalt26", 0,0,defaultBlock, Color.parseColor("#757575"), null));

        blocks.put(75, new Block("Блок Асфальта", "asphalt25", 0,0,defaultBlock, Color.parseColor("#757575"), null));

        blocks.put(74, new Block("Блок Асфальта", "asphalt24", 0,0,defaultBlock, Color.parseColor("#757575"), null));

        blocks.put(71, new Block("Блок Асфальта", "asphalt21", 0,0,defaultBlock, Color.parseColor("#757575"), null));

        blocks.put(70, new Block("Блок Асфальта", "asphalt20", 0,0,defaultBlock, Color.parseColor("#757575"), null));

        blocks.put(69, new Block("Блок Асфальта", "asphalt19", 0,0,defaultBlock, Color.parseColor("#757575"), null));

        blocks.put(68, new Block("Блок Асфальта", "asphalt18", 0,0,defaultBlock, Color.parseColor("#757575"), null));

        blocks.put(67,new Block("Бочка", "barrel1",0, -textures.textureSize, decorCollision,Color.parseColor("#994C00"), GameScreen.this));

        blocks.put(66,new Block("Кувшин", "jug1",0, -textures.textureSize, decorCollision,Color.parseColor("#994C00"), GameScreen.this));

        blocks.put(65, new Block("Машина", "car1r1",0, -textures.textureSize,Wehicle, Color.parseColor("#FF5722"), GameScreen.this));

        blocks.put(64, new Block("Конус", "cone1",0, -textures.textureSize/2, plantsNoCollision, Color.parseColor("#FF5722"), GameScreen.this));

        blocks.put(63, new Block("Блок Асфальта", "asphalt17", 0,0,defaultBlock, Color.parseColor("#757575"), null));

        blocks.put(62, new Block("Блок Асфальта", "asphalt16", 0,0,defaultBlock, Color.parseColor("#757575"), null));

        blocks.put(61, new Block("Блок Асфальта", "asphalt15", 0,0,defaultBlock, Color.parseColor("#757575"), null));

        blocks.put(60, new Block("Блок Асфальта", "asphalt14", 0,0,defaultBlock, Color.parseColor("#757575"), null));

        blocks.put(59, new Block("Блок Асфальта", "asphalt13", 0,0,defaultBlock, Color.parseColor("#757575"), null));

        blocks.put(58, new Block("Блок Асфальта", "asphalt12", 0,0,defaultBlock, Color.parseColor("#757575"), null));

        blocks.put(57, new Block("Блок Асфальта", "asphalt11", 0,0,defaultBlock, Color.parseColor("#757575"), null));

        blocks.put(56, new Block("Блок Асфальта", "asphalt10", 0,0,defaultBlock, Color.parseColor("#757575"), null));

        blocks.put(55, new Block("Блок Асфальта", "asphalt9", 0,0,defaultBlock, Color.parseColor("#757575"), null));

        blocks.put(54, new Block("Блок Асфальта", "asphalt8", 0,0,defaultBlock, Color.parseColor("#757575"), null));

        blocks.put(53, new Block("Диван", "sofa1",0, -textures.textureSize,decorCollision ,Color.parseColor("#994C00"), GameScreen.this));

        blocks.put(52, new Block("Пень", "tree2cutdown",0, -textures.textureSize/2, plantsNoCollision, Color.GREEN, GameScreen.this));

        blocks.put(51, new Block("Скошенная стена из блока кирпича", "brickwallbevelru",0, -textures.textureSize*2,WallRotated ,Color.RED, GameScreen.this));

        blocks.put(50, new Block("Скошенная стена из блока кирпича", "brickwallbevellu",0, -textures.textureSize*2,WallRotated ,Color.RED, GameScreen.this));

        blocks.put(49, new Block("Скошенная стена из блока кирпича", "brickwallbevelrd",0, -textures.textureSize*2,WallRotated ,Color.RED, GameScreen.this));

        blocks.put(48, new Block("Скошенная стена из блока кирпича", "brickwallbevelld",0, -textures.textureSize*2,WallRotated ,Color.RED, GameScreen.this));

        blocks.put(47, new Block("Скошенная стена из блока кирпича", "brickwallbevell",0, -textures.textureSize*2,WallRotated ,Color.RED, GameScreen.this));

        blocks.put(46, new Block("Скошенная стена из блока кирпича", "brickwallbevelr",0, -textures.textureSize*2,WallRotated ,Color.RED, GameScreen.this));

        blocks.put(45, new Block("Скошенная стена из блока кирпича", "brickwallbevelu",0, -textures.textureSize*2,WallRotated ,Color.RED, GameScreen.this));

        blocks.put(44, new Block("Скошенная стена из блока кирпича", "brickwallbeveld",0, -textures.textureSize*2,WallRotated ,Color.RED, GameScreen.this));

        blocks.put(43, new Block("Блок Асфальта", "asphalt7", 0,0,defaultBlock, Color.parseColor("#757575"), null));

        blocks.put(42, new Block("Блок Асфальта", "asphalt6", 0,0,defaultBlock, Color.parseColor("#757575"), null));

        blocks.put(41, new Block("Блок Асфальта", "asphalt5", 0,0,defaultBlock, Color.parseColor("#757575"), null));

        blocks.put(40, new Block("Блок Асфальта", "asphalt4", 0,0,defaultBlock, Color.parseColor("#757575"), null));

        blocks.put(39, new Block("Блок Асфальта", "asphalt3", 0,0,defaultBlock, Color.parseColor("#757575"), null));

        blocks.put(38, new Block("Блок Асфальта", "asphalt2", 0,0,defaultBlock, Color.parseColor("#757575"), null));

        blocks.put(37, new Block("Блок Асфальта", "asphalt", 0,0,defaultBlock, Color.parseColor("#757575"), null));

        blocks.put(36, new Block("Блок железа", "metalsheet", 0,0,defaultBlock, Color.GRAY, null));

        blocks.put(35, new Block("Стул", "chair1u",0, -textures.textureSize, decorCollision,Color.parseColor("#994C00"), GameScreen.this));

        blocks.put(34, new Block("Стул", "chair1d",0, -textures.textureSize, decorCollision,Color.parseColor("#994C00"), GameScreen.this));

        blocks.put(33, new Block("Стул", "chair1l",0, -textures.textureSize, decorCollision,Color.parseColor("#994C00"), GameScreen.this));

        blocks.put(32, new Block("Стол", "table1",0, -textures.textureSize/2,decorCollision ,Color.parseColor("#994C00"), GameScreen.this));

        blocks.put(31, new Block("Стул", "chair1r",0, -textures.textureSize, decorCollision,Color.parseColor("#994C00"), GameScreen.this));

        blocks.put(30, new Block("Стена из блока кирпича с окном", "brickwallwindow5",0, -textures.textureSize*2,WalkCombinabled ,Color.RED, GameScreen.this));

        blocks.put(29, new Block("Стена из блока кирпича с окном", "brickwallwindow4",0, -textures.textureSize*2,WalkCombinabled ,Color.RED, GameScreen.this));

        blocks.put(28, new Block("Стена из блока кирпича с окном", "brickwallwindow3",0, -textures.textureSize*2,WalkCombinabled,Color.RED, GameScreen.this));

        blocks.put(27, new Block("Стена из блока кирпича с окном", "brickwallwindow2",0, -textures.textureSize*2,WalkCombinabled,Color.RED, GameScreen.this));

        blocks.put(26, new Block("Стена из блока кирпича с окном", "brickwallwindow1",0, -textures.textureSize*2,plantsCollision ,Color.RED, GameScreen.this));

        blocks.put(25, new Block("Дерево", "tree2",-textures.textureSize/2 , -textures.textureSize*3/2,plantsCollision , Color.parseColor("#994C00"), GameScreen.this));

        blocks.put(24, new Block("Блок досок", "wood2", 0,0,defaultBlock, Color.parseColor("#994C00"), null));

        blocks.put(23, new Block("WARDEROBE:Block", "warderobeblock",0, -(textures.textureSize/2+textures.textureSize), atr,Color.GRAY, GameScreen.this));

        blocks.put(22, new Block("GUARD:Block", "guardblock",textures.textureSize/2, -textures.textureSize,atr , Color.GRAY, GameScreen.this));

        blocks.put(21, new Block("SCANNER:Block", "scannerblock",-textures.textureSize*2, -textures.textureSize/2,atr ,Color.GRAY, GameScreen.this));

        blocks.put(20, new Block("PROFILE:Block", "profileblock",0, -(textures.textureSize/2+textures.textureSize),atr , Color.GRAY, GameScreen.this));

        blocks.put(19, new Block("SETTINGS:Block", "settingsblock",0, -textures.textureSize/2, atr, Color.GRAY, GameScreen.this));

        blocks.put(18, new Block("Стена из блока кирпича", "brickwall",0, -textures.textureSize*2,Wall ,Color.RED, GameScreen.this));

        blocks.put(17, new Block("MAP:Block", "mapblock",0, -textures.textureSize/2,atr ,Color.GRAY, GameScreen.this));

        blocks.put(16, new Block("ID:Block", "idblock",0, -textures.textureSize/2, atr, Color.GRAY, GameScreen.this));

        blocks.put(14, new Block("Блок конструкционный", "construction", 0,0,defaultBlock, Color.YELLOW, null ));

        blocks.put(13, new Block("COMPASS:Block", "compassblock",0, -textures.textureSize/2, atr,Color.GRAY, GameScreen.this));

        blocks.put(11, new Block("Блок железа", "iron", 0,0,defaultBlock,Color.GRAY, null ));

        blocks.put(12, new Block("Блок кирпича", "brick", 0,0,defaultBlock,Color.RED, null ));

        blocks.put(10, new Block("TELEPORT:Block", "teleport",0, -textures.textureSize/2,atr, Color.GRAY, GameScreen.this));

        blocks.put(1, new Block("Блок песка", "sand", 0,0,defaultBlock, Color.YELLOW, null ));

        blocks.put(6, new Block("Блок керамики", "ceramics", 0,0,defaultBlock, Color.WHITE, null ));

        blocks.put(8, new Block("Блок досок", "wood", 0,0,defaultBlock, Color.parseColor("#994C00"), null));

        blocks.put(9, new Block("Блок камня", "stone", 0,0,defaultBlock, Color.GRAY, null ));

        blocks.put(2, new Block("Табличка", "table",0, -textures.textureSize/2, false, false, true, true, false, false,false,false,false, Color.parseColor("#994C00"), GameScreen.this));

        blocks.put(5, new Block("LOG:Block", "logblock",0, -textures.textureSize/2,atr , Color.GRAY, GameScreen.this));

        blocks.put(207, new Block("Дерево", "tree1",0, -textures.textureSize*3/2, plantsCollision, Color.parseColor("#994C00"), GameScreen.this));

        blocks.put(201, new Block("Роза", "flower1",0, -textures.textureSize/2, plantsNoCollision, Color.GREEN, GameScreen.this));

        blocks.put(205, new Block("Трава", "grass1",0, -textures.textureSize/2, plantsNoCollision, Color.GREEN, this));

        blocks.put(3, new Block("Блок травы", "grass", 0,0,defaultBlock, Color.GREEN, null));

        blocks.put(7, new Block("Блок воды", "water", 0,0,defaultBlock,Color.BLUE,null));

        blocks.put(15, new Block("Барьер", "barrier", 0,0,defaultBlock,Color.BLACK,null));

        blocks.put(4, new Block("Блок земли", "dirt", 0,0,defaultBlock,Color.parseColor("#994C00"), null));

    }
    public void blockSorterInit()
    {
        sorter= new blockSorter(textures);

        ArrayList<Integer> decor = new ArrayList<Integer>();
        decor.add(32);
        decor.add(31);
        decor.add(33);
        decor.add(34);
        decor.add(35);
        decor.add(53);
        decor.add(66);
        decor.add(67);

        ArrayList<Integer> wall = new ArrayList<Integer>();
        wall.add(30);
        wall.add(29);
        wall.add(28);
        wall.add(27);
        wall.add(26);
        wall.add(18);
        wall.add(44);
        wall.add(45);
        wall.add(46);
        wall.add(47);
        wall.add(48);
        wall.add(49);
        wall.add(50);
        wall.add(51);

        ArrayList<Integer> atr = new ArrayList<Integer>();
        atr.add(23);
        atr.add(22);
        atr.add(21);
        atr.add(20);
        atr.add(19);
        atr.add(17);
        atr.add(16);
        atr.add(13);
        atr.add(10);
        atr.add(5);

        ArrayList<Integer> blocks = new ArrayList<Integer>();
        blocks.add(24);
        blocks.add(14);
        blocks.add(11);
        blocks.add(12);
        blocks.add(1);
        blocks.add(6);
        blocks.add(8);
        blocks.add(9);
        blocks.add(3);
        blocks.add(7);
        blocks.add(4);
        blocks.add(36);


        ArrayList<Integer> road = new ArrayList<Integer>();
        road.add(37);
        road.add(38);
        road.add(39);
        road.add(40);
        road.add(41);
        road.add(42);
        road.add(43);
        road.add(54);
        road.add(55);
        road.add(56);
        road.add(57);
        road.add(58);
        road.add(59);
        road.add(60);
        road.add(61);
        road.add(62);
        road.add(63);
        road.add(64);
        road.add(65);
        road.add(68);
        road.add(69);
        road.add(70);
        road.add(71);
        road.add(74);
        road.add(75);
        road.add(76);
        road.add(77);
        road.add(78);
        road.add(79);
        road.add(80);
        road.add(81);
        road.add(82);
        road.add(83);
        road.add(84);
        road.add(85);
        road.add(86);
        road.add(87);
        road.add(88);
        road.add(89);
        road.add(90);
        road.add(91);
        road.add(92);
        road.add(93);
        road.add(94);
        road.add(95);
        road.add(96);
        road.add(97);
        road.add(98);
        road.add(99);
        road.add(100);
        road.add(101);
        road.add(102);
        road.add(103);
        road.add(104);
        road.add(105);
        road.add(106);





        ArrayList<Integer> wehicle = new ArrayList<Integer>();
        wehicle.add(65);

        ArrayList<Integer> plants = new ArrayList<Integer>();
        plants.add(25);
        plants.add(207);
        plants.add(201);
        plants.add(205);
        plants.add(52);

        ArrayList<Integer> special = new ArrayList<Integer>();
        special.add(2);
        special.add(15);

        sorter.addSort("Декор","chair1r", decor);
        sorter.addSort("Дорога","asphalt", road);
        sorter.addSort("Стены","brickwall", wall);
        sorter.addSort("ATR:Block","logblock", atr);
        sorter.addSort("Блоки","grass", blocks);
        sorter.addSort("Растения","flower1", plants);
        sorter.addSort("Специальные","table", special);
        sorter.addSort("Транспорт","car1r1", wehicle);
    }
}
