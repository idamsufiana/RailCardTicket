package com.rail.card.ticket.controller;

import com.rail.card.ticket.config.JwtUtils;
import com.rail.card.ticket.model.dto.LoginRequest;
import com.rail.card.ticket.model.dto.LoginResponse;
import com.rail.card.ticket.model.dto.RegisterRequest;
import com.rail.card.ticket.model.User;
import com.rail.card.ticket.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/Auth")
public class AuthController extends BaseController{


    @Value("${jwtCookieName}")
    private String jwtCookie;

    @Autowired
    AuthService authService;
    @Autowired
    JwtUtils jwtUtils;


    @PostMapping({"/register"})
    public ResponseEntity<Object> register(@RequestBody @Valid RegisterRequest registerRequest) {
        try {
            authService.register(registerRequest);
            return this.successRegister(null);
        } catch (Exception var3) {
            Exception exception = var3;
            exception.printStackTrace();
            return this.error(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
        }
    }

    @PostMapping({"login"})
    public ResponseEntity<Object> login(@RequestBody @Valid LoginRequest loginRequest) {
        try {
            LoginResponse loginResponse = authService.login(loginRequest);
            return success(loginResponse);
        } catch (Exception e) {
            return this.error(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

}
