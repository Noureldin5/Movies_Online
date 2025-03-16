package org.example.midterm.mapper;

import org.example.midterm.dto.type.TypeResponse;
import org.example.midterm.entities.Type;
import org.example.midterm.mapper.impl.TypeMapperImpl;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class TypeMapperTest {

    private final TypeMapper typeMapper = new TypeMapperImpl();

    @Test
    void toDtoS() {
        Type type = new Type("Action");
        List<Type> types = List.of(type);
        List<TypeResponse> typeResponses = typeMapper.toDtoS(types);

        assertNotNull(typeResponses);
        assertEquals(1, typeResponses.size());
        assertEquals("Action", typeResponses.get(0).getName());
    }
}