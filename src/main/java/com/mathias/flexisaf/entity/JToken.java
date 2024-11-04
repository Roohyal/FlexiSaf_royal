package com.mathias.flexisaf.entity;

import com.mathias.flexisaf.enums.TokenType;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@Entity
@AllArgsConstructor
@Table(name = "jtoken")
public class JToken extends BaseClass{
    @Column(unique = true)
    public String token;

    public boolean revoked;

    public boolean expired;

    @Enumerated(EnumType.STRING)
    public TokenType tokenType = TokenType.BEARER;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id")
    public Person person;
}
