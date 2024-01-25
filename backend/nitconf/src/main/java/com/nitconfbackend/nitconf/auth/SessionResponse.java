package com.nitconfbackend.nitconf.auth;

import java.util.Date;

import com.nitconfbackend.nitconf.models.Level;
import com.nitconfbackend.nitconf.models.Status;

public class SessionResponse {
    public String title;
    public String description;
    public String language;
    public Level level;
    public Status status;
    public Date date;
    // public List<Tags> tags;
    public Integer version;
}
