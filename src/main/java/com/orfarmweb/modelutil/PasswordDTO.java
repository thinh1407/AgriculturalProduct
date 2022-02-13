package com.orfarmweb.modelutil;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PasswordDTO {
    String oldPassword;
    String newPassword;
    String confirmPassword;
}
