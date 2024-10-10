package com.mathias.flexisaf.service;

import com.mathias.flexisaf.payload.request.LoginRequest;
import com.mathias.flexisaf.payload.request.PersonRegisterRequest;
import com.mathias.flexisaf.payload.response.LoginResponse;
import com.mathias.flexisaf.payload.response.PersonRegisterResponse;



public interface PersonService {
    PersonRegisterResponse registerPerson(PersonRegisterRequest personRegisterRequest) throws Exception;

    LoginResponse login(LoginRequest loginRequest);
}
