package org.example.midterm.service;

import org.example.midterm.dto.type.TypeResponse;

import java.util.List;

public interface TypeService {
    void addType(String name);

    List<TypeResponse> getAll();
}