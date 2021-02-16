package com.cst2335.liu00522;

import android.graphics.Bitmap;

public class Message {
    private int type;
    private String content;

    //  cons
    public Message() {
    }

    public Message(String content, int type) {
        this.content = content;
        this.type = type;
    }


    public Message(int type, String content) {
        this.type = type;
        this.content = content;
    }

    //  get & set
    public void setType(int type) {
        this.type = type;
    }

    public void setContent(String content) {
        this.content = content;
    }

    //  getter
    public String getContent() {
        return content;
    }

    public int getType() {
        return type;
    }


}
