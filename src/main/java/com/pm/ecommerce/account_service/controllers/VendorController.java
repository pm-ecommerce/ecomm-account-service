package com.pm.ecommerce.account_service.controllers;

import com.pm.ecommerce.account_service.models.*;
import com.pm.ecommerce.account_service.services.VendorService;
import com.pm.ecommerce.account_service.utils.JwtTokenUtil;
import com.pm.ecommerce.entities.ApiResponse;
import com.pm.ecommerce.enums.VendorStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/vendors")
public class VendorController {

    @Autowired
    private VendorService vendorService;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @GetMapping("")
    public ResponseEntity<ApiResponse<PagedResponse<VendorResponse>>> getAllVendors(
            @RequestParam(name = "itemsPerPage", defaultValue = "20") int itemsPerPage,
            @RequestParam(name = "currentPage", defaultValue = "1") int currentPage,
            @RequestParam(name = "status", defaultValue = "3") int status
    ) {
        ApiResponse<PagedResponse<VendorResponse>> response = new ApiResponse<>();
        try {
            PagedResponse<VendorResponse> vendor1 = vendorService.getAllVendorsByStatus(status, itemsPerPage, currentPage);
            response.setMessage("Vendor fetched successfully");
            response.setData(vendor1);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setStatus(500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse<VendorResponse>> createVendor(@RequestBody VendorRequest vendor) {
        ApiResponse<VendorResponse> response = new ApiResponse<>();
        System.out.println("Hello");
        try {
            VendorResponse vendor1 = vendorService.createVendor(vendor);
            response.setMessage("Vendor registered successfully");
            response.setData(vendor1);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setStatus(500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

    }

    @PostMapping("/login")
    private ResponseEntity<ApiResponse<LoginResponse>> loginVendor(@RequestBody LoginRequest loginRequest) {
        ApiResponse<LoginResponse> response = new ApiResponse<>();
        try {
            LoginResponse response1 = vendorService.login(loginRequest);
            response.setMessage("Vendor Logged In Successfully. ");
            response.setData(response1);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setStatus(500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<VendorResponse>> getVendorById(@PathVariable int id) {
        ApiResponse<VendorResponse> response = new ApiResponse<>();
        try {
            VendorResponse vendor1 = vendorService.getVendorById(id);
            response.setMessage("Vendor details loaded successfully");
            response.setData(vendor1);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setStatus(500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<VendorResponse>> updateVendor(@RequestBody VendorRequest vendor, @PathVariable int id) {
        ApiResponse<VendorResponse> response = new ApiResponse<>();
        try {
            VendorResponse vendor1 = vendorService.updateVendor(vendor, id);
            response.setMessage("Vendor updated successfully");
            response.setData(vendor1);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setStatus(500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<VendorResponse>> deleteVendor(@PathVariable int id) {
        ApiResponse<VendorResponse> response = new ApiResponse<>();
        try {
            VendorResponse vendor1 = vendorService.deleteVendor(id);
            response.setMessage("Vendor deleted successfully");
            response.setData(vendor1);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setStatus(500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

    }

    @PatchMapping("/{id}/approve")
    public ResponseEntity<ApiResponse<VendorResponse>> approveVendor(@PathVariable int id) {
        ApiResponse<VendorResponse> response = new ApiResponse<>();
        try {
            VendorResponse vendor1 = vendorService.approveVendor(id);
            response.setMessage("Vendor Approved ");
            response.setData(vendor1);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setStatus(500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

    }

    @PatchMapping("/{id}/reject")
    public ResponseEntity<ApiResponse<VendorResponse>> rejectVendor(@PathVariable int id) {
        ApiResponse<VendorResponse> response = new ApiResponse<>();
        try {
            VendorResponse vendor1 = vendorService.rejectVendor(id);
            response.setMessage("Vendor Rejected ");
            response.setData(vendor1);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setStatus(500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

    }

    @PatchMapping("/{id}/send-for-approval")
    public ResponseEntity<ApiResponse<VendorResponse>> sendForApproval(@PathVariable int id, @RequestParam int transactionId) {
        ApiResponse<VendorResponse> response = new ApiResponse<>();
        try {
            VendorResponse vendor1 = vendorService.sendForApproval(id, transactionId);
            response.setMessage("Vendor Sent for Approval ");
            response.setData(vendor1);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setStatus(500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

    }

}
