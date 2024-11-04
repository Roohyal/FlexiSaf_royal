package com.mathias.flexisaf.service.impl;

import com.mathias.flexisaf.entity.ConfirmationTokenModel;
import com.mathias.flexisaf.entity.JToken;
import com.mathias.flexisaf.entity.Person;
import com.mathias.flexisaf.enums.TokenType;
import com.mathias.flexisaf.exceptions.AlreadyExistsException;
import com.mathias.flexisaf.exceptions.NotEnabledException;
import com.mathias.flexisaf.exceptions.NotFoundException;
import com.mathias.flexisaf.infrastructure.config.JwtService;
import com.mathias.flexisaf.payload.request.LoginRequest;
import com.mathias.flexisaf.payload.request.PersonRegisterRequest;
import com.mathias.flexisaf.payload.response.LoginInfo;
import com.mathias.flexisaf.payload.response.LoginResponse;
import com.mathias.flexisaf.payload.response.PersonRegisterResponse;
import com.mathias.flexisaf.repository.ConfirmationTokenRepository;
import com.mathias.flexisaf.repository.JTokenRepository;
import com.mathias.flexisaf.repository.PersonRepository;
import com.mathias.flexisaf.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JTokenRepository jTokenRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public PersonRegisterResponse registerPerson(PersonRegisterRequest personRegisterRequest) throws Exception {
        // Validate email format
        String emailRegex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(personRegisterRequest.getEmail());

        if (!matcher.matches()) {
            throw new Exception("Invalid email domain");
        }
        String[] emailParts = personRegisterRequest.getEmail().split("\\.");
        if (emailParts.length < 2 || emailParts[emailParts.length - 1].length() < 2){
            throw new Exception("Invalid email domain");
        }
        if (personRepository.existsByEmail(personRegisterRequest.getEmail())) {
            throw new AlreadyExistsException("Email is already in use.");
        }
        if(!personRegisterRequest.getPassword().equals(personRegisterRequest.getConfirmPassword())) {
            throw new Exception("Passwords do not match.");
        }

        Optional<Person> existingUser = personRepository.findByEmail(personRegisterRequest.getEmail());
        if (existingUser.isPresent()) {
            throw new AlreadyExistsException("User already exists, please Login");
        }

        Person person = Person.builder()
                .firstName(personRegisterRequest.getFirstName())
                .lastName(personRegisterRequest.getLastName())
                .email(personRegisterRequest.getEmail())
                .password(passwordEncoder.encode(personRegisterRequest.getPassword()))
                .address(personRegisterRequest.getAddress())
                .dateOfBirth(personRegisterRequest.getDateOfBirth())
                .gender(personRegisterRequest.getGender())
                .phoneNumber(personRegisterRequest.getPhoneNumber())
                .build();

        Person savedPerson = personRepository.save(person);

        ConfirmationTokenModel confirmationTokenModel = new ConfirmationTokenModel(savedPerson);
        confirmationTokenRepository.save(confirmationTokenModel);

        return PersonRegisterResponse.builder()
                .responseCode("001")
                .responseMessage("Congratulations! You have successfully registered!, Kindly Login, Confirm and Login  ")
                .build();
    }

    private void saveUserToken(Person person, String jwtToken){
        var token = JToken.builder()
                .person(person)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        jTokenRepository.save(token);
    }
    private void revokeAllUserTokens(Person person){
        var validUserTokens = jTokenRepository.findAllValidTokenByUser(person.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        jTokenRepository.saveAll(validUserTokens);
    }


    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );
        Person person = personRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(()-> new NotFoundException("User is not found"));

        if (!person.isEnabled()){
            throw new NotEnabledException("User account is not enabled. Please check your email to confirm your account.");
        }

        var jwtToken = jwtService.generateToken(person);
        revokeAllUserTokens(person);
        saveUserToken(person, jwtToken);

        return LoginResponse.builder()
                .responseCode("002")
                .responseMessage("Your have been login successfully")
                .loginInfo(LoginInfo.builder()
                        .email(person.getEmail())
                        .token(jwtToken)
                        .build())
                .build();
    }

}
