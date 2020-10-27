package com.pm.ecommerce.account_service.models;

import com.pm.ecommerce.entities.User;
import lombok.Data;

@Data
public class LoginRequest {

    protected String email;
    protected String password;

    public User toUser() {
        User user = new User();
        user.setEmail(getEmail());
        user.setPassword(getPassword());
        return user;
    }
}
