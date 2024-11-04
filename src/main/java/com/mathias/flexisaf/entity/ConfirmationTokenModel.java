package com.mathias.flexisaf.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table
public class ConfirmationTokenModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String token;

    @Column(nullable = false)
    private LocalDateTime expiryDate;

    @Column(nullable = false)
    private LocalDateTime creationDate;
    @ManyToOne
    @JoinColumn(nullable = false, name = "person_id")
    private Person persons;


    public ConfirmationTokenModel (Person person){
        this.token = UUID.randomUUID().toString();
        this.creationDate = LocalDateTime.now();
        this.expiryDate = creationDate.plusDays(1);
        this.persons = person;
    }
}
