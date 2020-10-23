package com.pm.ecommerce.account_service.Models;

import com.pm.ecommerce.entities.User;
import com.pm.ecommerce.entities.Vendor;
import com.pm.ecommerce.enums.VendorStatus;
import lombok.Data;

@Data
public class UserRequest {

    protected String name;
    protected String email;
    protected String password;

    public User toUser() {
        User user = new User();
        user.setName(getName());
        user.setEmail(getEmail());
        return user;
    }
}
