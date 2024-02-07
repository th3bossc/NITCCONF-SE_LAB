package com.nitconfbackend.nitconf.types;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class ReviewRequest {
    public String comment;

    public void setComment(String comment){
        this.comment=comment;
    }
}
