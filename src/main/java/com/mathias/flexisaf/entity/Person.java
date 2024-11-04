package com.mathias.flexisaf.entity;

import com.mathias.flexisaf.enums.Gender;
import com.mathias.flexisaf.enums.Roles;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import jakarta.validation.constraints.NotBlank;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Person extends BaseClass implements UserDetails {
    @NotBlank(message = "Full name is required")
    private String firstName;

    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email Format is Wrong ")
    private String email;

    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @NotBlank(message = "Password is required")
    private String password;

    private String address;

    private LocalDate dateOfBirth;

    @Transient
    private String confirmPassword;

    @Enumerated(EnumType.STRING)
    private Roles role;

    private boolean enabled = false;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true )
    private List<Task> tasks;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JToken> jtokens;

    @OneToMany(mappedBy = "persons",  cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ConfirmationTokenModel> confirmationTokens;



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
