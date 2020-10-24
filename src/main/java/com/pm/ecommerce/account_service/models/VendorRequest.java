package com.pm.ecommerce.account_service.models;

import com.pm.ecommerce.entities.Address;
import com.pm.ecommerce.entities.Vendor;
import com.pm.ecommerce.enums.VendorStatus;
import lombok.Data;

@Data
public class VendorRequest {

    protected String name;
    protected String businessName;
    protected String email;
    protected String address1;
    protected String address2;
    protected String city;
    protected String zipcode;
    protected String state;
    protected String country;
    protected String password;
    protected String passwordConfirmation;


    public Vendor toVendor() {
        Vendor vendor = new Vendor();
        vendor.setName(getName());
        vendor.setStatus(VendorStatus.REGISTERED);
        vendor.setBusinessName(getBusinessName());
        vendor.setEmail(getEmail());

        Address address = new Address();
        address.setAddress1(address1);
        address.setAddress2(address2);
        address.setCity(city);
        address.setState(state);
        address.setCountry(country);
        address.setZipcode(zipcode);
        vendor.setAddress(address);

        vendor.setPassword(password);

        return vendor;
    }
}
