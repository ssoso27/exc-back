package com.swordmaster.excalibur.util;

import com.swordmaster.excalibur.enumclass.UserRole;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class UserRoleConverter implements AttributeConverter<UserRole, String> {

    @Override
    public String convertToDatabaseColumn(UserRole userRole) {
        if (userRole == null) {
            return null;
        }
        return userRole.getName();
    }

    @Override
    public UserRole convertToEntityAttribute(String name) {
        if (name == null) {
            return null;
        }

        return Stream.of(UserRole.values())
                .filter(c -> c.getName().equals(name))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}