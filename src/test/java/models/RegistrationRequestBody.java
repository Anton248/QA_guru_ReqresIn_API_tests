package models;

import lombok.Data;

@Data
public class RegistrationRequestBody {
    String email;
    String password;
}
