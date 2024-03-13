package com.nitconfbackend.nitconf.types;

import com.nitconfbackend.nitconf.models.Level;
import com.nitconfbackend.nitconf.models.Status;

import java.util.List;

public class PaperRequest {
    public String title;
    public String description;
    public String language;
    public Level level;
    public Status status;
    public List<String> tags;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String desc) {
        this.description = desc;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
