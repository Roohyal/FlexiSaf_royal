package com.mathias.flexisaf.payload.response;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponse {
    private String responseCode;
    private String responseMessage;
}
