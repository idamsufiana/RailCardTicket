package com.rail.card.ticket.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class RegisterRequest {
    private String UserName;
    private String password;
    @NotBlank(message = "Email is mandatory")
    @Email(message = "Invalid email format")
    private String email;
    @NotBlank(message = "Phone number is mandatory")
    @Pattern(regexp = "\\+628\\d{8,12}", message = "Invalid phone number format")
    private String phone;
    private String role;
}
