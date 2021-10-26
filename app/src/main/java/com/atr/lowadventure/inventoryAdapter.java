package com.atr.lowadventure;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.atr.lowadventure.activity.GameActivity;
import com.atr.lowadventure.item.Block;

import java.util.ArrayList;
import java.util.HashMap;

class inventoryAdapter extends ArrayAdapter<Integer> {

    private final GameActivity gameActivity;
    private ArrayList<Integer> list;
            private HashMap<Integer, Block> blocks;
            private Textures textures;

            public inventoryAdapter(GameActivity gameActivity, Context context, ArrayList<Integer> list, HashMap<Integer, Block> blocks, Textures textures) {
                        super(context, R.layout.inventory, list);
                this.gameActivity = gameActivity;
                this.blocks= blocks;
                        this.list=list;
                        this.textures=textures;
                }

            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;
        LayoutInflater vi = (LayoutInflater) gameActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = vi.inflate(R.layout.inventory, null);
         Block b = blocks.get(list.get(position));
        try{

            ((TextView) v.findViewById(R.id.name)).setText(b.name);
              ((ImageView) v.findViewById(R.id.image)).setImageBitmap(textures.get(b.texture));
            ((ImageView) v.findViewById(R.id.rotate)).setImageBitmap(textures.get("buttonrotate"));
            ((ImageView) v.findViewById(R.id.wehicle)).setImageBitmap(textures.get("ridebutton"));
            ((ImageView) v.findViewById(R.id.placable)).setImageBitmap(textures.get("plusbutton"));
            ((ImageView) v.findViewById(R.id.usable)).setImageBitmap(textures.get("usebutton"));
            ((ImageView) v.findViewById(R.id.stacable)).setImageBitmap(textures.get("button2"));

            if(b.author.equals(""))
            {
                ((TextView)v.findViewById(R.id.author)).setVisibility(View.GONE);
            } else {
                ((TextView)v.findViewById(R.id.author)).setVisibility(View.VISIBLE);
                ((TextView)v.findViewById(R.id.author)).setText("by"+b.author);
            }
            if(b.canRotate)
            {
                 ((ImageView) v.findViewById(R.id.rotate)).setVisibility(View.VISIBLE);
            } else {
                 ((ImageView) v.findViewById(R.id.rotate)).setVisibility(View.GONE);
            }

            if(b.isWehicle)
            {
                 ((ImageView) v.findViewById(R.id.wehicle)).setVisibility(View.VISIBLE);
            } else {
                 ((ImageView) v.findViewById(R.id.wehicle)).setVisibility(View.GONE);
            }

            if(b.isCombinable)
            {
                 ((ImageView) v.findViewById(R.id.stacable)).setVisibility(View.VISIBLE);
            } else {
                 ((ImageView) v.findViewById(R.id.stacable)).setVisibility(View.GONE);
            }

            if(b.isPlacable)
            {
                 ((ImageView) v.findViewById(R.id.placable)).setVisibility(View.VISIBLE);
            } else {
                 ((ImageView) v.findViewById(R.id.placable)).setVisibility(View.GONE);
            }

            if(b.isUsable)
            {
                 ((ImageView) v.findViewById(R.id.usable)).setVisibility(View.VISIBLE);
            } else {
                 ((ImageView) v.findViewById(R.id.usable)).setVisibility(View.GONE);
            }

        } catch(Exception err){}
        return v;
                }
}
