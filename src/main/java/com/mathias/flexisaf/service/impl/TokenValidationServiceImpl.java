package com.mathias.flexisaf.service.impl;

import com.mathias.flexisaf.entity.ConfirmationTokenModel;
import com.mathias.flexisaf.entity.Person;
import com.mathias.flexisaf.repository.ConfirmationTokenRepository;
import com.mathias.flexisaf.repository.PersonRepository;
import com.mathias.flexisaf.service.TokenValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TokenValidationServiceImpl implements TokenValidationService {
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final PersonRepository personRepository;

    @Override
    public String validateToken(String token) {
        Optional<ConfirmationTokenModel> confirmationTokenOptional = confirmationTokenRepository.findByToken(token);
        if (confirmationTokenOptional.isEmpty()) {
            return "Invalid token";
        }
        ConfirmationTokenModel confirmationToken = confirmationTokenOptional.get();
        if (confirmationToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            return "Token has expired";
        }
        Person person = confirmationToken.getPersons();
        person.setEnabled(true);
        personRepository.save(person);

        confirmationTokenRepository.delete(confirmationToken);

        return "Your Email has been Confirmed Succesfully";
    }
}
