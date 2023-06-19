package models;

import lombok.Data;

@Data
public class CreateUserResponseBody {
    String
        name,
        job,
        id,
        createdAt,
        error;

}
