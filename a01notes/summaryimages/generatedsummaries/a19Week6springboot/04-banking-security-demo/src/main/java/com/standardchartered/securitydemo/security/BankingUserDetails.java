package com.standardchartered.securitydemo.security;

import com.standardchartered.securitydemo.entity.SecurityUserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/*
 * SYNTAX COMMENTARY: Implementing UserDetails Core Interface
 *
 * UserDetails:
 * - Spring Security's core contract for encapsulating user identity, credentials, andGrantedAuthorities.
 */
public class BankingUserDetails implements UserDetails {

    private final SecurityUserEntity user;

    public BankingUserDetails(SecurityUserEntity user) {
        this.user = user;
    }

    /*
     * SYNTAX COMMENTARY: GrantedAuthorities Mapping
     *
     * SimpleGrantedAuthority(user.getRole()):
     * - Wraps the string role (e.g. "ROLE_CUSTOMER" or "ROLE_ADMIN") into a GrantedAuthority object used by AuthorizationFilter during path checks.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(user.getRole()));
    }

    @Override
    public String getPassword() {
        return user.getPassword(); // Returns BCrypt Hash from DB
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }
}
