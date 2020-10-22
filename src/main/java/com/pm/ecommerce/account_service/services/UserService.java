package com.pm.ecommerce.account_service.services;

import com.pm.ecommerce.account_service.repositories.UserRepository;

import com.pm.ecommerce.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User createUser(User user) throws Exception {
        if (user == null) {
            throw new Exception("Data expected with this request");
        }

        if (user.getEmail() == null || user.getEmail().length() == 0) {
            throw new Exception("Email field is empty");
        }

        if(!validateEmail(user.getEmail())){
            throw new Exception("Please provide a valid email");
        }

        if (user.getName() == null || user.getName().length() == 0) {
            throw new Exception("Name field is Empty");
        }

        User user1 = getByEmail(user.getEmail());
        if (user1 != null) {
            throw new Exception("Email is already registered");
        }

        return userRepository.save(user);
    }

    public boolean validateEmail(String emailStr) {
        Pattern VALID_EMAIL_ADDRESS_REGEX =
                Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

    public User getByEmail(String email) {
        return userRepository.findByEmail(email);
    }


}
