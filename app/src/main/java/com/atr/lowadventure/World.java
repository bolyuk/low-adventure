package com.atr.lowadventure;

import com.atr.Util;
import com.google.firebase.database.DataSnapshot;

import java.util.HashMap;

public class World {
    HashMap<String, HashMap<String, Integer>> world;
    HashMap<String, HashMap<String, Object>> attributes;
    GameScreen gm;
    public World(GameScreen gm)    {
        world = new HashMap<String, HashMap<String, Integer>>();
        attributes = new HashMap<String, HashMap<String, Object>>();
        this.gm=gm;
    }

    public void setWorld(DataSnapshot dataSnapshot){
        for (DataSnapshot ds : dataSnapshot.getChildren())
        {
            if(!world.containsKey(ds.getKey()))
            {
                world.put(ds.getKey(), new HashMap<String, Integer>());
            }

            if(ds.getKey().equals("configuration"))
            {
                world.get("configuration").put("size", Util.dsGetter(ds, "size", 0));
                gm.Shadow= Util.dsGetter(ds, "shadow", 0);
            } else {
                //String link = (String)isValid(ds,"link","");
                world.get(ds.getKey()).put("mid", Util.dsGetter(ds, "mid", 7));
                world.get(ds.getKey()).put("top",Util.dsGetter(ds, "top", 0));
            }
        }
    }

    public void setAttributes(DataSnapshot dataSnapshot){
        for (com.google.firebase.database.DataSnapshot ds : dataSnapshot.getChildren())
        {
            String id = ds.getKey();
            if(! attributes.containsKey(id))
            {
                attributes.put(id, new HashMap<String, Object>());
            }
            attributes.get(id).put("text", Util.dsGetter(ds,"text",""));
            attributes.get(id).put("link", Util.dsGetter(ds,"link",""));
            attributes.get(id).put("texture", Util.dsGetter(ds,"texture",""));
            attributes.get(id).put("x", Util.dsGetter(ds,"x",0));
            attributes.get(id).put("y", Util.dsGetter(ds,"y",0));
            attributes.get(id).put("size", Util.dsGetter(ds,"size",0));
            attributes.get(id).put("author", Util.dsGetter(ds,"author",""));
            attributes.get(id).put("whitelist", Util.dsGetter(ds,"whitelist",""));
            attributes.get(id).put("isWalk", Util.dsGetter(ds,"isWalk",true));
            attributes.get(id).put("isEdit", Util.dsGetter(ds,"isEdit",true));
            attributes.get(id).put("isUse", Util.dsGetter(ds,"isUse",true));
            attributes.get(id).put("isOn", Util.dsGetter(ds,"isOn",true));
        }
    }

    public HashMap<String, Integer> get(String key)
    {
     if(world.containsKey(key)){
         return world.get(key);
     }
     return null;
    }

    public boolean containsKey(String key){
        return world.containsKey(key);
    }

    public void put(String key,HashMap<String, Integer> map){
        world.put(key, map);
    }

    public String parseCoordinate(Integer x, Integer y, Integer z){
        return Integer.toString(x)+":"+Integer.toString(y)+":"+Integer.toString(z);
    }

}
