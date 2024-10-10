package com.mathias.flexisaf.entity;

import com.mathias.flexisaf.enums.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;
import java.util.List;
@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Person extends BaseClass {
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


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true )
    private List<Task> tasks;
}
