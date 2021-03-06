package com.pm.ecommerce.account_service.services;

import com.pm.ecommerce.account_service.models.*;
import com.pm.ecommerce.account_service.repositories.UserRepository;
import com.pm.ecommerce.account_service.utils.JwtTokenUtil;
import com.pm.ecommerce.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
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
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;


    //create a new user
    public UserResponse createUser(UserRequest user) throws Exception {
        if (user == null) {
            throw new Exception("Data expected with this request");
        }

        if (user.getPassword() == null || user.getPassword().length() == 0) {
            throw new Exception("Please provide a password");
        }

        if (user.getPasswordConfirmation() == null || user.getPasswordConfirmation().length() == 0) {
            throw new Exception("Please provide a confirmation password");
        }

        if (!user.getPassword().equals(user.getPasswordConfirmation())) {
            throw new Exception("Password doesn't match!");
        }

        if (user.getEmail() == null || user.getEmail().length() == 0) {
            throw new Exception("Email field is empty");
        }

        if (!validateEmail(user.getEmail())) {
            throw new Exception("Please provide a valid email");
        }

        if (user.getName() == null || user.getName().length() == 0) {
            throw new Exception("Name field is Empty");
        }

        User user1 = getByEmail(user.getEmail());
        if (user1 != null) {
            throw new Exception("Email is already registered");
        }

        User user2 = user.toUser();
        user2.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        userRepository.save(user2);

        return new UserResponse(user2);
    }

    public UserResponse createGuestUser(UserRequest user) throws Exception {
        if (user == null) {
            throw new Exception("Data expected with this request");
        }

        if (user.getEmail() == null || user.getEmail().length() == 0) {
            throw new Exception("Email field is empty");
        }

        if (!validateEmail(user.getEmail())) {
            throw new Exception("Please provide a valid email");
        }

        if (user.getName() == null || user.getName().length() == 0) {
            throw new Exception("Name field is Empty");
        }

        User user1 = getByEmail(user.getEmail());
        if (user1 != null) {
            if (user1.getPassword() != null && user1.getPassword().length() > 0) {
                throw new Exception("An account associated with this email already exists. Please login to your account.");
            }

            user1.setName(user.getName());
            userRepository.save(user1);

            return new UserResponse(user1);
        }

        User user2 = user.toUser();
        user2.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        userRepository.save(user2);

        return new UserResponse(user2);
    }


    //update a User
    public UserResponse updateUser(UserRequest user, int id) throws Exception {
        User user1 = getById(id);
        if (user1 == null) {
            throw new Exception("User not found");
        }
        if (user.getEmail() == null || user.getEmail().length() == 0) {
            throw new Exception("Email is empty");
        }

        if (!validateEmail(user.getEmail())) {
            throw new Exception("Email is invalid. Please provide a valid email");
        }

        if (user.getName() == null || user.getName().length() == 0) {
            throw new Exception("Name is empty");
        }

        User user2 = getByEmail(user.getEmail());
        if (user2 != null && user2.getId() != user1.getId()) {
            throw new Exception("Email is already being used by another account");
        }
        User user3 = user.toUser();
        user1.setName(user3.getName());
        user1.setEmail(user3.getEmail());
        user1.setUpdatedDate(new Timestamp(System.currentTimeMillis()));
        userRepository.save(user1);
        return new UserResponse(user1);

    }

    //delete a user
    public UserResponse deleteUser(int id) throws Exception {
        User user = getById(id);
        if (user == null) {
            throw new Exception("User doesn't exist");
        }
        userRepository.delete(user);
        return new UserResponse(user);
    }


    public boolean validateEmail(String emailStr) {
        Pattern VALID_EMAIL_ADDRESS_REGEX =
                Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

    public LoginResponse login(LoginRequest request) throws Exception {
        if (request.getEmail() == null || request.getEmail().length() == 0) {
            throw new Exception("Email is empty");
        }

        if (request.getPassword() == null || request.getPassword().length() == 0) {
            throw new Exception("Password is empty");
        }

        if (!validateEmail(request.getEmail())) {
            throw new Exception("Email is invalid. Please provide a valid email");
        }

        User user = getByEmail(request.getEmail());
        if (user == null) {
            throw new Exception("User not found");
        }

        if (!validateEmail(user.getEmail())) {
            throw new Exception("Email is invalid. Please provide a valid email");
        }

        if (!user.verify(request.getPassword())) {
            throw new Exception("Password did not match");
        }

        final String token = jwtTokenUtil.generateToken(user, "user");
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setId(user.getId());
        loginResponse.setEmail(user.getEmail());
        loginResponse.setToken(token);
        loginResponse.setName(user.getName());

        return loginResponse;
    }

    public PagedResponse<UserResponse> getAllUsers(int itemsPerPage, int currentPage) {
        Pageable paging = PageRequest.of(currentPage - 1, itemsPerPage);
        Page<User> pagedResult = userRepository.findAllByPasswordNotNull(paging);

        int totalPages = pagedResult.getTotalPages();
        List<UserResponse> response = pagedResult.toList().stream().map(UserResponse::new).collect(Collectors.toList());

        return new PagedResponse<>(totalPages, currentPage, itemsPerPage, response);
    }

    public PagedResponse<UserResponse> getAllGuestUsers(int itemsPerPage, int currentPage) {
        Pageable paging = PageRequest.of(currentPage - 1, itemsPerPage);
        Page<User> pagedResult = userRepository.findAllByPasswordNull(paging);

        int totalPages = pagedResult.getTotalPages();
        List<UserResponse> response = pagedResult.toList().stream().map(UserResponse::new).collect(Collectors.toList());

        return new PagedResponse<>(totalPages, currentPage, itemsPerPage, response);
    }

    // get user by Email
    public User getByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    //get user by Id
    public User getById(int id) {
        return userRepository.findById(id).get();
    }

    public UserResponse getUserById(int id) {
        return new UserResponse(getById(id));
    }

    public UserResponse updatePassword(UserRequest request, int id) throws Exception {
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

        User user = getById(id);
        if (user == null) {
            throw new Exception("User not found");
        }

        user.setPassword(request.getPassword());
        userRepository.save(user);

        return new UserResponse(user);
    }

}
