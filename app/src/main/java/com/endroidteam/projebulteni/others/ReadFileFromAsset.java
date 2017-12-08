package com.endroidteam.projebulteni.others;

import android.content.Context;
import android.widget.Toast;

import com.endroidteam.projebulteni.R;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by NuhKoca on 4.05.2016.
 */
public class ReadFileFromAsset {

    public static String getFile(Context context, String text) {
        byte[] buffer = null;
        InputStream is;

        try {
            is = context.getAssets().open(text);
            int size = is.available(); //size of the file in bytes
            buffer = new byte[size]; //declare the size of the byte array with size of the file
            is.read(buffer); //read file
            is.close(); //close file
        } catch (IOException e) {
            Toast.makeText(context, String.valueOf(R.string.asset_error), Toast.LENGTH_LONG).show();
        }

        String str_data = new String(buffer);
        return str_data;
    }
}
