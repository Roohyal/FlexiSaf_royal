package com.mathias.flexisaf.payload.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskResponse {
    private String responseCode;
    private String responseMessage;
}
