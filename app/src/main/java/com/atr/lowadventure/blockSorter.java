package com.atr.lowadventure;

import com.atr.lowadventure.item.Block;

import java.util.ArrayList;
import java.util.HashMap;

public class blockSorter {
    HashMap<String, Sorter> sorter;
    Textures textures;

    private class Sorter{
        ArrayList<Integer> blocks;
        String texture;

        public Sorter( String texture, ArrayList<Integer> blocks)
        {
            this.blocks = blocks;
            this.texture=texture;
        }

    }


    public blockSorter(Textures textures)
    {
        this.textures=textures;
        sorter=new HashMap<String, Sorter>();
    }

    public void addSort(String name, String texture, ArrayList<Integer>blocks)
    {
        sorter.put(name, new Sorter(texture, blocks));
    }

    public ArrayList<Integer> getSort(Integer index)
    {
        int i=0;
        for(String id: sorter.keySet())
        {
            if(i == index)
            {
                return sorter.get(id).blocks;
            }
            i++;
        }
        return null;
    }

    public ArrayList<Integer> getIndex()
    {
        ArrayList<Integer> result = new ArrayList<Integer>();
        int i=0;
        for(String id: sorter.keySet())
        {
            result.add(i);
            i++;
        }

        return result;
    }

    public HashMap<Integer, Block> getFakeBlocks()
    {
        HashMap<Integer,Block> result = new HashMap<Integer, Block>();

        int i= 0;
        for(String id: sorter.keySet())
        {
            result.put(i, new Block(id, sorter.get(id).texture));
            i++;
        }
        return result;
    }
}
