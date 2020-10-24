package com.pm.ecommerce.account_service.models;

import com.pm.ecommerce.entities.User;
import lombok.Data;

@Data
public class UserRequest {

    protected String name;
    protected String email;
    protected String password;

    public User toUser() {
        User user = new User();
        user.setName(getName());
        user.setPassword(getPassword());
        user.setEmail(getEmail());
        return user;
    }
}
