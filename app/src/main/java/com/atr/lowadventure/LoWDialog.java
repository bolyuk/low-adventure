package com.atr.lowadventure;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

public class LoWDialog extends Dialog {

    public interface OnClickListener {
        void onClick(DialogInterface dialog);
    }

    public interface OnItemSelected {
        void onClick(DialogInterface dialog, int which);
    }

    private ImageView icon;
    private ScrollView view;
    private LinearLayout childview;
    private LinearLayout background;
    private TextView title;
    private TextView text;

    private Button button1;
    private Button button2;
    private Button button3;

    public LoWDialog(Context context) {
        super(context);
        setContentView(R.layout.lowdialog);
        icon=(ImageView) findViewById(R.id.icon);
        background=(LinearLayout) findViewById(R.id.background);
        childview=(LinearLayout) findViewById(R.id.childview);
        view=(ScrollView) findViewById(R.id.view);
        title=(TextView) findViewById(R.id.title);
        text=(TextView) findViewById(R.id.text);
        button1=(Button) findViewById(R.id.button1);
        button2=(Button) findViewById(R.id.button2);
        button3=(Button) findViewById(R.id.button3);
        button1.setVisibility(View.INVISIBLE);
        button2.setVisibility(View.INVISIBLE);
        button3.setVisibility(View.INVISIBLE);
        text.setVisibility(View.GONE);
        title.setVisibility(View.GONE);

    }

    public void setIcon(Bitmap image) {
     icon.setImageBitmap(image);
    }

    public void setBackground(Bitmap image)
    {
        background.setBackground((Drawable)new BitmapDrawable(getContext().getResources(), image));
    }

    public void setAdapter(ListAdapter adapter, final LoWDialog.OnItemSelected listener)
    {
     ListView list = new ListView(getContext());
     list.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
     list.setAdapter(adapter);
     list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
         @Override
         public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
             listener.onClick(LoWDialog.this,i);
         }
     });
     childview.removeAllViews();;
     childview.addView(list);
    }

    public void setTitle(String text)
    {
        title.setVisibility(View.VISIBLE);
     title.setText(text);
    }

    public void addHorizontalScrollView(View view){
        HorizontalScrollView hor = new HorizontalScrollView(getContext());
        hor.setLayoutParams(view.getLayoutParams());
        hor.addView(view);
        clearView();
        setView(hor);
    }

    public void setText(String text){
        this.text.setText(text);
        this.text.setVisibility(View.VISIBLE);
    }

    public void setTextColor(int color)
    {
     text.setTextColor(color);
    }

    public void setView(View view){
        clearView();
     this.view.addView(view);
    }

    public void clearView(){
        view.removeAllViews();
    }

    public void setPositiveButton(String text, final LoWDialog.OnClickListener listener)
    {
     button1.setVisibility(View.VISIBLE);
     button1.setText(text);
     button1.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             listener.onClick(LoWDialog.this);
         }
     });
    }

    public void setNeutralButton(String text, final LoWDialog.OnClickListener listener)
    {
        button2.setVisibility(View.VISIBLE);
        button2.setText(text);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(LoWDialog.this);
            }
        });
    }

    public void setNegativeButton(String text, final LoWDialog.OnClickListener listener)
    {
        button3.setVisibility(View.VISIBLE);
        button3.setText(text);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(LoWDialog.this);
            }
        });
    }


    @Override
    public void show(){
        super.show();
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }

}
