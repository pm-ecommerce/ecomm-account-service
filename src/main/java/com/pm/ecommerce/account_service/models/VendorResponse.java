package com.pm.ecommerce.account_service.models;

import com.pm.ecommerce.entities.Address;
import com.pm.ecommerce.entities.Vendor;
import com.pm.ecommerce.enums.VendorStatus;
import lombok.Data;

@Data
public class VendorResponse {

    protected int id;
    protected String name;
    protected String businessName;
    protected String email;
    protected VendorStatus status;
    protected Address address;

    public VendorResponse(Vendor vendor) {
        setName(vendor.getName());
        setStatus(vendor.getStatus());
        setBusinessName(vendor.getBusinessName());
        setEmail(vendor.getEmail());
        setId(vendor.getId());
        if (vendor.getAddress() != null) {
            setAddress(vendor.getAddress());
        }
    }
}
