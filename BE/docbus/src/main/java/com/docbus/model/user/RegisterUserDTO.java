package com.docbus.model.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterUserDTO {
    private String email;
    private String password;
    private String name;
    private String nickname;
    private String profileImage;
}
