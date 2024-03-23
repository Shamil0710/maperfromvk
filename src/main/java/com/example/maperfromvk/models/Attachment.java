package com.example.maperfromvk.models;

import lombok.Data;

@Data
public class Attachment {
    public String type;
    public int id;
    public int from;
    public String link;
    public int duration;
    public String text;
    public String hd;
}
