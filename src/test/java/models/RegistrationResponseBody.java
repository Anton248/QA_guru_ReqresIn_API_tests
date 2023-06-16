package models;

import lombok.Data;

@Data
public class RegistrationResponseBody {

    String id;
    String token;
    String error;

}
