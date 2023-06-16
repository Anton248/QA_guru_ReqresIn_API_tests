package models;

import lombok.Data;

@Data
public class UserCreateResponseBody {
    String
        name,
        job,
        id,
        createdAt,
        error;

}
