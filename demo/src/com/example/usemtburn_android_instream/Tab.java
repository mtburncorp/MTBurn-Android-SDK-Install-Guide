package com.example.usemtburn_android_instream;

public class Tab {
    private String title;
    private int color;
    private String url;
    
    public Tab(String title, int color, String url) {
        this.title = title;
        this.color = color;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
    
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
