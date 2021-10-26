package com.atr.lowadventure;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.AudioTrack;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

class MiniMapTask extends AsyncTask<Void, Void, Void> {
    GameScreen gm;
    int sX, sY, size;
    Context context;
    ImageView image;

    public MiniMapTask(Context context,final GameScreen gm, int sX, int sY, int size){
        this.gm=gm;
        this.context=context;
        this.sY=sY;
        this.sX=sX;
        this.size=size;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... voids) {
            int[] pixels = new int[size*size];
            String current;
            for(int y=0; y < size; y++)
            {
                for(int x=0; x < size; x++)
                {
                    current = Integer.toString(sX+x)+":"+Integer.toString(sY+y);
                    try{
                        if(gm.world.containsKey(current)) {
                            pixels[(y * size) + x] = gm.blocks.get(gm.world.get(current).get("mid")).color;
                        } else {
                            pixels[(y*size)+x]= Color.BLUE;
                        }
                    }catch(Exception err){
                        pixels[(y*size)+x]= Color.BLUE;
                    }
                }
            }

            Bitmap result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888 );

            result.setPixels(pixels,  0, size, 0, 0, size, size);


            image = new ImageView(context);
            image.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
            image.setImageBitmap(Bitmap.createScaledBitmap(result, size*10, size*10, false));

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        LoWDialog dialog =new LoWDialog(context);
        dialog.setBackground(gm.textures.get("dialogbackground"));
        dialog.addHorizontalScrollView(image);;

        dialog.setTitle("Мини-картa");
        dialog.setIcon(gm.textures.get("map"));

        dialog.setPositiveButton("ок", new LoWDialog.OnClickListener() {
            public void onClick(DialogInterface dialog) {
                // Закрываем окно
                dialog.cancel();
            }
        });
        dialog.show();

    }
}
