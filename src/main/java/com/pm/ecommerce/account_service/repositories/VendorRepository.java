package com.pm.ecommerce.account_service.repositories;

import com.pm.ecommerce.entities.Vendor;
import com.pm.ecommerce.enums.VendorStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendorRepository extends JpaRepository<Vendor, Integer> {


    Vendor findByEmail(String email);

    Vendor findByBusinessName(String businessName);

    Page<Vendor> findAll(Pageable pageable);

    Page<Vendor> findAllByStatus(VendorStatus status, Pageable pageable);
}
