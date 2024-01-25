package com.nitconfbackend.nitconf.auth;
import com.nitconfbackend.nitconf.models.Level;
import com.nitconfbackend.nitconf.models.Status;
import com.nitconfbackend.nitconf.models.Tags;

import java.util.Date;
import java.util.List;

public class Sessionrequest {
    public String title;
    public String description;
    public String language;
    public Level level;
    public Status status;
    public Date date;
    public List<Tags> tags;
}
