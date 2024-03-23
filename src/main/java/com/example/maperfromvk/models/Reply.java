package com.example.maperfromvk.models;

import lombok.Data;

@Data
public class Reply {
    public int id;
    public int from;
    public String text;
    public int time;
}
