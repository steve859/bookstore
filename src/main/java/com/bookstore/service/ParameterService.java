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

    public double getParamValue(String name) {
        return parameterRepository.findByParamName(name)
                .map(Parameters::getValue)
                .orElseThrow(() -> new RuntimeException("Parameter not found: " + name));
    }

    public void setParamValue(String name, double value) {
        Parameters param = new Parameters(name, value);
        parameterRepository.save(param);
    }

    public void save(String name, Double value) {
        parameterRepository.save(new Parameters(name, value));
    }

    public Map<String, Double> getAll() {
        return parameterRepository.findAll().stream()
                .collect(Collectors.toMap(Parameters::getParamName, Parameters::getValue));
    }
}
