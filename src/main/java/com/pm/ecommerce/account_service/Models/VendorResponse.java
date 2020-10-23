package com.pm.ecommerce.account_service.Models;

import com.pm.ecommerce.entities.Vendor;
import com.pm.ecommerce.enums.VendorStatus;
import lombok.Data;

@Data
public class VendorResponse {

    protected int id;
    protected String name;
    protected String businessName;
    protected String email;
    protected String password;
    protected VendorStatus status;

    public VendorResponse(Vendor vendor) {
        setName(vendor.getName());
        setStatus(vendor.getStatus());
        setBusinessName(vendor.getBusinessName());
        setEmail(vendor.getEmail());
        setId(vendor.getId());
    }
}
