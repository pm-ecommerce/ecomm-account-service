package com.pm.ecommerce.account_service.repositories;

import com.pm.ecommerce.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);


    Page<User> findAllByPasswordNotNull(Pageable paging);
    Page<User> findAllByPasswordNull(Pageable paging);
}
