package models;

import lombok.Data;

@Data
public class RegisterUserRequestBody {
    String email;
    String password;
}
