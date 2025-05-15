package com.bookstore.controller;

import java.text.ParseException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookstore.dto.request.ApiResponse;
import com.bookstore.dto.request.AuthenticationRequest;
import com.bookstore.dto.request.IntrospectRequest;
import com.bookstore.dto.request.LogoutRequest;
import com.bookstore.dto.request.RefreshRequest;
import com.bookstore.dto.response.AuthenticationResponse;
import com.bookstore.dto.response.IntrospectResponse;
import com.bookstore.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    AuthenticationService authenticationService;

    @PostMapping("/token")
    ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
        log.info("Success");
        var res = authenticationService.authenticate(authenticationRequest);
        return ApiResponse.<AuthenticationResponse>builder()
                .code(1000)
                .result(res)
                .build();
    }

    @PostMapping("/introspect")
    ApiResponse<IntrospectResponse> authenticate(@RequestBody IntrospectRequest request)
            throws ParseException, JOSEException {
        log.info("Success");
        var result = authenticationService.introspect(request);
        return ApiResponse.<IntrospectResponse>builder()
                .result(result)
                .build();
    }

    @PostMapping("/logout")
    ApiResponse<Void> logout(@RequestBody LogoutRequest request) throws JOSEException, ParseException {
        log.info("Success");
        authenticationService.logout(request);
        return ApiResponse.<Void>builder()
                .build();
    }

    @PostMapping("/refresh")
    ApiResponse<AuthenticationResponse> refreshToken(@RequestBody RefreshRequest authenticationRequest)
            throws ParseException, JOSEException {
        log.info("Success");
        var res = authenticationService.refreshToken(authenticationRequest);
        return ApiResponse.<AuthenticationResponse>builder()
                .code(1000)
                .result(res)
                .build();
    }

}
