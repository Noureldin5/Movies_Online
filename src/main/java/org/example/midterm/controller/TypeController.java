package org.example.midterm.controller;

import org.example.midterm.dto.type.TypeResponse;
import org.example.midterm.service.TypeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/type")
@CrossOrigin(origins = "*", maxAge = 3600)
public class TypeController {
    private final TypeService typeService;

    @PostMapping("/add")
    public void addType(@RequestParam String name){
        typeService.addType(name);
    }
    @GetMapping("/types")
    public List<TypeResponse> typeResponses(){
        return typeService.getAll();
    }
}