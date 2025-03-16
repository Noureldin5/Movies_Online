package org.example.midterm.service.impl;

import org.example.midterm.dto.type.TypeResponse;
import org.example.midterm.entities.Type;
import org.example.midterm.exception.NotFoundException;
import org.example.midterm.mapper.TypeMapper;
import org.example.midterm.repositories.TypeRepository;
import org.example.midterm.service.TypeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TypeServiceImpl implements TypeService {

    private final TypeMapper typeMapper;
    private final TypeRepository typeRepository;
    @Override
    public void addType(String name) {
        if (typeRepository.findByName(name).isPresent())
            throw new NotFoundException("type with name: "+name+" is already exist!", HttpStatus.BAD_REQUEST);
        Type type = new Type();
        type.setName(name);
        typeRepository.save(type);
    }

    @Override
    public List<TypeResponse> getAll() {
        return typeMapper.toDtoS(typeRepository.findAll());
    }
}