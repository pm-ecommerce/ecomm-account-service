package com.pm.ecommerce.account_service.services;

import com.pm.ecommerce.account_service.Models.VendorRequest;
import com.pm.ecommerce.account_service.Models.VendorResponse;
import com.pm.ecommerce.account_service.repositories.VendorRepository;
import com.pm.ecommerce.entities.Vendor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class VendorService {

    @Autowired
    private VendorRepository vendorRepository;

    //Register a Vendor
    public VendorResponse createVendor(VendorRequest vendor) throws Exception {
        if (vendor == null) {
            throw new Exception("Data expected with this request");
        }

        if (vendor.getPassword() == null || vendor.getPassword().length() == 0) {
            throw new Exception("Please provide a password");
        }

        if (vendor.getPasswordConfirmation() == null || vendor.getPasswordConfirmation().length() == 0) {
            throw new Exception("Please provide a password");
        }

        if(!vendor.getPassword().equals(vendor.getPasswordConfirmation())){
            throw new Exception("Password doesn't match!");
        }

        if (vendor.getEmail() == null || vendor.getEmail().length() == 0) {
            throw new Exception("Please provide your business email");
        }
        if (vendor.getAddress1() == null || vendor.getAddress1().length() == 0) {
            throw new Exception("Please provide your address");
        }

        if (vendor.getCity() == null || vendor.getCity().length() == 0) {
            throw new Exception("Please provide your city name");
        }

        if (vendor.getZipcode() == null || vendor.getZipcode().length() < 5) {
            throw new Exception("Please provide your ZipCode");
        }

        if (vendor.getState() == null || vendor.getState().length() != 2) {
            throw new Exception("Please provide your State ");
        }

        if (vendor.getCountry() == null || vendor.getCountry().length() == 0) {
            vendor.setCountry("USA");
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


        Vendor vendor2 = vendor.toVendor();

        vendorRepository.save(vendor2);

        return new VendorResponse(vendor2);
    }

    //Email Validation
    public boolean validateEmail(String emailStr) {
        Pattern VALID_EMAIL_ADDRESS_REGEX =
                Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

    //Get Vendor By Email
    public Vendor getByEmail(String email) {
        return vendorRepository.findByEmail(email);
    }

    //Get Vendor By Business Name
    public Vendor getByBusinessName(String businessName) {
        return vendorRepository.findByBusinessName(businessName);
    }

    //get Vendor by id
    public Vendor getById(int id) {
        return vendorRepository.findById(id).get();
    }

    public VendorResponse getVendorById(int id) {
        return new VendorResponse(getById(id));
    }

    //get all vendor
    public List<VendorResponse> getAllVenodrs() {
        return vendorRepository.findAll().stream().map(e -> new VendorResponse(e)).collect(Collectors.toList());
    }

    //Update a Vendor details
    public VendorResponse updateVendor(VendorRequest vendor, int id) throws Exception {
        Vendor vendor1 = getById(id);
        if (vendor1 == null) {
            throw new Exception("Vendor not found!");
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

        Vendor vendor3 = getByEmail(vendor.getEmail());
        if (vendor3 != null && vendor3.getId() != vendor1.getId()) {
            throw new Exception("Email is already registered");
        }

        vendor3 = getByBusinessName(vendor.getBusinessName());
        if (vendor3 != null && vendor3.getId() != vendor1.getId()) {
            throw new Exception("Business already registered");
        }

        Vendor vendor2 = vendor.toVendor();
        vendor1.setName(vendor2.getName());
        vendor1.setBusinessName(vendor2.getBusinessName());
        vendor1.setEmail(vendor2.getEmail());

        vendorRepository.save(vendor1);

        return new VendorResponse(vendor1);
    }

    //delete a vendor
    public VendorResponse deleteVendor(int id) throws Exception {
        Vendor vendor = getById(id);
        if (vendor == null) {
            throw new Exception("Vendor doesn't exist");
        }
        vendorRepository.delete(vendor);
        return new VendorResponse(vendor);
    }
}
