package com.nitconfbackend.nitconf.models;

import java.util.Collection;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection="users")
public class User implements UserDetails {
    @Id public String id;
    public String firstName;
    public String lastName;
    @Indexed(unique = true) public String email;
    public String phoneNumber;
    @JsonIgnore public String password;
    public Role role;
    public Boolean isVerified;

    @JsonIgnore
    @DBRef
    public List<Session> sessions;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return email;
    }   

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() { 
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() { 
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() { 
        return true;
    }

    @Override //not required if password field is called password
    public String getPassword() {
        return password;
    }

}
