package com.rail.card.ticket.service;

import com.auth0.jwt.exceptions.JWTCreationException;
import com.rail.card.ticket.config.JwtConfig;
import com.rail.card.ticket.constant.ApplicationEnum;
import com.rail.card.ticket.model.dto.LoginRequest;
import com.rail.card.ticket.model.dto.LoginResponse;
import com.rail.card.ticket.model.dto.RegisterRequest;
import com.rail.card.ticket.model.Role;
import com.rail.card.ticket.model.User;
import com.rail.card.ticket.repository.RoleRepository;
import com.rail.card.ticket.repository.UserRepository;
import com.rail.card.ticket.support.BcryptWrapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.security.auth.message.AuthException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.management.ReflectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

@Service
public class AuthService {

    @Autowired
    private JwtConfig jwtConfigData;
    private static final BcryptWrapper bcrypt = new BcryptWrapper(11);

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

    public User register(RegisterRequest registerRequest) throws AuthException, ReflectionException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        try {
            User userModel = this.createUser(registerRequest);
            return userModel;
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException |
                 DataIntegrityViolationException var5) {
            throw new AuthException("User Already Exsist");
        }

    }

    public User createUser(RegisterRequest registerRequest) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        User entity = new User();
        entity.setPassword(bcrypt.hash(registerRequest.getPassword()));
        BeanUtils.copyProperties(registerRequest, entity);
        Optional<Role> role = Optional.empty();
        role = roleRepository.findByRole(ApplicationEnum.Group.Admin);
        entity.setRole(role.get());
        return entity;

    }

    public LoginResponse login(LoginRequest loginRequest) throws AuthException, ReflectionException {
        User userLogin = new User();
        User user = userRepository.findFirstByEmail(loginRequest.getEmail());
        if (!user.getStatus()) {
            throw new IllegalStateException("Account is not activated");
        }
        LoginResponse loginResponse = userLogin != null ? createToken(userLogin) : null;
        return loginResponse;
    }

    public User activateUser(String email) {
        User user = new User();
        if (userRepository.findFirstByEmail(email) != null) {
            user = userRepository.findFirstByEmail(email);
        } else{
            throw new EntityNotFoundException("User not found");
        }
        user.setStatus(true);
        return userRepository.save(user);
    }

    public LoginResponse createToken(User user) {

        Map<String, Object> tokenPayload = setPayloadToken(user);
        return new LoginResponse(createToken(tokenPayload, tokenType.AUTH_TOKEN));
    }

    public Map<String, Object> setPayloadToken(User userModel) {
        Map<String, Object> tokenPayload = new HashMap();
        tokenPayload.put("email", userModel.getEmail());
        tokenPayload.put("role", userModel.getRole());
        return tokenPayload;
    }

    public String createToken(Map<String, Object> payload, tokenType type) throws JWTCreationException {
        String secreet = "";
        long expiration = TimeUnit.MINUTES.toMillis((long)this.jwtConfigData.getAuthTokenLifetime());
        if (type.equals(tokenType.AUTH_TOKEN)) {
            secreet = this.jwtConfigData.getAuthTokenSecreet();
        } else if (type.equals(tokenType.REFRESH_TOKEN)) {
            expiration = TimeUnit.MINUTES.toMillis((long)this.jwtConfigData.getRefreshTokenLifetime());
            secreet = this.jwtConfigData.getRefreshTokenSecreet();
        }

        Algorithm algorithm = Algorithm.HMAC256(secreet);
        return JWT.create().withPayload(payload).withExpiresAt(new Date(System.currentTimeMillis() + expiration)).sign(algorithm);
    }

    public static enum tokenType {
        AUTH_TOKEN,
        REFRESH_TOKEN;

        private tokenType() {
        }
    }


}
