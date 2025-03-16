package org.example.midterm.mapper.impl;

import org.example.midterm.dto.type.TypeResponse;
import org.example.midterm.entities.Type;
import org.example.midterm.mapper.TypeMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TypeMapperImpl implements TypeMapper {
    @Override
    public List<TypeResponse> toDtoS(List<Type> all) {
        List<TypeResponse> typeResponses = new ArrayList<>();
        for (Type type: all){
            typeResponses.add(toDto(type));
        }
        return typeResponses;
    }

    private TypeResponse toDto(Type type) {
        TypeResponse typeResponse = new TypeResponse();
        typeResponse.setId(type.getId());
        typeResponse.setName(type.getName());
        return typeResponse;
    }
}