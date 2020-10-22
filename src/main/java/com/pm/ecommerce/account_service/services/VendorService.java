package com.pm.ecommerce.account_service.services;

import com.pm.ecommerce.account_service.repositories.VendorRepository;
import com.pm.ecommerce.entities.Vendor;
import com.pm.ecommerce.enums.VendorStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class VendorService {

    @Autowired
    private VendorRepository vendorRepository;

    public Vendor createVendor(Vendor vendor) throws Exception {
        if (vendor == null) {
            throw new Exception("Data expected with this request");
        }

        if (vendor.getEmail() == null || vendor.getEmail().length() == 0) {
            throw new Exception("Please provide your business email");
        }

        if (!validateEmail(vendor.getEmail())) {
            throw new Exception("Email is invalid. Please provide a valid email");
        }

        if (vendor.getBusinessName() == null || vendor.getBusinessName().length() == 0) {
            throw new Exception("Please provide your business name");
        }

        if (vendor.getName() == null || vendor.getName().length() == 0) {
            throw new Exception("Please provide your name");
        }

        Vendor vendor1 = getByEmail(vendor.getEmail());
        if (vendor1 != null) {
            throw new Exception("Email is already registered");
        }

        vendor1 = getByBusinessName(vendor.getBusinessName());
        if (vendor1 != null) {
            throw new Exception("Business already registered");
        }

        vendor.setStatus(VendorStatus.REGISTERED);
        return vendorRepository.save(vendor);
    }

    public boolean validateEmail(String emailStr) {
        Pattern VALID_EMAIL_ADDRESS_REGEX =
                Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

    public Vendor getByEmail(String email) {
        return vendorRepository.findByEmail(email);
    }

    public Vendor getByBusinessName(String businessName) {
        return vendorRepository.findByBusinessName(businessName);
    }
}
