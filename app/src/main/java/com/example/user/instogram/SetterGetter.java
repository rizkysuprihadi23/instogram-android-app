package com.example.user.instogram;

/**
 * Created by User on 13-Sep-17.
 */

public class SetterGetter {
    private String username;
    private String gambar;
    private String caption;

    public SetterGetter(String username, String gambar, String caption) {
        this.username = username;
        this.gambar = gambar;
        this.caption = caption;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

}
