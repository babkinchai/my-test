package org.example.mytest.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

@Entity
@Table(name = "t_student")
@Data
public class Student implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String fio;
    private String username;
    private String password;
    private Date registration_date;
    @Column(name = "telegram_id")
    private Long telegramId;
    @Column(name = "telegram_username")
    private String telegramUsername;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public String getUsername() {
        return username;
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
    @Column(name = "telegram_id")
    private Long telegramId;
    @Column(name = "telegram_username")
    private String telegramUsername;
}
