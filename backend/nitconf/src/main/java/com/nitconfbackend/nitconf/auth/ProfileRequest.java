package com.nitconfbackend.nitconf.auth;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class ProfileRequest {
    public String firstName;
    public String lastName;
    public String email;
    public String phoneNumber;
}
