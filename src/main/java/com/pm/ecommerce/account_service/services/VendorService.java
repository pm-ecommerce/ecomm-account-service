package com.pm.ecommerce.account_service.services;

import com.pm.ecommerce.account_service.models.*;
import com.pm.ecommerce.account_service.notifications.events.VendorApprovedEvent;
import com.pm.ecommerce.account_service.notifications.events.VendorDisapprovedEvent;
import com.pm.ecommerce.account_service.notifications.events.VendorRegisteredEvent;
import com.pm.ecommerce.account_service.repositories.AddressRepository;
import com.pm.ecommerce.account_service.repositories.TransactionRepository;
import com.pm.ecommerce.account_service.repositories.VendorRepository;
import com.pm.ecommerce.account_service.utils.JwtTokenUtil;
import com.pm.ecommerce.entities.Address;
import com.pm.ecommerce.entities.Transaction;
import com.pm.ecommerce.entities.Vendor;
import com.pm.ecommerce.enums.VendorStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class VendorService {

    @Autowired
    private VendorRepository vendorRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private ApplicationEventPublisher publisher;

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

        if (!vendor.getPassword().equals(vendor.getPasswordConfirmation())) {
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

        Address address = addressRepository.save(vendor2.getAddress());

        vendor2.setAddress(address);
        vendor2.setCreatedDate(new Timestamp(System.currentTimeMillis()));
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
        return vendorRepository.findById(id).orElse(null);
    }

    public VendorResponse getVendorById(int id) {
        return new VendorResponse(getById(id));
    }

    /**
     * Get vendors by status
     *
     * @param status       VendorStatus
     * @param itemsPerPage Integer
     * @param currentPage  Integer
     * @return PagedResponse<VendorResponse>
     */
    public PagedResponse<VendorResponse> getAllVendorsByStatus(int status, int itemsPerPage, int currentPage) {
        VendorStatus vendorStatus = VendorStatus.APPROVED;
        if (status == 1) {
            vendorStatus = VendorStatus.PAYMENT_DONE;
        }

        if (status == 2) {
            vendorStatus = VendorStatus.UNAPPROVED;
        }

        Pageable paging = PageRequest.of(currentPage - 1, itemsPerPage);
        Page<Vendor> pagedResult = vendorRepository.findAllByStatus(vendorStatus, paging);

        int totalPages = pagedResult.getTotalPages();
        List<VendorResponse> response = pagedResult.toList().stream().map(VendorResponse::new).collect(Collectors.toList());

        return new PagedResponse<>(totalPages, currentPage, itemsPerPage, response);
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
        vendor1.setUpdatedDate(new Timestamp(System.currentTimeMillis()));

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

    //Vendor Approval API
    public VendorResponse approveVendor(int id) throws Exception {
        Vendor vendor = getById(id);
        if (vendor == null) {
            throw new Exception("Vendor not found");
        }
        if (vendor.getStatus() != VendorStatus.PAYMENT_DONE) {
            throw new Exception("Please provide the minimum payment for approval");
        }

        vendor.setStatus(VendorStatus.APPROVED);

        vendorRepository.save(vendor);

        // notify the vendor
        publisher.publishEvent(new VendorApprovedEvent(this, vendor));

        return new VendorResponse(vendor);
    }

    //Vendor Reject API
    public VendorResponse rejectVendor(int id) throws Exception {
        Vendor vendor = getById(id);
        if (vendor == null) {
            throw new Exception("Vendor not found");
        }

        if (vendor.getStatus() != VendorStatus.PAYMENT_DONE && vendor.getStatus() != VendorStatus.APPROVED) {
            throw new Exception("Please provide the minimum payment for approval");
        }

        vendor.setStatus(VendorStatus.UNAPPROVED);

        vendorRepository.save(vendor);

        // notify the vendor
        publisher.publishEvent(new VendorDisapprovedEvent(this, vendor));

        return new VendorResponse(vendor);
    }

    public VendorResponse sendForApproval(int id, int paymentId) throws Exception {
        Vendor vendor = getById(id);
        if (vendor == null) {
            throw new Exception("Vendor not found");
        }

        Transaction transaction = transactionRepository.findById(paymentId).orElse(null);
        if (transaction == null) throw new Exception("Vendor has not made a payment yet");

        vendor.setPayment(transaction);
        vendor.setStatus(VendorStatus.PAYMENT_DONE);
        vendorRepository.save(vendor);

        // notify the admin
        publisher.publishEvent(new VendorRegisteredEvent(this, vendor));

        return new VendorResponse(vendor);
    }

    public LoginResponse login(LoginRequest request) throws Exception {
        if (request.getEmail() == null || request.getEmail().length() == 0) {
            throw new Exception("Please provide your business email");
        }

        if (request.getPassword() == null || request.getPassword().length() == 0) {
            throw new Exception("Password is empty");
        }

        if (!validateEmail(request.getEmail())) {
            throw new Exception("Email is invalid. Please provide a valid email");
        }

        Vendor vendor1 = getByEmail(request.getEmail());
        if (vendor1 == null) {
            throw new Exception("Vendor not found!");
        }

        if (!vendor1.verify(request.getPassword())) {
            throw new Exception("Password did not match");
        }

        if (vendor1.getStatus() != VendorStatus.APPROVED) {
            throw new Exception("Your account has not been approved yet");
        }

        final String token = jwtTokenUtil.generateToken(vendor1, "vendor");
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setId(vendor1.getId());
        loginResponse.setEmail(vendor1.getEmail());
        loginResponse.setToken(token);
        loginResponse.setName(vendor1.getBusinessName());

        return loginResponse;
    }

    public VendorResponse updatePassword(VendorRequest request, int id) throws Exception {
        if (request == null) {
            throw new Exception("Data expected with this request.");
        }

        if (request.getPassword() == null) {
            throw new Exception("Password is missing");
        }

        if (request.getPasswordConfirmation() == null) {
            throw new Exception("Password confirmation is missing");
        }

        if (!request.getPasswordConfirmation().equals(request.getPassword())) {
            throw new Exception("Passwords do not match");
        }

        Vendor vendor = getById(id);
        if (vendor == null) {
            throw new Exception("vendor not found");
        }

        vendor.setPassword(request.getPassword());
        vendorRepository.save(vendor);

        return new VendorResponse(vendor);
    }

}
