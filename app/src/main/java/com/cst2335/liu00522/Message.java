package com.cst2335.liu00522;

import android.graphics.Bitmap;

public class Message {
    private int type;
    private String content;
    private Bitmap icon;

    //  cons


    public Message( ) {
    }

    public Message(String content, int type) {
        this.content = content;
        this.type = type;
    }

    public Message(int type, String content, Bitmap icon) {
        this.type = type;
        this.content = content;
        this.icon = icon;
    }

    //  get & set
    public void setType(int type) {
        this.type = type;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Bitmap getIcon() {
        return icon;
    }

    public void setIcon(Bitmap icon) {
        this.icon = icon;
    }

    //  getter
    public String getContent() {
        return content;
    }

    public int getType() {
        return type;
    }


}
