package com.example.KeyboardArenaProject.entity;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

//import com.example.KeyboardArenaProject.utils.GenerateIdUtils;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "user")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity implements UserDetails{
    @Id
    @Column(name="id", updatable = false)
    private String id;

    @Column(name="user_id", nullable = false)
    private String userId;

    @Column(name="password", nullable = false)
    private String password;

    @Column(name="nickname", nullable = false)
    private String nickname;

    @Column(name="user_rank", nullable = false)
//    @ColumnDefault("1")
    private int rank;

    @Column(name="point", nullable = false)
//    @ColumnDefault("0")
    private int point;

    @Column(name="email", nullable = false)
    private String email;

    @Column(name="find_pw", nullable = false)
    private String findPw;

    @Column(name="find_pw_question", nullable = false)
    private String findPwQuestion;

    @Column(name="is_active", nullable = false)
//    @ColumnDefault("true")
    private Boolean isActive;

//    public User(LocalDateTime signUpDate) {
//        this.id = GenerateIdUtils.generateUserId(signUpDate);
//    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("user"));
    }

    @Override
    public String getUsername() {
        return userId;
    }

    @Override
    public String getPassword() {
        return password;
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