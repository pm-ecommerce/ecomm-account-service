package com.pm.ecommerce.account_service.Models;

import com.pm.ecommerce.entities.User;
import lombok.Data;

@Data
public class UserResponse {

    protected int id;
    protected String name;
    protected String email;

    public UserResponse(User user) {
        setName(user.getName());
        setEmail(user.getEmail());
        setId(user.getId());
    }
}

