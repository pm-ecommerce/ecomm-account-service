package com.pm.ecommerce.account_service.controllers;
import com.pm.ecommerce.account_service.services.VendorService;
import com.pm.ecommerce.entities.ApiResponse;
import com.pm.ecommerce.entities.Vendor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/vendors")
public class VendorController {

    @Autowired
    private VendorService vendorService;

    @PostMapping("")
    public ResponseEntity<ApiResponse<Vendor>> createVendor(@RequestBody Vendor vendor){
        ApiResponse<Vendor> response = new ApiResponse<>();
        try{
            Vendor vendor1 = vendorService.createVendor(vendor);
            response.setMessage("Vendor registered successfully");
            response.setData(vendor1);
            return ResponseEntity.ok(response);
        }catch (Exception e){
            response.setMessage(e.getMessage());
            response.setStatus(500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

    }

}
