package com.pm.ecommerce.account_service.models;

import lombok.Data;

@Data
public class LoginResponse {
    protected String name;
    protected String token;
    protected String email;
    protected int id;

}
