package com.bookstore.controller;

import com.bookstore.dto.request.ApiResponse;
import com.bookstore.dto.request.AuthenticationRequest;
import com.bookstore.dto.request.IntrospectRequest;
import com.bookstore.dto.response.AuthenticationResponse;
import com.bookstore.dto.response.IntrospectResponse;
import com.bookstore.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.text.ParseException;

import org.hibernate.annotations.Filter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    AuthenticationService authenticationService;
    private final RestClient.Builder builder;

    @PostMapping("/token")
    ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
        var res = authenticationService.authenticate(authenticationRequest);
        return ApiResponse.<AuthenticationResponse>builder()
                .code(1000)
                .result(res)
                .build();
    }

    @PostMapping("/introspect")
    ApiResponse<IntrospectResponse> introspect(@RequestBody IntrospectRequest request) throws JOSEException, ParseException{
        var res = authenticationService.introspect(request);
        return ApiResponse.<IntrospectResponse>builder()
            .result(res)
            .code(1000)
        .build();
    }
    
}
