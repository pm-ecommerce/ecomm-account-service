package com.pm.ecommerce.account_service.controllers;


import com.pm.ecommerce.account_service.models.UserResponse;
import com.pm.ecommerce.account_service.services.UserService;
import com.pm.ecommerce.account_service.utils.JwtTokenUtil;
import com.pm.ecommerce.entities.ApiResponse;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/token")
public class TokenController {

        @Autowired
        JwtTokenUtil jwtTokenUtil;

        @Autowired
        private UserService userService;

    @GetMapping("")
    public ResponseEntity<ApiResponse<UserResponse>> getUserByToken(@RequestParam String tokenstr, @RequestParam String userType) {
        ApiResponse<UserResponse> response = new ApiResponse<>();
        try {
            Claims claims = jwtTokenUtil.extractAllClaims(tokenstr);
            int userId = (int) claims.get("id");
            String type = (String) claims.get("type");
            UserResponse user1 = userService.getUserById(userId);
            System.out.println("User id " +userId);
            response.setMessage("Users Fetched Successfully");
            response.setData(user1);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setStatus(500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
