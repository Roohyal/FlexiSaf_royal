package com.mathias.flexisaf.infrastructure.controller;

import com.mathias.flexisaf.payload.request.LoginRequest;
import com.mathias.flexisaf.payload.request.PersonRegisterRequest;
import com.mathias.flexisaf.payload.response.LoginResponse;
import com.mathias.flexisaf.payload.response.PersonRegisterResponse;
import com.mathias.flexisaf.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class PersonController {
    private final PersonService personService;

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
}
