package com.edinnova.rocketchatrestclient.model;

import java.util.ArrayList;
import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.vdurmont.emoji.Emoji;
import com.vdurmont.emoji.EmojiManager;

public class Message extends Identified {
    private ArrayList<Attachment> attachments;
    private Emoji emoji;
    private String roomId, text, alias, avatar;

    public Message() {
        this.text = "";
        this.attachments = new ArrayList<Attachment>();
    }

    public Message(String message) {
        this.text = message;
        this.attachments = new ArrayList<Attachment>();
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getRoomId() {
        return this.roomId;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    @JsonSetter("alias")
    public void setUsernameAlias(String alias) {
        this.alias = alias;
    }

    @JsonGetter("alias")
    public String getUsernameAlias() {
        return this.alias;
    }

    public void setEmoji(String emoji) {
        this.emoji = EmojiManager.getForAlias(emoji.replaceAll(":", ""));
    }

    public String getEmoji() {
        return ":" + this.emoji.getAliases().get(0) + ":";
    }

    @JsonIgnore
    public void setEmojiAvatar(Emoji emoji) {
        this.emoji = emoji;
    }

    @JsonIgnore
    public Emoji getEmojiAvatar() {
        return this.emoji;
    }

    public void setAttachments(Attachment[] attachments) {
        this.attachments = (ArrayList<Attachment>) Arrays.asList(attachments);
    }

    public void addAttachment(Attachment attachment) {
        this.attachments.add(attachment);
    }

    public Attachment[] getAttachments() {
        return this.attachments.toArray(new Attachment[this.attachments.size()]);
    }
}
