package org.example.midterm.mapper;

import org.example.midterm.dto.type.TypeResponse;
import org.example.midterm.entities.Type;

import java.util.List;

public interface TypeMapper {
    List<TypeResponse> toDtoS(List<Type> all);
}