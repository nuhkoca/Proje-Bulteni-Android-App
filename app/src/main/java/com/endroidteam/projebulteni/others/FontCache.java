package com.endroidteam.projebulteni.others;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.Toast;

import java.util.Hashtable;

import com.endroidteam.projebulteni.R;

/**
 * Created by NuhKoca on 23.04.2016.
 */
public class FontCache {

    public static final int ROBOTO_BLACK = 0;
    public static final int ROBOTO_BOLD = 1;
    public static final int ROBOTO_LIGHT = 2;
    public static final int ROBOTO_MEDIUM = 3;
    public static final int ROBOTO_REGULAR = 4;
    public static final int ROBOTO_THIN = 5;

    private static final String ROBOTO_BLACK_PATH = "fonts/Roboto_Black.ttf";
    private static final String ROBOTO_BOLD_PATH = "fonts/Roboto_Bold.ttf";
    private static final String ROBOTO_LIGHT_PATH = "fonts/Roboto_Light.ttf";
    private static final String ROBOTO_MEDIUM_PATH = "fonts/Roboto_Medium.ttf";
    private static final String ROBOTO_REGULAR_PATH = "fonts/Roboto_Regular.ttf";
    private static final String ROBOTO_THIN_PATH = "fonts/Roboto_Thin.ttf";

    private static Hashtable<String, Typeface> fontCacheTable = new Hashtable<>();

    public static Typeface get(int fontType, Context context) {

        if (fontType == ROBOTO_BLACK) {
            Typeface roboto_black = fontCacheTable.get(ROBOTO_BLACK_PATH);
            if (roboto_black == null) {
                try {
                    roboto_black = Typeface.createFromAsset(context.getAssets(), ROBOTO_BLACK_PATH);
                    fontCacheTable.put(ROBOTO_BLACK_PATH, roboto_black);
                } catch (Exception e) {
                    Toast.makeText(context, R.string.font_error, Toast.LENGTH_LONG).show();
                    return null;
                }
            }
            return roboto_black;

        } else if (fontType == ROBOTO_BOLD) {
            Typeface roboto_bold = fontCacheTable.get(ROBOTO_BOLD_PATH);
            if (roboto_bold == null) {
                try {
                    roboto_bold = Typeface.createFromAsset(context.getAssets(), ROBOTO_BOLD_PATH);
                    fontCacheTable.put(ROBOTO_BOLD_PATH, roboto_bold);
                } catch (Exception e) {
                    Toast.makeText(context, R.string.font_error, Toast.LENGTH_LONG).show();
                    return null;
                }
            }
            return roboto_bold;

        } else if (fontType == ROBOTO_LIGHT) {
            Typeface roboto_light = fontCacheTable.get(ROBOTO_LIGHT_PATH);
            if (roboto_light == null) {
                try {
                    roboto_light = Typeface.createFromAsset(context.getAssets(), ROBOTO_LIGHT_PATH);
                    fontCacheTable.put(ROBOTO_LIGHT_PATH, roboto_light);
                } catch (Exception e) {
                    Toast.makeText(context, R.string.font_error, Toast.LENGTH_LONG).show();
                    return null;
                }
            }
            return roboto_light;

        } else if (fontType == ROBOTO_MEDIUM) {
            Typeface roboto_medium = fontCacheTable.get(ROBOTO_MEDIUM_PATH);
            if (roboto_medium == null) {
                try {
                    roboto_medium = Typeface.createFromAsset(context.getAssets(), ROBOTO_MEDIUM_PATH);
                    fontCacheTable.put(ROBOTO_MEDIUM_PATH, roboto_medium);
                } catch (Exception e) {
                    Toast.makeText(context, R.string.font_error, Toast.LENGTH_LONG).show();
                    return null;
                }
            }
            return roboto_medium;

        } else if (fontType == ROBOTO_REGULAR) {
            Typeface roboto_regular = fontCacheTable.get(ROBOTO_REGULAR_PATH);
            if (roboto_regular == null) {
                try {
                    roboto_regular = Typeface.createFromAsset(context.getAssets(), ROBOTO_REGULAR_PATH);
                    fontCacheTable.put(ROBOTO_REGULAR_PATH, roboto_regular);
                } catch (Exception e) {
                    Toast.makeText(context, R.string.font_error, Toast.LENGTH_LONG).show();
                    return null;
                }
            }
            return roboto_regular;

        } else if (fontType == ROBOTO_THIN) {
            Typeface roboto_thin = fontCacheTable.get(ROBOTO_THIN_PATH);
            if (roboto_thin == null) {
                try {
                    roboto_thin = Typeface.createFromAsset(context.getAssets(), ROBOTO_THIN_PATH);
                    fontCacheTable.put(ROBOTO_THIN_PATH, roboto_thin);
                } catch (Exception e) {
                    Toast.makeText(context, R.string.font_error, Toast.LENGTH_LONG).show();
                    return null;
                }
            }
            return roboto_thin;

        }

        return null;
    }
}
