package com.endroidteam.projebulteni.others;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by NuhKoca on 29.04.2016.
 */
public class GotoURLs {

    private static String FACEBOOK_ID = "projebulteni";
    private static String INSTAGRAM_ID = "projebulteni";
    private static String TWITTER_ID = "projebulteni";
    private static String LINKEDIN_ID = "nuhkoca";
    private static Intent mIntent;

    public static Intent setURLtoChrome(Context context, String url) {
        Uri uri = Uri.parse(url);
        mIntent = new Intent(Intent.ACTION_VIEW, uri);

        mIntent.setPackage("com.android.chrome");

        try {
            context.startActivity(mIntent);
        } catch (ActivityNotFoundException e) {
            context.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse(url)));
        }

        return null;
    }

    public static Intent getFacebookPageURL(Context context) {
        Uri uri = Uri.parse("fb://facewebmodal/f?href=/" + FACEBOOK_ID);
        mIntent = new Intent(Intent.ACTION_VIEW, uri);

        mIntent.setPackage("com.facebook.katana");

        try {
            context.startActivity(mIntent);
        } catch (ActivityNotFoundException e) {
            context.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://facebook.com/" + FACEBOOK_ID)));
        }

        return null;
    }

    public static Intent getInstagramPageURL(Context context) {
        Uri uri = Uri.parse("https://instagram.com/_u/" + INSTAGRAM_ID);
        mIntent = new Intent(Intent.ACTION_VIEW, uri);

        mIntent.setPackage("com.instagram.android");

        try {
            context.startActivity(mIntent);
        } catch (ActivityNotFoundException e) {
            context.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://instagram.com/_u/" + INSTAGRAM_ID)));
        }

        return null;
    }

    public static Intent getLinkedinPageURL(Context context) {
        Uri uri = Uri.parse("https://www.linkedin.com/in/" + LINKEDIN_ID);
        mIntent = new Intent(Intent.ACTION_VIEW, uri);

        mIntent.setPackage("com.linkedin.android");

        try {
            context.startActivity(mIntent);
        } catch (ActivityNotFoundException e) {
            context.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.linkedin.com/in/" + LINKEDIN_ID)));
        }

        return null;
    }

    public static Intent getTwitterPageURL(Context context) {
        Uri uri = Uri.parse("https://twitter.com/" + TWITTER_ID);
        mIntent = new Intent(Intent.ACTION_VIEW, uri);

        mIntent.setPackage("com.twitter.android");

        try {
            context.startActivity(mIntent);
        } catch (ActivityNotFoundException e) {
            context.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://twitter.com/" + TWITTER_ID)));
        }

        return null;
    }
}
