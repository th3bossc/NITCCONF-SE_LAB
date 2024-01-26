package com.nitconfbackend.nitconf.RequestTypes;
import com.nitconfbackend.nitconf.models.Level;
import com.nitconfbackend.nitconf.models.Status;

import java.util.List;

public class SessionRequest {
    public String title;
    public String description;
    public String language;
    public Level level;
    public Status status;
    public List<String> tags;
}
