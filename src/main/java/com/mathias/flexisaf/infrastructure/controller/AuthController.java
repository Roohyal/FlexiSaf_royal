package com.mathias.flexisaf.infrastructure.controller;

import com.mathias.flexisaf.payload.request.ForgetPasswordRequestDto;
import com.mathias.flexisaf.payload.request.LoginRequest;
import com.mathias.flexisaf.payload.request.PersonRegisterRequest;
import com.mathias.flexisaf.payload.request.ResetPasswordRequestDto;
import com.mathias.flexisaf.payload.response.LoginResponse;
import com.mathias.flexisaf.payload.response.PersonRegisterResponse;
import com.mathias.flexisaf.service.PersonService;
import com.mathias.flexisaf.service.TokenValidationService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final PersonService personService;
    private final TokenValidationService tokenValidationService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody PersonRegisterRequest person) throws Exception {

       PersonRegisterResponse response = personService.registerPerson(person);

        return ResponseEntity.ok(response);
    }

   @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest request){
        LoginResponse response = personService.login(request);

        return ResponseEntity.ok(response);
   }

   @GetMapping("/confirm")
    public ResponseEntity<?> confirmUser(@RequestParam("token") String token){
       String result = tokenValidationService.validateToken(token);
       if ("Email confirmed successfully".equals(result)) {
           return ResponseEntity.ok(Collections.singletonMap("message", result));
       } else {
           return ResponseEntity.badRequest().body(Collections.singletonMap("message", result));
       }
   }

   @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody ForgetPasswordRequestDto requestDto) throws MessagingException {

        String response = personService.forgotPassword(requestDto);

        return ResponseEntity.ok(response);

   }

   @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequestDto requestDto) {
       String response = personService.resetPassword(requestDto);

       return ResponseEntity.ok(response);
   }
}
