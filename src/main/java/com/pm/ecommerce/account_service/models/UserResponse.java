package com.pm.ecommerce.account_service.models;

import com.pm.ecommerce.entities.User;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class UserResponse {

    protected int id;
    protected String name;
    protected String email;
    protected Timestamp createdDate;
    protected Timestamp updatedDate;

    public UserResponse(User user) {
        setName(user.getName());
        setEmail(user.getEmail());
        setId(user.getId());
        createdDate = user.getCreatedDate();
        updatedDate = user.getUpdatedDate();
    }
}

