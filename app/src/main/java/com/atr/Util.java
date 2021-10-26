package com.atr;

import android.content.Context;

import com.atr.lowadventure.FileUtil;
import com.google.firebase.database.DataSnapshot;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Util {

    public float pow(float number){
        return number*number;
    }

    public static void logAdd(final String _text, Context context) {
        if (FileUtil.isExistFile(FileUtil.getPackageDataDir(context).concat("/log.txt"))) {
            FileUtil.writeFile(FileUtil.getPackageDataDir(context).concat("/log.txt"), FileUtil.readFile(FileUtil.getPackageDataDir(context).concat("/log.txt")).concat(new SimpleDateFormat("\n[dd/MM HH:mm] ").format(Calendar.getInstance().getTime()).concat(_text)));
        }
        else {
            FileUtil.writeFile(FileUtil.readFile(FileUtil.getPackageDataDir(context).concat("/log.txt")), new SimpleDateFormat("[dd/MM HH:mm] ").format(Calendar.getInstance().getTime()).concat(_text));
        }
    }

    public static Object dsGetter(DataSnapshot ds, String key, Object elseValue)
    {
        if(ds.hasChild(key))
        {
            return ds.child(key).getValue(Object.class);
        }
        return elseValue;
    }

    public static Long dsGetter(DataSnapshot ds, String key, Long elseValue)
    {
        if(ds.hasChild(key) && ds.child(key).getValue() instanceof Long)
        {
            return ds.child(key).getValue(Long.class);
        }
        return elseValue;
    }

    public static Integer dsGetter(DataSnapshot ds, String key, Integer elseValue)
    {
        if (ds.hasChild(key) && ds.child(key).getValue() instanceof Long) {
            return(int)((long) ds.child(key).getValue(Long.class));
        }
        return elseValue;
    }

    public static Boolean dsGetter(DataSnapshot ds, String key, Boolean elseValue)
    {
        if(ds.hasChild(key) && ds.child(key).getValue() instanceof Boolean)
        {
            return ds.child(key).getValue(Boolean.class);
        }
        return elseValue;
    }

    public static String dsGetter(DataSnapshot ds, String key, String elseValue)
    {
        if(ds.hasChild(key) && ds.child(key).getValue() instanceof String)
        {
            return ds.child(key).getValue(String.class);
        }
        return elseValue;
    }

    public static Double dsGetter(DataSnapshot ds, String key, Double elseValue)
    {
        if(ds.hasChild(key))
        {
            if(ds.child(key).getValue() instanceof Double)
            {
                return ds.child(key).getValue(Double.class);
            } else {
            return dsGetter(ds, key, elseValue.longValue()).doubleValue();
        }
        }
        return elseValue;
    }
}
