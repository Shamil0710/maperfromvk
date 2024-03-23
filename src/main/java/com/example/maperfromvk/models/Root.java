package com.example.maperfromvk.models;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Root {
    public ArrayList<Message> messages;
    public ArrayList<Profile> profiles;
}
