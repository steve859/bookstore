package com.bookstore.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookstore.service.ParameterService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/parameters")
@RequiredArgsConstructor
public class ParameterController {
    private final ParameterService parameterService;

    @PostMapping
    public ResponseEntity<Void> saveAll(@RequestBody Map<String, Double> params) {
        params.forEach(parameterService::save);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public Map<String, Double> getAll() {
        return parameterService.getAll();
    }
}
