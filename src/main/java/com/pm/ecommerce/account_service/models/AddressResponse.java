package com.pm.ecommerce.account_service.models;

import com.pm.ecommerce.entities.Address;
import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
public class AddressResponse {

    private int id;
    private String address1;
    private String address2;
    private String zipcode;
    private String city;
    private String state;
    private String country;

    public AddressResponse(Address address) {
        id = address.getId();
        address1= address.getAddress1();
        address2= address.getAddress2();
        zipcode = address.getZipcode();
        city= address.getCity();
        state= address.getState();
        country= address.getCountry();
    }
}
