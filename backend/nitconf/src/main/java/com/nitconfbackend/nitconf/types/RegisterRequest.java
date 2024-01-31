package com.nitconfbackend.nitconf.types;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    public String firstName;
    public String lastName;
    public String email;
    public String password;
    public String phoneNumber;
}
