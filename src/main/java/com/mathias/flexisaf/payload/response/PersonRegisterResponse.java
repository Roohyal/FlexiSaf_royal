package com.mathias.flexisaf.payload.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PersonRegisterResponse {
    private String responseCode;
    private String responseMessage;
}
