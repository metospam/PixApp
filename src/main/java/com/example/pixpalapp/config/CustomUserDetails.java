package com.example.pixpalapp.config;

import com.example.pixpalapp.model.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Getter
@Configuration
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CustomUserDetails implements UserDetails {

    Long id;
    String imagePath;
    String username;
    String password;
    List<GrantedAuthority> roles;

    public CustomUserDetails(User user) {
        this.id = user.getId();
        this.imagePath = user.getImagePath();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.roles = Collections.singletonList(
                new SimpleGrantedAuthority("USER_ROLE"));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
