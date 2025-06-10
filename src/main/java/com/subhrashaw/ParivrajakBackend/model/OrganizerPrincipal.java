package com.subhrashaw.ParivrajakBackend.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class OrganizerPrincipal implements UserDetails {
    private Organizer organizer;
    public OrganizerPrincipal(Organizer organizer)
    {
        this.organizer=organizer;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ORGANIZER"));
    }

    @Override
    public String getPassword() {
        return organizer.getPassword();
    }

    @Override
    public String getUsername() {
        return organizer.getEmail();
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
