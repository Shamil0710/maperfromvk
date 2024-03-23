package com.example.maperfromvk.models;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Message {
    public int id;
    public int from;
    public String text;
    public int time;
    public ArrayList<Attachment> attachments;
    public Reply reply;
}
