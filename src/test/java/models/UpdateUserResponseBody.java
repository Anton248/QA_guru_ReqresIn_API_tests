package models;

import lombok.Data;

@Data
public class UpdateUserResponseBody {
    String
        name,
        job,
        updatedAt,
        error;

}
