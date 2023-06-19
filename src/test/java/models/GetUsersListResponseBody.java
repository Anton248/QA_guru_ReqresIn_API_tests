package models;

import lombok.Data;

import java.util.List;

@Data
public class GetUsersListResponseBody {

    Integer
        page,
        per_page,
        total,
        total_pages;

    List<UserModel> data;

    SupportModel support;

    public Integer getPerPage() {
        return per_page;
    }

    public Integer getTotalPages() {
        return total_pages;
    }
}
