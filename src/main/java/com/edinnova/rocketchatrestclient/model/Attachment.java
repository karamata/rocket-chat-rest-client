package com.edinnova.rocketchatrestclient.model;

import java.util.ArrayList;

public class Attachment {
    private String color, text, ts, thumbUrl, messageLink;
    private boolean collapsed;
    private String authorName, authorLink, authorIcon;
    private String title, titleLink, titleLinkDownload;
    private String imageUrl, audioUrl, videoUrl;
    private ArrayList<AttachmentField> fields;
    
    public Attachment() {
        this.fields = new ArrayList<AttachmentField>();
    }
    
    /**
     * @return the color
     */
    public String getColor() {
        return color;
    }
    /**
     * @param color the color to set
     */
    public void setColor(String color) {
        this.color = color;
    }
    /**
     * @return the text
     */
    public String getText() {
        return text;
    }
    /**
     * @param text the text to set
     */
    public void setText(String text) {
        this.text = text;
    }
}
