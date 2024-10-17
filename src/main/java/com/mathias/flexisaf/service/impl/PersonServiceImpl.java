package com.mathias.flexisaf.service.impl;

import com.mathias.flexisaf.entity.Person;
import com.mathias.flexisaf.payload.request.LoginRequest;
import com.mathias.flexisaf.payload.request.PersonRegisterRequest;
import com.mathias.flexisaf.payload.response.LoginResponse;
import com.mathias.flexisaf.payload.response.PersonRegisterResponse;
import com.mathias.flexisaf.repository.PersonRepository;
import com.mathias.flexisaf.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public PersonRegisterResponse registerPerson(PersonRegisterRequest personRegisterRequest) throws Exception {
        if (personRepository.existsByEmail(personRegisterRequest.getEmail())) {
            throw new Exception("Email is already in use.");
        }
        if(!personRegisterRequest.getPassword().equals(personRegisterRequest.getConfirmPassword())) {
            throw new Exception("Passwords do not match.");
        }

        // Hash the password (using BCrypt for example)
        String hashedPassword = BCrypt.hashpw(personRegisterRequest.getPassword(), BCrypt.gensalt());

        Person person = Person.builder()
                .firstName(personRegisterRequest.getFirstName())
                .lastName(personRegisterRequest.getLastName())
                .email(personRegisterRequest.getEmail())
                .password(hashedPassword)
                .address(personRegisterRequest.getAddress())
                .dateOfBirth(personRegisterRequest.getDateOfBirth())
                .gender(personRegisterRequest.getGender())
                .phoneNumber(personRegisterRequest.getPhoneNumber())
                .build();

         personRepository.save(person);

        return PersonRegisterResponse.builder()
                .responseCode("001")
                .responseMessage("Congratulations! You have successfully registered!, Kindly Login ")
                .build();
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        // Use the repository method directly, which should return an Optional<Person>
        Optional<Person> optionalPerson = personRepository.findByEmail(loginRequest.getEmail());

        // Check if the user exists
        if (optionalPerson.isEmpty()) {
            throw new RuntimeException("Invalid email or password.");
        }

        Person person = optionalPerson.get();

        // Assuming you have a password encoder, use it to match the password
        if (!passwordEncoder.matches(loginRequest.getPassword(), person.getPassword())) {
            throw new RuntimeException("Invalid password.");
        }

        // If email and password are correct, return a success response
        return LoginResponse.builder()
                .responseCode("002")
                .responseMessage("You have successfully logged in.")
                .build();
    }

}
