package com.endroidteam.projebulteni.models;

/**
 * Created by NuhKoca on 30.04.2016.
 */
public class SettingsModel {
   String title;

    public SettingsModel() {
    }

    public SettingsModel(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
