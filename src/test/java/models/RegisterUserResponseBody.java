package models;

import lombok.Data;

@Data
public class RegisterUserResponseBody {

    String id;
    String token;
    String error;

}
