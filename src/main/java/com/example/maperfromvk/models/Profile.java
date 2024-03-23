package com.example.maperfromvk.models;

import lombok.Data;

@Data
public class Profile {
    public String firstName;
    public String lastName;
    public int id;
    public int gender;
    public String photo;
    public int ban;
    public int photosCount;
    public int videosCount;
    public int audiosCount;
    public int docsCount;
    public int attachmentsCount;
    public int messagesCount;
    public int online;
}
