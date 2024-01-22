package com.nitconfbackend.nitconf.auth;

import com.nitconfbackend.nitconf.models.Role;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class ProfileResponse {
    public String firstName;
    public String lastName;
    public String email;
    public String phoneNumber;
    public Role role;
}
