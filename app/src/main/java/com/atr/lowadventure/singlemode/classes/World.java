package com.atr.lowadventure.singlemode.classes;

import java.util.ArrayList;

public class World {

    private int[][][] _map;
    private ArrayList<Entity> _entity = new ArrayList<Entity>();

    public void World(){
      setSize(100, 5, 100);
      generateFlat();
    }

    public int get(int x, int y, int z){
        return _map[x][y][z];
    }

    public void set(int x, int y, int z, int value){
        _map[x][y][z] = value;
    }

    private void setSize(int x, int y, int z){
      _map   = new int[x][y][z];
    }

    private void generateFlat(){
     for(int x=0; x<_map.length; x++ ){
         for(int z=0; z<_map[0][0].length; z++ ){
             _map[x][0][z] = 1;
         }
     }
    }

}
