package com.pm.ecommerce.account_service.Models;

import com.pm.ecommerce.entities.Vendor;
import com.pm.ecommerce.enums.VendorStatus;
import lombok.Data;

@Data
public class VendorRequest {

    protected String name;
    protected String businessName;
    protected String email;
    protected String password;

    public Vendor toVendor() {
        Vendor vendor2 = new Vendor();
        vendor2.setName(getName());
        vendor2.setStatus(VendorStatus.REGISTERED);
        vendor2.setBusinessName(getBusinessName());
        vendor2.setEmail(getEmail());
        return vendor2;
    }
}
