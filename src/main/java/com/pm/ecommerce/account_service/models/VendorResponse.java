package com.pm.ecommerce.account_service.models;

import com.pm.ecommerce.entities.Vendor;
import com.pm.ecommerce.enums.VendorStatus;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class VendorResponse {

    protected int id;
    protected String name;
    protected String businessName;
    protected String email;
    protected VendorStatus status;
    protected AddressResponse address;
    protected Timestamp createdDate;
    protected Timestamp updatedDate;

    public VendorResponse(Vendor vendor) {
        setName(vendor.getName());
        setStatus(vendor.getStatus());
        setBusinessName(vendor.getBusinessName());
        setEmail(vendor.getEmail());
        setId(vendor.getId());
        createdDate = vendor.getCreatedDate();
        updatedDate = vendor.getUpdatedDate();
        if (vendor.getAddress() != null) {
            setAddress(new AddressResponse(vendor.getAddress()));
        }
    }
}
