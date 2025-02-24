package com.docbus.model.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegisterUserResponseDTO {
    private Long userId;
    private String email;
    private String name;
}
