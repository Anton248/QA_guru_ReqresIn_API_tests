package models;

import lombok.Data;

@Data
public class UserModel {

    Integer id;

    String
        email,
        first_name,
        last_name,
        avatar;


}
