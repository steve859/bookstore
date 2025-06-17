package com.bookstore.service;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.bookstore.entity.Parameters;
import com.bookstore.repository.ParameterRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ParameterService {
    private final ParameterRepository parameterRepository;

    public void save(String name, Double value) {
        parameterRepository.save(new Parameters(name, value));
    }

    public Map<String, Double> getAll() {
        return parameterRepository.findAll().stream()
                .collect(Collectors.toMap(Parameters::getParamName, Parameters::getValue));
    }
}
