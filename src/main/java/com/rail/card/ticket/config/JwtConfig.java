package com.rail.card.ticket.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {
    @Value("${jwt.auth.secret:PLEASE_SET}")
    private String authTokenSecreet;
    @Value("${jwt.refresh.secret:PLEASE_SET}")
    private String refreshTokenSecreet;
    @Value("${jwt.auth.lifetime:5}")
    private Integer authTokenLifetime;
    @Value("${jwt.refresh.lifetime:10000}")
    private Integer refreshTokenLifetime;

    public String getAuthTokenSecreet() {
        return this.authTokenSecreet;
    }

    public String getRefreshTokenSecreet() {
        return this.refreshTokenSecreet;
    }

    public Integer getAuthTokenLifetime() {
        return this.authTokenLifetime;
    }

    public Integer getRefreshTokenLifetime() {
        return this.refreshTokenLifetime;
    }

    public void setAuthTokenSecreet(String authTokenSecreet) {
        this.authTokenSecreet = authTokenSecreet;
    }

    public void setRefreshTokenSecreet(String refreshTokenSecreet) {
        this.refreshTokenSecreet = refreshTokenSecreet;
    }

    public void setAuthTokenLifetime(Integer authTokenLifetime) {
        this.authTokenLifetime = authTokenLifetime;
    }

    public void setRefreshTokenLifetime(Integer refreshTokenLifetime) {
        this.refreshTokenLifetime = refreshTokenLifetime;
    }
}