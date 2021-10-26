package com.atr.lowadventure;

import android.graphics.Canvas;
import android.os.AsyncTask;

public class DrawTask extends AsyncTask<Void, Void, Void> {

    DrawThread dt;
    public DrawTask(DrawThread dt){
        this.dt=dt;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        dt.WorldRender();
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        dt.frameDrawed=true;

    }
}
