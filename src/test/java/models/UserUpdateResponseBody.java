package models;

import lombok.Data;

@Data
public class UserUpdateResponseBody {
    String
        name,
        job,
        updatedAt,
        error;

}
