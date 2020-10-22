package com.pm.ecommerce.account_service.repositories;

import com.pm.ecommerce.entities.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendorRepository extends JpaRepository<Vendor, Integer> {


    Vendor findByEmail(String email);

    Vendor findByBusinessName(String businessName);
}
